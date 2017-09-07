package com.jekos.dddwatcher.generator;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.jekos.dddwatcher.R;

/**
 * Created by жекос on 23.08.2017.
 */

public class TextViewTagGenerator {

    public static TextView getTextViewTag(LayoutInflater layoutInflater, String text)
    {
        TextView textView = (TextView) layoutInflater.inflate(R.layout.textviewtag,null,false);
        textView.setText(text);
        return  textView;
    }
}
