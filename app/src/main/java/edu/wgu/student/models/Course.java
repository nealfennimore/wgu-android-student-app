package edu.wgu.students.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * CourseStatus
 */
public enum CourseStatus {
    IN_PROGRESS,
    COMPLETED, 
    DROPPED, 
    PLANNED,
};

/**
 * Course
 */
public class Course {
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CourseStatus status;
    private Mentor mentor;
    private ArrayList<Assessment> assessments;

    public Course(String title, LocalDateTime startDate, LocalDateTime endDate, CourseStatus status, Mentor mentor, ArrayList<Assessment> assessments) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentor = mentor;
        this.assessments = assessments;
    }

    public Course(String title, LocalDateTime startDate, LocalDateTime endDate, CourseStatus status, Mentor mentor ) {
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
    public Mentor getMentor() {
        return mentor;
    }

    /**
     * @param mentor the mentor to set
     */
    public void setMentor(Mentor mentor) {
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
}