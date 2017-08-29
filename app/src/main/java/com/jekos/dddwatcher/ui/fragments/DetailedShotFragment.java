package com.jekos.dddwatcher.ui.fragments;

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
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.shot_detail_fragment,container,false);

        share = view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,description.getText() + "\n\nСсылка на запись с сайта dribbble.com: " + shot.getHtml_url());
                //intent.putExtra(Intent.EXTRA_STREAM, Uri.parse(shot.getImages().get("normal")));
                /*if (shot.isAnimated())
                    intent.setType("image/gif");
                else
                    intent.setType("image/jpg");
                */
                intent.setType("text/plain");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent,"Send"));
            }
        });

        userName= (TextView) view.findViewById(R.id.username_shot_detail);
        description = (TextView) view.findViewById(R.id.detail_shot_description);
        description.setMovementMethod(LinkMovementMethod.getInstance());

        userAvarar = (ImageView) view.findViewById(R.id.imageuser_detail_shot);
        shotImage = (ImageView) view.findViewById(R.id.image_detail_shot);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_tags);

        shotsInterface = MyShotsServiceGenerator.createService(DribbbleShotsInterface.class);
        shotsInterface.getShot(shotId,BuildConfig.API_ACCESS_TOKEN).enqueue(new Callback<Shot>() {
            @Override
            public void onResponse(Call<Shot> call, Response<Shot> response) {
                if (response.body() != null) {
                    shot = response.body();
                    Log.d("response message",response.message());

                    userName.setText(shot.getUser().getName());
                    if (shot.getDescription()!=null)
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            description.setText(Html.fromHtml(shot.getDescription(),Html.FROM_HTML_MODE_LEGACY));
                        }
                        else
                        {
                            description.setText(Html.fromHtml(shot.getDescription()));
                        }
                    Glide.with(shotImage.getContext())
                            .load(shot.getImages().get("hidpi"))
                            .into(shotImage);
                    Log.d("response to json",new Gson().toJson(response));
                    Glide.with(shotImage.getContext())
                            .load(shot.getUser().getAvatar_url())
                            .apply(RequestOptions.circleCropTransform())
                            .into(userAvarar);
                }


                List<String> tags = shot.getTags();
                if ((tags != null) &&(tags.size()!=0))
                for (String tag:shot.getTags()) {
                    linearLayout.addView(TextViewTagGenerator.getTextViewTag(LayoutInflater.from(getActivity()),getActivity(),tag));
                    TextView rre = new TextView(getActivity());
                    rre.setText(" ");
                    linearLayout.addView(rre);
                }
                else
                {
                    CardView card = (CardView) view.findViewById(R.id.card_tags);
                    card.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
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
