package com.jekos.dddwatcher.ui.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.jekos.dddwatcher.BuildConfig;
import com.jekos.dddwatcher.R;
import com.jekos.dddwatcher.api.interfaces.DribbbleShotsInterface;
import com.jekos.dddwatcher.api.servicegenerators.MyShotsServiceGenerator;
import com.jekos.dddwatcher.models.Shot;

import com.jekos.dddwatcher.models.ShotsLab;
import com.jekos.dddwatcher.recyclerutil.PaginationScrollListner;
import com.jekos.dddwatcher.recyclerutil.ShotClickListner;
import com.jekos.dddwatcher.recyclerutil.ShotsAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by жекос on 21.08.2017.
 */

public class ShotsFragment extends Fragment {

    private static final String CURRENT_PAGE = "CURRENT_PAGE";
    private static final String IS_LAST_PAGE = "IS_LAST_PAGE";
    private static final String WAS_FIRST_LOAD = "WAS_FIRST_LOAD";

    private static final int PAGE_START = 1;
    private static final int TOTAL_PAGES = 3;
    private int currentPage;

    private List<Shot> shots;
    private RecyclerView recycler;
    private ProgressBar progressBar;
    private static boolean wasFirstLoad = false;

    private ShotsAdapter shotsAdapter;
    private LinearLayoutManager linearLayoutManager;

    private boolean isLoading = false;
    private boolean isLastPage;

    private DribbbleShotsInterface shotsInterface;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_PAGE, currentPage);
        outState.putBoolean(IS_LAST_PAGE,isLastPage);
        outState.putBoolean(WAS_FIRST_LOAD,wasFirstLoad);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            currentPage = savedInstanceState.getInt(CURRENT_PAGE);
            isLastPage = savedInstanceState.getBoolean(IS_LAST_PAGE);
            wasFirstLoad = savedInstanceState.getBoolean(WAS_FIRST_LOAD);
        } catch (NullPointerException e) {
            currentPage = PAGE_START;
            isLastPage = false;
            wasFirstLoad = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shots_fragment, container, false);
        recycler = view.findViewById(R.id.recyclerShots);
        progressBar = view.findViewById(R.id.main_progress);
        if (wasFirstLoad)
            progressBar.setVisibility(View.GONE);
        shotsAdapter = new ShotsAdapter((ShotClickListner) getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(shotsAdapter);
        recycler.addOnScrollListener(new PaginationScrollListner(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if (!isLoading && !isLastPage) {
                    isLoading = true;
                    currentPage += 1;
                    loadNextPage();
                }
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return currentPage >= TOTAL_PAGES;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        shotsInterface = MyShotsServiceGenerator.createService(DribbbleShotsInterface.class);
        if (!wasFirstLoad) {
            loadFirstPage();
            wasFirstLoad = true;
        }
        return view;
    }


    private void loadFirstPage() {

        shotsInterface.getShots(BuildConfig.API_ACCESS_TOKEN, PAGE_START).enqueue(new Callback<List<Shot>>() {
            @Override
            public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
                if (response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    shotsAdapter.addAll(response.body());
                    if (currentPage <= TOTAL_PAGES) shotsAdapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<List<Shot>> call, Throwable t) {
                error();
            }
        });
    }

    private void loadNextPage() {
        shotsInterface.getShots(BuildConfig.API_ACCESS_TOKEN, currentPage).enqueue(new Callback<List<Shot>>() {
            @Override
            public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
                if (response.body() != null) {
                    shots = response.body();
                    shotsAdapter.removeLoadingFooter();
                    isLoading = false;
                    shotsAdapter.addAll(shots);
                    if (currentPage < TOTAL_PAGES) shotsAdapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<List<Shot>> call, Throwable t) {
                error();
            }
        });
    }

    private void error() {
        Toast.makeText(getActivity(), getResources().getString(R.string.internet_error), Toast.LENGTH_SHORT).show();
    }
}
