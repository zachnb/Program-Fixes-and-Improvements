/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package homeworktwo;

/**
* File: Presentation.java
* Author: Zachary N. Brown
* Date Created: September 06, 2022
* Last Updated: September 12, 2022
* Purpose: This class initializes a JavaFX GUI for a user to 
* sign up or login to an application that abides by various NIST 
* RMF security guidelines.
*/

import java.time.Instant;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Presentation extends Application implements Data {
    
    public static int attempts = 0; // Initialize attempt counter for login 
                                    // attempts
    public static String passHash = null; // Initialize hashed password 
                                          // value to use for authentication
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("SDEV425 Login");
        // Grid Pane divides your window into grids
        GridPane grid = new GridPane();
        // Align to Center
        // Note Position is geometric object for alignment
        grid.setAlignment(Pos.CENTER);
        // Set gap between the components
        // Larger numbers mean bigger spaces
        grid.setHgap(10);
        grid.setVgap(10);

        // Create some text to place in the scene
        Text scenetitle = new Text("Welcome. Login or sign up to continue.");
        // Add text to grid 0,0 span 2 columns, 1 row
        grid.add(scenetitle, 0, 0, 2, 1);

        // Create Label
        Label userName = new Label("User Name:");
        // Add label to grid 0,1
        grid.add(userName, 0, 1);

        // Create Textfield
        TextField userTextField = new TextField();
        // Add textfield to grid 1,1
        grid.add(userTextField, 1, 1);

        // Create Label
        Label pw = new Label("Password:");
        // Add label to grid 0,2
        grid.add(pw, 0, 2);

        // Create Passwordfield
        PasswordField pwBox = new PasswordField();
        // Add Password field to grid 1,2
        grid.add(pwBox, 1, 2);

        // Create Login Button
        Button loginButton = new Button("Login");
        // Add button to grid 1,4
        grid.add(loginButton, 1, 4);
        
        // Create sign up button
        Button signUpButton = new Button("Sign Up");
        // Add button to grid 1,5
        grid.add(signUpButton, 1, 5);

        // Set the Action when button is clicked
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {   
                // Authenticate the user
                boolean isValid = Logic.authenticate(userTextField.getText(), 
                        pwBox.getText(), passHash);
                if (isValid) {
                    // Verification code to be passed to email method in Logic 
                    // class and reset hashed password to default empty value
                    passHash = null;
                    System.out.println(passHash); // Print for confirmation
                    Random random = new Random(); // Create new Random object
                    int number = random.nextInt(9999);
                    Logic.mail(number);
                        
                    grid.setVisible(false);
                    GridPane grid1 = new GridPane();
                    grid1.setAlignment(Pos.CENTER);
                    grid1.setHgap(10);
                    grid1.setVgap(10);
                    
                    Label code = new Label("Enter code:");
                    // Add label to grid 0,2
                    grid1.add(code, 0, 2);
                    
                    // Create code text field
                    PasswordField codePasswordField = new PasswordField();
                    // Addto grid 1,2
                    grid1.add(codePasswordField, 1, 2);

                    // Create policy accept button
                    Button confirmButton = new Button("Confirm");
                    // Add button to grid 1,3
                    grid1.add(confirmButton, 1, 3);
                    
                    Scene scene = new Scene(grid1, 500, 400);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                   
                    confirmButton.setOnAction(new EventHandler<ActionEvent>() {
                        
                        @Override
                        public void handle(ActionEvent e) {                   
                            try {
                                // Convert input string to double for comparison
                                Double inputCode = Double.parseDouble
                                                  (codePasswordField.getText());
                                // pass input and generated code to comparison
                                // method in Logic class IAW IA-2
                                boolean isValid = Logic.validCode(number, 
                                                        inputCode);
                                
                                if (isValid) {
                                    grid.setVisible(false);
                                    GridPane grid2 = new GridPane();
                                    grid2.setAlignment(Pos.TOP_CENTER);
                                    grid2.setHgap(10);
                                    grid2.setVgap(10);
                                    // Show privacy policy to user before 
                                    // continuting IAW AC-8
                                    Text privacyPolicy = new Text
                                    ("PRIVACY AND SECURITY POLICY\n" + "\n" +
                                    "You are accessing a U.S. Government system.\n" 
                                    + "System usage may be monitored, recorded, and "
                                            + "subject to audit.\n" +
                                    "Unauthorized use of the system is prohibited "
                                            + "and subject to criminal and civil "
                                            + "penalties.\n" +
                                    "Use of this system indicates consent to "
                                            + "monitoring and recording.\n" +
                                    "By clicking accept, you declare that you agree "
                                            + "to and will abide by these terms.");

                                    // Create pane for privacy policy
                                    ScrollPane sp = new ScrollPane();
                                    sp.setPrefSize(450, 200);
                                    sp.setContent(privacyPolicy);
                                    // Add scroll pane with policy to grid 1,1
                                    grid2.add(sp, 1, 1);
                                    // Create policy accept button
                                    Button acceptButton = new Button("Accept");
                                    // Add button to grid 1,3
                                    grid2.add(acceptButton, 1, 3);
                                    // Create policy deny button
                                    Button denyButton = new Button("Deny");
                                    // Add button to grid 1,4
                                    grid2.add(denyButton, 1, 4);
                                    // Set the size of privacy policy scene
                                    Scene scene = new Scene(grid2, 500, 400);
                                    primaryStage.setScene(scene);
                                    primaryStage.show();     

                                    denyButton.setOnAction(new 
                                                   EventHandler<ActionEvent>() {
                                        // Return to main page if user denies 
                                        //policies
                                        @Override
                                        public void handle(ActionEvent e) {
                                            start(primaryStage);
                                        }
                                    });

                                    acceptButton.setOnAction(new 
                                                   EventHandler<ActionEvent>() {

                                        @Override
                                        public void handle(ActionEvent e) {

                                            grid.setVisible(false);
                                            GridPane grid4 = new GridPane();
                                            grid4.setAlignment(Pos.CENTER);
                                            grid4.setHgap(10);
                                            grid4.setVgap(10);
                                            // Show welcome text if policy is 
                                            // accepted
                                            Text scenetitle = new Text
                                                        ("Welcome!");
                                            // Add text to grid 0,0 span 2 
                                            // columns, 1 row
                                            grid4.add(scenetitle, 0, 0, 2, 1);
                                            // Set the size of welcome scene
                                            Scene scene = new Scene(grid4, 500, 
                                                                        400);
                                            primaryStage.setScene(scene);
                                            primaryStage.show();
                                        }   
                                    });
                                   } 
                                   else { 
                                    
                                        final Text actionTarget = new Text();
                                        grid1.add(actionTarget, 1, 6);
                                        actionTarget.setFill(Color.FIREBRICK);
                                        actionTarget.setText("Invalid code.");
                                    }
                            // Catch format exception if another
                            // data type is entered
                            } catch (NumberFormatException nfe) {
                                
                                final Text actionTarget = new Text();
                                grid1.add(actionTarget, 1, 6);
                                actionTarget.setFill(Color.FIREBRICK);
                                actionTarget.setText("Invalid code.");
                            }
                        }
                    });                                 
                } 
                else {
                    // If invalid, ask user to try again
                    final Text actionTarget = new Text();
                    grid.add(actionTarget, 1, 6);
                    actionTarget.setFill(Color.FIREBRICK);
                    actionTarget.setText("Please try again.");
                    
                    // Login attempt limit and lockout IAW AC-7
                    boolean attemptNumber = Logic.attempts(attempts);
                    
                    if (attemptNumber) { 
                        final Text actionTargetTwo = new Text();
                        grid.add(actionTargetTwo, 1, 7);
                        actionTargetTwo.setFill(Color.FIREBRICK);
                        actionTargetTwo.setText("Too many login attempts.");
                        
                        final Text actionTargetThree = new Text();
                        grid.add(actionTargetThree, 1, 8);
                        actionTargetThree.setFill(Color.FIREBRICK);
                        actionTargetThree.setText("Try again later.");                       
                        // Make fields uneditable if attempts counter reaches 4
                        userTextField.setEditable(false);
                        pwBox.setEditable(false);
                        loginButton.setDisable(true);
                        signUpButton.setDisable(true);

                        // Create audit message to be passed to the logger
                        // method in the Logic class
                        // Include required information IAW AU-3
                        // Log UTC and system time IAW AU-8
                        String warning = "WHAT: TOO MANY LOGIN ATTEMPTS\n" +
                        "WHEN: UTC TIME: " + (Instant.now()) + "\n" +
                        "SYSTEM TIME: " + (System.currentTimeMillis()) + "\n" +
                        "WHERE: PERSONAL COMPUTER\n" +
                        "SOURCE: LOGIN PAGE\n" +
                        "OUTCOME: APPLICATION LOCKED\n" +
                        "IDENTITY: " + (userTextField.getText());
                        // Pass warning message to logger method in Logic class
                        Logic.logger(warning);
                        
                        // Create new timer object
                        Timer timer = new Timer();
                        TimerTask task = new TimerTask() {
                        
                            @Override
                            public void run() {
                            // Set text fields as editable and buttons useable
                            // when timer runs out
                            userTextField.setEditable(true);
                            pwBox.setEditable(true);
                            loginButton.setDisable(false);
                            signUpButton.setDisable(false);
                            // Reset attempt counter after lockout ends
                            attempts = 0; 
                            // Remove warning messages from grid
                            actionTarget.setText("");
                            actionTargetTwo.setText("");
                            actionTargetThree.setText("");
                            }
                        };
                        // Set timer to reauthorize login after short time
                        timer.schedule(task, 36000l);
                    } 
                    else {
                    // Increment attempts and pass value to method in Logic 
                    // class. Log issue in accordance with AC-7
                    attempts++;
                    Logic.attempts(attempts);
                    } 
                }
            }
        });

        // Set action to be performed when sign up button is clicked
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent e) {
                
                grid.setVisible(false);
                GridPane grid3 = new GridPane(); 
                grid3.setAlignment(Pos.CENTER);
                grid3.setHgap(10);
                grid3.setVgap(10);
                
                // Create new username Label
                Label newUserName = new Label("Desired Username:");
                // Add label to grid 0,1
                grid3.add(newUserName, 0, 1);

                // Create Textfield
                TextField userTextField = new TextField();
                // Add textfield to grid 1,1
                grid3.add(userTextField, 1, 1);

                // Create new password Label
                Label newPassword = new Label("Desired Password:");
                // Add label to grid 0,2
                grid3.add(newPassword, 0, 2);

                // Create Passwordfield
                PasswordField newPasswordBox = new PasswordField();
                // Add Password field to grid 1,2
                grid3.add(newPasswordBox, 1, 2);

                // Create Complete form button
                Button completeButton = new Button("Complete");
                // Add button to grid 1,4
                grid3.add(completeButton, 1, 4);
                
                 // Set action to be performed when complete button is clicked
                completeButton.setOnAction(new EventHandler<ActionEvent>() {
                    
                    @Override
                    public void handle(ActionEvent e) {
                        
                        // Add values to users data structure
                        String createdName = userTextField.getText(); 
                        String createdPassword = newPasswordBox.getText();
                        boolean goodPass = Logic.createPassword(createdName, 
                                createdPassword);
                        if (goodPass) {
                        start(primaryStage);
                        }
                        else {
                            final Text actionTarget = new Text();
                            grid3.add(actionTarget, 1, 6);
                            actionTarget.setFill(Color.FIREBRICK);
                            actionTarget.setText("Please use a stronger "
                              + "password\n" + "or pick a different username.");     
                        }
                    }
                });
                
                Button resetButton = new Button("Reset");
                // Add button to grid 1,5
                grid3.add(resetButton, 1, 5);
                
                // Set action to be performed when rest button is clicked
                resetButton.setOnAction(new EventHandler<ActionEvent>() {
                
                    @Override
                    public void handle(ActionEvent e) {
                           
                        // clear both fields
                        userTextField.clear();
                        newPasswordBox.clear();
                    }  
                });
                // Set the size of sign up scene
                Scene scene = new Scene(grid3, 500, 400);
                primaryStage.setScene(scene);
                primaryStage.show();
            }        
        });
        // Set the size of primary Scene
        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}               