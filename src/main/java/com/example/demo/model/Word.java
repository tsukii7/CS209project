package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Word {
    @Id
    @GeneratedValue
    private Long id;
    private String text;
    private int count;
    private String repoName;

    public Word(String text, int count, String repoName) {
        this.text = text;
        this.count = count;
        this.repoName = repoName;
    }

    public Word() {
        
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getCount() {
        return count;
    }

    public String getRepoName() {
        return repoName;
    }
}