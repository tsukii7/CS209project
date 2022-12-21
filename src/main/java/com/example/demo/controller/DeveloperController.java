package com.example.demo.controller;

import com.example.demo.model.Developer;
import com.example.demo.service.DeveloperService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/developers")
public class DeveloperController {
  private final DeveloperService developerService;

  public DeveloperController(DeveloperService developerService) {
    this.developerService = developerService;
  }

  @GetMapping("/count")
  public long count(@RequestParam String repoName) {
    return developerService.countByRepoName(repoName);
  }

  @GetMapping("/top")
  public List<Developer> getTop5(@RequestParam String repoName) {
    return developerService.getTop5(repoName);
  }

  @GetMapping("/all")
  public List<Developer> getAll(@RequestParam String repoName) {
    return developerService.getAll(repoName);
  }


}