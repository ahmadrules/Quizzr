package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    public void saveQuestionToFile(String filename, Question question) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename,true))){
            outputStream.writeObject(question);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Question readQuestionFromFile(String filename) {
        Question question1 = null;
        File file = new File(filename);
        try(ObjectInputStream objectInputStream= new ObjectInputStream(new FileInputStream(file))) {
            question1= (Question) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return question1;
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
