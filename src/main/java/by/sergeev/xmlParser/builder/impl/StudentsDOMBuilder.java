package by.sergeev.xmlParser.builder.impl;

import by.sergeev.xmlParser.builder.AbstractStudentsBuilder;
import by.sergeev.xmlParser.entity.Student;
import by.sergeev.xmlParser.entity.StudentEnum;
import by.sergeev.xmlParser.exception.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public class StudentsDOMBuilder extends AbstractStudentsBuilder {

    private static final String DEFAULT_FACULTY = "mmf";
    private DocumentBuilder documentBuilder;

    public StudentsDOMBuilder() throws ParserException {
        init();
    }

    public StudentsDOMBuilder(Set<Student> students) throws ParserException {
        super(students);
        init();
    }

    private void init() throws ParserException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            documentBuilder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new ParserException("Error while creating document builder", e);
        }
    }

    @Override
    public void buildSetStudents(String fileName) throws ParserException {
        Document document;
        try {
            document = documentBuilder.parse(fileName);
            Element root = document.getDocumentElement();
            NodeList studentsList = root.getElementsByTagName(StudentEnum.STUDENT.getValue());
            for (int i = 0; i < studentsList.getLength(); i++) {
                Element studentElement = (Element) studentsList.item(i);
                Student student = buildStudent(studentElement);
                students.add(student);
            }
        } catch (SAXException | IOException e) {
            throw new ParserException("Error while parsing XML", e);
        }
    }

    private Student buildStudent(Element studentElement) {
        Student student = new Student();
        student.setLogin(studentElement.getAttribute(StudentEnum.LOGIN.getValue()));
        if (studentElement.getAttribute(StudentEnum.FACULTY.getValue()) == null || studentElement.getAttribute(StudentEnum.FACULTY.getValue()).isEmpty()) {
            student.setFaculty(DEFAULT_FACULTY);
        } else {
            student.setFaculty(studentElement.getAttribute(StudentEnum.FACULTY.getValue()));
        }
        student.setName(getElementByTagName(studentElement, StudentEnum.NAME.getValue()));
        long telephone = Long.valueOf(getElementByTagName(studentElement, StudentEnum.TELEPHONE.getValue()));
        student.setTelephone(telephone);
        Student.Address address = student.getAddress();
        Element addressElement = (Element) studentElement.getElementsByTagName(StudentEnum.ADDRESS.getValue()).item(0);
        address.setCountry(getElementByTagName(addressElement, StudentEnum.COUNTRY.getValue()));
        address.setCity(getElementByTagName(addressElement, StudentEnum.CITY.getValue()));
        address.setStreet(getElementByTagName(addressElement, StudentEnum.STREET.getValue()));
        return student;
    }

    private String getElementByTagName(Element element, String tag) {
        NodeList nodeList = element.getElementsByTagName(tag);
        Element targetElement = (Element) nodeList.item(0);
        return targetElement.getTextContent();
    }

}
