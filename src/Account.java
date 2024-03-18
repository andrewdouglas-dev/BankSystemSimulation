import java.sql.*;
import java.util.Random;

public class Account {
    private String url = "jdbc:mysql://localhost:3306/Bank";
    private String user = "BankUser";
    private String pass = "Password123";

    public Account() {
    }

    public void getAllAccountByCustomer(String customerID) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Accounts WHERE customerID = '" + customerID + "';";

            ResultSet select = statement.executeQuery(query);

            System.out.println("Account Information:");

            while (select.next()) {
                System.out.println("Account ID: " + select.getString("AccountID") + " | Customer ID: " + select.getString("customerID") + " | Account Create Date: " + select.getString("createDate") + " | Account Type: " + select.getString("AccountType") + " | Account Balance: " + select.getString("Balance"));
            }
        } catch (Exception e) {
            System.out.println("There was an error with that operation");
        }
    }

    public void getAllCheckingAccountsByCustomer(String customerID) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Accounts WHERE customerID = '" + customerID + "' AND AccountType = 'Chk';";

            ResultSet select = statement.executeQuery(query);

            System.out.println("Account Information:");

            while (select.next()) {
                System.out.println("Account ID: " + select.getString("AccountID") + " | Customer ID: " + select.getString("customerID") + " | Account Create Date: " + select.getString("createDate") + " | Account Type: " + select.getString("AccountType") + " | Account Balance: " + select.getString("Balance"));
            }
        } catch (Exception e) {
            System.out.println("There was an error with that operation");
        }
    }

    public void getAllSavingsAccountsByCustomer(String customerID) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Accounts WHERE customerID = '" + customerID + "' AND AccountType = 'Sav';";

            ResultSet select = statement.executeQuery(query);

            System.out.println("Account Information:");

            while (select.next()) {
                System.out.println("Account ID: " + select.getString("AccountID") + " | Customer ID: " + select.getString("customerID") + " | Account Create Date: " + select.getString("createDate") + " | Account Type: " + select.getString("AccountType") + " | Account Balance: " + select.getString("Balance"));
            }
        } catch (Exception e) {
            System.out.println("There was an error with that operation");
        }
    }

    public void getAccountDetails(String customerID, String accountID) {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Accounts WHERE customerID = '" + customerID + "' AND accountID = '" + accountID + "';";

            ResultSet select = statement.executeQuery(query);

            while (select.next()) {
                System.out.println("Account Information:");
                System.out.println("Account ID: " + select.getString("AccountID") + " | Customer ID: " + select.getString("customerID") + " | Account Create Date: " + select.getString("createDate") + " | Account Type: " + select.getString("AccountType") + " | Account Balance: " + select.getString("Balance"));
                return;
            }

        } catch (Exception e) {
            System.out.println("There was an error performing this operation");
        }
    }

    public void createAccount(String customerID, String accType) {

        if (!accType.equals("Chk") && !accType.equals("Sav")) {
            System.out.println("Please enter valid account type.");
            return;
        }

        try {

            Random rand = new Random();
            int number = rand.nextInt(999999);
            String accountID = String.format("%06d", number);

            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String insert = "INSERT INTO Bank.Accounts VALUES ('" + accountID + "', '" + customerID + "', '" + java.time.LocalDate.now() + "', '" + accType + "', '" + 0.00 + "');";

            statement.executeUpdate(insert);

            System.out.println("Successfully added " + accountID + " to the Customer Database.");

        } catch (SQLIntegrityConstraintViolationException e1) {
            if (e1.getMessage().contains("PRIMARY")) {
                createAccount(customerID, accType);
                return;
            }
        } catch (Exception e) {
            System.out.println("There was an error performing this operation");
            e.printStackTrace();
        }
    }
}
