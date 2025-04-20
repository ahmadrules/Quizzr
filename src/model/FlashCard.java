package model;

import java.io.*;

public class FlashCard implements Serializable{
    private String FrontContent;
    private String BackContent;

    public FlashCard(String content) {
        this.FrontContent = content;
        this.BackContent = content;
    }
    public void setBackContent(String content) {
        this.BackContent = content;
    }
    public String getBackContent() {
        return this.BackContent;
    }
    public String getFrontContent() {
        return FrontContent;
    }
    public void setFrontContent(String frontContent) {
        this.FrontContent = frontContent;
    }
    public void saveToFile(String filename, FlashCard flashCard) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(flashCard);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FlashCard loadFromFile(String filename) {
        FlashCard flashCard = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename)) ) {
            flashCard=(FlashCard) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return flashCard;
    }
}
