package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table
public class Issue {
    @Id
    @GeneratedValue
    private Long id;
    private String repoName;
    private String state;
    private long duration;
    private String title;
    private String description;

    public Issue() {
    }

    public Issue(String repoName,String state, long duration) {
        this.repoName = repoName;
        this.state = state;
        this.duration = duration;
    }

    public Issue(String repoName,String state, long duration, String title, String description) {
        this.repoName = repoName;
        this.state = state;
        this.duration = duration;
        this.title = title;
        this.description = description;
    }
}