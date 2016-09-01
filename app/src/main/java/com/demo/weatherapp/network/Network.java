package com.demo.weatherapp.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * WeatherApp
 */
public class Network implements Methods {

    private static final boolean D = false;

    private static final String TAG = "Network";

    public static String execute(@NonNull String url, @NonNull String method) {
        if (TextUtils.equals(method, GET) || TextUtils.equals(method, DELETE)){
            return execute(url,method, null);
        } else {
            throw new IllegalStateException("This API is only accessible by GET or DELETE method");
        }
    }

    public static String execute(@NonNull String url, @NonNull String method,
            @Nullable JSONObject payload) {
        StringBuilder sb = null;
        HttpURLConnection conn = null;
        int status = 0;

        try {
            // Create connection
            conn = createConnection(url, method);
            if (conn != null) {
                sb = new StringBuilder();
                if (payload != null && !TextUtils.isEmpty(payload.toString())) {
                    PrintWriter out = new PrintWriter(conn.getOutputStream());
                    out.print(payload);
                    out.close();
                }


                // Get the server response and create JSON from it
                status = conn.getResponseCode();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(conn.getInputStream()));
                // Read Server Response
                String line = null;
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line);
                }
                reader.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return sb != null ? sb.toString() : null;
    }

    @Nullable
    public static HttpURLConnection createConnection(String url, String method) {
        HttpURLConnection conn = null;
        try {
            // Send GET data request
            conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setReadTimeout(10000);

            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoInput(true);
            conn.setDoOutput(TextUtils.equals(method, POST)
                    || TextUtils.equals(method, PUT));
            conn.setRequestMethod(method);

            conn.connect();
            return conn;

        } catch (Exception ex) {
            if (D) {
                ex.printStackTrace();
            }
            if (D) {
                Log.i(TAG, "Exception : " + ex.getMessage());
            }
        }
        return null;
    }

}
