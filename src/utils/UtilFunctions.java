package utils;

import java.util.Calendar;
import java.util.Date;

public class UtilFunctions {

    public static String getSemester() {
        Calendar date = Calendar.getInstance();
        String season = "";
        if(date.get(Calendar.MONTH) >= 2 && date.get(Calendar.MONTH) <= 5) {
            season = "Spring";
        }
        else if(date.get(Calendar.MONTH) >= 6 && date.get(Calendar.MONTH) <= 8) {
            season = "Summer";
        }
        else if(date.get(Calendar.MONTH) >= 9 && date.get(Calendar.MONTH) <= 12) {
            season = "Fall";
        }
        int year = date.get(Calendar.YEAR);
        return season + " " + String.valueOf(year).substring(1);
    }
}
