package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.R;

import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener15;
import net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by baher on 16/11/2017.
 */

public class DeliveyStatusDialogAdapter extends RecyclerView.Adapter<DeliveyStatusDialogAdapter.ViewHolder> {
    ArrayList<Delivery_Status> delivery_statusArrayList;
    Context context;
    private OnItemClickListener15 listener1;
    int row_index;

    public DeliveyStatusDialogAdapter(ArrayList<Delivery_Status> delivery_statusList1, Context context) {
        this.delivery_statusArrayList = delivery_statusList1;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delivery_status_dialog_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(DeliveyStatusDialogAdapter.ViewHolder holder, final int position) {
        Delivery_Status delivery_status = null;
        if (delivery_statusArrayList.get(position).getAvailable().equals("true")) {
            delivery_status = delivery_statusArrayList.get(position);

            if (delivery_status.getAvailable().equals("true")) {
                Picasso.with(context)
                        .load(delivery_status.getUrl_icon())
                        .transform(new ColorTransformation(Color.parseColor("#e01c4b")))
                        .into(holder.imageView);
                Log.e("delivery_status", "onBindViewHolder: " + delivery_status.getAvailable());
            }
        }

        Delivery_Status finalDelivery_status = delivery_status;
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(finalDelivery_status);
            }
        };

        holder.cardView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return delivery_statusArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.card_view);
            imageView = itemView.findViewById(R.id.delivery_image);
        }
    }
    public void setOnClickListener(OnItemClickListener15 listener) {
        this.listener1 = listener;
    }
}
