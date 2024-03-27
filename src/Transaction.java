import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    Account account;
    DBConnection dbConnection;

    public Transaction(Account acc) {
        account = acc;
        dbConnection = new DBConnection();
    }

    public boolean Deposit(float amount) {

        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatterLocalDateTime.format(LocalDateTime.now());

        try {
            //Add transaction to Transaction DB
            dbConnection.insert("Transactions","(`accountID`, `amount`, `Date`, `type`)", "('" + account.accountID + "', '" + amount + "', '" + dateTime + "', 'Deposit')");

            //Update Account Balance in Accounts DB
            account.updateBalance(amount);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public int Withdrawal(float amount) {
        float bal = account.getAccountBalance();

        if (bal < amount) {
            //Insufficient funds, transaction not processed
            return 1;
        }


        DateTimeFormatter formatterLocalDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = formatterLocalDateTime.format(LocalDateTime.now());


        try {
            float amt = -amount;

            //Add transaction to Transaction DB
            dbConnection.insert("Transactions","(`accountID`, `amount`, `Date`, `type`)", "('" + account.accountID + "', '" + amt + "', '" + dateTime + "', 'Withdrawal')");

            //Update Account Balance in Accounts DB
            account.updateBalance(amt);

            //RETURN SUCCESS CODE 3
            return 3;

        } catch (Exception e) {
            //ERROR RETURN ERROR CODE 2
            return 2;
        }
    }

    public int Transfer(Account accToTransferTo, float transferAmount) {
        //Check if both Withdrawal and Deposit Accounts exist
        int withdrawalCode = Withdrawal(transferAmount);
        if (withdrawalCode != 3) {
            return withdrawalCode;
        }

        Transaction depositTrans = new Transaction(accToTransferTo);
        if (depositTrans.Deposit(transferAmount)) {
            return 3;
        }

        //ERROR FOUND RETURN ERROR CODE 2
        return 2;
    }
}
