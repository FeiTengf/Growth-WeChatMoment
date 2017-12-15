package feiteng.test.wechatmoment.adapters;

import android.preference.PreferenceActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HeaderViewListAdapter;

/**
 * Wrapper class for recyclerAdapter, so we can add header view easier
 * Android template wizards.
 */
public class HeaderWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Inner adapter for the real content
    RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter = null;

    private final int HEADER_TYPE = -100;
    private View mHeaderView;

    /**
     * Create a HeaderWrapperAdapter
     *
     * @param wrappedAdapter: the Adapter to be wrapped
     */
    public HeaderWrapperAdapter(RecyclerView.Adapter wrappedAdapter) {
        mInnerAdapter = wrappedAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEADER_TYPE && hasHeaderView()) {
            return new ViewHolder(mHeaderView);
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //Header View has got updated from the beginning.
        if (position == 0 && hasHeaderView()) {
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, getInnerPosition(position));
    }

    @Override
    public int getItemCount() {
        //check for potential HeaderView
        return mInnerAdapter.getItemCount() + (hasHeaderView() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && hasHeaderView()) {
            return HEADER_TYPE;
        }
        return mInnerAdapter.getItemViewType(getInnerPosition(position));
    }


    /**
     * View Holder to store HeaderView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }

    /**
     * Check if we have a header view or not
     *
     * @return whether we got a headerview.
     */
    boolean hasHeaderView() {
        return mHeaderView != null;
    }


    /**
     * Set a headerView you want to use in this adapter.
     * You shall be aware that there can only has one headerview, no more.
     *
     * @param headerView the HeaderView you want to set.
     */
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
    }

    /**
     * Get the position for the inner Adapter, which should -1 if there is a Header view.
     *
     * @param pos the position in this adapter
     * @return the pos in the wrapped adapter.
     */
    private int getInnerPosition(int pos) {
        int ret = pos;
        if (hasHeaderView()) {
            ret = ret - 1;
        }
        return ret;
    }
}
