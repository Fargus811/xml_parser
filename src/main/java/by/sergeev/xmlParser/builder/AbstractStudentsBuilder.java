package by.sergeev.xmlParser.builder;

import by.sergeev.xmlParser.entity.Student;
import by.sergeev.xmlParser.exception.ParserException;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractStudentsBuilder {
    protected Set<Student> students;

    public AbstractStudentsBuilder() {
        students = new HashSet<>();
    }

    public AbstractStudentsBuilder(Set<Student> students) {
        this.students = students;
    }

    public Set<Student> getStudents() {
        return students;
    }

    abstract public void buildSetStudents(String fileName) throws ParserException;
}
