package ui;

import entities.Department;
import entities.Professor;
import entities.Subject;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Application extends JFrame {
    private static JFrame frame;

    private static Department department;
    private static Professor currentProfessor = null;
    private static Subject currentSubject = null;

    private static boolean editMode = false;
    private static boolean countryMode = true;

    private static JButton btnAddCountry = new JButton("Add Country");
    private static JButton btnAddCity = new JButton("Add City");
    private static JButton btnEdit = new JButton("Edit Data");
    private static JButton btnBack = new JButton("Back");
    private static JButton btnSave = new JButton("Save");
    private static JButton btnDelete = new JButton("Delete");

    private static Box menuPanel = Box.createVerticalBox();
    private static Box actionPanel = Box.createVerticalBox();
    private static Box comboPanel = Box.createVerticalBox();
    private static Box cityPanel = Box.createVerticalBox();
    private static Box countryPanel = Box.createVerticalBox();

    private static JComboBox comboCountry = new JComboBox();
    private static JComboBox comboCity = new JComboBox();

    private static JTextField textCountryName = new JTextField(30);
    private static JTextField textCityName = new JTextField(30);
    private static JTextField textCityCountryName = new JTextField(30);
    private static JTextField textCityPopulation = new JTextField(30);

    private Application() {
        super("Department");

        frame = this;
        frame.setSize(new Dimension(800, 800));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
//                frame.dispose();
//                try {
//                    DOMParser.write(department, "src/main/java/map.xml");
//                } catch (ParserConfigurationException | TransformerException e) {
//                    e.printStackTrace();
//                }
//                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();
        sizeAllElements();
        frame.setLayout(new FlowLayout());

        // Menu
        menuPanel.add(btnAddCountry);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddCountry.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                countryMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                countryPanel.setVisible(true);
                cityPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnAddCity);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddCity.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                countryMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                countryPanel.setVisible(false);
                cityPanel.setVisible(true);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        menuPanel.add(Box.createVerticalStrut(20));
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                countryPanel.setVisible(false);
                cityPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Country:"));
        comboPanel.add(comboCountry);
        comboPanel.add(Box.createVerticalStrut(20));
        comboCountry.addActionListener(e -> {
            String name = (String) comboCountry.getSelectedItem();
            currentProfessor = department.getProfessorByName(name, name); //TODO add last name
            countryMode = true;
            countryPanel.setVisible(true);
            cityPanel.setVisible(false);
            fillProfessorFields();
            pack();
        });
        comboPanel.add(new JLabel("City:"));
        comboPanel.add(comboCity);
        comboPanel.add(Box.createVerticalStrut(20));
        comboCity.addActionListener(e -> {
            String name = (String) comboCity.getSelectedItem();
            currentSubject = department.getSubjectByName(name);
            countryMode = false;
            countryPanel.setVisible(false);
            cityPanel.setVisible(true);
            fillSubjectFields();
            pack();
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        // City Fields
        cityPanel.add(new JLabel("Name:"));
        cityPanel.add(textCityName);
        cityPanel.add(Box.createVerticalStrut(20));
        cityPanel.add(new JLabel("Country Name:"));
        cityPanel.add(textCityCountryName);
        cityPanel.add(Box.createVerticalStrut(20));
        cityPanel.add(new JLabel("Population:"));
        cityPanel.add(textCityPopulation);
        cityPanel.add(Box.createVerticalStrut(20));
        cityPanel.setVisible(false);

        // Country Fields
        countryPanel.add(new JLabel("Name:"));
        countryPanel.add(textCountryName);
        countryPanel.add(Box.createVerticalStrut(20));
        countryPanel.setVisible(false);

        // Action Bar
        actionPanel.add(btnSave);
        actionPanel.add(Box.createVerticalStrut(20));
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                //save();
            }
        });
        actionPanel.add(btnDelete);
        actionPanel.add(Box.createVerticalStrut(20));
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                //delete();
            }
        });
        actionPanel.add(btnBack);
        actionPanel.add(Box.createVerticalStrut(20));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                countryPanel.setVisible(false);
                cityPanel.setVisible(false);
                actionPanel.setVisible(false);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.setPreferredSize(new Dimension(300, 500));
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(countryPanel);
        box.add(cityPanel);
        box.add(actionPanel);
        setContentPane(box);
        //pack();
    }

    private static void sizeAllElements() {
        Dimension dimension = new Dimension(300, 50);
        btnAddCountry.setMaximumSize(dimension);
        btnAddCity.setMaximumSize(dimension);
        btnEdit.setMaximumSize(dimension);
        btnBack.setMaximumSize(dimension);
        btnSave.setMaximumSize(dimension);
        btnDelete.setMaximumSize(dimension);

        btnAddCountry.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddCity.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension panelDimension = new Dimension(300, 300);
        menuPanel.setMaximumSize(panelDimension);
        comboPanel.setPreferredSize(panelDimension);
        actionPanel.setPreferredSize(panelDimension);
        cityPanel.setPreferredSize(panelDimension);
        countryPanel.setPreferredSize(panelDimension);

        comboCountry.setPreferredSize(dimension);
        comboCity.setPreferredSize(dimension);

        textCityCountryName.setPreferredSize(dimension);
        textCityName.setPreferredSize(dimension);
        textCityPopulation.setPreferredSize(dimension);
        textCountryName.setPreferredSize(dimension);
    }

//    private static void save() {
//        if (editMode) {
//            if (countryMode) {
//                var oldName = currentProfessor.getName();
//                var newName = textCountryName.getText();
//                if (changeCountry(currentProfessor) && !currentProfessor.getName().equals(oldName)) {
//                    comboCountry.removeItem(oldName);
//                    comboCountry.addItem(newName);
//                    comboCountry.setSelectedIndex(comboCountry.getItemCount() - 1);
//                }
//            } else {
//                var oldName = currentSubject.getName();
//                var newName = textCityCountryName.getText();
//                if (changeCity(currentSubject) && !currentSubject.getName().equals(oldName)) {
//                    comboCity.removeItem(oldName);
//                    comboCity.addItem(newName);
//                    comboCity.setSelectedIndex(comboCity.getItemCount() - 1);
//                }
//            }
//        } else {
//            if (countryMode) {
//                var country = new Country();
//                department.generateId(country);
//                if (changeCountry(country)) {
//                    department.addCountry(country);
//                    comboCountry.addItem(country.getName());
//                }
//            } else {
//                var city = new City();
//                department.generateId(city);
//                if (changeCity(city)) {
//                    department.addCity(city);
//                    comboCity.addItem(city.getName());
//                }
//            }
//        }
//    }
//
//    private static boolean changeCountry(Country country) {
//        var newName = textCountryName.getText();
//        if (department.getCountry(newName) == null) {
//            department.rename(country, newName);
//            return true;
//        }
//        fillCountryFields();
//        JOptionPane.showMessageDialog(null, "Error: this country already exists!");
//        return false;
//    }
//
//    private static boolean changeCity(City city) {
//        var currCountry = department.getCountry(textCityCountryName.getText());
//        if (currCountry == null) {
//            fillCityFields();
//            JOptionPane.showMessageDialog(null, "Error: no such country!");
//            return false;
//        }
//        var newName = textCityName.getText();
//        if (department.getCity(newName) == null)
//            department.rename(city, newName);
//        department.transferCity(city, currCountry);
//        city.setPopulation(Integer.parseInt(textCityPopulation.getText()));
//        return true;
//    }
//
//    private static void delete() {
//        if (editMode) {
//            if (countryMode) {
//                department.removeCountry(currentProfessor);
//                for (City c : currentProfessor.getCities())
//                    comboCity.removeItem(c.getName());
//                comboCountry.removeItem(currentProfessor.getName());
//            } else {
//                department.removeCity(currentSubject);
//                comboCity.removeItem(currentSubject.getName());
//            }
//        }
//    }
//
    private void fillComboBoxes() {
        comboCountry.removeAllItems();
        comboCity.removeAllItems();
        var professors = department.getProfessors();
        for (Professor p : professors) {
            comboCountry.addItem(p.getFirstName());
//            for (City city : entry.getValue().getCities()) {
//                comboCity.addItem(city.getName());
//            }
        }
    }

    private static void clearFields() {
        textCountryName.setText("");
        textCityName.setText("");
        textCityCountryName.setText("");
        textCityPopulation.setText("");
        currentProfessor = null;
        currentSubject = null;
    }

    private static void fillProfessorFields() {
        if (currentProfessor == null)
            return;
        textCountryName.setText(currentProfessor.getFirstName());
    }

    private static void fillSubjectFields() {
        if (currentSubject == null)
            return;
        //var countries = department.getCountries();
        textCityName.setText(currentSubject.getName());
        textCityCountryName.setText(department.getProfessorById(currentSubject.getProfessor().getId()).getFirstName());
        //textCityPopulation.setText(String.valueOf(currentSubject.getPopulation()));
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        department = new Department();
                //DOMParser.parse("src/main/java/map.xml");
        JFrame myWindow = new Application();
        myWindow.setVisible(true);
    }
}

