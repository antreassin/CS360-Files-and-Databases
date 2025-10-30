package database.tables;
import java.sql.Connection;
import java.sql.SQLException;
import database.DB_Connection;
import java.sql.*;
import java.util.Objects;

/**
 *
 CREATE TABLE tickets(
 ticket_id INT not NULL AUTO_INCREMENT,
 available BOOLEAN,
 price INT not NULL,
 type VARCHAR(20),
 PRIMARY KEY (ticket_id)
 );

 */
public class TicketsTable {
    public void createTicketTable() throws SQLException, ClassNotFoundException {
        Connection connection = DB_Connection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE tickets "
                + "(ticket_id INTEGER AUTO_INCREMENT, "
                + "available BOOLEAN, "
                + "price INT , "
                + "type VARCHAR(20), "
                + "booking_id INTEGER, "
                + "PRIMARY KEY (ticket_id))";
        statement.execute(sql);
        statement.close();
    }
    public void insertTicket(boolean available, int price, int booking_id,String type) {
        String sql =  "INSERT INTO tickets (available, price, booking_id, type) VALUES (?, ?, ?, ?)";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1,available);
            preparedStatement.setInt(2,price);
            preparedStatement.setInt(3,booking_id);
            preparedStatement.setString(4,type);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public int[] getNumberOfFromTickets(int idx) throws SQLException, ClassNotFoundException {
        int[] result = new int[3];
        String sql = "SELECT `type` FROM `tickets` WHERE `booking_id` = ?";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idx);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    if (Objects.equals(type, "VIP")) {
                        result[0]++;
                    } else if (Objects.equals(type, "Seat")) {
                        result[1]++;
                    } else {
                        result[2]++;
                    }
                }
            }
        }
        return result;
    }
    public void deleteTicket(int idx) throws SQLException, ClassNotFoundException {
        String sql =  "DELETE FROM `tickets` WHERE `booking_id` = ?";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,idx);
            preparedStatement.executeUpdate();
        }
    }

}
