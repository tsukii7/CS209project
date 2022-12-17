package com.example.demo.service;

import com.example.demo.model.Issue;
import com.example.demo.repository.IssueRepository;
import com.example.demo.util.GithubRestfulUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    @Autowired
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> getIssues() {
        return issueRepository.findAll();
    }

    public void addIssues() {
        List<String> states = new ArrayList<>();
        List<Long> durations = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        GithubRestfulUtil.getIssues(states, durations, titles, descriptions);
        List<Issue> issues = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
//            issues.add(new Issue(states.get(i), durations.get(i), titles.get(i), descriptions.get(i)));
            issues.add(new Issue(states.get(i), durations.get(i)));
        }
        issueRepository.saveAll(issues);
    }
    
    public long countByState(String state){
        return issueRepository.countByState(state);
    }
}