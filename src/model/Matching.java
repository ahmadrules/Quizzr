package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Matching extends Question implements Savable {
    private List<String> matches;
    private HashMap<String, Integer> correctMatches;

    public Matching(String question, List<String> statements,List<String> matches, HashMap<String,Integer> correctMatches ,int points) {
        super(question,statements,points);
        this.matches = matches;
        this.correctMatches = correctMatches;
    }

    @Override
    public boolean checkAnswer(String usersAnswer) {
        return false;
    }

    @Override
    public Question fromString(String line) {
        String[] parts = line.split(";");
        String question = parts[0];
        List<String> alternatives = Arrays.asList(parts[1].split(","));
        List<String> matches = Arrays.asList(parts[2].split(","));
        HashMap<String,Integer> correctMatches = new HashMap<>();
        String[] correctMatchs= parts[3].split(",");

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
