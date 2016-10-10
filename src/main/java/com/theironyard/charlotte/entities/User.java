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
    private int teamId;

    public User() {
    }

    public User(String userName, String password, int teamId) {
        this.userName = userName;
        this.password = password;
        this.teamId = teamId;
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
