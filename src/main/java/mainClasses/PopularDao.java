package mainClasses;

import database.DB_Connection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
/*SELECT
    e.event_id,
    e.name AS event_name,
    e.event_date,
    SUM(b.quantity) AS total_reservations
FROM
    events e
JOIN
    bookings b ON e.event_id = b.event_id
GROUP BY
    e.event_id, e.name, e.event_date
ORDER BY
    total_reservations DESC
LIMIT 1;
*/
public class PopularDao {
    public PopularSQL getPopular() throws SQLException, ClassNotFoundException {
            PopularSQL popular = new PopularSQL();
            String sql = "SELECT " +
                    "    e.event_id, " +
                    "    e.name AS event_name, " +
                    "    e.event_date, " +
                    "    SUM(b.quantity) AS total_reservations " +
                    "FROM " +
                    "    events e " +
                    "JOIN " +
                    "    bookings b ON e.event_id = b.event_id " +
                    "GROUP BY " +
                    "    e.event_id, e.name, e.event_date " +
                    "ORDER BY " +
                    "total_reservations DESC " +
                    "LIMIT 1";
            try (Connection connection = DB_Connection.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        popular.setEventId(resultSet.getInt("event_id"));
                        popular.setEventName(resultSet.getString("event_name"));
                        popular.setEventDate(resultSet.getString("event_date"));
                        popular.setTotalReservations(resultSet.getInt("total_reservations"));
                    }
                }
            return popular;
    }

}
