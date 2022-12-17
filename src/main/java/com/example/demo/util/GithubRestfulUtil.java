package com.example.demo.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
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
                HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
                conn.setRequestMethod("GET"); // POST GET PUT DELETE
                conn.setRequestProperty("Authorization", "token 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");
                conn.setRequestProperty("Accept", " application/vnd.github+json");
//            FileInputStream f = new FileInputStream("C:\\Users\\Ksco\\OneDrive\\桌面\\contributors.txt");

//        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Scanner sc = new Scanner(new InputStreamReader(conn.getInputStream()));
//            Scanner sc = new Scanner(f);
                StringBuilder sb = new StringBuilder();
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    sb.append(line);
                }
                sc.close();
//            System.out.println(sb);
                if (sb.toString().equals("[]")) {
                    break;
                }
                JSONArray jsonArray = new JSONArray(sb.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    accountList.add((String) ((JSONObject) jsonArray.get(i)).get("login"));
                    contributionsList.add((Integer) ((JSONObject) jsonArray.get(i)).get("contributions"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

//        for (int i = 0; i < accountList.size(); i++) {
//            System.out.println(accountList.get(i)+"   "+contributorList.get(i));
//        }

    }
}
