package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener7;
import net.phpsm.simsim.simsiminstantorder.models.FavoritsOrders;
import net.phpsm.simsim.simsiminstantorder.models.responses.FavoritsProviders;

import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by baher on 16/11/2017.
 */

public class MyFavoritsProvAdapter extends RecyclerView.Adapter<MyFavoritsProvAdapter.ViewHolder> {
    List<FavoritsProviders> favoritsProvidersList;
    Context context;
    private OnItemClickListener7 listener1;

    public MyFavoritsProvAdapter(List<FavoritsProviders> favoritsProvidersList, Context context) {
        this.favoritsProvidersList = favoritsProvidersList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.favorit_provider_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyFavoritsProvAdapter.ViewHolder holder, int position) {
        final FavoritsProviders favoritsProviders = favoritsProvidersList.get(position);
        if(favoritsProviders.getProvider()!=null) {
            holder.name.setText(favoritsProviders.getProvider().getName());
            holder.type.setText(favoritsProviders.getProvider().getType().getName());
            holder.datetime.setText(formateddate(favoritsProviders.getCreatedAt()));
            Picasso.with(context)
                    .load(favoritsProviders.getProvider().getUrl_image())
                    .placeholder(R.drawable.place_holder_provider)
                    .resize(200, 200)
                    .into(holder.image);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(favoritsProviders);
            }
        };

        holder.relativeLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return favoritsProvidersList.size();
    }

    public void addFavProv(List<FavoritsProviders> favoritsProviders) {

        for(int i=0;i<favoritsProviders.size();i++){
            favoritsProvidersList.add(favoritsProviders.get(i));

        }

        notifyDataSetChanged();

    }

    public void deleteAllItems() {
        favoritsProvidersList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, type, datetime;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            datetime = itemView.findViewById(R.id.datetime);
            relativeLayout = itemView.findViewById(R.id.relative_fav_prov);
        }
    }
    public void setOnClickListener(OnItemClickListener7 listener) {
        this.listener1 = listener;
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
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.US).parseDateTime(date);
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
