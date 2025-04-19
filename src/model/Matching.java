package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Matching extends Question {
    private List<String> matches;
    private HashMap<String, Integer> correctMatches;

    public Matching(String question, List<String> statements,List<String> matches, int points,HashMap<String,Integer> correctMatches) {
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
    @Override
    public String toString() {
        String alternativesStr = String.join(",",getAlternatives());
        String matchesStr = String.join(",", matches);

        List<String> correctList = new ArrayList<>();
        for (String key : correctMatches.keySet()) {
            correctList.add(key + ":" + correctMatches.get(key));
        }
       // String correctMatchesStr = String.join(",", correctList);

        return getQuestion() + "\n" + alternativesStr + "\n" + matchesStr + "\n" + getPoints()+"\n";
    }

}
