package com.example.demo.controller;

import com.example.demo.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }
    
    @RequestMapping("/list/issue")
    public String getIssues(Model model){
        model.addAttribute("issues",issueService.getIssues());
        return "index";
    }
}