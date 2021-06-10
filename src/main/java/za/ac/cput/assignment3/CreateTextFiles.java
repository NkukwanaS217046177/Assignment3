package za.ac.cput.assignment3;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * CreateStakeholderSer.java
 * Term 2: Assignment 3
 * @author Nkukwana Siphamandla (217046177)
 * Due date: 09 June 2021
 */

public class CreateTextFiles {
    ObjectInputStream input;
    BufferedWriter bw;
    Customer customer;
    Supplier supplier;
    Object obj;
    
    ArrayList<Customer> customerArray = new ArrayList();
    ArrayList<Supplier> supplierArray = new ArrayList();
    
    int canRent = 0;
    int cannotRent = 0;
    
    public void openSerializedFile(){     
        try{
            input = new ObjectInputStream( new FileInputStream( "stakeholder.ser" )); 
            System.out.println("File opened successfully");            
        }
        catch (IOException ioe){
            System.out.println("Error: " + ioe.getMessage());
            System.exit(1);
        }
    }
    public void readingFromSerializedFile(){
        try{
            openSerializedFile();
            System.out.println("Reading From Serialized File");   
           while(true){
                try{
                    obj = (Object) input.readObject();  
                    if(obj instanceof Customer){
                        customer = (Customer) obj;
                        customerArray.add(customer);
                    }else if(obj instanceof Supplier){
                        supplier = (Supplier) obj;
                        supplierArray.add(supplier);     
                    }
                }catch(ClassNotFoundException e) {
                    System.out.println("Error: " + e.getMessage( )); 
      
                }catch(EOFException e){
                    System.out.println("End of file reached");
                    break;
                }  
            } 
            
        }catch (IOException e) {
            System.out.println("Error: " + e.getMessage( )); 
        } finally{
            closeSerializedFile();
        }
    }
    
     public void closeSerializedFile(){
        try{
            input.close();
            System.out.println("File closed");
        }catch (IOException e) 
            { System.out.println("Error: " + e.getMessage( )); }
    }
    
    public void sortCustomerArrayList(){
        Collections.sort(customerArray, (Customer o1, Customer o2) -> o1.getStHolderId().compareTo(o2.getStHolderId()));
    }
    
    public void sortSupplierArrayList(){
        Collections.sort(supplierArray, (Supplier o1, Supplier o2) -> o1.getName().compareTo(o2.getName()));
    }
    
    public static String changeDateFormat(String oldDate){
        String newDate=""; 
        
        try{    
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = sdf.parse(oldDate);
                
                SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd MMM yyyy");
                newDate = newDateFormatter.format(d);
        
        }catch(ParseException e){
            System.out.println(" "+ e.getMessage());
        }
        return newDate;
    } 
    
    public void canRentOrNot(){
        
        for(int i = 0; i < customerArray.size(); i++){
            
            if(customerArray.get(i).getCanRent() == true){
                canRent++;
            }else if(customerArray.get(i).getCanRent() == false){
                cannotRent++;
            }
        }
    }
    public void openCustWriteFile(){
        try{
            bw = new BufferedWriter(new FileWriter("customerOutFile.txt"));
            System.out.println("Successfully created and opened custmerOutFile.txt");
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    public void openSuppWriteFile(){
        try{
            bw = new BufferedWriter(new FileWriter("supplierOutFile.txt"));
            System.out.println("Successfully created and opened supplierOutFile.txt");
        }
        catch (IOException ioe){            
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    public void closeWriteFile(){
        try{
            bw.close();
            System.out.println("Successfully closed writingToFile");
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    public void writingToCustomerFile(){
        try{
            
            openCustWriteFile();
             
            String heading = "============================================ CUSTOMERS ===========================================";
            String subHeading = "ID  \tName \tSurname \tAddress \tDate of Birth \tAge \tCredit \t\tCanRent";
            String border = "==================================================================================================";

            bw.write(heading);
            bw.newLine();
            bw.write(subHeading);
            bw.newLine();
            bw.write(border);
            bw.newLine();

            for(int i = 0;i< customerArray.size();i++){
                
                String oldDateFormat = customerArray.get(i).getDateOfBirth();
                String dob = customerArray.get(i).getDateOfBirth();
                
                String str = String.format("%-8s%-8s%-16s%-16s%-16s%-8dR %-14.2f%-8d",
                            customerArray.get(i).getStHolderId(),
                            customerArray.get(i).getFirstName(),
                            customerArray.get(i).getSurName(),
                            customerArray.get(i).getAddress(),
                            //changeDateFormat(oldDateFormat),
                            //ageOfCustomer(dob),   
                            customerArray.get(i).getCredit(),
                            customerArray.get(i).getCanRent());
                
                bw.write(str);
                bw.newLine();
            }
            
            canRentOrNot();
            String rentYes = "Number of customers who can rent: " + canRent;
            String rentNo = "Number of customers who cannot rent: " + cannotRent;
            bw.write(border);
            bw.newLine();
            bw.write(rentYes);
            bw.newLine();
            bw.write(rentNo);
            bw.newLine();
            bw.write(border);
            
            System.out.println("Customer written successfully ");
            
            
        }catch(FileNotFoundException e){ 
            System.out.println("" + e.getMessage( )); 
        }catch(IOException e){ 
            System.out.println("" + e.getMessage( )); 
        }finally{
            closeWriteFile();
        }
        
    }
    
    public void writingToSupplierFile(){
        try{
            
            openSuppWriteFile();
             
            String heading = "============================ Suppliers ===============================";
            String subHeading = String.format("%-8s%-20s%-17s%-18s","ID","Name","Product Type","Product Description");
            String border = "======================================================================";

            bw.write(heading);
            bw.newLine();
            bw.write(subHeading);
            bw.newLine();
            bw.write(border);
            bw.newLine();

            for(int i = 0;i< supplierArray.size();i++){
                String str = String.format("%-8s%-20s%-17s%-18s",
                            supplierArray.get(i).getStHolderId(),
                            supplierArray.get(i).getName(),
                            supplierArray.get(i).getProductType(),
                            supplierArray.get(i).getProductDescription()
                            );
                
                bw.write(str);
                bw.newLine();
            }
            bw.write(border);
            System.out.println("Supplier written successfully ");
        }catch(FileNotFoundException e){ 
            System.out.println("" + e.getMessage( )); 
        }catch(IOException e){ 
            System.out.println("" + e.getMessage( )); 
        }finally{
            closeWriteFile();
        }
    }
    public static void main(String args[])  {
        CreateStakeholderSer obj = new CreateStakeholderSer();
        obj.openFile();
        obj.writeToFile();
        
        CreateTextFiles ctf = new CreateTextFiles();
        ctf.readingFromSerializedFile();
        ctf.sortCustomerArrayList();
        ctf.writingToCustomerFile();
        ctf.sortSupplierArrayList();
        ctf.writingToSupplierFile();
    }
}
