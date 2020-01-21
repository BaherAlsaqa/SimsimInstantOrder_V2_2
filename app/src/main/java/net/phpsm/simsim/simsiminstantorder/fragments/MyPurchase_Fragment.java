package net.phpsm.simsim.simsiminstantorder.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.adapter.MyPurchaseAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyPurchaseData;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Mypurchase;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.PurchaseorderItems;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class MyPurchase_Fragment extends Fragment {
    List<PurchaseorderItems> myPurchaseList;
    View view;
    RecyclerView mRecyclerView;
    AVLoadingIndicatorView progressBar;
  //  MyPurchaseAdapter adapter;
    SharedPreferences sharedPreferencesGet;
    MyPurchaseAdapter myPurchaseAdapter;
    int PageId=1;
    String token,nextPage;
    AppSharedPreferences appSharedPreferences;
    SwipeRefreshLayout swiperefresh;
    View internet;
    View empityData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.activity_my_purchase_fragment);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          view = inflater.inflate(R.layout.activity_my_purchase_fragment, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progress);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        internet= view.findViewById(R.id.no_internet);
        empityData=view.findViewById(R.id.no_data);

        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));

        // mRecyclerView.setHasFixedSize(true);
       // mRecyclerView.setItemViewCacheSize(15);
        //mRecyclerView.setDrawingCacheEnabled(true);

        myPurchaseList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        myPurchaseAdapter = new MyPurchaseAdapter(myPurchaseList,getContext());
        mRecyclerView.setAdapter(myPurchaseAdapter);
        appSharedPreferences = new AppSharedPreferences(getActivity());
         token = appSharedPreferences.readString("access_token");
         progressBar.setVisibility(View.VISIBLE);
        getPurchaseData();

        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myPurchaseList.clear();
                PageId=1;
                myPurchaseAdapter.deleteAllData();
                getPurchaseData();

            }
        });
/*myPurchaseAdapter.setiClickListener(new MyPurchaseAdapter.IClickListener() {
    @Override
    public void onItemClick(int position, PurchaseorderItems myPurchaseList) {
        //Toast.makeText(getContext(), myPurchaseList.getOrderItems().size()+"", Toast.LENGTH_SHORT).show() ;

                Fragment selectedFragment = MyPurchaseDetails.newInstance();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle data = new Bundle();
        data.putParcelable("purcahsesItems",myPurchaseList);
        selectedFragment.setArguments(data);
                transaction.replace(R.id.frame_layout_home, selectedFragment).addToBackStack("purchases");


        transaction.commit();
    }
});*/


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible +2>= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                  //  Toast.makeText(getContext(),"end",Toast.LENGTH_SHORT).show();
if(nextPage!=null){
    PageId++;
   // progressBar.setVisibility(View.VISIBLE);
    getPurchaseData();
}//you have reached to the bottom of your recycler view
                }
            }
        });

        return view;
    }

    private void getPurchaseData() {
        if (!isNetworkConnected() &&PageId==1) {
            swiperefresh.setRefreshing(false);
            mRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        } else {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Mypurchase> call = apiService.getUserPurchase(appSharedPreferences.readString("lang"),"api/v1/mypurchase?page="+PageId,"application/json", "Bearer "+token);
        call.enqueue(new Callback<Mypurchase>() {
            @Override
            public void onResponse(Call<Mypurchase> call, Response<Mypurchase> response) {
                if (response.isSuccessful()) {
                    empityData.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    internet.setVisibility(View.GONE);

                 /*   if (response.body().getItems().getPurchaseData().size() > 0) {
                        for (int i = 0; i < response.body().getItems().getPurchaseData().size(); i++) {
                            myPurchaseList.add(response.body().getItems().getPurchaseData().get(i).getProvider());
                            Log.d("indx", i + "");
                        }
                    }*/
                     for(int i=0;i<response.body().getItems().getPurchaseorderItems().size();i++){
                         for(int i1=0;i1<response.body().getItems().getPurchaseorderItems().get(i).getOrder_items().size();i1++){
                             Log.d("size",response.body().getItems().getPurchaseorderItems().get(i).getOrder_items().size()+"");
                             myPurchaseList.add(response.body().getItems().getPurchaseorderItems().get(i).getOrder_items().get(i1));

                         }

                     }
                    Log.d("sizelist",myPurchaseList.size()+"");

                    if(myPurchaseList.size()==0 && PageId==1){
                        empityData.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        mRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                    nextPage=response.body().getItems().getNext_page_url();
                if(nextPage==null){
                        myPurchaseAdapter.setIsFinish();
                    }
                   //myPurchaseAdapter.addEmployee(myPurchaseList);
                    myPurchaseAdapter.notifyDataSetChanged();
                    Log.d("xxxx",myPurchaseList.size()+"");
                   progressBar.setVisibility(View.GONE);

                }
                else {
                    try {
                        if (getActivity() != null) {
                            swiperefresh.setRefreshing(false);

                            APIError apiError = ErrorUtils.parseError(response);
//                            Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                            //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
//                            Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                            //new ActivityMain().CustomToast1("Error is : " + apiError.getError(), getActivity());
                            progressBar.setVisibility(View.GONE);
                            internet.setVisibility(View.GONE);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Mypurchase> call, Throwable t) {

                if (getActivity() != null) {
                    progressBar.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    if(PageId==1){
                    mRecyclerView.setVisibility(View.GONE);
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);}
//                    Toast.makeText(getActivity(), R.string.tryagain, Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });}

    }
   /*   @Override
    public void onStart() {
        super.onStart();
      Toast.makeText(getContext(), "purchases", Toast.LENGTH_SHORT).show();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(10);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        int images[] = {
                R.drawable.image_2,
                R.drawable.image_1,
                R.drawable.image_3,
                R.drawable.image_5,
                R.drawable.image_4
        };

        String names[] = {
                "Shawerma", "Pizza Hut", "KFC", "Burger King", "MacDonalds"
        };

        String times[] = {
                "Friday at 10:22 PM", "Satarday at 11:22 AM", "Friday at 12:2 PM", "Satarday at 6:22 PM", "Friday at 5:00 AM"
        };


        for (int i = 0; i < images.length; i++) {
            MyPurchase myPurchase = new MyPurchase(images[i], names[i], times[i]);
            myPurchaseList.add(myPurchase);
        }

        adapter = new MyPurchaseAdapter(myPurchaseList, view.getContext());
        mRecyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new OnItemClickListener1() {
            @Override
            public void onItemClick(MyPurchase item) {
                Fragment selectedFragment = null;
                selectedFragment = MyPurchaseDetails.newInstance();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment).addToBackStack("purchases");
                transaction.commit();
                fcount++;

            }
        });
    }*/

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}

