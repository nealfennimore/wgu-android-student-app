package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Course
 */
@Entity(tableName = "course")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CourseStatus status;
    private MentorEntity mentor;
    private ArrayList<Assessment> assessments;

    @Ignore
    public CourseEntity() {
    }

    public CourseEntity(int id, String title, LocalDateTime startDate, LocalDateTime endDate, CourseStatus status, MentorEntity mentor, ArrayList<Assessment> assessments) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentor = mentor;
        this.assessments = assessments;
    }

    @Ignore
    public CourseEntity(String title, LocalDateTime startDate, LocalDateTime endDate, CourseStatus status, MentorEntity mentor ) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentor = mentor;
        this.assessments = new ArrayList<Assessment>();
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the status
     */
    public CourseStatus getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(CourseStatus status) {
        this.status = status;
    }

    /**
     * @return the mentor
     */
    public MentorEntity getMentor() {
        return mentor;
    }

    /**
     * @param mentor the mentor to set
     */
    public void setMentor(MentorEntity mentor) {
        this.mentor = mentor;
    }

    /**
     * @return the assessments
     */
    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    /**
     * @param assessments the assessments to set
     */
    public void setAssessments(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", mentor=" + mentor +
                ", assessments=" + assessments +
                '}';
    }
}