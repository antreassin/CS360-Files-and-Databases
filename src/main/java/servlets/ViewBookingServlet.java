package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mainClasses.ViewBookingDAO;
import mainClasses.ViewBookingSQL;

import java.io.IOException;
import java.util.List;

public class ViewBookingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ViewBookingDAO dao = new ViewBookingDAO();
        try {
            List<ViewBookingSQL> viewList = dao.getBookingYears();
            String json = new Gson().toJson(viewList);
            resp.setStatus(200);
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"error\": \"Failed to fetch ticket status.\"}");
        }
    }
}
