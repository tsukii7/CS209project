package com.example.demo.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                String s = getSringFromURL(restURL);
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

    public static void getIssues(List<String> stateList, List<Long> durationList) {
        String line = "";
        String url = "https://api.github.com/repos/openai/gym/issues?per_page=100&state=all&page=";
        int index = 1;
        while (true) {
            try {
                URL restURL = new URL(url + index);
                index++;
                String s = getSringFromURL(restURL);
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

    public static void getCommits(List<String> commitTimes, List<String> accounts) {

    }

    public static void getReleases(List<String> versions, List<String> releaseTimes) {

    }

    private static String getSringFromURL(URL restURL) throws IOException {
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
