package database.init;

import database.tables.BookingTable;
import database.tables.EventTable;
import database.tables.TicketsTable;
import database.tables.UsersTable;
import mainClasses.User;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static database.DB_Connection.getInitialConnection;

public class InitDatabase {
    public void initDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE DATABASE HY360");
        stmt.close();
        conn.close();
    }
    public void dropDatabase() throws SQLException, ClassNotFoundException {
        Connection conn = getInitialConnection();
        Statement stmt = conn.createStatement();
        String sql = "DROP DATABASE HY360";
        stmt.executeUpdate(sql);
        System.out.println("Database dropped successfully...");
    }
    public void initTables() throws SQLException, ClassNotFoundException {
        BookingTable bt = new BookingTable();
        bt.createBookingTable();
        EventTable et = new EventTable();
        et.createEventTable();
        TicketsTable tt = new TicketsTable();
        tt.createTicketTable();
        UsersTable ut = new UsersTable();
        ut.createUsersTable();
        System.out.println("Database init successfully...");
    }
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        InitDatabase init = new InitDatabase();
        //init.initDatabase();
        //init.initTables();
        init.dropDatabase();
        //User user = new User(123, "Mike", "Kouk", "meow", "meow", 123);
        //user.insertUser();
        //Date bookingDate = Date.valueOf(LocalDate.now());
        //System.out.println(bookingDate);

        //bt.insertBooking(1,2,3,30);
        //bt.deleteBooking(9);
        //TicketsTable tt = new TicketsTable();
        //int[] arr = tt.getNumberOfFromTickets(8);
        //System.out.println(arr[0]);
        //System.out.println(arr[1]);
        //System.out.println(arr[2]);
        //init.initDatabase();
        //init.initTables();
    }

}
