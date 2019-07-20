package edu.wgu.student.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Ignore;
import java.util.ArrayList;

/**
 * Mentor
 */
@Entity(tableName = "mentor")
public class MentorEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private ArrayList<String> phones;
    private ArrayList<String> emails;

    public MentorEntity( int id, String name, ArrayList<String> phones, ArrayList<String> emails ){
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.emails = emails;
    }

    @Ignore
    public MentorEntity( int id, String name ){
        this.name = name;
        this.phones = new ArrayList<String>();
        this.emails = new ArrayList<String>();
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
     * @return the phones
     */
    public ArrayList<String> getPhones() {
        return phones;
    }

    /**
     * @param phones the phones to set
     */
    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    /**
     * @return the emails
     */
    public ArrayList<String> getEmails() {
        return emails;
    }

    /**
     * @param emails the emails to set
     */
    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "MentorEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phones=" + phones +
                ", emails=" + emails +
                '}';
    }
}