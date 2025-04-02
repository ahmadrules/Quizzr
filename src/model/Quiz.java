package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {
    private List<Question> questions;
    private List<String> userAnswers;
    private String name;

    public Quiz(String name) {
        this.questions = new ArrayList<>();
        this.name = name;
    }
    public int CalculateScore(){
        int sum = 0;

        return sum;
    }
    public void addQuestion(Question question){
        questions.add(question);
    }

    public String getName() {
        return name;
    }
}
