package model;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private ArrayList<Quiz> quizzes ;
    private ArrayList<FlashCard> flashCards;

    public Module(String name) {
        this.name = name;
        this.quizzes = new ArrayList<>();
        this.flashCards = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<Quiz> getQuizzes() {
        return quizzes;
    }
    public void setQuizzes(ArrayList<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public ArrayList<FlashCard> getFlashCards() {
        return flashCards;
    }

    public void addFlashCard(FlashCard flashCard) {
        this.flashCards.add(flashCard);
    }

}
