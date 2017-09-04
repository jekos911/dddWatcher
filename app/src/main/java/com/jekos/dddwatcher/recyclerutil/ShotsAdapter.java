package com.jekos.dddwatcher.recyclerutil;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jekos.dddwatcher.R;
import com.jekos.dddwatcher.models.Shot;
import com.jekos.dddwatcher.models.ShotsLab;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by жекос on 21.08.2017.
 */

public class ShotsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private boolean isLoadingAdded = false;

    private ShotsLab shotsLab;

    private ShotClickListner clickListner;

    public ShotsAdapter(ShotClickListner clickListner) {
        this.clickListner = clickListner;
        shotsLab = ShotsLab.getShotsLab();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("Position", Integer.toString(position));
        switch (getItemViewType(position)) {

            case ITEM:
                ShotsViewHolder shotsVH = (ShotsViewHolder) holder;
                Log.d("Pre getting shot", Integer.toString(position));
                Shot shot = shotsLab.getShots().get(position);
                Log.d("Post getting shot", Integer.toString(position));

                if (shot != null) {

                    if (shot.isAnimated()) {
                        Log.d("anim", Integer.toString(position));
                        Glide.with(shotsVH.imageShot.getContext()).load(shot.getImages().get("normal")).into(shotsVH.imageShot);
                    } else {
                        Log.d("nonainm", Integer.toString(position));
                        Glide.with(shotsVH.imageShot.getContext()).load(shot.getImages().get("normal")).into(shotsVH.imageShot);
                    }

                    Glide.with(shotsVH.imageShot.getContext()).load(shot.getUser().getAvatar_url())
                            .apply(RequestOptions.circleCropTransform())
                            .into(shotsVH.userAvatar);
                    shotsVH.userName.setText(shot.getUser().getName());
                    shotsVH.titleShot.setText(shot.getTitle());
                    shotsVH.comments.setText(Integer.toString(shot.getComments_count()));
                    shotsVH.likes.setText(Integer.toString(shot.getLikes_count()));
                    shotsVH.views.setText(Integer.toString(shot.getViews_count()));
                    shotsVH.clickListner = clickListner;
                    shotsVH.shot = shot;
                    shotsVH.isGif.setVisibility(shot.isAnimated() ? View.VISIBLE : View.INVISIBLE);
                }
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }

    @Override
    public int getItemCount() {
        return shotsLab.getShots().size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == shotsLab.getShots().size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void add(Shot mc) {
        shotsLab.getShots().add(mc);
        notifyItemInserted(shotsLab.getShots().size() - 1);
    }

    public void addAll(List<Shot> mcList) {
        for (Shot mc : mcList) {
            add(mc);
        }
    }

    public void remove(Shot city) {
        int position = shotsLab.getShots().indexOf(city);
        if (position > -1) {
            shotsLab.getShots().remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Shot());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = shotsLab.getShots().size() - 1;
        Shot item = getItem(position);

        if (item != null) {
            shotsLab.getShots().remove(position);
            notifyItemRemoved(position);
        }
    }

    public Shot getItem(int position) {
        return shotsLab.getShots().get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.shots_item, parent, false);
        viewHolder = new ShotsViewHolder(v1);
        return viewHolder;
    }
}
