package controller;

import model.FlashCard;
import model.Module;
import view.main.MainFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
      //  new Controller();
        Module module= new Module("Module");
        FlashCard flashCard= new FlashCard("Constructor","is used to create an object from a class");
        module.addFlashCard(flashCard);
       // module.saveFlashCardsToFile(fileName);

    }
}