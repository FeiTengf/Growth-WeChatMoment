package feiteng.test.wechatmoment.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.LruCache;

import java.net.URL;

import feiteng.test.wechatmoment.R;

/**
 * Loading images from internet and show it on the view.
 * If there is something failed during this process,
 * this view will display an default connection_failed.png instead.
 * <p>
 * Created by inthe on 2017/12/16.
 */

public class LoaderImageView extends AppCompatImageView {

    private static LruCache<String, Drawable> sImageCache = new LruCache<String, Drawable>(20);

    private String mUrl;
    private String TAG = "LoaderImageView";

    private static final int MSG_DOWNLOAD_SUCCESS = 1;
    private static final int MSG_DOWNLOAD_FAILED = 2;

    //TODO fix the potential memory leak
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_DOWNLOAD_SUCCESS) {
                LoaderImageView.this.setImageDrawable((Drawable) msg.obj);
            } else if (msg.what == MSG_DOWNLOAD_FAILED) {
                // use getDrawable at API.14
                LoaderImageView.this.setImageDrawable(getContext().getResources().
                        getDrawable(R.drawable.connection_failed));
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
                Message message = mHandler.obtainMessage();
                if (drawable != null) {
                    //store images in an lrucache
                    sImageCache.put(mUrl, drawable);


                    message.what = MSG_DOWNLOAD_SUCCESS;
                    message.obj = drawable;
                } else {
                    message.what = MSG_DOWNLOAD_FAILED;
                }
                mHandler.sendMessage(message);
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
