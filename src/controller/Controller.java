package controller;

import model.*;
import model.Module;
import view.main.*;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private MainFrame view;
    private List<Program> programs; //Hard coding initial  programs
    private List<Course> courses;
    private List<Program> programList;  //All the applications programs (from the file)
    private final String programsFileName = "src/model/files/programs.dat";


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

        saveProgramsToFile();

        //Getting the applications programs from the file
        programList = new ArrayList<>();
        loadProgramsFromFile();
        for(Program program : programList){
            System.out.println(program.toString());
        }
    }

    //Method to get programs from file
    public void loadProgramsFromFile(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(programsFileName))){
            while (true) {
                try {
                    Program program = (Program) ois.readObject();
                    programList.add(program);
                } catch (EOFException exc) {
                    break;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }

    //This method saves the head coded programs only once
    public void saveProgramsToFile(){

        File file = new File(programsFileName);

        if(file.exists() && file.length() > 0) {
            return;
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(programsFileName))){
            for(Program program : programs){
                oos.writeObject(program);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());;
        }
    }

    public String[] getProgramsNames(){
        String[] programsNames = new String[programList.size()];
        for(int i = 0; i < programList.size(); i++){
            programsNames[i] = programList.get(i).getName();
        }
        return programsNames;
    }

    public String[] getCoursesNames(String programName){

        //Search for the selected program by the programName
        String[] coursesNames = new String[0];
        Program currentProgram = null;

        for(int i = 0; i < programList.size(); i++){
            currentProgram = programList.get(i);
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

    //This method adds a new program to the programList then to the file
    public void addProgramToProgramList(String programName){

        for (Program program : programList){
            if(program.getName().equals(programName)){
                return;
            }
        }
        Program newProgram = new Program(programName);
        programList.add(newProgram);
        updateProgramsInFile();
    }

    public void deleteProgramFromFile(String programName){
        for(int i = 0; i < programList.size(); i++){
            if(programList.get(i).getName().equals(programName)){
                programList.remove(programList.get(i));
            }
        }
        updateProgramsInFile();
    }

    public void updateProgramsInFile(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(programsFileName))){
            for(Program program : programList){
                oos.writeObject(program);
            }
        }catch(IOException e){
            System.out.println(e.getMessage());;
        }
    }
    public void addNewCourse(String programName, String courseName){
        for (int i = 0; i < programList.size(); i++) {
            if(programList.get(i).getName().equals(programName)){
                Course newCourse = new Course(courseName);
                programList.get(i).addNewCourse(newCourse);
                courses.add(newCourse);
            }
        }
        updateProgramsInFile();
    }

    public void addNewModule(String courseName, String moduleName){
        for(int i = 0; i < programList.size(); i++){
            for(int c = 0; c < programList.get(i).getCourses().size(); c++){
                if(programList.get(i).getCourses().get(c).getName().equals(courseName)){
                    Module newModule = new Module(moduleName);
                    programList.get(i).getCourses().get(c).addModule(newModule);
                }
            }
        }
        //Edit courses
        for(int i = 0; i < courses.size(); i++){
            if(courses.get(i).getName().equals(courseName)){
                Module newModule = new Module(moduleName);
                courses.get(i).addModule(newModule);
            }
        }

        updateProgramsInFile();
    }

    public void deleteCourse(String programName,String courseName){
        for (int i = 0; i < programList.size(); i++){
            Program currentProgram = programList.get(i);
            if(currentProgram.getName().equals(programName)){
                for(int c = 0; c <currentProgram.getCourses().size(); c++){
                    Course currentCourse = currentProgram.getCourses().get(c);
                    if(currentProgram.getCourses().get(c).getName().equals(courseName)){
                        currentProgram.getCourses().remove(c);
                        courses.remove(currentCourse);
                        break;
                    }
                }
            }
        }
        updateProgramsInFile();
    }

    public void deleteModule(String courseName, String moduleName){
        for(int i = 0; i < programList.size(); i++){
            for(int c = 0; c < programList.get(i).getCourses().size(); c++){
                if(programList.get(i).getCourses().get(c).getName().equals(courseName)){
                    for(int m = 0; m < programList.get(i).getCourses().get(c).getModules().size(); m++) {
                        if(programList.get(i).getCourses().get(c).getModules().get(m).getName().equals(moduleName)){
                            programList.get(i).getCourses().get(c).getModules().remove(m);
                        }
                    }
                }
            }
        }
        //Edit courses
        for(int i = 0; i < courses.size(); i++){
            if(courses.get(i).getName().equals(courseName)){
                for(int m = 0; m < courses.get(i).getModules().size(); m++) {
                    if(courses.get(i).getModules().get(m).getName().equals(moduleName)) {
                        courses.remove(m);
                    }
                }
            }
        }

        updateProgramsInFile();
    }

    public void editProgramName(String oldProgramName, String updatedProgramName){
        for (int i = 0; i < programList.size(); i++) {
            if(programList.get(i).getName().equals(oldProgramName)){
                programList.get(i).setName(updatedProgramName);
            }
        }
        updateProgramsInFile();
    }
    public void editCourseName(String oldCourseName, String updatedCourseName){

    }

}
