package net.phpsm.simsim.simsiminstantorder.activitys;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.view.Menu;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import net.phpsm.simsim.simsiminstantorder.CustomViews.CustomTypefaceSpan;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.adapter.MediaAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.ShowUserMediaAdapter;
import net.phpsm.simsim.simsiminstantorder.fragments.Checkin_tab;
import net.phpsm.simsim.simsiminstantorder.fragments.OrderListFragment;
import net.phpsm.simsim.simsiminstantorder.fragments.Recommended_tab;
import net.phpsm.simsim.simsiminstantorder.fragments.Save_Fragment;
import net.phpsm.simsim.simsiminstantorder.fragments.WalletsFragment;
import net.phpsm.simsim.simsiminstantorder.helpers.BottomNavigationViewHelper;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.fragments.Favorites_tab;
import net.phpsm.simsim.simsiminstantorder.fragments.Home_tab;
import net.phpsm.simsim.simsiminstantorder.fragments.MyProfile;
import net.phpsm.simsim.simsiminstantorder.models.Addfriends;
import net.phpsm.simsim.simsiminstantorder.models.Objects.mediaObject;
import net.phpsm.simsim.simsiminstantorder.models.UpOrder;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.LoginResponse;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.CustomerObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.NotificationObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.PProfileObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveImage;
import net.phpsm.simsim.simsiminstantorder.services.TrackingService;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import net.phpsm.simsim.simsiminstantorder.AbsRuntimePermission;
import net.phpsm.simsim.simsiminstantorder.utils.MenuSpannable;
import net.phpsm.simsim.simsiminstantorder.utils.Utils;

public class ActivityMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int CAPTURE_MEDIA = 368;

    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    // String fragmentOpen;
    boolean doubleBackToExitPressedOnce = false;
    Menu menu, menu2;
    BottomNavigationView bottomNavigationView;
    //View badge;
    int orderId;
    int orderItemId;
    Toolbar toolbar;
    CircleImageView userImage;
    LinearLayout userdata;
    TextView userNameTv;
    String token, refreshToken;
    EditText edittext;
    String FRAG_TAG;
    Home_tab home_tab;
    Checkin_tab checkin_tab;
    final int[] notifPageId = {1};
    String[] str = {"", "", "", ""};
    AppSharedPreferences appSharedPreferences;
    NavigationView navigationView;
    byte[] imgBytes;
    public boolean isRefreshing = false;
    static ActivityMain main;
    int StorednotificationCount, ApiNotificationCount;
    private static final int REQUEST_PERMISSION = 10;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD = 1234;
    public static int OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG = 5678;
    ArrayList<UpOrder> cartList = new ArrayList<>();
    boolean cartback = false;
    boolean notifiback = false;
    ImageView cartImage, notifiImage;
    RelativeLayout cartItemLayout, notificationItemLayout, savedItemLayout;
    TextView cartItemNumber, notifiItemNumber;
    int newNotifiNumber = 0;
    private ProgressDialog progressDialog;
    View header;
    MediaAdapter mediaAdapter;
    ArrayList<String> mediaUrls;
    ImageButton addMedia;
    Dialog m_dialog;
    Dialog dialog;
    RecyclerView mediaContainer;
    InputStream  iStream=null;

    //SharedPreferences.Editor editor;
    public static ActivityMain getInstance() {
        return main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main = this;

        appSharedPreferences = new AppSharedPreferences(ActivityMain.this);

        getprofile();

        ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.MODIFY_AUDIO_SETTINGS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.GET_ACCOUNTS,
                        Manifest.permission.READ_LOGS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,

                },
                REQUEST_PERMISSION);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar, menu);
        this.menu2 = menu;

        Log.d("safa", "ssssss");
       if(token!=null)
        GetNotificationsCount(1);

        ///2////
        notificationItemLayout = (RelativeLayout) menu.findItem(R.id.notification).getActionView();
        notifiImage = notificationItemLayout.findViewById(R.id.img);
        notifiItemNumber = notificationItemLayout.findViewById(R.id.notif_num);

        cartItemLayout = (RelativeLayout) menu.findItem(R.id.cart).getActionView();
        cartImage = cartItemLayout.findViewById(R.id.img);
        cartItemNumber = cartItemLayout.findViewById(R.id.cart_num);
        cartList = appSharedPreferences.readArray("uporder");
        if (cartList.size() > 99) {
            cartItemNumber.setVisibility(View.VISIBLE);
            cartItemNumber.setText("99+");
        } else if (cartList.size() == 0) {
            cartItemNumber.setVisibility(View.GONE);
        } else {
            cartItemNumber.setVisibility(View.VISIBLE);
            cartItemNumber.setText(cartList.size() + "");
        }
        cartItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivityMainCall.class)
                        .putExtra("fcall", "order")
                        .setFlags(new Intent().getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY));
                cartback = true;
            }
        });

        notificationItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("token",token);

                if(!token.equals("")){
                    Log.d("token",token);
                appSharedPreferences.writeInteger("notifi_count", ApiNotificationCount);

                startActivity(new Intent(getApplicationContext(), ActivityMainCall.class).putExtra("fcall", "notify"));
                notifiback = true;
                notifiItemNumber.setVisibility(View.INVISIBLE);}
                else {
                    showLoginToast();
                }
            }
        });
        ///3////


        return super.onCreateOptionsMenu(menu);
    }

    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        progressDialog = new ProgressDialog(ActivityMain.this);
        m_dialog=new Dialog(ActivityMain.this);
        m_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mediaUrls=new ArrayList<>();

        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            Utils.setToolbarHeight(TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics()));

            //Log.d("rrrr",TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics())+"") ;
        }
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);
        this.setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        home_tab = new Home_tab();
        checkin_tab = new Checkin_tab();
        FRAG_TAG = "Home";
        navigationView = findViewById(R.id.navigation);
        header = navigationView.getHeaderView(0);
        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        userNameTv = header.findViewById(R.id.name);
        userImage = header.findViewById(R.id.user_image);
        userdata = header.findViewById(R.id.userdata);

        userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*CustomToast("profile");
                Fragment selectedFragment = null;
                selectedFragment = MyProfile.newInstance();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.commit();*/

                startActivity(new Intent(getApplicationContext(), ActivityMainCall.class).putExtra("fcall", "profile"));

                flipMenu(GravityCompat.START);
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);
        drawerLayout = findViewById(R.id.drawer);


        //badge = findViewById(R.id.badge);
        StorednotificationCount = appSharedPreferences.readInteger("notifi_count");

        String username = appSharedPreferences.readString("username");
        String followers = appSharedPreferences.readString("user_followers");
        String following = appSharedPreferences.readString("user_following");
        String imageUrl = appSharedPreferences.readString("user_Image");
        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");
        /// for provider refresh
        appSharedPreferences.writeString("Prefresh", "1");

        if (!token.equals("")) {

            if (!username.equals("")) {
                userNameTv.setText(username);
            }
            if (!imageUrl.equals("")) {
                Picasso.with(this).load(imageUrl).resize(200, 200).placeholder(R.drawable.place_holder_user).into(userImage);
            }
        }
        if (token.equals("")) {
            navigationView.getHeaderView(0).setVisibility(View.INVISIBLE);
            navigationView.getMenu().findItem(R.id.profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.wallet).setVisible(false);
            navigationView.getMenu().findItem(R.id.messages).setVisible(false);
            navigationView.getMenu().findItem(R.id.adduser).setVisible(false);
            navigationView.getMenu().findItem(R.id.saved).setVisible(false);
            navigationView.getMenu().findItem(R.id.myorders).setVisible(false);
            navigationView.getMenu().findItem(R.id.Contact_us).setVisible(false);
            navigationView.getMenu().findItem(R.id.logout).setTitle(R.string.login);
            navigationView.getMenu().findItem(R.id.logout).setIcon(R.drawable.ic_login);
           // Menu m = navigationView.getMenu();
            for (int i = 0; i < m.size(); i++) {
                MenuItem mi = m.getItem(i);

                //for aapplying a font to subMenu ...
                SubMenu subMenu = mi.getSubMenu();
                if (subMenu != null && subMenu.size() > 0) {
                    for (int j = 0; j < subMenu.size(); j++) {
                        MenuItem subMenuItem = subMenu.getItem(j);
                        applyFontToMenuItem(subMenuItem);
                    }
                }
                //the method we have create in activity
                applyFontToMenuItem(mi);
            }
           // bottomNavigationView.getMenu().findItem(R.id.action_item_notify).setEnabled(false);
           // bottomNavigationView.getMenu().findItem(R.id.action_item_fav).setEnabled(false);


        }
        //  TextView numberfollowers =  header.findViewById(R.id.numberfollowers);
        // numberfollowers.setText(followers);
        //TextView numberfollowing = header.findViewById(R.id.numberfollowing);
        // numberfollowing.setText(following);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        if (bottomNavigationView != null) {
            Log.d("item", "item");
            menu = bottomNavigationView.getMenu();



            for(int i = 0; i < menu.size(); i++) {
                SpannableString spannableString = new SpannableString(menu.getItem(i).getTitle());
                spannableString.setSpan(new MenuSpannable(),0,spannableString.length(),0);
                menu.getItem(i).setTitle(spannableString);
                applyFontToMenuItem(menu.getItem(i));
            }
            bottomNavigationView.setOnNavigationItemSelectedListener
                    (item -> {

                        selectFragment(item);
                        return false;
                    });
        }
        selectFragment(menu.getItem(2));
        edittext = findViewById(R.id.Edittext);
        edittext.setFocusable(false);
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (FRAG_TAG == "Recommended"){
                        Log.d("Recommended", "Recommended");
                        startActivity(new Intent(ActivityMain.this, ActivityAddFriends.class).putExtra("search", "1"));
                    }else {
                        Log.d("Recommended", "Other");
                        edittext.setFocusableInTouchMode(true);
                    }
                }
                return false;
            }
        });
        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchFilter(home_tab, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("") || s.toString() == "") {
                    home_tab.afterSearch();
                }
            }
        });

        final View activityRootView = findViewById(R.id.activityRoot);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 200) { // if more than 100 pixels, its probably a keyboard...
                    Log.d("keyboard", "show");
                    edittext.setFocusableInTouchMode(true);
                }else{
                    Log.d("keyboard", "hide");
                    edittext.setFocusable(false);
                }
            }
        });

    }

    private void startTracking() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(ActivityMain.this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1234);
            } else if (Settings.canDrawOverlays(ActivityMain.this)) {
                startService(new Intent(ActivityMain.this, TrackingService.class).putExtra(Utils.P_Track_Img, appSharedPreferences.readString("image")));
            }
        } else {
            startService(new Intent(ActivityMain.this, TrackingService.class).putExtra(Utils.P_Track_Img, appSharedPreferences.readString("image")));
        }
        //getActivity().bindService(new Intent(getActivity(), TrackingService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    private void showChatHeadMsg() {
        java.util.Date now = new java.util.Date();
        String str = "test by henry  " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        Intent it = new Intent(ActivityMain.this, TrackingService.class);
        it.putExtra(Utils.EXTRA_MSG, str);
        startService(it);
    }

    private void needPermissionDialog(final int requestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub


            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void requestPermission(int requestCode) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, requestCode);
    }


    private void GetNotificationsCount(int notifPageId1) {
        Log.d("pageidd", notifPageId1 + "");
        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");
        StorednotificationCount = appSharedPreferences.readInteger("notifi_count");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<NotificationObject> call = apiService.getNotification(appSharedPreferences.readString("lang"), "application/json", "api/v1/notifications?page=" + notifPageId1, "Bearer " + token);
        call.enqueue(new Callback<NotificationObject>() {
            @Override
            public void onResponse(Call<NotificationObject> call, Response<NotificationObject> response) {
                if (response.isSuccessful()) {
                    ApiNotificationCount = response.body().getNotificationItemsObject().getTotal();
                    newNotifiNumber = ApiNotificationCount - StorednotificationCount;
                    if (newNotifiNumber > 99) {
                        notifiItemNumber.setVisibility(View.VISIBLE);
                        notifiItemNumber.setText("99+");
                    } else if (newNotifiNumber == 0) {
                        notifiItemNumber.setVisibility(View.INVISIBLE);
                    } else {
                        notifiItemNumber.setVisibility(View.VISIBLE);
                        notifiItemNumber.setText(newNotifiNumber + "");
                    }
                    Log.d("newNotifiNumber", newNotifiNumber + "");

                    // badge.setVisibility(View.VISIBLE);

                    // Log.d("nextPage",nextPageNotification);


                } else if (response.code() == 401) {
                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(ActivityMain.this, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    //CustomToast("Message is : " + apiError.getMessage());
                    RefreshUserToken(refreshToken, 5);

                }
            }

            @Override
            public void onFailure(Call<NotificationObject> call, Throwable t) {
                if (ActivityMain.this != null) {
//                    Toast.makeText(ActivityMain.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });

    }

    private void StartDialog() {
        Dialog dialog = new Dialog(ActivityMain.this);
        dialog.show();
        dialog.setContentView(R.layout.location_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(false);
        dialog.setTitle("");
        Button openMap = dialog.findViewById(R.id.open_map);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MapActivity.class));
                finish();

            }
        });
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface interface3, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    killActivity();

                }
                return true;
            }
        });

    }

    private void killActivity() {
        finish();
    }

    public void setHeaderDrawerImg(String headerDrawerImg) {
        if (headerDrawerImg != null) {
            Log.d("url_new_image1", headerDrawerImg+" main");
            Picasso.with(ActivityMain.this).load(headerDrawerImg).resize(200, 200).placeholder(R.drawable.place_holder_user).into(userImage);
        }
    }

    public void setUserName(String userName) {
        userNameTv.setText(userName);
    }

    protected void selectFragment(MenuItem item) {
        for(int i = 0; i < menu.size(); i++) {
            MenuSpannable menuSpannable = new MenuSpannable();
            menuSpannable.setSelected(item.getItemId() == menu.getItem(i).getItemId());
            SpannableString sString = new SpannableString(menu.getItem(i).getTitle());
            sString.setSpan(menuSpannable,0,sString.length(),0);
            menu.getItem(i).setTitle(sString);
        }
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.action_item1:
                item.setChecked(true);
                if(token.equals("")){
                    item.setIcon(R.drawable.ic_recommended2);
                    menu.getItem(1).setIcon(R.drawable.ic_checkin);
                    menu.getItem(2).setIcon(R.drawable.ic_home2);
                    menu.getItem(3).setIcon(R.drawable.ic_favorit1);
                    menu.getItem(4).setIcon(R.drawable.orders);
                    showLoginToast();
                }else {
                selectedFragment = Recommended_tab.newInstance();
                android.support.v4.app.FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.frame_layout_home, selectedFragment);
                transaction2.commit();
//                Toast.makeText(ActivityMain.this, "Recommended", Toast.LENGTH_SHORT).show();
               // CustomToast("Recommended");
                item.setIcon(R.drawable.ic_recommended2);
                menu.getItem(1).setIcon(R.drawable.ic_checkin);
                menu.getItem(2).setIcon(R.drawable.ic_home2);
                menu.getItem(3).setIcon(R.drawable.ic_favorit1);
                menu.getItem(4).setIcon(R.drawable.orders);
                FRAG_TAG = "Recommended";}
                break;
            case R.id.action_item2:
                item.setChecked(true);
                selectedFragment = Checkin_tab.newInstance();
                android.support.v4.app.FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.frame_layout_home, selectedFragment);
                transaction3.commit();
//                Toast.makeText(getApplicationContext(), "Chickin", Toast.LENGTH_SHORT).show();
                //CustomToast("Checkin");
                item.setIcon(R.drawable.ic_checkin2);
                menu.getItem(0).setIcon(R.drawable.ic_recommended1);
                menu.getItem(2).setIcon(R.drawable.ic_home2);
                menu.getItem(3).setIcon(R.drawable.ic_favorit1);
                menu.getItem(4).setIcon(R.drawable.orders);
                FRAG_TAG = "ChickIn";
                break;
            case R.id.action_item_home:
                item.setChecked(true);

                android.support.v4.app.FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                transaction4.replace(R.id.frame_layout_home, home_tab);
                transaction4.commit();
//                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
              //  CustomToast("Home");
                item.setIcon(R.drawable.ic_home3);

                menu.getItem(0).setIcon(R.drawable.ic_recommended1);
                menu.getItem(1).setIcon(R.drawable.ic_checkin);
                menu.getItem(3).setIcon(R.drawable.ic_favorit1);
                menu.getItem(4).setIcon(R.drawable.orders);
                FRAG_TAG = "Home";
                break;
            case R.id.action_item_fav:
                item.setChecked(true);
                if(token.equals("")){
                    item.setIcon(R.drawable.ic_favorit2);
                    menu.getItem(0).setIcon(R.drawable.ic_recommended1);
                    menu.getItem(1).setIcon(R.drawable.ic_checkin);
                    menu.getItem(2).setIcon(R.drawable.ic_home2);
                    menu.getItem(4).setIcon(R.drawable.orders);
                    showLoginToast();

                }else {
                selectedFragment = Favorites_tab.newInstance();
                android.support.v4.app.FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                transaction5.replace(R.id.frame_layout_home, selectedFragment);
                transaction5.commit();
//                Toast.makeText(getApplicationContext(), "Favorites", Toast.LENGTH_SHORT).show();
               // CustomToast("Favorites");
                item.setIcon(R.drawable.ic_favorit2);
                menu.getItem(0).setIcon(R.drawable.ic_recommended1);
                menu.getItem(1).setIcon(R.drawable.ic_checkin);
                menu.getItem(2).setIcon(R.drawable.ic_home2);
                menu.getItem(4).setIcon(R.drawable.orders);
                }
                break;
            case R.id.action_item_notify:
                item.setChecked(true);
                if(token.equals("")){
                    item.setIcon(R.drawable.orders_red);
                    menu.getItem(0).setIcon(R.drawable.ic_recommended1);
                    menu.getItem(1).setIcon(R.drawable.ic_checkin1);
                    menu.getItem(2).setIcon(R.drawable.ic_home2);
                    menu.getItem(3).setIcon(R.drawable.ic_favorit1);
                    showLoginToast();

                }else {
                selectedFragment = OrderListFragment.newInstance();
                android.support.v4.app.FragmentTransaction transaction6 = getSupportFragmentManager().beginTransaction();
                transaction6.replace(R.id.frame_layout_home, selectedFragment);
                transaction6.commit();
//                Toast.makeText(getApplicationContext(), "Listed Order", Toast.LENGTH_SHORT).show();
               // CustomToast("Listed Order");
                item.setIcon(R.drawable.orders_red);
                menu.getItem(0).setIcon(R.drawable.ic_recommended1);
                menu.getItem(1).setIcon(R.drawable.ic_checkin1);
                menu.getItem(2).setIcon(R.drawable.ic_home2);
                menu.getItem(3).setIcon(R.drawable.ic_favorit1);
                // appSharedPreferences.writeInteger("notifi_count",ApiNotificationCount);
               // badge.setVisibility(View.GONE);
                    }

                break;
        }
    }

    private void showLoginToast() {

        Typeface txtfont;
        Typeface titlefont;


        if(appSharedPreferences.readString("lang").equals("ar")){
            txtfont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Arabic_R.ttf");
            titlefont = Typeface.createFromAsset(getAssets(), "fonts/Abdoullah-Ashgar-EL-kharef.ttf");


        }else {
            txtfont = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Regular.ttf");
            titlefont = Typeface.createFromAsset(getAssets(), "fonts/forte.ttf");
        }
        Alerter.create(this)
                .setTitle(getResources().getString(R.string.dearCustomer))
                .setTitleTypeface(titlefont)
                .setTextTypeface(txtfont)
                .setTitleAppearance(R.style.AlertTextAppearance_Title)
                .setTextAppearance(R.style.AlertTextAppearance_Text)
                .setText(getResources().getString(R.string.mustLogin))
                .enableInfiniteDuration(false)
                .setBackgroundColorInt(getResources().getColor(R.color.colorPrimary))
                .setIcon(getResources().getDrawable(R.drawable.alerter_ic_notifications))
                .setIconColorFilter(getResources().getColor(R.color.white))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ActivityMain.this, LoginActivity.class));
                        finish();

                    }
                })
                .show();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (token.equals("")) {

            MenuItem itemCart = menu.findItem(R.id.cart);
            itemCart.setEnabled(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("profile");
                /*Fragment selectedFragment = null;
                selectedFragment = MyProfile.newInstance();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.commit();*/

                startActivity(new Intent(getApplicationContext(), ActivityMainCall.class).putExtra("fcall", "profile"));

                break;
            case R.id.wallet:
                startActivity(new Intent(getApplicationContext(), ActivityMainCall.class).putExtra("fcall", "wallet"));

//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("wallet");
                break;
            case R.id.messages:
                startActivity(new Intent(getApplicationContext(), ActivityMessages.class));
                notifiback = true;
                cartback = true;
//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("messages");
                break;
            case R.id.adduser:
                startActivity(new Intent(getApplicationContext(), ActivityAddFriends.class).putExtra("search", "0"));
                notifiback = true;
                cartback = true;
//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("adduser");
                break;
            case R.id.myorders:
                if (ActivityMyOrders.getInstance() != null) {
                    ActivityMyOrders.getInstance().closeActivity();
                }
                startActivity(new Intent(getApplicationContext(), ActivityMyOrders.class));
                notifiback = true;
                cartback = true;
//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("my orders");
                break;


            case R.id.saved:
//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("saved");
                startActivity(new Intent(getApplicationContext(), ActivityMainCall.class).putExtra("fcall", "saved"));
                /*Fragment selectedFragment3 = null;
                selectedFragment3 = Save_Fragment.newInstance();
                android.support.v4.app.FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.replace(R.id.frame_layout_call, selectedFragment3);
                transaction3.commit();*/

                break;

            case R.id.help:
                startActivity(new Intent(getApplicationContext(), HelpCenter.class));
                notifiback = true;
                cartback = true;
//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("help");
                break;
            case R.id.Contact_us:
                if (ContactUs.getInstance() != null) {
                    ContactUs.getInstance().closeActivity();
                }
                startActivity(new Intent(getApplicationContext(), ContactUs.class));
                notifiback = true;
                cartback = true;
//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("contact us");
                break;
            case R.id.logout:
                ///clear  shared data..
                clearUserData();
                //TODO /////////////////Language/////////////////////
                String lang = "en";
                if (Locale.getDefault().getLanguage().equals("ar")) {
                    lang = "ar";
                }
                appSharedPreferences.writeString("lang", lang);

                appSharedPreferences.writeInteger("is_validate", 1);

                appSharedPreferences.writeInteger("intro", 1);

                //////////////////////////////////////////////////////
                startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));

//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
                //CustomToast("logout");
                break;
        }


        flipMenu(GravityCompat.START);
        return true;
    }

    public void clearUserData() {
        appSharedPreferences.clear();

        // editor.clear();
        //  editor.commit();

    }

    public void flipMenu(int gravity) {
        if (drawerLayout.isDrawerOpen(gravity)) {
            drawerLayout.closeDrawer(gravity);
        } else {
            Log.d("flipMenu", "flipMenuOpen");
            drawerLayout.openDrawer(gravity);
            getprofile();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (cartback) {
            refreshCart();
        } else if (notifiback) {
            refreshNotify();
        }
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
            Log.d("popBackStackImmediate", "popBackStackImmediate");
        } else if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            stopService(new Intent(ActivityMain.this, TrackingService.class));
            finish();
            /*if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
                this.doubleBackToExitPressedOnce = true;
                CustomToast(getString(R.string.exitBackPressed), 0);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);*/
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(ActivityMain.this)) {
                needPermissionDialog(requestCode);
            }else{
                startTracking();
            }

        }else if(requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG) {
            if (!Utils.canDrawOverlays(ActivityMain.this)) {
                needPermissionDialog(requestCode);
            } else {
                showChatHeadMsg();
            }}
        if (requestCode == CAPTURE_MEDIA && resultCode == RESULT_OK) {

            m_dialog.setContentView(R.layout.add_3media_dialog);

            m_dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mediaUrls.clear();}});

            m_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    mediaUrls.clear();
                }});
            mediaContainer= m_dialog.findViewById(R.id.m_container);
            mediaAdapter=new MediaAdapter(ActivityMain.this,mediaUrls);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivityMain.this,LinearLayoutManager.HORIZONTAL,false);
            mediaContainer.setLayoutManager(layoutManager);
            mediaContainer.setAdapter(mediaAdapter);
            mediaUrls.add("file://"+data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH));
            mediaAdapter.notifyDataSetChanged();
            Log.d("iii",data.getStringExtra(AnncaConfiguration.Arguments.FILE_PATH));

            addMedia= m_dialog.findViewById(R.id.add_item);
            Button close= m_dialog.findViewById(R.id.close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaUrls.clear();
                    m_dialog.dismiss();}});
            if(mediaUrls.size()<3){
                addMedia.setVisibility(View.VISIBLE);
            }else {
                addMedia.setVisibility(View.GONE);}

            addMedia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AnncaConfiguration.Builder photo_video = new AnncaConfiguration.Builder(ActivityMain.this, CAPTURE_MEDIA);
                    photo_video.setMediaAction(AnncaConfiguration.MEDIA_ACTION_UNSPECIFIED);
                    photo_video.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_MEDIUM);
                    photo_video.setCameraFace(AnncaConfiguration.CAMERA_FACE_REAR);
                    photo_video.setMediaResultBehaviour(AnncaConfiguration.PREVIEW);
                    photo_video.setMinimumVideoDuration(1000);
                    photo_video.setVideoDuration(30000);
                    if (ActivityCompat.checkSelfPermission(ActivityMain.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    new Annca(photo_video.build()).launchCamera();
                }
            });

            Button savebtn=m_dialog.findViewById(R.id.savebtn);
            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mediaUrls.size()>0){
                        progressDialog.setTitle(getString(R.string.upload_media));
                        progressDialog.setMessage(getString(R.string.please_wait));
                        //progressDialog.setMax(100);
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        //    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();
                        try {
                            upload3Media(appSharedPreferences.readInteger("orderItemId"),
                                    appSharedPreferences.readInteger("orderId"), mediaUrls);
                        } catch (IOException e) {

                            e.printStackTrace();
                        }
                      /*  if (!WifiReciver.conn.equals("")) {
                            if (WifiReciver.conn.equals("1")) {
                                try {


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }} else if (WifiReciver.conn.equals("0")) {
                                message_dialog(0, "");
                                progressDialog.dismiss();
                            }
                        }*/
                    }}});
            m_dialog.show();

        }

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri selectedImageURI = data.getData();
                    InputStream is = ActivityMain.this.getContentResolver().openInputStream(data.getData());
                    //Toast.makeText(this, is.toString(), Toast.LENGTH_SHORT).show();
                    final Dialog dialog = new Dialog(this);
                    dialog.setContentView(R.layout.show_img_dialog);
                    dialog.setTitle("");
                    ImageView image = dialog.findViewById(R.id.selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    image.setImageBitmap(bitmap);
                    dialog.show();
                    /////

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    private void sendRecommendVideo(String videoUrl) throws IOException {
        Uri selectedVideoURI = Uri.parse(videoUrl);
        InputStream iStream = null;
        Log.d("sssssss", selectedVideoURI.toString());
        //InputStream iStream = null;
        //iStream = ShowPicActivity.this.getContentResolver().openInputStream(selectedImageURI);
        try {
            iStream = getContentResolver().openInputStream(selectedVideoURI);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        progressDialog.setTitle(getString(R.string.upload_media));
        progressDialog.setMessage(getString(R.string.please_wait));

        progressDialog.show();
        if (!WifiReciver.conn.equals("")) {
            if (WifiReciver.conn.equals("1")) {

                uploadVideo(null, appSharedPreferences.readInteger("orderItemId"),
                        appSharedPreferences.readInteger("orderId"), getVideoBytes(iStream));

            } else if (WifiReciver.conn.equals("0")) {
                CustomToast(getString(R.string.connection_message));
                progressDialog.dismiss();
            }
        }


    }

    private void uploadVideo(byte[] imageBytes, int orderItemId, int orderId, byte[] videoBytes) {
        imgBytes = imageBytes;
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        Log.d("eeeee", orderId + "===" + orderItemId);
        // Log.d("bytes", imageBytes.length + "");
        token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        // RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);
        RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), videoBytes);
        RequestBody orderIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderId + "");
        RequestBody orderItemIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderItemId + "");
        //MultipartBody.Part bodyImg = MultipartBody.Part.createFormData("customer_image", "image.jpg", requestFile);
        MultipartBody.Part bodyVideo = MultipartBody.Part.createFormData("customer_video", "video.mp4", requestFileVideo);
        Call<SaveImage> call = apiService.addImgToUserPurchase(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token, null, bodyVideo, orderIdRB, orderItemIdRB);
        // progressBarImage.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<SaveImage>() {
            @Override
            public void onResponse(Call<SaveImage> call, retrofit2.Response<SaveImage> response) {
                if (response.isSuccessful()) {
                    SaveImage responseBody = response.body();
                    if (responseBody.getStatus()) {
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        //CustomToast(response.body().getUrl_customer_image() + "");
                        Log.d("img", response.body().getUrl_customer_image());
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.uploadedSuccess));
                        progressDialog.dismiss();
                        selectFragment(bottomNavigationView.getMenu().getItem(0));


                    }

                } else if (response.code() == 401) {
                    progressDialog.dismiss();

                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss", "ssss");
                    //  Toast.makeText(ActivityMain.this, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();

                    //RefreshUserToken(refreshToken,5);
                    //getPurchaseData();
                    // Toast.makeText(getActivity(), "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();

//                    Toast.makeText(getApplicationContext(), getString(R.string.uploaderror), Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.uploaderror));
                }
            }

            @Override
            public void onFailure(Call<SaveImage> call, Throwable t) {
                progressDialog.dismiss();
                if (ActivityMain.this != null) {
//                    Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.uploaderror));
                }
                //Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private void sendRecommendImage(String imgUrl) {
        Uri selectedImageURI = Uri.parse(imgUrl);
        Log.d("sssssss", selectedImageURI.toString());
        progressDialog.setTitle(getString(R.string.upload_media));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        imgBytes = convertImageToByte(selectedImageURI);
        if (!WifiReciver.conn.equals("")) {
            if (WifiReciver.conn.equals("1")) {

                uploadImage(imgBytes, appSharedPreferences.readInteger("orderItemId"), appSharedPreferences.readInteger("orderId"), null);

            } else if (WifiReciver.conn.equals("0")) {
                CustomToast(getString(R.string.connection_message));
                progressDialog.dismiss();
            }
        }


    }

    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(ActivityMain.this);
        dialog1.show();
        dialog1.setContentView(R.layout.message_dialog);
        final TextView message = dialog1.findViewById(R.id.message);
        final Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        String char_message = null;
        if (conn == 0)
            char_message = getApplicationContext().getString(R.string.connection_message);
        else if (conn == 1)
            char_message = getApplicationContext().getString(R.string.tryagain);
        else if (conn == 2)
            char_message = messagedialg;
        message.setText(char_message);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    private void uploadImage(byte[] imageBytes, int orderItemId, int orderId, byte[] videoBytes) {
        imgBytes = imageBytes;
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        Log.d("eeeee", orderId + "===" + orderItemId);
        token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);
        //RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), videoBytes);
        RequestBody orderIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderId + "");
        RequestBody orderItemIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderItemId + "");
        MultipartBody.Part bodyImg = MultipartBody.Part.createFormData("customer_image", "image.jpg", requestFile);
        // MultipartBody.Part bodyVideo = MultipartBody.Part.createFormData("customer_video", "video.mp4", requestFileVideo);
        Call<SaveImage> call = apiService.addImgToUserPurchase(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token, bodyImg, null, orderIdRB, orderItemIdRB);
        // progressBarImage.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<SaveImage>() {
            @Override
            public void onResponse(Call<SaveImage> call, retrofit2.Response<SaveImage> response) {
                if (response.isSuccessful()) {
                    SaveImage responseBody = response.body();
                    if (responseBody.getStatus()) {
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        //CustomToast(response.body().getUrl_customer_image() + "");
                        Log.d("img", response.body().getUrl_customer_image());
                        progressDialog.dismiss();
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.uploadedSuccess));
                        selectFragment(bottomNavigationView.getMenu().getItem(0));

                    }
                } else if (response.code() == 401) {
                    progressDialog.dismiss();
                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss", "ssss");
                } else {
                    progressDialog.dismiss();
//                    Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.uploaderror));
                }
            }

            @Override
            public void onFailure(Call<SaveImage> call, Throwable t) {
                progressDialog.dismiss();
                if (ActivityMain.this != null) {
//                    Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.uploaderror));
                }
                //Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    public static byte[] getVideoBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public byte[] convertImageToByte(Uri uri) {
        byte[] data = null;
        try {
            ContentResolver cr = getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            Bitmap bitmap1 = modifyOrientation(bitmap, uri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static Bitmap modifyOrientation(Bitmap bitmap, Uri image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);
            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void uploadImage(byte[] imageBytes, int orderItemId, int orderId) {
        imgBytes = imageBytes;
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        Log.d("bytes", imageBytes.length + "");
        token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);
        RequestBody orderIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderId + "");
        RequestBody orderItemIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderItemId + "");

        MultipartBody.Part body = MultipartBody.Part.createFormData("customer_image", "image.jpg", requestFile);
        Call<SaveImage> call = apiService.addImgToUserPurchase(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token, body, null, orderIdRB, orderItemIdRB);
        // progressBarImage.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<SaveImage>() {
            @Override
            public void onResponse(Call<SaveImage> call, Response<SaveImage> response) {
                if (response.isSuccessful()) {
                    isRefreshing = false;
                    SaveImage responseBody = response.body();
                    if (responseBody.getStatus()) {
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                            //CustomToast(response.body().getUrl_customer_image() + "");
                        Log.d("img", response.body().getUrl_customer_image());
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.uploadedSuccess));
                    } else {
//                        Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.uploaderror));
                    }

                } else if (response.code() == 401) {
                    isRefreshing = true;
                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss", "ssss");
//                    Toast.makeText(ActivityMain.this, , Toast.LENGTH_SHORT).show();
                    //CustomToast("Message is : " + apiError.getMessage());
                    RefreshUserToken(refreshToken, 5);

                    //getPurchaseData();
                    // Toast.makeText(getActivity(), "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();
                    //progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<SaveImage> call, Throwable t) {
                // Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                //Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                if (ActivityMain.this != null) {
//                    Toast.makeText(ActivityMain.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }

    public void showUserVideo(String customer_video) {

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.show_video_dialog);
        dialog.setTitle("");
        ExoVideoView videoView = dialog.findViewById(R.id.selectedVideo);
        SimpleMediaSource mediaSource = new SimpleMediaSource(customer_video);
        videoView.play(mediaSource);

        videoView.setBackgroundColor(getResources().getColor(R.color.Black4));
        videoView.setBackListener(new ExoVideoPlaybackControlView.ExoClickListener() {
            @Override
            public boolean onClick(@Nullable View view, boolean isPortrait) {

                if (isPortrait)
                    dialog.dismiss();
                return false;
            }
        });
        dialog.show();
    }

    public void showUserImage(String customer_image) {
        Log.d("hhh", customer_image);
        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.show_img_dialog);
        dialog.setTitle("");
        ImageView image = dialog.findViewById(R.id.selectedImage);
        Picasso.with(this).load(customer_image).into(image);


        dialog.show();
    }

    public void searchFilter(Home_tab home_tab, String TextFilter) {

        if (FRAG_TAG == "Home") {
            ArrayList<Providers> filterProviders = new ArrayList<>();
            filterProviders.clear();
            ArrayList<Providers> providersData = home_tab.sharedData();
            int TotalPages = home_tab.getTOTAL_PAGES();
            int CurrentPage = home_tab.getCurrentPage();
            Log.e("DATA", providersData.size() + "");
            String resultEditText = TextFilter;
            int textLength = resultEditText.length();
            filterProviders.clear();

            for (int i = 0; i < providersData.size(); i++) {
                Providers providers = (Providers) providersData.get(i);
                String resultNamelist = providers.getName().trim();
                if (!resultEditText.equals("")) {
                    int xx = 0;
                    for (String ss : resultEditText.split(" ", 2)) {
                        str[xx] = ss;
                        xx++;
                    }
                    if (textLength <= resultNamelist.length()) {
                        if (resultNamelist.toLowerCase(Locale.getDefault()).contains(str[0].toLowerCase()) & resultNamelist.toLowerCase(Locale.getDefault()).contains(str[1].toLowerCase())) {
                            filterProviders.add(providers);
                        }

                    }
                } else {
                    filterProviders.add(providers);
                }
            }

            if (filterProviders.size() > 0) {
                home_tab.SearchResult(filterProviders);
            } else if (filterProviders.size() == 0) {
                if (CurrentPage < TotalPages) {
                    CurrentPage += 1;
                    home_tab.loadNextSearch();
                    searchFilter(home_tab, TextFilter);
                    home_tab.SearchResult(filterProviders);
                } else if (CurrentPage == TotalPages) {
                    home_tab.SearchResult(filterProviders);
                }
            }
        }
    }

    public void ImagePickChooser(int orderId, int orderItemId) {
        this.orderId = orderId;
        this.orderItemId = orderItemId;
        /////
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.pick_image_dialog);
        dialog.setTitle("");
        Button fromGallery = dialog.findViewById(R.id.from_gallery);
        Button fromCamera = dialog.findViewById(R.id.from_camera);
        fromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceAvatarFromGallery();
                dialog.dismiss();
            }
        });
        fromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceAvatarFromCamera();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void choiceAvatarFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1);
    }

    public void choiceAvatarFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    ////////
    public void RefreshUserToken(String userRefreshToken, int id) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        refreshToken = appSharedPreferences.readString("refresh_token");

        Call<LoginResponse> call = apiService.refreshToken(appSharedPreferences.readString("lang"), "application/json", "refresh_token", 3, "pzavbkx5WTC9HaEoKWEhY31xXqgXFNBzAYGzMUXb", refreshToken);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    appSharedPreferences.writeString("access_token", response.body().getAccess_token());
                    appSharedPreferences.writeString("token_type", response.body().getToken_type());
                    appSharedPreferences.writeString("refresh_token", response.body().getRefresh_token());
//                    Toast.makeText(ActivityMain.this, , Toast.LENGTH_SHORT).show();
                    //CustomToast("token is refreshed");
                    switch (id) {
                        case 0:
                            //notification
                            selectFragment(bottomNavigationView.getMenu().getItem(2));
                            break;
                        case 1:
                            appSharedPreferences.writeString("Orefresh", "");
                            appSharedPreferences.writeString("Prefresh", "");

                            //favourit
                            selectFragment(bottomNavigationView.getMenu().getItem(0));
                            break;

                        case 2:
                            //resturant
                            if (ActivityMainCall.getInstance() != null) {
                                ActivityMainCall.getInstance().closeActivity();
                            }
                            startActivity(new Intent(ActivityMain.this, ActivityMainCall.class)
                                    .putExtra("fcall", "res"));
                            break;
                        case 3:
                            if (ActivityMainCall.getInstance() != null) {
                                ActivityMainCall.getInstance().closeActivity();
                            }
                            startActivity(new Intent(ActivityMain.this, ActivityMainCall.class)
                                    .putExtra("fcall", "order"));
                            break;
                        case 4:
                            if (Map2Activity.getInstance() != null) {
                                Map2Activity.getInstance().closeActivity();
                            }
                            startActivity(new Intent(getApplicationContext(), Map2Activity.class));
                            break;
                        case 5:
                            GetNotificationsCount(1);
                            break;

                        case 6:
                            selectFragment(bottomNavigationView.getMenu().getItem(2));
                            break;
                        //   case 5:
                        //upload image
                        //   uploadImage(imgBytes, orderItemId,  orderId);

                        //   break;
                        default:
                            if (navigationView.getMenu().findItem(id) != null) {
                                navigationView.getMenu().performIdentifierAction(id, 0);
                                drawerLayout.closeDrawer(Gravity.START);
                            } else if (bottomNavigationView.getMenu().findItem(id) != null) {
                                bottomNavigationView.getMenu().performIdentifierAction(id, 0);
                            }
                            break;
                    }
                } else if (response.code() == 401) {
                    APIError apiError = ErrorUtils.parseError(response);
                    // Toast.makeText(ActivityMain.this, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    endTokenDialog();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (ActivityMain.this != null) {
//                    Toast.makeText(ActivityMain.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }


    private void endTokenDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.token_finish_dialog);
        dialog.setTitle("");

        Button btn_ok = dialog.findViewById(R.id.btn_ok);
        Button cancel = dialog.findViewById(R.id.cancel);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, LoginActivity.class));
                clearUserData();
                finish();
            }
        });
        dialog.show();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void showViews() {
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

    }

    public void hideViews() {
        toolbar.animate().translationY(-Utils.getToolbarHeight(this)).setInterpolator(new AccelerateInterpolator(2));
        // toolbar.setTranslationY(-toolbar.getHeight());
    }

    public void onMoved(int distance) {
        toolbar.setTranslationY(-distance);

    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font;
        if (appSharedPreferences.readString("lang").equals("ar")) {
            font = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Arabic_B.ttf");

        } else {
            font = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Bold.ttf");
        }
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public void refreshCart() {
        //cartItemLayout= (RelativeLayout) menu.findItem(R.id.call_cart).getActionView();

        //  cartImage = cartItemLayout.findViewById(R.id.img);
        // cartItemNumber= cartItemLayout.findViewById(R.id.cart_num);
        cartList = appSharedPreferences.readArray("uporder");
        if (cartList.size() > 99) {
            cartItemNumber.setVisibility(View.VISIBLE);
            cartItemNumber.setText("99+");
        } else if (cartList.size() == 0) {
            cartItemNumber.setVisibility(View.GONE);
        } else {
            cartItemNumber.setVisibility(View.VISIBLE);
            cartItemNumber.setText(cartList.size() + "");

        }

    }

    public void refreshNotify() {
        //cartItemLayout= (RelativeLayout) menu.findItem(R.id.call_cart).getActionView();

        //  cartImage = cartItemLayout.findViewById(R.id.img);
        // cartItemNumber= cartItemLayout.findViewById(R.id.cart_num);
        GetNotificationsCount(1);


    }

    private void getprofile() {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PProfileObject> call = apiService.getCustomer(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token);
        call.enqueue(new Callback<PProfileObject>() {
            @Override
            public void onResponse(Call<PProfileObject> call, Response<PProfileObject> response) {

                if (response.isSuccessful()) {
                    try {
                        PProfileObject pProfileObject = response.body();
                        if (pProfileObject.getStatus().equals("true")) {
                            CustomerObject customerObject = pProfileObject.getCustomerObject();
                            String imageS = customerObject.getImage();
                            if (imageS != null) {
                                appSharedPreferences.writeString("user_Image", imageS);
                            }
                            appSharedPreferences.writeString("user_followers", customerObject.getFollowers() + "");
                            appSharedPreferences.writeString("user_following", customerObject.getFollowing() + "");
                            appSharedPreferences.writeString("username", customerObject.getName() + "");
                            appSharedPreferences.writeString("customer_id", customerObject.getId() + "");

                            Picasso.with(ActivityMain.this).load(customerObject.getUrl_image()).resize(200, 200).placeholder(R.drawable.place_holder_user).into(userImage);
                            userNameTv.setText(customerObject.getName());
                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PProfileObject> call, Throwable t) {
                if (ActivityMain.this != null) {
//                    Toast.makeText(ActivityMain.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ActivityMain.this, texttoast, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }
    public void CustomToast1(String texttoast, Context context) {

        Toast toast = Toast.makeText(context, texttoast, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }

    private void upload3Media(int orderItemId, int orderId,ArrayList<String> mediaUrls) throws IOException {
        this.orderItemId=orderItemId;
        this.orderId=orderId;
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        RequestBody orderIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderId + "");
        RequestBody orderItemIdRB = RequestBody.create(MediaType.parse("multipart/form-data"), orderItemId + "");
        token = appSharedPreferences.readString("access_token");
        List<MultipartBody.Part> imgList=new ArrayList<>();
        MultipartBody.Part m1=null;
        MultipartBody.Part m2=null;
        MultipartBody.Part m3=null;
        if(mediaUrls.size()==1){
            Log.d("mmmmm",mediaUrls.get(0)+"");
            String[] separated = mediaUrls.get(0).split("\\.");
            if (separated[separated.length - 1].trim().equals("jpg") || separated[separated.length - 1].trim().equals("png")
                    || separated[separated.length - 1].trim().equals("jpeg")) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), convertImageToByte(Uri.parse(mediaUrls.get(0))));
                m1 = MultipartBody.Part.createFormData("customer_file_a", "image.jpg", requestFile);
                imgList.add(m1);

            } else if (separated[separated.length - 1].trim().equals("mp4")) {
                try {
                    iStream= getContentResolver().openInputStream(Uri.parse( mediaUrls.get(0)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();}
                RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), getVideoBytes(iStream));
                m1 = MultipartBody.Part.createFormData("customer_file_a", "video.mp4", requestFileVideo);
                imgList.add(m1);

            }


        }else if(mediaUrls.size()==2){
            String[] separated1 = mediaUrls.get(0).split("\\.");
            if (separated1[separated1.length - 1].trim().equals("jpg") || separated1[separated1.length - 1].trim().equals("png") ||
                    separated1[separated1.length - 1].trim().equals("jpeg")) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), convertImageToByte(Uri.parse(mediaUrls.get(0))));
                m1 = MultipartBody.Part.createFormData("customer_file_a", "image.jpg", requestFile);
                imgList.add(m1);

            } else if (separated1[separated1.length - 1].trim().equals("mp4")) {
                try {
                    iStream= getContentResolver().openInputStream(Uri.parse( mediaUrls.get(0)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();}
                RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), getVideoBytes(iStream));
                m1 = MultipartBody.Part.createFormData("customer_file_a", "video.mp4", requestFileVideo);
                imgList.add(m1);

            }

            String[] separated2 = mediaUrls.get(1).split("\\.");
            if (separated2[separated2.length - 1].trim().equals("jpg") || separated2[separated2.length - 1].trim().equals("png") ||
                    separated2[separated2.length - 1].trim().equals("jpeg")) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), convertImageToByte(Uri.parse(mediaUrls.get(1))));
                m2 = MultipartBody.Part.createFormData("customer_file_b", "image.jpg", requestFile);
                imgList.add(m2);

            } else if (separated2[separated2.length - 1].trim().equals("mp4")) {
                try {
                    iStream= getContentResolver().openInputStream(Uri.parse( mediaUrls.get(1)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();}
                RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), getVideoBytes(iStream));
                m2 = MultipartBody.Part.createFormData("customer_file_b", "video.mp4", requestFileVideo);
                imgList.add(m2);

            }

        }else if(mediaUrls.size()==3) {
            String[] separated1 = mediaUrls.get(0).split("\\.");
            if (separated1[separated1.length - 1].trim().equals("jpg") || separated1[separated1.length - 1].trim().equals("png")
                    || separated1[separated1.length - 1].trim().equals("jpeg")) {
                Log.d("img1",mediaUrls.get(0));
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), convertImageToByte(Uri.parse(mediaUrls.get(0))));
                m1 = MultipartBody.Part.createFormData("customer_file_a", "image.jpg", requestFile);
                imgList.add(m1);

            } else if (separated1[separated1.length - 1].trim().equals("mp4")) {
                try {
                    iStream = getContentResolver().openInputStream(Uri.parse(mediaUrls.get(0)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), getVideoBytes(iStream));
                m1 = MultipartBody.Part.createFormData("customer_file_a", "video.mp4", requestFileVideo);
                imgList.add(m1);

            }

            String[] separated2 = mediaUrls.get(1).split("\\.");
            if (separated2[separated2.length - 1].trim().equals("jpg") || separated2[separated2.length - 1].trim().equals("png")
                    || separated2[separated2.length - 1].trim().equals("jpeg")) {
                Log.d("img2",mediaUrls.get(1));

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), convertImageToByte(Uri.parse(mediaUrls.get(1))));
                m2 = MultipartBody.Part.createFormData("customer_file_b", "image.jpg", requestFile);
                imgList.add(m2);

            } else if (separated2[separated2.length - 1].trim().equals("mp4")) {
                try {
                    iStream = getContentResolver().openInputStream(Uri.parse(mediaUrls.get(1)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), getVideoBytes(iStream));
                m2 = MultipartBody.Part.createFormData("customer_file_b", "video.mp4", requestFileVideo);
                imgList.add(m2);

            }

            String[] separated3 = mediaUrls.get(2).split("\\.");
            if (separated3[separated3.length - 1].trim().equals("jpg") || separated3[separated3.length - 1].trim().equals("png")
                    || separated3[separated3.length - 1].trim().equals("jpeg")) {
                Log.d("img3",mediaUrls.get(2));

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), convertImageToByte(Uri.parse(mediaUrls.get(2))));
                m3 = MultipartBody.Part.createFormData("customer_file_c", "image.jpg", requestFile);
                imgList.add(m3);

            } else if (separated3[separated3.length - 1].trim().equals("mp4")) {
                try {
                    iStream = getContentResolver().openInputStream(Uri.parse(mediaUrls.get(2)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                RequestBody requestFileVideo = RequestBody.create(MediaType.parse("video/*"), getVideoBytes(iStream));
                m3 = MultipartBody.Part.createFormData("customer_file_c", "video.mp4", requestFileVideo);
                imgList.add(m3);

            }
        }

        Call<SaveImage> call = apiService.add3mediaToPurchase(appSharedPreferences.readString("lang"),"application/json","Bearer " + token, imgList, orderIdRB, orderItemIdRB);
        // progressBarImage.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<SaveImage>() {
            @Override
            public void onResponse(Call<SaveImage> call, retrofit2.Response<SaveImage> response) {
                if (response.isSuccessful()) {
                    SaveImage responseBody = response.body();
                    if (responseBody.getStatus()) {
                        mediaUrls.clear();

                        Toast.makeText(getApplicationContext(), response.body().getUrl_customer_image() + "", Toast.LENGTH_SHORT).show();
                        Log.d("img_a", response.body().getCustomer_file_a()+"");
                        Log.d("img_b", response.body().getCustomer_file_b()+"");
                        Log.d("img_c", response.body().getCustomer_file_c()+"");
                        Toast.makeText(getApplicationContext(), getString(R.string.uploadedSuccess), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        m_dialog.dismiss();
                        selectFragment(bottomNavigationView.getMenu().getItem(0));
                    }

                } else if(response.code()==401){
                    progressDialog.dismiss();
                    m_dialog.dismiss();

                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss","ssss");
                    RefreshUserToken(refreshToken,18);
                } else {
                    progressDialog.dismiss();
                    m_dialog.dismiss();


                    Toast.makeText(getApplicationContext(), getString(R.string.uploaderror), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveImage> call, Throwable t) {
                progressDialog.dismiss();
                if (ActivityMain.this != null) {
                    Toast.makeText(getApplicationContext(), getString(R.string.uploaderror), Toast.LENGTH_SHORT).show();
                }
                //Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
            }



        });
    }

    public void showAddButton() {
        addMedia.setVisibility(View.VISIBLE);
    }


    public void showUserMedia(ArrayList<mediaObject> mediaUrls) {
        boolean isclick=true;
        dialog=new Dialog(ActivityMain.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.slider_dialog);
        RecyclerView recyclerView= dialog.findViewById(R.id.media_recyclerview);
      //  FrameLayout delete_view=dialog.findViewById(R.id.delete_view);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ActivityMain.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ShowUserMediaAdapter  videoAdapter = new ShowUserMediaAdapter(ActivityMain.this, mediaUrls);
        recyclerView.setAdapter(videoAdapter);



        dialog.show();



    }

    public void closeMediaDialog() {

        dialog.dismiss();
    }


    public void updatePurchaseItem() {
        selectFragment(bottomNavigationView.getMenu().getItem(0));
    }
}
