package feiteng.test.wechatmoment;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import feiteng.test.wechatmoment.items.UserProfile;
import feiteng.test.wechatmoment.utils.MomentFetcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FetcherAndroidTest {
    @Test
    public void test_fetch_usrprofile() throws Exception {
        MomentFetcher fetcher = new MomentFetcher();
        UserProfile profile = fetcher.fetchUsr();
        assertNotNull(profile);

        // check details with my chrome result
        assertEquals(profile.getNickName(), "John Smith");
        assertEquals(profile.getUsrName(), "jsmith");
        assertEquals(profile.getAvatarUrl(), "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png");
        assertEquals(profile.getProfileUrl(), "http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png");
    }
}
