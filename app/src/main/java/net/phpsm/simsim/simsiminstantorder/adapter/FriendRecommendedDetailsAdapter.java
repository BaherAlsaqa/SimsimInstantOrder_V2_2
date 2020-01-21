package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener;
import net.phpsm.simsim.simsiminstantorder.models.Objects.mediaObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.LikeItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Orderitem;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.SaveItem1;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

/**
 * Created by baher on 16/11/2017.
 */

public class FriendRecommendedDetailsAdapter extends RecyclerView.Adapter<FriendRecommendedDetailsAdapter.ViewHolder> {
    ArrayList<Orderitem> orderitemArrayList;
    Context context;
    private OnItemClickListener listener1;
    AppSharedPreferences appSharedPreferences;

    public FriendRecommendedDetailsAdapter(ArrayList<Orderitem> orderitemArrayList1, Context context) {
        this.orderitemArrayList = orderitemArrayList1;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_friend_recomend_item_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(FriendRecommendedDetailsAdapter.ViewHolder holder, int position) {
        appSharedPreferences = new AppSharedPreferences(context);
        final Orderitem orderitem = orderitemArrayList.get(position);
        String typerecommend= appSharedPreferences.readString("customerTypeRecommend");
        switch (typerecommend){
            case "itsok":
               // holder.recommend_view.setText(context.getResources().getString(R.string.its_ok));
               // holder.recommend_view.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_normalface,0,0,0);
                holder.recommend_view.setImageResource(R.drawable.ic_normalface);

                break;
            case "recommend":
               // holder.recommend_view.setText(context.getResources().getString(R.string.recom));
               // holder.recommend_view.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_happyface3,0,0,0);
                holder.recommend_view.setImageResource(R.drawable.ic_happyface3);

                break;
        }


        Picasso.with(context).load(orderitem.getProducts().getUrlImageProduct()).into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<mediaObject> imagurl=new ArrayList<>();
                imagurl.add(new mediaObject(0,0,"",orderitem.getProducts().getUrlImageProduct())) ;
                ActivityMainCall.getInstance().showUserMedia(imagurl);
            }
        });
        holder.product_name.setText(orderitem.getProducts().getTitle());
        holder.provider_name.setText(orderitem.getProducts().getProvider().getName());
        if (orderitem.getIs_react().equals("true")) {
            holder.like.setChecked(true);
            holder.like.setTextColor(Color.parseColor("#e01c4b"));
        }
        if (orderitem.getCustomer_file_a_url() != null || orderitem.getCustomer_file_b_url() != null || orderitem.getCustomer_file_c_url() != null) {
            holder.showpic.setButtonDrawable(R.drawable.ic_pic2);
            holder.showpic.setTextColor(Color.parseColor("#e01c4b"));
        } else {
            holder.showpic.setButtonDrawable(R.drawable.ic_pic1);
            holder.showpic.setTextColor(Color.parseColor("#A7A7A7"));
        }
        if (orderitem.getIs_saved().equals("true")) {
            holder.save.setChecked(true);
            holder.save.setTextColor(Color.parseColor("#e01c4b"));
        }

        holder.like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    likeOrderItem(orderitem.getId(), "add");
                    holder.like.setTextColor(Color.parseColor("#e01c4b"));
                } else {
                    likeOrderItem(orderitem.getId(), "remove");
                    holder.like.setTextColor(Color.parseColor("#A7A7A7"));
                }
            }
        });

        holder.save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveOrderItem(orderitem.getId(), "add", orderitem.getCustomerId(), orderitem.getProducts().getId());
                    holder.save.setTextColor(Color.parseColor("#e01c4b"));
                } else {
                    saveOrderItem(orderitem.getId(), "remove", orderitem.getCustomerId(), orderitem.getProducts().getId());
                    holder.save.setTextColor(Color.parseColor("#A7A7A7"));
                }
            }
        });

        holder.showpic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<mediaObject> mediaUrls=new ArrayList<>();
                if (orderitem.getCustomer_file_a_url() != null || orderitem.getCustomer_file_b_url()!=null || orderitem.getCustomer_file_c_url()!=null) {
                    if (orderitem.getCustomer_file_a_url() != null) {
                        mediaUrls.add(new mediaObject(orderitem.getOrderId(),orderitem.getId(),"customer_file_a",orderitem.getCustomer_file_a_url()));

                    }
                    if (orderitem.getCustomer_file_b_url() != null) {
                        mediaUrls.add(new mediaObject(orderitem.getOrderId(),orderitem.getId(),"customer_file_b",orderitem.getCustomer_file_b_url()));
                    }
                    if (orderitem.getCustomer_file_c_url() != null) {
                        mediaUrls.add(new mediaObject(orderitem.getOrderId(),orderitem.getId(),"customer_file_c",orderitem.getCustomer_file_c_url()));
                    }

                    ((ActivityMainCall) context).showUserMedia(mediaUrls);
                }else
                    Toast.makeText(context, context.getString(R.string.nonpic), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderitemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView product_name, provider_name;
        CheckBox like, showpic, save;
        ImageView recommend_view;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            product_name = itemView.findViewById(R.id.product_name);
            provider_name = itemView.findViewById(R.id.provider_name);
            like = itemView.findViewById(R.id.like);
            showpic = itemView.findViewById(R.id.showpic);
            save = itemView.findViewById(R.id.save);
            recommend_view=itemView.findViewById(R.id.recommend_view);
        }
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener1 = listener;
    }

    private void likeOrderItem(int id, String operation) {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LikeItem> call = apiService.likeOrderItem( appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, id, operation);
        call.enqueue(new Callback<LikeItem>() {
            @Override
            public void onResponse(Call<LikeItem> call, Response<LikeItem> response) {
                if (response.isSuccessful()) {
                    String success = response.body().getStatus();

                    if (success.equals("true")) {
                    } else {
                       // Toast.makeText(context, context.getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(context.getString(R.string.tryagain),context);
                    }
                }
            }

            @Override
            public void onFailure(Call<LikeItem> call, Throwable t) {
                //Toast.makeText(context, context.getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                new ActivityMain().CustomToast1(context.getString(R.string.tryagain),context);

            }
        });
    }

    private void saveOrderItem(int id, String operation, int affiliate_customer_id, int product_id) {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem1> call = apiService.saveOrderItem(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, id, affiliate_customer_id, product_id, operation);
        call.enqueue(new Callback<SaveItem1>() {
            @Override
            public void onResponse(Call<SaveItem1> call, Response<SaveItem1> response) {
                if (response.isSuccessful()) {
                    String success = response.body().getStatus();
                    Toast.makeText(context, "saved" + success, Toast.LENGTH_SHORT).show();

                    if (success.equals("true")) {
                        Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, context.getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveItem1> call, Throwable t) {
                Toast.makeText(context, context.getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
