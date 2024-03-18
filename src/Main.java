import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account acc = new Account();

        acc.createAccount("3", "Mem");
        acc.getAllAccountByCustomer("3");
    }
}
