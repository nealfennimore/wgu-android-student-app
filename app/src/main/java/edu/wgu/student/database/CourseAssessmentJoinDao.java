package edu.wgu.student.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseAssessmentJoinDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseAssessmentJoinEntity courseAssessmentJoin);

    @Query("SELECT * FROM course " +
            "INNER JOIN course_assessment_join " +
            "ON course.id=course_assessment_join.courseId " +
            "WHERE course_assessment_join.assessmentId=:assessmentId")
    List<CourseEntity> getCoursesForAssessment(final int assessmentId);

    @Query("SELECT * FROM assessment " +
            "INNER JOIN course_assessment_join " +
            "ON assessment.id=course_assessment_join.assessmentId " +
            "WHERE course_assessment_join.courseId=:courseId")
    LiveData<List<AssessmentEntity>> getAssessmentsForCourse(final int courseId);

    @Query("DELETE FROM course_assessment_join " +
            "WHERE course_assessment_join.courseId=:courseId " +
            "AND course_assessment_join.assessmentId=:assessmentId")
    void delete(final int courseId, final int assessmentId);

    @Query("DELETE FROM course_assessment_join")
    int deleteAll();
}