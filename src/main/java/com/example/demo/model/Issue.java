package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Issue {
    @Id
    @GeneratedValue
    private Long id;
    private String state;
    private long duration;

    public Issue() {
    }

    public Issue(String state, long duration) {
        this.state = state;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public long getDuration() {
        return duration;
    }
}