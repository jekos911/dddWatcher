package com.jekos.dddwatcher.recyclerutil;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.jekos.dddwatcher.R;
import com.jekos.dddwatcher.models.Shot;


/**
 * Created by жекос on 21.08.2017.
 */

public class ShotsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    protected ImageView imageShot;
    protected TextView titleShot;
    protected TextView views;
    protected TextView likes;
    protected TextView comments;
    protected TextView isGif;
    protected ShotClickListner clickListner;
    protected Shot shot;
    protected ImageView userAvatar;
    protected TextView userName;

    public ShotsViewHolder(final View itemView) {
        super(itemView);
        imageShot =  itemView.findViewById(R.id.list_shots_image);
        titleShot = itemView.findViewById(R.id.list_shots_title);
        likes =  itemView.findViewById(R.id.likes_bottom_bar);
        views = itemView.findViewById(R.id.views_bottom_bar);
        comments =  itemView.findViewById(R.id.comments_bottom_bar);
        isGif = itemView.findViewById(R.id.shots_gif);
        userAvatar =  itemView.findViewById(R.id.user_avatar);
        userName = itemView.findViewById(R.id.user_name);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (clickListner != null && shot != null)
        {
            clickListner.onClick(shot);
        }
    }
}
