package homeworktwo;

/**
* File: Data.java
* Author: Zachary N. Brown
* Date Created: September 06, 2022
* Last Updated: September 12, 2022
* Purpose: This Java interface contains stored and checked user data for
* the application's methods.
*/

import java.util.ArrayList;
import java.util.HashMap;

public interface Data {
    
    // Initialize map to store login credentials
    final static  HashMap<String, String> USERS = new HashMap<>();
    // Initialize arraylist of usernames considered high risk IAW AC-2(13)
    final static ArrayList<String> BADUSERS = new ArrayList<>(); 
}
