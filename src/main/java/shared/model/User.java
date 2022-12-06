package shared.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "usertable")
public class User {
    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "user_seq")
    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name="user_id")
    private List<Event> events;


    public User(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User() {

    }


    //Getters and Setters

    public void setId(int id) {this.id = id;}
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setSecurityLevel(String role) {
        this.role = role;
    }
}
