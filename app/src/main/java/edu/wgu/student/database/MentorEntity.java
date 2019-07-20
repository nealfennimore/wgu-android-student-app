package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import java.util.List;

/**
 * Mentor
 */
@Entity(tableName = "mentor")
public class MentorEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String phone;
    private String email;

    public MentorEntity( int id, String name, String phone, String email ){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    @Ignore
    public MentorEntity( int id, String name ){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the phone
     */
    public String getPhones() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhones(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmails() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmails(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "MentorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", email=" + email +
                '}';
    }
}