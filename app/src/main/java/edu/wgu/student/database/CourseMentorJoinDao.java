package edu.wgu.student.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseMentorJoinDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE)
    void insert(CourseMentorJoinEntity courseMentorJoin);

    @Query("SELECT * FROM course " +
            "INNER JOIN course_mentor_join " +
            "ON course.id=course_mentor_join.courseId " +
            "WHERE course_mentor_join.mentorId=:mentorId")
    LiveData<List<CourseEntity>> getCoursesForMentor(final int mentorId);

    @Query("SELECT * FROM mentor " +
            "INNER JOIN course_mentor_join " +
            "ON mentor.id=course_mentor_join.mentorId " +
            "WHERE course_mentor_join.courseId=:courseId")
    LiveData<List<MentorEntity>> getMentorsForCourse(final int courseId);


    @Query("DELETE FROM course_mentor_join " +
            "WHERE course_mentor_join.courseId=:courseId " +
            "AND course_mentor_join.mentorId=:mentorId")
    void delete(final int courseId, final int mentorId);

    @Query("DELETE FROM course_mentor_join")
    int deleteAll();
}