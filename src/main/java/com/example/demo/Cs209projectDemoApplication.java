package com.example.demo;

import com.example.demo.service.*;
import com.example.demo.util.TitleDescriptionWriter;
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
            ReleaseService releaseService,
            WordService wordService
    ) {
        return args -> {
//            developerService.addDevelopers();
//            commitService.addCommits();
//            releaseService.addReleases();
//            issueService.addIssues();
////            TitleDescriptionWriter.writeWords();
//            wordService.addWords();
        };
    }
}
