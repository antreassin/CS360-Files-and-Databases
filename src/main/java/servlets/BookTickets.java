package servlets;
import com.google.gson.JsonObject;
import database.DB_Connection;
import database.tables.BookingTable;
import database.tables.EventTable;
import database.tables.TicketsTable;
import jakarta.servlet.annotation.WebServlet;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class BookTickets extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        try{
            StringBuilder jsonBuffer = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    jsonBuffer.append(line);
                }
            }
            String jsonString = jsonBuffer.toString();
            JSONObject json = new JSONObject(jsonString);
            int eventId = json.getInt("event_id");
            int userId = json.getInt("user_id");
            int vipTickets = json.getInt("vip");
            int regularTickets = json.getInt("regular_book");
            int seatTickets = json.getInt("seat_book");
            int quantity = vipTickets + regularTickets + seatTickets;
            int cost = (vipTickets * 30) + (regularTickets * 10) + (seatTickets * 20);
            int bookingId;
            BookingTable bt = new BookingTable();
            bookingId = bt.insertBooking(userId,eventId,quantity,cost);
            EventTable et = new EventTable();
            et.updateEventTable(vipTickets,regularTickets,seatTickets,eventId);
            TicketsTable tt = new TicketsTable();
            for (int i = 0; i < vipTickets; i++) {
                tt.insertTicket(false,30,bookingId,"VIP");
            }
            for (int i = 0; i < regularTickets; i++) {
                tt.insertTicket(false,10,bookingId,"Regular");
            }
            for (int i = 0; i < seatTickets; i++) {
                tt.insertTicket(false,20,bookingId,"Seat");
            }
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("{\"message\": \"Booking successful!\"}");
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("error", "Error occurred in BookTickets: " + e.getMessage());
            resp.getWriter().write(errorResponse.toString());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("error", "Unexpected error occurred: " + e.getMessage());
            resp.getWriter().write(errorResponse.toString());
        }
    }

}
