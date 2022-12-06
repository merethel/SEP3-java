package shared.model;

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
    @ManyToOne()
    public User user;
    public String title;
    public String description;
    public String location;
    public DateTime dateTime;
    public String category;
    public String area;


    @ManyToMany(fetch = FetchType.EAGER)
    public List<User> attendees;

    public Event() {
    }


    public Event(User user, String title, String description, String location, DateTime dateTime, String category, String area, List<User> attendees) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.location = location;
        this.dateTime = dateTime;
        this.category = category;
        this.area = area;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
