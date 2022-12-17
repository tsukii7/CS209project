package com.example.demo.service;

import com.example.demo.model.Developer;
import com.example.demo.repository.DeveloperRepository;
import com.example.demo.util.GithubRestfulUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    
    public void addDevelopers(){
        List<String> accountList = new ArrayList<>();
        List<Integer> contributionsList = new ArrayList<>();
        GithubRestfulUtil.getDevelopers(accountList,contributionsList);
        List<Developer> developers = new ArrayList<>();
        for (int i = 0; i < accountList.size(); i++) {
            developers.add(new Developer(accountList.get(i), contributionsList.get(i)));
        }
        developerRepository.saveAll(developers);
    }
    
    public long count(){
        return developerRepository.count();
    }
    
    public List<Developer> findAll(){
        return developerRepository.findAll();
    }
    
    public List<Developer> findTop5OrderByContributionsDesc(){
        return developerRepository.findTop5ByOrderByContributionsDesc();
    }
}