import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Account acc = new Account();
        Customer customer = new Customer();
        Transaction trans = new Transaction();

        acc.deleteAccount("3", "1");
        acc.deleteAccount("1","1");
        System.out.println();


        //customer.addNewCustomer("Michael", "Douglas", "111-12-1111", "1992-11-10");
        acc.deleteAccount("2","864653");

//        acc.getAllAccountByCustomer("1");
//        System.out.println();
//        trans.Deposit("1", "864651", (float)150.50);
//        System.out.println();
//        acc.getAllAccountByCustomer("1");
//        trans.Transfer("1", "863651", "864652",(float)300);
//        System.out.println();
//        acc.getAllAccountByCustomer("1");


//        Transaction trans = new Transaction();
//        trans.Withdrawal("1", "864651", (float)100.15);
//
//        acc.getAllAccountByCustomer("1");
    }
}
