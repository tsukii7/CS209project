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
    private String image;
    private String homepage;

    public Developer() {
    }

    public Developer(String account, int contributions, String image, String homepage) {
        this.account = account;
        this.contributions = contributions;
        this.image = image;
        this.homepage = homepage;
    }
}
