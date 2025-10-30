package mainClasses;

import database.DB_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {
    int user_id;
    String first_name, last_name, email, expiry_date;
    int card_number, cvv;
    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
    public int getCardNumber() {
        return card_number;
    }
    public void setCardNumber(int card_number) {
        this.card_number = card_number;
    }
    public String getExpiryDate() {
        return expiry_date;
    }
    public void setExpiryDate(String expiry_date) {
        this.expiry_date = expiry_date;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLastName() {
        return last_name;
    }
    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
    public String getFirstName() {
        return first_name;
    }
    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public User(int card_number, String first_name, String last_name, String email, String expiry_date, int cvv) {
        this.card_number = card_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.expiry_date = expiry_date;
        this.cvv = cvv;
    }
    public void insertUser() {
        String sql = "INSERT INTO `users`(`card_number`, `first_name`, `last_name`, `email`, `expiry_date`, `cvv`) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DB_Connection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Set the values for the prepared statement
            preparedStatement.setInt(1, this.getCardNumber());
            preparedStatement.setString(2, this.getFirstName());
            preparedStatement.setString(3, this.getLastName());
            preparedStatement.setString(4, this.getEmail());
            preparedStatement.setString(5, this.getExpiryDate());
            preparedStatement.setInt(6, this.getCvv());
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
