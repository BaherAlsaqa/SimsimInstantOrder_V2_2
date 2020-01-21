package net.phpsm.simsim.simsiminstantorder.fragments;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.adapter.PaginationMyFriendsRecommendedAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener3;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MyFriendRecommendedItems;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MyFriendsRecommended;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.PaginationScrollListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFriendsRecommended_Fragment extends Fragment {

    public MyFriendsRecommended_Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private View view;
    ArrayList<MyFriendsRecommended> myFriendsRecommendedList = new ArrayList<>();
    RecyclerView mRecyclerView;
    PaginationMyFriendsRecommendedAdapter adapter;
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
    Call<MyFriendRecommendedItems> call;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_friends_recommended_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        progressBar = view.findViewById(R.id.progress);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));
        internet= view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);
        appSharedPreferences = new AppSharedPreferences(getContext());

        adapter = new PaginationMyFriendsRecommendedAdapter(getContext());

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

        adapter.setOnClickListener(new OnItemClickListener3() {
            @Override
            public void onItemClick(MyFriendsRecommended item) {
                appSharedPreferences.writeInteger("f_recom_id", item.getId());
                appSharedPreferences.writeString("f_recom_c_name", item.getCustomer().getName());
                appSharedPreferences.writeString("f_recom_image", item.getProviders().getUrl_image());
                /*Fragment selectedFragment = null;
                selectedFragment = MyFriendsRecommendedDetails.newInstance();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle data = new Bundle();
                data.putParcelable("myFriendsRecommended", item);
                selectedFragment.setArguments(data);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_home, selectedFragment)
                        .addToBackStack(null).commit();

                transaction.replace(R.id.frame_layout_home, selectedFragment).addToBackStack("friends");
                MyCommunity_Fragment.fcount++;
                transaction.commit();*/
                ArrayList arrayList = new ArrayList();
                appSharedPreferences.writeArray("OrderItemMyFriendsRecommended", arrayList);

                appSharedPreferences.writeArray("OrderItemMyFriendsRecommended", item.getOrderitemArrayList());
                appSharedPreferences.writeString("customerNameRecom", item.getCustomer().getName());
                appSharedPreferences.writeString("customerTypeRecommend", item.getTypeRecommend());

                appSharedPreferences.writeString("providerURLImage", item.getProviders().getUrl_image());
                appSharedPreferences.writeString("providerName", item.getProviders().getName());

                startActivity(new Intent(getActivity(), ActivityMainCall.class).putExtra("fcall", "myfriend_recommended_details"));
            }
        });
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myFriendsRecommendedList.clear();
                currentPage=1;
                adapter.deleteAllItems();
                loadFirstPage();

            }
        });
        return view;
    }

    /*private void friendRecommended() {
        progressBar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MyFriendRecommendedItems> call = apiService.getFriendRecommended("Bearer "+token);
        call.enqueue(new Callback<MyFriendRecommendedItems>() {
            @Override
            public void onResponse(Call<MyFriendRecommendedItems> call, Response<MyFriendRecommendedItems> response) {

                if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("true")) {

                            Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();

                            myFriendsRecommendedList = response.body().getMyFriendRecommendedList().getData();

                            adapter = new MyFriendsRecommendedAdapter(myFriendsRecommendedList, view.getContext());
                            mRecyclerView.setAdapter(adapter);

                            adapter.setOnClickListener(new OnItemClickListener3() {
                                @Override
                                public void onItemClick(MyFriendsRecommended item) {
                                    Fragment selectedFragment = null;
                                    selectedFragment = MyFriendsRecommendedDetails.newInstance();
                                    android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame_layout_home, selectedFragment).addToBackStack("friends");
                                    fcount++;
                                    transaction.commit();
                                }
                            });

                            progressBar.setVisibility(View.GONE);

                        }
                }
            }

            @Override
            public void onFailure(Call<MyFriendRecommendedItems> call, Throwable t) {
                Toast.makeText(getContext(), "Error : " + getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);

            }
        });
    }*/

    private void loadFirstPage() {
        //Log.d(TAG, "loadNextPage: " + currentPage);
        progressBar.setVisibility(View.VISIBLE);
        if (!isNetworkConnected() &&currentPage==1) {
            swiperefresh.setRefreshing(false);
            mRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        } else {
        callTopRatedMoviesApi().enqueue(new Callback<MyFriendRecommendedItems>() {
            @Override
            public void onResponse(Call<MyFriendRecommendedItems> call, Response<MyFriendRecommendedItems> response) {

                try {
                    if (response.body().getStatus().equals("true")) {
                        swiperefresh.setRefreshing(false);
                        empityData.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        internet.setVisibility(View.GONE);
                        myFriendsRecommendedList = fetchResults(response);
                        if(myFriendsRecommendedList.size()==0 && currentPage==1){
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

                        progressBar.setVisibility(View.GONE);
                    }else {
                        swiperefresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MyFriendRecommendedItems> call, Throwable t) {
                t.printStackTrace();
                try {
                    if (getActivity() != null) {
                        progressBar.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        mRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.VISIBLE);
                        empityData.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // TODO: 08/11/16 handle failure
            }
        });}
    }

    private ArrayList<MyFriendsRecommended> fetchResults(Response<MyFriendRecommendedItems> response) {
        myFriendsRecommendedList = response.body().getMyFriendRecommendedList().getData();
        return  myFriendsRecommendedList;
    }

    private void loadNextPage() {

        //Toast.makeText(getContext(), "load next page", Toast.LENGTH_SHORT).show();
        //Log.d(TAG, "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new Callback<MyFriendRecommendedItems>() {
            @Override
            public void onResponse(Call<MyFriendRecommendedItems> call, Response<MyFriendRecommendedItems> response) {

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
            public void onFailure(Call<MyFriendRecommendedItems> call, Throwable t) {
                t.printStackTrace();
                try {
                    if (getActivity() != null) {
//                        Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // TODO: 08/11/16 handle failure
            }
        });
    }

    private Call<MyFriendRecommendedItems> callTopRatedMoviesApi() {
        return call = apiService.getFriendRecommended(appSharedPreferences.readString("lang"),"application/json", "Bearer "+token, currentPage);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
