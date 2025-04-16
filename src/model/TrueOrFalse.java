package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrueOrFalse extends Question {

    public TrueOrFalse(String question, List<String> alternatives, int points, String correctAnswer) {
        super(question,alternatives, points, correctAnswer);
    }

    @Override
    public boolean checkAnswer(String usersAnswer) {
        return usersAnswer.trim().equalsIgnoreCase(String.valueOf(correctAnswer));

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
        return "Question: " + question + "\n" +
                "Alternatives: " + alternatives + "\n" +
                "Correct Answer: " + correctAnswer + "\n" +
                "Points: " + points + "\n";
    }


}
