package feiteng.test.wechatmoment;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import feiteng.test.wechatmoment.items.Tweet;
import feiteng.test.wechatmoment.items.UserProfile;
import feiteng.test.wechatmoment.utils.MomentFetcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

        // check details with browser's result
        assertEquals("John Smith", profile.getNickName());
        assertEquals("jsmith", profile.getUsrName());
        assertEquals("http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png", profile.getAvatarUrl());
        assertEquals("http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png", profile.getProfileUrl());
    }

    @Test
    public void test_fetch_tweets() throws Exception {
        MomentFetcher fetcher = new MomentFetcher();
        List<Tweet> tweets = fetcher.fetchTweets();

        // check details with browser's result
        assertNotNull(tweets);
        assertEquals(15, tweets.size());

    }
}
