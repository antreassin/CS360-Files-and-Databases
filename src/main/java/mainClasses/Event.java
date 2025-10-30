package mainClasses;

import database.DB_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Event {
    int event_id,capacity,vip,regular,seat;
    String event_date,time,type,name;
    public Event(int event_id, int capacity, String event_date, String time, String type, String name, int vip, int regular, int seat){
        this.event_id = event_id;
        this.capacity = vip + regular + seat;
        this.event_date = event_date;
        this.time = time;
        this.type = type;
        this.name = name;
        this.vip = vip;
        this.regular = regular;
        this.seat = seat;
    }
    void setEvent_id(int event_id){
        this.event_id = event_id;
    }
    void setCapacity(int capacity){
        this.capacity = capacity;
    }
    void setEvent_date(String event_date){
        this.event_date = event_date;
    }
    void setTime(String time){
        this.time = time;
    }
    void setType(String type){
        this.type = type;
    }
    void setName(String name){
        this.name = name;
    }
    int getEvent_id(){
        return event_id;
    }
    int getCapacity(){
        return capacity;
    }
    String getEvent_date(){
        return event_date;
    }
    String getTime(){
        return time;
    }
    String getType(){
        return type;
    }
    String getName(){
        return name;
    }
    int getVip(){
        return vip;
    }
    int getRegular(){
        return regular;
    }
    int getSeat(){
        return seat;
    }
    void setVip(int vip){
        this.vip = vip;
    }
    void setRegular(int regular){
        this.regular = regular;
    }
    void setSeat(int seat){
        this.seat = seat;
    }
    public void insertEvent() {
        String sql = "INSERT INTO `events`(`event_date`, `name`, `time`, `vip`, `seat`, `regular`, `type`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, this.getEvent_date());
            preparedStatement.setString(2, this.getName());
            preparedStatement.setString(3, this.getTime());
            preparedStatement.setInt(4, this.getVip());
            preparedStatement.setInt(5, this.getSeat());
            preparedStatement.setInt(6, this.getRegular());
            preparedStatement.setString(7, this.getType());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events";

        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("event_id"),
                        resultSet.getInt("vip") + resultSet.getInt("regular") + resultSet.getInt("seat"),
                        resultSet.getString("event_date"),
                        resultSet.getString("time"),
                        resultSet.getString("type"),
                        resultSet.getString("name"),
                        resultSet.getInt("vip"),
                        resultSet.getInt("regular"),
                        resultSet.getInt("seat")
                );
                events.add(event);
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return events;
    }

}
