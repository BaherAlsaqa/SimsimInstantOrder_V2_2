package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener11;
import net.phpsm.simsim.simsiminstantorder.models.ProductCategory;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by baher on 16/11/2017.
 */

public class MyProductCategoryAdapter extends RecyclerView.Adapter<MyProductCategoryAdapter.ViewHolder> {
    List<ProductCategory> productCategoryList;
    Context context;
    private OnItemClickListener11 listener1;
    int row_index;

    public MyProductCategoryAdapter(List<ProductCategory> productCategoryList1, Context context) {
        this.productCategoryList = productCategoryList1;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_category_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyProductCategoryAdapter.ViewHolder holder, final int position) {
        final ProductCategory productCategory = productCategoryList.get(position);
        holder.name.setText(productCategory.getProduct_category_name());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(productCategory);
                row_index = position;
                notifyDataSetChanged();
            }
        };

        if(row_index == position){
            Picasso.with(context)
                    .load(productCategory.getUrl_image())
                    .transform( new ColorTransformation(Color.parseColor("#e01c4b") ) )
                    .into(holder.image);
            holder.name.setTextColor(Color.parseColor("#e01c4b"));
            //holder.image.setBorderColor(Color.parseColor("#e01c4b"));
            holder.image.setBackground(context.getResources().getDrawable(R.drawable.shapbackproductimage2));

        }
        else
        {
            Picasso.with(context)
                    .load(productCategory.getUrl_image())
                    .transform( new ColorTransformation(Color.parseColor("#B8B8B8") ) )
                    .into(holder.image);
            holder.name.setTextColor(Color.parseColor("#B8B8B8"));
          //  holder.image.setBorderColor(Color.parseColor("#B8B8B8"));
            holder.image.setBackground(context.getResources().getDrawable(R.drawable.shapbackproductimage1));
        }

        holder.cardView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return productCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        CardView cardView;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.card_view);
            linearLayout = itemView.findViewById(R.id.linear_layout);
        }
    }
    public void setOnClickListener(OnItemClickListener11 listener) {
        this.listener1 = listener;
    }
}
