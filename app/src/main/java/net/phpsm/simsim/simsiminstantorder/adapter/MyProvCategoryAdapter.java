package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener6;
import net.phpsm.simsim.simsiminstantorder.models.responses.ProviderCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baher on 16/11/2017.
 */

public class MyProvCategoryAdapter extends RecyclerView.Adapter<MyProvCategoryAdapter.ViewHolder> {
    ArrayList<ProviderCategory> providerCategoryList;
    Context context;
    private OnItemClickListener6 listener1;
    int row_index;

    public MyProvCategoryAdapter(ArrayList<ProviderCategory> providerCategoryList, Context context) {
        this.providerCategoryList = providerCategoryList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_category_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyProvCategoryAdapter.ViewHolder holder, final int position) {
        final ProviderCategory providerCategory = providerCategoryList.get(position);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(providerCategory);
                row_index = position;
                notifyDataSetChanged();
            }
        };

        holder.name.setText(providerCategory.getName());

        if(row_index == position){
            Picasso.with(context)
                    .load(providerCategory.getUrl_image())
                    .transform( new ColorTransformation(Color.parseColor("#e01c4b") ) )
                    .into(holder.image);
            holder.name.setTextColor(Color.parseColor("#e01c4b"));
            holder.image.setBorderColor(Color.parseColor("#e01c4b"));
        }
        else
        {
            Picasso.with(context)
                    .load(providerCategory.getUrl_image())
                    .transform( new ColorTransformation(Color.parseColor("#B8B8B8") ) )
                    .into(holder.image);
            holder.name.setTextColor(Color.parseColor("#B8B8B8"));
            holder.image.setBorderColor(Color.parseColor("#B8B8B8"));
        }

        holder.cardView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return providerCategoryList.size();
    }

    public void deleteAllData() {
        providerCategoryList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image;
        TextView name;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
    public void setOnClickListener(OnItemClickListener6 listener) {
        this.listener1 = listener;
    }
}
