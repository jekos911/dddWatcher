package com.jekos.dddwatcher.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
;
import com.jekos.dddwatcher.R;
import com.jekos.dddwatcher.models.Shot;
import com.jekos.dddwatcher.recyclerutil.ShotClickListner;
import com.jekos.dddwatcher.ui.fragments.DetailedShotFragment;
import com.jekos.dddwatcher.ui.fragments.ShotsFragment;


public class MainActivity extends AppCompatActivity implements ShotClickListner{

    private FragmentManager fragmentManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentById(R.id.container) == null)
            fragmentManager.beginTransaction()
            .add(R.id.container,new ShotsFragment())
            .commit();

        long i = 0;
        Long.toString(i);

    }

    @Override
    public void onClick(Shot shot) {
        //Intent intent = ShotActivity.getNewIntent(shot.getHtml_url(),this);
        //startActivity(intent);
        setContent(shot.getId());
    }

    public void setContent(int id)
    {
        fragmentManager.beginTransaction()
                .replace(R.id.container,DetailedShotFragment.newInstance(id))
                .addToBackStack("SALDJL")
                .commit();
    }
}
