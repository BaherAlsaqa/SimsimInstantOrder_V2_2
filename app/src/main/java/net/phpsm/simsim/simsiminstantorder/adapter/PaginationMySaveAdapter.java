package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener12;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.SaveResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Suleiman on 19/10/16.
 */

public class PaginationMySaveAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<SaveResponse> saveResponseList;
    private Context context;

    private boolean isLoadingAdded = false;
    private OnItemClickListener12 listener2;
    String purchaseFrom;

    public PaginationMySaveAdapter(Context context) {
        this.context = context;
        saveResponseList = new ArrayList<>();
    }

    public List<SaveResponse> getMovies() {
        return saveResponseList;
    }

    public void setMovies(ArrayList<SaveResponse> saveResponseList) {
        this.saveResponseList = saveResponseList;
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
        View v1 = inflater.inflate(R.layout.save_style, parent, false);
        viewHolder = new Holder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final SaveResponse saveResponse = saveResponseList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final Holder holder1 = (Holder) holder;
                purchaseFrom = context.getString(R.string.purchaseFrom);
                holder1.product_name.setText(saveResponse.getOrderitem().getProducts().getTitle());
                holder1.name.setText(saveResponse.getOrderitem().getProducts().getProvider().getName());
                holder1.time.setText(formateddate(saveResponse.getCreated_at()));
                Picasso.with(context)
                        .load(saveResponse.getOrderitem().getProducts().getProvider().getUrl_image())
                        .resize(150, 150)
                        .placeholder(R.drawable.place_holder_provider)
                        .into(holder1.image);
                Picasso.with(context)
                        .load(saveResponse.getOrderitem().getProducts().getUrlImageProduct())
                        .placeholder(R.drawable.place_holder_product)
                        .resize(200,200)
                        .into(((Holder) holder).product_image);

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener2.onItemClick(saveResponse);
                    }
                };

                holder1.cardView.setOnClickListener(listener);

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return saveResponseList == null ? 0 : saveResponseList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == saveResponseList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(SaveResponse m) {
        saveResponseList.add(m);
        notifyItemInserted(saveResponseList.size() - 1);
    }

    public void addAll(ArrayList<SaveResponse> saveResponseList) {
        for (SaveResponse result : saveResponseList) {
            add(result);
        }
    }

    public void remove(SaveResponse m) {
        int position = saveResponseList.indexOf(m);
        if (position > -1) {
            saveResponseList.remove(position);
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
        add(new SaveResponse());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = saveResponseList.size() - 1;
        SaveResponse result = getItem(position);

        if (result != null) {
            saveResponseList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public SaveResponse getItem(int position) {
        return saveResponseList.get(position);
    }

    public void deleteAllDate() {
        saveResponseList.clear();
        notifyDataSetChanged();
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class Holder extends RecyclerView.ViewHolder {
        TextView name, product_name, time;
        ImageView product_image, image;
        CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            product_name = itemView.findViewById(R.id.product_name);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.image);
            product_image = itemView.findViewById(R.id.product_image);
            cardView = itemView.findViewById(R.id.card_view);

        }
    }

    public void setOnClickListener(OnItemClickListener12 listener) {
        this.listener2 = listener;
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd-MM-yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public String parseDateToddMMyyyy2(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = " h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public  String formateddate(String date) {
        Log.d("dddd",date);
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss a").withLocale(Locale.US).parseDateTime(date);
        DateTime today = new DateTime();
        DateTime yesterday = today.minusDays(1);
        //DateTime twodaysago = today.minusDays(2);

        if (dateTime.toLocalDate().equals(today.toLocalDate())) {
            return "Today "+parseDateToddMMyyyy2(date);
        } else if (dateTime.toLocalDate().equals(yesterday.toLocalDate())) {
            return "Yesterday "+parseDateToddMMyyyy2(date);
        } //else if (dateTime.toLocalDate().equals(twodaysago.toLocalDate())) {
        //  return "2 days ago ";
        //}
        else {
            return parseDateToddMMyyyy(date);
        }
    }

}
