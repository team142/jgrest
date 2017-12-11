package com.team142.jgrest.framework.nio;

import com.team142.jgrest.framework.concurrency.DatabasePool;
import com.team142.jgrest.utils.JsonUtils;
import com.team142.jgrest.model.HttpResponseBundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Just1689
 */
public class HttpClient {

    public static void doPostAndForget(DatabasePool databasePool, String path, Object item) throws SocketTimeoutException, TimeoutException {
        databasePool.waitForNext();
        try {

            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(3000);

            JsonUtils.OBJECT_MAPPER.writeValue(connection.getOutputStream(), item);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED && connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
//                System.out.println(output);
            }
            connection.disconnect();

        } catch (SocketTimeoutException ex) {
            databasePool.giveBack();
            throw ex;

        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);

        }
        databasePool.giveBack();

    }

    public static void doPatchAndForget(DatabasePool databasePool, String path, Object item) throws SocketTimeoutException, TimeoutException {
        databasePool.waitForNext();
        try {

            URL url = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(3000);
            setVerb(connection, "PATCH");
//            setRequestMethod(connection, "PATCH");

            JsonUtils.OBJECT_MAPPER.writeValue(connection.getOutputStream(), item);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED
                    && connection.getResponseCode() != HttpURLConnection.HTTP_OK
                    && connection.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {

                System.out.println(connection.getResponseMessage());

                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
//                System.out.println(output);
            }

            //TODO: handle the output
            connection.disconnect();
        } catch (SocketTimeoutException ex) {
//            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
            databasePool.giveBack();
            throw ex;

        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);

        }
        databasePool.giveBack();

    }

    public static void doDeleteAndForget(DatabasePool databasePool, String path) throws SocketTimeoutException, TimeoutException {
        databasePool.waitForNext();
        try {

            URL url = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-type", "application/json");
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(3000);

//            setRequestMethod(connection, "DELETE");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED && connection.getResponseCode() != HttpURLConnection.HTTP_OK && connection.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                databasePool.giveBack();
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            StringBuilder out = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                out.append(output);
            }

            //TODO: handle the output
            connection.disconnect();

        } catch (SocketTimeoutException ex) {
            databasePool.giveBack();
            throw ex;

        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);

        }
        databasePool.giveBack();

    }

    public static String doGet(DatabasePool databasePool, String path) throws SocketException, TimeoutException {
        databasePool.waitForNext();
        try {

            URL url = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED && connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                databasePool.giveBack();
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            StringBuilder out = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                out.append(output);
            }

            //TODO: handle the output
            connection.disconnect();

            databasePool.giveBack();
            return out.toString();

        } catch (SocketException ex) {
            databasePool.giveBack();
            throw ex;
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        databasePool.giveBack();

        return "";

    }

    public static HttpResponseBundle doGetForBundle(DatabasePool databasePool, String path) throws SocketException, TimeoutException {
        databasePool.waitForNext();
        try {

            URL url = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED && connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));

            StringBuilder out = new StringBuilder();
            String output;
            while ((output = br.readLine()) != null) {
                out.append(output);
            }

            Map<String, List<String>> headerFields = connection.getHeaderFields();
            HttpResponseBundle httpResponseBundle = new HttpResponseBundle(out.toString(), headerFields);
            connection.disconnect();

            databasePool.giveBack();
            return httpResponseBundle;

        } catch (SocketException ex) {
            throw ex;
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }

        databasePool.giveBack();
        return null;

    }

    public static <T> void setProtectedFieldValue(Class<T> clazz, String fieldName, T object, Object newValue) throws Exception {
        Field field = clazz.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, newValue);
    }

    private static void setVerb(HttpURLConnection cn, String verb) throws IOException {

        switch (verb) {
            case "GET":
            case "POST":
            case "HEAD":
            case "OPTIONS":
            case "PUT":
            case "DELETE":
            case "TRACE":
                cn.setRequestMethod(verb);
                break;
            default:
                // set a dummy POST verb
                cn.setRequestMethod("POST");
                try {
                    // Change protected field called "method" of public class HttpURLConnection
                    setProtectedFieldValue(HttpURLConnection.class, "method", cn, verb);
                } catch (Exception ex) {
                    throw new IOException(ex);
                }
                break;
        }
    }

}
