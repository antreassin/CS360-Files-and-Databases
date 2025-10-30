package servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mainClasses.PopularDao;
import mainClasses.PopularSQL;

import java.io.IOException;

public class PopularServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        try {
            PopularDao popularDao = new PopularDao();
            PopularSQL popular = popularDao.getPopular();
            String json = new Gson().toJson(popular);
            resp.setStatus(200);
            resp.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
            resp.getWriter().write("{\"error\": \"Failed to fetch popular event.\"}");
        }
    }
}
