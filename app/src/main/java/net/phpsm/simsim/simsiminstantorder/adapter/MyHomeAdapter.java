package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener4;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by baher on 16/11/2017.
 */

public class MyHomeAdapter extends RecyclerView.Adapter<MyHomeAdapter.ViewHolder> {
    ArrayList<Providers> providersList;
    Context context;
    private OnItemClickListener4 listener1;

    public MyHomeAdapter(ArrayList<Providers> providersList, Context context) {
        this.providersList = providersList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyHomeAdapter.ViewHolder holder, int position) {
        final Providers provider = providersList.get(position);
        holder.name.setText(provider.getName());
        holder.description.setText(provider.getDescription());
        Picasso.with(context)
                .load(provider.getUrl_image())
                .placeholder(R.drawable.place_holder_provider)
                .into(holder.image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(provider, v);
            }
        };

        holder.relativeLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return providersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, catigories;
        TextView name, description;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.details);
            relativeLayout = itemView.findViewById(R.id.relative_home);
        }
    }
    public void setOnClickListener(OnItemClickListener4 listener) {
        this.listener1 = listener;
    }
}
