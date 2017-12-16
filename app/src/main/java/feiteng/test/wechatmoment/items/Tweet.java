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
    UserProfile mSenderProfile;
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
        mSenderProfile = new UserProfile();
        mComments = new ArrayList<Comment>();

        //Json must has content or image
        if (!isValidTweet(tweetObj)) {
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
        mSenderProfile = new UserProfile(senderObj);
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
     * Check whether it is a valid tweet, which means it must has a content or a image field
     * At the same time, we should check the value of them should not be null or empty
     *
     * @param jsonObj: JsonObject of a tweet, from tweets
     * @return whether it is a valid tweet
     */

    boolean isValidTweet(JSONObject jsonObj) throws JSONException {
        boolean hasContent = jsonObj.has(JSON_CONTENT) && (!jsonObj.getString(JSON_CONTENT).isEmpty());
        boolean hasImage = jsonObj.has(JSON_IMAGE) && (jsonObj.getJSONArray(JSON_IMAGE).length() > 0);
        return hasContent || hasImage;
    }

    public String getContent() {
        return mContent;
    }

    public List<String> getImages() {
        return mImages;
    }

    public UserProfile getSenderProfile() {
        return mSenderProfile;
    }

    public List<Comment> getComments() {
        return mComments;
    }


}
