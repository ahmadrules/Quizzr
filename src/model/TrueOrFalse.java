package model;

import java.util.Arrays;
import java.util.List;

public class TrueOrFalse extends Question {
    private String correctAnswer;

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
        //boolean correctAnswer = Boolean.parseBoolean(parts[3].trim());
        int points = Integer.parseInt(parts[4]);
        return new TrueOrFalse(question, alternatives, points, correctAnswer);
    }
    @Override
    public void saveToFile(String filename) {

    }

    @Override
    public Object loadFromFile(String filename) {
        return null;
    }
}
