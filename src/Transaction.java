import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    Account account;
    DBConnection dbConnection;

    public Transaction(Account acc) {
        account = acc;
        dbConnection = new DBConnection();
    }

    public void Deposit(float amount) {

        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatterLocalDateTime.format(LocalDateTime.now());

        try {
            //Add transaction to Transaction DB
            dbConnection.insert("Transactions","(`accountID`, `amount`, `Date`, `type`)", "('" + account.accountID + "', '" + amount + "', '" + dateTime + "', 'Deposit')");

            //Update Account Balance in Accounts DB
            account.updateBalance(amount);

            System.out.println("Successfully deposited to Account: " + account.accountID + ". New balance is: " + account.balance);

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }
    }

    public int Withdrawal(float amount) {
        float bal = account.getAccountBalance();

        if (bal < amount) {
            System.out.println("Insufficient funds, transaction not processed.");
            return 2;
        }


        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatterLocalDateTime.format(LocalDateTime.now());


        try {
            float amt = -amount;

            //Add transaction to Transaction DB
            dbConnection.insert("Transactions","(`accountID`, `amount`, `Date`, `type`)", "('" + account.accountID + "', '" + amt + "', '" + dateTime + "', 'Withdrawal')");

            //Update Account Balance in Accounts DB
            account.updateBalance(amt);

            System.out.println("Successfully withdrew " + amount + " from Account: " + account.accountID + " balance. New balance is: " + account.balance);

            return 3;

        } catch (Exception e) {
            System.out.println("There was a problem with this function");
            e.getStackTrace();
        }

        return 4;
    }

    public void Transfer(Account accToTransferTo, float transferAmount) {
        //Check if both Withdrawal and Deposit Accounts exist
        if (Withdrawal(transferAmount) != 3) {
            return;
        }

        Transaction depositTrans = new Transaction(accToTransferTo);
        depositTrans.Deposit(transferAmount);
    }
}
