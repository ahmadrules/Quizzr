package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

    /**
     * Class used to create an object of Matching question
     * @author Lilas Beirakdar
     */
public class Matching extends Question {
    private List<String> matches;
    private HashMap<String, Integer> correctMatches;
    private int numberOfWrongMatches;

    /**
     * Constructs a new matching question
     * @param query the part representing the question
     * @param statements a list of statements to be matched
     * @param matches a list of statements or words that matches the statements
     * @param points the number of points awarded for a correct answer
     * @param correctMatches a Hashmap of the correct matches between statements and matches
     * @author Lilas Beirakdar
     */
    public Matching(String query, List<String> statements,List<String> matches, int points,HashMap<String,Integer> correctMatches) {
        super(query, statements, points);
        this.matches = matches;
        this.correctMatches = correctMatches;
        this.numberOfWrongMatches = 0;
    }
    /**
     * Returns the correct matches for the matching question.
     * @return a Map where the key represents the statement and the values is the matching statement
     * @author Lilas Beirakdar
     */
    public HashMap<String, Integer> getCorrectMatches() {
        return correctMatches;
    }

    /**
     * Returns the list of matches
     * @return a list of matching statements
     * @author Lilas Beirakdar
     */
    public List<String> getMatches() {
        return  matches;
    }
    /**
     *Calculates the score for the matching question based on the number of incorrect matches
     * @return the calculated score:
     * for each wrong answer the user will lose 1/3 of the total mark
     * @author Lilas Beirakdar
     */
    @Override
    public int calculatePoints() {
        if (numberOfWrongMatches == 0) {
            return getPoints();
        }else if (numberOfWrongMatches == 1) {
            return 2*getPoints()/3;
        }else if (numberOfWrongMatches == 2) {
            return getPoints()/3;
        }
        return 0;
    }

    /**
     *Sets the number of incorrect matches made by the user
     * @param numberOfWrongMatches numberOfWrongMatches the number of incorrect matches
     * @author Lilas Beirakdar
     */
    public void setNumberOfWrongMatches(int numberOfWrongMatches) {
        this.numberOfWrongMatches = numberOfWrongMatches;
    }
    /**
     * Checks the user's answer compared to the correct answer
     * @param usersAnswer  the user's submitted answer
     * @return boolean values, true if the user made at least one right matching
     * returns false if all user answers is wrong
     * @author Lilas Beirakdar
     */
    @Override
    public boolean checkAnswer(String usersAnswer) {
        HashMap<String, Integer> userAnswers = new HashMap<>();
        String [] parts = usersAnswer.split(",");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if(keyValue.length == 2) {
                userAnswers.put(keyValue[0].trim(), Integer.parseInt(keyValue[1].trim()));
            }
        }
        int totalCorrectAnswers = 0;

        for (String key : correctMatches.keySet()) {
            if (userAnswers.containsKey(key) && userAnswers.get(key).equals(correctMatches.get(key))) {
                totalCorrectAnswers ++;
            }
        }

        int numberOfWrongMatches=correctMatches.size()-totalCorrectAnswers;
        setNumberOfWrongMatches(numberOfWrongMatches);
        if (numberOfWrongMatches == 3){
            return false;
        }else {
            return true;
        }
    }
    /**
     * This method is used to convert the question from the file into an object of matching question
     * part 0 represents the question
     * part 1 represents a list of alternatives split by , [,,]
     * part 2 represents a list of matches split by , [,,]
     * part 4 represents the points earned after answering the question
     * part 4 is an hashMap representing the correct solution as A:3, B:4, split by ,
     * @param line a String expected to follow a specific format, with parts separated by semicolons
     *
     * @return a new object of matching question
     * @author Lilas Beirakdar
     */
    @Override
    public Question fromString(String line) {
        String[] parts = line.split(";");
        String question = parts[0].trim();
        List<String> alternatives = Arrays.asList(parts[1].split(","));
        List<String> matches = Arrays.asList(parts[2].split(","));
        int points = Integer.parseInt(parts[3].trim());
        HashMap<String,Integer> correctMatches = new HashMap<>();
        String[] correctPairs= parts[4].split(",");
        for(String correctPair : correctPairs){
            String [] key = correctPair.split(":");
            correctMatches.put(key[0].trim(),Integer.parseInt(key[1].trim()));
        }
        return new Matching(question,alternatives,matches,points,correctMatches);
    }
    /**
     * Returns a String representation of a matching question
     * @return  a string describing the question, its alternatives, matches and its points
     * @author Lilas Beirakdar
     */
    @Override
    public String toString() {
        String alternativesStr = String.join(",",getAlternatives());
        String matchesStr = String.join(",", matches);

        List<String> correctList = new ArrayList<>();
        for (String key : correctMatches.keySet()) {
            correctList.add(key + ":" + correctMatches.get(key));
        }

        return getQuery() + "\n" + alternativesStr + "\n" + matchesStr + "\n" + getPoints()+"\n";
    }

}
