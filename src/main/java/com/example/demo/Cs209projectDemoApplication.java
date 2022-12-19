package com.example.demo;

import com.example.demo.service.CommitService;
import com.example.demo.service.DeveloperService;
import com.example.demo.service.IssueService;
import com.example.demo.service.ReleaseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Cs209projectDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cs209projectDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            DeveloperService developerService, 
            IssueService issueService,
            CommitService commitService,
            ReleaseService releaseService
    ) {
        return args -> {
//            developerService.addDevelopers();
//            issueService.addIssues();
            commitService.addCommits();
            releaseService.addReleases();
        };
    }
}
