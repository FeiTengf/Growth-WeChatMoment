package feiteng.test.wechatmoment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import feiteng.test.wechatmoment.adapters.HeaderWrapperAdapter;
import feiteng.test.wechatmoment.adapters.TweetAdapter;
import feiteng.test.wechatmoment.items.Tweet;
import feiteng.test.wechatmoment.items.UserProfile;
import feiteng.test.wechatmoment.utils.TweetDecoration;
import feiteng.test.wechatmoment.utils.TweetFetcher;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class MomentItemsFragment extends Fragment {

    private static final String TAG = "MomentItemsFragment";
    private static final String PREF_USR = "com.test.wechatmoment.MomentItemsFragment.usr";

    //5 tweets in one page
    private static final int TWEETS_IN_ONE_PAGE = 5;
    //An list to stor all the tweets
    private final List<Tweet> mCurrentTweetList = new ArrayList<>();
    //An arrayLit to hold tweet on the recycle view
    private final List<Tweet> mAllTweetList = new ArrayList<>();

    //adapter that holds tweets
    private final TweetAdapter mTweetAdapter = new TweetAdapter(mCurrentTweetList);
    //use this adapter to add a headerview
    private final HeaderWrapperAdapter mWrapperAdapter = new HeaderWrapperAdapter(mTweetAdapter);
    private SwipeRefreshLayout mRefreshWidget;
    private LinearLayoutManager mRecyclerManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentItemsFragment() {
    }

    public static MomentItemsFragment newInstance() {
        return new MomentItemsFragment();
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
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_list);
        mRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

        // Set the mWrapperAdapter
        mRecyclerManager = new LinearLayoutManager(context);
        mRecyclerManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(mRecyclerManager);
        mRecyclerView.addItemDecoration(new TweetDecoration(getActivity()));
        mRecyclerView.setAdapter(mWrapperAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //all tweets has already shown in list
                if (noMoreTweets()) {
                    return;
                }
                //show next 5 tweets
                int lastVisPos = mRecyclerManager.findLastVisibleItemPosition();
                boolean atBottom = lastVisPos + 1 == mWrapperAdapter.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && atBottom) {
                    mRefreshWidget.setRefreshing(true);
                    appendNextTweets();
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
        //load default profile
        String jsonString = PreferenceManager.getDefaultSharedPreferences(getActivity())
                .getString(PREF_USR, "");
        try {
            JSONObject obj = new JSONObject(jsonString);
            UserProfile profile = new UserProfile(obj);
            mWrapperAdapter.setUsrProfile(profile);
        } catch (JSONException e) {
            //clear usr in preference
            saveLastUsr("");
            e.printStackTrace();
        }

        //starting fetching json objects & imgs
        FetchProfileTask fetchUrlTask = new FetchProfileTask();
        fetchUrlTask.execute();

        //Fetch all tweets
        FetchTweetTask fetchTweetTask = new FetchTweetTask();
        fetchTweetTask.execute();
    }

    /**
     * Add some more tweets to current tweets.
     */
    private void appendNextTweets() {
        int start = mCurrentTweetList.size();
        int end = start + TWEETS_IN_ONE_PAGE;
        List<Tweet> subList = mAllTweetList.subList(start, Math.min(end, mAllTweetList.size()));
        mCurrentTweetList.addAll(subList);
    }

    /**
     * Whether we have more tweets to display.
     */
    private boolean noMoreTweets() {
        return mCurrentTweetList.size() >= mAllTweetList.size();
    }

    /**
     * Get usr info at the beginning.
     */
    private class FetchProfileTask extends AsyncTask<Void, Void, UserProfile> {

        @Override
        protected UserProfile doInBackground(Void... params) {
            TweetFetcher fetcher = new TweetFetcher();
            UserProfile userProfile;

            // fetch profile while it is visible
            userProfile = fetcher.fetchUsr();
            return userProfile;
        }

        @Override
        protected void onPostExecute(UserProfile userProfile) {
            super.onPostExecute(userProfile);

            //cannot be null
            if (userProfile == null) {
                return;
            }

            String lastUsr = PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString(PREF_USR, "");
            //new usr load and save now
            if (!lastUsr.equals(userProfile.getJsonString())) {
                saveLastUsr(userProfile.getJsonString());
                if (isVisible()) {
                    mWrapperAdapter.setUsrProfile(userProfile);
                }
            }
        }
    }

    /**
     * Save last usr to shared preference
     *
     * @param usr Last used user
     */
    private void saveLastUsr(String usr) {
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putString(PREF_USR, usr)
                .apply();
    }


    /**
     * Refresh or init tweets. We just show 5 of them at the very beginning.
     */
    private class FetchTweetTask extends AsyncTask<Void, Void, List<Tweet>> {

        @Override
        protected List<Tweet> doInBackground(Void... params) {
            TweetFetcher fetcher = new TweetFetcher();
            List<Tweet> tweets;
            // fetch tweets while it is visible
            tweets = fetcher.fetchTweets();
            return tweets;

        }

        @Override
        protected void onPostExecute(List<Tweet> tweets) {
            super.onPostExecute(tweets);

            //Work with asyncTask so these list are thread-safe
            if (isVisible() && tweets != null) {
                mAllTweetList.clear();
                mCurrentTweetList.clear();
                mAllTweetList.addAll(tweets);
                //copy first 5 to current list at the very beginning
                appendNextTweets();

                mWrapperAdapter.notifyDataSetChanged();
            }
            mRefreshWidget.setRefreshing(false);
        }
    }

}
