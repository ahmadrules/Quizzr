package model;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class used to handle files, read from it and write to it
 * @author Lilas Beirakdar
 */
public class FileHandler implements Serializable {
    /**
     * a constructor to create an object of the fileHandler class
     * @author Lilas Beirakdar
     */
    public FileHandler() {
    }

    /**
     * Method used to read a String question from a file and change it into a Question object
     * It can read all types of Strings and return a list of question objects
     * @param fileName the name of the file containing the questions
     * @param question the type of the question we want to read it can be a TrueOrFalse Question,
     * MultipleChoice or a Matching question
     * @return List of Question objects
     * @param <T> generic type representing any class inheriting from the class Question
     * @author Lilas Beirakdar
     */
    public <T extends Question> ArrayList<T> loadQuestions(String fileName, T question) {
        List<T> questions = new ArrayList<>();
        StringBuilder questionBuilder = new StringBuilder();

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                if (line.trim().isEmpty()) {
                    if (questionBuilder.length()> 0) {
                        String questionString = questionBuilder.toString();
                        T questionToAdd = (T) question.fromString(questionString);
                        if (!questionString.isEmpty()) {
                            questions.add(questionToAdd);}
                        questionBuilder.setLength(0);
                    }
                }else {
                    questionBuilder.append(line).append("\n");
                }
                line = br.readLine();
            }
            if (questionBuilder.length() > 0) {
                String questionString = questionBuilder.toString();
                T questionToAdd = (T) question.fromString(questionString);
                if (!questionString.isEmpty()) {
                    questions.add(questionToAdd);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (ArrayList<T>) questions;
    }

    /**
     * Used to save a question to a file
     * It adds a new question to the file and adds a line between question
     * The extra blank line helps to identify the end of a question
     * @param filename The name of the file to add the question to
     * @param question The question to be added to the file as String
     * @author Lilas Beirakdar
     */
    public void writeQuestionToFile(String filename, String question) {
        File file = new File(filename);
        try(BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter(file, true))) {
            if (file.exists() && file.length() > 0) {
               bufferedWriter.newLine();
               bufferedWriter.newLine();
            }

            bufferedWriter.append(question);
            bufferedWriter.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to save a multipleChoice question to a file
     * It gets an object of multipleChoice question
     * Turns it to String to ensure that the question has the right structure when added to the file
     * Then it calls writeToFile method to save the String to the file
     * @param filename the name of the file the question will be added to
     * @param question the multipleChoice object that needs to be saved to the file
     * @author Lilas Beirakdar
     */
    public void saveMultipleChoiceToFile(String filename, MultipleChoice question) {
        String query = question.getQuery();
        List<String> alternatives= question.getAlternatives();
        String firstAlternative = alternatives.get(0);
        String secondAlternative = alternatives.get(1);
        String thirdAlternative = alternatives.get(2);
        String correctAnswer = question.getCorrectAnswer();
        int points = question.getPoints();
        String questionToBeSaved= query+";"+firstAlternative+";"+secondAlternative+";"
                +thirdAlternative+";"+correctAnswer+";"+points;
        writeQuestionToFile(filename,questionToBeSaved);
    }

    /**
     * Saves a trueOrFalse question to the given file
     * It takes a trueOrFalseQuestion as an object
     * Turns it into String and ensures it has the right structure before it's added to the file
     * It calls the method writeToFile to save the question to the file
     * @param filename the name of the file the question will be saved to
     * @param question the trueOrFalse question as an object
     * @author Lilas Beirakdar
     */
    public void saveTrueOrFalseQuestionToFile(String filename, Question question) {
        String query = question.getQuery();
        List<String> alternatives= question.getAlternatives();
        String firstAlternative = alternatives.get(0);
        String secondAlternative = alternatives.get(1);
        int points = question.getPoints();
        String correctAnswer = question.getCorrectAnswer();
        String questionToBeSaved= query+";"+firstAlternative+";"+
                secondAlternative+";"+points+";"+correctAnswer;
        writeQuestionToFile(filename, questionToBeSaved);
    }

    /**
     * Method used to write a matching question to a file
     * It takes a matching question as an object
     * Then it turns the object into a String with the right structure to be saved to the file
     * It calls the metod writeToFile to save the question to the given file
     * @param filename The name of the file the question will be added to
     * @param question the question to be saved to the file as a matchning object
     * @author Lilas Beirakdar
     */
    public void saveMatchingQuestionToFile(String filename, Matching question) {
        String query = question.getQuery();

        List<String> alternatives= question.getAlternatives();
        String firstAlternative = alternatives.get(0);
        String secondAlternative = alternatives.get(1);
        String thirdAlternative = alternatives.get(2);
        String alternativesString= firstAlternative+","+secondAlternative+","+thirdAlternative;

        List<String> matches= question.getMatches();
        String firstMatch = matches.get(0);
        String secondMatch = matches.get(1);
        String thirdMatch = matches.get(2);
        String matchesString= "A."+firstMatch+","+"B."+secondMatch+","+"C."+thirdMatch;

        int points = question.getPoints();

        HashMap<String,Integer> correctMatches = question.getCorrectMatches() ;
        StringBuilder correctAnswer= new StringBuilder();
        for(Map.Entry<String,Integer> entry: correctMatches.entrySet()) {
            correctAnswer.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        if(correctAnswer.length() > 0) {
            correctAnswer.setLength(correctAnswer.length()-1);
        }
        String correctMatchesString= correctAnswer.toString();

        String questionToWrite= query+";"+alternativesString+";"+matchesString+";"+points+";"+correctMatchesString;
        writeQuestionToFile(filename, questionToWrite);


    }
    
    /**
     * Saves a flashcard object to a file
     * @param filename the filepath of the file that will contain the flashcards
     * @param flashCard a List of flashcards that will be saved to the file
     * @author Lilas Beirakdar
     */
    public void saveFlashcardToFile(String filename, List<FlashCard> flashCard) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(flashCard);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    /**
     *Loads a list of flashcards from a file
     * @param filename  the filepath of the file that contains the flashcards
     * @return a list of flashcards
     * @author Lilas Beirakdar
     */
    public ArrayList<FlashCard> loadFlashcardsFromFile(String filename) {
        ArrayList<FlashCard> flashCards = new ArrayList<FlashCard>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)) ) {
            flashCards=(ArrayList<FlashCard>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return flashCards;
    }
}
