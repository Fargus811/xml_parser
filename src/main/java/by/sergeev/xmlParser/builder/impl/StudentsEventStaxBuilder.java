package by.sergeev.xmlParser.builder.impl;

import by.sergeev.xmlParser.builder.AbstractStudentsBuilder;
import by.sergeev.xmlParser.entity.Student;
import by.sergeev.xmlParser.entity.StudentEnum;
import by.sergeev.xmlParser.exception.ParserException;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

public class StudentsEventStaxBuilder extends AbstractStudentsBuilder {
    private static final String DEFAULT_FACULTY = "mmf";
    private XMLInputFactory inputFactory;

    public StudentsEventStaxBuilder() {
        inputFactory = XMLInputFactory.newInstance();
    }

    public StudentsEventStaxBuilder(Set<Student> students) {
        super(students);
        inputFactory = XMLInputFactory.newInstance();
    }

    @Override
    public void buildSetStudents(String fileName) throws ParserException {
        Student student = null;
        XMLEventReader reader;
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            reader = inputFactory.createXMLEventReader(inputStream);
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals(StudentEnum.STUDENT.getValue())) {
                        student = new Student();
                        Attribute login = startElement.getAttributeByName(new QName(StudentEnum.LOGIN.getValue()));
                        student.setLogin(login.getValue());
                        Attribute faculty = startElement.getAttributeByName(new QName(StudentEnum.FACULTY.getValue()));
                        if (faculty != null) {
                            student.setFaculty(faculty.getValue());
                        } else {
                            student.setFaculty(DEFAULT_FACULTY);
                        }
                    } else if (startElement.getName().getLocalPart().equals(StudentEnum.TELEPHONE.getValue())) {
                        event = reader.nextEvent();
                        student.setTelephone(Long.parseLong(event.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals(StudentEnum.NAME.getValue())) {
                        event = reader.nextEvent();
                        student.setName(event.asCharacters().getData());
                    } else if (event.isStartElement()) {
                        StartElement startElementAddress = event.asStartElement();
                        if (startElementAddress.getName().getLocalPart().equals(StudentEnum.COUNTRY.getValue())) {
                            event = reader.nextEvent();
                            student.getAddress().setCountry(event.asCharacters().getData());
                        } else if (startElementAddress.getName().getLocalPart().equals(StudentEnum.CITY.getValue())) {
                            event = reader.nextEvent();
                            student.getAddress().setCity(event.asCharacters().getData());
                        } else if (startElementAddress.getName().getLocalPart().equals(StudentEnum.STREET.getValue())) {
                            event = reader.nextEvent();
                            student.getAddress().setStreet(event.asCharacters().getData());
                        }
                    }
                }
                if (event.isEndElement()) {
                    EndElement endElement = event.asEndElement();
                    if (endElement.getName().getLocalPart().equals(StudentEnum.STUDENT.getValue())) {
                        students.add(student);
                    }
                }
            }
        } catch (XMLStreamException | IOException e) {
            throw new ParserException("Error while parsing XML", e);
        }
    }
}
