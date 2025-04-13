package controller;

import model.*;
import model.Module;
import view.main.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private MainFrame view;
    private List<Program> programs;
    private List<Course> courses;

    public Controller(){

        programs = new ArrayList<>();
        courses = new ArrayList<>();

        createAndAddPrograms();
        createAndAddCourses();

        //Starting the GUI
        view = new MainFrame(this);

        SwingUtilities.invokeLater(view);
    }

    public void createAndAddPrograms(){
        //Creating test programs and adding them to the list
        for (int i = 1; i <= 3; i++){
            programs.add(new Program("Program " + i));
        }
    }

    public void createAndAddCourses(){
        //Creating test courses and adding them to the list and adding them to the programs
        for (int i = 1; i <= 3; i++) {
            Course course = new Course("Course A"+ i);
            for(int m = 1; m <= 3; m++) {
                course.addModule(new Module("Module A" + i + "-" + m));
            }
            courses.add(course);
            programs.get(0).addNewCourse(course);
        }
        for (int i = 1; i <= 3; i++) {
            Course course = new Course("Course B"+ i);
            for(int m = 1; m <= 3; m++) {
                course.addModule(new Module("Module B" + i + "-" + m));
            }
            courses.add(course);
            programs.get(1).addNewCourse(course);
        }
        for (int i = 1; i <= 3; i++) {
            Course course = new Course("Course C"+ i);
            for(int m = 1; m <= 3; m++) {
                course.addModule(new Module("Module C" + i + "-" + m));
            }
            courses.add(course);
            programs.get(2).addNewCourse(course);
        }
    }

    public String[] getProgramsNames(){
        String[] programsNames = new String[programs.size()];
        for(int i = 0; i < programs.size(); i++){
            programsNames[i] = programs.get(i).getName();
        }
        return programsNames;
    }

    public String[] getCoursesNames(String programName){

        //Search for the selected program by the programName
        String[] coursesNames = null;
        Program currentProgram = null;

        for(int i = 0; i < programs.size(); i++){
            currentProgram = programs.get(i);
            if(programName.equals(currentProgram.getName())){
                coursesNames = new String[currentProgram.getCourses().size()];
                break;
            }
        }

        //Fill the list with courses names for the selected program
        for (int i = 0; i < coursesNames.length; i++) {
            Course currentCourse = currentProgram.getCourses().get(i);
            coursesNames[i] = currentCourse.getName();
        }
        return coursesNames;
    }

    public String[] getModulesNames(String courseName){

        String[] modulesNames = null;
        Course currentCourse = null;

        //Search for the selected course
        for (int i = 0; i < courses.size(); i++){
            currentCourse = courses.get(i);
            if(courseName.equals(currentCourse.getName())){
                modulesNames = new String[currentCourse.getModules().size()];
                break;
            }
        }

        //Fill the list with modules names for the selected course
        for (int i = 0; i < modulesNames.length; i++) {
            Module currentModule = currentCourse.getModules().get(i);
            modulesNames[i] = currentModule.getName();
        }
        return modulesNames;
    }

}
