package main;

import xml.parsers.DepartmentsDOMParser;

public class Main {
    public static void main(String[] args) {
        DepartmentsDOMParser.loadDepartmentsFromFile("resources/departments.xml");
    }
}
