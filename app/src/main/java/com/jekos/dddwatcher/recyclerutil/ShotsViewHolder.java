package com.jekos.dddwatcher.recyclerutil;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.jekos.dddwatcher.R;


/**
 * Created by жекос on 21.08.2017.
 */

public class ShotsViewHolder extends RecyclerView.ViewHolder {

    protected ImageView imageShot;
    protected TextView titleShot;
    protected ProgressBar progressBar;
    protected TextView views;
    protected TextView likes;
    protected TextView comments;

    public ShotsViewHolder(View itemView) {
        super(itemView);
        imageShot = (ImageView) itemView.findViewById(R.id.list_shots_image);
        titleShot = (TextView) itemView.findViewById(R.id.list_shots_title);
        likes = (TextView) itemView.findViewById(R.id.likes_bottom_bar);
        views = (TextView) itemView.findViewById(R.id.views_bottom_bar);
        comments = (TextView) itemView.findViewById(R.id.comments_bottom_bar);
    }
}
