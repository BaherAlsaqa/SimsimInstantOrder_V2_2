package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.MissingPermissionException;
import com.sinch.android.rtc.calling.Call;
import com.squareup.picasso.Picasso;
import com.transitionseverywhere.ArcMotion;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.PathMotion;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;

import net.phpsm.simsim.simsiminstantorder.CustomViews.CustomMainTextView;
import net.phpsm.simsim.simsiminstantorder.CustomViews.CustomTitleTextView;
import net.phpsm.simsim.simsiminstantorder.adapter.ShowOtherMediaAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.ShowUserMediaAdapter;
import net.phpsm.simsim.simsiminstantorder.fragments.MyFriendsRecommendedDetails;
import net.phpsm.simsim.simsiminstantorder.fragments.MyProfile;
import net.phpsm.simsim.simsiminstantorder.fragments.NotificationFragment;
import net.phpsm.simsim.simsiminstantorder.fragments.Save_Fragment;
import net.phpsm.simsim.simsiminstantorder.fragments.WalletsFragment;
import net.phpsm.simsim.simsiminstantorder.helpers.BottomNavigationViewHelperCall;
import net.phpsm.simsim.simsiminstantorder.R;

import net.phpsm.simsim.simsiminstantorder.fragments.MyOrderFragment;
import net.phpsm.simsim.simsiminstantorder.fragments.RestaurantFragment;
import net.phpsm.simsim.simsiminstantorder.models.Objects.mediaObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MyFriendsRecommended;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityMainCall extends AppCompatActivity {

    String fragmentOpen;
    TextView mTitle;
    View badge;
    AppSharedPreferences appSharedPreferences;
    ArrayList<String> mobilesCallCenter = new ArrayList<>();
    String mobile;
    FrameLayout transition_container;
    CircleImageView animation;
    MyFriendsRecommended myFriendsRecommended;
    Dialog dialog;
    int x = 0;
    /////////////////////////////
Boolean isReturnAnimation=false;
    String callId;
    Fragment resturantFragment;
    static ActivityMainCall activityMainCall;
    public static ActivityMainCall getInstance(){
        return   activityMainCall;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_call);
        activityMainCall=this;
        Log.d("calll", "Activity call");
        animation=findViewById(R.id.animation);
//animation.setVisibility(View.INVISIBLE);
      //  BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
      //  BottomNavigationViewHelperCall.disableShiftMode(bottomNavigationView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        appSharedPreferences = new AppSharedPreferences(this);
       // animation=findViewById(R.id.animation);
       // animation.setVisibility(View.INVISIBLE);

        transition_container=findViewById(R.id.transition_container);

        setSupportActionBar(toolbar);

        mobilesCallCenter = appSharedPreferences.readArraymobileCallCenter("callmobiles");

//        mobile = "0592255925";
        /////////
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }

        );
        /////////

        //badge = findViewById(R.id.badge);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTitle = (CustomTitleTextView)toolbar.findViewById(R.id.toolbar_title);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        if (intent != null) {
            fragmentOpen = intent.getStringExtra("fcall");

            if (fragmentOpen.equals("res")) {
                resturantFragment=RestaurantFragment.newInstance();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_call,resturantFragment,"resturant");
                transaction.commit();
                mTitle.setText(getString(R.string.menu));
            } else if (fragmentOpen.equals("order")) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_call, MyOrderFragment.newInstance());
                transaction.commit();
                mTitle.setText(getString(R.string.checkout));
            } else if (fragmentOpen.equals("save")) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_call, Save_Fragment.newInstance());
                transaction.commit();
                mTitle.setText(getString(R.string.savedProducts));
            }else if(fragmentOpen.equals("notify")){
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_call, NotificationFragment.newInstance());
                transaction.commit();
                mTitle.setText(getString(R.string.notification));
            }else if(fragmentOpen.equals("wallet")){
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_call, WalletsFragment.newInstance());
                transaction.commit();
                mTitle.setText(getString(R.string.wallet));
            }else if(fragmentOpen.equals("profile")){
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_call, MyProfile.newInstance());
                transaction.commit();
                mTitle.setText(getString(R.string.profile));
            }else if(fragmentOpen.equals("saved")){
                Log.d("oooooo", "saved");
                Fragment selectedFragment = null;
                selectedFragment = Save_Fragment.newInstance();
//                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_call, selectedFragment)
                        .commit();
                mTitle.setText(getString(R.string.savedProducts));
            }else if(fragmentOpen.equals("myfriend_recommended_details")){
                Log.d("oooooo", "myfriend_recommended_details");
                Intent intent1 = getIntent();
                if (intent1 != null){
                    myFriendsRecommended = intent1.getParcelableExtra("myFriendsRecommended");
                    Log.d("oooooo", "myFriendsRecommended");
                }
                ///////////////////////////////////////////////////////////////////
                Fragment selectedFragment = null;
                selectedFragment = MyFriendsRecommendedDetails.newInstance();
//                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                /*Bundle data = new Bundle();
                data.putParcelable("myFriendsRecommended1", (Parcelable) myFriendsRecommended);
                Log.d("oooooo", "myFriendsRecommended1");
                selectedFragment.setArguments(data);*/
               // mTitle = (CustomMainTextView)toolbar.findViewById(R.id.toolbar_title);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_call, selectedFragment)
                        .commit();
             String title=   appSharedPreferences.readString("customerNameRecom");
                mTitle.setText(title);
            }
        }

     /*   bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.action_item1:
                                startActivity(new Intent(getApplicationContext(), ActivityMain.class).putExtra("fopen", "recom"));
                                break;
                            case R.id.action_item2:
                                startActivity(new Intent(getApplicationContext(), ActivityMain.class).putExtra("fopen", "checkin"));
                                break;
                            case R.id.action_item22:
                                startActivity(new Intent(getApplicationContext(), ActivityMain.class).putExtra("fopen", "home"));
                                break;
                            case R.id.action_item3:
                                startActivity(new Intent(getApplicationContext(), ActivityMain.class).putExtra("fopen", "fav"));
                                break;
                            case R.id.action_item4:
                                badge.setVisibility(View.GONE);
                                startActivity(new Intent(getApplicationContext(), ActivityMain.class).putExtra("fopen", "notify"));
                                break;
                        }
                        return true;
                    }
                });  */
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, , Toast.LENGTH_LONG).show();
            CustomToast("You may now place a call");
        } else {
            /*Toast.makeText(this, , Toast
                    .LENGTH_LONG).show();*/
            CustomToast("This application needs permission to use your microphone to function properly.");
        }
    }


    public void closeActivity() {
        finish();
    }


    public void doAnimations(String imgUrl){
        final boolean[] TransitionEnd = {false};
        String imagUrl=imgUrl;
        Log.d("sss",isReturnAnimation+"");

        Picasso.with(this).load(imgUrl).placeholder(R.drawable.place_holder_product)
                .resize(200, 200).into(animation);
    animation.setVisibility(View.VISIBLE);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) animation.getLayoutParams();
        //params.gravity = (Gravity.END | Gravity.TOP) ;
        params.gravity = (Gravity.END | Gravity.TOP);
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        int contentViewTop =
                window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight= contentViewTop - statusBarHeight;
        params.topMargin=statusBarHeight;
        params.rightMargin=statusBarHeight;
        params.leftMargin=statusBarHeight;


        TransitionManager.beginDelayedTransition(transition_container,
                new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(900).setPathMotion(PathMotion.STRAIGHT_PATH_MOTION)
                        .addListener(new Transition.TransitionListener() {
                            @Override
                            public void onTransitionStart(Transition transition) {
                                animation.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void onTransitionEnd(Transition transition) {
                                animation.setVisibility(View.INVISIBLE);
                                params.width=298;
                                params.height=298;
                                params.gravity = (Gravity.CENTER );
                                animation.setLayoutParams(params);
                                ((RestaurantFragment)resturantFragment).doCartAnimation();


                            }

                            @Override
                            public void onTransitionCancel(Transition transition) {

                            }

                            @Override
                            public void onTransitionPause(Transition transition) {

                            }

                            @Override
                            public void onTransitionResume(Transition transition) {
                                animation.setVisibility(View.VISIBLE);


                            }
                        }));



        params.width=298;
        params.height=298;
        animation.setLayoutParams(params);

        float width = 298;
        float height = 298;
        float percentageW= (float)(10*width)/100;
        float percentageH= (float)(10*height)/100;


        Log.d("ddd",width+",,"+height+",,"+percentageH);

       for(int i=0;i<10;i++) {
           animation.requestLayout();
           width = width - percentageW;
           height = height - percentageH;
           Log.d("ddd", width + ",," + height);
           animation.getLayoutParams().height = (int) height;
           // Apply the new width for ImageView programmatically
           animation.getLayoutParams().width = (int) width;
           // FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width,height);
           //animation.setLayoutParams(parms);
       }
       // animation.setVisibility(View.INVISIBLE);
       // params.gravity = (Gravity.CENTER ) ;

    }

    public void resetAnimation(){
        animation.setVisibility(View.VISIBLE);

        TransitionManager.beginDelayedTransition(transition_container,
                new ChangeBounds().setPathMotion(new ArcMotion()).setDuration(500).setPathMotion(PathMotion.STRAIGHT_PATH_MOTION));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) animation.getLayoutParams();
        params.gravity = (Gravity.CENTER) ;
        params.width=298;
        params.height=298;
        animation.setLayoutParams(params);
        animation.getLayoutParams().width = 298;
        animation.getLayoutParams().height =  298;
        animation.setLayoutParams(params);


        // FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(width,height);
        //parms.gravity = (Gravity.CENTER ) ;
       // animation.setLayoutParams(parms);

    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ActivityMainCall.this, texttoast, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }

    public void showUserMedia(ArrayList<mediaObject> mediaUrls) {
        dialog=new Dialog(ActivityMainCall.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.slider_dialog);
        RecyclerView recyclerView= dialog.findViewById(R.id.media_recyclerview);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityMainCall.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ShowOtherMediaAdapter videoAdapter = new ShowOtherMediaAdapter(ActivityMainCall.this, mediaUrls);
        recyclerView.setAdapter(videoAdapter);


        dialog.show();



    }
    public void closeMediaDialog() {
        dialog.dismiss();
    }

    public void reOpenRes() {
        resturantFragment=RestaurantFragment.newInstance();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_call,resturantFragment,"resturant");
        transaction.commit();
        mTitle.setText(getString(R.string.restaurant));
    }

    public void reOpenProfile() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_call, MyProfile.newInstance());
        transaction.commit();
        mTitle.setText(getString(R.string.menu));
    }

    public void OpenOrderFragment(){
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_layout_call, MyOrderFragment.newInstance());
        transaction.commit();
        mTitle.setText(getString(R.string.checkout));
    }



    @Override
    public void onBackPressed() {

        if(appSharedPreferences.readInteger("lang_flag")==1) {
            appSharedPreferences.writeInteger("lang_flag",0);
            ActivityMain.getInstance().finish();
            startActivity(new Intent(getApplicationContext(), ActivityMain.class));
            finish();
        }else if(resturantFragment!=null) {
            ( (RestaurantFragment) resturantFragment).cancelRunable();
            finish();
        }
        finish();

    }
}
