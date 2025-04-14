package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler{

    public FileHandler() {

    }
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
