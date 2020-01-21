package net.phpsm.simsim.simsiminstantorder.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by baher on 16/11/2017.
 */

public class MyLastOrdersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Datum> myOrdersList;
    Activity context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Boolean isFinish = false;
    boolean isSelected = false;
    AppSharedPreferences appSharedPreferences;

    public void deleteAllDate() {
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

    public MyLastOrdersListAdapter(List<Datum> myOrdersList1, Activity context) {
        this.myOrdersList = myOrdersList1;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mylastorderslist_style, viewGroup, false);
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
            ((ViewItemHolder) holder).orderstatus.setText(myOrders.getStatus() + "");
            ((ViewItemHolder) holder).order_id.setText(myOrders.getId()+"");
            if (myOrders.getDelivery_status().getUrlIcon() != null) {
                Picasso.with(context).load(myOrders.getDelivery_status().getUrlIcon()).transform(new ColorTransformation(Color.parseColor("#e01c4b")))
                        .resize(100, 100).into(((ViewItemHolder) holder).deleveryImg);
            }
            Picasso.with(context).load(myOrders.getProvider().getUrl_image()).resize(100, 100).into(((ViewItemHolder) holder).provider_img);
            ((ViewItemHolder) holder).provider_name.setText(myOrders.getProvider().getName());
            ((ViewItemHolder) holder).delevery_txt.setText(myOrders.getDelivery_status().getName());
            ((ViewItemHolder) holder).call.setOnClickListener(null);
               Log.d("phhhh",myOrders.getProvider().getMobile()+"");
            ((ViewItemHolder) holder).call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    View popupContent = lInflater.inflate(R.layout.call_popup, null);
                    PopupWindow  popup = new PopupWindow(popupContent,WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT,true);
                   popup.setAnimationStyle(R.style.PopupAnimation);
                    ((TextView)popupContent.findViewById(R.id.calltv)).setText("Tel: "+myOrders.getProvider().getMobile());
                    popupContent.findViewById(R.id.call_number).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+myOrders.getProvider().getMobile()));
                            Log.d("calll","calll");
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            context.startActivity(callIntent);
                            popup.dismiss();
                        }
                    });
                    popup.setTouchable(true);
                    popup.setFocusable(false);
                    popup.setOutsideTouchable(true);
                    popup.setBackgroundDrawable(new ColorDrawable());
                    popup.setTouchInterceptor(new View.OnTouchListener() {

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            // here I want to close the pw when clicking outside it but
                            // at all this is just an example of how it works and you can
                            // implement the onTouch() or the onKey() you want
                            if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                                popup.dismiss();
                                return true;
                            }
                            return false;
                        }

                    });
                    if (popup.isShowing()) {
                        popup.dismiss();
                    } else {
                        Rect location=locateView(((ViewItemHolder) holder).call_img);
                        popup.showAtLocation(popupContent, Gravity.TOP|Gravity.START, location.right, location.top);

                    }
                }
            });

            ((ViewItemHolder) holder).card_view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(Utils.canDrawOverlays(context)) {
                        //TODO iiiiiiiiiiiiiiiii startTracking iiiiiiiiiiiiiiiiiii
                        if (myOrders.getStatus().equalsIgnoreCase("PENDING")|myOrders.getStatus().equalsIgnoreCase("PREPARE")|myOrders.getStatus().equalsIgnoreCase("READY")) {
                            String orderNo = String.valueOf(myOrders.getId());
                            appSharedPreferences.writeString("statusOrder", myOrders.getStatus());
                            appSharedPreferences.writeInteger("typeId", 2);

                            appSharedPreferences.writeString("order_no", orderNo);
                            if (myOrders.getDelivery_status().getUrlIcon() != null) {
                                String delivery_status_url_icon = myOrders.getDelivery_status().getUrlIcon();
                                appSharedPreferences.writeString("delivery_status_url_icon", delivery_status_url_icon);
                            }
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
                            Toast.makeText(context, context.getString(R.string.yourOrderServed), Toast.LENGTH_SHORT).show();
                        }else if (myOrders.getStatus().equalsIgnoreCase("CANCELED")){
                            Toast.makeText(context, context.getString(R.string.yourOrderCanceled), Toast.LENGTH_SHORT).show();
                        }
                    }

                    return false;
                }
            });

            ((ViewItemHolder) holder).totalpriceTv.setText(context.getResources().getString(R.string.total_price) + myOrders.getTotalPrice() + " "+context.getResources().getString(R.string.s_r));


            ((ViewItemHolder) holder).orderView.setOnClickListener(null);
            isSelected = false;

            ((ViewItemHolder) holder).orderView.setOnClickListener(new View.OnClickListener() {
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
        CircleImageView provider_img,deleveryImg;
        TextView order_id,provider_name,delevery_txt,orderstatus,iamHere;
       FrameLayout call;
       CardView card_view;
       ImageView call_img;
        FrameLayout details_container;
        TextView  totalpriceTv;
        LinearLayout itemsContainer;
        RelativeLayout orderView;

        public ViewItemHolder(View itemView) {
            super(itemView);
            // this.setIsRecyclable(false);
            orderView=itemView.findViewById(R.id.orderView);
            card_view=itemView.findViewById(R.id.card_view);
            provider_img = itemView.findViewById(R.id.provider_img);
            deleveryImg = itemView.findViewById(R.id.deleveryImg);
            provider_name = itemView.findViewById(R.id.provider_name);
            delevery_txt = itemView.findViewById(R.id.delevery_txt);
            orderstatus = itemView.findViewById(R.id.status);
            iamHere = itemView.findViewById(R.id.iamHere);
            call = itemView.findViewById(R.id.call);
            order_id = itemView.findViewById(R.id.order_id);
            call_img=itemView.findViewById(R.id.call_img);


            details_container = itemView.findViewById(R.id.details_container);

            totalpriceTv = itemView.findViewById(R.id.totalpriceTv);
            itemsContainer = itemView.findViewById(R.id.itemsContainer);

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


    public static Rect locateView(View v)
    {
        int[] loc_int = new int[2];
        if (v == null) return null;
        try
        {
            v.getLocationOnScreen(loc_int);
        } catch (NullPointerException npe)
        {
            //Happens when the view doesn't exist on screen anymore.
            return null;
        }
        Rect location = new Rect();
        location.left = loc_int[0];
        location.top = loc_int[1];
        location.right = location.left + v.getWidth();
        location.bottom = location.top + v.getHeight();
        return location;
    }


}
