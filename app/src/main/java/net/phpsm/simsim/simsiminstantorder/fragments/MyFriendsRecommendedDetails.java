package net.phpsm.simsim.simsiminstantorder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.adapter.FriendRecommendedDetailsAdapter;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MyFriendsRecommended;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Orderitem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFriendsRecommendedDetails extends Fragment {

    TextView name;
    AppSharedPreferences appSharedPreferences;
    int f_recom_id;
    String f_recom_c_name, f_recom_image;
    ArrayList<Orderitem> orderitemArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    FriendRecommendedDetailsAdapter friendRecommendedDetailsAdapter;
    LinearLayoutManager linearLayoutManager;

    public static MyFriendsRecommendedDetails newInstance() {
        MyFriendsRecommendedDetails fragment = new MyFriendsRecommendedDetails();
        return fragment;
    }
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_friends_recommended_details, container, false);

        appSharedPreferences = new AppSharedPreferences(getContext());

        name = view.findViewById(R.id.name);

        /*f_recom_id = appSharedPreferences.readInteger("f_recom_id");
        f_recom_c_name = appSharedPreferences.readString("f_recom_c_name");
        f_recom_image = appSharedPreferences.readString("f_recom_image");*/

        recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);

        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        /*Bundle data = getArguments();
        MyFriendsRecommended myFriendsRecommended = data.getParcelable("myFriendsRecommended");*/

        orderitemArrayList = appSharedPreferences.orderitem_readArray("OrderItemMyFriendsRecommended");

        Log.d("dddd", orderitemArrayList.size()+"");

        f_recom_c_name = appSharedPreferences.readString("customerNameRecom");
        f_recom_image = appSharedPreferences.readString("providerURLImage");
        name.setText(getString(R.string.recommended_purchase)+" "+appSharedPreferences.readString("providerName"));

        friendRecommendedDetailsAdapter = new FriendRecommendedDetailsAdapter(orderitemArrayList, getContext());
        recyclerView.setAdapter(friendRecommendedDetailsAdapter);


        return view;
    }


}
