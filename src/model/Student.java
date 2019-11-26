package model;

import utils.Config;

import java.util.ArrayList;
import java.util.List;

public abstract class Student {
    private Name name;
    private String buid;
    private int status;
    private double bonus;
    private String comment;
    private List<Grade> grades;

    public Student(){
        grades = new ArrayList<>();
        status = Config.ACTIVE;
    }

    public Student(Name name, String buid) {
        this.name = name;
        this.buid = buid;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getBuid() {
        return buid;
    }

    public void setBuid(String buid) {
        this.buid = buid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }
}
