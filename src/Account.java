import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account {
    DBConnection dbConnection;
    Customer customer;
    String accountID;
    String accountType;
    String createDateTime;
    Float balance;


    public Account(Customer cus, String accID) {
        dbConnection = new DBConnection();
        customer = cus;
        accountID = accID;

        ResultSet rs = dbConnection.select("Accounts", "AccountID = '" + accountID + "' and customerSSN = '" + customer.SSN + "'");

        try {
            if (rs == null) {
                return;
            }

            if (rs.next()) {
                createDateTime = rs.getString("createDateTime");
                balance = rs.getFloat("Balance");
                accountType = rs.getString("AccountType");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Account(Customer cus, String accType, float bal){
        dbConnection = new DBConnection();

        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatterLocalDateTime.format(LocalDateTime.now());

        customer = cus;
        createDateTime = dateTime;
        balance = bal;
        accountType = accType;

        try {
            dbConnection.insert("Accounts", "(`customerSSN`, `createDateTime`, `AccountType`, `Balance`)", "('" + customer.SSN + "', '" + dateTime + "', '" + accType + "', '" + 0.00 + "')");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet rs = dbConnection.select("Accounts", "customerSSN = '" + customer.SSN + "' and createDateTime = '" + dateTime + "'");

        try {
            if (rs.next()) {
                accountID = rs.getString("AccountID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAccountDetails() {
        System.out.println(accountID + " | " + customer.SSN + " | " + createDateTime + " | " + accountType + " | " + balance);
    }

    public float getAccountBalance() {
        return balance;
    }

    public void updateBalance(float updateVal) {
        balance = balance + updateVal;
        dbConnection.update("Accounts", "Balance = " + balance, "accountID = '" + accountID + "' and customerSSN = '" + customer.SSN + "'");
    }

    public boolean accountExists() {
        return dbConnection.exists("Accounts", "accountID = '" + accountID + "' and customerSSN = '" + customer.SSN + "'");
    }


    public void deleteAccount() {
        dbConnection.delete("Accounts", "accountID = '" + accountID + "' and customerSSN = '" + customer.SSN + "'");
    }
}
