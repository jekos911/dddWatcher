package com.jekos.dddwatcher.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.jekos.dddwatcher.BuildConfig;
import com.jekos.dddwatcher.R;
import com.jekos.dddwatcher.api.interfaces.DribbbleShotsInterface;
import com.jekos.dddwatcher.api.servicegenerators.MyShotsServiceGenerator;
import com.jekos.dddwatcher.generator.TextViewTagGenerator;
import com.jekos.dddwatcher.models.Shot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by жекос on 24.08.2017.
 */

public class DetailedShotFragment extends Fragment {

    private TextView userName;
    private TextView description;
    private ImageView userAvarar;
    private ImageView shotImage;
    LinearLayout linearLayout;

    private int shotId;
    private Shot shot;

    private final static String ARG_KEY = "SHOT_ID";

    DribbbleShotsInterface shotsInterface;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shotId = getArguments().getInt(ARG_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shot_detail_fragment,container,false);

        userName= (TextView) view.findViewById(R.id.username_shot_detail);
        description = (TextView) view.findViewById(R.id.detail_shot_description);
        userAvarar = (ImageView) view.findViewById(R.id.imageuser_detail_shot);
        shotImage = (ImageView) view.findViewById(R.id.image_detail_shot);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_tags);

        shotsInterface = MyShotsServiceGenerator.createService(DribbbleShotsInterface.class);
        shotsInterface.getShot(shotId,BuildConfig.API_ACCESS_TOKEN).enqueue(new Callback<Shot>() {
            @Override
            public void onResponse(Call<Shot> call, Response<Shot> response) {
                if (response.body() != null) {
                    shot = response.body();
                    Log.d("XYU",response.message());

                    userName.setText(shot.getUser().getName());
                    description.setText(shot.getDescription());
                    Glide.with(shotImage.getContext())
                            .load(shot.getImages().get("hidpi"))
                            .into(shotImage);
                    Log.d("XUI",new Gson().toJson(response));
                    Glide.with(shotImage.getContext())
                            .load(shot.getUser().getAvatar_url())
                            .apply(RequestOptions.circleCropTransform())
                            .into(userAvarar);
                }
                for (String tag:shot.getTags()) {
                    linearLayout.addView(TextViewTagGenerator.getTextViewTag(LayoutInflater.from(getActivity()),getActivity(),tag));
                    TextView rre = new TextView(getActivity());
                    rre.setText(", ");
                    linearLayout.addView(rre);
                }
            }

            @Override
            public void onFailure(Call<Shot> call, Throwable t) {

            }
        });



        return view;
    }

    public static Fragment newInstance(int id)
    {
        Fragment fragment = new DetailedShotFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KEY,id);
        fragment.setArguments(args);
        return fragment;
    }
}
