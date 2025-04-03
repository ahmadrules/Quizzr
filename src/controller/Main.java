package controller;

import model.FileHandler;
import model.MultipleChoice;
import model.Question;
import view.QuizzrExample;
import model.Quiz;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       // QuizzrExample quiz = new QuizzrExample();
        //SwingUtilities.invokeLater(quiz);
       // MultipleChoice multipleChoice= new MultipleChoice("What is the next month", Collections.singletonList("june;july,May"),"May",5);
       // System.out.println(multipleChoice);
        //test test
       // multipleChoice.saveToFile("Test", multipleChoice);
       // multipleChoice.readFromFile("Test");
     //   System.out.println(multipleChoice);
        String filename= "testLoading.txt";
        FileHandler fileHandler = new FileHandler();
        MultipleChoice mc= new MultipleChoice("",new ArrayList<>(), "", 0);
        List<MultipleChoice> multipleChoices = fileHandler.loadQuestions(filename,mc);
        for (MultipleChoice mc2 : multipleChoices) {
            System.out.println(mc2);
        }
        //test test

    }
}