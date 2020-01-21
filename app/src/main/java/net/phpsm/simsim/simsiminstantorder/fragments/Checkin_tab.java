package net.phpsm.simsim.simsiminstantorder.fragments;

import android.Manifest;
import android.app.AlertDialog;
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
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.GPSTracker;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.adapter.MyBrancheAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.MyCheckinAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener5;
import net.phpsm.simsim.simsiminstantorder.models.Branche;
import net.phpsm.simsim.simsiminstantorder.models.Checkin;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.ProvidersCheckin;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class Checkin_tab extends Fragment {

    private View view;
    List<Checkin> checkinList = new ArrayList<>();
    RecyclerView mRecyclerView;
    MyCheckinAdapter adapter;
    ArrayList<Branche> brancheArrayList = new ArrayList<>();

    AVLoadingIndicatorView progressBar;
    String token;
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;
    Location location;
    ArrayList<Branche> providersCheckinArrayList;
    AppSharedPreferences appSharedPreferences;
    String cuisine_status;
    ArrayList<Branche> providersData;
    GPSTracker gpsTracker;
    SwipeRefreshLayout swiperefresh;

    View internet;
    View empityData;
    public static Checkin_tab newInstance() {
        Checkin_tab fragment = new Checkin_tab();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.checkin_tab, container, false);
        providersCheckinArrayList = new ArrayList<>();

        mRecyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        appSharedPreferences = new AppSharedPreferences(getContext());
        providersData = new ArrayList<>();
        gpsTracker = new GPSTracker(getActivity());
        internet= view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);
        TextView empitytxt= empityData.findViewById(R.id.empitytxt);
        empitytxt.setText(getContext().getResources().getString(R.string.no_provider));
        token = appSharedPreferences.readString("access_token");
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new MyCheckinAdapter(providersCheckinArrayList, view.getContext());
        mRecyclerView.setAdapter(adapter);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();

                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    providersCheckinArrayList.clear();
                    adapter.deleteAllData();

                    getLocation();

                }}
        });
        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Branche item) {
                appSharedPreferences.writeInteger("p_id", item.getProviders().getId());
                appSharedPreferences.writeInteger("b_id", item.getId());
                appSharedPreferences.writeString("cover_image", item.getProviders().getUrl_coverimage());
                appSharedPreferences.writeString("image", item.getProviders().getUrl_image());
                appSharedPreferences.writeString("name", item.getName());
                appSharedPreferences.writeString("address", item.getAddress());
                appSharedPreferences.writeString("provider_lat", item.getLatitude());
                appSharedPreferences.writeString("provider_long", item.getLongitude());
                appSharedPreferences.writeString("provider_mobile", item.getMobile());
                //////

                appSharedPreferences.writeString("branch_distance",item.getDistance()+"");
                ArrayList<String>  coverImgs=new ArrayList<>();
                coverImgs.add(item.getUrl_branchcoverimage());
                coverImgs.add(item.getUrl_branchcoverimage1());
                coverImgs.add(item.getUrl_branchcoverimage2());
                appSharedPreferences.writeArray("coverImgs",coverImgs);
                /////
                appSharedPreferences.writeString("totalPrice", "");
                appSharedPreferences.writeString("price", "");
                appSharedPreferences.writeInteger("onoff", item.getProviders().getOnoff());
                if (item.getProviders().getCusine() != null) {
                    cuisine_status = item.getProviders().getCusine().getName();
                    appSharedPreferences.writeString("cuisine", item.getProviders().getCusine().getName());
                } else {
                    cuisine_status = "";
                    appSharedPreferences.writeString("cuisine", "");
                }
                appSharedPreferences.writeString("is_favorite", item.getProviders().getIs_favorite());
                appSharedPreferences.writeInteger("transaction_type_id", 0);
                appSharedPreferences.writeInteger("affiliate_customer_id", 0);
                appSharedPreferences.writeInteger("order_item_id", 0);
                appSharedPreferences.writeInteger("product_id", 0);
                appSharedPreferences.writeInteger("checkin_arrival", 1);
                startActivity(new Intent(getContext(), ActivityMainCall.class)
                        .putExtra("fcall", "res"));
            }

            // startActivity(new Intent(getContext(),ActivityMainCall.class).putExtra("fcall", "res"));
        });
        return view;
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
        Button hideBtn = dialog1.findViewById(R.id.hidebtn);
        mRecyclerView1 = dialog1.findViewById(R.id.recyclerView1);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        //Toast.makeText(getContext(), "size : "+brancheArrayList1.size(), Toast.LENGTH_SHORT).show();

        adapter1 = new MyBrancheAdapter(brancheArrayList, view.getContext());
        mRecyclerView1.setAdapter(adapter1);

        adapter1.setOnClickListener(new OnItemClickListener5() {
            @Override
            public void onItemClick(Branche item) {
                int b_id = item.getId();

                appSharedPreferences.writeInteger("b_id", b_id);
                appSharedPreferences.writeString("name", item.getName());
                appSharedPreferences.writeString("address", item.getAddress());

                startActivity(new Intent(getContext(), ActivityMainCall.class)
                        .putExtra("fcall", "res"));
            }
        });

        hideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    private void getCheckinProviders(double latitude, double longitude) {
        progressBar.setVisibility(View.VISIBLE);
        if (!isNetworkConnected()) {
            swiperefresh.setRefreshing(false);
            mRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        } else {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProvidersCheckin> call = apiService.prvidersCheckin(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, latitude, longitude);
        call.enqueue(new Callback<ProvidersCheckin>() {
            @Override
            public void onResponse(Call<ProvidersCheckin> call, Response<ProvidersCheckin> response) {

                if (response.isSuccessful()) {
                    swiperefresh.setRefreshing(false);
                    empityData.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    internet.setVisibility(View.GONE);

                    ProvidersCheckin providers = response.body();
                    if (providers.getStatus().equals("true")) {
                        Log.d("ProvidersCheckin", providers.getStatus() + "");
                        providersCheckinArrayList = providers.getProvidersArrayList();
                        if(providersCheckinArrayList.size()==0){
                            empityData.setVisibility(View.VISIBLE);
                            swiperefresh.setRefreshing(false);
                            mRecyclerView.setVisibility(View.GONE);
                            internet.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                        }


                        adapter.addCheckinData(providersCheckinArrayList);
                        fillData(providersCheckinArrayList);
                        progressBar.setVisibility(View.GONE);
                        gpsTracker.stopUsingGPS();
                    }
                } else {
                    swiperefresh.setRefreshing(false);

                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ProvidersCheckin> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swiperefresh.setRefreshing(false);
                mRecyclerView.setVisibility(View.GONE);
                internet.setVisibility(View.VISIBLE);
                empityData.setVisibility(View.GONE);
            }
        });}
    }

    private void getLocation() {

        double Latitude = gpsTracker.getLatitude();//19.452419;//31.36857963
        double Longitude = gpsTracker.getLongitude();//-115.7547;//34.3367042
                /*lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);*/
        Log.d("gpsTrackerrr", Latitude+","+Longitude);
        getCheckinProviders(Latitude, Longitude);
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.turnOnGPS))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void fillData(ArrayList<Branche> olist) {
//        arrivedData = new ArrayList<>();
        for (int i = 0; i < olist.size(); i++) {
            providersData.add(olist.get(i));
        }
        Log.e("fillData", "SIZE olist : " + olist.size());
        Log.e("fillData", "SIZE providersData : " + providersData.size());
        sharedData();
    }

    public void SearchResult(ArrayList<Branche> fList) {
        Log.e("fListSize", "" + fList.size());
        adapter.setFilter(fList);
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Branche> sharedData() {
        Log.e("fillData", "SIZE providersData FRAG : " + providersData.size());
        return providersData;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gpsTracker.stopUsingGPS();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
