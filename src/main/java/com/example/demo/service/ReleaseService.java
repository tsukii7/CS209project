package com.example.demo.service;

import com.example.demo.model.Release;
import com.example.demo.repository.ReleaseRepository;
import com.example.demo.util.GithubRestfulUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReleaseService {
    private final ReleaseRepository releaseRepository;
    
    @Autowired
    public ReleaseService(ReleaseRepository releaseRepository) {
        this.releaseRepository = releaseRepository;
    }
    
    public List<Release> getReleases(){
        return releaseRepository.findAll();
    }
    
    public void addReleases(){
        List<String> repoNames = new ArrayList<>();
        List<String> versions = new ArrayList<>();
        List<String> releaseTimes = new ArrayList<>();
        GithubRestfulUtil.getReleases(repoNames, versions,releaseTimes);
        List<Release> releases = new ArrayList<>();
        for (int i = 0; i < versions.size(); i++) {
            releases.add(new Release(repoNames.get(i), versions.get(i), releaseTimes.get(i)));
        }
        releaseRepository.saveAll(releases);
    }
}