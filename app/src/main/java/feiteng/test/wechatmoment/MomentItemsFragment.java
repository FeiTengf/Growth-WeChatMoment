package feiteng.test.wechatmoment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

import feiteng.test.wechatmoment.adapters.HeaderWrapperAdapter;
import feiteng.test.wechatmoment.adapters.MyMomentItemRecyclerViewAdapter;
import feiteng.test.wechatmoment.items.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class MomentItemsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private MyMomentItemRecyclerViewAdapter mWrappedAdapter = new MyMomentItemRecyclerViewAdapter(DummyContent.ITEMS);
    private HeaderWrapperAdapter mAdapter = new HeaderWrapperAdapter(new MyMomentItemRecyclerViewAdapter(DummyContent.ITEMS));

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MomentItemsFragment() {
    }

    public static MomentItemsFragment newInstance(int columnCount) {
        MomentItemsFragment fragment = new MomentItemsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_momentitem_list, container, false);

        // Set the mAdapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            LayoutInflater inflater1 = getActivity().getLayoutInflater();
            @SuppressLint("InflateParams")
            View headerView = inflater1.inflate(R.layout.view_headerview, null);
            mAdapter.setHeaderView(headerView);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


}
