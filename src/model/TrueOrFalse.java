package model;

import java.util.Arrays;
import java.util.List;

public class TrueOrFalse extends Question {
    /**
     * Constructs a new true or false question
     * @param query the part representing the question
     * @param alternatives a list of alternatives
     * @param points the number of points awarded for a correct answer
     * @param correctAnswer the correct answer among the alternatives
     */
    public TrueOrFalse(String query, List<String> alternatives, int points, String correctAnswer) {
        super(query,alternatives, points, correctAnswer);
    }

    /**
     * Retrieves a list of matches
     * in the case of true or false question it will return null
     * @return null value
     * @author Lilas Beirakdar
     */
    @Override
    public List<String> getMatches() {
        return null;
    }
    /**
     * Calculates the number of points the user will get after answering the question
     * In true or false question case it will return the number of points
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
        return usersAnswer.trim().equalsIgnoreCase(correctAnswer.trim());
    }
    // parts [0] is the question, part[1] is the
    @Override
    public Question fromString(String line) {
        String [] parts = line.split(";");
        if (parts.length <4) {
            throw new IllegalArgumentException("Wrong question");
        }
        String question = parts[0].trim();
        List<String> alternatives = Arrays.asList(parts[1], parts[2]);
        int points = Integer.parseInt(parts[3]) ;
        String correctAnswer = parts[4].trim();
        return new TrueOrFalse(question, alternatives, points, correctAnswer);
    }
    @Override
    public String toString() {
        return "Question: " + query + "\n" +
                "Alternatives: " + alternatives + "\n" +
                "Points: " + points + "\n";
    }

}
