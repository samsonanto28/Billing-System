
import java.io.*;
import java.util.*;

abstract class Customer implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String name;

    Customer(String name) {
        this.name = name;
    }
    Customer()
    {
        this.name="";
    }
    
    abstract void buy(Bill bill);
}

class CasualCustomer extends Customer implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static long idCounter;
    private static String prefix = "CSTNUM";
    private Bill bill;
    
    CasualCustomer() {
        super(prefix + String.format("%7s", (++idCounter)).replace(" ", "0"));    
        // assigning a unique and incremental identification token
    }
    
    void buy(Bill bill) {
        this.bill = bill;
    }
    
    String getName()    {
        return name;
    }
    
    PremiumCustomer upgrade(String name)   {
        PremiumCustomer temp = new PremiumCustomer(name);
        temp.buy(bill);
        
        // temp till code is completed
        return temp;
        
    }
}

class PremiumCustomer extends Customer implements Serializable 
{
    static final long serialVersionUID = 1L;
    private static String prefix = "PRECSTNUM";
    static  long cardNumberCounter;
    private long cardNumber;
    private int points;
    Vector<PremiumCustomer> customerList;
    private double pointMultiplier = 0.05;
    // Bill History
    Vector<Bill> billHistory;          // The Vector that stores all the items in 

    PremiumCustomer(String name) {
        super(name);
        cardNumber =(++cardNumberCounter);
        points = 0;
        billHistory = new Vector<>(10,2);
        customerList=new Vector<>();
    }
    PremiumCustomer()
    {
        super();
        billHistory = new Vector<>(10,2);
        customerList=new Vector<>();
        
    }
    void buy(Bill bill) {
        // customer buys new stuff
        billHistory.add(bill);
        
        // add points 
        points += (int) (bill.getTotalCost() * pointMultiplier);
        
    }

    int getPoints()
            {
                return points;
            }
    boolean deductPoints(int points)   {
        if (getPoints() >= points)  {
            this.points -= points;
            return true;
        } else  {
            return false;
        }
    }
    String getName()
    {
        return name;
    }
    long getCardNumber()
    {
        return cardNumber;
    }
    void addPoints(int points) {
        this.points += points;
    }
    String formatedOutput()
    {
        return String.format("%d - %s%07d : %s",Calendar.getInstance().get(Calendar.YEAR),prefix,cardNumber,name);
    }
    String getFormatedCardNumber() {
        return String.format("Card No  : %d - %s%07d",Calendar.getInstance().get(Calendar.YEAR),prefix,cardNumber);
    }
    String getFormatedPoints() {
        return String.format("Points     : %d", points);
    }
    String getFormatedName()
    {
        return String.format("Name     : %s", name);
    }
    String getFormatedDetail()
    {
        return String.format("Name     : %s\nPoints     : %d\nCard No  : %d - %s%07d\n",name,points,Calendar.getInstance().get(Calendar.YEAR),prefix,cardNumber);
    }
    
}
