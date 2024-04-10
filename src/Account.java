import javax.swing.*;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Account {
    DBConnection dbConnection;
    Customer customer;
    String accountID;
    String accountType;
    String createDateTime;
    Float balance;
    private final DecimalFormat df = new DecimalFormat("0.00");


    public Account() {
        dbConnection = new DBConnection();
    }

    public boolean setAccount(Customer cus, String accID) {
        customer = cus;
        accountID = accID;

        ResultSet rs = dbConnection.select("Accounts", "AccountID = '" + accountID + "' and customerSSN = '" + customer.SSN + "'");

        try {
            if (rs == null) {
                return false;
            }

            if (rs.next()) {
                createDateTime = rs.getString("createDateTime");
                balance = rs.getFloat("Balance");
                accountType = rs.getString("AccountType");
                return true;
            }

            return false;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean addAccount(Customer cus, String accType, float bal) {

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

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String getAccountDetails() {
        return ("Account ID: " + accountID + " | SSN: " + customer.SSN + " | Account Type: " + accountType + " | Balance: $" + df.format(balance));
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

    public DefaultListModel<String> transactionPerAccount() {
        DefaultListModel<String> temp = new DefaultListModel<>();

        ResultSet rs = dbConnection.select("Transactions", "accountID = '" + accountID + "' ORDER BY transactionsID DESC LIMIT 10");

        try {
            while (rs.next()) {
                String type = rs.getString("type");
                if (type.equals("Deposit")) {
                    type = "Deposit     ";
                }

                temp.addElement(rs.getInt("transactionsID") + " | " + rs.getDate("Date") + " | " + type + " | $" + df.format(rs.getFloat("amount")) +"\n");
            }

            if (temp.isEmpty()) {
                temp.addElement("No transactions history.");
            }

        } catch (Exception e) {
        }

        return temp;
    }
}
