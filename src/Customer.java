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

            String insert = "INSERT INTO Bank.Customer VALUES (" + currentID + ", '" + firstName + "', '" + lastName + "', '" + SSN + "', '" + DOB + "');";
            statement.executeUpdate(insert);

            currentID++;

        } catch (SQLIntegrityConstraintViolationException e1) {
            if (e1.getMessage().contains("SSN_UNIQUE")) {
                System.out.println("Customer already exists with provided Social Security Number, please double check before retry.");
                return;
            }
            e1.printStackTrace();
        }catch (Exception e2) {
            e2.printStackTrace();
            System.out.println("There was a problem with this function");
        }
    }

    public void getCustomerInfoByID(int customerID) {
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

    public void getCustomerInfoBySSN(String SSN) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Customer WHERE SSN = '" + SSN + "';";

            ResultSet select = statement.executeQuery(query);

            while (select.next()) {
                System.out.println("Customer ID: " + select.getString("customerID") + " | First Name: " + select.getString("firstName") + " | Last Name: " + select.getString("lastName") + " | SSN: " + select.getString("SSN") + " | DOB: " + select.getString("DOB"));
                return;
            }
            System.out.println("No results, please try again with different Social Security Number");

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }
    }
}
