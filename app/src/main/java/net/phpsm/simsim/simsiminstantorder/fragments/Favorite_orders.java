package net.phpsm.simsim.simsiminstantorder.fragments;

import android.annotation.SuppressLint;
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
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.adapter.MyFavoritsOrderAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener8;
import net.phpsm.simsim.simsiminstantorder.models.FavoritsOrders;
import net.phpsm.simsim.simsiminstantorder.models.UpOrder;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.FavoritesList;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Favorite_orders extends Fragment {
    View view;
    List<FavoritsOrders> favoritsOrdersList = new ArrayList<>();
    RecyclerView mRecyclerViewOrder;
    MyFavoritsOrderAdapter adapterO;
    AVLoadingIndicatorView progress;
    AppSharedPreferences appSharedPreferences;
    String token;
    String refreshToken;
    private boolean isFragmentLoaded = false;
    Dialog dialog1;
    ArrayList<UpOrder> cartList = new ArrayList<>();
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;

    public Favorite_orders() {
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
        view = inflater.inflate(R.layout.fragment_favorite_orders, container, false);
        appSharedPreferences = new AppSharedPreferences(getContext());
        internet= view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);
        progress = view.findViewById(R.id.progress);
        mRecyclerViewOrder = view.findViewById(R.id.recyclerViewOrders);
        mRecyclerViewOrder.setLayoutManager(new LinearLayoutManager(view.getContext()));
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));


        String prvoderRefresh = appSharedPreferences.readString("Prefresh");
        if (prvoderRefresh.equals("1")) {
            Log.d("rrrrr", prvoderRefresh + "------");
        } else {
            getOrdersFav();
        }

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (prvoderRefresh.equals("1")) {
                    swiperefresh.setRefreshing(false);
                    Log.d("rrrrr", prvoderRefresh + "------");
                } else {
                    favoritsOrdersList.clear();
                    getOrdersFav();
                }
            }
        });


        return view;
    }

    private void getOrdersFav() {
        progress.setVisibility(View.VISIBLE);
        if (!isNetworkConnected()) {
            swiperefresh.setRefreshing(false);
            mRecyclerViewOrder.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progress.setVisibility(View.GONE);
        } else {


        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<FavoritesList> call = apiService.getProvidersFavorits(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token);
        call.enqueue(new Callback<FavoritesList>() {
            @Override
            public void onResponse(Call<FavoritesList> call, Response<FavoritesList> response) {

                if (response.code() == 200) {
                    swiperefresh.setRefreshing(false);
                    empityData.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    mRecyclerViewOrder.setVisibility(View.VISIBLE);
                    internet.setVisibility(View.GONE);
                    FavoritesList providers = response.body();
                    if (providers.getStatus().equals("true")) {
                        //Toast.makeText(getContext(), providers.getStatus()+"", Toast.LENGTH_SHORT).show();
                        favoritsOrdersList = providers.getItems().getOrders();
                        if(favoritsOrdersList.size()==0 ){
                            empityData.setVisibility(View.VISIBLE);
                            swiperefresh.setRefreshing(false);
                            mRecyclerViewOrder.setVisibility(View.GONE);
                            internet.setVisibility(View.GONE);
                            progress.setVisibility(View.GONE);
                        }
                        Log.d("size", favoritsOrdersList.size() + "");
                        adapterO = new MyFavoritsOrderAdapter(favoritsOrdersList, view.getContext());
                        mRecyclerViewOrder.setAdapter(adapterO);
                        appSharedPreferences.writeString("Orefresh", "");

                        adapterO.setOnClickListener(new OnItemClickListener8() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onItemClick(FavoritsOrders item) {

                                /*for (int i = 0 ; i < item.getOrder().getOrder_items().size() ; i ++){
                                    upOrderArrayList_SP.add(item.getOrder().getOrder_items().get(i));
                                }*/
                                String provider_name_cart = appSharedPreferences.readString("provider_name_cart");
                                int p_id = item.getOrder().getProviders().getId();
                                int p_id_cart = appSharedPreferences.readInteger("p_id_cart");
                                cartList = appSharedPreferences.readArray("uporder");
                                if (p_id != p_id_cart & cartList.size() > 0) {
                                    delete_cart_dialog(item, provider_name_cart, item.getOrder().getProviders().getName(), p_id);
                                } else {
                                    add_data_to_cart(item);
                                }
                            }
                        });

                        progress.setVisibility(View.GONE);
                    }
                } else if (response.code() == 401) {
                    APIError apiError = ErrorUtils.parseError(response);
                    appSharedPreferences.writeString("Orefresh", "1");

                    //  Toast.makeText(getActivity(), "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    ((ActivityMain) getContext()).RefreshUserToken(refreshToken, 1);
                    progress.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<FavoritesList> call, Throwable t) {
                try {
                    if (getActivity() != null) {
                        progress.setVisibility(View.GONE);
                        swiperefresh.setRefreshing(false);
                        mRecyclerViewOrder.setVisibility(View.GONE);
                        internet.setVisibility(View.VISIBLE);
                        empityData.setVisibility(View.GONE);

//                        Toast.makeText(getActivity(), , Toast.LENGTH_LONG).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isFragmentLoaded) {

            getOrdersFav();
            // Load your data here or do network operations here
            isFragmentLoaded = true;
        }
    }

    private void delete_cart_dialog(FavoritsOrders item1, String providerNameCart, String provider_name, int p_id) {

        dialog1 = new Dialog(getActivity());
        dialog1.show();
        dialog1.setContentView(R.layout.confirm_delete_cart_dialog);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);
        Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        TextView cart_provider = dialog1.findViewById(R.id.cart_provider);

        Log.d("providerNameCart", "providerNameCart : "+providerNameCart);
        cart_provider.setText(getResources().getString(R.string.delete_cart_details)+" "+providerNameCart);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList arrayList = new ArrayList();
                appSharedPreferences.writeArray("uporder", arrayList);
                add_data_to_cart(item1);
                appSharedPreferences.writeString("provider_name_cart", provider_name);
                appSharedPreferences.writeInteger("p_id_cart", p_id);
                dialog1.dismiss();
            }
        });
    }

    private void add_data_to_cart(FavoritsOrders item){
        ArrayList<UpOrder> upOrderArrayList_SP = new ArrayList<>();

        for (int i = 0; i < item.getOrder().getOrder_items().size(); i++) {

            try {
                upOrderArrayList_SP.add(new UpOrder(item.getOrder().getOrder_items().get(i).getProduct().getId(),
                        item.getOrder().getOrder_items().get(i).getProduct().getTitle(),
                        item.getOrder().getOrder_items().get(i).getAmount(),
                        item.getOrder().getOrder_items().get(i).getProduct().getDescription(),
                        item.getOrder().getOrder_items().get(i).getProduct().getPrice(),
                        item.getOrder().getOrder_items().get(i).getTotal_price_and_additional() + item.getOrder().getOrder_items().get(i).getTotalPriceItem(),                        item.getOrder().getOrder_items().get(i).getProduct().getDiscount(),
                        item.getOrder().getOrder_items().get(i).getProduct().getTrial(),
                        item.getOrder().getOrder_items().get(i).getProduct().getCreatedAt(),
                        item.getOrder().getOrder_items().get(i).getProduct().getUpdatedAt(),
                        item.getOrder().getOrder_items().get(i).getProduct().getDeletedAt(),
                        item.getOrder().getOrder_items().get(i).getProduct().getProviderId(),
                        item.getOrder().getOrder_items().get(i).getProduct().getImageProduct(),
                        item.getOrder().getOrder_items().get(i).getProduct().getAmount(),
                        item.getOrder().getOrder_items().get(i).getProduct().getProductCategoryId(),
                        item.getOrder().getOrder_items().get(i).getProduct().getUrlImageProduct(),
                        item.getOrder().getOrder_items().get(i).getProduct().getProductAdditionals()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.d("string", item.toString());

        appSharedPreferences.writeArray("uporder", upOrderArrayList_SP);
        appSharedPreferences.writeInteger("transaction_type_id", 0);
        appSharedPreferences.writeInteger("affiliate_customer_id", 0);
        appSharedPreferences.writeInteger("order_item_id", 0);
        appSharedPreferences.writeInteger("product_id", 0);
        startActivity(new Intent(getContext(), ActivityMainCall.class).putExtra("fcall", "order"));

//                                upOrderArrayList_SP.addAll(item.getOrder().getOrder_items());
        //  Product product =item.getOrder().getOrder_items().get(0).getProduct();

                            /*    upOrderArrayList_SP.add(new UpOrder(product.getId(),product.getTitle(),   product.getImageProduct(),product.getAmount(),product.getDescription(),
                                        product.getPrice(),product.getDiscount(),product.getTrial(),product.getCreatedAt(),product.getUpdatedAt()
                                        ,product.getDeletedAt(),product.getProviderId(),
                                        product.getImageProduct(),product.getAmount(),
                                        product.getProductCategoryId(),
                                        product.getUrlImageProduct(),null));

                                upOrderArrayList_SP.add(new UpOrder(item.getId(),item.getOrder().getOrder_items().get(0).getProduct().getTitle()
                                        ,item.getOrder().getOrder_items().get(0).getProduct().getAmount(),item.getOrder().getOrder_items().get(0).getProduct().getDescription(),
                                        item.getOrder().getOrder_items().get(0).getProduct().getPrice(),item.getOrder().getOrder_items().get(0).getProduct().getDiscount(),
                                        item.getOrder().getOrder_items().get(0).getProduct().getTitle(),item.getOrder().getOrder_items().get(0).getProduct().getCreatedAt()
                                        ,item.getOrder().getOrder_items().get(0).getProduct().getDeletedAt(),item.getOrder().getOrder_items().get(0).getProduct().getProviderId()
                                        ,item.getOrder().getOrder_items().get(0).getProduct().getImageProduct()
                                        ,item.getOrder().getOrder_items().get(0).getProduct().getAmount()
                                        ,item.getOrder().getOrder_items().get(0).getProduct().getProductCategoryId(),item.getOrder().getOrder_items().get(0).getProduct().getUrlImageProduct(),item.getOrder().getOrder_items().get,row.getProductCategoryId(),row.getUrlImageProduct(),additions));

                              */
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
