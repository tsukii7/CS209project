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
  private Long duration;

  public Issue() {
  }

  public Issue(String repoName, String state, long duration) {
    this.repoName = repoName;
    this.state = state;
    this.duration = duration;
  }

  public Long getId() {
    return id;
  }

  public String getRepoName() {
    return repoName;
  }

  public String getState() {
    return state;
  }

  public long getDuration() {
    return duration;
  }
}