package com.example.demo.model;

import jakarta.persistence.*;

import javax.persistence.Table;


@Entity
@Table
public class Developer {
    @Id
    @GeneratedValue
    private Long id;
    private String repoName;
    private String account;
    private int contributions;
    private String avatar;
    private String homepage;

    public Developer() {
    }

    public Developer(String repoName,String account, int contributions, String avatar, String homepage) {
        this.repoName = repoName;
        this.account = account;
        this.contributions = contributions;
        this.avatar = avatar;
        this.homepage = homepage;
    }

    public String getRepoName() {
        return repoName;
    }

    public String getAccount() {
        return account;
    }

    public int getContributions() {
        return contributions;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getHomepage() {
        return homepage;
    }

}
