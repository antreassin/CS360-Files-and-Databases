package mainClasses;
import database.DB_Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SELECT
 *     YEAR(e.event_date) AS event_year,
 *     COUNT(b.booking_id) AS total_bookings
 * FROM
 *     bookings b
 * JOIN
 *     events e
 * ON
 *     b.event_id = e.event_id
 * GROUP BY
 *     YEAR(e.event_date)
 * ORDER BY
 *     event_year;
 */
public class ViewBookingDAO {
    public List<ViewBookingSQL> getBookingYears() throws SQLException, ClassNotFoundException {
        List<ViewBookingSQL> bookings = new ArrayList<>();
        String sql = "SELECT " +
                "    YEAR(e.event_date) AS event_year, " +
                "    COUNT(b.booking_id) AS total_bookings " +
                "FROM " +
                "    bookings b " +
                "JOIN " +
                "    events e " +
                "ON " +
                "    b.event_id = e.event_id " +
                "GROUP BY " +
                "    YEAR(e.event_date) " +
                "ORDER BY " +
                "    event_year";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ViewBookingSQL b = new ViewBookingSQL();
                b.setTotal_bookings(resultSet.getInt("total_bookings"));
                b.setDate(resultSet.getString("event_year"));
                bookings.add(b);
            }
        }
        return bookings;
    }
}
