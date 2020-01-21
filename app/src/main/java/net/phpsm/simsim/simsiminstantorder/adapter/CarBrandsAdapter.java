package net.phpsm.simsim.simsiminstantorder.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener16;
import net.phpsm.simsim.simsiminstantorder.models.responses.CarBrands;

import java.util.List;

/**
 * Created by baher on 16/11/2017.
 */

public class CarBrandsAdapter extends RecyclerView.Adapter<CarBrandsAdapter.ViewHolder> {
    List<CarBrands> carBrandsList;
    Context context;
    private OnItemClickListener16 listener1;

    public CarBrandsAdapter(List<CarBrands> carBrandsList, Context context) {
        this.carBrandsList = carBrandsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carbrands_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(CarBrandsAdapter.ViewHolder holder, int position) {
        final CarBrands carBrands = carBrandsList.get(position);
        Log.d("img", carBrands.getUrl_img() + "");
        RequestCreator picasso = Picasso.with(context).load(carBrands.getUrl_img());

        picasso.placeholder(R.drawable.car_brand_holder)
                //.resize(200, 200)
                .into(holder.brand_image);
        picasso.transform(new ColorTransformation(Color.parseColor("#B8B8B8")));

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                picasso.transform(new ColorTransformation(Color.parseColor("#e01c4b")));

                listener1.onItemClick(carBrands);
            }
        };

        holder.cardView.setOnClickListener(listener);

        // LayerDrawable ld = new LayerDrawable(new Drawable[]{color, image});
        // holder.colorImage.setImageDrawable(new ColorDrawable(Color.parseColor(carColors.getColor_code())));
        // holder.colorImage.setBackgroundColor(Color.parseColor(carColors.getColor_code()));

    }

    @Override
    public int getItemCount() {
        return carBrandsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView brand_image;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            brand_image = itemView.findViewById(R.id.brand_image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }

    public void setOnClickListener(OnItemClickListener16 listener) {
        this.listener1 = listener;
    }

}
