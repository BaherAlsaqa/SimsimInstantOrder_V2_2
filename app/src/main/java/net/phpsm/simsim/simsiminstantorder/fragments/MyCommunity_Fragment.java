package net.phpsm.simsim.simsiminstantorder.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.Signup1;
import net.phpsm.simsim.simsiminstantorder.adapter.MyCommunityAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyPurchaseData;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Users;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UsersDatum;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class MyCommunity_Fragment extends Fragment {
   public static int fcount = 0;
    List<UsersDatum> addCommunityList = new ArrayList<>();
    RecyclerView communityRecyclerView;
    MyCommunityAdapter communityAdapter;
    SharedPreferences sharedPreferencesGet;
    String token;
    int pageId=1;
    String nextPage;
    AVLoadingIndicatorView progressBar;
    AppSharedPreferences appSharedPreferences;
    public static MyCommunity_Fragment newInstance() {
        MyCommunity_Fragment fragment = new MyCommunity_Fragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View  view = inflater.inflate(R.layout.activity_community_fragment, container, false);

        appSharedPreferences = new AppSharedPreferences(getActivity());
        communityRecyclerView = view.findViewById(R.id.recyclerView);
        progressBar=view.findViewById(R.id.progress);

        // mRecyclerView.setHasFixedSize(true);
        // mRecyclerView.setItemViewCacheSize(10);
        //mRecyclerView.setDrawingCacheEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        communityRecyclerView.setLayoutManager(layoutManager);


        communityAdapter = new MyCommunityAdapter(addCommunityList, view.getContext());
        communityRecyclerView.setAdapter(communityAdapter);

     /*  communityAdapter.setOnClickListener(new OnItemClickListener2() {
            @Override
            public void onItemClick(Community item) {
                Fragment selectedFragment = null;
                selectedFragment = MyFriendsRecommendedDetails.newInstance();
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment).addToBackStack("community");
                transaction.commit();

                fcount++;
            }
        });*/
     communityAdapter.setiClickListener(new MyCommunityAdapter.IClickListener() {
         @Override
         public void onItemClick(int position, MyPurchaseData myPurchaseList) {

         }
     });

        token = appSharedPreferences.readString("access_token");
        progressBar.setVisibility(View.VISIBLE);
        FetchAllCommunityUsers();
        communityRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible +1>= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if(nextPage!=null){
                        pageId++;
                        progressBar.setVisibility(View.VISIBLE);
                        FetchAllCommunityUsers();
                    }
                    //you have reached to the bottom of your recycler view
                }
            }
        });

        return view;
    }

    private void FetchAllCommunityUsers() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Users> call = apiService.getAllUsers(appSharedPreferences.readString("lang"),"api/v1/closedcustomers?page="+pageId, "application/json", "Bearer "+token);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful()) {
                 /*   if (response.body().getItems().getPurchaseData().size() > 0) {
                        for (int i = 0; i < response.body().getItems().getPurchaseData().size(); i++) {
                            myPurchaseList.add(response.body().getItems().getPurchaseData().get(i).getProvider());
                            Log.d("indx", i + "");
                        }
                    }*/
                    addCommunityList=response.body().getItems().getData();
                    nextPage=response.body().getItems().getNextPageUrl();
                    communityAdapter.addFriends(addCommunityList);
                    // myPurchaseAdapter.notifyDataSetChanged();
                    // Log.d("xxxx",myPurchaseList.size()+"");
                    progressBar.setVisibility(View.GONE);

                }
                else {
                    APIError apiError = ErrorUtils.parseError(response);
                    try {
                        if (getActivity() != null) {
//                            Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                            //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
//                            Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                            //new ActivityMain().CustomToast1("Error is : " + apiError.getError(), getActivity());
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (getActivity() != null) {
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });


    }
}
