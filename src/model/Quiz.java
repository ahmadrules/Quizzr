package model;

import java.io.Serializable;
import java.util.*;

public class Quiz implements Serializable {
    private List<Question> questions;
    private Map<Question,String> userAnswers;// här finns det en link mellan vilken fråga ställs och svaret som användaren anger
    private String name;
    private int result;

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.result = 0;
        this.userAnswers = new LinkedHashMap<>();
    }
    public void setResult(int result) {
        this.result = result;
    }
    public int getResult() {
        return result;
    }
    // primärt 
    public int CalculateTestResult(){
        int total = 0;
        for (Map.Entry<Question,String> entry : userAnswers.entrySet()) {
            Question question = entry.getKey();
            String userAnswer = entry.getValue();
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

    public void addUserAnswer(Question question, String answer) {
        userAnswers.put(question,answer);
    }
    public int getTotalPoints(){
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }
    public int getNumberOfCorrectAnswers() {
        int correctAnswers = 0;
        for (Question question : questions) {
            if (question.checkAnswer("correct")) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }


}
