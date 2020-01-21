package net.phpsm.simsim.simsiminstantorder.models;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.fragments.NotificationFragment;
import net.phpsm.simsim.simsiminstantorder.helpers.RecyclerItemTouchHelper;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Notifications;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MSI on 2/25/2018.
 */

public class HeaderRecyclerViewSection extends StatelessSection implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{
    private static final String TAG = HeaderRecyclerViewSection.class.getSimpleName();
    private String title;
    private List<Notifications> list;
    Context context;
    NotificationFragment notificationFragment;
    Notifications notification;
    String titleValue;

    public HeaderRecyclerViewSection(Context context, String title, List<Notifications> list, NotificationFragment notificationFragment) {
        super(R.layout.item_date, R.layout.notification_style);
        this.title = title;
        this.list = list;
        this.context = context;
        this.notificationFragment = notificationFragment;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ViewItemHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewItemHolder iHolder = (ViewItemHolder) holder;

        notification = list.get(position);
        iHolder.name.setText(notification.getMessage());
        iHolder.datetime.setText(parseDateToddMMyyyy(notification.getCreatedAt()));
        Picasso.with(context).load(notification.getImage()).resize(100, 100).into(iHolder.image);
        iHolder.fromImage1.setVisibility(View.GONE);
        if (notification.getTypeId() == 6 || notification.getTypeId() == 7 || notification.getTypeId() == 8) {
            iHolder.fromImage1.setVisibility(View.VISIBLE);
            Picasso.with(context).load(notification.getImageProvider()).resize(100, 100).into(iHolder.fromImage1);

        }

        iHolder.foreground_layout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                titleValue = title;
                return false;
            }
        });
        /*iHolder.delete.setOnClickListener(null);
        iHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNotification(notification.getId());
            }
        });*/
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new DateViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        DateViewHolder hHolder = (DateViewHolder) holder;
        hHolder.txtTitle.setText(title);
    }

    public void refreshList(List<Notifications> notificationsList) {
        if (list.size() == 0) {
            list = notificationsList;

        } else {
            for (int i = 0; i < notificationsList.size(); i++) {
                list.add(notificationsList.get(i));
            }
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        /*deleteNotification(notification.getId());
        viewHolder.notify();*/

        Log.d("onSwiped", "onSwipedSection");

    }




    public class ViewItemHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView fromImage1;
        TextView name, datetime;
        RecyclerView recyclerView;
        public RelativeLayout foreground_layout;
        public LinearLayout  background_layout;

        public ViewItemHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            fromImage1 = itemView.findViewById(R.id.fromimg);
            name = itemView.findViewById(R.id.name);
            //delete = itemView.findViewById(R.id.delete);
            datetime = itemView.findViewById(R.id.datetime);
            recyclerView = itemView.findViewById(R.id.recyclerView1);
            foreground_layout = itemView.findViewById(R.id.branchelinear);
            background_layout = itemView.findViewById(R.id.background_layout);
        }
    }


    class DateViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtTitle;

        public DateViewHolder(View v) {
            super(v);
            this.txtTitle = (TextView) v.findViewById(R.id.date);

        }
    }

    public void deleteNotification(Integer id) {
        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(context);
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
                        Toast.makeText(context, "delete " + success, Toast.LENGTH_SHORT).show();
                    }
                    DeleteNotificationFromList(id);
                } else {
                    APIError apiError = ErrorUtils.parseError(response);
                    Toast.makeText(context, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);

            }
        });


    }

    public void DeleteNotificationFromList(Integer id) {

        //notificationFragment.deleteNotification(title, id);

        //notifyDataSetChanged();
    }

    public String getTitleS(){

        return title;
    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}