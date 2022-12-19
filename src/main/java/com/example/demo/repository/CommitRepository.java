package com.example.demo.repository;

import com.example.demo.model.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitRepository extends JpaRepository<Commit,Long> {
    List<Commit> findByRepoNameOrderByCommitTimeDesc(String repoName); 
    List<Commit> findByRepoNameOrderByCommitTimeAsc(String repoName); 
}
