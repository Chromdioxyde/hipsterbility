package de.hsosnabrueck.iui.informatik.vma.hipsterbility.rest;

import android.util.Log;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import de.hsosnabrueck.iui.informatik.vma.hipsterbility.models.Session;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * Created by Albert Hoffmann on 23.02.14.
 * Sources: http://loopj.com/android-async-http/
 */
public class HipsterbilityRestClient {

    private final static String TAG = HipsterbilityRestClient.class.getName();
    private static final int MAX_RETRIES = 1;
    private static final int TIMEOUT = 10000;
    //TODO: make this changeable in app
    private static int PORT;// = 3000;
    //private static final String BASE_URL = "http://192.168.2.112/";
    private static String BASE_URL;// = "http://192.168.43.67/";
    private static final int DEFAULT_MAX_CONNECTIONS = 5;

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

    private static AsyncHttpClient client = createHTTPClient();

    private static AsyncHttpClient createHTTPClient() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(PORT);
        asyncHttpClient.setMaxConnections(DEFAULT_MAX_CONNECTIONS);
        asyncHttpClient.setMaxRetriesAndTimeout(MAX_RETRIES, TIMEOUT);
        return asyncHttpClient;
    }

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
        client.setMaxConnections(connections);
    }

    public static String testConnectionToServer() {
//        TODO: implement
        return null;
    }

    public static void setServer(String server, int port){
        BASE_URL = "http://"+server+"/";
        client = new AsyncHttpClient(port);
    }
}
