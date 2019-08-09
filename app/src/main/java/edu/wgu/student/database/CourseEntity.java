package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

import java.util.Date;
import java.util.List;

/**
 * Course
 */
@Entity(tableName = "course")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private Date startDate;
    private Date endDate;
    private CourseStatus status;
    private String note;
    private boolean startDateAlert;
    private boolean endDateAlert;

    @Ignore
    public CourseEntity() {
    }

    public CourseEntity(int id, String title, Date startDate, Date endDate, CourseStatus status, String note, boolean startDateAlert, boolean endDateAlert) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.note = note;
        this.startDateAlert = startDateAlert;
        this.endDateAlert = endDateAlert;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStartDateAlert() {
        return startDateAlert;
    }

    public void setStartDateAlert(boolean startDateAlert) {
        this.startDateAlert = startDateAlert;
    }

    public boolean isEndDateAlert() {
        return endDateAlert;
    }

    public void setEndDateAlert(boolean endDateAlert) {
        this.endDateAlert = endDateAlert;
    }

    @Override
    public String toString() {
        return "CourseEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}