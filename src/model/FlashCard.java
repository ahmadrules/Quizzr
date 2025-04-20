package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FlashCard implements Serializable{
    private String FrontContent;
    private String backContent;

    public FlashCard(String FrontContent, String backContent) {
        this.FrontContent = FrontContent;
        this.backContent = backContent;
    }
    public void setBackContent(String content) {
        this.backContent = content;
    }
    public String getBackContent() {
        return this.backContent;
    }
    public String getFrontContent() {
        return FrontContent;
    }
    public void setFrontContent(String frontContent) {
        this.FrontContent = frontContent;
    }

    public void saveToFile(String filename, List<FlashCard> flashCard) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(flashCard);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
    }

    public ArrayList<FlashCard> loadFromFile(String filename) {
        ArrayList<FlashCard> flashCards = new ArrayList<FlashCard>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)) ) {
            flashCards=(ArrayList<FlashCard>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return flashCards;
    }
    public String toString(){
        return getFrontContent()+" "+getBackContent();
    }}
