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


    public void addDevelopers() {
        List<String> accountList = new ArrayList<>();
        List<Integer> contributionsList = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<String> homepages = new ArrayList<>();
        GithubRestfulUtil.getDevelopers(accountList, contributionsList, images, homepages);
        List<Developer> developers = new ArrayList<>();
        for (int i = 0; i < accountList.size(); i++) {
            developers.add(new Developer(
                    accountList.get(i),
                    contributionsList.get(i), 
                    images.get(i), homepages.get(i)));
        }
        developerRepository.saveAll(developers);
    }

    public long count() {
        return developerRepository.count();
    }

    public List<Developer> findTop5OrderByContributionsDesc() {
        return developerRepository.findTop5ByOrderByContributionsDesc();
    }

    public List<Developer> findAll() {
        return developerRepository.findAll();
    }
}