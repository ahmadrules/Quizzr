package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Matching extends Question implements Savable {
    private List<String> matches;
    private HashMap<String, Integer> correctMatches;

    public Matching(String question, List<String> statements,List<String> matches, HashMap<String,Integer> correctMatches ,int points) {
        super(question, statements, points);
        this.matches = matches;
        this.correctMatches = correctMatches;
    }
    public HashMap<String, Integer> getCorrectMatches() {
        return correctMatches;
    }
    public void setCorrectMatches(HashMap<String, Integer> correctMatches) {
        this.correctMatches = correctMatches;
    }
    public ArrayList<String> getMatches() {
        return (ArrayList<String>) matches;
    }

    @Override
    public boolean checkAnswer(String usersAnswer) {
        return false;
    }

    /**
     * This method is used to convert the question from the file into an object of matching question
     * @param line
     * @return
     */
    // split the line which is representing the question into an array consisting of parts
    // Each part is an element in the array
    // part 0 represents the question
    // part 1 represents a list of alternatives split by , [,,]
    // part 2 represents a list of matches split by , [,,]
    // part 3 is an hashMap representing the correct solution as A:3, B:4, split by ,
    @Override
    public Question fromString(String line) {
        String[] parts = line.split(";");
        String question = parts[0];
        List<String> alternatives = Arrays.asList(parts[1].split(","));
        List<String> matches = Arrays.asList(parts[2].split(","));
        HashMap<String,Integer> correctMatches = new HashMap<>();
        String[] correctPairs= parts[3].split(",");
        for(String correctPair : correctPairs){
            String [] key = correctPair.split(":");
            correctMatches.put(key[0],Integer.parseInt(key[1]));
        }
        int points = Integer.parseInt(parts[4]);
        return new Matching(question,alternatives,matches,correctMatches,points);
    }

    @Override
    public void saveToFile(String filename) {

    }
    @Override
    public Object loadFromFile(String filename) {
        return null;
    }
}
