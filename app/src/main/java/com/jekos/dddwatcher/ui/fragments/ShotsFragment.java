package com.jekos.dddwatcher.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.jekos.dddwatcher.BuildConfig;
import com.jekos.dddwatcher.R;
import com.jekos.dddwatcher.api.interfaces.DribbbleShotsInterface;
import com.jekos.dddwatcher.api.servicegenerators.MyShotsServiceGenerator;
import com.jekos.dddwatcher.models.Shot;

import com.jekos.dddwatcher.recyclerutil.PaginationScrollListner;
import com.jekos.dddwatcher.recyclerutil.ShotClickListner;
import com.jekos.dddwatcher.recyclerutil.ShotsAdapter;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by жекос on 21.08.2017.
 */

public class ShotsFragment extends Fragment {

    private List<Shot> shots;
    private RecyclerView recycler;
    private ProgressBar progressBar;

    Realm realm;

    private ShotsAdapter shotsAdapter;
    LinearLayoutManager linearLayoutManager;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;

    DribbbleShotsInterface shotsInterface;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Realm.init(getActivity());
        realm = Realm.getDefaultInstance();
        View view = inflater.inflate(R.layout.shots_fragment,container,false);
        recycler = (RecyclerView) view.findViewById(R.id.recyclerShots);
        setRetainInstance(true);
        progressBar = (ProgressBar) view.findViewById(R.id.main_progress);
        shotsAdapter = new ShotsAdapter((ShotClickListner) getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(shotsAdapter);
        recycler.addOnScrollListener(new PaginationScrollListner(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading && !isLastPage)
                {
                isLoading = true;
                currentPage +=1;
                loadNextPage();}
            }

            @Override
            public int getTotalPageCount() {
                return 0;
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }
        });
        shotsInterface = MyShotsServiceGenerator.createService(DribbbleShotsInterface.class);
        loadFirstPage();

        return view;
    }

    private void loadFirstPage() {
        Log.d("MAINACTIVITY", "loadFirstPage: ");

        shotsInterface.getShots(BuildConfig.API_ACCESS_TOKEN,PAGE_START).enqueue(new Callback<List<Shot>>() {
            @Override
            public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
                progressBar.setVisibility(View.GONE);
                shotsAdapter.addAll(response.body());
                if (currentPage <=TOTAL_PAGES) shotsAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Shot>> call, Throwable t) {

            }
        });


    }

    private void loadNextPage() {
        Log.d("MAINACTIVITY", "loadNextPage: " + currentPage);
        shotsInterface.getShots(BuildConfig.API_ACCESS_TOKEN,currentPage).enqueue(new Callback<List<Shot>>() {
            @Override
            public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
                shots = response.body();
                shotsAdapter.removeLoadingFooter();
                isLoading = false;
                shotsAdapter.addAll(shots);
                if (currentPage <=TOTAL_PAGES) shotsAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Shot>> call, Throwable t) {

            }
        });
    }
}
