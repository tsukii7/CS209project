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
  private String repoName;
  private String commitTime;

  public Commit(String repoName, String commitTime, String account) {
    this.repoName = repoName;
    this.commitTime = commitTime;
    this.account = account;
  }

  private String account;

  public Commit() {
  }

  public String getRepoName() {
    return repoName;
  }

  public String getCommitTime() {
    return commitTime;
  }

  public String getAccount() {
    return account;
  }


}