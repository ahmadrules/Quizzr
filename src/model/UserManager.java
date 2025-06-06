package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to manage information related to users
 * It handles registering new user, logging in, logging out
 * And saving and loading information related to the users
 * @author Lilas Beirakdar
 */
public class UserManager {
    List<User> users;
    private User currentUser;
    private final String userFilePath="src/model/files/users.dat";

    /**
     * Constructor used to create an object of userManger
     * It loads users from user file as objects
     * And sets the value of the current user
     * To be set when the actual user logs in the program
     * @author Lilas Beirakdar
     */
    public UserManager() {
        this.users= loadUsersFromFiles();
        this.currentUser = null;
    }

    /**
     * Checks the user email the user enters when registering as a new student
     * If the given email already exits it will return true
     * Otherwise it will return fals
     * @param email new student entered email
     * @return a boolean value which indicates if the given email already exists or not
     * @author Lilas Beirakdar
     */
    public boolean doesUserEmailExist(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the current user of the program
     * @return User object representing the current user of the program
     * @author Lilas Beirakdar
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks the username the user have given when registering as a new student
     * It returns true if username already exists
     * Otherwise it will return false
     * @param userName the name given by the student when registering
     * @return boolean value which indicates whether username already exits or not
     * @author Lilas Beirakdar
     */
    public boolean doesUsernameExist(String userName) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Registers a new student in the program
     * It checks first if the user has given a unique name and email
     * If the username or email already exists it return false
     * Otherwise it adds the new student to the user list and saves it to the file
     * @param name the name entered by the user to be used when logging in the program
     * @param password the password to be used when logging in the program
     * @param email the email entered by the user
     * @param programCode the code related to the program the student studies as a String
     * @return boolean value indicating whether the user has been added successfully to user list or not
     * @author Lilas Beirakdar
     */
    public boolean registerNewUser(String name, String password, String email, String programCode) {
        if (!doesUserEmailExist(email) && !doesUsernameExist(name)) {
            String hashedPassword=Hasher.hash(password);
            users.add(new User(name, hashedPassword, email, programCode,false));
            saveUsersToFiles();
            return true;
        }
        return false;
    }

    /**
     * Logs in a registered student into the program
     * It gets the username and password and checks if they exist
     * It hashes the given password to ensure information security
     * Then matchs given information with users information in user list
     * If the given information did not match with any user information it returns false
     * @param userName a String representing username given by the user
     * @param password a String representing password given by the user
     * @return boolean value indicating whether log in has been done successfully or not
     * @author Lilas Beirakdar
     */
    public boolean loginUser(String userName, String password) {
        String hashedPassword=Hasher.hash(password);
        for (User user : users) {
            if (user.getName().equals(userName) && user.getPassword().equals(hashedPassword)) {
                this.currentUser = user;
                return true;
            }
        }
        return false;
    }

    /**
     * Logs out the current user and sets the values of the current user to null
     * @author Lilas Beirakdar
     */
    public void logoutUser() {
        this.currentUser = null;
    }

    /**
     *
     * @param email
     * @return
     */
    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Used to save any progress done with user objects to the file
     *  used after any change on user object
     * @author Lilas Beirakdar
     */
    public void saveUsersToFiles(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFilePath))){
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads user objects from the file they are saved in
     * Then it saves the in a list to be used to get information about the current user
     *
     * @return a list of users registered in the program
     * @author Lilas Beirakdar
     */
    public List<User> loadUsersFromFiles() {
        List<User> users = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFilePath))){
            users= (List<User>) ois.readObject();

        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return users;
    }

    /**
     * Returns a list of registered users
     * @return a list of users
     * @author Lilas Beirakdar
     */
    public List<User> getUsersList() {
        return users;
    }
}
