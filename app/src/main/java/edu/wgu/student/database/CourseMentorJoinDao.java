package edu.wgu.student.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseMentorJoinDao {
    @Insert
    void insert(CourseMentorJoin courseMentorJoin);

    @Query("SELECT * FROM course " +
            "INNER JOIN course_mentor_join " +
            "ON course.id=course_mentor_join.courseId " +
            "WHERE course_mentor_join.mentorId=:mentorId")
    List<CourseEntity> getCoursesForMentor(final int mentorId);

    @Query("SELECT * FROM mentor " +
            "INNER JOIN course_mentor_join " +
            "ON mentor.id=course_mentor_join.mentorId " +
            "WHERE course_mentor_join.courseId=:courseId")
    List<MentorEntity> getMentorsForCourse(final int courseId);
}