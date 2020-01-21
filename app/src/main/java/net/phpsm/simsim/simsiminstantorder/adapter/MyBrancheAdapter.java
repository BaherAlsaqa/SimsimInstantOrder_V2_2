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

import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener5;
import net.phpsm.simsim.simsiminstantorder.models.Branche;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by baher on 16/11/2017.
 */

public class MyBrancheAdapter extends RecyclerView.Adapter<MyBrancheAdapter.ViewHolder> {
    List<Branche> brancheList;
    Context context;
    private OnItemClickListener5 listener1;
    AppSharedPreferences appSharedPreferences;

    public MyBrancheAdapter(List<Branche> brancheList, Context context) {
        this.brancheList = brancheList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.branche_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }


    @Override
    public void onBindViewHolder(MyBrancheAdapter.ViewHolder holder, int position) {
        appSharedPreferences = new AppSharedPreferences(context);
        String p_image = appSharedPreferences.readString("image");
        final Branche branche = brancheList.get(position);
        holder.name.setText(branche.getName());
        holder.address.setText(branche.getAddress());
        String distance=String.format("%.2f", branche.getDistance());
        holder.distance_value.setText(distance+" km");
        holder.status.setText("");
        holder.linearLayout.setEnabled(true);
            if(branche.getOnoff()==1){
                holder.status.setText(context.getResources().getString(R.string.res_open));
                holder.status.setTextColor(context.getResources().getColor(R.color.green));
                holder.linearLayout.setEnabled(true);
            }else {
                holder.status.setText(context.getResources().getString(R.string.res_close));
                holder.status.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                holder.linearLayout.setEnabled(false);
            }



        if (!p_image.equals(""))
        Picasso.with(context)
                .load(p_image)
                .placeholder(R.drawable.place_holder_provider)
                .resize(200, 200)
                .into(holder.image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(branche);
            }
        };

        holder.linearLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return brancheList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, address;
        LinearLayout linearLayout;
        TextView distance_value,status;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            linearLayout = itemView.findViewById(R.id.branchelinear);
            distance_value=itemView.findViewById(R.id.distance);
            status=itemView.findViewById(R.id.status);
        }
    }
    public void setOnClickListener(OnItemClickListener5 listener) {
        this.listener1 = listener;
    }
}
