package feiteng.test.wechatmoment.adapters;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import feiteng.test.wechatmoment.R;
import feiteng.test.wechatmoment.items.Tweet;
import feiteng.test.wechatmoment.utils.StringUtl;
import feiteng.test.wechatmoment.widgets.LoaderImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Tweet} and makes a call to the
 * specified {@link }.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private final List<Tweet> mValues;
    private final String TAG = "Adapter";

    public TweetAdapter(List<Tweet> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Tweet tweet = holder.mItem;
        String usrName = tweet.getSender().getUsrName();
        holder.mSenderNameView.setText(Html.fromHtml(StringUtl.getColoredName(usrName)));
        holder.mContentView.setText(tweet.getContent());
        holder.mAvatarView.loadUrl(tweet.getSender().getAvatarUrl(), false);

        holder.mImagesView.setAdapter(null);
        if (!tweet.getImages().isEmpty()) {
            TweetImagesAdapter adapter = new TweetImagesAdapter(tweet.getImages());
            holder.mImagesView.setAdapter(adapter);
        }

        holder.mCommentsView.setAdapter(null);
        if (!tweet.getComments().isEmpty()) {
            TweetCommentsAdapter adapter = new TweetCommentsAdapter(tweet.getComments());
            holder.mCommentsView.setAdapter(adapter);
        }

        //hide or show items by content and images
        setItemVisibility(holder);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //process onclick here
            }
        });
    }

    /**
     * We must set layoutParams explicitly for this recycler view,
     * or there will still be an empty space
     *
     * @param holder The holder we want to change
     */
    private void setItemVisibility(ViewHolder holder) {

        //content view
        if (holder.mItem.getContent().isEmpty()) {
            holder.mContentView.setVisibility(View.GONE);
        } else {
            holder.mContentView.setVisibility(View.VISIBLE);
        }

        //imageview
        if (holder.mItem.getImages().isEmpty()) {
            holder.mImagesView.setVisibility(View.GONE);
        } else {
            holder.mImagesView.setVisibility(View.VISIBLE);
        }

        //comment
        if (holder.mItem.getImages().isEmpty()) {
            holder.mCommentsView.setVisibility(View.GONE);
        } else {
            holder.mCommentsView.setVisibility(View.VISIBLE);
        }

        //set its GONE is not enough, we must set params explicitly for recycler view
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) holder.mView.getLayoutParams();
        //hide all
        if (holder.mItem.canbeIgnored()) {
            param.height = 0;
            param.width = 0;
            holder.mView.setVisibility(View.GONE);
        } else {
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            holder.mView.setVisibility(View.VISIBLE);
        }
        holder.mView.setLayoutParams(param);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final LoaderImageView mAvatarView;
        final TextView mSenderNameView;
        final TextView mContentView;
        final RecyclerView mImagesView;
        final RecyclerView mCommentsView;

        Tweet mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatarView = (LoaderImageView) view.findViewById(R.id.tweet_sender_avatar_imageview);
            mSenderNameView = (TextView) view.findViewById(R.id.tweet_sender_name_textview);
            mContentView = (TextView) view.findViewById(R.id.tweet_content_textview);
            mImagesView = (RecyclerView) view.findViewById(R.id.tweet_images_recyclerview);
            mCommentsView = (RecyclerView) view.findViewById(R.id.tweet_comments_recyclerview);

            GridLayoutManager imageManager = new GridLayoutManager(view.getContext(), 3);
            mImagesView.setLayoutManager(imageManager);
            LinearLayoutManager commentManager = new LinearLayoutManager(view.getContext());
            mCommentsView.setLayoutManager(commentManager);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
