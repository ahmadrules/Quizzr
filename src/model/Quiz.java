package model;

import java.io.Serializable;
import java.util.*;

/**
 * Represents a quiz containing a list of questions, user answers, and metadata.
 * Implements Serializable for saving and loading quiz data.
 * @author Lilas Beirakdar
 * @author Sara Sheikho
 * @author Ahmad Maarouf
 */
public class Quiz implements Serializable {
    private List<Question> questions;
    private Map<Question,String> userAnswers;
    private String name;
    private Module relatedModule;
    private Course relatedCourse;
    private long timerSeconds;
    private String date;
    private boolean isDone;
    /**
     * Constructs a quiz object
     * @param name name of the quiz
     * @param relatedCourse the course that the created quiz will be related to
     * @param relatedModule the Module that the created quiz will be related to
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
    public Quiz(String name, Course relatedCourse, Module relatedModule) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.userAnswers = new LinkedHashMap<>();
        this.relatedCourse = relatedCourse;
        this.relatedModule = relatedModule;
        this.isDone = false;
    }

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
        this.userAnswers = new LinkedHashMap<>();
    }

    /**
     * Sets the quiz as done
     * @param done boolean indicating that the quiz is done
     * @author Lilas Beirakdar
     */
    public void setDone(boolean done){
        isDone = done;
    }

    /**
     * Returns a boolean value indicating that the quiz is totally done
     * @return boolean value indicating that the quiz is done
     * @author Lilas Beirakdar
     */
    public boolean getDone(){
        return isDone;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    /**
     * Retrieves the user's submitted answers for a quiz.
     *
     * @return A map containing {@link Question} objects as keys and the user's answers as values.
     * @author Sara Sheikho
     */
    public Map<Question, String> getUserAnswers() {
        return userAnswers;
    }

    /**
     * Sets the map of user's answers for a quiz.
     *
     * @param userAnswers A map with {@link Question} as keys and the user's answers as values.
     * @author Sara Sheikho
     */
    public void setUserAnswers(Map<Question, String> userAnswers) {
        this.userAnswers = userAnswers;
    }

    /**
     * Sets the list of questions in the quiz.
     *
     * @param questions A list of {@link Question} objects to be included in the quiz.
     * @author Sara Sheikho
     */
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Sets the name of the quiz.
     *
     * @param name The name to assign to the quiz.
     * @author Sara Sheikho
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return quizzes name as a String
     * @return quiz name as a String
     * @author Ahmad Maarouf
     */
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
        return total;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    /**
     * Adds a single question to the quiz.
     *
     * @param question The {@link Question} object to be added.
     * @author Sara Sheikho
     */
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

    /**
     * Returns the total number of points earned when answering all the question correctly
     * @return total number of pints of a quiz as an integer
     * @author Lilas Beirakdar
     */
    public int getTotalPoints(){
        int total = 0;
        for (Question question : questions) {
            total += question.getPoints();
        }
        return total;
    }

    /**
     * Gets the module related to this object.
     *
     * @return The {@link Module} associated with this quiz, or null if none is set.
     * @author Sara Sheikho
     */
    public Module getRelatedModule() {
        return relatedModule;
    }

    /**
     * Gets the course related to this object.
     *
     * @return The {@link Course} associated with this quiz, or null if none is set.
     * @author Sara Sheikho
     */
    public Course getRelatedCourse() {
        return relatedCourse;
    }

    public long getTimerSeconds() {
        return timerSeconds;
    }

    public void setTimerSeconds(long timerSeconds) {
        this.timerSeconds = timerSeconds;
    }
}
