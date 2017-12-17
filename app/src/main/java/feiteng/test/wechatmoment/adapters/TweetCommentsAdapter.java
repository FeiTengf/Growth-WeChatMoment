package feiteng.test.wechatmoment.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import feiteng.test.wechatmoment.R;
import feiteng.test.wechatmoment.items.Comment;
import feiteng.test.wechatmoment.utils.StringUtl;

/**
 * Adapter for recyclerview of tweetcomments.
 */
public class TweetCommentsAdapter extends RecyclerView.Adapter<TweetCommentsAdapter.ViewHolder> {

    private final List<Comment> mValues;
    private final String TAG = "Adapter";

    public TweetCommentsAdapter(List<Comment> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tweet_commentitem, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String senderName = holder.mItem.getSender().getUsrName();
        String content = holder.mItem.getContent();

        holder.mTextView.setText(Html.fromHtml(StringUtl.getColoredName(senderName) + content));
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mTextView;

        Comment mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mTextView = (TextView) view.findViewById(R.id.tweet_comment_textview);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTextView.getText() + "'";
        }
    }
}
