package com.example.demo.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class TitleDescriptionWriter {

    private static String getStringFromURL(URL restURL) throws IOException {
        String line;
        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
        conn.setRequestMethod("GET"); // POST GET PUT DELETE
        conn.setRequestProperty("Authorization", "token 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");
        Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            sb.append(line);
        }
        sc.close();
        return sb.toString();
    }

    private static void writeDescription(String repoName, PrintWriter writer) {
        String url = "https://api.github.com/repos/" + repoName + "/issues?per_page=100&state=all&page=";
        int index = 1;
        while (true) {
            try {
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
                    writer.println((String) obj.get("title"));
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
                    String[] descriptionArray1 = descrip.split("```");
                    StringBuilder text = new StringBuilder();
                    for (int j = 0; j < descriptionArray1.length; j += 2) {
                        String[] descriptionArray2 = descriptionArray1[j].split("``");
                        for (int k = 0; k < descriptionArray2.length; k += 2) {
                            String[] descriptionArray3 = descriptionArray2[k].split("`");
                            for (int l = 0; l < descriptionArray3.length; l += 2) {
                                Arrays.stream(descriptionArray3[l].split(" "))
                                        .filter(x -> !x.contains("](") && !x.startsWith("http"))
                                        .forEach(x -> text.append(x.replace("\r", "").replace("\n", "").trim()).append(" "));
                            }
                        }
                    }
                    writer.println(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.flush();
        writer.close();
    }

    public static void writeWords() {
        try {
            PrintWriter writer1 = new PrintWriter(new BufferedWriter(new FileWriter("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\test_openai.txt", false)));
            System.out.println("开始扒openai");
            writeDescription("openai/gym", writer1);
            System.out.println("爬取openai完毕");


            PrintWriter writer2 = new PrintWriter(new BufferedWriter(new FileWriter("D:\\Program\\Idea\\CS209project\\src\\main\\resources\\test_babysor.txt", false)));
            System.out.println("开始扒babysor");
            writeDescription("babysor/MockingBird", writer2);
            System.out.println("爬取babysor完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}