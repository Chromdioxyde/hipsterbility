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

/**
 * Created by Albert Hoffmann on 23.02.14.
 * Sources: http://loopj.com/android-async-http/
 */
public class HipsterbilityRestClient {

    private final static String TAG = HipsterbilityRestClient.class.getName();
    //TODO: make this changeable in app
    private static final int PORT = 3000;
    private static final String BASE_URL = "http://192.168.1.22/";

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

    private static AsyncHttpClient client = new AsyncHttpClient(PORT);

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


    /**
     * Sources:     http://www.javacreed.com/gson-deserialiser-example/
     */
    private class SessionDeserializer implements JsonDeserializer<Session> {
        public Session deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {

            // The variable 'json' is passed as a parameter to the deserialize() method
            final JsonObject jsonObject = json.getAsJsonObject();
            final long id = jsonObject.get("idsessions").getAsLong();
            final String name = jsonObject.get("name").getAsString();
            final boolean active = jsonObject.get("active").getAsBoolean();
//            final long appId = jsonObject.get("apps_idapps").getAsLong();
            Session s = new Session();
            s.setId(id);
            s.setName(name);
            s.setActive(active);
            return s;
        }
    }

//    private class SessionListDeserializer implements JsonDeserializer<List<Session>> {
//        public List<Session> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//                throws JsonParseException {
//
//            // The variable 'json' is passed as a parameter to the deserialize() method
//            final JsonObject jsonObject = json.getAsJsonObject();
//            final long id = jsonObject.get("idsessions").getAsLong();
//            final String name = jsonObject.get("name").getAsString();
//            final boolean active = jsonObject.get("active").getAsBoolean();
////            final long appId = jsonObject.get("apps_idapps").getAsLong();
//            Session s = new Session();
//            s.setId(id);
//            s.setName(name);
//            s.setActive(active);
//            return s;
//        }
//    }

}
