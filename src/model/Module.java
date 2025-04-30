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

    public Module(String name, String coursePackageName) {
        this.name = name;
        this.flashCards = new ArrayList<>();
        createPackage(coursePackageName);
        createFiles();
    }

    public String getName() {
        return name;
    }
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
        FlashCard flashCard=new FlashCard("","");
        this.flashCards= flashCard.loadFromFile(filename);
        return flashCards;
    }
    public void saveFlashCardsToFile(String filename) {
        FlashCard flashCard=new FlashCard("","");
        flashCard.saveToFile(filename, flashCards);
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

    public void generateQuiz(String quizType, String filePath, int numberOfQuestions) {
        switch (quizType){
            case "Matching":
                ArrayList<Question> MatchingQuiz= generateMatchingQuiz(numberOfQuestions);
                break;
            case "TrueOrFalse":
                generateTrueOrFalseQuiz(numberOfQuestions);
                break;
            case "MultipleChoice":
                generateMultipleChoiceQuiz(numberOfQuestions);
                break;
            default:
                //generateGeneralQuiz();
                break;
        }
    }
    /**
     * Generates a quiz that consists of all types of questions
     * @param multipleChoiceFile the filepath of the file that contains multiple choice questions
     * @param matchingFilePath the filepath of the file that contains matching questions
     * @param trueOrFalseFilePath the filepath of the file that contains true or false questions
     * @param numberOfQuestions the total number of questions the quiz will consist of
     * @return a List of questions
     */
    public ArrayList<Question> generateGeneralQuiz(String multipleChoiceFile,String matchingFilePath, String trueOrFalseFilePath , int numberOfQuestions) {
        ArrayList<Question> allQuestions= new ArrayList<>();
        ArrayList<Question> mc= generateMultipleChoiceQuiz(numberOfQuestions/3);
        ArrayList<Question> matching= generateMatchingQuiz(numberOfQuestions/3);
        ArrayList<Question> tf= generateTrueOrFalseQuiz(numberOfQuestions/3);
        allQuestions.addAll(mc);
        allQuestions.addAll(matching);
        allQuestions.addAll(tf);
        Collections.shuffle(allQuestions);
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
    private void createFiles(){
        this.multiChoiceFile = new File(directory, multiChoiceFileName);
        this.trueOrFalseFile = new File(directory, trueOrFalseFileName);
        this.matchingFile = new File(directory, matchingFileName);
        this.flashCardFile = new File(directory, flashCardFileName);
        //this.quizFile = new File(directory, quizFileName);

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
}
