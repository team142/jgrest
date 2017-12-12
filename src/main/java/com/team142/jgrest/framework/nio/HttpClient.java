package com.team142.jgrest.framework.nio;

import com.team142.jgrest.exceptions.JGrestException;
import com.team142.jgrest.framework.api.Settings;
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

    private static HttpURLConnection setupConnection(Database database, String path, String method) throws JGrestException {
        HttpURLConnection connection = null;
        try {
            URL url;
            url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            setVerb(connection, method);
            connection.setRequestProperty("Content-type", "application/json");
            connection.setConnectTimeout(database.getDatabasePool().getTimeoutSecondsMs());
            connection.setReadTimeout(database.getDatabasePool().getReadTimeout());
        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw new JGrestException(ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw new JGrestException(ex);
        }
        return connection;

    }

    public static void doPostAndForget(Database database, String path, Object item) throws JGrestException {
        DatabasePool databasePool = database.getDatabasePool();
        try {
            databasePool.waitForNext();
            HttpURLConnection connection = setupConnection(database, path, "POST");

            JsonUtils.OBJECT_MAPPER.writeValue(connection.getOutputStream(), item);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED && connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            String output = connectionToString(connection);
            connection.disconnect();

        } catch (JGrestException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (TimeoutException | IOException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw new JGrestException(ex);
        }
        databasePool.giveBack();

    }

    public static void doPatchAndForget(Database database, String path, Object item) throws JGrestException {
        DatabasePool databasePool = database.getDatabasePool();
        try {
            databasePool.waitForNext();
            HttpURLConnection connection = setupConnection(database, path, "PATCH");

            JsonUtils.OBJECT_MAPPER.writeValue(connection.getOutputStream(), item);
            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED
                    && connection.getResponseCode() != HttpURLConnection.HTTP_OK
                    && connection.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {

                System.out.println(connection.getResponseMessage());
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            String output = connectionToString(connection);
            connection.disconnect();

        } catch (JGrestException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (TimeoutException | IOException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw new JGrestException(ex);
        }
        databasePool.giveBack();

    }

    public static void doDeleteAndForget(Database database, String path) throws JGrestException {
        DatabasePool databasePool = database.getDatabasePool();
        try {
            databasePool.waitForNext();
            HttpURLConnection connection = setupConnection(database, path, "DELETE");

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED && connection.getResponseCode() != HttpURLConnection.HTTP_OK && connection.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
                databasePool.giveBack();
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            String output = connectionToString(connection);
            connection.disconnect();

        } catch (JGrestException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (TimeoutException | IOException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw new JGrestException(ex);
        }
        databasePool.giveBack();

    }

    public static String doGet(Database database, String path) throws JGrestException {
        if (Settings.DEBUG_ON.get()) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.INFO, "HTTP GET: " + path);
        }
        DatabasePool databasePool = database.getDatabasePool();
        StringBuilder out = new StringBuilder();
        try {
            databasePool.waitForNext();
            HttpURLConnection connection = setupConnection(database, path, "GET");

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED && connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                databasePool.giveBack();
                throw new RuntimeException("Failed : HTTP error code : "
                        + connection.getResponseCode());
            }

            connectionToString(connection, out);
            connection.disconnect();

        } catch (JGrestException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } catch (TimeoutException | IOException ex) {
            databasePool.giveBack();
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
            throw new JGrestException(ex);
        }

        databasePool.giveBack();
        return out.toString();

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

    public static String connectionToString(HttpURLConnection connection) {
        StringBuilder out = new StringBuilder();
        connectionToString(connection, out);
        return out.toString();

    }

    public static void connectionToString(HttpURLConnection connection, StringBuilder out) {
        try {
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(
                    (connection.getInputStream())));
            String output;
            while ((output = br.readLine()) != null) {
                out.append(output);
            }
        } catch (IOException ex) {
            Logger.getLogger(HttpClient.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
