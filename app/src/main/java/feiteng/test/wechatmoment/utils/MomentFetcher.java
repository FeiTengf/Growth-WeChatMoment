package feiteng.test.wechatmoment.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import feiteng.test.wechatmoment.items.UserProfile;

/**
 * The helper class to do all the http & json works for fetching usr's profile and moment info
 * from server.
 * <p>
 * <p>
 * Created by tengf on 2017/12/16.
 */

public class MomentFetcher {
    private static final String TAG = "MomentFetcher";

    private static final String USR_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith";
    private static final String TWEETS_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";

    public UserProfile fetchUsr() {
        try {
            String jsonString = getUrlString(USR_URL);
            JSONObject object = new JSONObject(jsonString);
            UserProfile profile = new UserProfile(object);
            return profile;
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        } catch (JSONException e) {
            Log.e(TAG, "Failed to fetch items Json", e);
        }

        return null;
    }

    /**
     * Get the http raw content from spec url
     *
     * @param urlSpec the Url you want to get
     * @return the content in this Url as byte[]
     * @throws IOException something nasty happened during this connection
     */
    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            InputStream in = connection.getInputStream();
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            in.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }


    /**
     * Get Spec Url content as string
     * <p>
     * If there is some kinds of error in connection, an Empty String will be returned
     *
     * @param urlSpec the Url you want to get
     * @return the http content as String, which may be empty
     * @throws IOException something nasty happened during this connection
     */
    public String getUrlString(String urlSpec) throws IOException {
        byte[] bytes = getUrlBytes(urlSpec);
        String ret = "";
        if (bytes != null) {
            ret = new String(bytes);
        }
        return ret;
    }

}
