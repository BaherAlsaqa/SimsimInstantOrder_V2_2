package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMyOrders;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderAditionalView;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderItemView;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Datum;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.services.TrackingService;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.Utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

/**
 * Created by baher on 16/11/2017.
 */

public class MyOrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Datum> myOrdersList;
    Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Boolean isFinish = false;
    boolean isSelected = false;
    AppSharedPreferences appSharedPreferences;

    public void deleteAllData() {
        myOrdersList.clear();
        notifyDataSetChanged();
    }

    public interface IClickListener {
        void onItemClick(int position, Datum myOrderItems);
    }

    IClickListener iClickListener;
    int x = 0;

    @Override
    public int getItemViewType(int position) {
        if (position >= myOrdersList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public MyOrdersAdapter(List<Datum> myOrdersList1, Context context) {
        this.myOrdersList = myOrdersList1;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myorders_style, viewGroup, false);
            ViewItemHolder holderR = new ViewItemHolder(view);
            return holderR;
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_progress_footer, viewGroup, false);
            ViewFooterHolder holderF = new ViewFooterHolder(view);
            return holderF;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        appSharedPreferences = new AppSharedPreferences(context);

        if (holder instanceof ViewItemHolder) {
            final Datum myOrders = myOrdersList.get(position);
            ((ViewItemHolder) holder).datetime.setText(formateddate(myOrders.getCreatedAt()) + "");
            if (myOrders.getProvider() != null) {
                ((ViewItemHolder) holder).title.setText(myOrders.getProvider().getName() + "");
                Picasso.with(context).load(myOrders.getProvider().getUrl_image()).fit()
                        .centerCrop().into(((ViewItemHolder) holder).image);
            }
            ((ViewItemHolder) holder).orderstatus.setText(myOrders.getStatus() + "");
            ////
            ((ViewItemHolder) holder).orderstatus.setTextColor(context.getResources().getColor(R.color.bold_name));
           // ((ViewItemHolder) holder).image.setBorderColor(context.getResources().getColor(R.color.colorAccent));
            ((ViewItemHolder) holder).img_v1.setBackground(context.getResources().getDrawable(R.drawable.borders2));
            ((ViewItemHolder) holder).pin.setBackground(context.getResources().getDrawable(R.drawable.ic_push_pin));

            if (myOrders.getStatus().equals("SERVED")) {
                ((ViewItemHolder) holder).orderstatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                //((ViewItemHolder) holder).image.setBorderColor(context.getResources().getColor(R.color.colorPrimary));
                ((ViewItemHolder) holder).img_v1.setBackground(context.getResources().getDrawable(R.drawable.borders_red));

                ((ViewItemHolder) holder).pin.setBackground(context.getResources().getDrawable(R.drawable.ic_push_pin_red));
            }
            ((ViewItemHolder) holder).totalprice.setText(myOrders.getTotalPrice()+"");
            ((ViewItemHolder) holder).checkFav.setOnCheckedChangeListener(null);
            ((ViewItemHolder) holder).checkFav.setChecked(myOrders.getIs_favorite());
            ((ViewItemHolder) holder).checkFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AddItemToFav(myOrdersList.get(position).getId(), myOrdersList.get(position).getProviderId(), "add");
                        myOrdersList.get(position).setIs_favorite(true);
                    } else {
                        AddItemToFav(myOrdersList.get(position).getId(), myOrdersList.get(position).getProviderId(), "remove");
                        myOrdersList.get(position).setIs_favorite(false);

                    }
                }
            });

            ((ViewItemHolder) holder).order_id.setText(myOrders.getId()+"");
            ((ViewItemHolder) holder).totalpriceTv.setText(context.getResources().getString(R.string.total_price) + myOrders.getTotalPrice() +" "+ context.getResources().getString(R.string.s_r));
            if (myOrders.getProvider() != null) {
                Picasso.with(context).load(myOrders.getProvider().getUrl_image()).resize(200,200)
                        .into(((ViewItemHolder) holder).providerImg);
                ((ViewItemHolder) holder).providerNameTv.setText(myOrders.getProvider().getName());
            }

            ((ViewItemHolder) holder).cardView.setOnClickListener(null);
            isSelected = false;

            ((ViewItemHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!isSelected) {
                        List<OrderItem> data = myOrders.getOrderItems();
                        if (data != null) {
                            ((ViewItemHolder) holder).itemsContainer.removeAllViews();
                            for (int i = 0; i < data.size(); i++) {
                                OrderItemView orderItemView = new OrderItemView(context, data.get(i));
                                ((ViewItemHolder) holder).itemsContainer.addView(orderItemView);
                            }
                            List<OrderAdditional> additionals = myOrders.getOrderAdditionals();
                            if (additionals != null) {
                                for (int i2 = 0; i2 < additionals.size(); i2++) {
                                    OrderAditionalView orderAditionalView = new OrderAditionalView(context, additionals.get(i2));
                                    ((ViewItemHolder) holder).itemsContainer.addView(orderAditionalView);
                                }
                            }
                        }
                        ((ViewItemHolder) holder).details_container.setVisibility(View.VISIBLE);

                        isSelected = true;

                    } else {
                        ((ViewItemHolder) holder).itemsContainer.removeAllViews();
                        ((ViewItemHolder) holder).details_container.setVisibility(View.GONE);
                        isSelected = false;

                    }

                }
            });

            ((ViewItemHolder) holder).cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(Utils.canDrawOverlays(context)) {
                        //TODO iiiiiiiiiiiiiiiii startTracking iiiiiiiiiiiiiiiiiii
                        if (myOrders.getStatus().equalsIgnoreCase("PENDING")|myOrders.getStatus().equalsIgnoreCase("PREPARE")|myOrders.getStatus().equalsIgnoreCase("READY")) {
                            String orderNo = String.valueOf(myOrders.getId());
                            appSharedPreferences.writeString("order_no", orderNo);
                            appSharedPreferences.writeString("statusOrder", myOrders.getStatus());
                            appSharedPreferences.writeInteger("typeId", 2);
                            if (appSharedPreferences.readString("startTracking").equals("1")) {
                                //context.stopService(new Intent(context, TrackingService.class));
                                if (myOrders.getBranche() != null){
                                    appSharedPreferences.writeString("name_copy", myOrders.getBranche().getName());
                                    appSharedPreferences.writeString("provider_mobile_copy", myOrders.getBranche().getMobile());
                                }else{
                                    appSharedPreferences.writeString("name_copy", myOrders.getProvider().getName());
                                    appSharedPreferences.writeString("provider_mobile_copy", myOrders.getProvider().getMobile());
                                }
                                appSharedPreferences.writeString("image_copy", myOrders.getProvider().getUrl_image());
                                appSharedPreferences.writeInteger("customer_track_time", Integer.parseInt(myOrders.getCustomerTrackTime()));

                                startTracking();
                                appSharedPreferences.writeString("startTracking", "0");
                            }else{
                                if (myOrders.getBranche() != null){
                                    appSharedPreferences.writeString("name_copy", myOrders.getBranche().getName());
                                    appSharedPreferences.writeString("provider_mobile_copy", myOrders.getBranche().getMobile());
                                }else{
                                    appSharedPreferences.writeString("name_copy", myOrders.getProvider().getName());
                                    appSharedPreferences.writeString("provider_mobile_copy", myOrders.getProvider().getMobile());
                                }
                                appSharedPreferences.writeString("image_copy", myOrders.getProvider().getUrl_image());
                                appSharedPreferences.writeInteger("customer_track_time", Integer.parseInt(myOrders.getCustomerTrackTime()));
                                startTracking();
                                appSharedPreferences.writeString("startTracking", "1");

                            }
                        }else if (myOrders.getStatus().equalsIgnoreCase("SERVED")){
                          //  Toast.makeText(context, context.getString(R.string.yourOrderServed), Toast.LENGTH_SHORT).show();
                            (ActivityMain.getInstance()).CustomToast1(context.getString(R.string.yourOrderServed),context);
                        }else if (myOrders.getStatus().equalsIgnoreCase("CANCELED")){
                          //  Toast.makeText(context, context.getString(R.string.yourOrderCanceled), Toast.LENGTH_SHORT).show();
                            (ActivityMain.getInstance()).CustomToast1(context.getString(R.string.yourOrderCanceled),context);

                        }
                    }

                    return false;
                }
            });
        } else if ((holder instanceof ViewFooterHolder)) {
            if (myOrdersList.size() > 0) {
                ((ViewFooterHolder) holder).progressBar.setVisibility(View.VISIBLE);

            }
            // ((ViewFooterHolder)holder).progressBar.setVisibility(View.VISIBLE);
            if (isFinish) {
                Log.d("isfinash", "finish");
                ((ViewFooterHolder) holder).progressBar.setVisibility(View.GONE);
            }
            // MyPurchaseData myPurchase = myPurchaseList.get(position);

        }

    }

    private void AddItemToFav(Integer id, Integer providerId, String operation) {
        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(context);
        String token = appSharedPreferences.readString("access_token");
        String refreshToken = appSharedPreferences.readString("refresh_token");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.saveOrderItemToFav(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, id, providerId, operation);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body().getStatus();
                    // Toast.makeText(context,"saved"+success,Toast.LENGTH_SHORT).show();

                    if (success) {
                        //  Toast.makeText(context,"saved",Toast.LENGTH_SHORT).show();
                    }

                } else {
                    APIError apiError = ErrorUtils.parseError(response);
                    // Toast.makeText(context, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    //   Toast.makeText(context, "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();
                    ActivityMain.getInstance().RefreshUserToken(refreshToken, R.id.myorders);
                    Toast.makeText(context, R.string.addFaverror, Toast.LENGTH_SHORT).show();

                    // progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);

            }
        });
    }

    public void setIsFinish() {
        isFinish = true;
    }

    @Override
    public int getItemCount() {
        return myOrdersList.size() + 1;
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder /*implements View.OnCreateContextMenuListener*/ {
        ImageView image;
        TextView title, datetime, orderstatus, totalprice, order_id;
        CheckBox checkFav;
        CardView cardView;
        ImageView pin;
        FrameLayout details_container;
        CircleImageView providerImg;
        TextView providerNameTv, totalpriceTv;
        LinearLayout itemsContainer;
        FrameLayout img_v1;

        public ViewItemHolder(View itemView) {
            super(itemView);
            // this.setIsRecyclable(false);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title1);
            datetime = itemView.findViewById(R.id.datetime);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            totalprice = itemView.findViewById(R.id.totalprice);
            checkFav = itemView.findViewById(R.id.addfavorit);
            cardView = itemView.findViewById(R.id.card_view);
            pin = itemView.findViewById(R.id.pin);
            details_container = itemView.findViewById(R.id.details_container);
            providerImg = itemView.findViewById(R.id.providerImg);
            providerNameTv = itemView.findViewById(R.id.providerNameTv);
            totalpriceTv = itemView.findViewById(R.id.totalpriceTv);
            itemsContainer = itemView.findViewById(R.id.itemsContainer);
            order_id = itemView.findViewById(R.id.order_id);
            img_v1=itemView.findViewById(R.id.img_v1);

            /*itemView.setOnCreateContextMenuListener(this);
            itemView.setLongClickable(true);*/


        }

        /*@Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(context.getString(R.string.tracking));
            menu.add(0, v.getId(), 0, context.getString(R.string.startTracking));//groupId, itemId, order, title
        }*/
    }


    public class ViewFooterHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ViewFooterHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar1);
            // Add your UI Components here
        }

    }

    public void addOrders(List<Datum> orders) {
        for (int i = 0; i < orders.size(); i++) {
            myOrdersList.add(orders.get(i));
        }
        notifyDataSetChanged();
    }

    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    private void startTracking(){
        context.startService(new Intent(context, TrackingService.class).putExtra(Utils.P_Track_Img, appSharedPreferences.readString("image_copy")));
        //getActivity().bindService(new Intent(getActivity(), TrackingService.class), serviceConnection, BIND_AUTO_CREATE);
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
    public String parseDateToddMMyyyy2(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = " h:mm a";
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
    public  String formateddate(String date) {
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(date);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        //DateTime twodaysago = today.minusDays(2);

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today "+parseDateToddMMyyyy2(date);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday "+parseDateToddMMyyyy2(date);
        } //else if (dateTime.toLocalDate().equals(twodaysago.toLocalDate())) {
        //  return "2 days ago ";
        //}
        else {
            return parseDateToddMMyyyy(date);
        }
    }
}
