package model;

import java.util.List;
import java.util.Map;

public class GraduateStudent extends Student {
    public GraduateStudent(){
        super();
    }

    public GraduateStudent(Name name, String buid) {
        super(name, buid);
    }

    public GraduateStudent(Name name, String buid, int status, double bonus,
                   Map<String, Grade> grades) {
        super(name, buid, status, bonus, grades);
    }
    public GraduateStudent(Name name, String buid, String comment) {
        super(name, buid, comment);
    }
    public GraduateStudent(Name name, String buid, int status, double bonus,
                           Map<String, Grade> grades, String comment) {
        super(name, buid, status, bonus, comment, grades);
    }
}
