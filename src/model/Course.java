package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Course {
    private String name;
    private List<Module> modules;

    public Course(String name) {
        this.name = name;
        modules = new ArrayList<Module>();
    }

    public Course(String name, ArrayList<Module> modules) {
        this.name = name;
        this.modules = modules;
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

}
