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
       // FileHandler fileHandler = new FileHandler();
        //List<Question> questions1 = new ArrayList<>();
        String filePath1= "src/model/files/course2/module1/trueFalse_questions.txt";
       /** TrueOrFalse tf= new TrueOrFalse("", Collections.singletonList(""),0,"");
        questions1=fileHandler.loadQuestions(filePath1,tf);
        for (Question question : questions1) {
            System.out.println(question);
        }*/
      String filePath2= "src/model/files/course2/module1/multiChoice_questions.txt";
     /**  List<MultipleChoice> multipleChoices = new ArrayList<>();
       MultipleChoice multipleChoice= new MultipleChoice("", Collections.singletonList(""),"",5);
       ArrayList<Question> questions2= fileHandler.loadQuestions(filePath2,multipleChoice);
        for (Question question : questions2) {
            System.out.println(question);
        }*/
       String filePath3= "src/model/files/course2/module1/matching_questions.txt";
       /** List<Matching> questions3 = new ArrayList<>();
        HashMap<String,Integer> matches = new HashMap<>();
        Matching matching= new Matching("",Collections.singletonList(""),Collections.singletonList(""),0,matches);
        questions3= fileHandler.loadQuestions(filePath3, matching);
        for (Question question : questions3) {
            System.out.printf("Question: %s\n",question);
        }*/
        Module module1 = new Module("module1");
        //ArrayList<Question> matchingQuestions =module1.generateMatchingQuiz(filePath3,5);
      /**  for (Question question : questions2) {
            System.out.println(question);
        }*/
       //ArrayList<Question> trueFalseQuestions = module1.generateTrueOrFalseQuiz(filePath1,8);
        /**for (Question question : questions1) {
            System.out.println(question);
        }*/
     // ArrayList<Question> questions1 = module1.generateMultipleChoiceQuiz(filePath,10);
     /** for (Question question : questions1) {
          System.out.println(question);
      }**/
     ArrayList<Question> questions= module1.generateGeneralQuiz(filePath2,filePath3,filePath1,21);
     for (Question question : questions) {
         System.out.println(question);
     }

    }
}