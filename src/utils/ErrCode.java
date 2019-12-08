package utils;

public enum ErrCode {
    OK("OK", 0),
    //course service
    COURSEEXIST("Course exists.", 101),
    COURSENOTEXIST("Course exists.", 102),

    // Student service
    STUDENTEXIST("Student already exists.", 201),
    STUDENTNOTEXIST("Student doesn't exist.", 202),
    STUDENTTYPEERROR("Wrong student type", 203),
    IMPORTERROR("Student import error", 204),

    // views
    TEXTFIELDEMPTY("Please fill in all textfields with *.", 301),
    ADDCOURSEFAIL("Fail to add course", 302),

    //grading rule service
    DELETEGRADINGRULEERROR("There is an error while deleting grading rule", 401),

    //database error
    UPDATEERROR("There is an error while updating table", 501),
    DELETEERROR("There is an error while deleting table", 502),

    // breakdown
    BREAKDOWNNOTEXIST("Breakdown doesn't exist.", 601);

    private final String description;
    private final int code;

    private ErrCode(String description, int code){
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
