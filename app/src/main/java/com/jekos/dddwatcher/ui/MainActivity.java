package com.jekos.dddwatcher.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
;
import com.jekos.dddwatcher.R;



public class MainActivity extends AppCompatActivity {

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
    }


}
