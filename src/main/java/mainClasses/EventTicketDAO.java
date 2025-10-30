package mainClasses;
import database.DB_Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventTicketDAO {

    public List<EventTicketSQL> getEventTicketStatus() throws SQLException, ClassNotFoundException {
        List<EventTicketSQL> ticketStatusList = new ArrayList<>();
        String sql = "SELECT " +
                "    e.event_id, " +
                "    e.capacity AS available_tickets, " +
                "    COALESCE(SUM(CASE WHEN t.type = 'VIP' THEN 1 ELSE 0 END), 0) AS booked_vip, " +
                "    COALESCE(SUM(CASE WHEN t.type = 'Regular' THEN 1 ELSE 0 END), 0) AS booked_regular, " +
                "    COALESCE(SUM(CASE WHEN t.type = 'Seat' THEN 1 ELSE 0 END), 0) AS booked_seat " +
                "FROM " +
                "    events e " +
                "LEFT JOIN " +
                "    bookings b ON e.event_id = b.event_id " +
                "LEFT JOIN " +
                "    tickets t ON b.booking_id = t.booking_id " +
                "GROUP BY " +
                "    e.event_id, e.capacity";
        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                EventTicketSQL status = new EventTicketSQL();
                status.setEventId(resultSet.getInt("event_id"));
                status.setAvailableTickets(resultSet.getInt("available_tickets"));
                status.setBookedVip(resultSet.getInt("booked_vip"));
                status.setBookedRegular(resultSet.getInt("booked_regular"));
                status.setBookedSeat(resultSet.getInt("booked_seat"));
                ticketStatusList.add(status);
            }
        }
        return ticketStatusList;
    }
}

