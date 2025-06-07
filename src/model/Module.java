package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Module implements Serializable{
    private String name;
    private ArrayList<FlashCard> flashCards;
    private Quiz currentQuiz;
    private final String matchingFileName = "matching_questions.txt";
    private final String multiChoiceFileName = "multiChoice_questions.txt";
    private final String trueOrFalseFileName = "trueFalse_questions.txt";
    private final String flashCardFileName = "flashcards.dat";
    private File multiChoiceFile;
    private File trueOrFalseFile;
    private File matchingFile;
    private File directory;
    FileHandler fileHandler = new FileHandler();

    public Module(String name, String coursePackageName) {
        this.name = name;
        this.flashCards = new ArrayList<>();
        createPackage(coursePackageName);
        createFiles();
    }

    /**
     * Returns Modules name
     * @return A String representing module's name
     * @author Lilas Beirakdar
     */
    public String getName() {
        return name;
    }

    /**
     * Sets module's name
     * @param name module's name
     * @author Lilas Beirakdar
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Generates a quiz that consists of all types of questions
     *
     * @param numberOfQuestions the total number of questions the quiz will consist of
     * @return a List of questions
     * @author Lilas  Beirakdar
     */
    public ArrayList<Question> generateGeneralQuiz(int numberOfQuestions) {
        currentQuiz = new Quiz("General quiz");
        ArrayList<Question> allQuestions= new ArrayList<>();
        ArrayList<Question> mc= generateMultipleChoiceQuiz(numberOfQuestions/3);
        ArrayList<Question> matching= generateMatchingQuiz(numberOfQuestions/3);
        ArrayList<Question> tf= generateTrueOrFalseQuiz(numberOfQuestions/3);
        allQuestions.addAll(mc);
        allQuestions.addAll(matching);
        allQuestions.addAll(tf);
        Collections.shuffle(allQuestions);
        currentQuiz.setQuestions(allQuestions);
        return allQuestions;
    }

    /**
     * Generate a quiz that consists of multiple choice questions
     * @param numberOfQuestions the number of questions to include in the generated quiz
     * @return  a List of questions
     * @author Lilas Beirakdar
     */
    public ArrayList<Question> generateMultipleChoiceQuiz(int numberOfQuestions) {
        currentQuiz = new Quiz("multiChoiceQuiz");
        MultipleChoice multipleChoice= new MultipleChoice("", Collections.singletonList(""),"",0);
        ArrayList<Question> multipleChoiceQuestion = fileHandler.loadQuestions(multiChoiceFile.getPath(),multipleChoice);
        currentQuiz.setQuestions(multipleChoiceQuestion);
        return generateRandomQuiz(multipleChoiceQuestion,numberOfQuestions);
    }

    /**
     * Generate a quiz that consists of true or false questions
     * @param numberOfQuestions the number of questions to include in the generated quiz
     * @return  a List of questions
     * @author Lilas Beirakdar
     */
    public ArrayList<Question> generateTrueOrFalseQuiz(int numberOfQuestions){
        currentQuiz = new Quiz("trueOrFalseQuiz");
        TrueOrFalse trueOrFalse= new TrueOrFalse("", Collections.singletonList(""),0,"");
        ArrayList<Question> trueOrFalseQuestion = fileHandler.loadQuestions(trueOrFalseFile.getPath(),trueOrFalse);
        currentQuiz.setQuestions(trueOrFalseQuestion);
        return generateRandomQuiz(trueOrFalseQuestion,numberOfQuestions);
    }

    /**
     * Generate a quiz that consists of matching questions
     * @param numberOfQuestions the number of questions to include in the generated quiz
     * @return  a List of questions
     * @author Lilas Beirakdar
     */
    public ArrayList<Question> generateMatchingQuiz(int numberOfQuestions){
        currentQuiz = new Quiz("matching");
        HashMap<String,Integer> matches=new HashMap<>();
        Matching matching= new Matching("", Collections.singletonList(""), Collections.singletonList(""),0 ,matches);
        ArrayList<Question> matchingQuestion = fileHandler.loadQuestions(matchingFile.getPath(),matching);
        currentQuiz.setQuestions(matchingQuestion);
        return generateRandomQuiz(matchingQuestion,numberOfQuestions);
    }

    /**
     *Generates a randomized quiz by shuffling the provided list of questions and selecting a subset.
     * @param questions a list of questions to select from
     * @param numberOfQuestions the total number of questions to include in the generated quiz
     * @return a randomly selected list of questions with a maximum size of numberOfQuestions
     * @author Lilas Beirakdar
     */
    private ArrayList<Question> generateRandomQuiz(ArrayList<Question> questions, int numberOfQuestions){
        Collections.shuffle(questions);
        return new ArrayList<>(questions.subList(0, Math.min(numberOfQuestions, questions.size())));
    }
    public void createPackage(String coursePackageName){
        directory = new File("src/model/files/" + coursePackageName + "/" + name.trim());
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
        }
    }

    /**
     * Creates the necessary data files for questions for every module.
     * <p>
     * This method initializes and attempts to create physical files on disk for
     * multiple-choice questions, true/false questions, matching questions, and flashcards.
     * </p>
     * @author Sara Sheikho
     **/
    private void createFiles(){
        this.multiChoiceFile = new File(directory, multiChoiceFileName);
        this.trueOrFalseFile = new File(directory, trueOrFalseFileName);
        this.matchingFile = new File(directory, matchingFileName);

        try {
            multiChoiceFile.createNewFile();
            trueOrFalseFile.createNewFile();
            matchingFile.createNewFile();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Saves a multiple choice question to the file
     * @param multiChoiceQuestion the multipleChoice question to be saved to file as an object
     * @author Lilas Beirakdar
     */
    public void saveMultiChoiceQuestionToFile(MultipleChoice multiChoiceQuestion){
        fileHandler.saveMultipleChoiceToFile(multiChoiceFile.getPath(),multiChoiceQuestion);
    }

    /**
     * Saves a true or false question to the file
     * @param trueOrFalseQuestion the true or false question to be saved to the file as an object
     * @author Lilas Beirakdar
     */
    public void saveTrueOrFalseQuestionToFile(TrueOrFalse trueOrFalseQuestion){
        fileHandler.saveTrueOrFalseQuestionToFile(trueOrFalseFile.getPath(),trueOrFalseQuestion);
    }

    /**
     * Saves a matching question to the file
     * @param matching the matching question to be saved to the file as an object
     * @author Lilas Beirakdar
     */
    public void saveMatchingQuestionToFile(Matching matching){
        fileHandler.saveMatchingQuestionToFile(matchingFile.getPath(),matching);
    }

    /**
     * Removes the entire module directory from the file system.
     * <p>
     * This method checks if the module's directory exists and deletes it
     * along with all of its contents using a recursive method.
     * </p>
     * @author Sara Sheikho
     */
    public void removePackage(){
        if(directory.exists()) {
            deleteDirectory(directory);
        }
    }

    /**
     * Recursively deletes a directory and all files and subdirectories within it.
     *
     * @param dir The directory to be deleted.
     * @author Sara Sheikho
     */
    public void deleteDirectory(File dir){
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        dir.delete();
    }

    /**
     * Updates the module's directory location based on a new course package name.
     * <p>
     * The method moves the directory to the new course folder, then updates
     * all internal file paths (question files) accordingly.
     * </p>
     *
     * @param newCoursePackageName The new parent directory for this module.
     * @author Sara Sheikho
     */
    public void updateDirectory(String newCoursePackageName) {
        File oldDir = this.directory;
        File newDir = new File("src/model/files/" + newCoursePackageName + "/" + name.trim());

        if (oldDir.exists()) {
            oldDir.renameTo(newDir);
        }

        this.directory = newDir;
        this.multiChoiceFile = new File(directory, multiChoiceFileName);
        this.trueOrFalseFile = new File(directory, trueOrFalseFileName);
        this.matchingFile = new File(directory, matchingFileName);
    }

    /**
     * Renames the module's directory using the module's current name and course package name.
     * <p>
     * This method is used when the module is renamed and its directory must reflect
     * the new name while preserving existing files.
     * </p>
     *
     * @param coursePackageName The course folder under which this module resides.
     * @author Sara Sheikho
     */
    public void renameModuleDirectory(String coursePackageName) {
        File newDir = new File("src/model/files/" + coursePackageName + "/" + name.trim());
        if (directory.exists()) {
            directory.renameTo(newDir);
        }

        this.directory = newDir;
        this.multiChoiceFile = new File(directory, multiChoiceFileName);
        this.trueOrFalseFile = new File(directory, trueOrFalseFileName);
        this.matchingFile = new File(directory, matchingFileName);
    }

}
