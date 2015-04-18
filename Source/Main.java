package Source; /**
 * Created by Shaaheen on 4/9/2015.
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    //Database details
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/newschemadatabase";
    static final String USER = "shaaheen";
    static final String PASS = "gigabyte";
    public static Scanner user_input = new Scanner(System.in);

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
        System.out.println("Welcome to the Banking Application"); //UI
        startUI(stmt);
    }
    private static void startUI(Statement stmt) throws SQLException {
        System.out.println("Select type of user");
        System.out.println("1)User 2)Management 3)Exit"); //gets type of user so can determine what user can do
        int typeOfUser = user_input.nextInt();
        if (typeOfUser == 1){
            userUI(stmt);
        }
        else if (typeOfUser == 2){
            TotalAccounts totAcc = createTotalAccounts(stmt);
            managementUI(stmt,totAcc);
        }
        else if (typeOfUser == 3){
            System.out.println("End of Session");
            return;
        }
    }

    private static TotalAccounts createTotalAccounts(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM accounts");
        ArrayList<Account> allAccounts = new ArrayList<Account>();
        while (rs.next()){
            int ID = Integer.parseInt(rs.getString(1));
            String name = rs.getString(2);
            double bal = Double.parseDouble(rs.getString(3));
            int bnk = Integer.parseInt(rs.getString(4));
            int pin = Integer.parseInt(rs.getString(5));
            Account currentUser = new Account(ID,name,bal,bnk,pin);
            allAccounts.add(currentUser);
        }
        TotalAccounts allTheAccounts = new TotalAccounts(allAccounts);
        return allTheAccounts;
    }

    private static void managementUI(Statement stmt,TotalAccounts allTheAccounts) throws SQLException {
        System.out.println("The managing of all accounts");
        System.out.println("1)Create New Account 2)Delete account 3)List all accounts 4)Total balance 5)Wealthiest Account 6)Back");
        int manageChoice = user_input.nextInt();
        if (manageChoice == 1){
            int currID = Utilities.getCurrID(stmt) + 1;
            System.out.println("Name of account user :");
            user_input.nextLine();
            String name = user_input.nextLine();
            String adjName = "\"" + name + "\"";
            System.out.println("Name of Bank :");
            String bankName = user_input.nextLine();
            int bankID = Utilities.getBank(bankName);
            int pin = Utilities.createPin();
            if (bankID != 0 ) {
                stmt.executeUpdate("INSERT INTO accounts VALUES(" + currID + "," + adjName + "," + 0.0 + "," + bankID + "," + pin + ")");
                System.out.println("Account added ");
                System.out.println(new Account(currID,name,0.00,bankID,pin));
                System.out.println("Your new Pin is :" + pin);
                System.out.println();
            }
            managementUI(stmt,createTotalAccounts(stmt)); //Updates the allTheAccounts var

        }
        else if (manageChoice == 2){
            System.out.println("Enter Account number to delete account");
            int accNo = user_input.nextInt();
            System.out.println("Enter name to confirm deletion");
            user_input.nextLine();
            String deleteName = user_input.nextLine();
            deleteName = "\"" + deleteName + "\"";
            try {
                stmt.executeUpdate("DELETE FROM logindetails WHERE accID=" + accNo);
                System.out.println("Login removed");
                stmt.executeUpdate("DELETE FROM accounts WHERE Name=" + deleteName + "AND ID=" + accNo);
                System.out.println("Account deleted");
            }
            catch (Exception e){
                System.out.println("Account does not exist");
            }
            managementUI(stmt,createTotalAccounts(stmt));

        }
        else if (manageChoice == 3){
            allTheAccounts.printString();
            managementUI(stmt,allTheAccounts);
        }
        else if (manageChoice == 4){
            System.out.println("Total of all Balances : " + allTheAccounts.getTotalBalt());
            managementUI(stmt, allTheAccounts);
        }
        else if (manageChoice == 5){
            System.out.println(allTheAccounts.getWealthiest());
            managementUI(stmt,allTheAccounts);
        }
        else if (manageChoice == 6){
            try {
                startUI(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private static void userUI(Statement stmt) throws SQLException {
        System.out.println("1)Login 2)Create New Account 3)Back");
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
                rs = stmt.executeQuery("SELECT Password,accID from logindetails WHERE Username=" + Username);
                String passFound = "not found";
                int ID = 0;
                rs.next();
                passFound = rs.getString(1);
                ID = Integer.parseInt(rs.getString(2));

                if (Password.equals(passFound)){
                    System.out.println("Login successful");
                    Account accountUser = createAccount(stmt,ID);
                    successfulLogin(accountUser,stmt);
                }
                else{
                    System.out.println("Password or Username not correct");
                    userUI(stmt);
                }
            } catch (SQLException e){
                System.out.println("Username not found");
                userUI(stmt);
            }
        }
        else if(userChoice == 2){
            System.out.println("The Name and Pin of the account you would like to link must provided");
            System.out.println("Name:");
            user_input.nextLine();
            String name = user_input.nextLine();
            System.out.println("Pin");
            int pin = user_input.nextInt();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("SELECT ID from accounts WHERE Pin=" + pin + " AND Name=" + "\"" + name + "\"");
            } catch (SQLException e) {
                System.out.println("Incorrect details");
                userUI(stmt);
                return;
            }
            if (rs.isBeforeFirst()){ //Checks if there is data in resultset hence checks if name and pin where correct
                rs.next();
                int linkedID = Integer.parseInt(rs.getString(1));
                System.out.println("Choose a Username and password to be linked to this account");
                System.out.println("Username");
                user_input.nextLine();
                String user = user_input.nextLine();
                System.out.println("Password");
                String pass = user_input.nextLine();
                user = "\"" + user + "\"";
                pass = "\"" + pass + "\"";
                try {
                    stmt.executeUpdate("INSERT INTO logindetails VALUES (" + user + "," + pass + "," + linkedID + ")");
                    System.out.println("Account created successfully");
                    userUI(stmt);
                } catch (SQLException e) {
                    System.out.println("Failed to create account");
                    userUI(stmt);
                    return;
                }
            }
            else{
                System.out.println("Details were entered incorrectly");
                userUI(stmt);
            }
        }
        else if (userChoice == 3){
            startUI(stmt);
        }
    }

    private static Account createAccount(Statement stm,int accountID) throws SQLException {
        ResultSet rs = null;
        try {
            rs = stm.executeQuery("SELECT * FROM accounts where ID=" + accountID);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Account currentUser = null;
        while (rs.next()){
            int ID = Integer.parseInt(rs.getString(1));
            String name = rs.getString(2);
            double bal = Double.parseDouble(rs.getString(3));
            int bnk = Integer.parseInt(rs.getString(4));
            int pin = Integer.parseInt(rs.getString(5));
            currentUser = new Account(ID,name,bal,bnk,pin);
        }
        return currentUser;
    }

    private static void successfulLogin(Account currentUser,Statement stm) throws SQLException {

        System.out.println("1)Check account details 3)Logout");
        int accountChoice = user_input.nextInt();
        if (accountChoice == 1){
            System.out.println(currentUser);
            successfulLogin(currentUser,stm);
        }
        else if (accountChoice == 3){
            userUI(stm);
        }
    }

}
