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
        programs.add(new Program("Computer System Developer"));
        programs.add(new Program("Game Development"));
        programs.add(new Program("Data Technology"));
    }

    public void createAndAddCourses(){
        //Creating courses and adding them to the list and to the programs
        Course DA339A = new Course("Object-Oriented Programming");
        Course DA343A = new Course("Object-Oriented Software Development, Threads and Data Communication");
        Course DA336A = new Course("System Development and project");

        for(int m = 1; m <= 3; m++) {
            DA339A.addModule(new Module("Module A"+ m));
        }
        for(int m = 1; m <= 3; m++) {
            DA343A.addModule(new Module("Module B"+ m));
        }
        for(int m = 1; m <= 3; m++) {
            DA336A.addModule(new Module("Module C"+ m));
        }
        courses.add(DA339A);
        courses.add(DA343A);
        courses.add(DA336A);

        
        //Adding the courses to the first program "Computer System Developer"
        List<Course> coursesSYS = new ArrayList<>();
        coursesSYS.add(DA339A);
        coursesSYS.add(DA343A);
        coursesSYS.add(DA336A);
        programs.get(0).setCourses(coursesSYS);

        //Adding the courses to the second program "Game Development"
        List<Course> coursesGD = new ArrayList<>();
        coursesGD.add(DA336A);
        programs.get(1).setCourses(coursesGD);

        //Adding the courses to the third program "Data Technology"
        List<Course> coursesDT = new ArrayList<>();
        coursesDT.add(DA339A);
        coursesDT.add(DA343A);
        programs.get(2).setCourses(coursesDT);

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
