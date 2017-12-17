package feiteng.test.wechatmoment.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import feiteng.test.wechatmoment.items.Tweet;
import feiteng.test.wechatmoment.items.UserProfile;

/**
 * The helper class to do all the http & json works for fetching usr's profile and moment info
 * from server.
 * <p>
 * <p>
 * Created by tengf on 2017/12/16.
 */

public class TweetFetcher {
    private static final String TAG = "TweetFetcher";

    private static final String USR_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith";
    private static final String TWEETS_URL = "http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets";
    //10s
    private static final int TIME_OUT = 10000;

    /**
     * get usr info from <code>USR_URL</code>
     *
     * @return the usr profile or null, when json or connection failed.
     */
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
     * get tweet info from <code>TWEETS_URL</code>
     *
     * @return the tweet list or null, when json or connection failed.
     */
    public List<Tweet> fetchTweets() {
        try {
            String jsonString = getUrlString(TWEETS_URL);
            List<Tweet> ret = new ArrayList<Tweet>();
            JSONArray array = (JSONArray) new JSONTokener(jsonString).nextValue();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                String json = obj.toString();
                try {
                    Tweet item = new Tweet(array.getJSONObject(i));
                    ret.add(item);

                } catch (JSONException e) {
                    Log.w(TAG, "invalid tweet");
                }
            }

            return ret;
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
        connection.setConnectTimeout(TIME_OUT);
        connection.setReadTimeout(TIME_OUT);

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, "connection failed. response: " + connection.getResponseCode());
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
     * @return the http content as String, which may be empty if anything was failed
     * @throws IOException something failed during this connection
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
