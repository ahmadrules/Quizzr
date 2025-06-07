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

    /**
     * Constructs a new User with the specified details.
     *
     * @param name        the user's name
     * @param password    the user's password
     * @param email       the user's email address
     * @param programCode the program code associated with the user
     * @param isAdmin     true if the user has administrative privileges; false otherwise
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
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

    /**
     * Returns whether the user has administrative privileges.
     *
     * @return true if the user is an admin; false otherwise
     * @author Lilas Beirakdar
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Returns the user's name.
     *
     * @return the name of the user
     * @author Sara Sheikho
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the user's name.
     *
     * @param name the new name of the user
     * @author Sara Sheikho
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the user's password.
     *
     * @return the password of the user
     * @author Sara Sheikho
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     *
     * @param password the new password of the user.
     * @author Sara Sheikho
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email address of the user
     * @author Sara Sheikho
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the user's email address.
     *
     * @param email the new email address of the user
     * @author Sara Sheikho
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the program code associated with the user.
     *
     * @return the user's program code
     * @author Lilas Beirakdar
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * Adds a new quiz to the user's list of created quizzes.
     *
     * @param newQuiz the quiz to be added
     * @author Sara Sheikho
     */
    public void addToCreatedQuiz(Quiz newQuiz){
        createdQuiz.add(newQuiz);
    }

    /**
     * Adds a new flashcard to the user's list of created flashcards.
     *
     * @param newFlashcard the flashcard to be added
     * @author Sara Sheikho
     */
    public void addToCreatedFlashcards(FlashCard newFlashcard){
        flashCards.add(newFlashcard);
    }

    /**
     * Returns the list of quizzes created by the user.
     *
     * @return a list of quizzes
     * @author Sara Sheikho
     */
    public List<Quiz> getCreatedQuiz() {
        return createdQuiz;
    }

    /**
     * Returns the list of flashcards created by the user.
     *
     * @return a list of flashcards
     * @author Sara Sheikho
     */
    public List<FlashCard> getFlashCards() {
        return flashCards;
    }

    /**
     * Returns user's information as email,username and password
     * @return An array of Strings containing user's information
     * @author Ahmad Maarouf
     */
    public String[] userInfoToString(){
        String[] info = new String[3];
        info[0] = name;
        info[1] = email;
        info[2] = password;
        return info;
    }

    /**
     * Removes a quiz from the list of quizzes created by the user.
     *
     * @param quiz the quiz to be removed
     * @author Lilas Beirakdar
     */
    public void removeQuiz(Quiz quiz){
        createdQuiz.remove(quiz);
    }

    /**
     * Removes a quiz from the user's quiz history.
     *
     * @param quiz the quiz to be removed from history
     * @author Ahmad Maarouf
     */
    public void removeHistoryQuiz(Quiz quiz){
        history.remove(quiz);
    }

    /**
     * Returns the list of quizzes the user has completed or interacted with.
     *
     * @return a list of quizzes from the user's history
     * @author Lilas Beirakdar
     */
    public List<Quiz> getHistory() {
        return history;
    }

    /**
     * Adds a quiz to the user's history.
     *
     * @param quiz the quiz to be added to history
     * @author Lilas Beirakdar
     */
    public void addToHistory(Quiz quiz){
        history.add(quiz);
    }

    /**
     * Sets the file path of the user's profile picture.
     *
     * @param selectedPicPath the path to the profile picture
     * @author Sara Sheikho
     */
    public void setProfilePicPath(String selectedPicPath){
        this.profilePicturePath = selectedPicPath;
    }

    /**
     * Returns the file path of the user's profile picture.
     *
     * @return the profile picture path
     * @author Sara Sheikho
     */
    public String getProfilePicPath(){
        return profilePicturePath;
    }
}
