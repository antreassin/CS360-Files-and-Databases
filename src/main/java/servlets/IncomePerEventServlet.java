package servlets;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mainClasses.IncomePerEventDAO;
import mainClasses.IncomePerEventSQL;

import java.io.IOException;
import java.util.List;


public class IncomePerEventServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        IncomePerEventDAO dao = new IncomePerEventDAO();
        try {
            List<IncomePerEventSQL> viewList = dao.getIncomePerEvent();
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
