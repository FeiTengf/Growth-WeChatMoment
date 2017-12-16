package feiteng.test.wechatmoment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import feiteng.test.wechatmoment.adapters.HeaderWrapperAdapter;
import feiteng.test.wechatmoment.adapters.MyMomentItemRecyclerViewAdapter;
import feiteng.test.wechatmoment.items.DummyContent;
import feiteng.test.wechatmoment.items.UserProfile;
import feiteng.test.wechatmoment.utils.MomentFetcher;
import feiteng.test.wechatmoment.widgets.LoaderImageView;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class MomentItemsFragment extends Fragment {

    private static final String TAG = "MomentItemsFragment";

    private static final int COLUMN_COUNT = 1;
    private MyMomentItemRecyclerViewAdapter mWrappedAdapter = new MyMomentItemRecyclerViewAdapter(DummyContent.ITEMS);
    //use this adapter to add a headerview
    private HeaderWrapperAdapter mAdapter = new HeaderWrapperAdapter(new MyMomentItemRecyclerViewAdapter(DummyContent.ITEMS));
    private RecyclerView mRecyclerView;
    private View mHeaderView;
    //fetch all json objects first
    private FetchProfileTask mFetchAllTask;
    private UserProfile mProfile;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentItemsFragment() {
    }

    public static MomentItemsFragment newInstance() {
        MomentItemsFragment fragment = new MomentItemsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_momentitem_list, container, false);

        // Set the mAdapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mHeaderView = inflater.inflate(R.layout.view_headerview, null);
            mRecyclerView.setAdapter(mAdapter);
        }

        //starting fetching json objects & imgs
        mFetchAllTask = new FetchProfileTask();
        mFetchAllTask.execute();

        return view;
    }


    private class FetchProfileTask extends AsyncTask<Void, Void, UserProfile> {

        @Override
        protected UserProfile doInBackground(Void... params) {
            MomentFetcher fetcher = new MomentFetcher();
            return fetcher.fetchUsr();
        }

        @Override
        protected void onPostExecute(UserProfile userProfile) {
            super.onPostExecute(userProfile);
            if (userProfile == null) {
                return;
            }

            mAdapter.setUsrProfile(userProfile);
        }
    }

}
