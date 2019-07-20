package edu.wgu.students.models;

import java.util.ArrayList;

/**
 * Mentor
 */
public class Mentor {
    private String name;
    private ArrayList<String> phones;
    private ArrayList<String> emails;

    public Mentor( String name, ArrayList<String> phones, ArrayList<String> emails ){
        this.name = name;
        this.phones = phones;
        this.emails = emails;
    }

    public Mentor( String name ){
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
}