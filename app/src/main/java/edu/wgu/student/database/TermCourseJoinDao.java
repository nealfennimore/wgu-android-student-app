package edu.wgu.student.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermCourseJoinDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(TermCourseJoinEntity termCourseJoin);

    @Query("SELECT * FROM term " +
            "INNER JOIN term_course_join " +
            "ON term.id=term_course_join.termId " +
            "WHERE term_course_join.courseId=:courseId")
    LiveData<List<TermEntity>> getTermsForCourse(final int courseId);

    @Query("SELECT * FROM course " +
            "INNER JOIN term_course_join " +
            "ON course.id=term_course_join.courseId " +
            "WHERE term_course_join.termId=:termId")
    LiveData<List<CourseEntity>> getCoursesForTerm(final int termId);

    @Query("DELETE FROM term_course_join " +
            "WHERE term_course_join.termId=:termId " +
            "AND term_course_join.courseId=:courseId")
    void delete(final int termId, final int courseId);

    @Query("DELETE FROM term_course_join")
    int deleteAll();
}