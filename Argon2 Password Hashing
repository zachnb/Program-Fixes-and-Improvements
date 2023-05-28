import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.InputMismatchException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;


/**
* File: HomeworkOne.java
* Author: Zachary N. Brown
* Date: August 25, 2022
* Purpose: This program demonstrates the implementation
* of security measures for a file-uploading program
*/

public class HomeworkOne {
 
    /**
    * @param args the command line arguments
     *
    */
      
    public static void main(final String[] args) {
         
        final String filename = args[0];
        
        // Check file name for malicous characters
        Pattern pattern = Pattern.compile("[^A-Za-z0-9._]");
        Matcher matcher = pattern.matcher(filename);
            
        if (matcher.find()) {
            System.out.println("Possible malicious code detected");
            System.exit(0);
            } else { 
                // Here we are comparing the command line argument import with 
                // an original to ensure no malicious code was added
                try {
                    BufferedReader reader1 = new BufferedReader(new FileReader
                        ("Original.txt")); 
                    BufferedReader reader2 = new BufferedReader(new FileReader
                        (args[0])); 
                    String line1 = reader1.readLine();  
                    String line2 = reader2.readLine();  
                    boolean areEqual = true;  
                    int lineNum = 1;

                    while (line1 != null || line2 != null) {

                        if(line1 == null || line2 == null) {
                            System.out.println("Possible malicious code "
                                    + "detected.");
                            System.exit(0);
                            // added code to check for content deletion (AUG 25)
                        } else if (!line1.equalsIgnoreCase(line2)){
                            System.out.println("Possible malicious code "
                                    + "detected.");
                            System.exit(0);
                        } else {
                            line1 = reader1.readLine();
                            line2 = reader2.readLine();
                            lineNum++;
                        }
                    }
                        if(areEqual) {
                            // set file to read-only to further prevent 
                            // malicious code injection prior to entering 
                            // application and being passed through
                            final File file = new File(filename);
                            file.setReadOnly();
                            HomeworkOne.menu(file);
                        } else {
                            System.out.println("Possible malicious code "
                                    + "detected.");
                            System.exit(0);
                        }
                        // close readers
                        reader1.close();
                        reader2.close();                        
                } catch (IOException e){
                    System.out.println("Possible malicious code "
                                    + "or no file detected");
                    System.exit(0);
                }
            }   
    }
        // initialize array list to store hashes, the password string and 
        // attempt counter
        private static final ArrayList<String> USERS = new ArrayList<>();
        private static String password = null;
        private static int attemptCount = 0; 
        
        // sets the password in raw format to check 
        private static void setPass(String tempPass) {
        HomeworkOne.password = tempPass;
        }

            private static void menu(File file) {
 
                    Scanner scn = new Scanner(System.in);

                    System.out.println(USERS); // only to check hash is added

                    System.out.println("Please select: " + '\n' 
                            + "1: Create new password" + '\n'
                            + "2: Verify and view file" + '\n'
                            + "3: Exit program");
                    try {
                        int selection = scn.nextInt();
                        switch (selection) {
                            case 1:
                                createPassword(USERS, file);
                                break;
                            case 2:
                                verifyPassword(file);
                                break;
                            case 3:
                                System.exit(0);
                            default:
                                System.out.println("Invalid input");
                                menu(file);
                                break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input");
                        menu(file);
                    }
            }

            private static void createPassword(ArrayList users, File file) {
                
                Scanner scn = new Scanner(System.in);
                
                System.out.println("Enter password:");
                String password = scn.nextLine(); 
                // ensure created password has adequate complexity
                Pattern validPattern = Pattern.compile("^(?=.*[!@#$&*])(?=.*"
                        + "[0-9])(?=.*[a-z]).{8,20}$");
                Matcher matcher = validPattern.matcher(password);
                boolean matchFound = matcher.find();
                
                    if (matchFound) {
                        Argon2 argon2 = Argon2Factory.create
                        (Argon2Types.ARGON2id);
                        String hash = argon2.hash(2, 16000, 1, password);
                        boolean confirm = argon2.verify(hash, password);
                        
                    if (confirm){
                        // add hashed password to list
                        users.add(hash);
                        // set password
                        HomeworkOne.setPass(password);
                        menu(file);
                    } else { 
                        System.out.println("Fail");
                        menu(file);
                    }
                    } else {
                        System.out.println("Invalid password format");
                        createPassword(users, file);
                    }
            }
            
            private static void verifyPassword(File file) {
                
                Scanner scn = new Scanner(System.in);

                        System.out.println("Enter password:");
                        String inputPass = scn.nextLine();                      
                        boolean match = (inputPass.equals(password));
                        
                        if (attemptCount == 3) {
                                System.out.println("Too many attempts, "
                                    + "exiting");
                                System.exit(0);        
                        } else if (match){
                            // if verified, open file
                            openFile(file);
                        } else {
                            System.out.println("Invalid password");
                            attemptCount++; // increment attempt counter
                            verifyPassword(file);
                        }
                 }    
       
            private static void openFile(File file) {
                
                BufferedReader inputStream = null;
                String fileLine;

                    try {
                        inputStream = new BufferedReader(new FileReader(file
                        ));
                            System.out.println("Email Addresses:");
                        // Read one Line using BufferedReader
                        while ((fileLine = inputStream.readLine()) != null) {
                            System.out.println(fileLine);
                        }
                    } catch (IOException io) {
                            System.out.println("File IO exception" 
                                    + io.getMessage());
                    } finally {
                    // Need another catch for closing
                    // the streams
                        try {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                        } catch (IOException io) {
                            System.out.println("Issue closing the Files" +
                            io.getMessage());
                        }
                    }
            }
}   
