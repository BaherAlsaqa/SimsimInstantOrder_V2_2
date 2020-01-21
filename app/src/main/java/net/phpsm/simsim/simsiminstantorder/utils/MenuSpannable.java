package net.phpsm.simsim.simsiminstantorder.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import net.phpsm.simsim.simsiminstantorder.R;

/**
 * Created by MSI on 5/19/2018.
 */

public class MenuSpannable extends MetricAffectingSpan {
    int color =Color.parseColor("#e01c4b");
    //int size = 35;

    public MenuSpannable() {
        setSelected(false);
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        p.setColor(color);
        //p.setTextSize(size);
            /* p.setText --- whatever --- */
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(color);
    //    tp.setTextSize(size);
            /* tp.setText --- whatever --- */
    }
    public void setSelected(boolean selected){
        if(selected){
            color = Color.parseColor("#e01c4b");
       //    size = 35;
        }else{
            color = Color.parseColor("#C4C4C4");
         //   size = 30;
        }
    }
}
