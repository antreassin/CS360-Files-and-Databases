package mainClasses;
import database.DB_Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SELECT
 *     e.event_id,
 *     e.name AS event_name,
 *     SUM(CASE WHEN t.type = 'VIP' THEN 30 *1 ELSE 0 END) AS vip_income,
 *     SUM(CASE WHEN t.type = 'regular' THEN 10 * 1 ELSE 0 END) AS regular_income,
 *     SUM(CASE WHEN t.type = 'seat' THEN 20 *1 ELSE 0 END) AS seat_income,
 *     SUM(
 *         CASE
 *             WHEN t.type = 'VIP' THEN 30 * b.quantity
 *             WHEN t.type = 'regular' THEN 10 * b.quantity
 *             WHEN t.type = 'seat' THEN 20 * b.quantity
 *             ELSE 0
 *         END
 *     ) AS total_income
 * FROM
 *     bookings b
 * JOIN
 *     tickets t ON b.booking_id = t.booking_id
 * JOIN
 *     events e ON b.event_id = e.event_id
 * GROUP BY
 *     e.event_id, e.name
 * ORDER BY
 *     total_income DESC;
 */
public class IncomePerEventDAO {
    public List<IncomePerEventSQL> getIncomePerEvent() throws SQLException, ClassNotFoundException {
        List<IncomePerEventSQL> incomePerEventList = new ArrayList<>();
        String sql = "SELECT " +
                "    e.event_id, " +
                "    e.name AS event_name, " +
                "    SUM(CASE WHEN t.type = 'VIP' THEN 30 *1 ELSE 0 END) AS vip_income, " +
                "    SUM(CASE WHEN t.type = 'regular' THEN 10 * 1 ELSE 0 END) AS regular_income, " +
                "    SUM(CASE WHEN t.type = 'seat' THEN 20 *1 ELSE 0 END) AS seat_income, " +
                "    SUM( " +
                "        CASE  " +
                "            WHEN t.type = 'VIP' THEN 30 * 1 " +
                "            WHEN t.type = 'regular' THEN 10 * 1 " +
                "            WHEN t.type = 'seat' THEN 20 * 1" +
                "            ELSE 0 " +
                "        END " +
                "    ) AS total_income " +
                "FROM " +
                "    bookings b " +
                "JOIN " +
                "    tickets t ON b.booking_id = t.booking_id " +
                "JOIN " +
                "    events e ON b.event_id = e.event_id " +
                "GROUP BY " +
                "    e.event_id, e.name " +
                "ORDER BY " +
                "    total_income DESC";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                IncomePerEventSQL income = new IncomePerEventSQL();
                income.setEvent_id(resultSet.getInt("event_id"));
                income.setName_event(resultSet.getString("event_name"));
                income.setVip_income(resultSet.getInt("vip_income"));
                income.setRegular_income(resultSet.getInt("regular_income"));
                income.setSeat_income(resultSet.getInt("seat_income"));
                income.setTotal_income(resultSet.getInt("total_income"));
                incomePerEventList.add(income);
            }
        }
        return incomePerEventList;
    }
}
