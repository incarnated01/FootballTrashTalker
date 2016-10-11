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

    String abbreviation;

    public TeamIdentifier() {
    }

    public TeamIdentifier(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
