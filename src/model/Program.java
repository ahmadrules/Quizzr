package model;

import java.util.ArrayList;

public class Program {
    private String name;
    private ArrayList<Course> courses;
    public Program(String name, ArrayList<Course> courses) {
        this.name = name;
        this.courses = courses;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }

}
