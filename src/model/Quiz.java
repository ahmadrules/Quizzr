package model;

import java.io.Serializable;
import java.util.*;

public class Quiz implements Serializable {
    private List<Question> questions;
    private Map<Question,String> userAnswers;
    private String name;
    private int result;
    private Module relatedModule;
    private Course relatedCourse;
    private long timerSeconds;
    private boolean timer;
    private String userName;
    private String date;
    private boolean isDone;
    /**
     * Constructs a quiz object
     * @param name name of the quiz
     * @param relatedCourse the course that the created quiz will be related to
     * @param relatedModule the Module that the created quiz will be related to
     */
    public Quiz(String name, Course relatedCourse, Module relatedModule) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.result = 0;
        this.userAnswers = new LinkedHashMap<>();
        this.relatedCourse = relatedCourse;
        this.relatedModule = relatedModule;
        this.timer = false;
        this.isDone = false;
    }

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.result = 0;
        this.userAnswers = new LinkedHashMap<>();
    }
    public void setDone(boolean done){
        isDone = done;
    }
    public boolean getDone(){
        return isDone;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
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

    /**
     * Calculates the total number of points earned by the user
     * It compares users answer with the right answer
     * and add points to the total if the answer is right
     * @return the total points earned by the user
     * @author Lilas Beirakdar
     */
    public int CalculateTestResult(){
        int total = 0;
        for (Map.Entry<Question,String> entry : userAnswers.entrySet()) {
            Question question = entry.getKey();
            String userAnswer = entry.getValue();
            if (question.checkAnswer(userAnswer)) {
                total+=question.calculatePoints();
            }
        }
        result = total;
        return total;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    /**
     * Adds the given user answer with it's related question to user answers hashmap
     * @param question The question that the user has answered
     * @param answer user's answer as a String
     * @author Lilas Beirakdar
     */
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
    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    public long getTimerSeconds() {
        return timerSeconds;
    }

    public void setTimerSeconds(long timerSeconds) {
        this.timerSeconds = timerSeconds;
    }
}
