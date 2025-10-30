package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mainClasses.EventTicketDAO;
import mainClasses.EventTicketSQL;

import java.io.IOException;
import java.util.List;

public class EventTicketServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        EventTicketDAO dao = new EventTicketDAO();
        try {
            List<EventTicketSQL> ticketStatusList = dao.getEventTicketStatus();
            String json = new Gson().toJson(ticketStatusList);
            resp.setStatus(200);
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"error\": \"Failed to fetch ticket status.\"}");
        }
    }
}
