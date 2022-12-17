package com.example.demo.model;

import jakarta.persistence.*;

import javax.persistence.Table;


@Entity
@Table
public class Contributors {
    @Id
    @GeneratedValue
    private Long id;
    private String account;
    private int contributions;

}
