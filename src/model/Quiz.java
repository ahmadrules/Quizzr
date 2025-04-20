package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {
    private List<Question> questions;
    private List<String> userAnswers;
    private String name;
    private int result;

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.result = 0;
    }
    public void setResult(int result) {
        this.result = result;
    }
    public int getResult() {
        return result;
    }
    public int CalculateScore(){
        int sum = 0;

        return sum;
    }
    public int correctAnswer(){
        return 0;
    }
    public void addQuestion(Question question){
        questions.add(question);
    }
}
