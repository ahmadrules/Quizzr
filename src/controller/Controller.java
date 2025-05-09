package controller;

import model.*;
import model.Module;
import view.main.*;
import view.subPanels.LogInFrame;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class Controller {
    private MainFrame view;
    private List<Program> programs; //Hard coding initial  programs
    private List<Course> courses;
    private List<Program> programList;  //All the applications programs (from the file)
    private final String programsFileName = "src/model/files/programs.dat";
    private User currentUser;
    private Module currentModule;
    private Quiz onGoingQuiz;
    private UserManager userManager;
    private List<User> users;
// hämta en lista av all quizes
    private List<Quiz> usersQuizzes ;
    private Program currentStudentProgram;
    private List<FlashCard> currentFlashCards;

    public Controller() {
        programs = new ArrayList<>();
        courses = new ArrayList<>();
        this.userManager = new UserManager();
        this.users = userManager.getUsersList();


       // currentUser.loadCreatedQuizes();

        createAndAddPrograms();
        createAndAddCourses();

        SwingUtilities.invokeLater(()->new LogInFrame(this));
        //Starting the GUI
       // SwingUtilities.invokeLater(view);
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.view=mainFrame;
    }


    public void createAndAddPrograms() {
        //Creating test programs and adding them to the list
        programs.add(new Program("Computer System Developer","TGSYA"));
        programs.add(new Program("Game Development","TGSPA"));
        programs.add(new Program("Data Technology","TCDAT"));
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

    //Method to get programs from file
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

    //This method saves the head coded programs only once
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

    public String[] getProgramsNames() {
        String[] programsNames = new String[programList.size()];
        for (int p = 0; p < programList.size(); p++) {
            programsNames[p] = programList.get(p).getName();
        }
        return programsNames;
    }

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

    //This method adds a new program to the programList then to the file
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

    public void deleteProgramFromFile(String programName) {
        for (int p = 0; p < programList.size(); p++) {
            if (programList.get(p).getName().equals(programName)) {
                programList.remove(programList.get(p));
            }
        }
        updateProgramsInFile();
    }

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

    public void addNewCourse(String programName, String courseName) {
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            if (currentProgram.getName().equals(programName)) {
                Course newCourse = new Course(courseName, courseName.trim());
                currentProgram.addNewCourse(newCourse);
                courses.add(newCourse);
            }
        }
        updateProgramsInFile();
    }

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

    public void deleteCourse(String programName, String courseName) {
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            if (currentProgram.getName().equals(programName)) {
                for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                    Course currentCourse = currentProgram.getCourses().get(c);
                    if (currentCourse.getName().equals(courseName)) {
                        currentProgram.getCourses().remove(c);
                        //TODO remove the package for the course
                        courses.remove(currentCourse);
                        break;
                    }
                }
            }
        }
        updateProgramsInFile();
    }

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

    public void deleteModule(String courseName, String moduleName) {
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                if (currentProgram.getCourses().get(c).getName().equals(courseName)) {
                    for (int m = 0; m < currentProgram.getCourses().get(c).getModules().size(); m++) {
                        if (currentProgram.getCourses().get(c).getModules().get(m).getName().equals(moduleName)) {
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

    public void editProgramName(String oldProgramName, String updatedProgramName) {
        for (int p = 0; p < programList.size(); p++) {
            if (programList.get(p).getName().equals(oldProgramName)) {
                programList.get(p).setName(updatedProgramName);
            }
        }
        updateProgramsInFile();
    }

    public void editCourseName(String oldCourseName, String updatedCourseName) {

        //Update for courses that belongs to programs
        for (int p = 0; p < programList.size(); p++) {
            Program currentProgram = programList.get(p);
            for (int c = 0; c < currentProgram.getCourses().size(); c++) {
                Course currentCourse = programList.get(p).getCourses().get(c);
                if (currentCourse.getName().equals(oldCourseName)) {
                    currentCourse.setName(updatedCourseName);
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

    public String[] getCurrentUserInfo() {
        return currentUser.userInfoToString();
    }

    public void setNewUsername(String username) {
        currentUser.setName(username);
        userManager.saveUsersToFiles();
    }

    public void setNewEmail(String email) {
        currentUser.setEmail(email);
        userManager.saveUsersToFiles();
    }

    public void setNewPassword(String password) {
        String hashedPassword=Hasher.hash(password);
        currentUser.setPassword(hashedPassword);
        userManager.saveUsersToFiles();
    }

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

    public boolean registerNewUser(String username, String password, String email, String programCode) {
        return userManager.registerNewUser(username, password, email, programCode);
    }

    public boolean loginUser(String username, String password) {
        boolean success = userManager.loginUser(username, password);
        if (success) {
            currentUser = userManager.getCurrentUser();
          //  currentUser.loadCreatedQuizes();
        }

        return success;
    }

    public void logoutUser() {
        userManager.logoutUser();
        if (view!=null){
            view.dispose();
            view=null;
        }
        SwingUtilities.invokeLater(()-> {
            new LogInFrame(this);
        });
    }

    public void saveUsers() {
        userManager.saveUsersToFiles();
    }

    public void generateTrueOrFalseQuiz(String selectedCourse, String selectedModule, int nbrOfQuestions) {
        ArrayList<Question> questions = new ArrayList<>();
        Course relatedCourse = null;
        Module relatedModule = null;
        for (int p = 0; p < programList.size(); p++) {
            for (int c = 0; c < programList.get(p).getCourses().size(); c++) {
                Course currentCourse = programList.get(p).getCourses().get(c);
                if (currentCourse.getName().equals(selectedCourse)) {
                    relatedCourse = currentCourse;
                    for (int m = 0; m < currentCourse.getModules().size(); m++) {
                        Module currentModule = currentCourse.getModules().get(m);
                        if (currentModule.getName().equals(selectedModule)) {
                            relatedModule = currentModule;
                            this.currentModule = currentModule;
                            questions = currentModule.generateTrueOrFalseQuiz(relatedCourse, relatedModule, nbrOfQuestions);
                            this.onGoingQuiz = currentModule.getCurrentQuiz();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void generateMultipleChoiceQuiz(String selectedCourse, String selectedModule, int nbrOfQuestions) {
        ArrayList<Question> questions = new ArrayList<>();
        Course relatedCourse = null;
        Module relatedModule = null;
        for (int p = 0; p < programList.size(); p++) {
            for (int c = 0; c < programList.get(p).getCourses().size(); c++) {
                Course currentCourse = programList.get(p).getCourses().get(c);
                if (currentCourse.getName().equals(selectedCourse)) {
                    relatedCourse = currentCourse;
                    for (int m = 0; m < currentCourse.getModules().size(); m++) {
                        Module currentModule = currentCourse.getModules().get(m);
                        if (currentModule.getName().equals(selectedModule)) {
                            relatedModule = currentModule;
                            this.currentModule = currentModule;
                            questions = currentModule.generateMultipleChoiceQuiz(relatedCourse, relatedModule, nbrOfQuestions);
                            this.onGoingQuiz = currentModule.getCurrentQuiz();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void generateMatchingQuiz(String selectedCourse, String selectedModule, int nbrOfQuestions) {
        ArrayList<Question> questions = new ArrayList<>();
        Course relatedCourse = null;
        Module relatedModule = null;
        for (int p = 0; p < programList.size(); p++) {
            for (int c = 0; c < programList.get(p).getCourses().size(); c++) {
                Course currentCourse = programList.get(p).getCourses().get(c);
                if (currentCourse.getName().equals(selectedCourse)) {
                    relatedCourse = currentCourse;
                    for (int m = 0; m < currentCourse.getModules().size(); m++) {
                        Module currentModule = currentCourse.getModules().get(m);
                        if (currentModule.getName().equals(selectedModule)) {
                            relatedModule = currentModule;
                            this.currentModule = currentModule;
                            questions = currentModule.generateMatchingQuiz(relatedCourse, relatedModule, nbrOfQuestions);
                            this.onGoingQuiz = currentModule.getCurrentQuiz();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void generateGeneralQuiz(String selectedCourse, String selectedModule, int nbrOfQuestions) {
        ArrayList<Question> questions = new ArrayList<>();
        Course relatedCourse = null;
        Module relatedModule = null;
        for (int p = 0; p < programList.size(); p++) {
            for (int c = 0; c < programList.get(p).getCourses().size(); c++) {
                Course currentCourse = programList.get(p).getCourses().get(c);
                if (currentCourse.getName().equals(selectedCourse)) {
                    relatedCourse = currentCourse;
                    for (int m = 0; m < currentCourse.getModules().size(); m++) {
                        Module currentModule = currentCourse.getModules().get(m);
                        if (currentModule.getName().equals(selectedModule)) {
                            relatedModule = currentModule;
                            this.currentModule = currentModule;
                            questions = currentModule.generateGeneralQuiz(relatedCourse, relatedModule, nbrOfQuestions);
                            this.onGoingQuiz = currentModule.getCurrentQuiz();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @return List of queries for the current selected quiz
     * @author Sara Sheikho
     */
    public List<String> getQueryList() {
        List<String> queryList = new ArrayList<>();
        for (int i = 0; i < currentModule.getCurrentQuiz().getQuestions().size(); i++) {
            queryList.add(currentModule.getCurrentQuiz().getQuestions().get(i).getQuery());
        }
        return queryList;
    }

    /**
     * @param query This parameter has been sent by view to get the list of alternatives for the send query
     * @return List of alternatives soe the query
     * @author Sara Sheikho
     */
    public List<String> getAlternativeList(String query) {
        List<String> alternatives = new ArrayList<>();
        for (int i = 0; i < currentModule.getCurrentQuiz().getQuestions().size(); i++) {
            Question currentQuestion = currentModule.getCurrentQuiz().getQuestions().get(i);
            if (currentQuestion.getQuery().equals(query)) {
                alternatives = currentQuestion.getAlternatives();
            }
        }
        return alternatives;
    }

    /**
     * @return A list of correct answers for the list of questions for the current selected quiz
     * @author Sara Sheikho
     */
    public List<String> getCorrectAnswers() {
        List<String> correctAnswers = new ArrayList<>();
        for (int i = 0; i < currentModule.getCurrentQuiz().getQuestions().size(); i++) {
            correctAnswers.add(currentModule.getCurrentQuiz().getQuestions().get(i).getCorrectAnswer());
        }
        return correctAnswers;
    }

    /**
     * @param questionIndex that view will send to get the matches of the current question
     * @return list of the question matches
     */
    public List<String> getMatches(int questionIndex) {
        return currentModule.getCurrentQuiz().getQuestions().get(questionIndex).getMatches();
    }

    public List<String> getQuizHistory(String selectedCourse, String selectedModule) {
        List<String> createdQuizNames = new ArrayList<>();
        for (int i = 0; i < currentUser.getCreatedQuiz().size(); i++) {
            Quiz currentQuiz = currentUser.getCreatedQuiz().get(i);
            String relatedCourse = currentQuiz.getRelatedCourse().getName();
            String relatedModule = currentQuiz.getRelatedModule().getName();
            if (relatedCourse.equals(selectedCourse) && relatedModule.equals(selectedModule)) {
                createdQuizNames.add(currentQuiz.getName());
            }
        }
        return createdQuizNames;
    }

    public void reDoQuiz(String selectedCourse, String selectedModule, String selectedQuiz) {
        for (int i = 0; i < currentUser.getCreatedQuiz().size(); i++) {
            Quiz currentQuiz = currentUser.getCreatedQuiz().get(i);
            String relatedCourse = currentQuiz.getRelatedCourse().getName();
            String relatedModule = currentQuiz.getRelatedModule().getName();
            if(relatedCourse.equals(selectedCourse) && relatedModule.equals(selectedModule)){
                if(currentQuiz.getName().equals(selectedQuiz)){
                    this.onGoingQuiz = currentQuiz;
                    this.currentModule = currentQuiz.getRelatedModule();
                }
            }
        }
    }

   public Program getCurrentStudentProgram(){
        String programCode= currentUser.getProgramCode();
        for (Program program : programList) {
            if (program.getProgramCode().equals(programCode)) {
                this.currentStudentProgram = program;
                break;
            }
        }
        return this.currentStudentProgram;
   }

    public List<String> programCodes(){
        List<String> programCodes = new ArrayList<>();
        for (Program program : programList) {
            programCodes.add(program.getProgramCode());
        }
        return programCodes;
    }
    public String getCurrentStudentProgramName(){
        return currentStudentProgram.getName();
    }

    private Module getModule(String courseName, String moduleName) {
        Course currentCourse=null;
        Module module=null;
        for (int c=0; c<courses.size(); c++){
            if (courses.get(c).getName().equals(courseName)) {
                currentCourse = courses.get(c);
                for (int m=0; m<currentCourse.getModules().size(); m++){
                    if (currentCourse.getModules().get(m).getName().equals(moduleName)) {
                        module = currentCourse.getModules().get(m);
                    }
                }

            }
        }
        return module;
    }

    public void saveTrueOrFalseQuestion(String query, List<String> alternatives,int points, String correctAnswer ,String courseName, String moduleName ) {
        Module module = getModule(courseName,moduleName);
        TrueOrFalse trueOrFalse= new TrueOrFalse(query,alternatives,points,correctAnswer);
        module.saveTrueOrFalseQuestionToFile(trueOrFalse);
    }

    public void saveMultipleChoiceToFile(String query, List<String> alternatives, int points, String correctAnswer, String courseName, String moduleName ) {
        Module module = getModule(courseName,moduleName);
        MultipleChoice multipleChoice= new MultipleChoice(query,alternatives,correctAnswer,points);
        module.saveMultiChoiceQuestionToFile(multipleChoice);
    }

    public void saveMatchingToFile(String query, List<String> statements, List<String> matches, int points, HashMap<String,Integer> correctMatches, String courseName, String moduleName ) {
        Module module = getModule(courseName,moduleName);
        Matching matching= new Matching(query,statements,matches,points,correctMatches);
        module.saveMatchingQuestionToFile(matching);
    }

    public void setQuizDone(boolean isDone){
        onGoingQuiz.setDone(isDone);
    }

    public List<Quiz> getCurrentUsersQuizList(){
        List<Quiz> quizList = new ArrayList<>();
        for(User user: users){
            if (currentUser.getName().equals(user.getName())){
                this.usersQuizzes=user.getCreatedQuiz();
                return usersQuizzes;
            }
        }
        return new ArrayList<>();
    }

    public void saveCurrentUserQuiz(Quiz quiz){
        quiz.setDate(new Date());
        currentUser.addToCreatedQuiz(quiz);
        userManager.saveUsersToFiles();
    }
    public List<FlashCard> getUsersFlashCards(){
        List<FlashCard> flashCards = new ArrayList<>();
        for(User user: users){
            if (currentUser.getName().equals(user.getName())){
                flashCards=user.getFlashCards();
                return flashCards;
            }
        }
        return new ArrayList<>();
    }

    public void saveUsersFlashCards(FlashCard flashCard){
        currentUser.addToCreatedFlashcards(flashCard);
        userManager.saveUsersToFiles();
    }

    public List<Quiz> getCurrentUsersQuizHistory(){
        List<Quiz> availableQuizList = new ArrayList<>();
        List<Quiz> quizHistory = new ArrayList<>();
        for(User user: users){
            if (currentUser.getName().equals(user.getName())){
                availableQuizList=user.getCreatedQuiz();
                for(Quiz quiz: availableQuizList){
                    if (quiz.getDone()){
                        quizHistory.add(quiz);
                    }
                }
                return quizHistory;
            }
        }
        return new ArrayList<>();
    }


}