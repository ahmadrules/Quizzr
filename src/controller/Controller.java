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
        for(int p = 0; p < programList.size(); p++){
            programsNames[p] = programList.get(p).getName();
        }
        return programsNames;
    }

    public String[] getCoursesNames(String programName){

        //Search for the selected program by the programName
        String[] coursesNames = new String[0];
        Program currentProgram = null;

        for(int p = 0; p < programList.size(); p++){
            currentProgram = programList.get(p);
            if(programName.equals(currentProgram.getName())){
                coursesNames = new String[currentProgram.getCourses().size()];
                break;
            }
        }

        //Fill the list with courses names for the selected program
        for (int c = 0; c < coursesNames.length; c++) {
            Course currentCourse = currentProgram.getCourses().get(c);
            coursesNames[c] = currentCourse.getName();
        }
        return coursesNames;
    }

    public String[] getModulesNames(String courseName){

        String[] modulesNames = new String[0];
        Course selectedCourse = null;
        boolean courseFound = false;

        for(int p = 0; p < programList.size(); p++){
            Program currentProgram = programList.get(p);
            for(int c = 0; c < currentProgram.getCourses().size(); c++){
                Course currentCourse = currentProgram.getCourses().get(c);
                if(currentCourse.getName().equals(courseName)){
                    selectedCourse = currentCourse;
                    modulesNames = new String[currentCourse.getModules().size()];
                    courseFound = true;
                    break;
                }
            }
            if(courseFound){
                break;
            }
        }

        //Fill the list with modules names for the selected course
        for (int m = 0; m < modulesNames.length; m++) {
            Module currentModule = selectedCourse.getModules().get(m);
            modulesNames[m] = currentModule.getName();
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
        for(int p = 0; p < programList.size(); p++){
            if(programList.get(p).getName().equals(programName)){
                programList.remove(programList.get(p));
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
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            if(currentProgram.getName().equals(programName)){
                Course newCourse = new Course(courseName);
                currentProgram.addNewCourse(newCourse);
                courses.add(newCourse);
            }
        }
        updateProgramsInFile();
    }

    public void addNewModule(String courseName, String moduleName){
        for(int p = 0; p < programList.size(); p++){
            Program currentProgram = programList.get(p);
            for(int c = 0; c < currentProgram.getCourses().size(); c++){
                Course currentCourse = currentProgram.getCourses().get(c);
                if(currentCourse.getName().equals(courseName)){
                    Module newModule = new Module(moduleName);
                    currentCourse.addModule(newModule);
                    break;
                }
            }
        }
        updateProgramsInFile();
    }

    public void deleteCourse(String programName,String courseName){
        for (int p = 0; p < programList.size(); p++){
            Program currentProgram = programList.get(p);
            if(currentProgram.getName().equals(programName)){
                for(int c = 0; c <currentProgram.getCourses().size(); c++){
                    Course currentCourse = currentProgram.getCourses().get(c);
                    if(currentCourse.getName().equals(courseName)){
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
        for(int p = 0; p < programList.size(); p++){
            Program currentProgram = programList.get(p);
            for(int c = 0; c < currentProgram.getCourses().size(); c++){
                if(currentProgram.getCourses().get(c).getName().equals(courseName)){
                    for(int m = 0; m < currentProgram.getCourses().get(c).getModules().size(); m++) {
                        if(currentProgram.getCourses().get(c).getModules().get(m).getName().equals(moduleName)){
                            currentProgram.getCourses().get(c).getModules().remove(m);
                        }
                    }
                }
            }
        }
        //Edit courses
        for(int c = 0; c < courses.size(); c++){
            if(courses.get(c).getName().equals(courseName)) {
                for(int m = 0; m < courses.get(c).getModules().size(); m++) {
                    if(courses.get(c).getModules().get(m).getName().equals(moduleName)) {
                        courses.get(c).getModules().remove(m);
                    }
                }
            }
        }
        updateProgramsInFile();
    }

    public void editProgramName(String oldProgramName, String updatedProgramName){
        for (int p = 0; p < programList.size(); p++) {
            if(programList.get(p).getName().equals(oldProgramName)){
                programList.get(p).setName(updatedProgramName);
            }
        }
        updateProgramsInFile();
    }
    public void editCourseName(String oldCourseName, String updatedCourseName){

        //Update for courses that belongs to programs
        for (int p = 0; p < programList.size(); p++){
            Program currentProgram = programList.get(p);
            for(int c = 0; c < currentProgram.getCourses().size(); c++) {
                Course currentCourse = programList.get(p).getCourses().get(c);
                if(currentCourse.getName().equals(oldCourseName)){
                    currentCourse.setName(updatedCourseName);
                }
            }
        }

        for(int c = 0; c < courses.size(); c++){
            if(courses.get(c).getName().equals(oldCourseName)){
                courses.get(c).setName(updatedCourseName);
            }
        }
        updateProgramsInFile();
    }

    public void editModuleName(String courseName, String oldModuleName, String updatedModuleName){
        for(int p = 0; p < programList.size(); p++){
            Program currentProgram = programList.get(p);
            for(int c = 0; c < currentProgram.getCourses().size(); c++){
                Course currentCourse = currentProgram.getCourses().get(c);
                if(currentCourse.getName().equals(courseName)){
                    for(int m = 0; m < currentCourse.getModules().size(); m++){
                        Module currentModule = currentCourse.getModules().get(m);
                        if(currentModule.getName().equals(oldModuleName)){
                            currentModule.setName(updatedModuleName);
                            break;
                        }
                    }
                }
            }
        }

        for(int c = 0; c < courses.size(); c++){
            Course currentCourse = courses.get(c);
            if(courses.get(c).getName().equals(courseName)){
                for(int m = 0; m < currentCourse.getModules().size(); m++){
                    Module currentModule = currentCourse.getModules().get(m);
                    if(currentModule.getName().equals(oldModuleName)){
                        currentModule.setName(updatedModuleName);
                    }
                }
            }
        }
        updateProgramsInFile();
    }

}
