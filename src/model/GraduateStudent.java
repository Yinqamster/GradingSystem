package model;

import java.util.List;

public class GraduateStudent extends Student {
    public GraduateStudent(){
        super();
    }

    public GraduateStudent(Name name, String buid) {
        super(name, buid);
    }

    public GraduateStudent(Name name, String buid, int status, double bonus,
                   String comment, List<Grade> grades) {
        super(name, buid, status, bonus, comment, grades);
    }
}
