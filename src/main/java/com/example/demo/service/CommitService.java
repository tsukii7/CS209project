package com.example.demo.service;

import com.example.demo.model.Commit;
import com.example.demo.repository.CommitRepository;
import com.example.demo.util.GithubRestfulUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitService {
    private final CommitRepository commitRepository;

    @Autowired
    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }
    
    public List<Commit> getCommits(){
        return commitRepository.findAll();
    }
    
    public void addCommits(){
        List<String> commitTimes = new ArrayList<>();
        List<String> accounts = new ArrayList<>();
        GithubRestfulUtil.getCommits(commitTimes,accounts);
        List<Commit> commits = new ArrayList<>();
        for (int i = 0; i < commitTimes.size(); i++) {
            commits.add(new Commit(commitTimes.get(i),accounts.get(i)));
        }
        commitRepository.saveAll(commits);
    }
}