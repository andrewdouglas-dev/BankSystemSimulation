import java.sql.*;

public class Customer {
    private String url = "jdbc:mysql://localhost:3306/Bank";
    private String user = "BankUser";
    private String pass = "Password123";
    private int currentID = 1;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String SSN, String DOB) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String insert = "INSERT INTO Customer VALUES (" + currentID + ", '" + firstName + "', '" + lastName + "', '" + SSN + "', '" + DOB + "');";
            statement.executeUpdate(insert);

            currentID++;

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
        }
    }

    public void getCustomerInfo(int customerID) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Customer WHERE customerID = " + customerID + ";";

            ResultSet select = statement.executeQuery(query);

            while (select.next()) {
                System.out.println("Customer ID: " + select.getString("customerID") + " | First Name: " + select.getString("firstName") + " | Last Name: " + select.getString("lastName") + " | SSN: " + select.getString("SSN") + " | DOB: " + select.getString("DOB"));
                return;
            }
            System.out.println("No results, please try again with different customerID");

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }
    }
}
