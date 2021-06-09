package za.ac.cput.assignment3;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * ReadFromSerFile.java
 * Term 2: Assignment 3
 * @author Nkukwana Siphamandla (217046177)
 * Due date: 09 June 2021
 */

public class ReadFromSerFile {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    
    Stakeholder stakeholder;
    Stakeholder[] stakeholderArray = new Stakeholder[11];
    Customer customer;
    Customer[] customerArray = new Customer[6];
    Supplier supplier;
    Supplier[] supplierArray = new Supplier[5];
    
    public void openFile() {
        try {
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("Stakeholder Serialized File Open For Reading: ");
        }
        catch (IOException ioe) {
            System.out.println("Error:" + ioe.getMessage());   
        }
    }
    public void closeFile() {
        try {
            input.close(); 
        }
        catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
    }
    public void readFromFile() {
        try {
            while (true) {
                stakeholder = (Stakeholder)input.readObject();
                System.out.println(stakeholder);
           // for (int i = 0; i < stakeholderArray.length; i ++) {
           //   stakeholderArray[i] = (Stakeholder)input.readObject();
           // System.out.println(stakeholderArray[i]);
            }
        }
        catch (EOFException eofe) {
            System.out.println("End of file reached");
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
        catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
        finally {
            closeFile();
            System.out.println("File closed");
        }
    }
    
    
    public void customerArray() {
        customerArray[0] = new Customer("C100", "Mike", "Rohsopht", "Bellville", "24 Jan 1993", 975.10, true);
        customerArray[1] = new Customer("C130", "Stu", "Padassol", "Sea Point", "18 May 1987", 645.25, true);
        customerArray[2] = new Customer("C150", "Luke", "Atmyass", "Bellville", "27 Jan 1981", 1520.50, false);
        customerArray[3] = new Customer("C250", "Eileen", "Sideways", "Nyanga", "27 Nov 1999", 190.85, true);
        customerArray[4] = new Customer("C260", "Ima", "Stewpidas", "Atlantis", "27 Jan 2001", 1890.70, true);
        customerArray[5] = new Customer("C300", "Ivana.B", "Withew", "Langa", "16 Jul 1998", 1190.50, false);      
    }
    
     
    
    public void supplierArray() {
        supplierArray[0] = new Supplier("S350", "Auto Delight", "BMW", "Luxury SUV");
        supplierArray[1] = new Supplier("S270", "Grand Theft Auto", "Toyota", "Mid-size sedan");
        supplierArray[2] = new Supplier("S290", "MotorMania", "Hyundai", "compact budget");
        supplierArray[3] = new Supplier("S400", "Prime Motors", "Lexus", "Luxury sedan");
        supplierArray[4] = new Supplier("S300", "We got Cars", "Toyota", "10-seater minibus");
    }
    
    public void readCustomer() {
        try {
            for (int i = 0; i < customerArray.length; i ++) {
                customerArray[i] = (Customer)input.readObject();
                System.out.println(customerArray[i]);
            }
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
        catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
        finally {
            closeFile();
        }
    }
    public void readSupplier() {
        try {
            for (int i = 0; i < supplierArray.length; i ++) {
                supplierArray[i] = (Supplier)input.readObject();
                System.out.println(supplierArray[i]);
            }
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Error:" + ioe.getMessage());
        }
        catch (IOException ioe) {
            System.out.println("Error: " + ioe.getMessage());
        }
        finally {
            closeFile();
        }
    }  
    
    

    public static void main(String[] args) {
        ReadFromSerFile rfsf = new ReadFromSerFile();
        rfsf.openFile();
        rfsf.closeFile();
        rfsf.readFromFile();
        
        rfsf.customerArray();
        rfsf.supplierArray();
        //rfsf.readCustomer();
        //rfsf.readSupplier();
    }
}
