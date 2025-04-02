package model;

public interface Savable {
    public void saveToFile(String filename);
    public Object loadFromFile(String filename);
}
