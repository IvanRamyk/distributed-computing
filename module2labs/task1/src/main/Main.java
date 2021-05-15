package main;

import entities.Department;
import xml.parsers.DepartmentsDOMParser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Main {
    public static void main(String[] args) throws TransformerException, ParserConfigurationException {
        Department department = DepartmentsDOMParser.loadDepartmentsFromFile("resources/departments.xml");
        assert department != null;
        department.addProfessor(10L, "Fname", "Lname", 12);
        DepartmentsDOMParser.write(department, "resources/departments.xml");
    }
}
