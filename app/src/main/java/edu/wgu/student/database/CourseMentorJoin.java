package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "course_mentor_join",
        primaryKeys = { "courseId", "mentorId" },
        foreignKeys = {
                @ForeignKey(entity = CourseEntity.class,
                        parentColumns = "id",
                        childColumns = "courseId"),
                @ForeignKey(entity = MentorEntity.class,
                        parentColumns = "id",
                        childColumns = "mentorId")
        })
public class CourseMentorJoin {
    public int courseId;
    public int mentorId;
}