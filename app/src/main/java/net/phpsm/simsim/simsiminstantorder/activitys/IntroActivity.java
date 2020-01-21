package net.phpsm.simsim.simsiminstantorder.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

public class IntroActivity extends AppIntro {
    AppSharedPreferences appSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appSharedPreferences = new AppSharedPreferences(IntroActivity.this);

         setFadeAnimation();
       // setZoomAnimation();
       // setFlowAnimation();
      // setSlideOverAnimation();
        //setDepthAnimation();

        // Add your slide fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        //addSlide(firstFragment);
        //addSlide(secondFragment);
       // addSlide(thirdFragment);
      //  addSlide(fourthFragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.

        AppIntroFragment intro1 =new AppIntroFragment();




        addSlide(AppIntroFragment.newInstance(getString(R.string.hitTitle1),null, getString(R.string.hitdesc1),null, R.drawable.intro1, Color.parseColor("#f4f4f4"),Color.parseColor("#656565"),Color.parseColor("#656565")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.hitTitle2),null, getString(R.string.hitdesc2),null, R.drawable.intro2, Color.parseColor("#f4f4f4"),Color.parseColor("#656565"),Color.parseColor("#656565")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.hitTitle3),null, getString(R.string.hitdesc3),null, R.drawable.intro3, Color.parseColor("#f4f4f4"),Color.parseColor("#656565"),Color.parseColor("#656565")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.hitTitle4),null, getString(R.string.hitdesc4),null, R.drawable.intro4, Color.parseColor("#f4f4f4"),Color.parseColor("#656565"),Color.parseColor("#656565")));
        addSlide(AppIntroFragment.newInstance(getString(R.string.hitTitle5),null, getString(R.string.hitdesc5),null, R.drawable.intro5, Color.parseColor("#f4f4f4"),Color.parseColor("#656565"),Color.parseColor("#656565")));


        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#f4f4f4"));
        setSeparatorColor(Color.parseColor("#c7c7c7"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setColorDoneText(Color.parseColor("#656565"));
        setIndicatorColor(Color.parseColor("#656565"),Color.parseColor("#b6b6b6"));
        setColorSkipButton(Color.parseColor("#656565"));
        setNextArrowColor(Color.parseColor("#656565"));
        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(false);
        //setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        appSharedPreferences.writeInteger("intro", 1);

        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
         finish();
        // Do something when users tap on Skip button.
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        appSharedPreferences.writeInteger("intro", 1);

        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
         finish();
         // Do something when users tap on Done button.
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}