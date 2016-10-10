package com.theironyard.charlotte.entities;

import javax.persistence.*;

/**
 * Created by mfahrner on 10/10/16.
 */
@Entity
@Table(name = "teams")
public class TeamIdentifier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    public TeamIdentifier() {
    }

    public TeamIdentifier(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
