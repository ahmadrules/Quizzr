package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an educational program consisting of multiple courses.
 * Implements Serializable to allow object persistence.
 * @author Lilas Beirakdar
 * @author Sara Sheiho
 */
public class Program implements Serializable {
    private String name;
    private List<Course> courses;
    private String programCode;

    /**
     * Constructs a Program with a name and a predefined list of courses.
     *
     * @param name    the name of the program
     * @param courses the list of courses in the program
     * @author Lilas Beirakdar
     */
    public Program(String name, ArrayList<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    /**
     * Constructs a Program with a name and a program code. Initializes an empty course list.
     *
     * @param name the name of the program
     * @param programCode the unique code for the program
     * @author Lilas Beirakdar
     * @author Sara Sheiho
     */
    public Program(String name, String programCode) {
        this.name = name;
        courses = new ArrayList<>();
        this.programCode = programCode;
    }

    /**
     * Returns the name of a program
     * @return the name of a program as a String
     * @author Lilas Beirakdar
     */
    public String getName() {
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the list of courses related to a specific program
     * @return List of courses
     * @author Lilas Beirakdar
     */
    public List<Course> getCourses() {
        return courses;
    }
    public void addNewCourse(Course newCourse){
        courses.add(newCourse);
    }
    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    /**
     * Returns the programCode of a specific program
     * @return programCode as a String
     * @author Lilas Beirakdar
     */
    public String getProgramCode() {
        return programCode;
    }

    @Override
    public String toString(){
        return "Program name: " + name;
    }

}
