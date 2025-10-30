package servlets;

import database.tables.BookingTable;
import database.tables.EventTable;
import database.tables.TicketsTable;
import jakarta.servlet.http.HttpServlet;
import org.json.JSONObject;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CancelEvent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try {
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String jsonString = jsonBuffer.toString();
            JSONObject json = new JSONObject(jsonString);
            int event_id = json.getInt("cancelEvent_id");
            BookingTable bt = new BookingTable();
            TicketsTable tt = new TicketsTable();
            List<Integer> deletedBooking = new ArrayList<>();
            //sbino bookings
            try {
                deletedBooking = bt.deleteBookingsByEvent(event_id);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            // sbinw tickets associated w bookings
            for(int id : deletedBooking){
                try {
                    tt.deleteTicket(id);
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            EventTable ev = new EventTable();
            try {
                ev.deleteEvent(event_id);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            response.setStatus(HttpServletResponse.SC_OK);
            JSONObject successResponse = new JSONObject();
            successResponse.put("message", "Event canceled successfully");
            response.getWriter().write(successResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Unexpected error: " + e.getMessage());
            response.getWriter().write(errorResponse.toString());
        }
    }
}