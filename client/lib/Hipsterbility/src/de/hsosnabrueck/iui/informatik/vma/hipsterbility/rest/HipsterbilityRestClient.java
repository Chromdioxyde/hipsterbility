package de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest;

import android.util.Log;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

/**
 * Created by Albert Hoffmann on 23.02.14.
 * Sources: http://loopj.com/android-async-http/
 */
public class HipsterbilityRestClient {


    private final static String TAG = HipsterbilityRestClient.class.getName();
    private static int MAX_RETRIES = 0;
    private static int TIMEOUT = 1000;
    private static int PORT;
    private static String BASE_URL;
    private static int MAX_CONNECTIONS = 5;
    private static AsyncHttpClient client;

    /**
     * Type adapter for GSON to also read 0 and 1 integers as booleans where needed
     * Source: http://stackoverflow.com/questions/11399079/convert-ints-to-booleans
     */
    public static final TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }
        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(in.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d(TAG, "GET: " + getAbsoluteUrl(url));
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d(TAG, "POST: " + getAbsoluteUrl(url));
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        Log.d(TAG, "PUT: " + getAbsoluteUrl(url));
        client.put(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void setMaxConnections(int connections){
        MAX_CONNECTIONS = connections;
        client.setMaxConnections(connections);
    }

    public static void setServer(String server, int port){
        BASE_URL = "http://"+server;
        PORT = port;
        createHTTPClient();
    }

    private static void createHTTPClient() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(PORT);
        asyncHttpClient.setMaxConnections(MAX_CONNECTIONS);
        asyncHttpClient.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
        client = asyncHttpClient;
    }

    public static void setMaxRetriesAndTimeout(int retries, int timeout){
        TIMEOUT = timeout;
        MAX_RETRIES = retries;
        if(client != null){
            client.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
        } else {
            createHTTPClient();
        }
    }

    public static Gson getGsonBooleanWorkaround(){
        return new GsonBuilder()
                .registerTypeAdapter(Boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, HipsterbilityRestClient.booleanAsIntAdapter)
                .setPrettyPrinting().create();
    }

}
