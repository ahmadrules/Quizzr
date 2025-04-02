package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Savable, Serializable {
    private List<Question> questions;
    private List<String> userAnswers;
    private String MultipleChoiceFile;
    private String TrueOrFalseFile;
    private String matchingFile;

    public Quiz() {
        this.questions = new ArrayList<>();
    }
    public int CalculateScore(){
        int sum = 0;

        return sum;
    }
    public void generateGeneralQuiz(){

    }
    public void generateMultipleChoiceQuiz(){}
    public void generateTrueOrFalseQuiz(){}
    public void generateMatchingQuiz(){}
    public int correctAnswer(){
        return 0;
    }
    @Override
    public void saveToFile(String filename) {

    }

    @Override
    public Quiz loadFromFile(String filename) {
    return null;
    }
}
