package feiteng.test.wechatmoment;

import org.junit.Test;

import java.io.IOException;

import feiteng.test.wechatmoment.items.UserProfile;
import feiteng.test.wechatmoment.utils.MomentFetcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FetcherUnitTest {
    @Test
    public void test_fetching_url() throws Exception {
        MomentFetcher fetcher = new MomentFetcher();
        byte[] testNormal = fetcher.getUrlBytes("http://www.bing.com");
        assertNotNull(testNormal);


        byte[] testThoughtworks = fetcher.getUrlBytes("http://thoughtworks-ios.herokuapp.com/user/jsmith/tweets");
        assertNotNull(testThoughtworks);

        byte[] testNotOk = fetcher.getUrlBytes("https://www.cnblogs.com/ddd.html");
        assertNull(testNotOk);
    }

    @Test
    public void test_fetching_url_string() throws Exception {
        MomentFetcher fetcher = new MomentFetcher();
        String testBing = fetcher.getUrlString("http://www.bing.com");
        assertNotNull(testBing);


        String testThoughtworks = fetcher.getUrlString("http://thoughtworks-ios.herokuapp.com/user/jsmith");
        assertNotNull(testThoughtworks);
        assertFalse(testThoughtworks.isEmpty());

        String testEmpty = fetcher.getUrlString("https://www.cnblogs.com/ddd.html");
        assertNotNull(testEmpty);
        assertTrue(testEmpty.isEmpty());
    }



}