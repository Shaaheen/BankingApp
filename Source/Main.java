package Source; /**
 * Created by Shaaheen on 4/9/2015.
 */
import java.sql.*;
import java.util.Scanner;

public class Main {
    //Database details
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/newschemadatabase";
    static final String USER = "shaaheen";
    static final String PASS = "gigabyte";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //Connecting to JDBC Driver
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS); //Establishing connection with server
        System.out.println("Connected");
        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE); //creates statements that are updatable
        Scanner user_input = new Scanner(System.in);
        System.out.println("Welcome to the Banking Application"); //UI
        System.out.println("Select type of user");
        System.out.println("1)User 2)Management"); //gets type of user so can determine what user can do
        int typeOfUser = user_input.nextInt();
        if (typeOfUser == 1){
            userUI(stmt);
        }
        /*
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
        }*/

    }
    private static void userUI(Statement stmt){
        Scanner user_input = new Scanner(System.in);
        System.out.println("1)Login 2)Create New Account");
        int userChoice = user_input.nextInt();
        if (userChoice == 1){
            System.out.println("Username:");
            user_input.nextLine();
            String Username = user_input.nextLine();
            System.out.println("Password:");
            String Password = user_input.nextLine();
            ResultSet rs = null;
            try{
                Username = "\"" + Username + "\"";
                rs = stmt.executeQuery("SELECT Password from logindetails WHERE Username=" + Username);
                String passFound = "not found";
                while (rs.next()){
                    passFound = rs.getString(1);
                }
                if (Password.equals(passFound)){
                    System.out.println("Login successful");
                    
                }
                else{
                    System.out.println("Password or Username not correct");
                }
            } catch (SQLException e){
                System.out.println("Username not found");
            }


        }
    }

}
