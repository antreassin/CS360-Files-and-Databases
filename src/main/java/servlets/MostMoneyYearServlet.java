package servlets;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import mainClasses.MostMoneyDAO;
import mainClasses.MostMoneyYearSQL;

import java.io.IOException;

public class MostMoneyYearServlet extends HttpServlet{
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            String startDate = req.getParameter("startDate");
            String endDate = req.getParameter("endDate");
            if (startDate == null || endDate == null) {
                resp.setStatus(400); // Bad request
                resp.getWriter().write("{\"error\": \"Start date and end date are required.\"}");
                return;
            }
            try {
                MostMoneyDAO mostMoneyDAO = new MostMoneyDAO();
                MostMoneyYearSQL topIncomeEvent = mostMoneyDAO.getTopIncomeEvent(startDate, endDate);

                if (topIncomeEvent != null) {
                    String json = new Gson().toJson(topIncomeEvent);
                    resp.setStatus(200);
                    resp.getWriter().write(json);
                } else {
                    resp.setStatus(404);
                    resp.getWriter().write("{\"error\": \"No events found in the specified date range.\"}");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.setStatus(500);
                resp.getWriter().write("{\"error\": \"Failed to fetch top income event.\"}");
            }
        }
}