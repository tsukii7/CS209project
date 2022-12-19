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
        List<String> repoNames = new ArrayList<>();
        List<String> accountList = new ArrayList<>();
        List<Integer> contributionsList = new ArrayList<>();
        List<String> avatarList = new ArrayList<>();
        List<String> homepageList = new ArrayList<>();
        GithubRestfulUtil.getDevelopers(repoNames, accountList, contributionsList, avatarList, homepageList);
        List<Developer> developers = new ArrayList<>();
        for (int i = 0; i < accountList.size(); i++) {
            developers.add(new Developer(
                    repoNames.get(i),
                    accountList.get(i),
                    contributionsList.get(i),
                    avatarList.get(i), homepageList.get(i)));
        }
        developerRepository.saveAll(developers);
    }

    public long countByRepoName(String repoName) {
        return developerRepository.countByRepoName(repoName);
    }

    public List<Developer> getTop5(String repoName) {
        return developerRepository.findTop5ByRepoNameOrderByContributionsDesc(repoName);
    }

    public List<Developer> getAll(String repoName) {
        return developerRepository.findAllByRepoNameOrderByContributionsDesc(repoName);
    }
}