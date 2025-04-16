package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Module {
    private String name;
    private List<FlashCard> flashCards;
    private final String quizFileName = "src/files/recent_quiz.dat";
    private Quiz currentQuiz;
    private final String matchingFileName = "matching_questions.txt";
    private final String multiChoiceFileName = "multiChoice_questions.txt";
    private final String trueOrFalseFileName = "trueFalse_questions.txt";
    private List<Question> allQuestions;
    private List<Matching> matchingQuestions;
    private List<MultipleChoice> multiChoiceQuestions;
    private List<TrueOrFalse> trueOrFalseQuestions;
    FileHandler fileHandler = new FileHandler();

    public Module(String name) {
        this.name = name;
        this.flashCards = new ArrayList<>();
        this.allQuestions = new ArrayList<>();
        this.matchingQuestions = new ArrayList<>();
        this.multiChoiceQuestions = new ArrayList<>();
        this.trueOrFalseQuestions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    public void addFlashCard(FlashCard flashCard) {
        this.flashCards.add(flashCard);
    }
    public void saveQuiz(Quiz quiz){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(quizFileName))){
            oos.writeObject(quiz);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadQuizFromFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(quizFileName))){
            Quiz readQuiz = (Quiz) ois.readObject();
            currentQuiz = readQuiz;
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void generateQuiz(String quizType, String filePath){
        switch (quizType){
            case "Matching":
                generateMatchingQuiz(filePath+matchingFileName);
                break;
            case "TrueOrFalse":
                generateTrueOrFalseQuiz(filePath+matchingFileName);
                break;
            case "MultipleChoice":
                generateMultipleChoiceQuiz(filePath+matchingFileName);
                break;
            default:
                generateGeneralQuiz();
                break;
        }
    }
    private void generateGeneralQuiz(){

    }

    private ArrayList<Question> generateMultipleChoiceQuiz(String fileName){

        currentQuiz = new Quiz("multiChoiceQuiz");
        MultipleChoice multipleChoice= new MultipleChoice("", Collections.singletonList(""),"",0);
        ArrayList<Question> multipleChoiceQuestion = fileHandler.loadQuestions(fileName,multipleChoice);
        return multipleChoiceQuestion;

    }
    private ArrayList<Question> generateTrueOrFalseQuiz(String fileName){
        currentQuiz = new Quiz("trueOrFalseQuiz");
        TrueOrFalse trueOrFalse= new TrueOrFalse("", Collections.singletonList(""),0,"");
        ArrayList<Question> trueOrFalseQuestion = fileHandler.loadQuestions(fileName,trueOrFalse);
        return trueOrFalseQuestion;
    }
    private ArrayList<Question> generateMatchingQuiz(String fileName){
        HashMap<String,Integer> matches=new HashMap<>();
        Matching matching= new Matching("", Collections.singletonList(""), Collections.singletonList(""),0 ,matches);
        ArrayList<Question> matchingQuestion = fileHandler.loadQuestions(fileName,matching);
        return matchingQuestion;
    }

}
