package controller;

import model.*;
import model.Module;
import view.main.MainFrame;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Controller();
        //test test

        //test test
        FileHandler fileHandler = new FileHandler();
        List<Question> questions = new ArrayList<>();
        //String filePath= "src/model/files/course2/module1/trueFalse_questions.txt";
       /** TrueOrFalse tf= new TrueOrFalse("", Collections.singletonList(""),0,"");
        questions=fileHandler.loadQuestions(filePath,tf);
        for (Question question : questions) {
            System.out.println(question);
        }*/
       String filePath= "src/model/files/course2/module1/multiChoice_questions.txt";
       /**List<MultipleChoice> multipleChoices = new ArrayList<>();
       MultipleChoice multipleChoice= new MultipleChoice("", Collections.singletonList(""),"",5);
       questions= fileHandler.loadQuestions(filePath,multipleChoice);
        for (Question question : questions) {
            System.out.println(question);
        }*/
      // String filePath= "src/model/files/course2/module1/matching_questions.txt";
     /**   List<Matching> questions3 = new ArrayList<>();
        HashMap<String,Integer> matches = new HashMap<>();
        Matching matching= new Matching("",Collections.singletonList(""),Collections.singletonList(""),0,matches);
        questions3= fileHandler.loadQuestions(filePath, matching);
        for (Question question : questions3) {
            System.out.printf("Question: %s\n",question);
        }*/
        Module module1 = new Module("module1");
       /** ArrayList<Question> questions1 =module1.generateMatchingQuiz(filePath,5);
        for (Question question : questions1) {
            System.out.println(question);
        }*/
      /**  ArrayList<Question> questions1 = module1.generateTrueOrFalseQuiz(filePath,8);
        for (Question question : questions1) {
            System.out.println(question);
        }*/
      ArrayList<Question> questions1 = module1.generateMultipleChoiceQuiz(filePath,10);
      for (Question question : questions1) {
          System.out.println(question);
      }

    }
}