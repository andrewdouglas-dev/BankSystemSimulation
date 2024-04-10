import java.sql.*;

public class Customer {
    DBConnection dbConnection;
    String fname;
    String lname;
    String SSN;
    String DOB;

    public Customer() {
        dbConnection = new DBConnection();
    }

    public String customerInfo() {
        return "SSN: " + SSN + " First Name: " + fname + " Last Name: " + lname + " DOB: " + DOB;
    }

    public boolean setCustomer(String searchValue) {
        ResultSet rs = dbConnection.select("Customer", "SSN = '" + searchValue + "'");

        try {
            if (rs == null) {
                return false;
            }

            if (rs.next()) {
                fname = rs.getString("firstName");
                lname = rs.getString("lastName");
                SSN = rs.getString("SSN");
                DOB = rs.getString("DOB");
                return true;
            }

            throw new Exception("No Customer found with provided Social Security Number. Please create customer.");
        } catch (Exception e) {
            return false;
        }
    }

    public int addCustomer(String socialSecurityNum, String firstName, String lastName, String dateOfBirth) {
        try {
            dbConnection.insert("Customer", "(`SSN`, `firstName`, `lastName`, `DOB`)", "('" + socialSecurityNum + "', '" + firstName + "', '" + lastName + "', '" + dateOfBirth + "')");
            SSN = socialSecurityNum;
            fname = firstName;
            lname = lastName;
            DOB = dateOfBirth;

            return 3;
        } catch (SQLIntegrityConstraintViolationException e) {
            //ERROR CODE 1, SSN ALREADY IN SYSTEM
            return 1;
        } catch (Exception e1) {
            //ERROR CODE 2
            return 2;
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
