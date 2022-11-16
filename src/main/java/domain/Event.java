package domain;

import com.google.type.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @SequenceGenerator(name = "event_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "event_seq")
    private int id;
    @ManyToOne(cascade=CascadeType.ALL)
    public User user;
    public String title;
    public String description;
    public String location;
    public DateTime dateTime;


    public Event() {
    }

    public Event(User owner, String title, String description, String location, DateTime date) {
        this.user = owner;
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

    public void setId(int id) {this.id = id;}
    public int getId() {
        return id;
    }

    public User getOwner() {
        return user;
    }

    public void setOwner(User owner) {
        this.user = owner;
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
