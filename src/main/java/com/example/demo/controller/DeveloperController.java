package com.example.demo.controller;

import com.example.demo.service.DeveloperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @RequestMapping("/list/developer")
    public String getDevelopers(Model model){
        model.addAttribute("developers",developerService.getDevelopers());
        return "index";
    }
    
    
}