package net.phpsm.simsim.simsiminstantorder.fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.adapter.MyNotificationAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.helpers.RecyclerItemTouchHelper1;
import net.phpsm.simsim.simsiminstantorder.models.HeaderRecyclerViewSection;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.NotificationObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Notifications;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Product;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.SimpleDividerItemDecoration;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class NotificationFragment extends Fragment implements RecyclerItemTouchHelper1.RecyclerItemTouchHelperListener{

    private View view;
    List<Notifications> Today_notificationsList,Earlier_notificationsList,Default_notificationsList;


    RecyclerView mRecyclerView1;
    AVLoadingIndicatorView notifprogress;
    MyNotificationAdapter myNotificationAdapter;
    int notifPageId = 1;
    String nextPageNotification;
    String token,refresh_token;
    LinearLayoutManager linearLayoutManager;
    int totalItemCount;
    int lastVisible;
    AppSharedPreferences appSharedPreferences;
    SectionedRecyclerViewAdapter sectionAdapter;
    HeaderRecyclerViewSection firstSection,secondSection;
    SwipeRefreshLayout swiperefresh;

    public static NotificationFragment newInstance() {
        NotificationFragment fragment = new NotificationFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_notification_fragment,container,false);

        Today_notificationsList = new ArrayList<>();
        Earlier_notificationsList=new ArrayList<>();
        Default_notificationsList=new ArrayList<>();
        appSharedPreferences = new AppSharedPreferences(getContext());

      //  sharedPreferences_Get = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        token = appSharedPreferences.readString("access_token");
        refresh_token=appSharedPreferences.readString("refresh_token");

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView1 = view.findViewById(R.id.recyclerView1);
        notifprogress= view.findViewById(R.id.progress);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));


        notifprogress.setVisibility(View.VISIBLE);
        mRecyclerView1.setLayoutManager(linearLayoutManager);
         firstSection = new HeaderRecyclerViewSection(getActivity(),getString(R.string.today), Today_notificationsList,NotificationFragment.this);
         secondSection = new HeaderRecyclerViewSection(getActivity(),getString(R.string.previously), Earlier_notificationsList,NotificationFragment.this);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        sectionAdapter.addSection(firstSection);
        sectionAdapter.addSection(secondSection);
        mRecyclerView1.setAdapter(sectionAdapter);

        //myNotificationAdapter = new MyNotificationAdapter(sortedNotificationsList, getContext());
        //mRecyclerView1.setAdapter(myNotificationAdapter);
        GetNotifications(notifPageId);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notifPageId=1;
                nextPageNotification=null;
                Today_notificationsList.clear();
                Earlier_notificationsList.clear();
                sectionAdapter.removeAllSections();
                firstSection = new HeaderRecyclerViewSection(getActivity(),getString(R.string.today), Today_notificationsList,NotificationFragment.this);
                secondSection = new HeaderRecyclerViewSection(getActivity(),getString(R.string.previously), Earlier_notificationsList,NotificationFragment.this);
                sectionAdapter.addSection(firstSection);
                sectionAdapter.addSection(secondSection);
                mRecyclerView1.setAdapter(sectionAdapter);
                GetNotifications(notifPageId);


            }
        });

        mRecyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                boolean endHasBeenReached = lastVisible +2>= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if(nextPageNotification!=null){
                        notifPageId++;
                        // progressBar.setVisibility(View.VISIBLE);
                        GetNotifications(notifPageId);
                        Log.d("pageid", notifPageId +"");
                    }
                    //you have reached to the bottom of your recycler view
                }
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper1(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView1);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(mRecyclerView1);

        return view;
    }

    private void GetNotifications(int notifPageId1) {
        Log.d("pageidd", notifPageId1 +"");
        token = appSharedPreferences.readString("access_token");
        refresh_token=appSharedPreferences.readString("refresh_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<NotificationObject> call = apiService.getNotification(appSharedPreferences.readString("lang"),"application/json","api/v1/notifications?page="+notifPageId1,"Bearer "+token);
        call.enqueue(new Callback<NotificationObject>() {
            @Override
            public void onResponse(Call<NotificationObject> call, Response<NotificationObject> response) {
                if (response.isSuccessful()) {
               swiperefresh.setRefreshing(false);
                    Default_notificationsList=response.body().getNotificationItemsObject().getData();

                    nextPageNotification=response.body().getNotificationItemsObject().getNextPageUrl();
                   // Log.d("nextPage",nextPageNotification);
                    for (int i=0;i<Default_notificationsList.size();i++){
                        try {
                            if (DateIsNow(Default_notificationsList.get(i).getCreatedAt())){
                                Log.d("nowww","nowww");
                                Today_notificationsList.add(Default_notificationsList.get(i));
                            }else {
                                Earlier_notificationsList.add(Default_notificationsList.get(i));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }


                    if(notifPageId1==1){
                        if(Today_notificationsList.size()==0){
                            sectionAdapter.getSectionForPosition(0).setVisible(false);
                            Log.d("removed","today");
                            sectionAdapter.notifyDataSetChanged();
                            mRecyclerView1.setAdapter(sectionAdapter);

                        }
                    }

                    if(nextPageNotification==null){
                        if(Earlier_notificationsList.size()==0){
                            sectionAdapter.removeSection("Earlier");
                            sectionAdapter.notifyDataSetChanged();
                        }
                       // myNotificationAdapter.setIsFinish();
                    }


                    sectionAdapter.notifyDataSetChanged();
                    notifprogress.setVisibility(View.GONE);

                }
                else if (response.code()==401){
                    swiperefresh.setRefreshing(false);

                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
                    ((ActivityMain)getContext()).RefreshUserToken(refresh_token,0);

                    notifprogress.setVisibility(View.GONE);

                }
            }
            @Override
            public void onFailure(Call<NotificationObject> call, Throwable t) {
                swiperefresh.setRefreshing(false);

                notifprogress.setVisibility(View.GONE);
            }
        });

    }



   public boolean DateIsNow(String date_txt) throws ParseException {
        boolean equal=false;
       String date=date_txt;
       SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date d =dateFormat.parse(date);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
       String now_date = sdf.format(new Date().getTime());
       String real_date=sdf.format(d);
       Log.d("d",now_date+"//"+real_date);

       if (now_date.equals(real_date)) {
           Log.d("yessss","safa");
           equal=true;

       }else {
           Log.d("noooo","safa");

           equal=false;
       }


        return equal;
    }


    /*public void deleteNotification(String title,Integer id) {
        if(title.equals("Today")){
            for (int i=0;i<Today_notificationsList.size();i++){
                if(Today_notificationsList.get(i).getId()==id){

                    Today_notificationsList.remove(i);
                    sectionAdapter.notifyDataSetChanged();
                }
            }
        }else if(title.equals("Earlier")){
            for(int i=0;i<Earlier_notificationsList.size();i++){
                if(Earlier_notificationsList.get(i).getId()==id){
                Earlier_notificationsList.remove(i);
                Log.d("ssss",title+id);
                sectionAdapter.notifyDataSetChanged();}
            }
        }

    }*/

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

Log.d("type",sectionAdapter.getSectionForPosition(position)+"");
HeaderRecyclerViewSection section= (HeaderRecyclerViewSection) sectionAdapter.getSectionForPosition(position);
section.getTitleS();
Log.d("getTitleS", section.getTitleS());

        Log.d("onSwiped", "onSwiped");

        if(section.getTitleS().equals(getString(R.string.today))){
            Notifications notifications = Today_notificationsList.get(position-1);
            for (int i=0;i<Today_notificationsList.size();i++){
                if(Today_notificationsList.get(i).getId() == notifications.getId()){

                    deleteNotification(notifications.getId());
                    Today_notificationsList.remove(i);
                    sectionAdapter.notifyDataSetChanged();
                }
            }
        }else if(section.getTitleS().equals(getString(R.string.previously))){
            Notifications notifications = Earlier_notificationsList.get(position-(Today_notificationsList.size()+2));
            for(int i=0;i<Earlier_notificationsList.size();i++){
                if(Earlier_notificationsList.get(i).getId() == notifications.getId()){

                    deleteNotification(notifications.getId());
                    Earlier_notificationsList.remove(i);
                    sectionAdapter.notifyDataSetChanged();
                }
            }
        }

    }

    public void deleteNotification(Integer id) {
        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(getActivity());
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.deleteNotification(appSharedPreferences.readString("lang"),id, "application/json", "Bearer " + token);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body().getStatus();
                    //Toast.makeText(context,"delete "+success,Toast.LENGTH_SHORT).show();

                    if (success) {
                        int allnotif=  appSharedPreferences.readInteger("notifi_count");
                        allnotif=allnotif-1;
                        appSharedPreferences.writeInteger("notifi_count",allnotif);
//                        Toast.makeText(getActivity(),  + success, Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.delete), getActivity());
                    }else{
//                        Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    }
                    //Default_notificationsList.remove(i);

                    ///////////////////////////////////////////////

                    ///////////////////////////////////////////////
                } else {
                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Error is : " + apiError.getError(), getActivity());
                    // progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);
                if (getActivity() != null) {
//                    Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });


    }
}
