package homeworktwo;

/**
* File: Logic.java
* Author: Zachary N. Brown
* Date Created: September 06, 2022
* Last Updated: September 12, 2022
* Purpose: This class contains all of the required methods for
* validations, a logger method for audit purposes and a mailing method.
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;


public class Logic implements Data {
    
    // Method to check password with adequate complexity
    public static boolean createPassword(String createdName, 
            String createdPassword) {
                
        boolean isValid = false;
        // REGEX for password complexity
        Pattern validPattern = Pattern.compile("^(?=.*[!@#$&*])(?=.*"
                        + "[0-9])(?=.*[a-z]).{8,20}$");
        Matcher matcher = validPattern.matcher(createdPassword);
        boolean matchFound = matcher.find(); 
            // Check password meets complexity requirements and username does 
            // not already exist
            if (matchFound && !USERS.containsKey(createdName)) {
                // Hash and salt password
                String passHash = BCrypt.hashpw(createdPassword, 
                        BCrypt.gensalt());
                USERS.put(createdName, passHash);
                // Set hashed password value to pass to authentication method
                Presentation.passHash = passHash;
                // Print to console for confirmation
                System.out.println(Presentation.passHash);
                isValid = true;
            }
        return isValid;
    }
         
    // Method to authenticate user login credentials
    public static boolean authenticate(String enteredUser, 
            String enteredPassword, String passHash) { 
        // Add simulated "high-risk" user to array list for validation
        BADUSERS.add("highriskperson01");
        boolean isValid = false;
            // Check entered password with stored hash and ensure entered name 
            // is not an "unauthorized user"
            if (USERS.containsKey(enteredUser) && BCrypt.checkpw
        (enteredPassword, passHash) && (!BADUSERS.contains(enteredUser))) {
            isValid = true;
            }
        return isValid;
    }
    
    // Method to track login attempts
    public static boolean attempts(int attempts) {
        
        boolean isValid = false;
            // If user fails to enter valid credentials more than 3 times 
            // session becomes locked
            if (attempts > 3) {
                isValid = true;
            }
        return isValid;
    }
    
    // Method to confirm security code
    public static boolean validCode(int codeConfirm, Double inputCode) {
        
        boolean isValid = false;
            // Checks whether entered code matches emailed code
            if (inputCode == codeConfirm) {
                isValid = true;
            } 
        return isValid;
    }
    
    // Method to write the Audit message to file
    public static void logger(String warning) {
        
            BufferedWriter writer = null;
            // Create new file object
            File file = new File("C:\\Users\\Public\\"
                            + "Audit.txt");
            // Initialize variable for file existence
            boolean exists = file.exists();
            // If the file exists, append on a new line
            if (exists) {
                try {
                    writer = new BufferedWriter(new FileWriter("C:\\Users\\"
                            + "Public\\Audit.txt", true));
                    writer.append("\n" + "\n" + warning);
                } catch (IOException io) {
                    System.out.println("File IO Exception" +
                            io.getMessage());
                }
                // Close the file 
                finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException io) {
                        System.out.println("Issue closing the File." +
                                io.getMessage());
                    }
                }
            }
            else {
                // If the file does not exist, append without a new line
                try {
                    writer = new BufferedWriter(new FileWriter("C:\\Users\\"
                            + "Public\\Audit.txt", true));
                    writer.append(warning);
                    
                } catch (IOException io) {
                    System.out.println("File IO Exception" + io.getMessage());
                }
                // Close the file
                finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException io) {
                        System.out.println("Issue closing the File." +
                                io.getMessage());
                    }
                }
            }
    } 
       
   public static void mail(int number){
       
        Properties props = new Properties();
	props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
	props.put("mail.smtp.socketFactory.class",
			"javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.port", "465");
	// Create new session for the authenticator
        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                
                protected PasswordAuthentication getPasswordAuthentication() {
                    // Specify login credentials
                    return new PasswordAuthentication("zb116622@gmail.com",
                            "omfqjopkouixlmut");
                }
            });
		try {
                    // Specify "from" and "to" address
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("zazbro92@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("zazbro92@gmail.com"));
                    message.setSubject("Code Verification");
                    // Pass the random generated number variable into the 
                    // message
                    message.setText("Here is your verification code:\n" +
				(number));
                    // Send the message
                    Transport.send(message);
                    // Print message to console to confirm email was sent
                    System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
