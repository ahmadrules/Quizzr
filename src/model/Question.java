package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Question {
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
    public void saveToFile(String filename, MultipleChoice multipleChoice) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename,true))){
            outputStream.writeObject(multipleChoice);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String toString() {
        return  getQuestion()+getCorrectAnswer();
    }
    public Question(){}
    public Question readFromFile(String filename) {
        Question question1 = null;
        File file = new File(filename);
        try(ObjectInputStream objectInputStream= new ObjectInputStream(new FileInputStream(file))) {
            question1= (Question) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());;
        }
        ;return question1;
    }
    public String getQuestion() {
        return question;
    }
    public int getPoints() {
        return points;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public abstract boolean checkAnswer(String usersAnswer);
    public abstract Question fromString(String line);
}
