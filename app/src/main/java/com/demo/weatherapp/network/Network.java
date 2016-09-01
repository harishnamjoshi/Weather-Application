package com.demo.weatherapp.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.demo.weatherapp.BuildConfig;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Weather Application 1.0
 *
 * <p>
 *      Network utility to perform the network protocols. This will have the capability of following
 *      network request.
 *      <ul>
 *          <li>{@link #GET}</li>
 *
 *          <li>{@link #PUR}</li>
 *
 *          <li>{@link #POST}</li>
 *
 *          <li>{@link #DELETE}</li>
 *      </ul>
 *
 *      This will implement the {@link Methods} interface.
 * </p>
 */
public class Network implements Methods {

    /**
     * Debug field to use it over logging.
     */
    private static final boolean D = false || BuildConfig.DEBUG;

    /**
     * Tags used inside log.
     */
    private static final String TAG = "Network";


    /**
     * This method will perform the network call with the paramter passed. Internally this will
     * call {@link #execute(String, String, JSONObject)} to create the url connection.
     * <p>
     *     Although method will check for the valid method type. If method type {@link #POST} or
     *     {@link #PUT} passed it will throw a IllegalStateException as there is not payload passed
     *     under this.
     * </p>
     *
     * @param url to which connection needs to establish
     * @param method the method which this request is of.
     *
     * @return response body which will be returned by the server, in json string format.
     */

    public static String execute(@NonNull String url, @NonNull String method) {
        if (TextUtils.equals(method, GET) || TextUtils.equals(method, DELETE)){
            return execute(url,method, null);
        } else {
            throw new IllegalStateException("This API is only accessible by GET or DELETE method");
        }
    }

    /**
     * This method will perform the network call with the paramter passed. Internally this will
     * call {@link #createConnection(String, String)} to create the url connection.
     *
     * @param url to which connection needs to establish
     * @param method the method which this request is of.
     *
     * @param payload which will be passed to the server.
     *
     * @return response body which will be returned by the server, in json string format.
     */
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

    /**
     * Method which will create {@link HttpURLConnection} using url and method passed. Connection
     * will have 10 sec time out.
     *
     *
     * @param url to which connection needs to establish
     * @param method the method which this request is of.
     *
     * @return {@link HttpURLConnection} to perform network protocol.
     */
    @Nullable
    public static HttpURLConnection createConnection(@NonNull String url, @NonNull String method) {
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
