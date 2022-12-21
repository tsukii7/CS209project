package com.example.demo.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GithubRestfulUtil {

  //get
  public static void getDevelopers(
      List<String> repoNametList,
      List<String> accountList,
      List<Integer> contributionsList,
      List<String> avatarList,
      List<String> homepageList
  ) {
    getDevelopersByRepo(repoNametList, accountList, contributionsList, avatarList, homepageList, "openai/gym");
    getDevelopersByRepo(repoNametList, accountList, contributionsList, avatarList, homepageList, "babysor/MockingBird");
  }

  private static void getDevelopersByRepo(List<String> repoNametList, List<String> accountList, List<Integer> contributionsList, List<String> avatarList, List<String> homepageList, String repoName) {
    String url = "https://api.github.com/repos/" + repoName + "/contributors?per_page=100&page=";
    int index = 1;
    while (true) {
      try {
        URL restURL = new URL(url + index);
        index++;
        String s = getStringFromURL(restURL);
        if (s.equals("[]")) {
          break;
        }
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject obj = (JSONObject) jsonArray.get(i);
          repoNametList.add(repoName);
          accountList.add((String) obj.get("login"));
          contributionsList.add((Integer) obj.get("contributions"));
          avatarList.add((String) obj.get("avatar_url"));
          homepageList.add((String) obj.get("html_url"));
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  ;

  public static void getIssues(List<String> repoNametList, List<String> stateList, List<Long> durationList) {
    getIssuesByRepo(repoNametList, stateList, durationList, "openai/gym");
    getIssuesByRepo(repoNametList, stateList, durationList, "babysor/MockingBird");
  }

  private static void getIssuesByRepo(List<String> repoNametList, List<String> stateList, List<Long> durationList, String repoName) {
    try {
      String url = "https://api.github.com/repos/" + repoName + "/issues?per_page=100&state=all&page=";
      int index = 1;
      while (true) {
        System.out.println(index);
        URL restURL = new URL(url + index);
        index++;
        String s = getStringFromURL(restURL);
        if (s.equals("[]")) {
          break;
        }
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject obj = (JSONObject) jsonArray.get(i);
          if (obj.get("body").toString().equals("null")) {
            continue;
          }
          repoNametList.add(repoName);
          String state = (String) obj.get("state");
          stateList.add(state);
          if (state.equals("open")) {
            durationList.add(-1L);
          } else {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date stateDate = format.parse((String) obj.get("created_at"));
            Date closeDate = format.parse((String) obj.get("closed_at"));
            durationList.add(closeDate.getTime() / 1000 - stateDate.getTime() / 1000);
          }
        }
      }
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  public static void getCommits(List<String> repoNametList, List<String> accountList, List<String> commitTimeList) {
    getCommitsByRepo(repoNametList, accountList, commitTimeList, "openai/gym");
    getCommitsByRepo(repoNametList, accountList, commitTimeList, "babysor/MockingBird");
  }

  private static void getCommitsByRepo(List<String> repoNametList, List<String> accountList, List<String> commitTimeList, String repoName) {
    String url = "https://api.github.com/repos/" + repoName + "/commits?per_page=100&page=";
    int index = 1;
    while (true) {
      try {
        URL restURL = new URL(url + index);
        index++;
        String s = getStringFromURL(restURL);
        if (s.equals("[]")) {
          break;
        }
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
          String account = "";
          JSONObject obj = (JSONObject) jsonArray.get(i);
          repoNametList.add(repoName);
          if (obj.has("author") && !obj.get("author").toString().equals("null") && ((JSONObject) obj.get("author")).has("login")) {
            account = (String) ((JSONObject) obj.get("author")).get("login");
          } else {
            account = "null";
          }
          JSONObject commit = (JSONObject) obj.get("commit");
          JSONObject committer = (JSONObject) commit.get("committer");
          String nickname = (String) committer.get("name");
          if (account.equals("null")) {
            account = nickname;
          }
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
          Date commitDate = format.parse((String) committer.get("date"));
          SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          accountList.add(account);
          commitTimeList.add(format2.format(commitDate));
        }
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      } catch (JSONException e) {

      }
    }
  }


  public static void getReleases(List<String> repoNametList, List<String> versionList, List<String> releaseTimeList) {
    getReleasesByRepo(repoNametList, versionList, releaseTimeList, "openai/gym");
    getReleasesByRepo(repoNametList, versionList, releaseTimeList, "babysor/MockingBird");
  }

  private static void getReleasesByRepo(List<String> repoNametList, List<String> versionList, List<String> releaseTimeList, String repoName) {
    String url = "https://api.github.com/repos/" + repoName + "/releases?per_page=100&page=";
    int index = 1;
    while (true) {
      try {
        URL restURL = new URL(url + index);
        index++;
        String s = getStringFromURL(restURL);
        if (s.equals("[]")) {
          break;
        }
        JSONArray jsonArray = new JSONArray(s);
        for (int i = 0; i < jsonArray.length(); i++) {
          JSONObject obj = (JSONObject) jsonArray.get(i);
          repoNametList.add(repoName);
          String tag = (String) obj.get("tag_name");
          if (!tag.startsWith("v")) {
            tag = "v" + tag;
          }
          versionList.add(tag);
          SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
          Date publishDate = format.parse((String) obj.get("published_at"));
          SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          releaseTimeList.add(format2.format(publishDate));
        }
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    }
  }

  private static String getStringFromURL(URL restURL) throws IOException {
    String line;
    HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
    conn.setRequestMethod("GET"); // POST GET PUT DELETE
    conn.setRequestProperty("Authorization", "token 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");
    conn.setRequestProperty("Accept", " application/vnd.github+json");
//        FileInputStream f;
//        if ((restURL + "").contains("comments")) {
//            f = new FileInputStream("C:\\Users\\Ksco\\OneDrive\\桌面\\comments.txt");
//
//        } else {
//            f = new FileInputStream("C:\\Users\\Ksco\\OneDrive\\桌面\\contributors.txt");
//        }
    Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
//        Scanner sc = new Scanner(f);
    StringBuilder sb = new StringBuilder();
    while (sc.hasNextLine()) {
      line = sc.nextLine();
      sb.append(line);
    }
    sc.close();
    return sb.toString();
  }
}
