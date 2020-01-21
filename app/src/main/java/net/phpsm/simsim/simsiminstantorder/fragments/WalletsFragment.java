package net.phpsm.simsim.simsiminstantorder.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.Signup1;
import net.phpsm.simsim.simsiminstantorder.adapter.MyWalletAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UserWallet;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Wallet;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class WalletsFragment extends Fragment {

    private View view;
    List<Wallet> walletList ;
    RecyclerView mRecyclerView;
    MyWalletAdapter walletsAdapter;
    SharedPreferences sharedPreferencesGet;
    TextView totalpoints;
    TextView price;
    AVLoadingIndicatorView progressBar;
    String token;
    AppSharedPreferences appSharedPreferences;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;
    private static DecimalFormat df2 = new DecimalFormat(".##");
    public static WalletsFragment newInstance() {
        WalletsFragment fragment = new WalletsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_wallets,container,false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        totalpoints=view.findViewById(R.id.totalpoints);
        progressBar=view.findViewById(R.id.progress);
        internet= view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));

        walletList = new ArrayList<>();
        walletsAdapter = new MyWalletAdapter(walletList, getContext());
        mRecyclerView.setAdapter(walletsAdapter);
        appSharedPreferences = new AppSharedPreferences(getActivity());
        token = appSharedPreferences.readString("access_token");
        price=view.findViewById(R.id.price);
        progressBar.setVisibility(View.VISIBLE);
        getWalletsData();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                walletList.clear();
                walletsAdapter.deleteAllDate();
                getWalletsData();


            }
        });
        return view;
    }


    private void getWalletsData() {
        if (!isNetworkConnected()) {
            swiperefresh.setRefreshing(false);
            mRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        } else {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UserWallet> call = apiService.getUserWallets(appSharedPreferences.readString("lang"),"application/json", "Bearer "+token);
        call.enqueue(new Callback<UserWallet>() {
            @Override
            public void onResponse(Call<UserWallet> call, Response<UserWallet> response) {
                if (response.isSuccessful()) {
                    swiperefresh.setRefreshing(false);
                    empityData.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    internet.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    walletList=response.body().getItems().getWallet();
                    if(walletList.size()==0){
                        empityData.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        mRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                    totalpoints.setText(response.body().getItems().getTotalPoints().toString());
                    String totalPrice = df2.format(response.body().getItems().getTotal_price());
                    price.setText(totalPrice+" ");
                    walletsAdapter.addWallet(walletList);
                    // myPurchaseAdapter.notifyDataSetChanged();
                }
                else {
                    swiperefresh.setRefreshing(false);

                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
//                    Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Error is : " + apiError.getError(), getActivity());
                    progressBar.setVisibility(View.GONE);

                }
            }
            @Override
            public void onFailure(Call<UserWallet> call, Throwable t) {
                if (getActivity() != null){
                    progressBar.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    mRecyclerView.setVisibility(View.GONE);
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }

            }
        });
    }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
