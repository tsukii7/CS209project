package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Release {
  @Id
  @GeneratedValue
  private Long id;
  private String repoName;
  private String version;
  private String releaseTime;

  public Release() {
  }

  public Release(String repoName, String version, String releaseTime) {
    this.repoName = repoName;
    this.version = version;
    this.releaseTime = releaseTime;
  }

  public Long getId() {
    return id;
  }

  public String getVersion() {
    return version;
  }

  public String getReleaseTime() {
    return releaseTime;
  }
}