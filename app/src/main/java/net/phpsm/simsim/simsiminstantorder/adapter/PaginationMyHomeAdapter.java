package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
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
 * Created by Suleiman on 19/10/16.
 */

public class PaginationMyHomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<Providers> providersList;
    private Context context;

    private boolean isLoadingAdded = false;
    private OnItemClickListener4 listener1;

    public PaginationMyHomeAdapter(Context context) {
        this.context = context;
        providersList = new ArrayList<>();
    }

    public ArrayList<Providers> getProvidersList() {
        return providersList;
    }

    public void setProvidersList(ArrayList<Providers> providersList) {
        this.providersList = providersList;
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
        View v1 = inflater.inflate(R.layout.home_style, parent, false);
        viewHolder = new Holder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Providers provider = providersList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final Holder holder1 = (Holder) holder;

                holder1.name.setText(provider.getName());
                holder1.description.setText(provider.getDescription());
                Picasso.with(context)
                        .load(provider.getUrl_image())
                        .placeholder(R.drawable.place_holder_provider)
                        .resize(200, 200)
                        .into(holder1.image);

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener1.onItemClick(provider, v);
                    }
                };

                holder1.relativeLayout.setOnClickListener(listener);

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return providersList == null ? 0 : providersList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == providersList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Providers r) {
        providersList.add(r);
        notifyItemInserted(providersList.size() - 1);
    }

    public void addAll(ArrayList<Providers> providersList) {
        for (Providers result : providersList) {
            add(result);
        }
    }

    public void remove(Providers r) {
        int position = providersList.indexOf(r);
        if (position > -1) {
            providersList.remove(position);
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
        add(new Providers());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = providersList.size() - 1;
        Providers result = getItem(position);

        if (result != null) {
            providersList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Providers getItem(int position) {
        return providersList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class Holder extends RecyclerView.ViewHolder {
        ImageView image, catigories;
        TextView name, description;
        RelativeLayout relativeLayout;

        public Holder(View itemView) {
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

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public void setFilter(ArrayList<Providers> orders) {
        providersList = new ArrayList<>();
        providersList.addAll(orders);
        notifyDataSetChanged();
    }


}
