import java.sql.*;

public class DBConnection {
    private final String url = "jdbc:mysql://localhost:3306/Bank";
    private final String user = "BankUser";
    private final String pass = "Password123";

    public void insert(String Table, String Parameters, String Values) throws SQLIntegrityConstraintViolationException {
        try {
            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String insert = "INSERT INTO Bank." + Table + " " + Parameters + " VALUES " +  Values + ";";

            statement.executeUpdate(insert);

        } catch (SQLIntegrityConstraintViolationException e1) {
            if (e1.getMessage().contains("PRIMARY")) {
                throw new SQLIntegrityConstraintViolationException("Duplicate PRIMARY key");
            }
        }catch (Exception e2) {
            e2.printStackTrace();
            System.out.println("There was a problem with this function");
        }
    }

    public void delete(String Table, String whereParameters) {
        try {

            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String delete = "Delete FROM Bank." + Table + " WHERE " + whereParameters + ";";

            statement.executeUpdate(delete);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There was a problem executing delete function");
        }
    }

    public void update(String Table, String setValues, String whereParameters) {
        try {

            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String update = "Update Bank." + Table + " SET " + setValues + " WHERE " + whereParameters + ";";

            statement.executeUpdate(update);

        } catch (Exception e) {
            System.out.println("There was a problem executing update function");
            e.getStackTrace();
        }
    }

    public ResultSet select(String Table, String whereParameters) {
        try {

            Connection connection = DriverManager.getConnection(url, user, pass);

            Statement statement = connection.createStatement();

            String query = "Select * FROM Bank." + Table + " WHERE " + whereParameters + ";";

            return statement.executeQuery(query);

        } catch (Exception e) {
            System.out.println("There was a problem executing select function");
            e.getStackTrace();
        }

        return null;
    }

    public boolean exists(String Table, String whereParameters) {
        ResultSet rs = select(Table, whereParameters);
        try {
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }
}
