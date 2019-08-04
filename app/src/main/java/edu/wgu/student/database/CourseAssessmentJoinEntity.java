package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "course_assessment_join",
        primaryKeys = { "courseId", "assessmentId" },
        foreignKeys = {
                @ForeignKey(entity = CourseEntity.class,
                        parentColumns = "id",
                        childColumns = "courseId"),
                @ForeignKey(entity = AssessmentEntity.class,
                        parentColumns = "id",
                        childColumns = "assessmentId")
        })
public class CourseAssessmentJoinEntity {
    public int courseId;
    public int assessmentId;

    public CourseAssessmentJoinEntity(int courseId, int assessmentId){
        this.courseId = courseId;
        this.assessmentId = assessmentId;
    }
}