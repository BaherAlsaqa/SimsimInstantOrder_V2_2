package net.phpsm.simsim.simsiminstantorder.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
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

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.activitys.Signup1;
import net.phpsm.simsim.simsiminstantorder.adapter.PaginationMySaveAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener12;
import net.phpsm.simsim.simsiminstantorder.models.Save;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MySavedItems;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.SaveResponse;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.PaginationScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Save_Fragment extends Fragment {

    private View view;
    ArrayList<SaveResponse> myFriendsRecommendedList = new ArrayList<>();
    RecyclerView mRecyclerView;
    PaginationMySaveAdapter adapter;
    AVLoadingIndicatorView progressBar;
    SharedPreferences sharedPreferencesGet;
    String token;
    LinearLayoutManager linearLayoutManager;
    AppSharedPreferences appSharedPreferences;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 2;
    private int currentPage = PAGE_START;

    ApiService apiService;
    Call<MySavedItems> call;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;
    ArrayList<String> mobileCallCenter;
    String cuisine_status;

    public static Save_Fragment newInstance() {
        Save_Fragment fragment = new Save_Fragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_save_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mobileCallCenter=new ArrayList<>();
        progressBar = view.findViewById(R.id.progress);
        internet=view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        appSharedPreferences = new AppSharedPreferences(getContext());
        adapter = new PaginationMySaveAdapter(getContext());
        token = appSharedPreferences.readString("access_token");

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(10);
        mRecyclerView.setDrawingCacheEnabled(true);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                //Toast.makeText(getContext(), "scrol", Toast.LENGTH_SHORT).show();
                isLoading = true;
                currentPage += 1;
                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
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
        ///////////////////
        apiService = ApiClient.getClient().create(ApiService.class);
        loadFirstPage();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage=1;
                myFriendsRecommendedList.clear();
                adapter.deleteAllDate();
                loadFirstPage();


            }
        });
        adapter.setOnClickListener(new OnItemClickListener12() {
            @Override
            public void onItemClick(SaveResponse item) {

                mobileCallCenter = item.getOrderitem().getProducts().getProvider().getMobileCallCenterArrayList();

                if (mobileCallCenter != null) {
                    appSharedPreferences.writeArray("callmobiles", mobileCallCenter);
                    Log.d("getttt", mobileCallCenter.size() + "");
                }
                appSharedPreferences.writeInteger("p_id", item.getOrderitem().getProducts().getProvider().getId());
                appSharedPreferences.writeString("cover_image", item.getOrderitem().getProducts().getProvider().getUrl_coverimage());
                appSharedPreferences.writeString("image", item.getOrderitem().getProducts().getProvider().getUrl_image());
                appSharedPreferences.writeInteger("onoff", item.getOrderitem().getProducts().getProvider().getOnoff());

                if (item.getOrderitem().getProducts().getProvider().getCusine() != null) {
                    cuisine_status = item.getOrderitem().getProducts().getProvider().getCusine().getName();
                    appSharedPreferences.writeString("cuisine", item.getOrderitem().getProducts().getProvider().getCusine().getName());
                } else {
                    cuisine_status = "";
                    appSharedPreferences.writeString("cuisine", "");
                }
                appSharedPreferences.writeString("is_favorite", item.getOrderitem().getProducts().getProvider().getIs_favorite());
               if(item.getOrderitem().getOrder().getBranch()!=null) {
                appSharedPreferences.writeInteger("b_id", item.getOrderitem().getOrder().getBranch().getId());
                appSharedPreferences.writeString("name", item.getOrderitem().getOrder().getBranch().getName());
                appSharedPreferences.writeString("address", item.getOrderitem().getOrder().getBranch().getAddress());
                appSharedPreferences.writeString("provider_lat", item.getOrderitem().getOrder().getBranch().getLatitude());
                appSharedPreferences.writeString("provider_long", item.getOrderitem().getOrder().getBranch().getLongitude());
                appSharedPreferences.writeString("provider_mobile", item.getOrderitem().getOrder().getBranch().getMobile());
                appSharedPreferences.writeString("totalPrice", "");
                appSharedPreferences.writeString("price", "");
                appSharedPreferences.writeString("branch_distance", item.getOrderitem().getOrder().getBranch().getDistance() + "");
                ArrayList<String> coverImgs = new ArrayList<>();
                coverImgs.add(item.getOrderitem().getOrder().getBranch().getUrl_branchcoverimage());
                coverImgs.add(item.getOrderitem().getOrder().getBranch().getUrl_branchcoverimage1());
                coverImgs.add(item.getOrderitem().getOrder().getBranch().getUrl_branchcoverimage2());
                appSharedPreferences.writeArray("coverImgs", coverImgs);
            }else {
                   appSharedPreferences.writeInteger("b_id", item.getOrderitem().getProducts().getProvider().getId());
                   appSharedPreferences.writeString("name", item.getOrderitem().getProducts().getProvider().getName());
                   appSharedPreferences.writeString("address", item.getOrderitem().getProducts().getProvider().getAddress());
                   appSharedPreferences.writeString("provider_lat", item.getOrderitem().getProducts().getProvider().getLatitude());
                   appSharedPreferences.writeString("provider_long", item.getOrderitem().getProducts().getProvider().getLongitude());
                   appSharedPreferences.writeString("provider_mobile", item.getOrderitem().getProducts().getProvider().getMobile());
                   appSharedPreferences.writeString("totalPrice", "");
                   appSharedPreferences.writeString("price", "");
                   appSharedPreferences.writeString("branch_distance", item.getOrderitem().getProducts().getProvider().getDistance() + "");
                   ArrayList<String> coverImgs = new ArrayList<>();
                   coverImgs.add(item.getOrderitem().getProducts().getProvider().getUrl_coverimage());
                   coverImgs.add(item.getOrderitem().getProducts().getProvider().getUrl_coverimage1());
                   coverImgs.add(item.getOrderitem().getProducts().getProvider().getUrl_coverimage2());
                   appSharedPreferences.writeArray("coverImgs", coverImgs);
               }

                appSharedPreferences.writeInteger("transaction_type_id", 10);
                appSharedPreferences.writeInteger("affiliate_customer_id", item.getAffiliate_customer_id());
                appSharedPreferences.writeInteger("order_item_id", item.getOrderitem().getId());
                appSharedPreferences.writeInteger("product_id", item.getOrderitem().getProducts().getId());
                appSharedPreferences.writeInteger("checkin_arrival", 0);
                startActivity(new Intent(getContext(), ActivityMainCall.class)
                        .putExtra("fcall", "res"));
//                Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
//                new ActivityMain().CustomToast1(item.getOrderitem().getProducts().getTitle(), getActivity());
            }
        });

        return view;
    }

    private void loadFirstPage() {
        progressBar.setVisibility(View.VISIBLE);
        if (!isNetworkConnected()) {
            swiperefresh.setRefreshing(false);
            mRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        } else {
        callTopRatedMoviesApi().enqueue(new Callback<MySavedItems>() {
            @Override
            public void onResponse(Call<MySavedItems> call, Response<MySavedItems> response) {
                swiperefresh.setRefreshing(false);
                empityData.setVisibility(View.GONE);
                swiperefresh.setRefreshing(false);
                mRecyclerView.setVisibility(View.VISIBLE);
                internet.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);

                if (response.body().getStatus().equals("true")) {
                    swiperefresh.setRefreshing(false);
                    myFriendsRecommendedList = fetchResults(response);
                    if(myFriendsRecommendedList.size()==0){
                        empityData.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        mRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                    adapter.addAll(myFriendsRecommendedList);
                    TOTAL_PAGES = response.body().getMyFriendRecommendedList().getLastPage();
                    if (currentPage < TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;

                }else {
                    swiperefresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<MySavedItems> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(getContext(), "FirstPage : "+t+" CurrentPage : "+currentPage, Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    progressBar.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    mRecyclerView.setVisibility(View.GONE);
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
                // TODO: 08/11/16 handle failure
            }
        });}
    }

    private ArrayList<SaveResponse> fetchResults(Response<MySavedItems> response) {
        myFriendsRecommendedList = response.body().getMyFriendRecommendedList().getData();
        return  myFriendsRecommendedList;
    }

    private void loadNextPage() {

        //Toast.makeText(getContext(), "load next page", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new Callback<MySavedItems>() {
            @Override
            public void onResponse(Call<MySavedItems> call, Response<MySavedItems> response) {

                if (response.body().getStatus().equals("true")) {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    myFriendsRecommendedList = fetchResults(response);
                    adapter.addAll(myFriendsRecommendedList);

                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<MySavedItems> call, Throwable t) {
                t.printStackTrace();
                if (getActivity() != null){
//                    Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
                //Toast.makeText(getContext(), "NextPage : "+t+" CurrentPage : "+currentPage, Toast.LENGTH_SHORT).show();
                // TODO: 08/11/16 handle failure
            }
        });
    }

    private Call<MySavedItems> callTopRatedMoviesApi() {
        return call = apiService.getSavedItems(appSharedPreferences.readString("lang"),"application/json","Bearer "+token, currentPage);
    }

    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(getContext());
        dialog1.show();
        dialog1.setContentView(R.layout.message_dialog);
        final TextView message = dialog1.findViewById(R.id.message);
        final Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        String char_message = null;
        if (conn == 0)
            char_message = getString(R.string.connection_message);
        else if (conn == 1)
            char_message = getString(R.string.tryagain);
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
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
