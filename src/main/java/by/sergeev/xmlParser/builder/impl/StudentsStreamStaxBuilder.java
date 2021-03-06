package by.sergeev.xmlParser.builder.impl;

import by.sergeev.xmlParser.builder.AbstractStudentsBuilder;
import by.sergeev.xmlParser.entity.Student;
import by.sergeev.xmlParser.entity.StudentEnum;
import by.sergeev.xmlParser.exception.ParserException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class StudentsStreamStaxBuilder extends AbstractStudentsBuilder {
    private static final String DEFAULT_FACULTY = "mmf";
    private XMLInputFactory inputFactory;

    public StudentsStreamStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public StudentsStreamStaxBuilder(Set<Student> students) {
        super(students);
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetStudents(String fileName) throws ParserException {
        XMLStreamReader reader;
        String name;
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            reader = inputFactory.createXMLStreamReader(inputStream);
            while (reader.hasNext()) {
                int type = reader.next();
                if (type == XMLStreamConstants.START_ELEMENT) {
                    name = reader.getLocalName();
                    if (name.equals(StudentEnum.STUDENT.getValue())) {
                        Student student = buildStudent(reader);
                        students.add(student);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new ParserException("Error while parsing XML", e);
        }
    }

    private Student buildStudent(XMLStreamReader reader) throws XMLStreamException {
        Student student = new Student();
        student.setLogin(reader.getAttributeValue(null, StudentEnum.LOGIN.getValue()));
        if (reader.getAttributeValue(null, StudentEnum.FACULTY.getValue()) == null
                || reader.getAttributeValue(null, StudentEnum.FACULTY.getValue()).isEmpty()) {
            student.setFaculty(DEFAULT_FACULTY);
        } else {
            student.setFaculty(reader.getAttributeValue(null, StudentEnum.FACULTY.getValue()));
        }
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (StudentEnum.valueOf(name.toUpperCase())) {
                        case NAME:
                            student.setName(getXmlText(reader));
                            break;
                        case TELEPHONE:
                            student.setTelephone(Long.parseLong(getXmlText(reader)));
                            break;
                        case ADDRESS:
                            student.setAddress(getXMLAddress(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (StudentEnum.valueOf(name.toUpperCase()) == StudentEnum.STUDENT) {
                        return student;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag <student>");
    }

    private Student.Address getXMLAddress(XMLStreamReader reader) throws XMLStreamException {
        Student.Address address = new Student().new Address();
        String name;
        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    name = reader.getLocalName();
                    switch (StudentEnum.valueOf(name.toUpperCase())) {
                        case COUNTRY:
                            address.setCountry(getXmlText(reader));
                            break;
                        case CITY:
                            address.setCity(getXmlText(reader));
                            break;
                        case STREET:
                            address.setStreet(getXmlText(reader));
                            break;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    name = reader.getLocalName();
                    if (StudentEnum.valueOf(name.toUpperCase()) == StudentEnum.ADDRESS) {
                        return address;
                    }
                    break;
            }
        }
        throw new XMLStreamException("Unknown element in tag Student");
    }

    private String getXmlText(XMLStreamReader reader) throws XMLStreamException {
        String text = null;
        if (reader.hasNext()) {
            reader.next();
            text = reader.getText();
        }
        return text;
    }
}
