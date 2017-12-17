package feiteng.test.wechatmoment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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

    //5 tweets in one page
    private static final int TWEETS_IN_ONE_PAGE = 5;
    //An list to stor all the tweets
    private List<Tweet> mCurrentTweetList = new ArrayList<Tweet>();
    //An arrayLit to hold tweet on the recycle view
    private List<Tweet> mAllTweetList = new ArrayList<Tweet>();

    //adapter that holds tweets
    private MyMomentItemRecyclerViewAdapter mTweetAdapter = new MyMomentItemRecyclerViewAdapter(mCurrentTweetList);
    //use this adapter to add a headerview
    private HeaderWrapperAdapter mWrapperAdapter = new HeaderWrapperAdapter(mTweetAdapter);
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshWidget;
    private LinearLayoutManager mRecyclerManager;

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

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_list);
        mRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        // Set the mWrapperAdapter
        mRecyclerManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mRecyclerManager);
        mRecyclerView.setAdapter(mWrapperAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //all tweets has already shown in list
                if (!hasMoreTweets()) {
                    return;
                }
                int lastVisPos = mRecyclerManager.findLastVisibleItemPosition();
                boolean atBottom = lastVisPos + 1 == mWrapperAdapter.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && atBottom) {
                    mRefreshWidget.setRefreshing(true);
                    appendNextTweets(TWEETS_IN_ONE_PAGE);
                    mWrapperAdapter.notifyDataSetChanged();
                    //we will load images later, but now just keep the progressbar satisfied.
                    mRefreshWidget.setRefreshing(false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }

        });

        //fetch for the first time
        initTweetAndProfile();

        //setup refresh widget
        mRefreshWidget.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()

        {
            @Override
            public void onRefresh() {
                //Fetch all tweets
                FetchTweetTask fetchTweetTask = new FetchTweetTask();
                fetchTweetTask.execute();
            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * load first <code>TWEETS_IN_ONE_PAGE</code> of tweet
     * load user's profile at the same time.
     */
    private void initTweetAndProfile() {
        //starting fetching json objects & imgs
        FetchProfileTask fetchUrlTask = new FetchProfileTask();
        fetchUrlTask.execute();

        //Fetch all tweets
        FetchTweetTask fetchTweetTask = new FetchTweetTask();
        fetchTweetTask.execute();
    }

    /**
     * Add some more tweets to current tweets.
     *
     * @param count the count of tweets tobe added
     */
    private void appendNextTweets(int count) {
        int start = mCurrentTweetList.size();
        int end = start + count;
        List<Tweet> subList = mAllTweetList.subList(start, Math.min(end, mAllTweetList.size()));
        mCurrentTweetList.addAll(subList);
    }

    /**
     * Whether we have more tweets to display.
     */
    private boolean hasMoreTweets() {
        return mCurrentTweetList.size() < mAllTweetList.size();
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
                mWrapperAdapter.setUsrProfile(userProfile);
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
                mCurrentTweetList.clear();
                mAllTweetList.addAll(tweets);
                //copy first 5 to current list at the very beginning
                appendNextTweets(TWEETS_IN_ONE_PAGE);

                mWrapperAdapter.notifyDataSetChanged();
                mRefreshWidget.setRefreshing(false);
            }
        }
    }

}
