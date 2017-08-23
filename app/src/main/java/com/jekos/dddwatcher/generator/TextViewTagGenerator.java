package com.jekos.dddwatcher.generator;

import android.content.Context;
import android.widget.TextView;

import com.jekos.dddwatcher.R;

/**
 * Created by жекос on 23.08.2017.
 */

public class TextViewTagGenerator {

    public static TextView getTextViewTag(Context context, String text)
    {
        TextView textView = new TextView(context);
        textView.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        textView.setText(text);
        return  textView;
    }
}
