package com.netcracker.practise.eventsplanner.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Андрей Белоусов
 */

/**Task is just a data-keeping structure*/

public class Task {

    private boolean isMissing = false;
    private String name;
    private Date date;
    private String description;
    private List<String> contacts;

    public Task(String name, String description, List<String> contacts, Date date){
        this.name = name;
        this.date = date;
        this.description = description;
        this.contacts = contacts;
    }

    public Task(){
        this.contacts = new ArrayList<>();
    };

    //________________________________________________

    public void addContact(String c){
        contacts.add(c);
    }

    public void setContact(int number, String value) {
        contacts.set(number, value);
    }

    public String getContact(int number){
        return contacts.get(number);
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMissing() {
        return isMissing;
    }

    public void setMissing(boolean missing) {
        isMissing = missing;
    }
}
