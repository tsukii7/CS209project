package com.example.demo.service;

import com.example.demo.model.Commit;
import com.example.demo.model.Release;
import com.example.demo.repository.CommitRepository;
import com.example.demo.repository.ReleaseRepository;
import com.example.demo.util.GithubRestfulUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ReleaseService {
  private final ReleaseRepository releaseRepository;
  private final CommitRepository commitRepository;

  @Autowired
  public ReleaseService(ReleaseRepository releaseRepository, CommitRepository commitRepository) {
    this.releaseRepository = releaseRepository;
    this.commitRepository = commitRepository;
  }


  public List<Release> getReleases() {
    return releaseRepository.findAll();
  }

  public void addReleases() {
    List<String> repoNames = new ArrayList<>();
    List<String> versions = new ArrayList<>();
    List<String> releaseTimes = new ArrayList<>();
    GithubRestfulUtil.getReleases(repoNames, versions, releaseTimes);
    List<Release> releases = new ArrayList<>();
    for (int i = 0; i < versions.size(); i++) {
      releases.add(new Release(repoNames.get(i), versions.get(i), releaseTimes.get(i)));
    }
    releaseRepository.saveAll(releases);
  }

  public List<Release> getByRepoName(String repoName) {
    return releaseRepository.findByRepoNameOrderByReleaseTimeDesc(repoName);
  }

  public List<Integer> getDaysBetweenReleases(String repoName) {
    List<Commit> commits = commitRepository.findByRepoNameOrderByCommitTimeAsc(repoName);
    List<Release> releases = releaseRepository.findByRepoNameOrderByReleaseTimeAsc(repoName);
    List<Integer> intervals = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int commitIndex = 0;
    try {
      Date commitDate = dateFormat.parse(commits.get(commitIndex).getCommitTime());
      for (Release release : releases) {
        Date releaseDate = dateFormat.parse(release.getReleaseTime());
        int count = 0;
        while (commitDate.before(releaseDate) || commitDate.equals(releaseDate)) {
          count++;
          commitIndex++;
          commitDate = dateFormat.parse(commits.get(commitIndex).getCommitTime());

        }
        intervals.add(count);
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    Collections.reverse(intervals);
    return intervals;
  }
}