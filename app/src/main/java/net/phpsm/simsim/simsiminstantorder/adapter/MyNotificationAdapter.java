package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Notifications;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

/**
 * Created by baher on 16/11/2017.
 */

public class MyNotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Notifications> notificationsList;
    Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Boolean isFinish=false;

    public MyNotificationAdapter(List<Notifications> notificationsList, Context context) {
        this.notificationsList = notificationsList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType==TYPE_ITEM) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_style, viewGroup, false);
            ViewItemHolder holderR = new ViewItemHolder(view);
        return holderR;
    }else if(viewType==TYPE_FOOTER){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_progress_footer, viewGroup, false);
        ViewFooterHolder holderF = new ViewFooterHolder(view);
        return holderF;
    }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

}
    @Override
    public int getItemViewType(int position) {
        if(position >= notificationsList.size()){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewItemHolder ) {
        final Notifications notification = notificationsList.get(position);
            ((ViewItemHolder)holder).name.setText(notification.getMessage());
            ((ViewItemHolder)holder).datetime.setText(parseDateToddMMyyyy(notification.getCreatedAt()));
        Picasso.with(context)
                .load(notification.getImage())
                .resize(200, 200)
                .into( ((ViewItemHolder)holder).image);
            ((ViewItemHolder)holder).fromImage1.setVisibility(View.GONE);
        if(notification.getTypeId()==6 || notification.getTypeId()==7 || notification.getTypeId()==8){
            ((ViewItemHolder)holder).fromImage1.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(notification.getImageProvider())
                    .resize(200, 200)
                    .into( ((ViewItemHolder)holder).fromImage1);

        }
            /*((ViewItemHolder)holder).delete.setOnClickListener(null);
            ((ViewItemHolder)holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteNotification(notification.getId());
                }
            });*/


        }else if ((holder instanceof ViewFooterHolder)){
            if(notificationsList.size()>0){
                ((ViewFooterHolder)holder).progressBar.setVisibility(View.VISIBLE);

            }                if(isFinish){
                Log.d("isfinash","finish");
                ((ViewFooterHolder)holder).progressBar.setVisibility(View.GONE);
            }
            // MyPurchaseData myPurchase = myPurchaseList.get(position);

        }

    }

    private void deleteNotification(Integer id) {
        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(context);
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.deleteNotification(appSharedPreferences.readString("lang"),id,"application/json","Bearer "+ token);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success=response.body().getStatus();
                    //Toast.makeText(context,"delete "+success,Toast.LENGTH_SHORT).show();

                    if(success){
                        Toast.makeText(context,"delete "+success,Toast.LENGTH_SHORT).show();
                    }
                    DeleteNotificationFromList(id);
                }
                else {
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
    private void DeleteNotificationFromList(Integer id) {
        for (int i=0;i<notificationsList.size();i++){
            if(notificationsList.get(i).getId()==id){
                notificationsList.remove(i);
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return notificationsList.size();
    }

    public void addNotification(List<Notifications> notificationsList2) {
        for(int i=0;i<notificationsList2.size();i++){
            notificationsList.add(notificationsList2.get(i));
        }
        notifyDataSetChanged();
    }

    public void setIsFinish() {
        isFinish=true;
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView fromImage1;
        TextView name, datetime;
        public RelativeLayout foreground_layout;
        public LinearLayout background_layout;

        public ViewItemHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            fromImage1 = itemView.findViewById(R.id.fromimg);
            name = itemView.findViewById(R.id.name);
            //delete=itemView.findViewById(R.id.delete);
            datetime = itemView.findViewById(R.id.datetime);
            foreground_layout = itemView.findViewById(R.id.branchelinear);
            background_layout = itemView.findViewById(R.id.background_layout);
        }
    }

    public class ViewFooterHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ViewFooterHolder(View v) {
            super(v);
            progressBar= v.findViewById(R.id.progressBar1);
            // Add your UI Components here
        }

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
