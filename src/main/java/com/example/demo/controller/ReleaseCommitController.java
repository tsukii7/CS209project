package com.example.demo.controller;

import com.example.demo.service.ReleaseCommitService;
import org.springframework.stereotype.Controller;

@Controller
public class ReleaseCommitController {
    private final ReleaseCommitService releaseCommitService;

    public ReleaseCommitController(ReleaseCommitService releaseCommitService) {
        this.releaseCommitService = releaseCommitService;
    }
}