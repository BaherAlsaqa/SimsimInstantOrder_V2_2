package net.phpsm.simsim.simsiminstantorder.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.adapter.MyLastOrdersListAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.MyOrdersAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Datum;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyOrders;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderListFragment extends Fragment {



    List<Datum> myOrdersList ;
    RecyclerView myOrderRecyclerView;
    MyOrdersAdapter adapter;
    AVLoadingIndicatorView progressBar;
    String token,refreshToken;
    int pageId=1;
    String nextPage;
    MyLastOrdersListAdapter myOrdersAdapter;
    //  MyPurchaseAdapter adapter;
    AppSharedPreferences appSharedPreferences;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;
    View v;
    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         v= inflater.inflate(R.layout.fragment_order_list, container, false);
        myOrderRecyclerView = v.findViewById(R.id.recyclerView);
        progressBar=v.findViewById(R.id.progress);
        internet= v.findViewById(R.id.no_internet);
        empityData=v.findViewById(R.id.no_data);

        swiperefresh=v.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));

        myOrdersList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        myOrderRecyclerView.setLayoutManager(layoutManager);
        myOrdersAdapter = new MyLastOrdersListAdapter(myOrdersList,getActivity());
        myOrderRecyclerView.setAdapter(myOrdersAdapter);
        appSharedPreferences = new AppSharedPreferences(getActivity());

        token = appSharedPreferences.readString("access_token");
        refreshToken=appSharedPreferences.readString("refresh_token");


        progressBar.setVisibility(View.VISIBLE);
        //  myOrdersAdapter.setIsFinish(false);

        getOrdersData();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myOrdersList.clear();
                myOrdersAdapter.deleteAllDate();
                getOrdersData();

            }
        });

        myOrderRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible +1>= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if(nextPage!=null){
                        pageId++;
                        //     progressBar.setVisibility(View.VISIBLE);
                        getOrdersData();
                    }
                    //you have reached to the bottom of your recycler view
                }
            }
        });

        return v;
    }


    private void getOrdersData() {
        if (!isNetworkConnected()&& pageId==1) {
            swiperefresh.setRefreshing(false);
            myOrderRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        } else {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MyOrders> call = apiService.getUserLatestOrders(appSharedPreferences.readString("lang"),"api/v1/mylastorders?page="+pageId,"application/json","Bearer "+token);
        call.enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                if (response.isSuccessful()) {
                    swiperefresh.setRefreshing(false);
                    empityData.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    myOrderRecyclerView.setVisibility(View.VISIBLE);
                    internet.setVisibility(View.GONE);
                 /*   if (response.body().getItems().getPurchaseData().size() > 0) {
                        for (int i = 0; i < response.body().getItems().getPurchaseData().size(); i++) {
                            myPurchaseList.add(response.body().getItems().getPurchaseData().get(i).getProvider());
                            Log.d("indx", i + "");
                        }
                    }*/
                    myOrdersList=response.body().getItems().getData();
                    if(myOrdersList.size()==0 &&pageId==1){
                        empityData.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        myOrderRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                    /*for (int i=0; i<response.body().getItems().getData().size(); i++){
                        myOrdersList = response.body().getItems().getData();
                        for (int j=0; j<response.body().getItems().getData().get(i).getOrderItems().size(); j++){
                            myOrdersList.get(i).setOrderItems(response.body().getItems().getData().get(i).getOrderItems());
                            for (int h=0; h<response.body().getItems().getData().get(i).getOrderItems().get(j).getProduct().getProductAdditionals().size(); h++){
                                myOrdersList.get(i).getOrderItems().get(j).getProduct().setProductAdditionals(response.body().getItems().getData().get(i).getOrderItems().get(j).getProduct().getProductAdditionals());
                            }
                        }
                    }*/
                    nextPage=response.body().getItems().getNextPageUrl();
                    if(nextPage==null){
                        myOrdersAdapter.setIsFinish();
                    }
                    myOrdersAdapter.addOrders(myOrdersList);
                    // myPurchaseAdapter.notifyDataSetChanged();
                    // Log.d("xxxx",myPurchaseList.size()+"");
                    progressBar.setVisibility(View.GONE);

                }
                else if (response.code()==401){
                    swiperefresh.setRefreshing(false);
                 progressBar.setVisibility(View.GONE);
                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss","ssss");

                    ActivityMain.getInstance().RefreshUserToken(refreshToken,R.id.myorders);
                }
            }
            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                swiperefresh.setRefreshing(false);
                myOrderRecyclerView.setVisibility(View.GONE);
                internet.setVisibility(View.VISIBLE);
                empityData.setVisibility(View.GONE);

            }
        });}
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }



}
