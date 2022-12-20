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

    public long[] getDurations(String repoName) {
        return issueRepository.findByRepoNameAndDurationIsNot(repoName, -1).stream()
                .mapToLong(Issue::getDuration).toArray();
    }

    public void addIssues() {
        List<String> repoNames = new ArrayList<>();
        List<String> states = new ArrayList<>();
        List<Long> durations = new ArrayList<>();
        GithubRestfulUtil.getIssues(repoNames, states, durations);
        List<Issue> issues = new ArrayList<>();
        for (int i = 0; i < states.size(); i++) {
//            issues.add(new Issue(states.get(i), durations.get(i), titles.get(i), descriptions.get(i)));
            issues.add(new Issue(
                    repoNames.get(i),
                    states.get(i),
                    durations.get(i)
            ));
        }
        issueRepository.saveAll(issues);
    }


    public long countByRepoNameAndState(String repoName, String state) {
        return issueRepository.countByRepoNameAndState(repoName, state);
    }
}