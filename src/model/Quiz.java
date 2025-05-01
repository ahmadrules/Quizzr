package model;

import java.io.Serializable;
import java.util.*;

public class Quiz implements Serializable {
    private List<Question> questions;
    private Map<Question,String> userAnswers; //här finns det en link mellan vilken fråga ställs och svaret som användaren anger
    private String name;
    private int result;
    private Module relatedModule;
    private Course relatedCourse;

    public Quiz(String name, Course relatedCourse, Module relatedModule) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.result = 0;
        this.userAnswers = new LinkedHashMap<>();
        this.relatedCourse = relatedCourse;
        this.relatedModule = relatedModule;
    }

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

    public Map<Question, String> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(Map<Question, String> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // primärt
    public int CalculateTestResult(){
        int total = 0;
        for (Map.Entry<Question,String> entry : userAnswers.entrySet()) {
            Question question = entry.getKey();
            String userAnswer = entry.getValue();
            if (question.checkAnswer(userAnswer)) {
                total+=question.calculatePoints();
            }
        }
        return total;
    }

    public List<Question> getQuestions() {
        return questions;
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
        for (Map.Entry<Question,String> entry : userAnswers.entrySet()) {
            if (entry.getKey().checkAnswer(entry.getValue())) {
                correctAnswers++;
            }
        }
        return correctAnswers;
    }

    public Module getRelatedModule() {
        return relatedModule;
    }

    public Course getRelatedCourse() {
        return relatedCourse;
    }
}
