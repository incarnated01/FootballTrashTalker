package com.theironyard.charlotte.entities;

import javax.persistence.*;

/**
 * Created by mfahrner on 10/4/16.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column (nullable = false, unique = true)
    private String userName;

    @Column (nullable = false)
    private String password;

    @Column (nullable = false)
    private String favTeam;

    public User() {
    }

    public User(String username, String password, String favTeam) {
        this.userName = username;
        this.password = password;
        this.favTeam = favTeam;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFavTeam() {
        return favTeam;
    }

    public void setFavTeam(String favTeam) {
        this.favTeam = favTeam;
    }
}
