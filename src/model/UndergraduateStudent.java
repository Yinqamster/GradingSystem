package model;

import java.util.List;
import java.util.Map;

public class UndergraduateStudent extends Student {

    public UndergraduateStudent(){
        super();
    }

    public UndergraduateStudent(Name name, String buid) {
        super(name, buid);
    }

    public UndergraduateStudent(Name name, String buid, int status, double bonus,
                                String comment, Map<String, Grade> grades) {
        super(name, buid, status, bonus, comment, grades);
    }
    public UndergraduateStudent(Name name, String buid, String comment) {
        super(name, buid, comment);
    }
}
