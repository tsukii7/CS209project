package com.example.demo.repository;

import com.example.demo.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeveloperRepository extends JpaRepository<Developer,Long> {
    List<Developer> findTop5ByOrderByContributionsDesc();

}