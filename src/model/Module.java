package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private List<FlashCard> flashCards;
    private final String quizFileName = "src/files/recent_quiz.dat";
    private Quiz currentQuiz;
    private final String matchingFileName = "matching_questions.txt";
    private final String multiChoiceFileName = "multiChoice_questions.txt";
    private final String trueOrFalseFileName = "trueFalse_questions.txt";


    public Module(String name) {
        this.name = name;
        this.flashCards = new ArrayList<>();
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

    private void generateMultipleChoiceQuiz(String fileName){
        currentQuiz = new Quiz("multiChoiceQuiz");
    }
    private void generateTrueOrFalseQuiz(String fileName){}
    private void generateMatchingQuiz(String fileName){}

}
