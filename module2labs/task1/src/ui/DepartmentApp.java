package ui;

import entities.Department;
import entities.Professor;
import entities.Subject;
import xml.parsers.DepartmentsDOMParser;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;


public class DepartmentApp extends JFrame {

    private final String path = "resources/departments.xml";


    private Department department;
    private final Vector<String> professorsLabels = new Vector<String>(Arrays.asList("Id", "First name", "Last name", "Age"));
    private Vector<Vector<String>> professorsData = new Vector<Vector<String>>();

    private Box buttonsHolder = new Box(BoxLayout.X_AXIS);

    private final Vector<String> subjectLabels = new Vector<String>(Arrays.asList("Id", "Name", "Hours", "Professor"));
    private Vector<Vector<String>> subjectData = new Vector<Vector<String>>();

    private JTable professorTable = new JTable(professorsData, professorsLabels);
    private JScrollPane professorTablePane;
    private JButton professorAdd = new JButton("Add professor");
    private JButton professorDelete = new JButton("Delete professor");

    private JButton professorBoxFormAdd = new JButton("Add");

    private JTable subjectTable = new JTable(subjectData, subjectLabels);
    private JScrollPane subjectTablePane;
    private JButton subjectAdd = new JButton("Add subject");
    private JButton subjectDelete = new JButton("Delete subject");

    private final Box contents;

    private JPanel professorBoxForm;
    private JTextArea professorBoxFormName;
    private JButton professorBoxFormBack = new JButton("Back");
    private JLabel pNameLabel;
    private Box pNameLabelBox;


    private Container c;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JLabel lname;
    private JTextField tlname;
    private JTextField tage;
    private JLabel age;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dob;
    private JComboBox date;
    private JComboBox month;
    private JComboBox year;
    private JLabel add;
    private JTextArea tadd;
    private JCheckBox term;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;
    private JLabel res;
    private JTextArea resadd;


    private void buildProfessorBoxForm() {
        professorBoxForm = new JPanel();
        professorBoxForm.setLayout(null);
        pNameLabelBox = new Box(BoxLayout.X_AXIS);
        professorBoxFormName = new JTextArea();
        professorBoxFormName.setFont(new Font("Arial", Font.PLAIN, 15));
        professorBoxFormName.setSize(300, 400);
        pNameLabel = new JLabel("Name");
        pNameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        pNameLabel.setSize(100, 20);
        pNameLabelBox.add(pNameLabel);

        professorBoxFormBack.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        contents.setVisible(true);
                        professorBoxForm.setVisible(false);
                    }
            );
            tr.start();
        });




        professorBoxForm.add(pNameLabelBox);
        professorBoxForm.add(professorBoxFormAdd);
        professorBoxForm.add(professorBoxFormBack);


        title = new JLabel("New professor");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        professorBoxForm.add(title);

        name = new JLabel("FName");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 100);
        professorBoxForm.add(name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(190, 20);
        tname.setLocation(200, 100);
        professorBoxForm.add(tname);

        lname = new JLabel("LName");
        lname.setFont(new Font("Arial", Font.PLAIN, 20));
        lname.setSize(100, 20);
        lname.setLocation(100, 150);
        professorBoxForm.add(lname);

        tlname = new JTextField();
        tlname.setFont(new Font("Arial", Font.PLAIN, 15));
        tlname.setSize(150, 20);
        tlname.setLocation(200, 150);
        professorBoxForm.add(tlname);

        age = new JLabel("Age");
        age.setFont(new Font("Arial", Font.PLAIN, 20));
        age.setSize(100, 20);
        age.setLocation(100, 200);
        professorBoxForm.add(age);

        tage = new JTextField();
        tage.setFont(new Font("Arial", Font.PLAIN, 15));
        tage.setSize(150, 20);
        tage.setLocation(200, 200);
        professorBoxForm.add(tage);


//        date = new JComboBox(dates);
//        date.setFont(new Font("Arial", Font.PLAIN, 15));
//        date.setSize(50, 20);
//        date.setLocation(200, 250);
//        c.add(date);
//
//        month = new JComboBox(months);
//        month.setFont(new Font("Arial", Font.PLAIN, 15));
//        month.setSize(60, 20);
//        month.setLocation(250, 250);
//        c.add(month);
//
//        year = new JComboBox(years);
//        year.setFont(new Font("Arial", Font.PLAIN, 15));
//        year.setSize(60, 20);
//        year.setLocation(320, 250);
//        c.add(year);



        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 450);
        //sub.addActionListener(this);
        professorBoxForm.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 450);
        //reset.addActionListener(this);
        professorBoxForm.add(reset);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        professorBoxForm.add(tout);

        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 20));
        res.setSize(500, 25);
        res.setLocation(100, 500);
        professorBoxForm.add(res);

        resadd = new JTextArea();
        resadd.setFont(new Font("Arial", Font.PLAIN, 15));
        resadd.setSize(200, 75);
        resadd.setLocation(580, 175);
        resadd.setLineWrap(true);
        professorBoxForm.add(resadd);


        professorBoxForm.setVisible(false);
    }

    private Vector<String> professorToStringArray(Professor professor) {
        return new Vector<>(Arrays.asList(professor.getId().toString(), professor.getFirstName(), professor.getLastName(), String.valueOf(professor.getAge())));
    }

    private Vector<Vector<String>> professorsToStringData(ArrayList<Professor> professors) {
        var res = new Vector<Vector<String>>();
        for (Professor professor : professors) {
            res.add(professorToStringArray(professor));
        }
        return res;
    }


    private Vector<String> subjectToStringArray(Subject subject) {
        return new Vector<>(Arrays.asList(subject.getId().toString(), subject.getName(), String.valueOf(subject.getHours()), subject.getProfessor().getLastName()));
    }

    private Vector<Vector<String>> subjectsToStringData(ArrayList<Subject> subjects) {
        var res = new Vector<Vector<String>>();
        for (Subject subject : subjects) {
            res.add(subjectToStringArray(subject));
        }
        return res;
    }


    private void rebuildProfessorTable() {
        contents.remove(professorTablePane);
        professorTable =  new JTable(professorsData, professorsLabels);
        professorTablePane = new JScrollPane(professorTable);
        contents.add(professorTablePane);
    }

    private void rebuildSubjectTable() {
        contents.remove(subjectTablePane);
        subjectTable =  new JTable(subjectData, subjectLabels);
        subjectTablePane = new JScrollPane(subjectTable);
        contents.add(subjectTablePane);
    }

    private void setButtonsActions() {
        professorAdd.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        contents.setVisible(false);
                        add(professorBoxForm);
                        professorBoxForm.setVisible(true);
                    }
            );
            tr.start();
        });
    }

    public DepartmentApp() {
        super("Department");
        department = DepartmentsDOMParser.loadDepartmentsFromFile(path);
        contents = new Box(BoxLayout.Y_AXIS);

        buttonsHolder.add(professorAdd);
        buttonsHolder.add(subjectAdd);
        buttonsHolder.add(professorDelete);
        buttonsHolder.add(subjectDelete);

        setButtonsActions();

        professorTable =  new JTable(professorsData, professorsLabels);
        professorTablePane = new JScrollPane(professorTable);
        contents.add(buttonsHolder);
        contents.add(professorTablePane);

        subjectTable =  new JTable(professorsData, professorsLabels);
        subjectTablePane = new JScrollPane(professorTable);
        contents.add(subjectTablePane);


        buildProfessorBoxForm();


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(professorBoxForm);
        add(contents);
        setBounds(300, 90, 900, 600);
        setResizable(false);


        professorsData = professorsToStringData(department.getProfessors());
        subjectData = subjectsToStringData(department.getSubjects());
        rebuildProfessorTable();
        rebuildSubjectTable();
        setVisible(true);
    }


    public static void main(String[] args) {
        new DepartmentApp();
    }
}