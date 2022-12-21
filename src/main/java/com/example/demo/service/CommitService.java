package com.example.demo.service;

import com.example.demo.model.Commit;
import com.example.demo.repository.CommitRepository;
import com.example.demo.util.GithubRestfulUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class CommitService {
  private final CommitRepository commitRepository;

  @Autowired
  public CommitService(CommitRepository commitRepository) {
    this.commitRepository = commitRepository;
  }


  public void addCommits() {
    List<String> repoNames = new ArrayList<>();
    List<String> accounts = new ArrayList<>();
    List<String> commitTimes = new ArrayList<>();
    GithubRestfulUtil.getCommits(repoNames, accounts, commitTimes);
    List<Commit> commits = new ArrayList<>();
    for (int i = 0; i < commitTimes.size(); i++) {
      commits.add(new Commit(repoNames.get(i), commitTimes.get(i), accounts.get(i)));
    }
    commitRepository.saveAll(commits);
  }

  public HashMap<String, int[]> getDistributions(String repoName) {
    List<Commit> commits = commitRepository.findByRepoNameOrderByCommitTimeDesc(repoName);
    int[] weekdayDis = new int[7];
    int[] monthDis = new int[12];
    int[] hourDis = new int[24];
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    commits.stream()
        .map(commit -> {
          try {
            return dateFormat.parse(commit.getCommitTime());
          } catch (ParseException e) {
            throw new RuntimeException(e);
          }
        })
        .forEach(date -> {
          calendar.setTime(date);

          int weekday = calendar.get(Calendar.DAY_OF_WEEK);
          if (calendar.getFirstDayOfWeek() == Calendar.SUNDAY) {
            weekday--;
            if (weekday == 0)
              weekday = 7;
          }
          // 此时 weekday，周一就是1，周日就是7
          weekdayDis[weekday - 1]++;

          // 一月本来就为0，可以直接做索引
          int month = calendar.get(Calendar.MONTH);
          monthDis[month]++;

          // 24小时制，取值[0,23]，可以直接做索引
          int hour = calendar.get(Calendar.HOUR_OF_DAY);
          hourDis[hour]++;
        });
    HashMap<String, int[]> distributions = new HashMap<>();
    distributions.put("hour", hourDis);
    distributions.put("month", monthDis);
    distributions.put("weekday", weekdayDis);
    return distributions;
  }
}