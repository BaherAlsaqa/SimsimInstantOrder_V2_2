package net.phpsm.simsim.simsiminstantorder.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.CustomViews.CustomEditText;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.adapter.MyAddFriendsAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.UpOrder;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.NotificationObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Users;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UsersDatum;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class ActivityAddFriends extends AppCompatActivity {

    List<UsersDatum> addfriendsList = new ArrayList<>();
    RecyclerView friendsRecyclerView;
    MyAddFriendsAdapter UsersAdapter;
    SharedPreferences sharedPreferencesGet;
    String token, refreshToken;
    int pageId = 1;
    String nextPage;
    AVLoadingIndicatorView progressBar;
    CustomEditText searchView;
    String searchedText;
    int StorednotificationCount, ApiNotificationCount;

    AppSharedPreferences appSharedPreferences;
    ArrayList<UpOrder> cartList = new ArrayList<>();
    boolean back = false;

    ImageView cartImage, notifiImage;
    RelativeLayout cartItemLayout, notificationItemLayout, savedItemLayout;
    TextView cartItemNumber, notifiItemNumber;
    int newNotifiNumber = 0;
    boolean cartback = false;
    boolean notifiback = false;
    RelativeLayout rl_title;
    SwipeRefreshLayout swiperefresh;
    int totalItemCount;
    View internet;
    View empityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        Toolbar toolbar = findViewById(R.id.toolbar);
        searchView = toolbar.findViewById(R.id.search_view);
        appSharedPreferences = new AppSharedPreferences(ActivityAddFriends.this);
        StorednotificationCount = appSharedPreferences.readInteger("notifi_count");
        swiperefresh=findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        internet=findViewById(R.id.no_internet);
        empityData=findViewById(R.id.no_data);
        setSupportActionBar(toolbar);
        friendsRecyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progress);
        rl_title = findViewById(R.id.rl_title);
        //friendsRecyclerView.setHasFixedSize(true);
        // friendsRecyclerView.setItemViewCacheSize(10);
        // friendsRecyclerView.setDrawingCacheEnabled(true);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityAddFriends.this));
        UsersAdapter = new MyAddFriendsAdapter(addfriendsList, ActivityAddFriends.this);
        friendsRecyclerView.setAdapter(UsersAdapter);
        sharedPreferencesGet = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        token = appSharedPreferences.readString("access_token");
        progressBar.setVisibility(View.VISIBLE);
        FetchAllUsers();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                addfriendsList.clear();
                UsersAdapter.deleteAllData();
                pageId=1;
               // nextPage=null;
                FetchAllUsers();



            }
        });
        friendsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                // totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean endHasBeenReached = lastVisible + 1 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if (nextPage != null) {
                        pageId++;
                        //   progressBar.setVisibility(View.VISIBLE);
                        FetchAllUsers();
                    }
                    //you have reached to the bottom of your recycler view
                }
            }
        });

        //TODO ///////////////keyboard///////////////
        Intent intent = getIntent();
        if (intent != null){
            String searchValue = intent.getStringExtra("search");
            if (searchValue.equals("1")){
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                rl_title.setVisibility(View.GONE);
                Log.d("searchValue", "1");
            }else if (searchValue.equals("0")){
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                searchView.setFocusable(false);

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
                            searchView.setFocusableInTouchMode(true);
                        }else{
                            Log.d("keyboard", "hide");
                            searchView.setFocusable(false);
                        }
                    }
                });
                Log.d("searchValue", "0");
            }
        }

        /////////////////////////

        searchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                searchView.setFocusableInTouchMode(true);
                return false;
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.d("tt", s.toString());
                searchedText = s.toString();

                UsersAdapter.filterList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void FetchAllUsers() {
        if (!isNetworkConnected() &&pageId==1) {
            swiperefresh.setRefreshing(false);
            friendsRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        } else {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Users> call = apiService.getAllUsers(appSharedPreferences.readString("lang"), "api/v1/allcustomers?page=" + pageId, "application/json", "Bearer " + token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                    swiperefresh.setRefreshing(false);
                    empityData.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    friendsRecyclerView.setVisibility(View.VISIBLE);
                    internet.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                 /*   if (response.body().getItems().getPurchaseData().size() > 0) {
                        for (int i = 0; i < response.body().getItems().getPurchaseData().size(); i++) {
                            myPurchaseList.add(response.body().getItems().getPurchaseData().get(i).getProvider());
                            Log.d("indx", i + "");
                        }
                    }*/
                    addfriendsList = response.body().getItems().getData();
                    if(addfriendsList.size()==0 &&pageId==1){
                        empityData.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        friendsRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                    totalItemCount=addfriendsList.size();
                    nextPage = response.body().getItems().getNextPageUrl();
           Log.d("nextpage",nextPage+"");
                    if (nextPage == null) {
                        UsersAdapter.setIsFinish();
                    }

                    if (searchedText != null) {
                        UsersAdapter.filterList(searchedText);
                    }
                    UsersAdapter.addFriends(addfriendsList);
                    // myPurchaseAdapter.notifyDataSetChanged();
                    // Log.d("xxxx",myPurchaseList.size()+"");



                } else {
                    swiperefresh.setRefreshing(false);

                    APIError apiError = ErrorUtils.parseError(response);
                    /*Toast.makeText(getApplicationContext(), "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();*/
                    CustomToast("Message is : " + apiError.getMessage());
                    CustomToast("Error is : " + apiError.getError());
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

                if (ActivityAddFriends.this != null) {
                    progressBar.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    friendsRecyclerView.setVisibility(View.GONE);
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);
                    /*Toast.makeText(ActivityAddFriends.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();*/
                    CustomToast(getString(R.string.tryagain));
                }

            }
        });}

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
                    /*Toast.makeText(ActivityAddFriends.this, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();*/
                    CustomToast("Message is : " + apiError.getMessage());
                    ActivityMain.getInstance().RefreshUserToken(refreshToken, 5);

                }
            }

            @Override
            public void onFailure(Call<NotificationObject> call, Throwable t) {
                if (ActivityAddFriends.this != null) {
                    progressBar.setVisibility(View.GONE);
                    /*Toast.makeText(ActivityAddFriends.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();*/
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });

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

    public void refreshNotify() {
        //cartItemLayout= (RelativeLayout) menu.findItem(R.id.call_cart).getActionView();

        //  cartImage = cartItemLayout.findViewById(R.id.img);
        // cartItemNumber= cartItemLayout.findViewById(R.id.cart_num);
        GetNotificationsCount(1);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar, menu);
        Log.d("safa", "ssssss");

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
                startActivity(new Intent(ActivityAddFriends.this, ActivityMainCall.class)
                        .putExtra("fcall", "order")
                        .setFlags(new Intent().getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY));
                cartback = true;
            }
        });

        notificationItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appSharedPreferences.writeInteger("notifi_count", ApiNotificationCount);

                startActivity(new Intent(getApplicationContext(), ActivityMainCall.class).putExtra("fcall", "notify"));
                notifiback = true;
                notifiItemNumber.setVisibility(View.INVISIBLE);
            }
        });
        ///3////


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.saved) {
//            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
            CustomToast(getString(R.string.saved));
            startActivity(new Intent(getApplicationContext(), ActivityMainCall.class).putExtra("fcall", "save"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void NextPageOfUsers() {


        if (nextPage != null) {
            pageId++;
            //    progressBar.setVisibility(View.VISIBLE);
            FetchAllUsers();


        }
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

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ActivityAddFriends.this, texttoast, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
