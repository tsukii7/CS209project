package com.example.demo.controller;

import com.example.demo.model.Release;
import com.example.demo.service.ReleaseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/releases")
public class ReleaseController {
  private final ReleaseService releaseService;

  public ReleaseController(ReleaseService releaseService) {
    this.releaseService = releaseService;
  }

  @GetMapping("/all")
  public List<Release> getByRepoName(@RequestParam String repoName) {
    return releaseService.getByRepoName(repoName);
  }

  @GetMapping("/intervals")
  public List<Integer> getDaysBetweenReleases(@RequestParam String repoName) {
    return releaseService.getDaysBetweenReleases(repoName);
  }
}