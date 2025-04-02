package model;

import java.util.ArrayList;

public class Course {
    private String name;
    private ArrayList<Module> modules;
    public Course(String name, ArrayList<Module> modules) {
        this.name = name;
        this.modules = modules;
    }
    public String getName() {
        return name;
    }
    public ArrayList<Module> getModules() {
        return modules;
    }
    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }
    public void addModule(Module module) {
        this.modules.add(module);
    }

}
