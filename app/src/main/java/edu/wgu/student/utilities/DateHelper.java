package edu.wgu.student.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    static final DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

    static public Date toDate(String date){
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    static public String toFormattedString(Date date){
        return formatter.format(date);
    }
}
