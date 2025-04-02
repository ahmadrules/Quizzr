package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Question implements Savable, Serializable {
    protected String question;
    protected List<String> alternatives;
    protected int points;

    public Question(String question,List<String> alternatives,int points) {
        this.question = question;
        this.alternatives = alternatives;
        this.points = points;
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
