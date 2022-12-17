package com.example.demo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GithubRestfulUtil {

    //get
    public void getMethod(String url) throws IOException {
        URL restURL = new URL(url);



        HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();

        conn.setRequestMethod("GET"); // POST GET PUT DELETE
        conn.setRequestProperty("Authorization", "token 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");
        conn.setRequestProperty("Accept", " application/vnd.github+json");

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

        br.close();
    }

    public static void main(String[] args) {
        GithubRestfulUtil restUtil = new GithubRestfulUtil();
        try {
            restUtil.getMethod("https://api.github.com/repos/openai/gym/contributors?per_page=100&page=");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
