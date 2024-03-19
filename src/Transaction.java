import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Transaction {
    private String url = "jdbc:mysql://localhost:3306/Bank";
    private String user = "BankUser";
    private String pass = "Password123";

    public Transaction() {
    }

    public void Deposit(String CustomerID, String AccountID, float amount) {
        Account acc = new Account();

        if (!acc.accountExists(CustomerID, AccountID)) {
            System.out.println("No Account found with provided Customer ID and Account ID");
            return;
        }

        try {
            float bal = acc.getAccountBalance(CustomerID, AccountID);

            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String update = "Update Bank.Accounts SET Balance = '" + (bal+amount) + "' WHERE accountID = '" + AccountID + "';";

            statement.executeUpdate(update);

            System.out.println("Successfully deposited to Account: " + AccountID + ". New balance is: " + acc.getAccountBalance(CustomerID, AccountID));

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }
    }

    public int Withdrawal(String CustomerID, String AccountID, float amount) {
        Account acc = new Account();

        if (!acc.accountExists(CustomerID, AccountID)) {
            System.out.println("No Account found with provided Customer ID and Account ID");
            return 1;
        }

        float bal = acc.getAccountBalance(CustomerID, AccountID);

        if (bal < amount) {
            System.out.println("Insufficient funds, transaction not processed.");
            return 2;
        }

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String update = "Update Bank.Accounts SET Balance = '" + (bal-amount) + "' WHERE accountID = '" + AccountID + "';";

            statement.executeUpdate(update);

            System.out.println("Successfully withdrew " + amount + " from Account: " + AccountID + " balance. New balance is: " + acc.getAccountBalance(CustomerID, AccountID));

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
}
