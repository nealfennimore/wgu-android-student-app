package edu.wgu.student.models;

import java.time.LocalDateTime;

/**
 * Assessment
 */
public class Assessment {
    private String title;
    private AssessmentType type;
    private LocalDateTime dueDate;

    public Assessment(String title, AssessmentType type, LocalDateTime dueDate){
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
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
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
