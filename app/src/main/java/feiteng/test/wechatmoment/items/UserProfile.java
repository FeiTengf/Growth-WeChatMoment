package feiteng.test.wechatmoment.items;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * The model for Usr
 * including usrName, NickName, Profile, and Avatar.
 */

public class UserProfile {


    public static final String JSON_USERNAME = "username";
    public static final String JSON_NICKNAME = "nick";
    public static final String JSON_AVATAR = "avatar";
    public static final String JSON_PROFILE_IMG = "profile-image";
    String mUsrName = "";
    String mNickName = "";
    String mProfileUrl = "";
    String mAvatarUrl = "";

    //create an empty profile
    public UserProfile() {

    }


    /**
     * Construct an instance through JsonObject, which may from the Internet
     * Please be aware that <code>mProfileUrl</code> may be empty, because it's can be used in a tweet
     * , or as content. So we allow JSON_PROFILE_IMG to be null
     *
     * @param jsonObject the json to be resolved.
     * @throws JSONException if anything goes wrong
     */
    public UserProfile(JSONObject jsonObject) throws JSONException {
        mUsrName = jsonObject.getString(JSON_USERNAME);
        mNickName = jsonObject.getString(JSON_NICKNAME);
        mAvatarUrl = jsonObject.getString(JSON_AVATAR);
        try {
            mProfileUrl = jsonObject.getString(JSON_PROFILE_IMG);
        } catch (JSONException e) {
            mProfileUrl = "";
        }
    }

    public String getUsrName() {
        return mUsrName;
    }

    public String getNickName() {
        return mNickName;
    }

    public String getProfileUrl() {
        return mProfileUrl;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    /**
     * Whether this usrProfile is an Empty one
     *
     * @return All usr info is Empty
     */
    public boolean isEmpty() {
        return mUsrName.isEmpty() && mNickName.isEmpty() && mProfileUrl.isEmpty() && mAvatarUrl.isEmpty();
    }

    @Override
    public String toString() {
        return "UsrName:" + mUsrName;
    }
}
