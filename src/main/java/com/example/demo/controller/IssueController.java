package com.example.demo.controller;

import com.example.demo.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/openai/issues")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
    
    @GetMapping("/open/count")
    public long getOpenCount(){
        return issueService.countOpenStates();
    }
    
    @GetMapping("/close/count")
    public long getCloseCount(){
        return issueService.countCloseStates();
    }
}