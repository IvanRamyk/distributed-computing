package xml.parsers;

import entities.Department;
import entities.Professor;
import entities.Subject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentsDOMParser {
    public static Department loadDepartmentsFromFile(String path) {
        DocumentBuilderFactory dbf = null;
        DocumentBuilder documentBuilder = null;
        try
        {
            dbf = DocumentBuilderFactory.newInstance();
            documentBuilder = dbf.newDocumentBuilder();
        }
        catch(ParserConfigurationException e)
        {
            e.printStackTrace();
        }
        Document document = null;
        try
        {
            document = documentBuilder.parse(path);
            Element root = document.getDocumentElement();
            if (root.getTagName().equals("Department"))
            {
                ArrayList<Professor> resProfessors = new ArrayList<>();
                ArrayList<Subject> resSubjects = new ArrayList<>();
                NodeList professors = root.getElementsByTagName("Professor");
                for (int i=0; i<professors.getLength(); i++)
                {
                    Element professor = (Element)professors.item(i);
                    String id = professor.getAttribute("id");
                    String firstName = professor.getAttribute("firstName");
                    String lastName = professor.getAttribute("lastName");
                    String age = professor.getAttribute("age");
                    System.out.println(id + " " +firstName + ":");
                    Professor professor1 = new Professor(Long.parseLong(id), firstName, lastName, Integer.parseInt(age), new ArrayList<>());
                    resProfessors.add(professor1);
                    NodeList subjects = professor.getElementsByTagName("Subject");
                    for (int j=0; j<subjects.getLength(); j++)
                    {
                        Element subject = (Element)subjects.item(j);
                        String sid = subject.getAttribute("id");
                        String name = subject.getAttribute("name");
                        String hours = subject.getAttribute("hours");
                        System.out.println(" " +name);
                        Subject subject1 = new Subject(Long.parseLong(sid), name, Integer.parseInt(hours), professor1);
                        resSubjects.add(subject1);
                        professor1.getSubjects().add(subject1);
                    }
                }
                return new Department(resProfessors, resSubjects);
            } else {
                System.out.println("Invalid xml file!");
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(Department department, String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        var doc = builder.newDocument();
        Element root = doc.createElement("Department");
        doc.appendChild(root);

        var professors = department.getProfessors();
        var subjects = department.getSubjects();
        for (Professor professor : professors) {
            Element professorElem = doc.createElement("Professor");
            professorElem.setAttribute("id", professor.getId().toString());
            professorElem.setAttribute("firstName", professor.getFirstName());
            professorElem.setAttribute("lastName", professor.getLastName());
            professorElem.setAttribute("age", String.valueOf(professor.getAge()));
            root.appendChild(professorElem);

            for (Subject subject : subjects) {
                if (subject.getProfessor().getId().equals(professor.getId())) {
                    Element subjectElem = doc.createElement("Subject");
                    subjectElem.setAttribute("id", subject.getId().toString());
                    subjectElem.setAttribute("name", subject.getName());
                    subjectElem.setAttribute("hours", String.valueOf(subject.getHours()));
                    professorElem.appendChild(subjectElem);
                }
            }
        }
        Source domSource = new DOMSource(doc);
        Result fileResult = new StreamResult(new File(path));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.transform(domSource, fileResult);
    }
}
