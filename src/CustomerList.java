/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

/**
 *
 * @author Edwin Clement
 */
public final class CustomerList {
    private ObjectOutputStream fout;
    private ObjectInputStream fin;
    private boolean debug=false;
    File customerFile = new File("CustomerInfo.data");
    Vector<PremiumCustomer> customerList;
    CustomerList() {
        customerList = new Vector<>(50, 3);
        loadData();
        setCardNumberCounter();
    }
    boolean loadData()
    {
        int i=0;
        PremiumCustomer p=null;
        try
        {

            fin=new ObjectInputStream(new FileInputStream(customerFile));
            while((i++)<10000)
            {
                p=(PremiumCustomer)fin.readObject();
                customerList.add(p);
            }
        }
        catch (EOFException eofex) {
            if (debug) System.out.println("Read " + i + "Records");
            if (debug) System.out.println("No more records");
            return true;
        } catch (ClassNotFoundException cnfex) {
            //if (debug) System.out.println("unable to create object - class not found");
            return false;
        } catch (IOException e) {
            //if (debug) System.out.println("some other Error");
            return false;
        }
        //if (debug) System.out.println("Update the number of Records");
        return false;
    }
    boolean saveData() 
    {
        try {
              fout = new ObjectOutputStream(new FileOutputStream(customerFile));

            for (PremiumCustomer p : customerList) {
                //it.showConcise();
                fout.writeObject(p);
            }
            fout.flush();
            fout.close();
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
    Vector<PremiumCustomer> getList()
    {
        return customerList;
    }
    void addCustomer(PremiumCustomer p)
    {
        customerList.add(p);
    }
    public static void main(String args[])
    {
        new PremiumCustomer();
    }
	@SuppressWarnings("static-access")
	void setCardNumberCounter()
    {
        int i=0;
        PremiumCustomer p,tmp;
        p=tmp=null;
        try
        {
            fin=new ObjectInputStream(new FileInputStream(customerFile)); 
            while((i++)<10000)
            {
                p=(PremiumCustomer)fin.readObject();
                tmp=p;
            }
            fin.close();
        }
        catch(Exception e){}
        if(tmp!=null) 
            tmp.cardNumberCounter=tmp.getCardNumber();
        
    }
    Vector<PremiumCustomer> getCustomerListFromName(String name)
    {
        Vector<PremiumCustomer> customerListTemp = new Vector<>(10,3);
        int n;
        String tmp;
        for(PremiumCustomer p: customerList) 
        {
            n=name.trim().length();
            if(n>p.getName().length())
                continue;
            tmp=p.getName().trim().substring(0, n);
            System.out.println("Debug 1 : "+tmp);
            if(tmp.equalsIgnoreCase(name.trim()))  
            {    
                System.out.println("Debug"+p.getName());
                customerListTemp.add(p);
            }
        }
        return customerListTemp;
    }
    
    PremiumCustomer getCustomerDetailsFromName(String str)
    {
        // Vector<PremiumCustomer> customerListTemp = new Vector<>(10,3);
        
        for(PremiumCustomer it: customerList)  {
            if(str.toUpperCase().indexOf(it.getName().toUpperCase()) != -1)  {
                return it;
            }
        }
        return null;
    }
}
