package model;

import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private List<Module> modules;

    public Course(String name, List<Module> modules) {
        this.name = name;
        this.modules = modules;
    }

    public Course(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
    public void addNewModule(Module newModule){
        modules.add(newModule);
    }
}
