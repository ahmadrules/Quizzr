package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Question implements Savable, Serializable {
    protected String question;
    protected List<String> alternatives;
    protected int points;
    protected String correctAnswer;

    public Question(String question,List<String> alternatives,int points) {
        this.question = question;
        this.alternatives = alternatives;
        this.points = points;
    }

    public Question(String question,List<String> alternatives,int points, String correctAnswer) {
        this.question = question;
        this.alternatives = alternatives;
        this.points = points;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }
    public int getPoints() {
        return points;
    }
    public abstract boolean checkAnswer(String usersAnswer);
    public abstract Question fromString(String line);
}
