package net.phpsm.simsim.simsiminstantorder.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;


/**
 * Created by 12 on 01/03/2017.
 */

public class CustomMainTextView extends android.support.v7.widget.AppCompatTextView {
    AppSharedPreferences appSharedPreferences;
    public CustomMainTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        appSharedPreferences=new AppSharedPreferences(context);
        init(context);
    }

    public CustomMainTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomMainTextView(Context context) {
        super(context);
        init(context);
    }
    public void init(Context context) {
        Typeface tf;

        appSharedPreferences=new AppSharedPreferences(context);

            if (appSharedPreferences.readString("lang").equals("ar")) {
                tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Ubuntu-Arabic_B.ttf");
            } else {
                tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Ubuntu-Arabic_B.ttf");
            }
            setTypeface(tf, 1);

    }

}
