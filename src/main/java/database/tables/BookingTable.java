package database.tables;

import java.sql.Connection;
import java.sql.SQLException;
import database.DB_Connection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * CREATE TABLE Bookings(
 * 	booking_id INT not NULL AUTO_INCREMENT,
 * 	user_id INT,
 * 	event_id INT,
 * 	quantity INT,
 * 	date DATE,
 * 	cost INT,
 * 	PRIMARY KEY (booking_id)
 * );
 */
public class BookingTable {
    public void createBookingTable() throws SQLException, ClassNotFoundException {
        Connection connection = DB_Connection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE bookings "
                + "(booking_id INTEGER AUTO_INCREMENT, "
                + "user_id INTEGER, "
                + "event_id INTEGER, "
                + "quantity INTEGER, "
                + "date DATE, "
                + "cost INTEGER, "
                + "PRIMARY KEY (booking_id))";
        statement.execute(sql);
        statement.close();
    }
    public int insertBooking(int user_id,int event_id,int quantity, int cost) throws SQLException, ClassNotFoundException {
        Date bookingDate = Date.valueOf(LocalDate.now());
        Connection connection = DB_Connection.getConnection();
        String sql = "INSERT INTO bookings (user_id, event_id, quantity, date, cost) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Set the values for the INSERT query
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, event_id);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setDate(4, bookingDate);
            preparedStatement.setInt(5, cost);

            // Execute the INSERT query
            preparedStatement.executeUpdate();

            // Retrieve the generated key
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve the booking ID.");
                }
            }
        }
    }
    public int[] getUserEventFROM_Booking(int idx) throws SQLException, ClassNotFoundException {
        int[] result = new int[2];
        String sql =  "SELECT `user_id`,`event_id` FROM `bookings` WHERE ?";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,idx);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result[0] = resultSet.getInt("user_id");
                    result[1] = resultSet.getInt("event_id");
                }
            }
        }
        return result;
    }
    public void deleteBooking(int idx) throws SQLException, ClassNotFoundException {
        String sql =  "DELETE FROM `bookings` WHERE `booking_id` = ?";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1,idx);
            preparedStatement.executeUpdate();
        }
    }

    public List<Integer> deleteBookingsByEvent(int idx) throws SQLException, ClassNotFoundException {
        String selectSql = "SELECT booking_id FROM bookings WHERE event_id = ?";
        String deleteSql = "DELETE FROM bookings WHERE event_id = ?";
        List<Integer> deletedBookingIds = new ArrayList<>();
        try (Connection connection = DB_Connection.getConnection()) {
            try (PreparedStatement selectStmt = connection.prepareStatement(selectSql)) {
                selectStmt.setInt(1, idx);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    while (rs.next()) {
                        deletedBookingIds.add(rs.getInt("booking_id"));
                    }
                }
            }
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, idx);
                deleteStmt.executeUpdate();
            }
        }
        return deletedBookingIds;
    }
}