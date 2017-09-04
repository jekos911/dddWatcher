package com.jekos.dddwatcher.ui.fragments;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.jekos.dddwatcher.models.ShotsLab;

import java.util.List;

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
    private LinearLayout linearLayout;
    private Button share;
    private ShotsLab shotsLab;
    private LinearLayout containerDetail;


    private int shotId;
    private Shot shot;

    private final static String ARG_KEY = "SHOT_ID";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shotId = getArguments().getInt(ARG_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.shot_detail_fragment, container, false);
        shotsLab = ShotsLab.getShotsLab();

        containerDetail = view.findViewById(R.id.container_shot_detail);
        containerDetail.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        share = view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Смотри, что я нашел на Dribbble!\n\nСсылка на запись с сайта dribbble.com: " + shot.getHtml_url());
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Send"));
            }
        });

        userName = view.findViewById(R.id.username_shot_detail);
        description = view.findViewById(R.id.detail_shot_description);
        description.setMovementMethod(LinkMovementMethod.getInstance());

        userAvarar = view.findViewById(R.id.imageuser_detail_shot);
        shotImage = view.findViewById(R.id.image_detail_shot);
        linearLayout = view.findViewById(R.id.linear_tags);

        for (Shot shot1:shotsLab.getShots()
             ) {
            if (shot1.getId() ==shotId)
                shot = shotsLab.getShots().get(shotsLab.getShots().indexOf(shot1));   // FIXME: 04.09.2017 ну и фегняяя
        }

        userName.setText(shot.getUser().getName());
        if (shot.getDescription() != null)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                description.setText(Html.fromHtml(shot.getDescription(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                description.setText(Html.fromHtml(shot.getDescription()));
            }
        Glide.with(shotImage.getContext())
                .load(shot.getImages().get("hidpi"))
                .into(shotImage);
        Glide.with(shotImage.getContext())
                .load(shot.getUser().getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(userAvarar);

        List<String> tags = shot.getTags();
        if ((tags != null) && (tags.size() != 0))
            for (String tag : shot.getTags()) {
                linearLayout.addView(TextViewTagGenerator.getTextViewTag(LayoutInflater.from(getActivity()), getActivity(), tag));
                TextView rre = new TextView(getActivity());
                rre.setText(" ");
                linearLayout.addView(rre);
            }
        else {
            CardView card = (CardView) view.findViewById(R.id.card_tags);
            card.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);
        }

        return view;
    }

    public static Fragment newInstance(int id) {
        Fragment fragment = new DetailedShotFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_KEY, id);
        fragment.setArguments(args);
        return fragment;
    }
}
