package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String name;
    private List<Module> modules;
    private String packageName;

    public Course(String name, String packageName, ArrayList<Module> modules) {
        this.name = name;
        this.modules = modules;
        this.packageName = packageName;
        createPackage();
    }

    public Course(String name, String packageName){
        this.name = name;
        this.modules = new ArrayList<>();
        this.packageName = packageName;

    }

    public String getName() {
        return name;
    }

    public String getPackageName(){
        return packageName;
    }

    public void setName(String name){
        this.name = name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public void addModule(Module module) {
        this.modules.add(module);
    }

    public void createPackage(){
        File directory = new File("src/model/files/" + packageName);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
        }
    }

    public void removePackage(){
        File directory = new File("src/model/files/" + packageName);
        if(directory.exists()) {
            deleteDirectory(directory);
        }
    }

    public void deleteDirectory(File dir){
        File[] files = dir.listFiles();
        if(files != null){
            for(File file : files){
                deleteDirectory(file);
            }
        }
        dir.delete();
    }

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
