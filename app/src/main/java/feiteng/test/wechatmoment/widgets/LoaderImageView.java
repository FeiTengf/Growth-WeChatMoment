package feiteng.test.wechatmoment.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.LruCache;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by inthe on 2017/12/16.
 */

public class LoaderImageView extends AppCompatImageView {

    private static LruCache<String, Drawable> sImageCache = new LruCache<String, Drawable>(20);

    private String mUrl;
    private String TAG = "LoaderImageView";
    private static final int MSG_DOWNLOAD_COMPLETE = 1;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_DOWNLOAD_COMPLETE) {
                LoaderImageView.this.setImageDrawable((Drawable) msg.obj);
            }
        }
    };

    public LoaderImageView(Context context) {
        super(context);
    }

    public LoaderImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    public void loadUrl(String urlToBeLoad) {
        mUrl = urlToBeLoad;
        Drawable cached = sImageCache.get(urlToBeLoad);
        if (cached != null) {
            this.setBackgroundDrawable(cached);
            return;
        }

        new Thread() {
            @Override
            public void run() {
                Drawable drawable = LoadImageFromUrl(mUrl);
                //skip null objects
                if (drawable != null) {
                    //store images in an lrucache
                    sImageCache.put(mUrl, drawable);


                    Message message = mHandler.obtainMessage();
                    message.what = MSG_DOWNLOAD_COMPLETE;
                    message.obj = drawable;
                    mHandler.sendMessage(message);
                }
            }
        }.start();
    }

    /**
     * create a drawable from imageUrl
     *
     * @param imageUrl the Url that contains a drawable
     * @return a drawable or null, if something goes wrong
     */
    private Drawable LoadImageFromUrl(String imageUrl) {
        try {
            return Drawable.createFromStream(new URL(imageUrl).openStream(),
                    "src");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //return the lrucache for unittest only
    public static LruCache<String, Drawable> getImageCache() {
        return sImageCache;
    }
}
