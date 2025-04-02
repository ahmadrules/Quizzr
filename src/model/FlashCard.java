package model;

public class FlashCard implements Savable {
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

    @Override
    public void saveToFile(String filename) {

    }

    @Override
    public Object loadFromFile(String filename) {
        return null;
    }
}
