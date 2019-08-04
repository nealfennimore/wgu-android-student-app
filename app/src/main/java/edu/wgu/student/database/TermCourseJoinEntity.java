package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(tableName = "term_course_join",
        primaryKeys = { "termId", "courseId" },
        foreignKeys = {
                @ForeignKey(entity = TermEntity.class,
                        parentColumns = "id",
                        childColumns = "termId"),
                @ForeignKey(entity = CourseEntity.class,
                        parentColumns = "id",
                        childColumns = "courseId")
        })
public class TermCourseJoinEntity {
    public int termId;
    public int courseId;

    public TermCourseJoinEntity( int termId, int courseId ){
        this.termId = termId;
        this.courseId = courseId;
    }
}