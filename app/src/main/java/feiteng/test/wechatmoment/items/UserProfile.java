package feiteng.test.wechatmoment.items;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The model for Usr
 * including usrName, NickName, Profile, and Avatar.
 */

public class UserProfile {


    String mUsrName;
    String mNickName;
    String mProfileUrl;
    String mAvatarUrl;

    /**
     * Construct an instance through JsonObject, which may from the Internet
     * Please be aware that <code>mProfileUrl</code> may be null, because it's in a comment
     * Here  we catch that exception in this constructor
     *
     * @param jsonObject the json to be resolved.
     * @throws JSONException if anything goes wrong
     */
    public UserProfile(JSONObject jsonObject) throws JSONException {
        mUsrName = jsonObject.getString("username");
        mNickName = jsonObject.getString("nick");
        mAvatarUrl = jsonObject.getString("avatar");
        try {
            mProfileUrl = jsonObject.getString("profile-image");
        } catch (JSONException e) {
            mProfileUrl = null;
        }
    }

    public String getUsrName() {
        return mUsrName;
    }

    public void setUsrName(String mUsrName) {
        this.mUsrName = mUsrName;
    }

    public String getNickName() {
        return mNickName;
    }

    public void setNickName(String mNickName) {
        this.mNickName = mNickName;
    }

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public void setProfileUrl(String mProfileUrl) {
        this.mProfileUrl = mProfileUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String mAvatarUrl) {
        this.mAvatarUrl = mAvatarUrl;
    }

    @Override
    public String toString() {
        return "UsrName:" + mUsrName;
    }
}
