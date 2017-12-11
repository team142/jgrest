package com.team142.jgrest.framework.nio;

import com.team142.jgrest.framework.concurrency.DatabasePool;
import com.team142.jgrest.model.Database;
import com.team142.jgrest.utils.JsonUtils;
import com.team142.jgrest.model.HttpResponseBundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
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

    private static HttpURLConnection setupConnection(String path, String method) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        setVerb(connection, method);
        connection.setRequestProperty("Content-type", "application/json");
        connection.setConnectTimeout(1000);
        connection.setReadTimeout(3000);
        return connection;

    }

    public static void doPostAndForget(Database database, String path, Object item) throws TimeoutException, SocketTimeoutException {
        DatabasePool databasePool = database.getDatabasePool();
        databasePool.waitForNext();
        try {
            HttpURLConnection connection = setupConnection(path, "POST");

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

    public static void doPatchAndForget(Database database, String path, Object item) throws SocketTimeoutException, TimeoutException {
        DatabasePool databasePool = database.getDatabasePool();
        databasePool.waitForNext();
        try {

            HttpURLConnection connection = setupConnection(path, "PATCH");

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

    public static void doDeleteAndForget(Database database, String path) throws SocketTimeoutException, TimeoutException {
        DatabasePool databasePool = database.getDatabasePool();
        databasePool.waitForNext();
        try {

            HttpURLConnection connection = setupConnection(path, "DELETE");

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

    public static String doGet(Database database, String path) throws SocketException, TimeoutException {
        DatabasePool databasePool = database.getDatabasePool();
        databasePool.waitForNext();
        try {

            HttpURLConnection connection = setupConnection(path, "GET");

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

    public static HttpResponseBundle doGetForBundle(Database database, String path) throws SocketException, TimeoutException {
        DatabasePool databasePool = database.getDatabasePool();
        databasePool.waitForNext();
        try {

            HttpURLConnection connection = setupConnection(path, "GET");

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
