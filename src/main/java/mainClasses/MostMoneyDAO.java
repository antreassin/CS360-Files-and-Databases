package mainClasses;
import database.DB_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SELECT
 *     event_id,
 *     name AS event_name,
 *     event_date,
 *     income
 * FROM
 *     events
 * WHERE
 *     event_date BETWEEN ? AND ?
 * ORDER BY
 *     income DESC
 * LIMIT 1;
 */
public class MostMoneyDAO {
    public MostMoneyYearSQL getTopIncomeEvent(String startDate, String endDate) throws SQLException, ClassNotFoundException {
        MostMoneyYearSQL mm = null;
        String sql = "SELECT " +
                "    event_id, " +
                "    name AS event_name, " +
                "    event_date, " +
                "    income " +
                "FROM " +
                "    events " +
                "WHERE " +
                "    event_date BETWEEN ? AND ? " +
                "ORDER BY " +
                "    income DESC " +
                "LIMIT 1";

        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    mm = new MostMoneyYearSQL();
                    mm.setEventId(resultSet.getInt("event_id"));
                    mm.setEventName(resultSet.getString("event_name"));
                    mm.setEventDate(resultSet.getString("event_date"));
                    mm.setIncome(resultSet.getDouble("income"));
                }
            }
        }

        return mm;
    }
}
