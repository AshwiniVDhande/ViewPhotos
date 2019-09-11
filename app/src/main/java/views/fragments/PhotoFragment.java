package views.fragments;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viewphotos.R;

import org.parceler.Parcels;

import java.util.ArrayList;

import adapter.PhotoListAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import models.Photos;
import networks.NetworkUtil;
import utils.Constant;
import viewmodel.MainActivityViewModel;
import views.MainActivity;
import widgets.CustomVerticalScrollListener;
import widgets.FeedDividerItemDecoration;
import widgets.WrapContentLinearLayoutManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment implements PhotoListAdapter.IActivityFeedCallback {

    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.action_toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.tv_no_network)
    AppCompatTextView tvNoNetwork;

    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private static String LIST_STATE = "list_state";
    private PhotoListAdapter mAdapter;
    private RecyclerViewScrollListener m_scrollListener;
    private int m_currentFeedPage = 0;
    private SearchView searchView = null;
    MainActivityViewModel viewModel;
    private Parcelable mSavedRecyclerLayoutState;
    private ArrayList<Photos> mPhotoList;

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            setViewVisibility(true);
            mShowPhotoList();
        } else {
            mPhotoList = new ArrayList<>();
            mPhotoList = savedInstanceState.getParcelableArrayList(LIST_STATE);
            displayData();
            setViewVisibility(false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Initialized all the views
     */
    private void init() {
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.photo));
        WrapContentLinearLayoutManager manager = new WrapContentLinearLayoutManager(getActivity());
        rvPhoto.setLayoutManager(manager);
        rvPhoto.addItemDecoration(new FeedDividerItemDecoration(getActivity(), FeedDividerItemDecoration.VERTICAL_LIST,
                this.getResources().getString(R.string.activity_feed)));
        m_scrollListener = new RecyclerViewScrollListener(manager);
        rvPhoto.addOnScrollListener(m_scrollListener);
        mAdapter = new PhotoListAdapter((AppCompatActivity) getActivity(), this);
    }

    @Override
    public void getItemDetails(@NonNull Photos photoDetails) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.PHOTODETAILS, Parcels.wrap(photoDetails));
        Fragment fragment = new PhotoDetailsFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container_photos, fragment, Constant.TAG_FRAGMENT_PHOTO_DETAILS).addToBackStack(Constant.TAG_FRAGMENT_PHOTO_DETAILS);
        fragmentTransaction.commit();
    }

    /**
     * Handles list scroll events for pagination.
     */
    private class RecyclerViewScrollListener extends CustomVerticalScrollListener {

        /**
         * Constructor function
         *
         * @param manager instance of recycler view.
         */
        RecyclerViewScrollListener(WrapContentLinearLayoutManager manager) {
            super(manager);
        }


        @Override
        public void onScrolledToBottom(int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            final int lastItem = firstVisibleItem + visibleItemCount;
            if (lastItem == totalItemCount) {
                if (mAdapter != null) {
                    mAdapter.setFooterProgressBarVisibility(true);
                    mShowPhotoList();
                }
            }
        }
    }

    /**
     * set the views visibility
     *
     * @param isVisible of boolean
     */
    private void setViewVisibility(boolean isVisible) {
        progressbar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        rvPhoto.setVisibility(isVisible ? View.GONE : View.VISIBLE);
        tvNoNetwork.setVisibility(View.GONE);
    }

    /**
     * Show the list of photos
     */
    private void mShowPhotoList() {
        if (NetworkUtil.isNetworkAvailable()) {
            viewModel.getPhotoList(m_currentFeedPage).observe(this, photoDetails -> {
                if (photoDetails != null) {
                    setViewVisibility(false);
                    mAdapter.setPhotoList(photoDetails);
                    if (m_currentFeedPage == 0) {
                        rvPhoto.setAdapter(mAdapter);
                    } else {
                        rvPhoto.post(() -> mAdapter.addNewItems());
                    }
                    m_currentFeedPage += photoDetails.size();
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.no_data), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            progressbar.setVisibility(View.GONE);
            tvNoNetwork.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
        if (searchView == null)
            searchView = (SearchView) menu.findItem(R.id.action_search)
                    .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(LIST_STATE, mAdapter.getPhotoList());
        savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, rvPhoto.getLayoutManager().onSaveInstanceState());
    }

    //Set Adapter to recyclerview and display photoList
    private void displayData() {
        mAdapter.setPhotoList(mPhotoList);
        rvPhoto.setAdapter(mAdapter);
        restoreLayoutManagerPosition();
    }

    //Restore the recyclerView Position after rotation
    private void restoreLayoutManagerPosition() {
        if (mSavedRecyclerLayoutState != null) {
            rvPhoto.getLayoutManager().onRestoreInstanceState(mSavedRecyclerLayoutState);
        }
    }
}
