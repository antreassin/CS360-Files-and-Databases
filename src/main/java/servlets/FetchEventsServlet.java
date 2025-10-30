package servlets;

import com.google.gson.Gson;
import mainClasses.Event;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
public class FetchEventsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Event> events = null;
        events = Event.getAllEvents();

        Gson gson = new Gson();
        String json = gson.toJson(events);

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

    }
}