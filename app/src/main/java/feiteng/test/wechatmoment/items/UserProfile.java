package feiteng.test.wechatmoment.items;

import android.graphics.Bitmap;

/**
 * Created by inthe on 2017/12/15.
 */

public class UserProfile {
    String mUsrName;
    String mNickName;
    String mProfileUrl;
    String mAvatarUrl;
    Bitmap mProfilePicCache;
    Bitmap mAvatarPicCache;


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

    public Bitmap getProfilePicCache() {
        return mProfilePicCache;
    }

    public void setProfilePicCache(Bitmap mProfilePicCache) {
        this.mProfilePicCache = mProfilePicCache;
    }

    public Bitmap getAvatarPicCache() {
        return mAvatarPicCache;
    }

    public void setAvatarPicCache(Bitmap mAvatarPicCache) {
        this.mAvatarPicCache = mAvatarPicCache;
    }
}
