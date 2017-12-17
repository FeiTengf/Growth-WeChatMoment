package feiteng.test.wechatmoment.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;

import feiteng.test.wechatmoment.R;
import feiteng.test.wechatmoment.utils.TweetFetcher;

/**
 * Loading images from internet and show it on the view.
 * If there is something failed during this process,
 * this view will display an default connection_failed.png instead.
 * <p>
 * Created by inthe on 2017/12/16.
 */

public class LoaderImageView extends AppCompatImageView {

    private static LruCache<String, Bitmap> sImageCache = new LruCache<String, Bitmap>(20);

    private String mUrl;
    private String TAG = "LoaderImageView";

    private static final int MSG_DOWNLOAD_SUCCESS = 1;
    private static final int MSG_DOWNLOAD_FAILED = 2;

    //TODO fix the potential memory leak
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_DOWNLOAD_SUCCESS) {
                Log.d(TAG, "LoadImg Success");
                LoaderImageView.this.setImageBitmap((Bitmap) msg.obj);
            } else if (msg.what == MSG_DOWNLOAD_FAILED) {
                // use getDrawable at API.14
                Log.d(TAG, "LoadImg Failed");
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
        Bitmap cached = sImageCache.get(urlToBeLoad);
        if (cached != null) {
            this.setImageBitmap(cached);
            return;
        }

        new Thread() {
            @Override
            public void run() {
                Log.d(TAG, "Thread ID" + (this.getId()));
                Bitmap bitmap = LoadImageFromUrl(mUrl);
                Message message = mHandler.obtainMessage();
                if (bitmap != null) {
                    //store images in an lrucache
                    sImageCache.put(mUrl, bitmap);

                    message.what = MSG_DOWNLOAD_SUCCESS;
                    message.obj = bitmap;
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
    private Bitmap LoadImageFromUrl(String imageUrl) {
        Bitmap ret = null;
        Log.d(TAG, "url:" + imageUrl);
        try {
            byte[] bitmapBytes = new TweetFetcher().getUrlBytes(imageUrl);
            if (bitmapBytes != null) {
                ret = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            } else {
                Log.e(TAG, "Load Image failed data is null Url:" + imageUrl);
            }
        } catch (IOException e) {
            Log.e(TAG, "Load Image failed Url:" + imageUrl + "");
            e.printStackTrace();
        }
        Log.d(TAG, "Bitmap is null? " + (ret == null));
        return ret;
    }

    //return the lrucache for unittest only
    public static LruCache<String, Bitmap> getImageCache() {
        return sImageCache;
    }

    public String getUrl() {
        return mUrl;
    }
}
