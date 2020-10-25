package by.sergeev.xmlParser.builder;

import by.sergeev.xmlParser.builder.impl.StudentsDOMBuilder;
import by.sergeev.xmlParser.builder.impl.StudentsEventStaxBuilder;
import by.sergeev.xmlParser.builder.impl.StudentsSAXBuilder;
import by.sergeev.xmlParser.builder.impl.StudentsStreamStaxBuilder;
import by.sergeev.xmlParser.exception.ParserException;

public class StudentBuilderFactory {
    private enum TypeParser {
        SAX, DOM, EVENT_STAX, STREAM_STAX
    }

    public StudentBuilderFactory() {
    }

    public static AbstractStudentsBuilder createStudentBuilder(String typeParser) throws ParserException {
        TypeParser type = TypeParser.valueOf(typeParser.toUpperCase());
        switch (type) {
            case DOM:
                return new StudentsDOMBuilder();
            case SAX:
                return new StudentsSAXBuilder();
            case EVENT_STAX:
                return new StudentsEventStaxBuilder();
            case STREAM_STAX:
                return new StudentsStreamStaxBuilder();
            default:
                throw new EnumConstantNotPresentException(type.getDeclaringClass(), type.name());
        }
    }
}
