package domain;

import com.google.type.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue
    private int id;
    @ManyToOne(cascade=CascadeType.ALL)
    public User owner_id;
    public String title;
    public String description;
    public String location;
    public DateTime dateTime;


    public Event() {
    }

    public Event(User owner, String title, String description, String location, DateTime date) {
        this.owner_id = owner;
        this.title = title;
        this.description = description;
        this.location = location;
        this.dateTime = date;
    }

    public Event(String title, String description, String location, DateTime date) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.dateTime = date;
    }


    //Getters and Setters

    public int getId() {
        return id;
    }

    public User getOwner() {
        return owner_id;
    }

    public void setOwner(User owner) {
        this.owner_id = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDate(DateTime dateTime) {
        this.dateTime = dateTime;
    }
}
