package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleChoice extends Question {

    public MultipleChoice(String question, List<String> alternatives , String correctAnswer, int points) {
        super(question,alternatives,points, correctAnswer);
    }
    public void addAlternative(String alternative) {
        this.alternatives.add(alternative);
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public List<String> getMatches() {
        return null;
    }

    @Override
    public int calculatePoints() {
        return getPoints();
    }

    // subString takes only the first letter from the correctAnswer
    // trim get rid of any extra space that might be in the start of users answer
    @Override
    public boolean checkAnswer(String usersAnswer) {
        return usersAnswer.trim().equalsIgnoreCase(correctAnswer);
    }
    // splits the String representing the question, alternativs and the correctAnswer
    //it returns an object of multiple choice question
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
    public String toString() {
        return "Question: " + question + "\n" +
                "Alternatives: " + alternatives + "\n" ;
    }
}
