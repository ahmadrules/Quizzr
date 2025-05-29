package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String name;
    private String password;
    private String email;
    private String profilePicturePath;
    private List<Quiz> createdQuiz;
    private List<FlashCard> flashCards;
    private List<Quiz> history;
    private final String quizFilePath = "src/model/files/user_created_quizes.dat";
    private String programCode;
    private boolean isAdmin;

    public User(String name, String password, String email, String programCode, boolean isAdmin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdQuiz = new ArrayList<>();
        this.flashCards = new ArrayList<>();
        this.programCode = programCode;
        this.isAdmin = isAdmin;
        this.history = new ArrayList<>();
    }
    public boolean isAdmin() {
        return isAdmin;
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
    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public void addToCreatedQuiz(Quiz newQuiz){
        createdQuiz.add(newQuiz);
    }

    public void addToCreatedFlashcards(FlashCard newFlashcard){
        flashCards.add(newFlashcard);
    }

    public List<Quiz> getCreatedQuiz() {
        return createdQuiz;
    }

    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    public String[] userInfoToString(){
        String[] info = new String[3];
        info[0] = name;
        info[1] = email;
        info[2] = password;
        return info;
    }

    public void removeFlashCard(FlashCard flashCard){
        flashCards.remove(flashCard);
    }

    public void removeQuiz(Quiz quiz){
        createdQuiz.remove(quiz);
    }

    public void removeHistoryQuiz(Quiz quiz){
        history.remove(quiz);
    }
    public List<Quiz> getHistory() {
        return history;
    }

    public void addToHistory(Quiz quiz){
        history.add(quiz);
    }

    public void clearHistory() {
        List<Quiz> toRemove = history;
        history.removeAll(toRemove);
    }

    public void clearCreatedQuiz() {
        List<Quiz> toRemove = createdQuiz;
        createdQuiz.removeAll(toRemove);
    }

    public void loadCreatedQuizes(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(quizFilePath))) {
            while (true) {
                Quiz quiz = (Quiz) ois.readObject();
                createdQuiz.add(quiz);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());;
        }
    }

    public void setProfilePicPath(String selectedPicPath){
        this.profilePicturePath = selectedPicPath;
    }

    public String getProfilePicPath(){
        return profilePicturePath;
    }

}
