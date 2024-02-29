import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Customer newCustomer = new Customer();

        Scanner scan = new Scanner(System.in);

        int id = scan.nextInt();

        newCustomer.getCustomerInfo(id);
    }
}
