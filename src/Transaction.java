import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String url = "jdbc:mysql://localhost:3306/Bank";
    private String user = "BankUser";
    private String pass = "Password123";

    public Transaction() {
    }

    public void Deposit(String CustomerID, String accountID, float amount) {
        Account acc = new Account();

        if (!acc.accountExists(CustomerID, accountID)) {
            System.out.println("No Account found with provided Customer ID and Account ID");
            return;
        }

        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatterLocalDateTime.format(LocalDateTime.now());

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();


            //Add transaction to Transaction DB
            String insert = "INSERT INTO Bank.Transactions (`accountID`, `amount`, `Date`, `type`) VALUES ('" + accountID + "', '" + amount + "', '" + dateTime + "', 'Deposit');";

            statement.executeUpdate(insert);


            //Update Account Balance in Accounts DB
            float bal = acc.getAccountBalance(CustomerID, accountID);

            String update = "Update Bank.Accounts SET Balance = '" + (bal+amount) + "' WHERE accountID = '" + accountID + "';";

            statement.executeUpdate(update);

            System.out.println("Successfully deposited to Account: " + accountID + ". New balance is: " + acc.getAccountBalance(CustomerID, accountID));

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }
    }

    public int Withdrawal(String CustomerID, String accountID, float amount) {
        Account acc = new Account();

        if (!acc.accountExists(CustomerID, accountID)) {
            System.out.println("No Account found with provided Customer ID and Account ID");
            return 1;
        }

        float bal = acc.getAccountBalance(CustomerID, accountID);

        if (bal < amount) {
            System.out.println("Insufficient funds, transaction not processed.");
            return 2;
        }


        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatterLocalDateTime.format(LocalDateTime.now());


        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();

            float transactionAmount = (float)-amount;

            //Add transaction to Transaction DB
            String insert = "INSERT INTO Bank.Transactions (`accountID`, `amount`, `Date`, `type`) VALUES ('" + accountID + "', '" + transactionAmount + "', '" + dateTime + "', 'Withdrawal');";

            statement.executeUpdate(insert);


            //Update Account Balance in Accounts DB
            String update = "Update Bank.Accounts SET Balance = '" + (bal-amount) + "' WHERE accountID = '" + accountID + "';";

            statement.executeUpdate(update);

            System.out.println("Successfully withdrew " + amount + " from Account: " + accountID + " balance. New balance is: " + acc.getAccountBalance(CustomerID, accountID));

            return 3;

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }

        return 4;
    }

    public void Transfer(String CustomerID, String withdrawalAccountID, String depositAccountID, float transferAmount) {
        //Check if both Withdrawal and Deposit Accounts exist
        Account acc = new Account();
        if (!acc.accountExists(CustomerID,withdrawalAccountID)) {
            System.out.println("Withdrawal Account not found, Transfer FAILED.");
            return;
        }
        if (!acc.accountExists(CustomerID, depositAccountID)) {
            System.out.println("Deposit Account not found, Transfer FAILED.");
            return;
        }

        //Process Transfer because Accounts exists and verified
        int withdrawalOutcome = Withdrawal(CustomerID, withdrawalAccountID, transferAmount);

        if (withdrawalOutcome == 1 || withdrawalOutcome == 2 || withdrawalOutcome == 4) {
            return;
        } else {
            Deposit(CustomerID, depositAccountID, transferAmount);
        }
    }

    public void Transfer(String fromCustomerID, String toCustomerID, String withdrawalAccountID, String depositAccountID, float transferAmount) {
        //Check if both Withdrawal and Deposit Accounts exist
        Account acc = new Account();
        if (!acc.accountExists(fromCustomerID,withdrawalAccountID)) {
            System.out.println("Withdrawal Account not found, Transfer FAILED.");
            return;
        }
        if (!acc.accountExists(toCustomerID, depositAccountID)) {
            System.out.println("Deposit Account not found, Transfer FAILED.");
            return;
        }

        //Process Transfer because Accounts exists and verified
        int withdrawalOutcome = Withdrawal(fromCustomerID, withdrawalAccountID, transferAmount);

        if (withdrawalOutcome == 1 || withdrawalOutcome == 2 || withdrawalOutcome == 4) {
            return;
        } else {
            Deposit(toCustomerID, depositAccountID, transferAmount);
        }
    }
}
