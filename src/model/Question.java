package model;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public abstract class Question implements Serializable {
    protected String query;
    protected List<String> alternatives;
    protected int points;
    protected String correctAnswer;

    public Question(String question,List<String> alternatives,int points) {
        this.query = question;
        this.alternatives = alternatives;
        this.points = points;
    }
    public List<String> getAlternatives() {
        return alternatives;
    }

    public Question(String query,List<String> alternatives,int points, String correctAnswer) {
        this.query = query;
        this.alternatives = alternatives;
        this.points = points;
        this.correctAnswer = correctAnswer;
    }
    public abstract List<String> getMatches();

    public String getQuery() {
        return query;
    }
    public int getPoints() {
        return points;
    }
    public abstract int calculatePoints();
    public abstract boolean checkAnswer(String usersAnswer);
    public abstract Question fromString(String line);
    public void saveToFile(String filename, Question question) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename,true))){
            outputStream.writeObject(question);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Question readFromFile(String filename) {
        Question question1 = null;
        File file = new File(filename);
        try(ObjectInputStream objectInputStream= new ObjectInputStream(new FileInputStream(file))) {
            question1= (Question) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return question1;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
