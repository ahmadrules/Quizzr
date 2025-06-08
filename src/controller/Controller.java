package controller;

import model.*;
import model.Module;
import view.main.*;
import view.subPanels.LogInFrame;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>
 * Responsibility: This class acts as the controller in the MVC pattern.
 * It handles user interactions from the view, updates the model accordingly, and updates the GUI
 * with changes from the model layer.
 * </p>
 *
 * <p>
 * Main Responsibilities:
 * <ul>
 *   <li>Processes user choices from the GUI (MainFrame).</li>
 *   <li>Interacts with model classes such as <code>UserManager</code>, <code>Program</code>, <code>Course</code>, <code>Quiz</code>.</li>
 *   <li>Handles loading and saving of data (e.g. reading program data from <code>programs.dat</code>).</li>
 *   <li>Maintains references to the current user, ongoing quiz, current program/module, etc.</li>
 *   <li>Provides necessary data to update views (e.g. list of quizzes, courses, user points).</li>
 * </ul>
 * </p>
 *
 * <p>
 * Fields Overview:
 * <ul>
 *   <li><code>view</code> - the main GUI frame</li>
 *   <li><code>programs</code> - hardcoded initial list of programs</li>
 *   <li><code>courses</code> - list of courses used in the system</li>
 *   <li><code>programList</code> - all programs read from file</li>
 *   <li><code>programsFileName</code> - path to the file storing program data</li>
 *   <li><code>currentUser</code> - the user currently logged in</li>
 *   <li><code>userManager</code> - manager class for all user-related logic</li>
 *   <li><code>usersQuizzes</code> - list of quizzes attempted by the current user</li>
 *   <li><code>currentStudentProgram</code> - the program currently enrolled by the student</li>
 *   <li><code>currentFlashCards</code> - flashcards available to the current module</li>
 * </ul>
 * </p>
 *
 * @author Sara Sheikho
 * @author Lilas Beirakdar
 * @author Ahmad Maarouf
 */
public class Controller {
    private MainFrame view;
    private List<Program> programs; //Hard coding initial  programs
    private List<Course> courses;
    private List<Program> programList;  //All the applications programs (from the file)
    private final String programsFileName = "src/model/files/programs.dat";
    private User currentUser;
    private UserManager userManager;
    private List<User> users;
    private List<Quiz> userAvailableQuizzes;
    private List<Quiz> usersHistoryQuizzes;
    private Program currentStudentProgram;
    private List<FlashCard> currentFlashCards;

    public Controller() {
        programs = new ArrayList<>();
        courses = new ArrayList<>();
        this.userManager = new UserManager();
        this.users = userManager.getUsersList();

        createAndAddPrograms();
        createAndAddCourses();

        SwingUtilities.invokeLater(() -> new LogInFrame(this));
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.view = mainFrame;
    }


    public void createAndAddPrograms() {
        //Creating test programs and adding them to the list
        programs.add(new Program("Computer System Developer", "TGSYA"));
        programs.add(new Program("Game Development", "TGSPA"));
        programs.add(new Program("Data Technology", "TCDAT"));
    }

    public void createAndAddCourses() {
        //Creating courses and adding them to the list and to the programs
        Course DA339A = new Course("Object-Oriented Programming", "Object-OrientedProgramming");
        Course DA343A = new Course("Object-Oriented Software Development, Threads And Data Communication", "Object-OrientedSoftwareDevelopment,ThreadsAndDataCommunication");
        Course DA336A = new Course("System Development And project", "SystemDevelopmentAndProject");

        for (int m = 1; m <= 3; m++) {
            DA339A.addModule(new Module("Module A" + m, DA339A.getPackageName()));
        }
        for (int m = 1; m <= 3; m++) {
            DA343A.addModule(new Module("Module B" + m, DA343A.getPackageName()));
        }
        for (int m = 1; m <= 3; m++) {
            DA336A.addModule(new Module("Module C" + m, DA336A.getPackageName()));
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

    /**
     * This method Loads all {@link Program} objects from a serialized data file into the program list.
     * <p>
     * This method is called when an admin opens the application. It reads the contents
     * of the file using an {@link ObjectInputStream}, and adds each deserialized {@link Program} object
     * to the programs list
     * </p>
     *
     * <p>
     * The file is expected to contain multiple serialized {@link Program} objects. The method
     * reads until an {@link EOFException} is thrown, which signals the end of the file.
     * </p>
     *
     * @throws IOException            if there is a problem reading the file
     * @throws ClassNotFoundException if the {@link Program} class cannot be found during deserialization
     * @author Sara Sheikho
     */
    public void loadProgramsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(programsFileName))) {
            while (true) {
                try {
                    Program program = (Program) ois.readObject();
                    programList.add(program);
                } catch (EOFException exc) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Saves all {@link Program} objects from the {@code programs} list to a serialized data file.
     *
     * <p>
     * This method writes each {@link Program} object in the {@code programs} list to the dat file
     * using {@link ObjectOutputStream}.
     * </p>
     *
     * <p>
     * Before writing, the method checks if the file already exists and is non-empty.
     * If so, it does not overwrite it and returns early to prevent duplication.
     * </p>
     *
     * <p>
     * This method is typically called during the initial setup of the application to persist
     * hardcoded or predefined programs.
     * </p>
     *
     * @throws IOException if writing to the file fails; the exception message is printed to the console.
     * @author Sara Sheikho
     */

    public void saveProgramsToFile() {

        File file = new File(programsFileName);

        if (file.exists() && file.length() > 0) {
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(programsFileName))) {
            for (Program program : programs) {
                oos.writeObject(program);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
    }

    /**
     * Retrieves the names of all available programs in the {@code programList}.
     * <p>
     * This method is used to extract only the names of the {@link Program} objects so they
     * can be displayed in the GUI
     * </p>
     *
     * @return a String array containing the names of all programs in {@code programList}.
     * @author Sara Sheikho
     */

    public String[] getProgramsNames() {
        String[] programsNames = new String[programList.size()];
        for (int p = 0; p < programList.size(); p++) {
            programsNames[p] = programList.get(p).getName();
        }
        return programsNames;
    }

    /**
     * Retrieves the names of all available courses in the {@code courseList} with a selected program name.
     * <p>
     * This method searches the list of programs {@code programList} for a program whose name matches
     * the provided programName. Once found, it extracts the names of all {@link Course}
     * objects in that program and returns them as a String array to
     * display these course names in the GUI.
     * </p>
     *
     * @param programName the name of the program for which associated course names are to be retrieved
     * @return a String array containing the names of all courses in {@code courseList}.
     * @author Sara Sheikho
     */

    public String[] getCoursesNames(String programName) {

        //Search for the selected program by the programName
        String[] coursesNames = new String[0];
        Program currentProgram = null;

        for (int p = 0; p < programList.size(); p++) {
            currentProgram = programList.get(p);
            if (programName.equals(currentProgram.getName())) {
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

    /**
     * Retrieves the names of all modules associated with the given course name.
     * <p>
     * This method searches through all {@link Program} objects in the {@code programList},
     * and for each program, it iterates over its {@link Course} objects to find a match
     * with the given {@code courseName}. Once the matching course is found, it extracts
     * and returns the names of all {@link Module} objects within that course.
     * </p>
     *
     * @param courseName the name of the course whose module names should be retrieved
     * @return a String array containing the names of all {@link Module} objects
     * in the matching course, or an empty array if no such course is found
     * @author Sara Sheikho
     */

    public String[] getModulesNames(String courseName) {

        String[] modulesNames = new String[0];
        Course selectedCourse = null;
        boolean courseFound = false;

        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                Course currentCourse = currentProgram.getCourses().get(c);
                if (currentCourse.getName().equals(courseName)) {
                    selectedCourse = currentCourse;
                    modulesNames = new String[currentCourse.getModules().size()];
                    courseFound = true;
                    break;
                }
            }
            if (courseFound) {
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

    /**
     * Adds a new {@link Program} to the {@code programList} if a program with the same name does not already exist.
     * <p>
     * This method first checks if a program with the given name is already present in the list.
     * If not, it creates a new {@code Program} object with the provided name and code,
     * adds it to the {@code programList}, and updates the persistent file storage by
     * calling {@code updateProgramsInFile()}.
     * </p>
     *
     * <p>Note: Program names are considered unique identifiers for this check.</p>
     *
     * @param programName the name of the new program to add
     * @param programCode the code of the new program to add
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
    public void addProgramToProgramList(String programName, String programCode) {
        for (Program program : programList) {
            if (program.getName().equals(programName)) {
                return;
            }
        }
        Program newProgram = new Program(programName, programCode);
        programList.add(newProgram);
        updateProgramsInFile();
    }

    /**
     * Deletes a {@link Program} from the {@code programList} by its name.
     * <p>
     * This method searches for a program in the list that matches the given name
     * and removes it. After the removal, the updated list is saved to file storage
     * by calling {@code updateProgramsInFile()}.
     * </p>
     *
     * <p>Note: If multiple programs have the same name, all matches will be removed.</p>
     *
     * @param programName the name of the program to be deleted
     * @author Sara Sheikho
     */

    public void deleteProgramFromFile(String programName) {
        for (int p = 0; p < programList.size(); p++) {
            if (programList.get(p).getName().equals(programName)) {
                programList.remove(programList.get(p));
            }
        }
        updateProgramsInFile();
    }

    /**
     * Updates the program list in the file by writing all the current {@link Program} objects
     * to the dat file.
     * <p>
     * This method will overwrite the existing file with the updated list of programs.
     * </p>
     *
     * @throws IOException if an I/O error occurs while writing the programs to the file.
     * @author Sara Sheikho
     */

    public void updateProgramsInFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(programsFileName))) {
            for (Program program : programList) {
                oos.writeObject(program);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
    }

    /**
     * Adds a new {@link Course} to the specified {@link Program} by its name.
     * <p>
     * This method searches the {@code programList} for a program with the given name.
     * If found, it creates a new {@link Course} with the specified course name and a trimmed version
     * of the same name as the course package name, then adds it to the program and the system {@code courses} list.
     * After updating the data, it calls {@code updateProgramsInFile()} to save changes to disk.
     * </p>
     *
     * @param programName the name of the program to which the new course should be added
     * @param courseName  the name (and code) of the new course to be created
     * @author Sara Sheikho
     */

    public void addNewCourse(String programName, String courseName) {
        Course course = null;
        for (Program program : programList) {
            for (Course currentCourse : program.getCourses()) {
                if (currentCourse.getName().equals(courseName)) {
                    course = currentCourse;
                }
            }
        }

        if (course == null) {
            for (int p = 0; p < programList.size(); p++) {
                Program currentProgram = programList.get(p);
                if (currentProgram.getName().equals(programName)) {
                    Course newCourse = new Course(courseName, courseName.trim());
                    currentProgram.addNewCourse(newCourse);
                    courses.add(newCourse);
                }
            }
        } else {
            for (int p = 0; p < programList.size(); p++) {
                Program currentProgram = programList.get(p);
                if (currentProgram.getName().equals(programName)) {
                    currentProgram.addNewCourse(course);
                }
            }
        }
        updateProgramsInFile();
    }

    /**
     * Adds a new {@link Module} to a {@link Course} identified by its name.
     * <p>
     * This method searches through the {@code programList} to locate the {@link Course} with the given name.
     * Once the course is found, it creates a new {@link Module} using the provided module name and
     * the course's package name to creates the text files where the questions should stored
     * and then adds the module to the course.
     * Finally, it updates the file storing the list of programs to persist the changes.
     * </p>
     *
     * @param courseName the name of the course to which the module should be added
     * @param moduleName the name of the new module to be created
     * @author Sara Sheikho
     */

    public void addNewModule(String courseName, String moduleName) {

        Course requestedCourse = null;
        Program requestedProgram = null;
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                Course currentCourse = currentProgram.getCourses().get(c);
                if (currentCourse.getName().equals(courseName)) {
                    requestedProgram = currentProgram;
                    requestedCourse = currentCourse;
                    break;
                }
            }
        }

        if (requestedCourse != null) {
            Module newModule = new Module(moduleName, requestedCourse.getPackageName());
            requestedCourse.addModule(newModule);
        }
        updateProgramsInFile();
    }

    /**
     * Deletes a course from a specific program
     *
     * <p>
     * This method searches through the {@code programList} to locate the {@link Program} with the given name.
     * Once the program is found, it searches for the {@link Course} with the specified name and removes it
     * from both the program's course list and the general {@code courses} list.
     * Finally, it updates the file storing the list of programs to persist the changes.
     * </p>
     *
     * @param programName the name of the program from which the course should be deleted
     * @param courseName  courseName the name of the course to be removed
     * @author Sara Sheikho
     */
    public void deleteCourse(String programName, String courseName) {
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            if (currentProgram.getName().equals(programName)) {
                for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                    Course currentCourse = currentProgram.getCourses().get(c);
                    if (currentCourse.getName().equals(courseName)) {
                        currentProgram.getCourses().remove(c);
                        currentCourse.removePackage();
                        courses.remove(currentCourse);
                        break;
                    }
                }
            }
        }
        updateProgramsInFile();
    }

    /**
     * Retrieves a {@link Module} by its name from a specific {@link Course} and {@link Program}.
     * <p>
     * This method iterates through the {@code programs} list to find the {@link Program} and
     * {@link Course} matching the given names. If found, it searches for the {@link Module} with the
     * specified name and returns it. If no matching module is found, {@code null} is returned.
     * </p>
     *
     * @param programName the name of the program containing the course
     * @param courseName  the name of the course containing the module
     * @param moduleName  the name of the module to retrieve
     * @return the matching {@link Module}, or {@code null} if not found
     * @author Ahmad Maarouf
     **/
    public Module getModule(String programName, String courseName, String moduleName) {
        for (Program program : programs) {
            if (program.getName().equals(programName)) {
                for (Course course : program.getCourses()) {
                    if (course.getName().equals(courseName)) {
                        for (Module module : course.getModules()) {
                            if (module.getName().equals(moduleName)) {
                                return module;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public Course getCourse(String programName, String courseName) {
        for (Program program : programs) {
            if (program.getName().equals(programName)) {
                for (Course course : program.getCourses()) {
                    if (course.getName().equals(courseName)) {
                        return course;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Deletes a {@link Module} from a {@link Course} identified by its name.
     * <p>
     * This method iterates through all {@link Program} objects in {@code programList} and their corresponding
     * {@link Course} lists to find and remove the {@link Module} with the specified name.
     * It also removes the module from the general {@code courses} list to ensure consistency.
     * Finally, it updates the program list file to persist the changes.
     * </p>
     *
     * @param courseName the name of the course from which the module should be removed
     * @param moduleName the name of the module to be deleted
     * @author Sara Sheikho
     **/
    public void deleteModule(String courseName, String moduleName) {
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                if (currentProgram.getCourses().get(c).getName().equals(courseName)) {
                    for (int m = 0; m < currentProgram.getCourses().get(c).getModules().size(); m++) {
                        if (currentProgram.getCourses().get(c).getModules().get(m).getName().equals(moduleName)) {
                            currentProgram.getCourses().get(c).getModules().get(m).removePackage();
                            currentProgram.getCourses().get(c).getModules().remove(m);
                        }
                    }
                }
            }
        }
        //Edit courses
        for (int c = 0; c < courses.size(); c++) {
            if (courses.get(c).getName().equals(courseName)) {
                for (int m = 0; m < courses.get(c).getModules().size(); m++) {
                    if (courses.get(c).getModules().get(m).getName().equals(moduleName)) {
                        courses.get(c).getModules().remove(m);
                    }
                }
            }
        }
        updateProgramsInFile();
    }

    /**
     * Updates the name of a {@link Program}.
     * <p>
     * This method searches for a {@link Program} in the {@code programList} with the specified old name
     * and updates it to the new name. Finally, it updates the file storing the list of programs
     * to persist the name change.
     * </p>
     *
     * @param oldProgramName     the current name of the program
     * @param updatedProgramName the new name to assign to the program
     * @author Sara Sheikho
     **/
    public void editProgramName(String oldProgramName, String updatedProgramName) {
        for (int p = 0; p < programList.size(); p++) {
            if (programList.get(p).getName().equals(oldProgramName)) {
                programList.get(p).setName(updatedProgramName);
            }
        }
        updateProgramsInFile();
    }

    /**
     * Updates the name of a {@link Course}.
     * <p>
     * This method finds and updates the name of a course in both the {@code programList} and the general {@code courses} list.
     * It ensures that all references to the old course name are replaced with the updated name across the system.
     * Finally, it updates the file storing the list of programs to persist the changes.
     * </p>
     *
     * @param oldCourseName     the current name of the course
     * @param updatedCourseName the new name to assign to the course
     * @author Sara Sheikho
     **/
    public void editCourseName(String oldCourseName, String updatedCourseName) {

        //Update for courses that belongs to programs
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                Course currentCourse = programList.get(p).getCourses().get(c);
                if (currentCourse.getName().equals(oldCourseName)) {
                    currentCourse.setName(updatedCourseName);
                    currentCourse.renamePackage();
                }
            }
        }

        for (int c = 0; c < courses.size(); c++) {
            if (courses.get(c).getName().equals(oldCourseName)) {
                courses.get(c).setName(updatedCourseName);
            }
        }
        updateProgramsInFile();
    }

    /**
     * Updates the name of a {@link Module} within a {@link Course}.
     * <p>
     * This method searches through all {@link Program} objects and their {@link Course} lists
     * to find the module with the specified old name and update it to the new name.
     * It also updates the module name in the general {@code courses} list.
     * Finally, it updates the file storing the list of programs to persist the changes.
     * </p>
     *
     * @param courseName        the name of the course containing the module
     * @param oldModuleName     the current name of the module
     * @param updatedModuleName the new name to assign to the module
     * @author Sara Sheikho
     **/
    public void editModuleName(String courseName, String oldModuleName, String updatedModuleName) {
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                Course currentCourse = currentProgram.getCourses().get(c);
                if (currentCourse.getName().equals(courseName)) {
                    for (int m = 0; m < currentCourse.getModules().size(); m++) {
                        Module currentModule = currentCourse.getModules().get(m);
                        if (currentModule.getName().equals(oldModuleName)) {
                            currentModule.setName(updatedModuleName);
                            currentModule.renameModuleDirectory(currentCourse.getPackageName());
                            break;
                        }
                    }
                }
            }
        }

        for (int c = 0; c < courses.size(); c++) {
            Course currentCourse = courses.get(c);
            if (courses.get(c).getName().equals(courseName)) {
                for (int m = 0; m < currentCourse.getModules().size(); m++) {
                    Module currentModule = currentCourse.getModules().get(m);
                    if (currentModule.getName().equals(oldModuleName)) {
                        currentModule.setName(updatedModuleName);
                    }
                }
            }
        }
        updateProgramsInFile();
    }

    public boolean ifProgramExists(String programName) {
        for (Program program : programList) {
            if (program.getName().equals(programName)) {
                return true;
            }
        }
        return false;
    }

    public boolean ifCourseExists(String programName, String courseName) {
        for (Program program : programList) {
            if (program.getName().equals(programName)) {
                for (Course course : program.getCourses()) {
                    if (course.getName().equals(courseName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean ifModuleExists(String programName, String courseName, String moduleName) {
        for (Program program : programList) {
            if (program.getName().equals(programName)) {
                for (Course course : program.getCourses()) {
                    if (course.getName().equals(courseName)) {
                        for (Module module : course.getModules()) {
                            if (module.getName().equals(moduleName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns current user's information like username, email and password as String
     *
     * @return An array of String representing current user's information
     * @author Ahmad Maarouf
     */
    public String[] getCurrentUserInfo() {
        return currentUser.userInfoToString();
    }

    /**
     * Sets a new username for the current user
     * After setting the new username it saves users changes to the file
     *
     * @param username the new username entered by the user as String
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     */
    public void setNewUsername(String username) {
        currentUser.setName(username);
        userManager.saveUsersToFiles();
    }

    /**
     * Used to set a new email for the current user
     * After setting the new password it saves users changes to the file
     *
     * @param email New email entered by the user as String
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     */
    public void setNewEmail(String email) {
        currentUser.setEmail(email);
        userManager.saveUsersToFiles();
    }

    /**
     * Sets a new password for current user
     *
     * @param password new password Entered by the user as String
     * @author Lilas Beirakdar
     * @author Ahmad Maarouf
     */
    public void setNewPassword(String password) {
        String hashedPassword = Hasher.hash(password);
        currentUser.setPassword(hashedPassword);
        userManager.saveUsersToFiles();
    }

    /**
     * Checks if the email is written as abs@abc.com
     * If not it returns false else it returns true
     *
     * @param email the Email entered by the user as a String
     * @return boolean value indicating whether the entered email is valid or not
     * @author Ahmad Maarouf
     */
    //Code taken from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
    public boolean isEmailValid(String email) {

        // Regular expression to match valid email formats
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex
        Pattern p = Pattern.compile(emailRegex);

        // Check if email matches the pattern
        return email != null && p.matcher(email).matches();
    }

    /**
     * Used to register a new student in the program
     *
     * @param username    Entered username by the user as a String
     * @param password    Entered password by the user as a String
     * @param email       the entered email by the user as a String
     * @param programCode the program code of the program the student wants to practice on
     * @return boolean value indicating if the registering process has been done successfully
     * @author Lilas Beirakdar
     */

    public boolean registerNewUser(String username, String password, String email, String programCode) {
        return userManager.registerNewUser(username, password, email, programCode);
    }

    /**
     * Loggs in the registered user in the program
     * It gets the username and password
     * Checks if they matchs to the data existed in the file
     * If the user exist it loads all quizzes related to the user
     * Else it returns false
     *
     * @param username the name of the user as a String
     * @param password the password entered by the user
     * @return boolean indicating whether logging in has succeeded or not
     * @author Lilas Beirakdar
     */

    public boolean loginUser(String username, String password) {
        boolean success = userManager.loginUser(username, password);
        if (success) {
            currentUser = userManager.getCurrentUser();
            this.usersHistoryQuizzes = currentUser.getHistory();
            this.userAvailableQuizzes = currentUser.getCreatedQuiz();
            System.out.println("Quizzes have been loaded");
            this.currentStudentProgram = getCurrentStudentProgram();
        }

        return success;
    }

    /**
     * Used to log out the current user
     * It sets the current user to null, closes the mainframe and starts Login frame
     *
     * @author Lilas Beirakdar
     */
    public void logoutUser() {
        userManager.logoutUser();
        if (view != null) {
            view.dispose();
            view = null;
        }
        SwingUtilities.invokeLater(() -> {
            new LogInFrame(this);
        });
    }

    /**
     * Saves the changed the current user makes to the file
     *
     * @author Lilas Beirakdar
     */
    public void saveUsers() {

        userManager.saveUsersToFiles();
    }

    /**
     * Returns the program that the current student studies
     *
     * @return the program that the current student have given the program code of
     * When registered into the program
     * @author Lilas Beirakdar
     */
    public Program getCurrentStudentProgram() {
        String usersProgramCode = currentUser.getProgramCode();
        for (Program program : programList) {
            if (program.getProgramCode().equals(usersProgramCode)) {
                this.currentStudentProgram = program;
                break;
            }
        }
        return this.currentStudentProgram;
    }

    /**
     * Returns a list of program codes as String
     *
     * @return a List of String representing program codes
     * @author Lilas Beirakdar
     * @author Lilas Beirakdar
     */
    public List<String> getProgramCodes() {
        List<String> programCodes = new ArrayList<>();
        for (Program program : programList) {
            programCodes.add(program.getProgramCode());
        }
        return programCodes;
    }

    /**
     * Returns program name of the current student as String
     *
     * @return program name as String
     * @author Lilas Beirakdar
     */

    public String getCurrentStudentProgramName() {
        return currentStudentProgram.getName();
    }

    /**
     * Returns the module depending on course name and module name given in the GUI
     *
     * @param courseName the course name of the course that includes the module
     * @param moduleName the name of the wanted module
     * @return
     * @author Lilas Beirakdar
     */
    private Module getModule(String courseName, String moduleName) {
        Course currentCourse = null;
        Module module = null;
        for (Program program : programList) {
            for (int c = 0; c < program.getCourses().size(); c++) {
                currentCourse = program.getCourses().get(c);
                if (currentCourse.getName().equals(courseName)) {
                    for (int m = 0; m < currentCourse.getModules().size(); m++) {
                        if (currentCourse.getModules().get(m).getName().equals(moduleName)) {
                            module = currentCourse.getModules().get(m);
                        }
                    }

                }
            }
        }
        return module;
    }

    /**
     * Saves a true or false question to a file
     *
     * @param query         the part of question object representing the question
     * @param alternatives  the alternatives of the question
     * @param points        number of points gained after answering the question correctly
     * @param correctAnswer the correct answer of the question as String
     * @param courseName    the course that the question is related to
     * @param moduleName    the module that the question is related to
     * @author Lilas Beirakdar
     */
    public void saveTrueOrFalseQuestion(String query, List<String> alternatives, int points, String correctAnswer, String courseName, String moduleName) {
        Module module = getModule(courseName, moduleName);
        TrueOrFalse trueOrFalse = new TrueOrFalse(query, alternatives, points, correctAnswer);
        module.saveTrueOrFalseQuestionToFile(trueOrFalse);
    }

    /**
     * Saves a multiple choice question to a file
     *
     * @param query         the part representing the question
     * @param alternatives  the part of question representing the alternatives
     * @param points        number of points earned after answering the question correctly
     * @param correctAnswer the correct answer of the question
     * @param courseName    the course that the question is related to
     * @param moduleName    the module that the course is related to
     * @author Lilas Beirakdar
     */
    public void saveMultipleChoiceToFile(String query, List<String> alternatives, int points, String correctAnswer, String courseName, String moduleName) {
        Module module = getModule(courseName, moduleName);
        MultipleChoice multipleChoice = new MultipleChoice(query, alternatives, correctAnswer, points);
        module.saveMultiChoiceQuestionToFile(multipleChoice);
    }

    /**
     * Saves a matching question to  a file
     *
     * @param query          the part representing the question
     * @param statements     represents the statements that needs to be matched to another statement
     * @param matches        represents the matches of the given statements
     * @param points         number of points earned after answering the question correctly
     * @param correctMatches a HashMap representing the statement and the match that matches to it
     * @param courseName     the course that the question is related to
     * @param moduleName     the module that the course is related to
     * @author Lilas Beirakdar
     */

    public void saveMatchingToFile(String query, List<String> statements, List<String> matches, int points, HashMap<String, Integer> correctMatches, String courseName, String moduleName) {
        Module module = getModule(courseName, moduleName);
        Matching matching = new Matching(query, statements, matches, points, correctMatches);
        module.saveMatchingQuestionToFile(matching);
    }

    public void saveUsersFlashCards(FlashCard flashCard) {
        currentUser.addToCreatedFlashcards(flashCard);
        userManager.saveUsersToFiles();
    }

    /**
     * Returns the history of quizzes the current user have done
     *
     * @return a List of history quizzes the current user have done before
     * @author Lilas Beirakdar
     */
    public List<Quiz> getCurrentUsersQuizHistory() {
        List<Quiz> availableQuizList = new ArrayList<>();
        List<Quiz> quizHistory = new ArrayList<>();
        for (User user : users) {
            if (currentUser.getName().equals(user.getName())) {
                availableQuizList = user.getCreatedQuiz();
                for (Quiz quiz : availableQuizList) {
                    if (quiz.getDone()) {
                        quizHistory.add(quiz);
                    }
                }
                return quizHistory;
            }
        }
        return new ArrayList<>();
    }

    /**
     * Retrieves the front content of all {@link FlashCard}s for a specific {@link Course} and {@link Module}.
     * <p>
     * This method filters the current user's flashcards by matching the given course and module names,
     * then collects and returns the front content of each flashcard.
     * </p>
     *
     * @param selectedCourse the name of the course associated with the flashcards
     * @param selectedModule the name of the module associated with the flashcards
     * @return a list of front content strings from matching flashcards
     * @author Sara Sheikho
     **/
    public List<String> getFlashCardsFrontContent(String selectedCourse, String selectedModule) {
        List<String> frontContent = new ArrayList<>();
        for (FlashCard flashCard : currentUser.getFlashCards()) {
            if (flashCard.getRelatedCourse().getName().equals(selectedCourse) && flashCard.getRelatedModule().getName().equals(selectedModule)) {
                frontContent.add(flashCard.getFrontContent());
            }
        }
        return frontContent;
    }

    /**
     * Retrieves the back content of all {@link FlashCard}s for a specific {@link Course} and {@link Module}.
     * <p>
     * This method filters the current user's flashcards by matching the given course and module names,
     * then collects and returns the back content of each flashcard.
     * </p>
     *
     * @param selectedCourse the name of the course associated with the flashcards
     * @param selectedModule the name of the module associated with the flashcards
     * @return a list of back content strings from matching flashcards
     * @author Sara Sheikho
     **/
    public List<String> getFlashCardsBackContent(String selectedCourse, String selectedModule) {
        List<String> backContent = new ArrayList<>();
        for (FlashCard flashCard : currentUser.getFlashCards()) {
            if (flashCard.getRelatedCourse().getName().equals(selectedCourse) && flashCard.getRelatedModule().getName().equals(selectedModule)) {
                backContent.add(flashCard.getBackContent());
            }
        }
        return backContent;
    }

    /**
     * @return
     */
    public List<Quiz> getUsersHistoryQuizzes() {
        return usersHistoryQuizzes;
    }

    /**
     * Returns a list of created quizzes of the current user
     *
     * @return a list of quizzes created by the current user
     * @author Lilas Beirakdar
     */
    public List<Quiz> getUsersAvailableQuizzes() {
        return currentUser.getCreatedQuiz();
    }

    public List<String> getAvailableQuizNames() {
        List<String> availableQuizNames = new ArrayList<>();

        List<Quiz> availableQuiz = getUsersAvailableQuizzes();
        for (Quiz quiz : availableQuiz) {
            availableQuizNames.add(quiz.getName());
        }
        return availableQuizNames;
    }

    public List<String> getHistoryQuizNames() {
        List<String> historyQuizNames = new ArrayList<>();

        List<Quiz> historyQuiz = getUsersHistoryQuizzes();
        for (Quiz quiz : historyQuiz) {
            historyQuizNames.add(quiz.getName());
        }
        return historyQuizNames;
    }

    /**
     * Adds a quiz to created quizzes list
     * It sets the date of the quiz to current date
     * It saves the user after adding the quiz to users file
     *
     * @param quiz the quiz to be added to the created quizzes list associated with the current student
     * @author Lilas Beirakdar
     */
    public void addQuizToAvailableQuizzes(Quiz quiz) {
        quiz.setDate(DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now()));
        currentUser.addToCreatedQuiz(quiz);
        userManager.saveUsersToFiles();
        this.userAvailableQuizzes = currentUser.getCreatedQuiz();
    }

    /**
     * @param quizName
     * @param questions
     * @param answers
     * @author Ahmad Maarouf
     * @author Lilas Beirakdar
     */

    public void addQuizToHistory(String quizName, List<Question> questions, Map<Question, String> answers, String relatedModule, String relatedCourse) {
        Module selectedModule = getModule(relatedCourse, relatedModule);
        Course selectedCourse = getCourse(getCurrentStudentProgramName(), relatedCourse);
        Quiz quiz1 = new Quiz(quizName, selectedCourse, selectedModule);
        String currentDate = new SimpleDateFormat("yyyy/MM/dd-HH:mm").format(Calendar.getInstance().getTime());
        quiz1.setDate(currentDate);
        quiz1.setName(currentDate + " " + quizName);
        Map<Question, String> answerMap = new HashMap<>();
        for (Question question : questions) {
            answerMap.put(question, answers.get(question));
        }
        quiz1.setQuestions(questions);
        quiz1.setUserAnswers(answerMap);


        currentUser.addToHistory(quiz1);
        userManager.saveUsersToFiles();
        this.usersHistoryQuizzes = currentUser.getHistory();
    }

    public void deleteQuiz(String quizName, String relatedModule, String relatedCourse) {
        Quiz quizToDelete = findQuiz(quizName, relatedModule, relatedCourse);
        currentUser.removeQuiz(quizToDelete);
        userManager.saveUsersToFiles();
    }

    public Quiz findQuiz(String quizName, String relatedModule, String relatedCourse) {
        List<Quiz> quizList = currentUser.getCreatedQuiz();
        for (Quiz quiz : quizList) {
            String moduleName = quiz.getRelatedModule().getName();
            String courseName = quiz.getRelatedCourse().getName();
            String foundQuizName = quiz.getName();
            if (Objects.equals(moduleName, relatedModule) && Objects.equals(courseName, relatedCourse) && Objects.equals(foundQuizName, quizName)) {
                return quiz;
            }
        }
        return null;
    }

    public Quiz findHistoryQuiz(String quizName, String relatedModule, String relatedCourse) {
        List<Quiz> quizList = currentUser.getHistory();
        for (Quiz quiz : quizList) {
            if (quiz.getName().equals(quizName)) {
                return quiz;
            }
        }
        return null;
    }

    public void clearHistory(String selectedModule, String selectedCourse) {
        List<Quiz> quizList = currentUser.getHistory();
        List<Quiz> toRemove = new ArrayList<>();

        for (Quiz quiz : quizList) {
            String relatedModule = quiz.getRelatedModule().getName();
            String relatedCourse = quiz.getRelatedCourse().getName();

            if (relatedModule.equals(selectedModule) && relatedCourse.equals(selectedCourse)) {
                toRemove.add(quiz);
            }
        }

        for (Quiz quiz : toRemove) {
            currentUser.removeHistoryQuiz(quiz);
        }
    }

    public void clearCreatedQuiz(String selectedModule, String selectedCourse) {
        List<Quiz> quizList = currentUser.getCreatedQuiz();
        List<Quiz> toRemove = new ArrayList<>();

        for (Quiz quiz : quizList) {
            String relatedModule = quiz.getRelatedModule().getName();
            String relatedCourse = quiz.getRelatedCourse().getName();

            if (relatedModule.equals(selectedModule) && relatedCourse.equals(selectedCourse)) {
                toRemove.add(quiz);
            }
        }

        for (Quiz quiz : toRemove) {
            currentUser.removeQuiz(quiz);
        }
    }

    public List<String> getRelatedQuiz(String relatedModule, String relatedCourse) {
        return getStrings(relatedModule, relatedCourse, userAvailableQuizzes);
    }

    public List<String> getRelatedHistoryQuiz(String relatedModule, String relatedCourse) {
        //System.out.println("Size: " + usersHistoryQuizzes.size());
        //currentUser.clearHistory();
        //List<Quiz> toRemove = usersHistoryQuizzes;
        //usersHistoryQuizzes.removeAll(toRemove);
        return getStrings(relatedModule, relatedCourse, usersHistoryQuizzes);
    }

    private List<String> getStrings(String relatedModule, String relatedCourse, List<Quiz> relatedQuiz) {
        List<String> relatedQuizNames = new ArrayList<>();
        for (Quiz quiz : relatedQuiz) {
            String relatedCourseName = quiz.getRelatedCourse().getName();
            String relatedModuleName = quiz.getRelatedModule().getName();
            if (Objects.equals(relatedCourseName, relatedCourse) && Objects.equals(relatedModuleName, relatedModule)) {
                relatedQuizNames.add(quiz.getName());
            }
        }
        return relatedQuizNames;
    }

    public void generateQuiz(int amountOfQuestions, String quizName, String typeOfQuiz, long timerSeconds, String relatedModule, String relatedCourse) {
        Module currentModule = getModule(relatedCourse, relatedModule);
        Course currentCourse = getCourse(getCurrentStudentProgramName(), relatedCourse);
        List<Question> questionsList = new ArrayList<>();
        Quiz newQuiz = new Quiz(quizName, currentCourse, currentModule);

        if (typeOfQuiz.equals("Matching")) {
            questionsList = currentModule.generateMatchingQuiz(amountOfQuestions);
        } else if (typeOfQuiz.equals("True/False")) {
            questionsList = currentModule.generateTrueOrFalseQuiz(amountOfQuestions);
        } else if (typeOfQuiz.equals("Multiple choice")) {
            questionsList = currentModule.generateMultipleChoiceQuiz(amountOfQuestions);
        }

        questionsList.forEach(question -> {
            newQuiz.addQuestion(question);
        });

        newQuiz.setTimerSeconds(timerSeconds);
        addQuizToAvailableQuizzes(newQuiz);

    }

    public void changeProfilePicture(String selectedPicPath) {
        currentUser.setProfilePicPath(selectedPicPath);
        saveUsers();
    }

    public String getUserProfilePicturePath() {
        return currentUser.getProfilePicPath();
    }

    public Quiz generateGeneralCourseQuiz(String selectedCourse) {
        ArrayList<Question> questions = new ArrayList<>();
        Course currentCourse = getCourse(currentStudentProgram.getName(), selectedCourse);
        List<Module> modules = currentCourse.getModules();
        int amountOfModules = currentCourse.getModules().size();

        for (Module module : modules) {
            questions.addAll(module.generateGeneralQuiz(30 / amountOfModules));
        }

        Quiz newQuiz = new Quiz("General");
        for (Question question : questions) {
            newQuiz.addQuestion(question);
        }
        return newQuiz;
    }
}