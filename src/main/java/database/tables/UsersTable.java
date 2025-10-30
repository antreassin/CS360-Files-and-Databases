package database.tables;

import java.sql.Connection;
import java.sql.SQLException;
import database.DB_Connection;
import mainClasses.User;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CREATE TABLE users(
 * 	user_id INT INTEGER not NULL AUTO_INCREMENT,
 * 	card_number INT,
 * 	first_name VARCHAR(20) not NULL,
 * 	last_name VARCHAR(20) not NULL,
 * 	email VARCHAR(20),
 * 	expiry_date DATE,
 * 	cvv INT not NULL,
 * 	PRIMARY KEY (user_id)
 * );
 */


public class UsersTable {
    public void createUsersTable() throws SQLException, ClassNotFoundException {
        Connection connection = DB_Connection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE users "
                + "(user_id INT AUTO_INCREMENT, "
                + "card_number INT, "
                + "first_name VARCHAR(20), "
                + "last_name VARCHAR(20), "
                + "email VARCHAR(20), "
                + "expiry_date DATE, "
                + "cvv INT, "
                + "PRIMARY KEY (user_id))";

        statement.execute(sql);
        statement.close();
    }
}



