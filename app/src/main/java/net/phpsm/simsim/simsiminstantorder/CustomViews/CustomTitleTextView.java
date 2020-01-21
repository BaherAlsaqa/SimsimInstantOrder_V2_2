package net.phpsm.simsim.simsiminstantorder.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;


/**
 * Created by 12 on 01/03/2017.
 */

public class CustomTitleTextView extends android.support.v7.widget.AppCompatTextView {
    AppSharedPreferences appSharedPreferences;
    public CustomTitleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomTitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTitleTextView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        Typeface tf;
        appSharedPreferences=new AppSharedPreferences(context);

            if (appSharedPreferences.readString("lang").equals("ar")) {
                tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/bein-ar-black.ttf");
            } else {
                tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/forte.ttf");
            }
            setTypeface(tf, 1);

    }

}
