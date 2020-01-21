package net.phpsm.simsim.simsiminstantorder.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.fragments.MyOrderFragment;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.Locale;

public class YourOrderComplete extends AppCompatActivity {
    LinearLayout animate;
    AppSharedPreferences appSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order_complete);

        appSharedPreferences = new AppSharedPreferences(YourOrderComplete.this);
        appSharedPreferences.writeInteger("order_delivered_time",1);

        animate=findViewById(R.id.animate);

        Animation animation = AnimationUtils.loadAnimation(YourOrderComplete.this, R.anim.logo_anim);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animate.setVisibility(View.GONE);
                /*Intent i = new Intent(getApplicationContext(), ActivityMainCall.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                i.putExtra("fcall", "order");
                startActivity(i);
                finish();*/

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animate.startAnimation(animation);

    }
}
