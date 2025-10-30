package servlets;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import database.tables.EventTable;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import com.google.gson.Gson;
import mainClasses.Event;

public class SearchEvent extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        String eventIdParam = request.getParameter("event_id");
        if (eventIdParam == null || eventIdParam.isEmpty()) {
            response.setStatus(400);
            out.print("{\"error\": \"Missing or invalid 'event_id' parameter.\"}");
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdParam);
            EventTable eventTable = new EventTable();
            List<String> availableTickets = eventTable.searchEvent(eventId);

            if (availableTickets.isEmpty()) {
                response.setStatus(404);
                out.print("{\"error\": \"Event not found.\"}");
            } else {
                JsonObject json = new JsonObject();
                json.addProperty("vip", availableTickets.get(0));
                json.addProperty("regular", availableTickets.get(1));
                json.addProperty("seat", availableTickets.get(2));
                json.addProperty("capacity", availableTickets.get(3));
                response.setStatus(200);
                out.print(json);
            }
        } catch (NumberFormatException e) {
            response.setStatus(400);
            out.print("{\"error\": \"Invalid event_id. It must be an integer.\"}");
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(500);
            out.print("{\"error\": \"An error occurred while retrieving the event.\"}");
            e.printStackTrace(out);
        }
    }
}
