package com.example.demo.repository;

import com.example.demo.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
  List<Developer> findTop5ByRepoNameOrderByContributionsDesc(String repoName);

  long countByRepoName(String repoName);

  List<Developer> findByRepoName(String repoName);

  List<Developer> findAllByRepoNameOrderByContributionsDesc(String repoName);

}