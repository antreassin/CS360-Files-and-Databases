package mainClasses;

import database.DB_Connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GivemeMYMONEY {
    public List<MoneySQL> getMoney() throws SQLException, ClassNotFoundException {
        List<MoneySQL> money = new ArrayList<>();
        String sql = "SELECT " +
                "    e.event_id, " +
                "    e.name, " +
                "    e.event_date, " +
                "    SUM(b.cost) AS income " +
                "FROM " +
                "    events e " +
                "JOIN " +
                "    bookings b ON e.event_id = b.event_id " +
                "GROUP BY " +
                "    e.event_id, e.name, e.event_date " +
                "ORDER BY " +
                "income DESC";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                MoneySQL m = new MoneySQL();
                m.setEventId(resultSet.getInt("event_id"));
                m.setEventName(resultSet.getString("name"));
                m.setEventDate(resultSet.getString("event_date"));
                m.setTotalIncome(resultSet.getInt("income"));
                money.add(m);
            }
        }
        return money;
    }
}
