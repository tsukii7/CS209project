package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import javax.persistence.Table;

@Entity
@Table
public class ReleaseCommit {
    @Id
    @GeneratedValue
    private Long id;

    public ReleaseCommit() {
    }
}