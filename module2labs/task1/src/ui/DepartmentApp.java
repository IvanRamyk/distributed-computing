package ui;

import entities.Department;
import entities.Professor;
import entities.Subject;
import xml.parsers.DepartmentsDOMParser;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;


public class DepartmentApp extends JFrame {

    private final String path = "resources/departments.xml";


    private final Department department;
    private final Vector<String> professorsLabels = new Vector<String>(Arrays.asList("Id", "First name", "Last name", "Age"));
    private Vector<Vector<String>> professorsData = new Vector<Vector<String>>();

    private Box buttonsHolder = new Box(BoxLayout.X_AXIS);

    private final Vector<String> subjectLabels = new Vector<String>(Arrays.asList("Id", "Name", "Hours", "Professor"));
    private Vector<Vector<String>> subjectData = new Vector<Vector<String>>();

    private JTable professorTable = new JTable(professorsData, professorsLabels);
    private JScrollPane professorTablePane;
    private JButton professorAdd = new JButton("Add professor");
    private JButton professorDelete = new JButton("Delete professor");
    private JButton professorUpdate = new JButton("Update professor");


    private JTable subjectTable = new JTable(subjectData, subjectLabels);
    private JScrollPane subjectTablePane;
    private JButton subjectAdd = new JButton("Add subject");
    private JButton subjectDelete = new JButton("Delete subject");
    private JButton subjectUpdate = new JButton("Update subject");

    private final Box contents;

    private JPanel professorBoxForm;
    private JLabel title;
    private JLabel name;
    private JTextField tname;
    private JLabel lname;
    private JTextField tlname;
    private JTextField tage;
    private JLabel age;
    private JButton sub;
    private JButton reset;
    private JTextArea tout;


    private JPanel subjectBoxForm;
    private JLabel stitle;
    private JLabel sname;
    private JLabel professorLabel;
    private JTextField stname;
    private JTextField thours;
    private JLabel hours;
    private JButton subjectSubmit;
    private JButton subjectReset;
    private JTextArea sTout;
    private JComboBox subjectProfessors;
    private String[] professorsNames;
    private ArrayList<Professor> professors;


    private JPanel deleteProfessorForm;
    private JComboBox deleteProfessor;
    private JLabel deleteProfessorLabel;
    private JButton deleteProfessorButton;

    private String[] subjectsNames;
    private ArrayList<Subject> subjects;

    private JPanel deleteSubjectForm;
    private JComboBox deleteSubject;
    private JLabel deleteSubjectLabel;
    private JButton deleteSubjectButton;



    private String[] buildProfessorsNames(ArrayList<Professor> professors) {
        return professors.stream().map((p) -> p.getFirstName() + " " + p.getLastName()).toArray(String[]::new);
    }

    private String[] buildSubjectsNames(ArrayList<Subject> subjects) {
        return subjects.stream().map((s) -> s.getName()).toArray(String[]::new);
    }

    private void buildSubjectDeleteForm() {
        deleteSubjectForm = new JPanel();
        deleteSubjectForm.setLayout(null);

        stitle = new JLabel("Delete subject");
        stitle.setFont(new Font("Arial", Font.PLAIN, 30));
        stitle.setSize(300, 30);
        stitle.setLocation(300, 30);
        deleteSubjectForm.add(stitle);

        subjects = department.getSubjects();
        subjectsNames = buildSubjectsNames(subjects);

        deleteSubjectLabel = new JLabel("Subject");
        deleteSubjectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        deleteSubjectLabel.setSize(100, 20);
        deleteSubjectLabel.setLocation(100, 250);
        deleteSubjectForm.add(deleteSubjectLabel);

        deleteSubject = new JComboBox(subjectsNames);
        deleteSubject.setFont(new Font("Arial", Font.PLAIN, 15));
        deleteSubject.setSize(150, 20);
        deleteSubject.setLocation(320, 250);
        deleteSubjectForm.add(deleteSubject);

        deleteSubjectButton = new JButton("Delete");
        deleteSubjectButton.setFont(new Font("Arial", Font.PLAIN, 15));
        deleteSubjectButton.setSize(100, 20);
        deleteSubjectButton.setLocation(150, 450);
        deleteSubjectButton.addActionListener(
                e -> {
                    var th = new Thread(
                            () -> {
                                int subjectId = deleteSubject.getSelectedIndex();
                                Long pId = subjects.get(subjectId).getId();
                                try {
                                    department.deleteSubjectById(pId);
                                    rebuildProfessorTable();
                                    rebuildSubjectTable();
                                    contents.setVisible(true);
                                    deleteSubjectForm.setVisible(false);

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }

                            }
                    );
                    th.start();
                }
        );
        deleteSubjectForm.add(deleteSubjectButton);

        subjectReset = new JButton("Back");
        subjectReset.setFont(new Font("Arial", Font.PLAIN, 15));
        subjectReset.setSize(100, 20);
        subjectReset.setLocation(270, 450);
        subjectReset.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        rebuildProfessorTable();
                        rebuildSubjectTable();
                        contents.setVisible(true);
                        deleteSubjectForm.setVisible(false);
                    }
            );
            tr.start();
        });
        deleteSubjectForm.add(subjectReset);
        deleteSubjectForm.setVisible(false);
    }

    private void buildProfessorDeleteForm() {
        deleteProfessorForm = new JPanel();
        deleteProfessorForm.setLayout(null);

        stitle = new JLabel("Delete professor");
        stitle.setFont(new Font("Arial", Font.PLAIN, 30));
        stitle.setSize(300, 30);
        stitle.setLocation(300, 30);
        deleteProfessorForm.add(stitle);

        professors = department.getProfessors();
        professorsNames = buildProfessorsNames(professors);

        deleteProfessorLabel = new JLabel("Professor");
        deleteProfessorForm.setFont(new Font("Arial", Font.PLAIN, 20));
        deleteProfessorLabel.setSize(100, 20);
        deleteProfessorLabel.setLocation(100, 250);
        deleteProfessorForm.add(deleteProfessorLabel);

        deleteProfessor = new JComboBox(professorsNames);
        deleteProfessor.setFont(new Font("Arial", Font.PLAIN, 15));
        deleteProfessor.setSize(150, 20);
        deleteProfessor.setLocation(320, 250);
        deleteProfessorForm.add(deleteProfessor);

        deleteProfessorButton = new JButton("Delete");
        deleteProfessorButton.setFont(new Font("Arial", Font.PLAIN, 15));
        deleteProfessorButton.setSize(100, 20);
        deleteProfessorButton.setLocation(150, 450);
        deleteProfessorButton.addActionListener(
                e -> {
                    var th = new Thread(
                            () -> {
                                int professorId = deleteProfessor.getSelectedIndex();
                                Long pId = professors.get(professorId).getId();
                                try {
                                    department.deleteProfessorsById(pId);
                                    rebuildProfessorTable();
                                    rebuildSubjectTable();
                                    contents.setVisible(true);
                                    deleteProfessorForm.setVisible(false);

                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }

                            }
                    );
                    th.start();
                }
        );
        deleteProfessorForm.add(deleteProfessorButton);

        subjectReset = new JButton("Back");
        subjectReset.setFont(new Font("Arial", Font.PLAIN, 15));
        subjectReset.setSize(100, 20);
        subjectReset.setLocation(270, 450);
        subjectReset.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        rebuildProfessorTable();
                        rebuildSubjectTable();
                        contents.setVisible(true);
                        deleteProfessorForm.setVisible(false);
                    }
            );
            tr.start();
        });
        deleteProfessorForm.add(subjectReset);
        deleteProfessorForm.setVisible(false);
    }


    private void buildSubjectBoxForm(boolean isUpdate) {
        subjectBoxForm = new JPanel();
        subjectBoxForm.setLayout(null);
        if (!isUpdate) {
            stitle = new JLabel("New subject");
            stitle.setFont(new Font("Arial", Font.PLAIN, 30));
            stitle.setSize(300, 30);
            stitle.setLocation(300, 30);
            subjectBoxForm.add(stitle);
        } else {
            deleteSubjectLabel = new JLabel("Subject");
            deleteSubjectLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            deleteSubjectLabel.setSize(150, 30);
            deleteSubjectLabel.setLocation(300, 30);
            subjectBoxForm.add(deleteSubjectLabel);

            subjects = department.getSubjects();
            subjectsNames = buildSubjectsNames(subjects);

            deleteSubject = new JComboBox(subjectsNames);
            deleteSubject.setFont(new Font("Arial", Font.PLAIN, 15));
            deleteSubject.setSize(300, 30);
            deleteSubject.setLocation(450, 30);
            subjectBoxForm.add(deleteSubject);
        }
        sname = new JLabel("Name");
        sname.setFont(new Font("Arial", Font.PLAIN, 20));
        sname.setSize(100, 20);
        sname.setLocation(100, 100);
        subjectBoxForm.add(sname);

        stname = new JTextField();
        stname.setFont(new Font("Arial", Font.PLAIN, 15));
        stname.setSize(190, 20);
        stname.setLocation(200, 100);
        subjectBoxForm.add(stname);

        hours = new JLabel("Hours");
        hours.setFont(new Font("Arial", Font.PLAIN, 20));
        hours.setSize(100, 20);
        hours.setLocation(100, 150);
        subjectBoxForm.add(hours);

        thours = new JTextField();
        thours.setFont(new Font("Arial", Font.PLAIN, 15));
        thours.setSize(150, 20);
        thours.setLocation(200, 150);
        subjectBoxForm.add(thours);

        professors = department.getProfessors();
        professorsNames = buildProfessorsNames(professors);

        professorLabel = new JLabel("Professor");
        professorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        professorLabel.setSize(100, 20);
        professorLabel.setLocation(100, 250);
        subjectBoxForm.add(professorLabel);

        subjectProfessors = new JComboBox(professorsNames);
        subjectProfessors.setFont(new Font("Arial", Font.PLAIN, 15));
        subjectProfessors.setSize(150, 20);
        subjectProfessors.setLocation(320, 250);
        subjectBoxForm.add(subjectProfessors);

        subjectSubmit = new JButton("Submit");
        subjectSubmit.setFont(new Font("Arial", Font.PLAIN, 15));
        subjectSubmit.setSize(100, 20);
        subjectSubmit.setLocation(150, 450);
        if (!isUpdate) {
            subjectSubmit.addActionListener(
                    e -> {
                        var th = new Thread(
                                () -> {
                                    String name = stname.getText();
                                    String shours = thours.getText();
                                    int professorId = subjectProfessors.getSelectedIndex();
                                    Long pId = professors.get(professorId).getId();
                                    try {
                                        int hours = Integer.parseInt(shours);
                                        department.addSubject(department.getNextSubjectId(), name, hours, pId);
                                    } catch (Exception exception) {
                                        exception.printStackTrace();

                                    }
                                    stname.setText("");
                                    thours.setText("");
                                }
                        );
                        th.start();
                    }
            );
        } else  {
            subjectSubmit.addActionListener(
                    e -> {
                        var th = new Thread(
                                () -> {
                                    String name = stname.getText();
                                    if (name.equals(""))
                                        name = null;
                                    String shours = thours.getText();
                                    if (shours.equals(""))
                                        shours = "-1";
                                    int professorId = subjectProfessors.getSelectedIndex();
                                    Long pId = professors.get(professorId).getId();

                                    int subjectId = deleteSubject.getSelectedIndex();
                                    Long sId = subjects.get(subjectId).getId();
                                    try {
                                        int hours = Integer.parseInt(shours);
                                        department.updateSubject(new Subject(sId, name, hours, department.getProfessorById(pId)));
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                    rebuildProfessorTable();
                                    rebuildSubjectTable();
                                    contents.setVisible(true);
                                    subjectBoxForm.setVisible(false);
                                }
                        );
                        th.start();
                    }
            );
        }
        subjectBoxForm.add(subjectSubmit);

        subjectReset = new JButton("Back");
        subjectReset.setFont(new Font("Arial", Font.PLAIN, 15));
        subjectReset.setSize(100, 20);
        subjectReset.setLocation(270, 450);
        subjectReset.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        rebuildProfessorTable();
                        rebuildSubjectTable();
                        contents.setVisible(true);
                        subjectBoxForm.setVisible(false);
                    }
            );
            tr.start();
        });
        subjectBoxForm.add(subjectReset);

        sTout = new JTextArea();
        sTout.setFont(new Font("Arial", Font.PLAIN, 15));
        sTout.setSize(300, 400);
        sTout.setLocation(500, 100);
        sTout.setLineWrap(true);
        sTout.setEditable(false);
        subjectBoxForm.add(sTout);

        subjectBoxForm.setVisible(false);
    }


    private void buildProfessorBoxForm(boolean isUpdate) {
        professorBoxForm = new JPanel();
        professorBoxForm.setLayout(null);
        if (isUpdate) {
            deleteProfessorLabel = new JLabel("Professor");
            deleteProfessorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            deleteProfessorLabel.setSize(150, 30);
            deleteProfessorLabel.setLocation(300, 30);
            professorBoxForm.add(deleteProfessorLabel);

            professors = department.getProfessors();
            professorsNames = buildProfessorsNames(professors);

            deleteProfessor = new JComboBox(professorsNames);
            deleteProfessor.setFont(new Font("Arial", Font.PLAIN, 15));
            deleteProfessor.setSize(150, 30);
            deleteProfessor.setLocation(450, 30);
            professorBoxForm.add(deleteProfessor);

        } else {
            title = new JLabel("New professor");
            title.setFont(new Font("Arial", Font.PLAIN, 30));
            title.setSize(300, 30);
            title.setLocation(300, 30);
            professorBoxForm.add(title);
        }
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

        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 450);
        if (isUpdate) {
            sub.addActionListener(
                    e -> {
                        var th = new Thread(
                                () -> {
                                    String firstName = tname.getText();
                                    String lastName = tlname.getText();
                                    String sAge = tage.getText();
                                    if (firstName.equals(""))
                                        firstName = null;
                                    if (lastName.equals(""))
                                        lastName = null;
                                    if (sAge.equals(""))
                                        sAge = "-1";
                                    int professorId = deleteProfessor.getSelectedIndex();
                                    Long pId = professors.get(professorId).getId();
                                    try {
                                        int age = Integer.parseInt(sAge);
                                        department.updateProfessor(new Professor(pId, firstName, lastName, age, null));
                                    } catch (Exception exception) {
                                        exception.printStackTrace();

                                    }
                                    rebuildProfessorTable();
                                    rebuildSubjectTable();
                                    contents.setVisible(true);
                                    professorBoxForm.setVisible(false);
                                }
                        );
                        th.start();
                    }
            );
        } else {
            sub.addActionListener(
                    e -> {
                        var th = new Thread(
                                () -> {
                                    String firstName = tname.getText();
                                    String lastName = tlname.getText();
                                    String sAge = tage.getText();
                                    try {
                                        int age = Integer.parseInt(sAge);
                                        department.addProfessor(department.getNextProfessorId(), firstName, lastName, age);
                                    } catch (Exception exception) {
                                        exception.printStackTrace();

                                    }
                                    tname.setText("");
                                    tlname.setText("");
                                    tage.setText("");
                                }
                        );
                        th.start();
                    }
            );
        }
        professorBoxForm.add(sub);

        reset = new JButton("Back");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 450);
        reset.addActionListener(e -> {
            var tr = new Thread(
                    () -> {;
                        rebuildProfessorTable();
                        rebuildSubjectTable();
                        contents.setVisible(true);
                        professorBoxForm.setVisible(false);
                    }
            );
            tr.start();
        });
        professorBoxForm.add(reset);

        tout = new JTextArea();
        tout.setFont(new Font("Arial", Font.PLAIN, 15));
        tout.setSize(300, 400);
        tout.setLocation(500, 100);
        tout.setLineWrap(true);
        tout.setEditable(false);
        professorBoxForm.add(tout);

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
        professorsData = professorsToStringData(department.getProfessors());
        contents.remove(professorTablePane);
        professorTable =  new JTable(professorsData, professorsLabels);
        professorTablePane = new JScrollPane(professorTable);
        contents.add(professorTablePane);
    }

    private void rebuildSubjectTable() {
        subjectData = subjectsToStringData(department.getSubjects());
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
                        buildProfessorBoxForm(false);
                        add(professorBoxForm);
                        professorBoxForm.setVisible(true);
                    }
            );
            tr.start();
        });

        subjectAdd.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        contents.setVisible(false);
                        buildSubjectBoxForm(false);
                        add(subjectBoxForm);
                        subjectBoxForm.setVisible(true);
                    }
            );
            tr.start();
        });

        professorDelete.addActionListener(e -> {
            var tr = new Thread(
                () -> {
                    contents.setVisible(false);
                    buildProfessorDeleteForm();
                    add(deleteProfessorForm);
                    deleteProfessorForm.setVisible(true);
                }
            );
            tr.start();
        });

        subjectDelete.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        contents.setVisible(false);
                        buildSubjectDeleteForm();
                        add(deleteSubjectForm);
                        deleteSubjectForm.setVisible(true);
                    }
            );
            tr.start();
        });

        professorUpdate.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        contents.setVisible(false);
                        buildProfessorBoxForm(true);
                        add(professorBoxForm);
                        professorBoxForm.setVisible(true);
                    }
            );
            tr.start();
        });

        subjectUpdate.addActionListener(e -> {
            var tr = new Thread(
                    () -> {
                        contents.setVisible(false);
                        buildSubjectBoxForm(true);
                        add(subjectBoxForm);
                        subjectBoxForm.setVisible(true);
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
        buttonsHolder.add(professorUpdate);
        buttonsHolder.add(subjectUpdate);

        setButtonsActions();

        professorTable =  new JTable(professorsData, professorsLabels);
        professorTablePane = new JScrollPane(professorTable);
        contents.add(buttonsHolder);
        contents.add(professorTablePane);

        subjectTable =  new JTable(professorsData, professorsLabels);
        subjectTablePane = new JScrollPane(professorTable);
        contents.add(subjectTablePane);


        buildProfessorBoxForm(false);
        buildSubjectBoxForm(false);


        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                dispose();
                try {
                    DepartmentsDOMParser.write(department, path);
                } catch (ParserConfigurationException | TransformerException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        });
        add(professorBoxForm);
        add(contents);
        setBounds(300, 90, 900, 600);
        setResizable(false);



        rebuildProfessorTable();
        rebuildSubjectTable();
        setVisible(true);
    }


    public static void main(String[] args) {
        new DepartmentApp();
    }
}