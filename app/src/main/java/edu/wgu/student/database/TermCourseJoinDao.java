package edu.wgu.student.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermCourseJoinDao {
    @Insert
    void insert(TermCourseJoin termCourseJoin);

    @Query("SELECT * FROM term " +
            "INNER JOIN term_course_join " +
            "ON term.id=term_course_join.termId " +
            "WHERE term_course_join.courseId=:courseId")
    List<TermEntity> getTermsForCourse(final int courseId);

    @Query("SELECT * FROM course " +
            "INNER JOIN term_course_join " +
            "ON course.id=term_course_join.courseId " +
            "WHERE term_course_join.termId=:termId")
    List<CourseEntity> getCoursesForTerm(final int termId);
}