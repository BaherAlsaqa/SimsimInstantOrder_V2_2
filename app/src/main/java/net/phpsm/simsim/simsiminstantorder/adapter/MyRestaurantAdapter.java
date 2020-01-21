package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener9;
import net.phpsm.simsim.simsiminstantorder.models.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by baher on 16/11/2017.
 */

public class MyRestaurantAdapter extends RecyclerView.Adapter<MyRestaurantAdapter.ViewHolder> {
    List<Restaurant> restaurantList;
    Context context;
    private OnItemClickListener9 listener1;

    public MyRestaurantAdapter(List<Restaurant> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyRestaurantAdapter.ViewHolder holder, int position) {
        final Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.name);
        holder.price.setText(restaurant.price+" "+context.getResources().getString(R.string.s_r));
        Picasso.with(context).load(restaurant.image).into(holder.image);

        /*View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(restaurant);
            }
        };

        holder.linearLayout.setOnClickListener(listener);*/
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
    public void setOnClickListener(OnItemClickListener9 listener) {
        this.listener1 = listener;
    }
}
