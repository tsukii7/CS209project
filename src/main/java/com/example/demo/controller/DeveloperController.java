package com.example.demo.controller;

import com.example.demo.model.Developer;
import com.example.demo.service.DeveloperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/openai/developers")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping("/count")
    public long count(){
        return developerService.count();
    }
    
    @GetMapping("/top")
    public List<Developer> getTop5(){
        return developerService.findTop5OrderByContributionsDesc();
    }
    
    @GetMapping("/all")
    public List<Developer> getAll(){
        return developerService.findAll();
    }


}