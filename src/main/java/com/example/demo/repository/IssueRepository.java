package com.example.demo.repository;

import com.example.demo.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue,Long> {
    long countByRepoNameAndState(String repoName, String state);
    
    List<Issue> findByRepoNameAndDurationIsNot(String repoName,long duration);
}