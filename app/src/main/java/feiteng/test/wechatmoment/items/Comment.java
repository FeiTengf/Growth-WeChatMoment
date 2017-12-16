package feiteng.test.wechatmoment.items;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The model for Comments
 */

public class Comment {
    private static String JSON_CONTENT = "content";
    private static String JSON_SENDER = "sender";

    //content
    String mContent = "";
    //sender
    UserProfile mSender = new UserProfile();

    /**
     * Construct an instance through JsonObject, which may from the Internet
     * Please be aware that <code>mProfileUrl</code> may be null, because it's in a comment
     *
     * @param jsonObject the json to be resolved.
     * @throws JSONException if anything goes wrong
     */
    public Comment(JSONObject jsonObject) throws JSONException {
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
     * Check whether this comment is empty
     *
     * @return
     */
    public boolean isEmpty() {
        return mContent.isEmpty() && mSender.isEmpty();
    }

}
