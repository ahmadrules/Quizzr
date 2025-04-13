package controller;

import model.*;
import view.main.*;

import javax.swing.*;
import java.util.List;

public class Controller {
    private MainFrame view;
    private List<Course> courses;
    private List<Program> programs;

    public Controller(){
        view = new MainFrame();
        SwingUtilities.invokeLater(view);
    }
}
