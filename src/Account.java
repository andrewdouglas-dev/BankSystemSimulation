import java.sql.*;

public class Account {
    private String url = "jdbc:mysql://localhost:3306/Bank";
    private String user = "BankUser";
    private String pass = "Password123";

    public Account() {
    }

    public void createAccount(String customerID, String accType) {

        Customer customer = new Customer();
        if (!customer.customerExists(customerID)) {
            System.out.println("Provided CustomerID does not exist in the Customer Database. To create an Account first create create new customer.");
            return;
        }

        if (!accType.equals("Chk") && !accType.equals("Sav")) {
            System.out.println("Please enter valid account type.");
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String insert = "INSERT INTO Bank.Accounts (`customerID`, `createDate`, `AccountType`,`Balance`) VALUES ('" + customerID + "', '" + java.time.LocalDate.now() + "', '" + accType + "', '" + 0.00 + "');";

            statement.executeUpdate(insert);

            System.out.println("Successfully added new account for Customer ID: " + customerID + " to the Account Database.");

        } catch (Exception e) {
            System.out.println("There was an error performing this operation");
            e.printStackTrace();
        }
    }

    public void getAllAccountByCustomer(String customerID) {
        Customer customer = new Customer();
        if (!customer.customerExists(customerID)) {
            System.out.println("Provided CustomerID is invalid.");
            return;
        }

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
        Customer customer = new Customer();
        if (!customer.customerExists(customerID)) {
            System.out.println("Provided CustomerID is invalid.");
            return;
        }

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
        Customer customer = new Customer();
        if (!customer.customerExists(customerID)) {
            System.out.println("Provided CustomerID is invalid.");
            return;
        }

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
        if (!accountExists(customerID, accountID)) {
            return;
        }

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

    public float getAccountBalance(String customerID, String accountID) {
        if (!accountExists(customerID,accountID)) {
            return (float) -.555;
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Accounts WHERE customerID = '" + customerID + "' AND accountID = '" + accountID + "';";

            ResultSet select = statement.executeQuery(query);

            while (select.next()) {
                return select.getFloat("Balance");
            }

        } catch (Exception e) {
            System.out.println("There was an error performing this operation");
        }

        return (float) -.555;
    }

    public boolean accountExists(String customerID, String accountID) {
        Customer customer = new Customer();
        if (!customer.customerExists(customerID)) {
            System.out.println("Provided CustomerID does not exist in the Customer Database, and therefore has no associated accounts.");
            return false;
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank.Accounts WHERE customerID = '" + customerID + "' AND accountID = '" + accountID + "';";

            ResultSet select = statement.executeQuery(query);

            while (select.next()) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("There was an error performing this operation");
        }

        System.out.println("No Account found with provided Account ID.");
        return false;
    }

    public void deleteAccount(String customerID, String accountID) {
        if (!accountExists(customerID, accountID)) {
            return;
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String delete = "Delete FROM Bank.Accounts WHERE accountID = '" + accountID + "';";

            statement.executeUpdate(delete);

            System.out.println("Successfully deleted " + accountID + " from the Accounts Database.");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem with this function");
        }
    }
}
