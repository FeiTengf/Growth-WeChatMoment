package feiteng.test.wechatmoment.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;
import java.lang.ref.WeakReference;

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

    private static final LruCache<String, Bitmap> sImageCache = new LruCache<>(40);

    private String mUrl;
    private static final String TAG = "LoaderImageView";
    private boolean useCache = false;
    private final Object mLock = new Object();

    private static final int MSG_DOWNLOAD_SUCCESS = 1;
    private static final int MSG_DOWNLOAD_FAILED = 2;
    MyHandler mHandler = new MyHandler(LoaderImageView.this);

    public LoaderImageView(Context context) {
        super(context);
    }

    public LoaderImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * Load image from url, and set that to this view.
     * isSqure: make the view's height equals its width, for avatar and tweets image
     *
     * @param urlToBeLoad the image's url on this view
     * @param isSquare    making its with equals its height
     */
    public void loadUrl(String urlToBeLoad, final boolean isSquare) {
        mUrl = urlToBeLoad;
        Bitmap cached = sImageCache.get(urlToBeLoad);
        if (cached != null) {
            synchronized (mLock) {
                useCache = true;
            }
            this.setImageBitmap(cached);
            return;
        }

        //set an empty image as default
        this.setImageBitmap(null);
        new Thread() {
            @Override
            public void run() {
                synchronized (mLock) {
                    useCache = false;
                }
                Bitmap bitmap = LoadImageFromUrl(mUrl, isSquare);

                Message message = mHandler.obtainMessage();

                //another image has set to this view, so skip loading
                if (bitmap != null && !useCache) {
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
    private Bitmap LoadImageFromUrl(String imageUrl, boolean isSqure) {
        Bitmap ret = null;
        try {
            byte[] bitmapBytes = new TweetFetcher().getUrlBytes(imageUrl);
            if (bitmapBytes != null) {
                ret = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
                if (ret != null && isSqure) {
                    ret = getSquareImages(ret);
                }
            } else {
                Log.e(TAG, "Load Image failed data is null Url:" + imageUrl);
            }
        } catch (IOException e) {
            Log.e(TAG, "Load Image failed Url:" + imageUrl + "");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Convert a bitmap, make it width == height
     *
     * @param bitmap a squred bitmap.
     * @return processed bitmap
     */
    private Bitmap getSquareImages(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == height) {
            return bitmap;
        }

        float scaleWidth = height > width ? ((float) height) / width : 1;
        float scaleHeight = height > width ? 1 : ((float) width) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    //return the lrucache for unittest only
    public static LruCache<String, Bitmap> getImageCache() {
        return sImageCache;
    }

    public String getUrl() {
        return mUrl;
    }


    private static class MyHandler extends Handler {
        WeakReference<LoaderImageView> mImageView;

        MyHandler(LoaderImageView view) {
            mImageView = new WeakReference(view);
        }

        @Override
        public void handleMessage(Message msg) {
            LoaderImageView view = mImageView.get();
            //check the view first
            if (view != null) {
                if (msg.what == MSG_DOWNLOAD_SUCCESS) {
                    view.setImageBitmap((Bitmap) msg.obj);
                } else if (msg.what == MSG_DOWNLOAD_FAILED) {
                    // use getDrawable at API.14
                    view.setImageBitmap(
                            BitmapFactory.decodeResource(view.getResources(), R.drawable.connection_failed));
                }
            }
        }
    }
}
