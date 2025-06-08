package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String name;
    private List<Module> modules;
    private String packageName;

    /**
     * Constructs a new course with a specific name, package path, and a list of modules.
     * Creates the corresponding package directory if it doesn't exist.
     *
     * @param name        The name of the course.
     * @param packageName The directory name where the course data is stored.
     * @param modules     The list of modules associated with this course.
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
    public Course(String name, String packageName, ArrayList<Module> modules) {
        this.name = name;
        this.modules = modules;
        this.packageName = packageName;
        createPackage();
    }

    /**
     * Constructs a new course with no initial modules.
     *
     * @param name        The name of the course.
     * @param packageName The directory name for storing course data.
     * @author Sara Sheikho
     */
    public Course(String name, String packageName){
        this.name = name;
        this.modules = new ArrayList<>();
        this.packageName = packageName;

    }

    /**
     * Gets the name of the course.
     *
     * @return The course name as a string.
     * @author Lilas Beirakdar
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the package name where the course's data is stored.
     *
     * @return The name of the data directory.
     * @author Sara Sheikho
     */
    public String getPackageName(){
        return packageName;
    }

    /**
     * Sets the name of the course.
     *
     * @param name The new course name.
     * @author Sara Sheikho
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Returns the list of modules associated with this course.
     *
     * @return A list of {@link Module} objects.
     * @author Sara Sheikho
     * @author Lilas Beirakdar
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * Sets the list of modules associated with this course.
     *
     * @param modules A list of modules to assign to this course.
     * @author Lilas Beirakdar
     */
    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    /**
     * Adds a single module to this course.
     *
     * @param module The {@link Module} object to be added.
     * @author Lilas Beirakdar
     */
    public void addModule(Module module) {
        this.modules.add(module);
    }

    /**
     * Creates the directory for the course's files if it does not already exist.
     * @author Sara Sheikho
     */
    public void createPackage(){
        File directory = new File("src/model/files/" + packageName);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
        }
    }

    /**
     * Removes the directory and all files associated with this course.
     * @author Sara Sheikho
     */
    public void removePackage(){
        File directory = new File("src/model/files/" + packageName);
        if(directory.exists()) {
            deleteDirectory(directory);
        }
    }

    /**
     * Recursively deletes a directory and all of its contents.
     *
     * @param dir The directory to delete.
     * @author Sara Sheikho
     */
    public void deleteDirectory(File dir){
        File[] files = dir.listFiles();
        if(files != null){
            for(File file : files){
                deleteDirectory(file);
            }
        }
        dir.delete();
    }

    /**
     * Renames the course's package directory to match the course name.
     * Also updates all associated modules' directories.
     * @author Sara Sheikho
     */
    public void renamePackage() {
        File oldDir = new File("src/model/files/" + packageName);
        File newDir = new File("src/model/files/" + name.trim());

        if (oldDir.exists()) {
            oldDir.renameTo(newDir);
        }
        this.packageName = name.trim();

        for (Module module : modules) {
            module.updateDirectory(packageName);
        }
    }
}
