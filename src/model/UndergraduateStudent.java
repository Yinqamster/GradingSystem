package model;

public class UndergraduateStudent extends Student {

    public UndergraduateStudent(){
        super();
    }

    public UndergraduateStudent(Name name, String buid) {
        super(name, buid);
    }

    public UndergraduateStudent(Name name, String buid, String comment) {
        super(name, buid, comment);
    }
}
