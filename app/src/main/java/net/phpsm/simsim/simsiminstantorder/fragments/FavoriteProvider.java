package net.phpsm.simsim.simsiminstantorder.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.adapter.MyBrancheAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.MyFavoritsProvAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener5;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener7;
import net.phpsm.simsim.simsiminstantorder.models.Branche;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.FavoritsProviders;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.FavoritesList;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoriteProvider extends Fragment {


    RecyclerView mRecyclerViewProv;
    MyFavoritsProvAdapter adapterP;
    List<FavoritsProviders> favoritsProvidersList = new ArrayList<>();
    AppSharedPreferences appSharedPreferences;
    String cuisine_status;

    String token;
    String refreshToken;
    ArrayList<Branche> brancheArrayList = new ArrayList<>();
    AVLoadingIndicatorView progress;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;
    View view;
    public FavoriteProvider() {
        // Required empty public constructor
    }

       @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view= inflater.inflate(R.layout.fragment_favorite_provider, container, false);
        appSharedPreferences = new AppSharedPreferences(getContext());


        mRecyclerViewProv = view.findViewById(R.id.recyclerViewProviders);
        mRecyclerViewProv = view.findViewById(R.id.recyclerViewProviders);
        progress = view.findViewById(R.id.progress);
        internet= view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);
        mRecyclerViewProv.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapterP = new MyFavoritsProvAdapter(favoritsProvidersList, view.getContext());
        mRecyclerViewProv.setAdapter(adapterP);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));


        String OrderRefresh= appSharedPreferences.readString("Orefresh");
        if (OrderRefresh.equals("1")) {
        }else {
            getProvidersFav();
            }

        adapterP.setOnClickListener(new OnItemClickListener7() {
            @Override
            public void onItemClick(FavoritsProviders item) {
                switch (item.getProvider().getBranchesList().size()){
                    case 0:
                        if (appSharedPreferences.readInteger("p_id") != item.getId()) {
                            ArrayList arrayList = new ArrayList();
                            appSharedPreferences.writeArray("uporder", arrayList);
                        }
                        appSharedPreferences.writeInteger("p_id", item.getProviderId());
                        appSharedPreferences.writeInteger("b_id", 0);
                        appSharedPreferences.writeString("cover_image", item.getProvider().getCover_image());
                        appSharedPreferences.writeString("image", item.getProvider().getUrl_image());
                        appSharedPreferences.writeString("name", item.getProvider().getName());
                        appSharedPreferences.writeString("address", item.getProvider().getAddress());
                        appSharedPreferences.writeString("provider_lat", item.getProvider().getLatitude());
                        appSharedPreferences.writeString("provider_long", item.getProvider().getLongitude());
                        appSharedPreferences.writeString("provider_mobile", item.getProvider().getMobile());

                        appSharedPreferences.writeString("branch_distance",item.getProvider().getDistance()+"");
                        ArrayList<String>  coverImgs=new ArrayList<>();
                        coverImgs.add(item.getProvider().getUrl_coverimage());
                        coverImgs.add(item.getProvider().getUrl_coverimage1());
                        coverImgs.add(item.getProvider().getUrl_coverimage2());
                       appSharedPreferences.writeArray("coverImgs",coverImgs);

                        appSharedPreferences.writeString("totalPrice", "");
                        appSharedPreferences.writeString("price", "");
                        appSharedPreferences.writeInteger("onoff", item.getProvider().getOnoff());
                        if (item.getProvider().getCusine() != null) {
                            cuisine_status = item.getProvider().getCusine().getName();
                            appSharedPreferences.writeString("cuisine", item.getProvider().getCusine().getName());
                        }else {
                            cuisine_status = "";
                            appSharedPreferences.writeString("cuisine", "");
                        }
                        appSharedPreferences.writeString("is_favorite", item.getProvider().getIs_favorite());

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
                        if (appSharedPreferences.readInteger("p_id") != item.getId()) {
                            ArrayList arrayList = new ArrayList();
                            appSharedPreferences.writeArray("uporder", arrayList);
                        }
                        appSharedPreferences.writeString("cover_image", item.getProvider().getUrl_coverimage());
                        appSharedPreferences.writeString("image", item.getProvider().getUrl_image());
                        appSharedPreferences.writeString("name", item.getProvider().getName());
                        appSharedPreferences.writeString("address", item.getProvider().getAddress());
                        appSharedPreferences.writeString("provider_lat", item.getProvider().getLatitude());
                        appSharedPreferences.writeString("provider_long", item.getProvider().getLongitude());
                        appSharedPreferences.writeString("provider_mobile", item.getProvider().getMobile());
                        appSharedPreferences.writeString("totalPrice", "");
                        appSharedPreferences.writeString("price", "");
                        appSharedPreferences.writeInteger("onoff", item.getProvider().getOnoff());
                        if (item.getProvider().getCusine() != null) {
                            cuisine_status = item.getProvider().getCusine().getName();
                            appSharedPreferences.writeString("cuisine", item.getProvider().getCusine().getName());
                        }else {
                            cuisine_status = "";
                            appSharedPreferences.writeString("cuisine", "");
                        }
                        appSharedPreferences.writeString("is_favorite", item.getProvider().getIs_favorite());
                        brancheArrayList = item.getProvider().getBranchesList();
                        int p_id = item.getProviderId();
                        Log.d("prov",p_id+"");
                        branches_dialog(p_id, item.getProvider().getUrl_coverimage(), item.getProvider().getUrl_image(), cuisine_status, item.getProvider().getIs_favorite());
                }
            }
        });


        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (OrderRefresh.equals("1")) {
                    swiperefresh.setRefreshing(false);

                }else {
                    favoritsProvidersList.clear();
                    adapterP.deleteAllItems();
                    adapterP.notifyDataSetChanged();
                    getProvidersFav();
                }
            }
        });
        return view;
    }
    private void getProvidersFav() {
        progress.setVisibility(View.VISIBLE);
        if(!isNetworkConnected()){
            swiperefresh.setRefreshing(false);
            mRecyclerViewProv.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
        }else {
            token = appSharedPreferences.readString("access_token");
            refreshToken = appSharedPreferences.readString("refresh_token");

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<FavoritesList> call = apiService.getProvidersFavorits(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token);
            call.enqueue(new Callback<FavoritesList>() {
                @Override
                public void onResponse(Call<FavoritesList> call, Response<FavoritesList> response) {

                    if (response.code() == 200) {
                        empityData.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        mRecyclerViewProv.setVisibility(View.VISIBLE);
                        internet.setVisibility(View.GONE);
                        FavoritesList providers = response.body();
                        if (providers.getStatus().equals("true")) {
                            //Toast.makeText(getContext(), providers.getStatus()+"", Toast.LENGTH_SHORT).show();
                            favoritsProvidersList = providers.getItems().getProviders();

                            if(favoritsProvidersList.size()==0){
                                empityData.setVisibility(View.VISIBLE);
                                swiperefresh.setRefreshing(false);
                                mRecyclerViewProv.setVisibility(View.GONE);
                                internet.setVisibility(View.GONE);
                                progress.setVisibility(View.GONE);
                            }
                            adapterP.addFavProv(favoritsProvidersList);
                            progress.setVisibility(View.GONE);
                            appSharedPreferences.writeString("Prefresh", "");

                        }

                    } else if (response.code() == 401) {
                        swiperefresh.setRefreshing(false);

                        appSharedPreferences.writeString("Prefresh", "1");

                        APIError apiError = ErrorUtils.parseError(response);
                        // Toast.makeText(getActivity(), "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                        ((ActivityMain) getContext()).RefreshUserToken(refreshToken, 1);
                        progress.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<FavoritesList> call, Throwable t) {
                    swiperefresh.setRefreshing(false);
                    mRecyclerViewProv.setVisibility(View.GONE);
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);
                    progress.setVisibility(View.GONE);
                    if (getActivity() != null) {
//                    Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    }
                }
            });
        }
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
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO /////////Line between items //////////
        mRecyclerView1.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity()));
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
                appSharedPreferences.writeString("provider_lat", item.getLatitude());
                appSharedPreferences.writeString("provider_long", item.getLongitude());
                appSharedPreferences.writeString("provider_mobile", item.getMobile());

                appSharedPreferences.writeInteger("transaction_type_id", 0);
                appSharedPreferences.writeInteger("affiliate_customer_id", 0);
                appSharedPreferences.writeInteger("order_item_id", 0);
                appSharedPreferences.writeInteger("product_id", 0);
                appSharedPreferences.writeInteger("checkin_arrival", 0);
                startActivity(new Intent(getContext(),ActivityMainCall.class)
                        .putExtra("fcall", "res"));
            }
        });
close.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        dialog1.dismiss();
    }
});


    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
