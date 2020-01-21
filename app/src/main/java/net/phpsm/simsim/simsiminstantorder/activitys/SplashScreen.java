package net.phpsm.simsim.simsiminstantorder.activitys;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.LocaleHelper;

import java.util.Locale;

public class SplashScreen extends AppCompatActivity {

    AppSharedPreferences appSharedPreferences;
    String mobile;
    RelativeLayout animate;
    String token;
    int is_validate;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appSharedPreferences = new AppSharedPreferences(SplashScreen.this);
        token = appSharedPreferences.readString("access_token");
        is_validate = appSharedPreferences.readInteger("is_validate");
        logo=findViewById(R.id.logo);
        animate=findViewById(R.id.animate);

        Animation animation = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.logo_anim);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                //Log.d("lang1",Resources.getSystem().getConfiguration().locale.getLanguage());
                //Log.d("lang11",appSharedPreferences.readString("lang"));

             /*   if(appSharedPreferences.readString("lang")!=null){
                    lang=appSharedPreferences.readString("lang");
                    Log.d("lang2",lang);
                    Resources res = getApplicationContext().getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.setLocale(new Locale(lang.toLowerCase())); // API 17+ only.
                    // Use conf.locale = new Locale(...) if targeting lower versions
                    res.updateConfiguration(conf, dm);
                }else*/
             if(appSharedPreferences.readString("lang").equals("")){
                 String lang="en";
                 if(Locale.getDefault().getLanguage().equals("ar")){
                    lang="ar";}
                appSharedPreferences.writeString("lang",lang);}
                else {
                 Resources res = getResources();
                 DisplayMetrics dm = res.getDisplayMetrics();
                 android.content.res.Configuration conf = res.getConfiguration();
                 conf.setLocale(new Locale(appSharedPreferences.readString("lang").toLowerCase())); // API 17+ only.
                 // Use conf.locale = new Locale(...) if targeting lower versions
                 res.updateConfiguration(conf, dm);
             }
            }
        @Override
            public void onAnimationEnd(Animation animation) {
                animate.setVisibility(View.GONE);
                if (appSharedPreferences.readInteger("intro") == 1) {
                    if (is_validate == 1) {
                        if (token.equals("")) {
                            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        } else {
                            startActivity(new Intent(SplashScreen.this, ActivityMain.class).putExtra("fopen", "home"));
                        }
                    } else {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    }
                    finish();
                }else{
                    startActivity(new Intent(SplashScreen.this, IntroActivity.class));
                    finish();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animate.startAnimation(animation);

    }
}
