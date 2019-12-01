package utils;

public enum ErrCode {
    OK("OK", 0),
    //course service
    COURSEEXIST("Course exists.", 1),

    STUDENTTYPEERROR("Wrong student type", 2)


    ;

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
