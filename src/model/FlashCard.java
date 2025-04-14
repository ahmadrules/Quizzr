package model;

import java.io.*;

public class FlashCard{
    private String content;

    public FlashCard(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void saveToFile(String filename, Object content) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(content);
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
