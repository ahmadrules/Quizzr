package model;

public interface Savable {
    public void saveToFile(String filename, Object obj);
    public Object loadFromFile(String filename);
}
