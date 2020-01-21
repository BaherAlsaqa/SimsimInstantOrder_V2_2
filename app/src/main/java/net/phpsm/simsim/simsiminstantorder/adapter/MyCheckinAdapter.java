package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener;
import net.phpsm.simsim.simsiminstantorder.models.Branche;
import net.phpsm.simsim.simsiminstantorder.models.responses.Providers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baher on 16/11/2017.
 */

public class MyCheckinAdapter extends RecyclerView.Adapter<MyCheckinAdapter.ViewHolder> {
    List<Branche> brancheList;
    Context context;
    private OnItemClickListener listener1;

    public MyCheckinAdapter(ArrayList<Branche> brancheList1, Context context) {
        this.brancheList = brancheList1;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.checkin_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyCheckinAdapter.ViewHolder holder, int position) {
        final Branche branche = brancheList.get(position);
        holder.name.setText(branche.getName());
        if (branche.getProviders().getProviderCategory() != null) {
            holder.category.setText(branche.getProviders().getProviderCategory().getName());
        }
        Picasso.with(context)
                .load(branche.getProviders().getUrl_image())
                .resize(200, 200)
                .placeholder(R.drawable.place_holder_provider)
                .into(holder.image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(branche);
            }
        };

        holder.relativeLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return brancheList.size();
    }

    public void addCheckinData(ArrayList<Branche> brancheArrayList) {

        for(int i=0;i<brancheArrayList.size();i++){
            brancheList.add(brancheArrayList.get(i));
        }
        brancheList = brancheArrayList;
        notifyDataSetChanged();
    }

    public void deleteAllData() {
        brancheList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, category;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            category = itemView.findViewById(R.id.category);
            relativeLayout = itemView.findViewById(R.id.linear_checkin);
        }
    }
    public void setOnClickListener(OnItemClickListener listener) {
        this.listener1 = listener;
    }

    public void setFilter(ArrayList<Branche> orders) {
        brancheList = new ArrayList<>();
        brancheList.addAll(orders);
        notifyDataSetChanged();
    }
}
