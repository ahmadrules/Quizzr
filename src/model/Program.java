package model;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private String name;
    private List<Course> courses;

    public Program(String name, List<Course> courses) {
        this.name = name;
        this.courses = courses;
    }

    public Program(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
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

}
