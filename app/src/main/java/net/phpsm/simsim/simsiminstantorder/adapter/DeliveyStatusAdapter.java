package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener11;
import net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by baher on 16/11/2017.
 */

public class DeliveyStatusAdapter extends RecyclerView.Adapter<DeliveyStatusAdapter.ViewHolder> {
    ArrayList<Delivery_Status> delivery_statusArrayList;
    Context context;
    private OnItemClickListener11 listener1;
    int row_index;

    public DeliveyStatusAdapter(ArrayList<Delivery_Status> delivery_statusList1, Context context) {
        this.delivery_statusArrayList = delivery_statusList1;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.delivery_status_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(DeliveyStatusAdapter.ViewHolder holder, final int position) {
        final Delivery_Status delivery_status = delivery_statusArrayList.get(position);

        Picasso.with(context)
                .load(delivery_status.getUrl_icon())
                .transform( new ColorTransformation(Color.parseColor("#B8B8B8") ) )
                .resize(50, 50)
                .into(holder.imageView);

        if(delivery_status.getAvailable().equals("true")){
            Picasso.with(context)
                    .load(delivery_status.getUrl_icon())
                    .transform( new ColorTransformation(Color.parseColor("#e01c4b") ) )
                    .resize(50, 50)
                    .into(holder.imageView);
        }
        else
        {
            Picasso.with(context)
                    .load(delivery_status.getUrl_icon())
                    .transform( new ColorTransformation(Color.parseColor("#B8B8B8") ) )
                    .resize(50, 50)
                    .into(holder.imageView);
        }
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
    public void setOnClickListener(OnItemClickListener11 listener) {
        this.listener1 = listener;
    }
}
