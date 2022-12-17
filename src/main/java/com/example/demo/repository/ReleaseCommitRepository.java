package com.example.demo.repository;

import com.example.demo.model.ReleaseCommit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseCommitRepository extends JpaRepository<ReleaseCommit, Long> {
    
}
