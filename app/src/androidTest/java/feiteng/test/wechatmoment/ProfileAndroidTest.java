package feiteng.test.wechatmoment;

import android.support.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import feiteng.test.wechatmoment.items.UserProfile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test some function of the class UserProfile
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ProfileAndroidTest {
    @Test
    public void test_jport() throws Exception {
        JSONObject object = new JSONObject("{\n" +
                "      \"username\": \"jport\",\n" +
                "      \"nick\": \"Joe Portman\",\n" +
                "      \"avatar\": \"https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcRJm8UXZ0mYtjv1a48RKkFkdyd4kOWLJB0o_l7GuTS8-q8VF64w\"\n" +
                "    }");

        UserProfile profile = new UserProfile(object);

        assertNotNull(profile);
        assertEquals("jport", profile.getUsrName());
    }


}
