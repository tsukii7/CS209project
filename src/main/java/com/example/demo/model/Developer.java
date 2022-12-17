package com.example.demo.model;

import jakarta.persistence.*;

import javax.persistence.Table;


@Entity
@Table
public class Developer {
    @Id
    @GeneratedValue
    private Long id;
    private String account;
    private int contributions;

    public Developer() {
    }
    public Developer(String account, int contributions) {
        this.account = account;
        this.contributions = contributions;
    }


    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public int getContributions() {
        return contributions;
    }
}
