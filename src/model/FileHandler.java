package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to read files
 * @author Lilas Beirakdar
 */
public class FileHandler implements Serializable {
    /**
     * a constructor to create an object of the filehandler class
     * @author Lilas Beirakdar
     */
    public FileHandler() {
    }

    /**
     * Method used to read a String question from a file and change it into a Question object
     * It can read all types of Strings and return a list of question objects
     * @param fileName the name of the file containing the questions
     * @param question the type of the question we want to read it can be a TrueOrFalse Question,
     * MultipleChoice or a Matching question
     * @return List of Question objects
     * @param <T> generic type representing any class inheriting from the class Question
     * @author Lilas Beirakdar
     */
    public <T extends Question> ArrayList<T> loadQuestions(String fileName, T question) {
        List<T> questions = new ArrayList<>();
        StringBuilder questionBuilder = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                if (line.trim().isEmpty()) {
                    if (questionBuilder.length()> 0) {
                        String questionString = questionBuilder.toString();
                        T questionToAdd = (T) question.fromString(questionString);
                        if (!questionString.isEmpty()) {
                            questions.add(questionToAdd);}
                        questionBuilder.setLength(0);
                    }
                }else {
                    if (questionBuilder.length() > 10000) {
                        System.out.println("Question builder exceeds 10000 characters");
                    }
                    questionBuilder.append(line).append("\n");
                }
                line = br.readLine();
            }
            if (questionBuilder.length() > 0) {
                String questionString = questionBuilder.toString();
                T questionToAdd = (T) question.fromString(questionString);
                if (!questionString.isEmpty()) {
                    questions.add(questionToAdd);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (ArrayList<T>) questions;
    }

}
