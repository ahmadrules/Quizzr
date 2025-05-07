package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    List<User> users;
    private User currentUser;
    private final String userFilePath="src/model/files/users.dat";

    public UserManager() {
        this.users= loadUsersFromFiles();
        this.currentUser = null;
    }

    public boolean doesUserEmailExist(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean doesUsernameExist(String userName) {
        for (User user : users) {
            if (user.getName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean registerNewUser(String name, String password, String email, String programCode) {
        if (!doesUserEmailExist(email) && !doesUsernameExist(name)) {
            String hashedPassword=Hasher.hash(password);
            users.add(new User(name, hashedPassword, email, programCode));
            saveUsersToFiles();
            return true;
        }
        return false;
    }

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

    public void logoutUser() {
        this.currentUser = null;
    }

    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
    public void saveUsersToFiles(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userFilePath))){
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<User> loadUsersFromFiles() {
        List<User> users = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userFilePath))){
            users= (List<User>) ois.readObject();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public List<User> getUsersList() {
        return users;
    }
}
