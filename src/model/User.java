package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private String email;
    private List<Quiz> createdQuiz;
    private List<FlashCard> flashCards;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdQuiz = new ArrayList<>();
        this.flashCards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addToCreatedQuiz(Quiz newQuiz){
        createdQuiz.add(newQuiz);
    }
    public void addToCreatedFlashcards(FlashCard newFlashcard){
        flashCards.add(newFlashcard);
    }
}
