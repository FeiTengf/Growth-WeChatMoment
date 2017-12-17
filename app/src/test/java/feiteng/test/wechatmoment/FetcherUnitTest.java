package feiteng.test.wechatmoment;

import org.junit.Test;

import feiteng.test.wechatmoment.utils.TweetFetcher;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test some function of TweetFetcher
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FetcherUnitTest {
    @Test
    public void test_fetching_url() throws Exception {
        TweetFetcher fetcher = new TweetFetcher();
        byte[] testNormal = fetcher.getUrlBytes("http://www.bing.com");
        assertNotNull(testNormal);


        byte[] testThoughtworks = fetcher.getUrlBytes("http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets");
        assertNotNull(testThoughtworks);

        byte[] testNotOk = fetcher.getUrlBytes("https://www.cnblogs.com/ddd.html");
        assertNull(testNotOk);
    }

    @Test
    public void test_fetching_url_string() throws Exception {
        TweetFetcher fetcher = new TweetFetcher();
        String testBing = fetcher.getUrlString("http://www.bing.com");
        assertNotNull(testBing);


        String testProfile = fetcher.getUrlString("http://thoughtworks-ios.herokuapp.com/user/jsmith");
        assertNotNull(testProfile);
        assertFalse(testProfile.isEmpty());

        String testTweets = fetcher.getUrlString("http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets");
        assertNotNull(testTweets);
        assertFalse(testTweets.isEmpty());

        String testEmpty = fetcher.getUrlString("https://www.cnblogs.com/ddd.html");
        assertNotNull(testEmpty);
        assertTrue(testEmpty.isEmpty());
    }



}