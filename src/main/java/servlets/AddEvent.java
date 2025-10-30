package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mainClasses.Event;
import mainClasses.User;

import java.io.BufferedReader;
import java.io.IOException;

public class AddEvent extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        try(BufferedReader r = request.getReader()){
            JsonObject jsonObject = gson.fromJson(r, JsonObject.class);
            Event event = gson.fromJson(jsonObject, Event.class);
            event.insertEvent();
            JsonObject jsonResponse = new JsonObject();
            jsonResponse.addProperty("message", "Η εγγραφή σας πραγματοποιήθηκε επιτυχώς");
            jsonResponse.add("EventData", gson.toJsonTree(event));
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
