package net.phpsm.simsim.simsiminstantorder.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.GPSTracker;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityAddFriends;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.adapter.MyBrancheAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.MyProvCategoryAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.PaginationMyHomeAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener4;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener5;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener6;
import net.phpsm.simsim.simsiminstantorder.models.Branche;
import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;
import net.phpsm.simsim.simsiminstantorder.models.Save;
import net.phpsm.simsim.simsiminstantorder.models.get_providers.Provider;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.HomeItemsObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.HomeObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.ProviderCategory;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.ProvidersCategoriesList;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.HidingScrollListener;
import net.phpsm.simsim.simsiminstantorder.utils.PaginationScrollListener;
import net.phpsm.simsim.simsiminstantorder.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class Home_tab extends Fragment {

    private View view;
    RecyclerView mRecyclerViewHorez;
    ///////////////////////////////////////////////////////
    ArrayList<ProviderCategory> providersCategoriesList = new ArrayList<>();

    RecyclerView mRecyclerView;
    //MyHomeAdapter adapter;
    SharedPreferences sharedPreferences_Get;
    AVLoadingIndicatorView progress;
    ArrayList<ProviderCategory> providersCategoriesLists = new ArrayList<>();
    ArrayList<ProviderCategory> providersCategoriesListsFill = new ArrayList<>();
    ArrayList<Providers> homeProvidersList = new ArrayList<>();
    ArrayList<Providers> homeProvidersListFill = new ArrayList<>();
    ArrayList<Branche> brancheArrayList = new ArrayList<>();
    //ArrayList<Delivery_Status> deliveryStatusArrayList = new ArrayList<>();
    RecyclerView mRecyclerView1;
    PaginationMyHomeAdapter adapter1;
    AppSharedPreferences appSharedPreferences;
    String token;
    MyProvCategoryAdapter categoryAdapter;

    private static int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 2;
    private int currentPage = PAGE_START;

    ApiService apiService;
    Call<HomeObject> call;
    int p_category_id = 1;
    LinearLayoutManager linearLayoutManager;
    Bundle bundle;
    ArrayList<Delivery_Status> deliveryStatusArrayList = new ArrayList<>();
    String cuisine_status;
    ArrayList<String> mobileCallCenter = new ArrayList<>();
    ArrayList<Providers> providersData;
    LocationManager locationManager;
    Location location;
    double latitudeG = 0.0, longitudeG = 0.0;
    private static final int REQUEST_LOCATION = 1;
    GPSTracker gpsTracker;
    AppBarLayout app_bar;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;
    public static Home_tab newInstance() {
        Home_tab fragment = new Home_tab();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_tab, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerViewHorez = view.findViewById(R.id.recyclerViewHorizontal);
        sharedPreferences_Get = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        appSharedPreferences = new AppSharedPreferences(getContext());
        progress = view.findViewById(R.id.progress);
        gpsTracker = new GPSTracker(getActivity());
        app_bar = view.findViewById(R.id.app_bar);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));
        internet= view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);
        /////////////////////////////
        isLoading = false;
        isLastPage = false;
        currentPage = PAGE_START;
        ////////////////////////////
        providersData = new ArrayList<>();

        adapter1 = new PaginationMyHomeAdapter(getContext());

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mRecyclerView1 = view.findViewById(R.id.recyclerView);
        mRecyclerView1.setLayoutManager(linearLayoutManager);

        mRecyclerViewHorez.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView1.setAdapter(adapter1);
        categoryAdapter = new MyProvCategoryAdapter(providersCategoriesList, view.getContext());
        mRecyclerViewHorez.setAdapter(categoryAdapter);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            progress.setVisibility(View.VISIBLE);

            getLocation();
        }

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    progress.setVisibility(View.GONE);

                    providersCategoriesList.clear();
                    categoryAdapter.deleteAllData();
                    categoryAdapter.notifyDataSetChanged();

                    getLocation();
                }
            }
        });

        // sharedPreferencesGet = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        // sharedPreferencesEdit = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
/*trackingFab=view.findViewById(R.id.tracking_fab);
trackingFab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        final Dialog tracking_dialog = new Dialog(getActivity());
        tracking_dialog.show();
        tracking_dialog.setCancelable(false);
        tracking_dialog.setContentView(R.layout.tracking_dialog);
        tracking_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tracking_dialog.show();
        Button close=tracking_dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tracking_dialog.dismiss();
            }
        });

    }
});*/
        token = appSharedPreferences.readString("access_token");
        ////////////////////////////
        apiService = ApiClient.getClient().create(ApiService.class);

        mRecyclerView1.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage(latitudeG, longitudeG);
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        //getHomeProviders();

        adapter1.setOnClickListener(new OnItemClickListener4() {
            @Override
            public void onItemClick(Providers item, View view) {
                mobileCallCenter = item.getMobileCallCenterArrayList();
                switch (item.getBranchesList().size()) {
                    case 0:

                        /*if (appSharedPreferences.readInteger("p_id") != item.getId()) {
                            ArrayList arrayList = new ArrayList();
                            appSharedPreferences.writeArray("uporder", arrayList);
                        }*/

                        if (mobileCallCenter != null) {
                            appSharedPreferences.writeArray("callmobiles", mobileCallCenter);
                            Log.d("getttt", mobileCallCenter.size() + "");
                        }
                        appSharedPreferences.writeInteger("p_id", item.getId());
                        appSharedPreferences.writeInteger("b_id", 0);
                        appSharedPreferences.writeString("cover_image", item.getUrl_coverimage());
                        appSharedPreferences.writeString("image", item.getUrl_image());
                        appSharedPreferences.writeString("name", item.getName());
                        appSharedPreferences.writeString("address", item.getAddress());
                        appSharedPreferences.writeString("provider_lat", item.getLatitude());
                        appSharedPreferences.writeString("provider_long", item.getLongitude());
                        appSharedPreferences.writeString("provider_mobile", item.getMobile());
                        appSharedPreferences.writeString("totalPrice", "");
                        appSharedPreferences.writeString("price", "");
                        appSharedPreferences.writeInteger("onoff", item.getOnoff());
                        ArrayList<String>  coverImgs=new ArrayList<>();
                        coverImgs.add(item.getUrl_coverimage());
                        coverImgs.add(item.getUrl_coverimage1());
                        coverImgs.add(item.getUrl_coverimage2());
                        appSharedPreferences.writeArray("coverImgs",coverImgs);


                        if (item.getCusine() != null) {
                            cuisine_status = item.getCusine().getName();
                            appSharedPreferences.writeString("cuisine", item.getCusine().getName());
                        } else {
                            cuisine_status = "";
                            appSharedPreferences.writeString("cuisine", "");
                        }
                        appSharedPreferences.writeString("is_favorite", item.getIs_favorite());
                        /////////////
                        appSharedPreferences.writeInteger("transaction_type_id", 0);
                        appSharedPreferences.writeInteger("affiliate_customer_id", 0);
                        appSharedPreferences.writeInteger("order_item_id", 0);
                        appSharedPreferences.writeInteger("product_id", 0);
                        appSharedPreferences.writeInteger("checkin_arrival", 0);
                        startActivity(new Intent(getContext(), ActivityMainCall.class)
                                .putExtra("fcall", "res"));

                        break;
                    default:
                        /*if (appSharedPreferences.readInteger("p_id") != item.getId()) {
                            ArrayList arrayList = new ArrayList();
                            appSharedPreferences.writeArray("uporder", arrayList);
                        }*/
                        appSharedPreferences.writeArray("callmobiles", mobileCallCenter);
                        if (mobileCallCenter != null) {
                            Log.d("getttt", mobileCallCenter.size() + "");
                        }
                        appSharedPreferences.writeString("cover_image", item.getUrl_coverimage());
                        appSharedPreferences.writeString("image", item.getUrl_image());
                        appSharedPreferences.writeString("name", item.getName());
                        appSharedPreferences.writeString("address", item.getAddress());
                        appSharedPreferences.writeInteger("onoff", item.getOnoff());

                        if (item.getCusine() != null) {
                            cuisine_status = item.getCusine().getName();
                            appSharedPreferences.writeString("cuisine", item.getCusine().getName());
                        } else {
                            cuisine_status = "";
                            appSharedPreferences.writeString("cuisine", "");
                        }
                        appSharedPreferences.writeString("is_favorite", item.getIs_favorite());

                        brancheArrayList = item.getBranchesList();
                        int p_id = item.getId();
                        branches_dialog(p_id, item.getUrl_coverimage(), item.getUrl_image(), cuisine_status, item.getIs_favorite());
                }
            }
        });

        return view;
    }

    private void getProvidersCategories(double latitude, double longitude) {

        if(!isNetworkConnected()){
            swiperefresh.setRefreshing(false);
            mRecyclerViewHorez.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);

        }else{
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProvidersCategoriesList> call = apiService.getProvidersCategories(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token);
        call.enqueue(new Callback<ProvidersCategoriesList>() {
            @Override
            public void onResponse(Call<ProvidersCategoriesList> call, Response<ProvidersCategoriesList> response) {

                if (response.code() == 200) {
                  swiperefresh.setRefreshing(false);
                    ProvidersCategoriesList providers = response.body();
                    if (providers.getStatus().equals("true")) {
                        providersCategoriesList = providers.getProviderCategoryArrayList();
                        mRecyclerViewHorez.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        internet.setVisibility(View.GONE);
                        empityData.setVisibility(View.GONE);
                        progress.setVisibility(View.VISIBLE);
           if(providersCategoriesList.size()==0){
               empityData.setVisibility(View.VISIBLE);
               swiperefresh.setRefreshing(false);
               mRecyclerViewHorez.setVisibility(View.GONE);
               mRecyclerView.setVisibility(View.GONE);

               internet.setVisibility(View.GONE);
               progress.setVisibility(View.GONE);
                  }
                        if (providersCategoriesList.size() <= 1) {
                            app_bar.setVisibility(View.GONE);
                        } else {
                            app_bar.setVisibility(View.VISIBLE);
                        }

                        categoryAdapter = new MyProvCategoryAdapter(providersCategoriesList, view.getContext());
                        mRecyclerViewHorez.setAdapter(categoryAdapter);



                        p_category_id = providersCategoriesList.get(0).getId();

                        loadFirstPage(latitude, longitude);
                        ////////////////////////////////////

                        categoryAdapter.setOnClickListener(new OnItemClickListener6() {
                            @Override
                            public void onItemClick(ProviderCategory item) {

                                providersData.clear();
                                isLoading = false;
                                isLastPage = false;
                                currentPage = PAGE_START;
                                p_category_id = item.getId();

                                loadFirstPage(latitude, longitude);
                                //Toast.makeText(getContext(), p_category_id+"", Toast.LENGTH_SHORT).show();

                            }
                        });
                        progress.setVisibility(View.GONE);
                    }
                } else if (response.code() == 401) {

                    swiperefresh.setRefreshing(false);
                    APIError apiError = ErrorUtils.parseError(response);
                    //  Toast.makeText(getActivity(), "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    ((ActivityMain) getContext()).RefreshUserToken(appSharedPreferences.readString("refresh_token"), 6);
                    progress.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<ProvidersCategoriesList> call, Throwable t) {
                //  Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
                try {
                  //  Log.d("ssssssssssss",t.getMessage());
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);
                    mRecyclerViewHorez.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    progress.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });}
    }

    private void branches_dialog(int p_id, String cover, String image, String cusine, String favorite) {

        appSharedPreferences.writeInteger("p_id", p_id);
        appSharedPreferences.writeString("cover_image", cover);

        appSharedPreferences.writeString("image", image);
        if (!cusine.equals(""))
            appSharedPreferences.writeString("cuisine", cusine);
        else
            appSharedPreferences.writeString("cuisine", cusine);
        appSharedPreferences.writeString("is_favorite", favorite);

        final Dialog dialog1 = new Dialog(getActivity());
        RecyclerView mRecyclerView1;
        MyBrancheAdapter adapter1;
        dialog1.show();
        dialog1.setContentView(R.layout.branches_dialog);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button close = dialog1.findViewById(R.id.close);
        mRecyclerView1 = dialog1.findViewById(R.id.recyclerView1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView1.setLayoutManager(layoutManager);
        //TODO /////////Line between items
        mRecyclerView1.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity()));
        //Toast.makeText(getContext(), "size : "+brancheArrayList1.size(), Toast.LENGTH_SHORT).show();
        dialog1.setCancelable(false);
        adapter1 = new MyBrancheAdapter(brancheArrayList, view.getContext());
        mRecyclerView1.setAdapter(adapter1);

        adapter1.setOnClickListener(new OnItemClickListener5() {
            @Override
            public void onItemClick(Branche item) {
                int b_id = item.getId();

                appSharedPreferences.writeInteger("b_id", b_id);
                appSharedPreferences.writeString("name", item.getName());
                appSharedPreferences.writeString("address", item.getAddress());
                appSharedPreferences.writeString("provider_lat", item.getLatitude());
                appSharedPreferences.writeString("provider_long", item.getLongitude());
                appSharedPreferences.writeString("provider_mobile", item.getMobile());
                appSharedPreferences.writeString("branch_distance",item.getDistance()+"");
                ArrayList<String>  coverImgs=new ArrayList<>();
                coverImgs.add(item.getUrl_branchcoverimage());
                coverImgs.add(item.getUrl_branchcoverimage1());
                coverImgs.add(item.getUrl_branchcoverimage2());
                appSharedPreferences.writeArray("coverImgs",coverImgs);
                appSharedPreferences.writeString("totalPrice", "");
                appSharedPreferences.writeString("price", "");

                appSharedPreferences.writeInteger("transaction_type_id", 0);
                appSharedPreferences.writeInteger("affiliate_customer_id", 0);
                appSharedPreferences.writeInteger("order_item_id", 0);
                appSharedPreferences.writeInteger("product_id", 0);
                appSharedPreferences.writeInteger("checkin_arrival", 0);
                startActivity(new Intent(getContext(), ActivityMainCall.class)
                        .putExtra("fcall", "res"));

                dialog1.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    private void loadFirstPage(double latitude, double longitude) {
        Log.e(getTag(), "loadFirstPage: " + currentPage);
        progress.setVisibility(View.VISIBLE);
        if(!isNetworkConnected()){
            swiperefresh.setRefreshing(false);
            mRecyclerViewHorez.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);

        }else{
        callTopRatedMoviesApi(latitude, longitude).enqueue(new Callback<HomeObject>() {
            @Override
            public void onResponse(Call<HomeObject> call, Response<HomeObject> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {
                        homeProvidersList.clear();
                        adapter1.clear();
                        homeProvidersList = response.body().getHomeItemsObject().getData();
                    /*for (int i=0; i<homeProvidersList.size(); i++) {
                        deliveryStatusArrayList = homeProvidersList.get(i).getDelivery_Status();
                    }*/
                        TOTAL_PAGES = response.body().getHomeItemsObject().getLastPage();
                        //Toast.makeText(getContext(), "total page : "+TOTAL_PAGES, Toast.LENGTH_SHORT).show();
                        homeProvidersList = fetchResults(response);

                        if(homeProvidersList.size()==0){
                            empityData.setVisibility(View.VISIBLE);
                            swiperefresh.setRefreshing(false);
                            mRecyclerViewHorez.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            internet.setVisibility(View.GONE);
                            progress.setVisibility(View.GONE);
                        }
                        Collections.sort(homeProvidersList, Providers.Comparators.distance);
                        adapter1.addAll(homeProvidersList);
                        fillData(homeProvidersList);
                        if (currentPage < TOTAL_PAGES) adapter1.addLoadingFooter();
                        else isLastPage = true;

                        progress.setVisibility(View.GONE);
                    } else {
                        try {
                            progress.setVisibility(View.GONE);
                            Log.d("firsterror", "error");
                            if (getActivity() != null) {
//                                Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        if (getActivity() != null) {
//                            Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();

                            CustomToast(getString(R.string.tryagain));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeObject> call, Throwable t) {
                t.printStackTrace();
                try {
                    if (getActivity() != null) {
//                        Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        internet.setVisibility(View.VISIBLE);
                        empityData.setVisibility(View.GONE);
                        mRecyclerViewHorez.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        progress.setVisibility(View.GONE);
                        CustomToast(getString(R.string.tryagain));
                    }
                    progress.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });}
    }

    private ArrayList<Providers> fetchResults(Response<HomeObject> response) {
        HomeObject homeObject = response.body();
        HomeItemsObject homeItemsObject = homeObject.getHomeItemsObject();
        return homeItemsObject.getData();
    }

    public void loadNextPage(double latitude, double longitude) {

        //Toast.makeText(getContext(), "load next page", Toast.LENGTH_SHORT).show();
        Log.d(getTag(), "loadNextPage: " + currentPage);

        callTopRatedMoviesApi(latitude, longitude).enqueue(new Callback<HomeObject>() {
            @Override
            public void onResponse(Call<HomeObject> call, Response<HomeObject> response) {

                if (response.body().getStatus().equals("true")) {

                    homeProvidersList = fetchResults(response);
                    Collections.sort(homeProvidersList, Providers.Comparators.distance);
                    fillData(homeProvidersList);

                    if(isLoading==false){

                    }else {
                        adapter1.removeLoadingFooter();
                        isLoading = false;
                        adapter1.addAll(homeProvidersList);
                    }

                    if (currentPage != TOTAL_PAGES) adapter1.addLoadingFooter();
                    else isLastPage = true;
                } else {
                    try {
                        Log.d("lasterror", "error");
                        if (getActivity() != null) {
//                            Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                            CustomToast(getString(R.string.tryagain));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HomeObject> call, Throwable t) {
                t.printStackTrace();
                try {
                    if (getActivity() != null) {
//                        Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.tryagain));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Call<HomeObject> callTopRatedMoviesApi(double latitude, double longitude) {
        return call = apiService.getProvidersHome(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token, p_category_id, currentPage, latitude, longitude);
    }

    public void fillData(ArrayList<Providers> olist) {
//        arrivedData = new ArrayList<>();

        for (int i = 0; i < olist.size(); i++) {
            providersData.add(olist.get(i));
        }
        Log.e("fillData", "SIZE olist : " + olist.size());
        Log.e("fillData", "SIZE providersData : " + providersData.size());
        sharedData();
    }

    public void SearchResult(ArrayList<Providers> fList) {
        Log.e("fListSize", "" + fList.size());

        //Collections.sort(homeProvidersList, Providers.Comparators.distance);
        adapter1.clear();
        adapter1.addAll(fList);
        adapter1.notifyDataSetChanged();

        /*if (fList.size() == 0) {
            currentPage += 1;
            isLoading = false;
            loadNextPage(latitudeG, longitudeG);
        } else if (fList.size() > 0) {
            adapter1.addAll(fList);
            adapter1.notifyDataSetChanged();
        }*/

    }

    public ArrayList<Providers> sharedData() {
        Log.e("fillData", "SIZE providersData FRAG : " + providersData.size());
        return providersData;
    }

    public int getTOTAL_PAGES() {
        return TOTAL_PAGES;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void loadNextSearch () {
        isLoading = false;
        currentPage += 1;
        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNextPage(latitudeG, longitudeG);
            }
        }, 1000);
    }

    private void getLocation() {

        double Latitude = gpsTracker.getLatitude();
        double Longitude = gpsTracker.getLongitude();
                /*lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);*/

        getProvidersCategories(Latitude, Longitude);

        latitudeG = Latitude;
        longitudeG = Longitude;
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.turnOnGPS))
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gpsTracker.stopUsingGPS();
    }

    public void afterSearch() {
        homeProvidersList.clear();
        adapter1.clear();
        providersData.clear();
        currentPage = PAGE_START;
        isLoading = false;
        isLastPage = false;
        loadFirstPage(latitudeG,longitudeG);
    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(getActivity(), texttoast, Toast.LENGTH_LONG);
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
        if(getActivity()!=null){
            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;}
        return false;
    }

}
