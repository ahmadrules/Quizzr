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
    private final String flashCardFileName = "flashCards.dat";
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
                ArrayList<Question> MatchingQuiz= generateMatchingQuiz(filePath+matchingFileName, numberOfQuestions);
                break;
            case "TrueOrFalse":
                generateTrueOrFalseQuiz(filePath+matchingFileName,numberOfQuestions);
                break;
            case "MultipleChoice":
                generateMultipleChoiceQuiz(filePath+matchingFileName, numberOfQuestions);
                break;
            default:
                //generateGeneralQuiz();
                break;
        }
    }
    public ArrayList<Question> generateGeneralQuiz(String multipleChoiceFile,String matchingFilePath, String trueOrFalseFilePath , int numberOfQuestions) {
        ArrayList<Question> allQuestions= new ArrayList<>();
        ArrayList<Question> mc= generateMultipleChoiceQuiz(multipleChoiceFile,numberOfQuestions/3);
        ArrayList<Question> matching= generateMatchingQuiz(matchingFilePath, numberOfQuestions/3);
        ArrayList<Question> tf= generateTrueOrFalseQuiz(trueOrFalseFilePath, numberOfQuestions/3);
        allQuestions.addAll(mc);
        allQuestions.addAll(matching);
        allQuestions.addAll(tf);
        Collections.shuffle(allQuestions);
        return allQuestions;
    }

    public ArrayList<Question> generateMultipleChoiceQuiz(String fileName, int numberOfQuestions) {
        currentQuiz = new Quiz("multiChoiceQuiz");
        MultipleChoice multipleChoice= new MultipleChoice("", Collections.singletonList(""),"",0);
        ArrayList<Question> multipleChoiceQuestion = fileHandler.loadQuestions(fileName,multipleChoice);
        return generateRandomQuiz(multipleChoiceQuestion,numberOfQuestions);
    }
    public ArrayList<Question> generateTrueOrFalseQuiz(String fileName, int numberOfQuestions){
        currentQuiz = new Quiz("trueOrFalseQuiz");
        TrueOrFalse trueOrFalse= new TrueOrFalse("", Collections.singletonList(""),0,"");
        ArrayList<Question> trueOrFalseQuestion = fileHandler.loadQuestions(fileName,trueOrFalse);
        return generateRandomQuiz(trueOrFalseQuestion,numberOfQuestions);
    }
    public ArrayList<Question> generateMatchingQuiz(String fileName, int numberOfQuestions){
        HashMap<String,Integer> matches=new HashMap<>();
        Matching matching= new Matching("", Collections.singletonList(""), Collections.singletonList(""),0 ,matches);
        ArrayList<Question> matchingQuestion = fileHandler.loadQuestions(fileName,matching);
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
        this.quizFile = new File(directory, quizFileName);
    }

}
