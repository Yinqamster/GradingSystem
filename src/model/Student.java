package model;

import utils.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Student {
    private Name name;
    private String buid;
    private int status;
    private double bonus;
    private String comment;
    private Map<String, Grade> grades;

    public Student(){
        grades = new HashMap<>();
        status = Config.ACTIVE;
    }

    public Student(Name name, String buid) {
        this.name = name;
        this.buid = buid;
    }

    public Student(Name name, String buid, int status, double bonus,
                   String comment, Map<String, Grade> grades) {
        this.name = name;
        this.buid = buid;
        this.status = status;
        this.bonus = bonus;
        this.comment = comment;
        this.grades = grades;
    }
    public Student(Name name, String buid, String comment) {
        this.name = name;
        this.buid = buid;
        this.comment = comment;
    }

    public Student(Name name, String buid, int status, double bonus,
                   Map<String, Grade> grades) {
        this.name = name;
        this.buid = buid;
        this.status = status;
        this.bonus = bonus;
        this.grades = grades;
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

    public Map<String, Grade> getGrades() {
        return grades;
    }

    public void setGrades(Map<String, Grade> grades) {
        this.grades = grades;
    }

    public String toString() {
        return "BUID: " + buid + "\n" + "Name: " + name.getFullName() + "\n"
                 + "Status: " + status + "\n" + "Bonus: " + bonus + "\n";
    }
}
