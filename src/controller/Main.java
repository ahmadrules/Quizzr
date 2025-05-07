package controller;

import model.*;
import model.Module;
import view.main.MainFrame;

import java.util.*;


public class Main {
    public static void main(String[] args) {
       new Controller();
     UserManager userManager = new UserManager();
     userManager.registerNewUser("admin", "admin", "admin", "admin");
     ArrayList<User>users= (ArrayList<User>) userManager.loadUsersFromFiles();

     for (User user : users) {
         if (user==null){
             users.remove(user);
         }
         System.out.println(user.getName());
     }
    }
}