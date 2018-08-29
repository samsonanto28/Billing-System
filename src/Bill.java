import java.io.*;
import java.util.*;

interface IdentificationCode   {    
    public String getId();
    public boolean verifyId(String id);
}

class BarCode implements IdentificationCode, Serializable {
    static final long serialVersionUID = 1L;

    private String id;

    public BarCode(String seed) {
        generateId(seed);
    }
    
    private void generateId(String seed)    {
        id = (new Integer(seed.hashCode() & 0xfffffff)).toString();
        System.out.println(id);
    }
    
    public String getId() {
        return id;
    }
    
    public boolean verifyId(String id)    {
        return (this.id == id);
    }
}
    

class Bill implements Serializable{
    static final long serialVersionUID = 1L;

    static long counterForId = 0;
    long id;
    // date
    Date date;
    Vector<Item> items; 

    Bill()  {
        date = new Date();
        id  = counterForId++;
        items = new Vector<>(10,3);
        System.out.println("Enter");
    }

    void addItem(Item it)  {
        // add item
        items.add(it);
    }

    void listItems()  {
        // List all items in the array
        System.out.println();       // for a blank line before the statement
        System.out.println("Elements of the list are:");
        for (Item it : items)
        {
            it.showConcise();
        }
    }

    int getTotalCost() {
        int totalCost = 0;
        for (Item it : items)
        {
            totalCost += it.getPrice();
        }
        return totalCost;
    }
}

class Item  implements Serializable  {
    static final long serialVersionUID = 1L;
    private String name;
    private double price;
    static transient Scanner sc;
    
    private BarCode barCodeId;

    Item(String name, double price)  {
        this.name = name;
        this.price = price;
        sc = new Scanner(System.in);
        barCodeId = new BarCode(name + price);
    }

    void showConcise()  {
        System.out.println(String.format("%12s :%-20s: Rs.%.2f", 
                barCodeId.getId(), name, price));
    }
    
    String getConcise()   {
        return String.format("%12s :%-20s: Rs.%.2f ", barCodeId.getId(),
                name, price);
    }
    
    String getBarCodeStringId() {
        return barCodeId.getId();
    }
    
    void inputData()
    {
        System.out.println("Enter name & price");
        name=sc.next();
        price=sc.nextDouble();
    }
    
    String getName()  {
        return name;
    }

    double getPrice()  {
        return price;
    }
}



