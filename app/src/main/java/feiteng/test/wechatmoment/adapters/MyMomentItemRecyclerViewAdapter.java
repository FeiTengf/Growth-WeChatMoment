package feiteng.test.wechatmoment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import feiteng.test.wechatmoment.R;
import feiteng.test.wechatmoment.items.Tweet;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Tweet} and makes a call to the
 * specified {@link }.
 */
public class MyMomentItemRecyclerViewAdapter extends RecyclerView.Adapter<MyMomentItemRecyclerViewAdapter.ViewHolder> {

    private final List<Tweet> mValues;

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
        if (holder.mItem.canbeIgnored()) {
            holder.mView.setVisibility(View.INVISIBLE);
        } else {
            holder.mView.setVisibility(View.VISIBLE);
        }

        holder.mIdView.setText(mValues.get(position).getSender().toString());
        holder.mContentView.setText(mValues.get(position).getContent());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //process onclick here
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mIdView;
        final TextView mContentView;
        Tweet mItem;

        ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
