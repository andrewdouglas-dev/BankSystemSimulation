import java.sql.*;
import java.util.Random;

public class Customer {
    private String url = "jdbc:mysql://localhost:3306/Bank";
    private String user = "BankUser";
    private String pass = "Password123";

    public Customer() {
    }

    public void addNewCustomer(String firstName, String lastName, String SSN, String DOB) {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        String CustomerID = String.format("%06d", number);

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String insert = "INSERT INTO Bank.Customer VALUES (" + CustomerID + ", '" + firstName + "', '" + lastName + "', '" + SSN + "', '" + DOB + "');";

            statement.executeUpdate(insert);

            System.out.println("Successfully added " + CustomerID + " to the Customer Database.");

        } catch (SQLIntegrityConstraintViolationException e1) {
            if (e1.getMessage().contains("SSN_UNIQUE")) {
                System.out.println("Customer already exists with provided Social Security Number, please double check before retry.");
                return;
            } else if (e1.getMessage().contains("PRIMARY")) {
                addNewCustomer(firstName, lastName, SSN, DOB);
                return;
            }
            e1.printStackTrace();
        }catch (Exception e2) {
            e2.printStackTrace();
            System.out.println("There was a problem with this function");
        }
    }

    public void deleteCustomer(String CustomerID) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String delete = "Delete FROM Bank.Customer WHERE customerID = '" + CustomerID + "';";

            statement.executeUpdate(delete);

            System.out.println("Successfully deleted " + CustomerID + " from the Customer Database.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem with this function");
        }
    }

    public void updateCustomerFirstName(int customerID, String firstName) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String update = "Update Bank.Customer SET firstName = '" + firstName + "' WHERE customerID = '" + customerID + "';";

            statement.executeUpdate(update);

            System.out.println("Successfully updated " + customerID + " first name in the Customer Database.");

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }
    }

    public void updateCustomerLastName(int customerID, String lastName) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String update = "Update Bank.Customer SET lastName = '" + lastName + "' WHERE customerID = '" + customerID + "';";

            statement.executeUpdate(update);

            System.out.println("Successfully updated " + customerID + " last name in the Customer Database.");

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }
    }

    public void getCustomerInfoByID(int customerID) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Customer WHERE customerID = '" + customerID + "';";

            ResultSet select = statement.executeQuery(query);

            while (select.next()) {
                System.out.println("Customer Information:");
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
                System.out.println("Customer Information:");
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
