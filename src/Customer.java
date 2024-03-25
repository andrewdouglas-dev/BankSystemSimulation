import java.sql.*;

public class Customer {
    DBConnection dbConnection;
    String fname;
    String lname;
    String SSN;
    String DOB;

    public Customer(String searchValue) {
        dbConnection = new DBConnection();
        ResultSet rs = dbConnection.select("Customer", "SSN = '" + searchValue + "'");

        try {
            if (rs == null) {
                return;
            }

            if (rs.next()) {
                fname = rs.getString("firstName");
                lname = rs.getString("lastName");
                SSN = rs.getString("SSN");
                DOB = rs.getString("DOB");
                return;
            }

            throw new Exception("No Customer found with provided Social Security Number. Please create customer.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer(String socialSecurityNum, String firstName, String lastName, String dateOfBirth) {
        dbConnection = new DBConnection();
        try {
            dbConnection.insert("Customer", "(`SSN`, `firstName`, `lastName`, `DOB`)", "('" + socialSecurityNum + "', '" + firstName + "', '" + lastName + "', '" + dateOfBirth + "')");
            SSN = socialSecurityNum;
            fname = firstName;
            lname = lastName;
            DOB = dateOfBirth;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Duplicate SSN found. Please try again with new SSN");
        }
    }

    public void deleteCustomer() {
        dbConnection.delete("Customer", "SSN = '" + SSN + "'");
    }

    public void updateCustomerFirstName(String firstName) {
        fname = firstName;
        dbConnection.update("Customer", "firstName = '" + fname + "'", "SSN = '" + SSN + "'");
    }

    public void updateCustomerLastName(String lastName) {
        lname = lastName;
        dbConnection.update("Customer", "lastName = '" + lname + "'", "SSN = '" + SSN + "'");
    }

    public boolean customerExists() {
        return dbConnection.exists("Customer", "SSN = '" + SSN + "'");
    }

    public void customerInformation() {
        System.out.println(SSN + " | " + fname + " | " + lname + " | " + DOB);
    }

    //ACCOUNTS PER CUSTOMER


    public void getAllAccountByCustomer(String customerID) {
        ResultSet rs = dbConnection.select("Accounts", "customerSSN = '" + SSN + "'");
        try {
            System.out.println("Account Information:");

            while (rs.next()) {
                System.out.println("Account ID: " + rs.getString("AccountID") + " | Customer SSN: " + SSN + " | Account Create Date: " + rs.getString("createDate") + " | Account Type: " + rs.getString("AccountType") + " | Account Balance: " + rs.getString("Balance"));
            }
        } catch (Exception e) {
            System.out.println("There was an error with that operation");
        }
    }

    public void getAllCheckingAccountsByCustomer(String customerID) {
        ResultSet rs = dbConnection.select("Accounts", "customerSSN = '" + SSN + "' AND AccountType = 'Chk'");
        try {
            System.out.println("Account Information:");

            while (rs.next()) {
                System.out.println("Account ID: " + rs.getString("AccountID") + " | Customer SSN: " + SSN + " | Account Create Date: " + rs.getString("createDate") + " | Account Type: " + rs.getString("AccountType") + " | Account Balance: " + rs.getString("Balance"));
            }
        } catch (Exception e) {
            System.out.println("There was an error with that operation");
        }
    }

    public void getAllSavingsAccountsByCustomer() {
        ResultSet rs = dbConnection.select("Accounts", "customerSSN = '" + SSN + "' AND AccountType = 'Sav'");
        try {
            System.out.println("Account Information:");

            while (rs.next()) {
                System.out.println("Account ID: " + rs.getString("AccountID") + " | Customer SSN: " + SSN + " | Account Create Date: " + rs.getString("createDate") + " | Account Type: " + rs.getString("AccountType") + " | Account Balance: " + rs.getString("Balance"));
            }
        } catch (Exception e) {
            System.out.println("There was an error with that operation");
        }
    }
}
