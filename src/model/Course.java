package model;

import java.util.HashMap;
import java.util.Map;

public class Course {
    private String name;
    private String section;
    private String semester;
    private String description;
    private Breakdown breakdown;
    //buid, student
    Map<String, Student> students;

    public Course() {
        students = new HashMap<>();
    }

    public Course(String name, String section, String semester, String description) {
        this();
        this.name = name;
        this.section = section;
        this.semester = semester;
        this.description = description;
    }

    public Course(String name, String semester, String description) {
        this();
        this.name = name;
        this.semester = semester;
        this.description = description;
    }

    public Course(String name, String section, String semester, String description, Map<String, Student> studentMap) {
        this.students = studentMap;
        this.name = name;
        this.section = section;
        this.semester = semester;
        this.description = description;
    }

    public Course(String name, String section, String semester, String description, Breakdown breakdown) {
        this(name, section, semester, description);
        this.breakdown = breakdown;
    }

    public Course(String name, String section, String semester, String description, Map<String, Student> studentMap, Breakdown breakdown) {
        this(name, section, semester, description, studentMap);
        this.breakdown = breakdown;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Breakdown getBreakdown() {
        return breakdown;
    }

    public void setBreakdown(Breakdown breakdown) {
        this.breakdown = breakdown;
    }

    public Map<String, Student> getStudents() {
        return students;
    }

    public void setStudents(Map<String, Student> students) {
        this.students = students;
    }
}
