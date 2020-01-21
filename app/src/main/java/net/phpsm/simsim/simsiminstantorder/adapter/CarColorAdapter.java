package net.phpsm.simsim.simsiminstantorder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener13;
import net.phpsm.simsim.simsiminstantorder.models.responses.CarColors;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baher on 16/11/2017.
 */

public class CarColorAdapter extends RecyclerView.Adapter<CarColorAdapter.ViewHolder> {
    List<CarColors> carColorsList;
    Context context;
    private OnItemClickListener13 listener1;

    public CarColorAdapter(List<CarColors> carColorsList1, Context context) {
        this.carColorsList = carColorsList1;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carcolor_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }
    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(CarColorAdapter.ViewHolder holder, int position) {
        final CarColors carColors = carColorsList.get(position);

       // LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
        holder.CardViewColor.setCardBackgroundColor(Color.parseColor(carColors.getColor_code()));
        holder.colorName.setText(carColors.getColor_name());
       // holder.colorImage.setBackgroundColor(Color.parseColor(carColors.getColor_code()));

       View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            listener1.onItemClick(carColors);
        }
    };

       holder.CardViewColor.setOnClickListener(listener);
}

    @Override
    public int getItemCount() {
        return carColorsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView  CardViewColor;
        TextView colorName;

        public ViewHolder(View itemView) {
            super(itemView);
            CardViewColor = itemView.findViewById(R.id.color_image);
           // cardView = itemView.findViewById(R.id.card_view);
            colorName=itemView.findViewById(R.id.color_name);
        }
    }

    public void setOnClickListener(OnItemClickListener13 listener) {
        this.listener1 = listener;
    }
}
