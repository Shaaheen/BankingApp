package Source; /**
 * Created by Shaaheen on 4/9/2015.
 */
import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/newschemadatabase";
    static final String USER = "shaaheen";
    static final String PASS = "gigabyte";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connected to JDBC Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        System.out.println("Trying to Connect to Database");
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        System.out.println("Connected");
        System.out.println("Creating Statement");
        Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
        Scanner user_input = new Scanner(System.in);
        System.out.println("Welcome to the Banking Application");
        System.out.println("Choose an option below");
        System.out.println("Find Update Create");

        System.out.println("Option: ");
        String inputed = user_input.nextLine();
        while (!inputed.equals("exit")){
            String sql = "Select * from accounts";
            if (inputed.equals("Find")) {
                System.out.println("Enter query");
                sql = user_input.nextLine();
                ResultSet rs = stmt.executeQuery(sql);
                System.out.println("ID   " + "Balance");
                while (rs.next()) {
                    System.out.println(rs.getString(1) + "     " + rs.getString(2));
                }
            }
            else if (inputed.equals("Update")){

            }
            else if (inputed.equals("Create")){
                ResultSet rs = stmt.executeQuery("SELECT ID from accounts");
                int currentID = 0;
                while (rs.next()) {
                    currentID = Integer.parseInt(rs.getString(1));
                }
                System.out.println("What is the balance?");
                double balance = user_input.nextDouble();
                currentID +=1;
                sql = "INSERT INTO accounts VALUES(" + currentID + "," + balance + ")";
                stmt.executeUpdate(sql);
                System.out.println("Added successfully");
            }
            else{
                System.out.println(inputed + " is not an option");
            }

            System.out.println("New Option: ");
            inputed = user_input.nextLine();
        }

    }

}
