import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Discussion {
     public static void main(String[] args) {
          String query = "SELECT * FROM users WHERE username = ? AND password = ?";
          String url = "jdbc:mysql://localhost:3306/mydatabase";
          Scanner myObj = new Scanner(System.in);  // Create a Scanner object
          System.out.println("Enter username");
          String userName = myObj.nextLine();
          System.out.println("Enter password");
          String password = myObj.nextLine();

        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "username");
            preparedStatement.setString(2, "securePassword");
            // Execute the query and process the results
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}