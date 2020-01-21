package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;

import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener9;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Products;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Suleiman on 19/10/16.
 */

public class PaginationRestaurantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<Products> productsArrayList;
    private Context context;

    private boolean isLoadingAdded = false;
    private OnItemClickListener9 listener1;

    public PaginationRestaurantAdapter(Context context) {
        this.context = context;
        productsArrayList = new ArrayList<>();
    }

    public ArrayList<Products> getProductsArrayList() {
        return productsArrayList;
    }

    public void setProvidersList(ArrayList<Products> productsArrayList1) {
        this.productsArrayList = productsArrayList1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.restaurant_style, parent, false);
        viewHolder = new Holder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Products products = productsArrayList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final Holder holder1 = (Holder) holder;

                holder1.name.setText(products.getTitle());
                holder1.price.setText(products.getPrice()+" "+context.getResources().getString(R.string.s_r));
                Picasso.with(context)
                        .load(products.getUrlImageProduct())
                        .placeholder(R.drawable.place_holder_product)
                        .resize(200, 200)
                        .into(holder1.image);

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener1.onItemClick(products, v);
                    }
                };

                holder1.linearLayout.setOnClickListener(listener);

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return productsArrayList == null ? 0 : productsArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == productsArrayList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Products r) {
        productsArrayList.add(r);
        notifyItemInserted(productsArrayList.size() - 1);
    }

    public void addAll(ArrayList<Products> productsArrayList) {
        for (Products result : productsArrayList) {
            add(result);
        }
    }

    public void remove(Products r) {
        int position = productsArrayList.indexOf(r);
        if (position > -1) {
            productsArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Products());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = productsArrayList.size() - 1;
        Products result = getItem(position);

        if (result != null) {
            productsArrayList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Products getItem(int position) {
        return productsArrayList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;
        LinearLayout linearLayout;

        public Holder(View itemView) {
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

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}
