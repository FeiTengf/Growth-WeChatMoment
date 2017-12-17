package feiteng.test.wechatmoment.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import feiteng.test.wechatmoment.R;
import feiteng.test.wechatmoment.widgets.LoaderImageView;

/**
 * Adapter for recyclerview of tweetimages.
 */
public class TweetImagesAdapter extends RecyclerView.Adapter<TweetImagesAdapter.ViewHolder> {

    private final List<String> mValues;
    private final String TAG = "Adapter";

    public TweetImagesAdapter(List<String> items) {
        for (int i = 0; i < items.size(); i++) {
            Log.d("Adapter ", "url is " + items.get(i));
        }
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_imageitem, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mLoaderView.loadUrl(holder.mItem);
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final LoaderImageView mLoaderView;

        String mItem;

        ViewHolder(View view) {
            super(view);
            mView = (View) view;
            mLoaderView = (LoaderImageView) view.findViewById(R.id.tweet_imageitem_imageview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLoaderView.getUrl() + "'";
        }
    }
}
