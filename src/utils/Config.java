package utils;

public class Config {
    public static final String CURRENT_SEMETER = "Fall 2019";

    public static final int FREEZE = 0;
    public static final int ACTIVE = 1;

    public static final String FINALRULENAME = "final";

    //course type
    public static final int NOTIMPORT = 0;
    public static final int BREAKDOWN = 1;
    public static final int TEMPLATE = 2;

    //student type
    public static final String UNDERGRADUATE = "Undergraduate";
    public static final String GRADUATE = "Graduate";

    // ShowEditStudent Type
    public static final int ADDNEWSTUDENT = 0; // add a new student
    public static final int EDITSTUDENT = 1; // edit a saved student
    public static final int EDITSTUDENTCOMMENT = 2; // edit a saved student
    public static final int ADDEDITCOMMENT = 3; // add or edit the comment for a certain grade of a certain student
}
