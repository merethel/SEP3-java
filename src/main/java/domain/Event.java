package domain;

import com.google.type.DateTime;

import javax.persistence.*;
import java.util.List;

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

    @ManyToMany(fetch = FetchType.EAGER)
    public List<User> attendees;

    public Event() {
    }

    public Event(User user, String title, String description, String location, DateTime dateTime, List<User> attendees) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.attendees = attendees;
    }



    //Getters and Setters
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

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public DateTime getDateTime() {
        return dateTime;
    }

}
