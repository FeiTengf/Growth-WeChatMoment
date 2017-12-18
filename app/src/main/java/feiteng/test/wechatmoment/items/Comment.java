package feiteng.test.wechatmoment.items;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The model for Comments
 */

public class Comment {
    private static final String JSON_CONTENT = "content";
    private static final String JSON_SENDER = "sender";

    //content
    private String mContent = "";
    //sender
    private UserProfile mSender = new UserProfile();

    /**
     * Construct an instance through JsonObject, which may from the Internet
     * Please be aware that <code>mProfileUrl</code> may be null, because it's in a comment
     *
     * @param jsonObject the json to be resolved.
     * @throws JSONException if anything goes wrong
     */
    Comment(JSONObject jsonObject) throws JSONException {
        mContent = jsonObject.getString(JSON_CONTENT);
        mSender = new UserProfile(jsonObject.getJSONObject(JSON_SENDER));
    }

    public String getContent() {
        return mContent;
    }

    public UserProfile getSender() {
        return mSender;
    }

    /**
     * Check whether this comment's content
     *
     * @return whether is an empty comment
     */
    public boolean isEmpty() {
        return mContent.isEmpty() && mSender.isEmpty();
    }

}
