
import java.io.*;
import java.util.*;

public final class ItemsList {
    private ObjectOutputStream output;
    private ObjectInputStream input;
    File itemFile = new File("ItemInfo.data");
    Vector<Item> itemList;          // The Vector that stores all the items in 
                                    // shop and their amount
    boolean debug = false;
        
    ItemsList() {
        itemList = new Vector<>(50, 3);
        loadData();
    }
    
    Vector<Item> getList() {
        return itemList;
    }
    
    void addItem(Item it) {
        itemList.add(it);
    
    }
    
    // for the Instant autocompletion
    Vector<Item> getItemListFromBarCode(String barcode) {
        Vector<Item> itemListTemp = new Vector<>(10,3);
        
        for(Item it: itemList)  {
            if(it.getBarCodeStringId().toUpperCase().indexOf(barcode.toUpperCase()) != -1)  {
                itemListTemp.add(it);
            }
        }
        return itemListTemp;
    }
    
    // for the Instant autocompletion
    Vector<Item> getItemListFromName(String name) {
        Vector<Item> itemListTemp = new Vector<>(10,3);
        
        for(Item it: itemList)  {
            if(it.getName().toUpperCase().indexOf(name.toUpperCase()) != -1)  {
                itemListTemp.add(it);
            }
        }
        return itemListTemp;
    }
    
    boolean removeItem(Item it)  {
        if(itemList.indexOf(it) != -1)  {
            itemList.remove(it);
            if (debug) System.out.println("The Object was removed.");   // for debugging
            return true;
        } else  {
            if (debug) System.out.println("No such object.");   // for debugging       
            return false;
        }
    }
    
    void printList()  {
        System.out.println("The Items in the List are:");
        for(Item it: itemList)   {
            // for having a tabbed display
            System.out.print("    ");
            it.showConcise();
        }
    }
    
    boolean loadData() {
        Item r;
        int i = 0;
        try {
            input = new ObjectInputStream(new FileInputStream(itemFile));
            
            do {
                // for(int i = 0; i < 4; i++)  {    // Removed since number
                // of Items unknown.
                r = (Item) input.readObject();
                itemList.add(r);
                if (debug) r.showConcise();        // for debugging
            } while ((++i) < 1000);     // just a condition to prevent infinite
            // loop if no exceptions occur.
            input.close();
        } catch (EOFException eofex) {
            if (debug) System.out.println("Read " + i + "Records");
            if (debug) System.out.println("No more records");
            return true;
        } catch (ClassNotFoundException cnfex) {
            if (debug) System.out.println("unable to create object - class not found");
            return false;
        } catch (IOException e) {
            if (debug) System.out.println("some other Error");
            return false;
        }
        if (debug) System.out.println("Update the number of Records");
        return false;       // Should Ideally never reach here
                            // only way is that number of records is > 1000
    }

    boolean saveData() {
        try {
            output = new ObjectOutputStream(new FileOutputStream(itemFile));

            for (Item it : itemList) {
                it.showConcise();
                output.writeObject(it);
            }
            output.flush();
            output.close();
        } catch (InvalidClassException icex) {
            if (debug) System.out.println("invalid Class");
            return false;
        } catch (NotSerializableException nsex) {
            if (debug) System.out.println("Object is not Serializable");
            return false;
        } catch (IOException e) {
            if (debug) System.out.println("other problems");
            return false;

        }
        return true;
    }
}

