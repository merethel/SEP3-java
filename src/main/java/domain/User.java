package domain;

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
    private int securityLevel;

    @OneToMany
    @JoinColumn(name="user_id")
    private List<Event> events;


    public User(String username, String password, String email, int securityLevel) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.securityLevel = securityLevel;
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

    public String getEmail() {
        return email;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }
}
