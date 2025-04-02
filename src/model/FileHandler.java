package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler{


    public FileHandler() {

    }
    public <T extends Question> List<T> loadQuestions(String fileName, T question) {
        List<T> questions = new ArrayList<>();
        StringBuilder questionBuilder = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    if (questionBuilder.length()> 0) {
                        T questionToAdd = (T) question.fromString(questionBuilder.toString());
                        questions.add(questionToAdd);
                        questionBuilder.setLength(0);
                    }
                }else {
                    questionBuilder.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return questions;
    }
}
