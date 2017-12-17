package feiteng.test.wechatmoment;

import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import feiteng.test.wechatmoment.widgets.LoaderImageView;

import static org.junit.Assert.assertNotNull;

/**
 * Test LoderImageView
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoaderImageViewTest {
    @Test
    public void test_load_bitmap() throws Exception {
        // Context of the app under test.
        String TEST_AVATAR = "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png";
        //this test would faile
        //String TEST_PROFILE = "http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png";

        Context appContext = InstrumentationRegistry.getContext();
        Looper.prepare();
        LoaderImageView view = new LoaderImageView(appContext);
        view.loadUrl(TEST_AVATAR, true);
        //wait for downloading
        Thread.sleep(5000);
        assertNotNull(view.getBackground() != null);
        assertNotNull(LoaderImageView.getImageCache().get(TEST_AVATAR));
    }
}
