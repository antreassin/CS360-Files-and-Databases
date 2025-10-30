package database.tables;

import java.sql.Connection;
import java.sql.SQLException;
import database.DB_Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CREATE TABLE events(
 * 	event_id INT not NULL AUTO_INCREMENT,
 * 	event_date DATE,
 * 	name VARCHAR(20),
 * 	time DATETIME,
 * 	type VARCHAR(20),
 * 	capacity INT DEFAULT 0,
 * 	PRIMARY KEY (event_id)
 * );
 */
public class EventTable {
    public void createEventTable() throws SQLException, ClassNotFoundException {
        Connection connection = DB_Connection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE events "
                + "(event_id INTEGER AUTO_INCREMENT, "
                + "event_date DATE, "
                + "name VARCHAR(20), "
                + "time TIME, "
                + "vip INTEGER, "
                + "seat INTEGER, "
                + "regular INTEGER, "
                + "type VARCHAR(20), "
                + "income INTEGER DEFAULT 0, "
                + "capacity INT AS (vip + regular + seat) STORED,"
                + "PRIMARY KEY (event_id))";
        statement.execute(sql);
        statement.close();
    }
    public List<String> searchEvent(int event_id) throws SQLException, ClassNotFoundException {
        Connection connection = DB_Connection.getConnection();
        String sql = "SELECT vip, regular, seat, capacity FROM events WHERE event_id = ?";
        List<String> availableTickets = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, event_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    availableTickets.add("VIP: " + resultSet.getInt("vip"));
                    availableTickets.add("Regular: " + resultSet.getInt("regular"));
                    availableTickets.add("Seat: " + resultSet.getInt("seat"));
                    availableTickets.add("Capacity: " + resultSet.getInt("capacity"));
                }
            }
        }

        return availableTickets;
    }
    public void updateEventTable(int vip,int regular,int seat,int event_id)
            throws SQLException, ClassNotFoundException {int cap = vip+regular+seat;
        int total = 30*vip+20*seat+10*regular;
            Connection connection = DB_Connection.getConnection();
            String sql = "UPDATE events SET vip = vip - ?, regular = regular - ?, seat = seat - ?, " +
                    "income = income + ? WHERE event_id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setInt(1,vip);
                preparedStatement.setInt(2,regular);
                preparedStatement.setInt(3,seat);
                preparedStatement.setInt(4,total);
                preparedStatement.setInt(5,event_id);
                preparedStatement.executeUpdate();
                System.out.println("update event,done");
            }catch (SQLException e) {
                e.printStackTrace();
            }
    }
    public void deleteEvent(int idx) throws SQLException, ClassNotFoundException {
        String sql =  "DELETE FROM `events` WHERE `event_id` = ?";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,idx);
            preparedStatement.executeUpdate();
        }
    }

}
