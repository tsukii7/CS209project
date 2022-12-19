package com.example.demo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class Main {

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

    private static void getIssuesByRepo(List<String> descriptionList, String repoName) {
        String url = "https://api.github.com/repos/" + repoName + "/issues?per_page=100&state=all&page=";
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
                    if (obj.get("body").toString().equals("null")) {
                        continue;
                    }

                    StringBuilder description = new StringBuilder((String) obj.get("body"));
                    String comments_url = (String) obj.get("comments_url");
                    String comments = getStringFromURL(new URL(comments_url + "?per_page=100"));
                    // get comments of the issue
                    JSONArray commentArray = new JSONArray(comments);
                    for (int j = 0; j < commentArray.length(); j++) {
                        JSONObject comment = (JSONObject) commentArray.get(j);
                        description.append(comment.get("body"));
                    }
                    // process the "body" of comments and issues
                    String descrip = description.toString();
                    String[] descriptionArray = descrip.split("```");
                    StringBuilder text = new StringBuilder();
                    for (int k = 0; k < descriptionArray.length; k++) {
                        if (k % 2 == 0) {
                            String[] words = descriptionArray[k].split("`");
                            for (int j = 0; j < words.length; j++) {
                                if (j % 2 == 0) {
                                    String[] lines = words[j].split("\n");
                                    for (String l : lines) {
                                        if (!l.contains("](")) {
                                            text.append(l);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    descriptionList.add(text.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> descriptionList = new ArrayList<>();
        getIssuesByRepo(descriptionList, "openai/gym");
        BufferedWriter bw1 = new BufferedWriter(new FileWriter("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\openai_description.txt"));
        descriptionList.forEach(s -> {
            try {
                bw1.write(s);
                bw1.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bw1.close();
        descriptionList.clear();
        getIssuesByRepo(descriptionList, "babysor/MockingBird");
        BufferedWriter bw2 = new BufferedWriter(new FileWriter("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\babysor_description.txt"));
        descriptionList.forEach(s -> {
            try {
                bw2.write(s);
                bw2.write("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bw2.close();
    }
}