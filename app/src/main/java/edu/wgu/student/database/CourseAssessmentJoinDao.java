package edu.wgu.student.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseAssessmentJoinDao {
    @Insert
    void insert(CourseAssessmentJoin courseAssessmentJoin);

    @Query("SELECT * FROM course " +
            "INNER JOIN course_assessment_join " +
            "ON course.id=course_assessment_join.courseId " +
            "WHERE course_assessment_join.assessmentId=:assessmentId")
    List<CourseEntity> getCoursesForAssessment(final int assessmentId);

    @Query("SELECT * FROM assessment " +
            "INNER JOIN course_assessment_join " +
            "ON assessment.id=course_assessment_join.assessmentId " +
            "WHERE course_assessment_join.courseId=:courseId")
    List<AssessmentEntity> getAssessmentsForCourse(final int courseId);
}