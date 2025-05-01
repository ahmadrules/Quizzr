package model;

import java.io.*;
import java.util.HashMap;
import java.util.List;

/**
 * An abstract class which represents a question object
 * It is inherited by all types of questions like MultipleChoice, Matching and TrueOrFalse questions
 * @author Lilas Beirakdar
 */
public abstract class Question implements Serializable {
    protected String query;
    protected List<String> alternatives;
    protected int points;
    protected String correctAnswer;

    /**
     * Constructs a question with the specified text, alternatives, and point value.
     * This constructor is intended to be used by the subclasses
     * @param query the part representing the question
     * @param alternatives alternatives the list of answer choices
     * @param points the number of points awarded for a correct answer
     * @author Lilas Beirakdar
     */
    public Question(String query,List<String> alternatives,int points) {
        this.query = query;
        this.alternatives = alternatives;
        this.points = points;
    }
    /**
     * Retrieves a list of questions alternatives
     * @return a list of alternatives related to the question
     * @author Lilas Beirakdar
     */
    public List<String> getAlternatives() {
        return alternatives;
    }

    /**
     * Constructs a question with the specified text, alternatives, and point value.
     * This constructor is intended to be used by the subclasses
     * @param query the part representing the question
     * @param alternatives alternatives the list of answer choices
     * @param points
     * @param correctAnswer the correct answer among the alternatives
     * @author Lilas Beirakdar
     */
    public Question(String query,List<String> alternatives,int points, String correctAnswer) {
        this.query = query;
        this.alternatives = alternatives;
        this.points = points;
        this.correctAnswer = correctAnswer;
    }
    public abstract List<String> getMatches();
    /**
     * Returns the part of the question that represents the question
     * @return question part
     * @author Lilas Beirakdar
     */
    public String getQuery() {
        return query;
    }

    /**
     * Returns the number representing points awarded after answering the question
     * @return
     * @author Lilas Beirakdar
     */
    public int getPoints() {
        return points;
    }
    /**
     * An abstract method that implemented by subclasses to calculate the points earned by the user
     * @return total number of points for a question
     */
    public abstract int calculatePoints();
    /**
     *An abstract method used to validate users answer
     * @param usersAnswer  a String representing the user answer in a specific format
     * @return boolean indicating if users answer is right or wrong
     * @author Lilas Beirakdar
     */
    public abstract boolean checkAnswer(String usersAnswer);
    /**
     * An abstract method used to create a question object from a String
     * @param line the semicolon-separated string
     * @return a question object
     * @author Lilas Beirakdar
     */
    public abstract Question fromString(String line);
    /**
     * Returns the correct answer of the question
     * @return the correct answer of the question as a String
     * @author Sara Sheikho
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
