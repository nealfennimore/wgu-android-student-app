package edu.wgu.student.database;

import androidx.room.TypeConverter;

public class CourseStatusConverter {

    @TypeConverter
    public static CourseStatus toCourseStatus(String status){
        return status == null ? null : CourseStatus.valueOf(status);
    }

    @TypeConverter
    public static String toCourseStatusString(CourseStatus status){
        return status == null ? null : status.toString();
    }
}
