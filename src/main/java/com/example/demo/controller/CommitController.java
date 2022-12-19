package com.example.demo.controller;

import com.example.demo.service.CommitService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequestMapping("/api/commits")
public class CommitController {
    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    @GetMapping("/distributions")
    public HashMap<String, int[]> getDistributions(@RequestParam String repoName) {
        return commitService.getDistributions(repoName);
    }
}