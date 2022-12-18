package com.example.demo.controller;

import com.example.demo.service.IssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/count")
    public long getOpenCount(@RequestParam String repoName, @RequestParam String state) {
        return issueService.countByRepoNameAndState(repoName, state);
    }

}