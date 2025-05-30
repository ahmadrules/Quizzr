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
    private final String quizFileName = "quiz.dat";
    private File multiChoiceFile;
    private File trueOrFalseFile;
    private File matchingFile;
    private File flashCardFile;
    private File quizFile;
    private File directory;
    FileHandler fileHandler = new FileHandler();
    private ArrayList<Quiz> quizList;

    public Module(String name, String coursePackageName) {
        this.name = name;
        this.flashCards = new ArrayList<>();
        this.quizList = new ArrayList<>();
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
    public List<FlashCard> getFlashCards() {
        return flashCards;
    }
    public void addFlashCard(FlashCard flashCard) {
        this.flashCards.add(flashCard);
    }
    public ArrayList<FlashCard> loadFlashCardsFromFile(String filename) {
        FileHandler fileHandler = new FileHandler();
        this.flashCards= fileHandler.loadFlashcardsFromFile(filename);
        return flashCards;
    }
    public void saveFlashCardsToFile(String filename) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.saveFlashcardToFile(filename, flashCards);
    }

    /**
     *
     * @param selectedCourse
     * @param selectedModule
     */
    public void saveQuizToFile(Course selectedCourse, Module selectedModule, Quiz selectedQuiz) {
        if (quizList==null){
        quizList = new ArrayList<>();
        }
        quizList.add(selectedQuiz);
        fileHandler.saveQuizToFile(quizFile.getPath(),quizList);
    }
    public ArrayList<Quiz> getQuizList() {
        this.quizList=fileHandler.loadQuizFromFile(quizFile.getPath());
        return quizList;
    }


    public void saveQuiz(Quiz quiz){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(quizFileName))){
            oos.writeObject(quiz);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadQuizFromFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(quizFileName))){
            Quiz readQuiz = (Quiz) ois.readObject();
            currentQuiz = readQuiz;
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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

    public ArrayList<Question> generateGeneralQuiz(Course selectedCourse, Module selectedModule, int numberOfQuestions) {
        //Will be called by controller
        currentQuiz = new Quiz("General quiz", selectedCourse, selectedModule);
        ArrayList<Question> allQuestions= new ArrayList<>();
        ArrayList<Question> mc= generateMultipleChoiceQuiz(selectedCourse, selectedModule, numberOfQuestions/3);
        ArrayList<Question> matching= generateMatchingQuiz(selectedCourse, selectedModule, numberOfQuestions/3);
        ArrayList<Question> tf= generateTrueOrFalseQuiz(selectedCourse, selectedModule, numberOfQuestions/3);
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

    public ArrayList<Question> generateMultipleChoiceQuiz(Course selectedCourse,Module selectedModule, int numberOfQuestions) {
        //Will be called by controller
        currentQuiz = new Quiz("multiChoiceQuiz", selectedCourse, selectedModule);
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

    public ArrayList<Question> generateTrueOrFalseQuiz(Course selectedCourse, Module selectedModule, int numberOfQuestions){
        //Will be called by controller
        currentQuiz = new Quiz("trueOrFalseQuiz", selectedCourse, selectedModule);
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
     * Generates a matching type {@link Quiz} for a given {@link Course} and {@link Module}.
     * <p>
     * This method initializes a new quiz of type "matching", loads all matching-type {@link Question}
     * from the matching file and sets the loaded questions into the current quiz.
     * </p>
     *
     * @param selectedCourse the course to which the quiz is related
     * @param selectedModule the module to which the quiz is related
     * @param numberOfQuestions the number of questions to include in the quiz
     * @return a randomly selected list of questions {@link Question}for the matching quiz
     * @author Sara Sheikho
     **/
    public ArrayList<Question> generateMatchingQuiz(Course selectedCourse, Module selectedModule, int numberOfQuestions){
        //Will be called by controller
        currentQuiz = new Quiz("matching", selectedCourse, selectedModule);
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
        this.flashCardFile = new File(directory, flashCardFileName);

        try {
            multiChoiceFile.createNewFile();
            trueOrFalseFile.createNewFile();
            matchingFile.createNewFile();
            flashCardFile.createNewFile();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public void saveMultiChoiceQuestionToFile(MultipleChoice multiChoiceQuestion){
        fileHandler.saveMultipleChoiceToFile(multiChoiceFile.getPath(),multiChoiceQuestion);
    }

    public void saveTrueOrFalseQuestionToFile(TrueOrFalse trueOrFalseQuestion){
        fileHandler.saveTrueOrFalseQuestionToFile(trueOrFalseFile.getPath(),trueOrFalseQuestion);
    }

    public void saveMatchingQuestionToFile(Matching matching){
        fileHandler.saveMatchingQuestionToFile(matchingFile.getPath(),matching);
    }

    public void removePackage(){
        if(directory.exists()) {
            deleteDirectory(directory);
        }
    }

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
}
