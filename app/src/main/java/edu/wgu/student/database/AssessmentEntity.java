package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;

import java.util.Date;

/**
 * Assessment
 */
@Entity(tableName = "assessment")
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private AssessmentType type;
    private Date dueDate;
    private boolean dueDateAlert;

    @Ignore
    public AssessmentEntity() {
    }

    public AssessmentEntity(int id, String title, AssessmentType type, Date dueDate, boolean dueDateAlert){
        this.id = id;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.dueDateAlert = dueDateAlert;
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
     * @return the type
     */
    public AssessmentType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(AssessmentType type) {
        this.type = type;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDueDateAlert() {
        return dueDateAlert;
    }

    public void setDueDateAlert(boolean dueDateAlert) {
        this.dueDateAlert = dueDateAlert;
    }

    @Override
    public String toString() {
        return "AssessmentEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", dueDate=" + dueDate +
                '}';
    }
}
