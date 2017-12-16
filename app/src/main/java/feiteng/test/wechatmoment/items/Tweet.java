package feiteng.test.wechatmoment.items;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The model for Tweets
 * including usrName, NickName, Profile, and Avatar.
 */

public class Tweet {
    private static String JSON_CONTENT = "content";
    private static String JSON_IMAGE = "images";
    private static String JSON_SENDER = "sender";
    private static String JSON_COMMENT = "comments";
    private static String JSON_URL = "url";

    //content
    String mContent;
    //images
    List<String> mImages;
    //sender
    UserProfile mSender;
    //comments 
    List<Comment> mComments;

    /**
     * Construct an instance through JsonObject, which may from the Internet
     * This Jsonobj must have at content or images. or we will through JsonException
     * <p>
     * If any field is empty or not exist, there will be an empty field, instead of null
     * in this class.
     * <p>
     * Also this tweet may not have a comment, so we check that before parse comment.
     *
     * @param tweetObj the json to be resolved.
     * @throws JSONException if anything goes wrong
     */
    public Tweet(JSONObject tweetObj) throws JSONException {
        //adding default tweet
        mContent = "";
        mImages = new ArrayList<String>();
        mSender = new UserProfile();
        mComments = new ArrayList<Comment>();

        //A Tweet must have a valid senderS
        if (!hasSender(tweetObj)) {
            throw new JSONException("Invalid Tweet: A tweet must have content or images");
        }
        //content
        if (tweetObj.has(JSON_CONTENT)) {
            mContent = tweetObj.getString(JSON_CONTENT);
        }
        //images
        if (tweetObj.has(JSON_IMAGE)) {
            JSONArray imgArray = tweetObj.getJSONArray(JSON_IMAGE);
            for (int i = 0; i < imgArray.length(); i++) {
                JSONObject obj = imgArray.getJSONObject(i);
                mImages.add(obj.getString(JSON_URL));
            }
        }
        //sender
        JSONObject senderObj = tweetObj.getJSONObject(JSON_SENDER);
        mSender = new UserProfile(senderObj);
        //comments
        if (tweetObj.has(JSON_COMMENT)) {
            JSONArray commentArray = tweetObj.getJSONArray(JSON_COMMENT);
            for (int i = 0; i < commentArray.length(); i++) {
                JSONObject obj = commentArray.getJSONObject(i);
                mComments.add(new Comment(obj));
            }
        }

    }


    /**
     * Check whether this tweet has images or content, otherwise it should be ignored
     * At the same time, we should check the value of them should not be null or empty
     *
     * @return whether should be ignored in the tweets list.
     */

    public boolean canbeIgnored() {
        return mContent.isEmpty() && mImages.isEmpty();
    }

    /**
     * Return whether this tweet has a sender field.
     *
     * @param tweetObj jsonObjet that containts a full tweet.
     * @return whether it has a sender
     */
    boolean hasSender(JSONObject tweetObj) {
        return tweetObj.has(JSON_SENDER);
    }

    public String getContent() {
        return mContent;
    }

    public List<String> getImages() {
        return mImages;
    }

    public UserProfile getSender() {
        return mSender;
    }

    public List<Comment> getComments() {
        return mComments;
    }

}
