package servlets;

import jakarta.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.tables.UsersTable;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mainClasses.User;

public class AddToDatabase extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        try(BufferedReader r = request.getReader()){
            JsonObject jsonObject = gson.fromJson(r, JsonObject.class);
            User user = gson.fromJson(jsonObject, User.class);
            user.insertUser();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("message", "Η εγγραφή σας πραγματοποιήθηκε επιτυχώς");
            jsonResponse.add("userData", gson.toJsonTree(user));
            response.setStatus(200);
            response.getWriter().write(jsonResponse.toString());
        }catch (Exception e) {
            response.setStatus(403);
            JsonObject errorResponse = new JsonObject();
            errorResponse.addProperty("error", "Error occurred in AddToDatabase: " + e.getMessage());
            response.getWriter().write(errorResponse.toString());
        }
    }
}
