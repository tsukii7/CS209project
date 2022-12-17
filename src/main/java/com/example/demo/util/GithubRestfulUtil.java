package com.example.demo.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GithubRestfulUtil {

    //get
    public static void getDevelopers(List<String> accountList, List<Integer> contributionsList) {
        String line = "";
        String url = "https://api.github.com/repos/openai/gym/contributors?per_page=100&page=";
        int index = 1;
        while (true) {
            try {
                URL restURL = new URL(url + index);
                index++;
                String s = getStringFromURL(restURL);
//            System.out.println(sb);
                if (s.equals("[]")) {
                    break;
                }
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    accountList.add((String) ((JSONObject) jsonArray.get(i)).get("login"));
                    contributionsList.add((Integer) ((JSONObject) jsonArray.get(i)).get("contributions"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    ;

    public static void getIssues(List<String> stateList, List<Long> durationList, List<String> titles, List<String> descriptions) {
        String line = "";
        String url = "https://api.github.com/repos/openai/gym/issues?per_page=100&state=all&page=";
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
                    String state = (String) ((JSONObject) jsonArray.get(i)).get("state");
                    stateList.add(state);
                    if (state.equals("open")) {
                        durationList.add(-1L);
                    } else {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Date stateDate = format.parse((String) ((JSONObject) jsonArray.get(i)).get("created_at"));
                        Date closeDate = format.parse((String) ((JSONObject) jsonArray.get(i)).get("closed_at"));
                        durationList.add(closeDate.getTime() / 1000 - stateDate.getTime() / 1000);
                    }
                }
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public static void getCommits(List<String> accountList, List<String> commitTimeList) {
        String line = "";
        String url = "https://api.github.com/repos/openai/gym/commits?per_page=100&page=";
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
                    if (((JSONObject) jsonArray.get(i)).get("author").toString().equals("null")) {
                        account = "null";
                    } else {
                        account = (String) ((JSONObject) ((JSONObject) jsonArray.get(i)).get("author")).get("login");
                    }
                    JSONObject commit = (JSONObject) ((JSONObject) jsonArray.get(i)).get("commit");
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
//                break;
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            }
        }
    }


    public static void getReleases(List<String> versionList, List<String> releaseTimeList) {
        String line = "";
        String url = "https://api.github.com/repos/openai/gym/releases?per_page=100&page=";
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
                    String tag = (String) ((JSONObject) jsonArray.get(i)).get("tag_name");
                    if (!tag.startsWith("v")){
                        tag = "v"+tag;
                    }
                    versionList.add(tag);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    Date publishDate = format.parse((String) ((JSONObject) jsonArray.get(i)).get("published_at"));
                    SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
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
//        FileInputStream f = new FileInputStream("C:\\Users\\Ksco\\OneDrive\\桌面\\contributors.txt");
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
