package com.ruthiefloats.asynctask;

import android.net.http.AndroidHttpClient;
import android.util.Base64;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {

    /**
     * @param uri the location of the feed on web
     * @return
     */
    public static String getData(String uri) {
        BufferedReader reader = null;
        HttpURLConnection con = null;

        try {
            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            //todo Whats pros cons of specifying MalformedURLException over just Exception?
            //ah, wait, when you added the httpurlconnection, compiler suggested IOException.
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            try {
                int status = con.getResponseCode();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

    public static String getData(String uri, String userName, String password) {
        BufferedReader reader = null;

        byte[] loginBytes = (userName + ":" + password).getBytes();
        StringBuilder loginBuilder = new StringBuilder()
                .append("Basic ")
                .append(Base64.encodeToString(loginBytes, Base64.DEFAULT));


        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.addRequestProperty("Authorization", loginBuilder.toString());

            //todo Whats pros cons of specifying MalformedURLException over just Exception?
            //ah, wait, when you added the httpurlconnection, compiler suggested IOException.
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }

    }

}
