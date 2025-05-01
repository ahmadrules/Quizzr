package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 A class that represents a flashcard with front and back content.
 * @author Lilas Beirakdar
 */
public class FlashCard implements Serializable{
    private String frontContent;
    private String backContent;

    /**
     * Constructs a flashcard object
     * @param frontContent the String content of the front side of the flashcard
     * @param backContent the String content of the back side of the flashcard
     * @author Lilas Beirakdar
     */
    public FlashCard(String frontContent, String backContent) {
        this.frontContent = frontContent;
        this.backContent = backContent;
    }

    /**
     * Sets the content of the backside of the flashcard
     * @param content a String representation of the backside content
     * @author Lilas Beirakdar
     */
    public void setBackContent(String content) {
        this.backContent = content;
    }

    /**
     * Returns the content of the backside of the flashcard
     * @return the backContent of the flashcard as String
     * @author Lilas Beirakdar
     */
    public String getBackContent() {
        return this.backContent;
    }

    /**
     * Returns the content of the front side of the flashcard
     * @return the front contents of the flashcard as String
     * @author Lilas Beirakdar
     */
    public String getFrontContent() {
        return frontContent;
    }

    /**
     * Sets the front content of the front side of the flashcard
     * @param frontContent a String representation of the backside content
     * @author Lilas Beirakdar
     */
    public void setFrontContent(String frontContent) {
        this.frontContent = frontContent;
    }

    /**
     * Returns a string representation of a flashcard.
     * @return a String that contains the front content and back content of the flashcard
     * @author Lilas Beirakdar
     */
    public String toString(){
        return frontContent+" "+backContent;
    }}
