package model;

import java.io.*;
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
                    if (questionBuilder.length() > 10000) {
                        System.out.println("Question builder exceeds 10000 characters");
                    }
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
     *
     * @param filename
     * @param question
     */
    public void writeQuestionToFile(String filename, String question) {
        File file = new File(filename);
        try(BufferedWriter bufferedWriter= new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.write(question);
        }
        catch (IOException e) {
            System.out.println("Error saving question");
        }
    }
    /**
     *
     * @param filename
     * @param question
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
        writeQuestionToFile(filename, questionToBeSaved);
    }

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
     * Method used to write a multiple choice question to a file
     * @param filename
     * @param question
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
        // ta bort sista komma
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
    public void saveQuizToFile(String filename, List<Quiz> quizes) {
        try {
            ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(quizes);
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }
    public ArrayList<Quiz> loadQuizFromFile(String filename) {
        ArrayList<Quiz> quizzes= new ArrayList<>();
        try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(filename))) {
            quizzes=(ArrayList<Quiz>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return quizzes;
    }
}
