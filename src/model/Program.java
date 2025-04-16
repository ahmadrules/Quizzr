package model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Program implements Serializable {
    private String name;
    private List<Course> courses;
    private final String fileName = "src/model/files/programs.dat";

    public Program(String name, ArrayList<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public Program(String name){
        this.name = name;
        courses = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public List<Course> getCourses() {
        return courses;
    }
    public void addNewCourse(Course newCourse){
        courses.add(newCourse);
    }
    public void setCourses(List<Course> courses){
        this.courses = courses;
    }

    public String getFileName() {
        return fileName;
    }
    @Override
    public String toString(){
        return "Program name: " + name;
    }

}
