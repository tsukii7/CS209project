package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Commit {
    @Id
    @GeneratedValue
    private Long id;
    private String commitTime;
    private String account;

    public Commit() {
    }

    public Commit(String commitTime, String account) {
        this.commitTime = commitTime;
        this.account = account;
    }

    public Long getId() {
        return id;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public String getAccount() {
        return account;
    }
}