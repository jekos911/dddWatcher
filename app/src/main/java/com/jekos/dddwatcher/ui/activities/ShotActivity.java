package com.jekos.dddwatcher.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by жекос on 23.08.2017.
 */

public class ShotActivity extends AppCompatActivity {
    public static final String ARGUMENT_URL = "ARGUMENT_URL";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        setContentView(webView);
        String url = getIntent().getExtras().getString(ARGUMENT_URL);
        webView.loadUrl(url);
    }

    public static Intent getNewIntent(String url, Context context)
    {
        Intent intent = new Intent(context,ShotActivity.class);
        intent.putExtra(ARGUMENT_URL,url);
        return intent;
    }


}
