package feiteng.test.wechatmoment.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import feiteng.test.wechatmoment.R;
import feiteng.test.wechatmoment.items.UserProfile;
import feiteng.test.wechatmoment.widgets.LoaderImageView;

/**
 * Wrapper class for recyclerAdapter, so we can add header view easier
 * Android template wizards.
 */
public class HeaderWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Inner adapter for the real content
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter = null;
    private UserProfile mUserProfile = null;

    private final int HEADER_TYPE = -100;
    private View mHeaderView;

    /**
     * Create a HeaderWrapperAdapter
     *
     * @param wrappedAdapter: the Adapter to be wrapped
     */
    public HeaderWrapperAdapter(RecyclerView.Adapter wrappedAdapter) {
        //noinspection unchecked
        mInnerAdapter = wrappedAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE) {
            mHeaderView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tweet_headerview, parent, false);
            setUsrProfile(mUserProfile);
            return new ViewHolder(mHeaderView);
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Header View has got updated ealier.
        if (position == 0) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, getInnerPosition(position));
    }

    @Override
    public int getItemCount() {
        //check for HeaderView
        return mInnerAdapter.getItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        //it's header
        if (position == 0) {
            return HEADER_TYPE;
        }
        return mInnerAdapter.getItemViewType(getInnerPosition(position));
    }


    /**
     * View Holder to store HeaderView
     */
    private class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;

        ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }

    /**
     * Set Usr profile and avatar and name to mHeaderView
     * The Recycler's header view will be updated
     *
     * @param profile This usr's profile
     */
    public void setUsrProfile(UserProfile profile) {
        if (profile == null) {
            return;
        }
        if (mHeaderView != null) {
            TextView nameView = (TextView) mHeaderView.findViewById(R.id.name_textview);
            LoaderImageView profileView = (LoaderImageView) mHeaderView.findViewById(R.id.profile_imageview);
            LoaderImageView avatarView = (LoaderImageView) mHeaderView.findViewById(R.id.avatar_imageview);

            nameView.setText(profile.getUsrName());
            profileView.loadUrl(profile.getProfileUrl(), false);
            avatarView.loadUrl(profile.getAvatarUrl(), true);

            //clear cache
            mUserProfile = null;
        } else {
            //load profile later
            mUserProfile = profile;
        }
    }

    /**
     * Get the position for the inner Adapter, which should -1 if there is a Header view.
     *
     * @param pos the position in this adapter
     * @return the pos in the wrapped adapter.
     */
    private int getInnerPosition(int pos) {
        return pos - 1;
    }
}
