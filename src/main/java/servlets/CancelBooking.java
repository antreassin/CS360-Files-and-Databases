package servlets;

import database.tables.BookingTable;
import database.tables.TicketsTable;
import jakarta.servlet.http.HttpServlet;
import org.json.JSONObject;
import database.DB_Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.*;

public class CancelBooking extends HttpServlet {
    @Override
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
            int bookingId = json.getInt("cancelBook_id");
            boolean refundRequested = json.optBoolean("refund", false);
            BookingTable bt = new BookingTable();
            int[] arr =new int[2];
            int user_id,event_id;
            arr = bt.getUserEventFROM_Booking(bookingId);
            user_id = arr[0];
            event_id = arr[1];
            int money;
            int[] typesT = new int[3];
            TicketsTable tt = new TicketsTable();
            typesT = tt.getNumberOfFromTickets(bookingId);
            money = typesT[0]*30+typesT[1]*20+typesT[2]*10;
            int tax = (int) (0.20 * money);
            tt.deleteTicket(bookingId);
            bt.deleteBooking(bookingId);
            try (Connection conn = DB_Connection.getConnection()) {
                if (!refundRequested) {
                    String sql = "UPDATE events SET vip = vip + ?, regular = regular + ?, seat = seat + ?, " +
                            "income = income - ? WHERE event_id = ?";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setInt(1,typesT[0]);
                    preparedStatement.setInt(2,typesT[2]);
                    preparedStatement.setInt(3,typesT[1]);
                    preparedStatement.setInt(4,money);
                    preparedStatement.setInt(5,event_id);
                    preparedStatement.executeUpdate();
                } else {
                    money -= tax;
                    String sql = "UPDATE events SET vip = vip + ?, regular = regular + ?, seat = seat + ?, " +
                            "income = income - ? WHERE event_id = ?";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setInt(1,typesT[0]);
                    preparedStatement.setInt(2,typesT[2]);
                    preparedStatement.setInt(3,typesT[1]);
                    preparedStatement.setInt(4,money);
                    preparedStatement.setInt(5,event_id);
                    preparedStatement.executeUpdate();
                }
                response.setStatus(HttpServletResponse.SC_OK);
                JSONObject successResponse = new JSONObject();
                successResponse.put("message", "Booking canceled successfully");
                response.getWriter().write(successResponse.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                JSONObject errorResponse = new JSONObject();
                errorResponse.put("error", "Error occurred while canceling booking: " + e.getMessage());
                response.getWriter().write(errorResponse.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JSONObject errorResponse = new JSONObject();
            errorResponse.put("error", "Unexpected error: " + e.getMessage());
            response.getWriter().write(errorResponse.toString());
        }
    }
}
