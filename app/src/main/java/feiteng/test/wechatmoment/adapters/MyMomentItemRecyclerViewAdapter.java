package feiteng.test.wechatmoment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import feiteng.test.wechatmoment.R;
import feiteng.test.wechatmoment.items.Tweet;
import feiteng.test.wechatmoment.widgets.LoaderImageView;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Tweet} and makes a call to the
 * specified {@link }.
 */
public class MyMomentItemRecyclerViewAdapter extends RecyclerView.Adapter<MyMomentItemRecyclerViewAdapter.ViewHolder> {

    private final List<Tweet> mValues;
    private final String TAG = "Adapter";

    public MyMomentItemRecyclerViewAdapter(List<Tweet> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_momentitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //hide or show items by content and images
        setItemVisibility(holder);

        holder.mSenderNameView.setText(holder.mItem.getSender().getUsrName());
        holder.mContentView.setText(holder.mItem.getContent());
        holder.mAvatarView.loadUrl(holder.mItem.getSender().getProfileUrl());


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
        //set its GONE is not enough, we must set params explicitly for recycler view
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) holder.mView.getLayoutParams();

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

        Tweet mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mAvatarView = (LoaderImageView) view.findViewById(R.id.tweet_sender_avatar_imageview);
            mSenderNameView = (TextView) view.findViewById(R.id.tweet_sender_name_textview);
            mContentView = (TextView) view.findViewById(R.id.tweet_content_textview);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
