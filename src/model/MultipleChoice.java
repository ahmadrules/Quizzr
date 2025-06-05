package model;

import java.util.Arrays;
import java.util.List;

/**
 * This class creates a multiple choice question object and saves information about it.
 * It inherits from the class Question
 * @author Lilas Beirakdar
 */
public class MultipleChoice extends Question {
    /**
     * Constructs a new multiple choice question
     * @param query the part representing the question
     * @param alternatives alternatives the list of answer choices
     * @param correctAnswer the correct answer among the alternatives
     * @param points the number of points awarded for a correct answer
     */
    public MultipleChoice(String query, List<String> alternatives , String correctAnswer, int points) {
        super(query,alternatives,points, correctAnswer);
    }

    /**
     * Method used to retrieve the correct answer of a specific question
     * @return correctAnswer a String representing the correct answer
     * @author Lilas Beirakdar
     */
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    /**
     * Retrieves a list of matches
     * in the case of multiple choice question it will return null
     * @return null value
     * @author Lilas Beirakdar
     */
    @Override
    public List<String> getMatches() {
        return null;
    }
    /**
     * Calculates the number of points the user will get after answering the question
     * In Multiple choice case it will return the number of points
     * @return number of points
     */
    @Override
    public int calculatePoints() {
        return getPoints();
    }

    /**
     * Checks users answer, returns true if the answer is equal to the correct answer
     * @param usersAnswer users answer as a String
     * @return boolean indicating if users answer is right or wrong
     * @author Lilas Beirakdar
     */
    @Override
    public boolean checkAnswer(String usersAnswer) {
        return usersAnswer.trim().equalsIgnoreCase(correctAnswer);
    }

    /**
     * Creates a multipleChoice question object from a semicolon-separated string.
     * @param line  the semicolon-separated string representing a multiple choice question
     * @return a new multiple choice question
     * @throws IllegalArgumentException if the input line does not contain at least 6 parts
     * @author Lilas Beirakdar
     */
    @Override
    public Question fromString(String line) {
        String [] parts = line.split(";");
        if (parts.length <6) {
            throw new IllegalArgumentException("Wrong number of parts: " + parts.length);
        }
        String question = parts[0].trim();
        List<String> alternatives = Arrays.asList(parts[1].trim(), parts[2].trim(), parts[3].trim());
        String correctAnswer = parts[4].trim();
        int points = Integer.parseInt(parts[5].trim());
        return new MultipleChoice(question, alternatives, correctAnswer,points);
    }
    /**
     * Returns a string representation of the multiple choice question.
     * @return a string describing the question and its alternatives
     * @author Lilas Beirakdar
     */
    public String toString() {
        return "Question: " + query + "\n" +
                "Alternatives: " + alternatives + "\n" ;
    }
}
