package Source;

import java.util.ArrayList;

/**
 * Created by Shaaheen on 4/11/2015.
 */
public class TotalAccounts {

    private ArrayList<Account> allAccounts = null;

    public TotalAccounts(ArrayList<Account> allAccounts) {
        this.allAccounts = allAccounts;
    }

    public void printString(){
        String toString = "";
        System.out.println("ID    Name        Balance      Bank           ");
        for (Account acc : allAccounts){
            //toString = toString + acc.getName() +"     " + acc.getBalance() + "     " + acc.getBank() + "    " + acc.getID();
            System.out.printf("%-6d",acc.getID());
            System.out.printf("%-13s", acc.getName());
            System.out.printf("%-12.2f", acc.getBalance());
            System.out.printf("%-14s",acc.getBank());
            System.out.println();
        }
    }

    public double getTotalBalt(){
        double totalBal = 0.00;
        for (Account acc:allAccounts){
            totalBal = totalBal + acc.getBalance();
        }
        return totalBal;
    }

    public Account getWealthiest(){
        Account wealthiest = null;
        int highestBal = 0;
        for (Account acc:allAccounts){
            if (acc.getBalance()>highestBal){
                wealthiest = acc;
            }
        }
        return wealthiest;
    }
}
