package Source;

/**
 * Created by Shaaheen on 4/10/2015.
 */
public class Account {
    private int ID;
    private String name;
    private double balance;
    private String bank;
    private int pin;

    public Account(int ID, String name, double balance, String bank,int pin) {
        this.ID = ID;
        this.name = name;
        this.balance = balance;
        this.bank = bank;
        this.pin = pin;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getBank() {
        return bank;
    }

    public String toString(){
        return "Name: " + name + "\r\nBalance: " + balance + "\r\nBank: " + bank + "\r\nAccount Number: "+ ID;
    }

}
