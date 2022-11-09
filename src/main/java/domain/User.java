package domain;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    public int owner_id;
    public String username;
    public String password;
    public String email;
    public int securityLevel;


    public User(String username, String password, String email, int securityLevel) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.securityLevel = securityLevel;
    }

    public User() {

    }

    //Getters and Setters

    public int getId() {
        return owner_id;
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

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }
}
