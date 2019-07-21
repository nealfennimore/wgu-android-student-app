package edu.wgu.student.database;

import androidx.room.TypeConverter;

public class AssessmentTypeConverter {

    @TypeConverter
    public static AssessmentType toAssessmentType(String assessmentType){
        return assessmentType == null ? null : AssessmentType.valueOf(assessmentType);
    }

    @TypeConverter
    public static String toAssessmentTypeString(AssessmentType assessmentType){
        return assessmentType == null ? null : assessmentType.toString();
    }
}
