package com.soniyasharma.eventbooking.eventbookingsystem.classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {
    public static String get(String getURL) throws Exception {
        URL getUrl = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        } else {
            throw new RuntimeException("Failed: HTTP error code: " + responseCode);
        }
    }

    public static String post(String url, String data, String method) throws Exception{
        URL productUrl = new URL(url);

        HttpURLConnection connection = (HttpURLConnection) productUrl.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.setDoOutput(true);

        try(OutputStream os = connection.getOutputStream()){
            os.write(data.getBytes());
            os.flush();
        }

        int responseCode = connection.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return response.toString();
            }
        }else{
            return "Failed: HTTP error code:" + responseCode;
        }
    }
}
