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

import java.util.ArrayList;
import java.util.List;

import feiteng.test.wechatmoment.adapters.HeaderWrapperAdapter;
import feiteng.test.wechatmoment.adapters.MyMomentItemRecyclerViewAdapter;
import feiteng.test.wechatmoment.items.Tweet;
import feiteng.test.wechatmoment.items.UserProfile;
import feiteng.test.wechatmoment.utils.MomentFetcher;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class MomentItemsFragment extends Fragment {

    private static final String TAG = "MomentItemsFragment";

    private static final int COLUMN_COUNT = 1;
    //An list to stor all the tweets
    private List<Tweet> mCurrentTweetList = new ArrayList<Tweet>();
    //An arrayLit to hold tweet on the recycle view
    private List<Tweet> mAllTweetList = new ArrayList<Tweet>();

    //adapter that holds tweets
    private MyMomentItemRecyclerViewAdapter mWrappedAdapter = new MyMomentItemRecyclerViewAdapter(mCurrentTweetList);
    //use this adapter to add a headerview
    private HeaderWrapperAdapter mAdapter = new HeaderWrapperAdapter(mWrappedAdapter);
    private RecyclerView mRecyclerView;
    //fetch all json objects
    private FetchProfileTask mFetchUsrTask;
    private FetchTweetTask mFetchTweetTask;

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
            mRecyclerView.setAdapter(mAdapter);
        }

        //starting fetching json objects & imgs
        mFetchUsrTask = new FetchProfileTask();
        mFetchUsrTask.execute();
        //Fetch all tweets
        mFetchTweetTask = new FetchTweetTask();
        mFetchTweetTask.execute();

        return view;
    }

    /**
     * Get usr info at the beginning.
     */
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

            if (isVisible()) {
                mAdapter.setUsrProfile(userProfile);
            }
        }
    }

    /**
     * Refresh or init tweets. We just show 5 of them at the very beginning.
     */
    private class FetchTweetTask extends AsyncTask<Void, Void, List<Tweet>> {

        @Override
        protected List<Tweet> doInBackground(Void... params) {
            MomentFetcher fetcher = new MomentFetcher();
            return fetcher.fetchTweets();
        }

        @Override
        protected void onPostExecute(List<Tweet> tweets) {
            super.onPostExecute(tweets);
            if (tweets == null) {
                return;
            }
            //Work with asyncTask so these list are thread-safe
            if (isVisible()) {
                mAllTweetList.clear();
                mAllTweetList.addAll(tweets);
                //copy first 5 to current list at the very beginning
                List<Tweet> subList = mAllTweetList.subList(0, Math.min(5, mAllTweetList.size()));
                mCurrentTweetList.clear();
                mCurrentTweetList.addAll(subList);
                mWrappedAdapter.notifyDataSetChanged();
            }
        }
    }

}
