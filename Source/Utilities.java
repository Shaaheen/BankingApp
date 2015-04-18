package Source;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

/**
 * Created by Shaaheen on 4/18/2015.
 */
public class Utilities {

    protected static int getCurrID(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT ID from accounts");
        int currentID = 0;
        while (rs.next()) {
            currentID = Integer.parseInt(rs.getString(1));
        }
        return currentID;
    }

    protected static int getBank(String bank){
        if (bank.equals("FNB"))
            return 1;
        else if (bank.equals("Standard bank"))
            return 2;
        else if (bank.equals("Nedbank"))
            return 3;
        else if (bank.equals("Absa"))
            return 4;
        else{
            System.out.println("Bank not recognised");
            return 0;
        }
    }
    
    protected static String getBank(int bankID){
        if (bankID == 1)
            return "FNB";
        else if (bankID == 2)
            return "Standard Bank";
        else if (bankID == 3)
            return "Nedbank";
        else if (bankID == 4)
            return "Absa";
        else{
            System.out.println("bank not recognised");
            return null;
        }
    }

    protected static int createPin(){
        Random rnd = new Random();
        String pin = "";
        while (pin.length() < 4){
            int dig = rnd.nextInt(9);
            if (dig >= 0){
                pin = pin + dig;
            }
        }
        return Integer.parseInt(pin);
    }

}
