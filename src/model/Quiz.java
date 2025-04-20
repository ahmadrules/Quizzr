package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz implements Serializable {
    private List<Question> questions;
    private Map<Question,String> userAnswers;// här finns det en link mellan vilken fråga ställs och svaret som användaren anger
    private String name;
    private int result;

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.result = 0;
        this.userAnswers = new HashMap<>();
    }
    public void setResult(int result) {
        this.result = result;
    }
    public int getResult() {
        return result;
    }
    public int CalculateTestResult(){
        int total = 0;
        for (Question question : questions) {
            String userAnswer = userAnswers.get(question);
            if (question.checkAnswer(userAnswer)) {
                total+=question.getPoints();
            }
        }
        return total;
    }
    public int correctAnswer(){
        return 0;
    }
    public void addQuestion(Question question){
        questions.add(question);
    }
}
