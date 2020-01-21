package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener3;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MyFriendsRecommended;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by baher on 16/11/2017.
 */

public class MyFriendsRecommendedAdapter extends RecyclerView.Adapter<MyFriendsRecommendedAdapter.ViewHolder> {
    List<MyFriendsRecommended> myFriendsRecommendedList;
    Context context;
    private OnItemClickListener3 listener2;

    public MyFriendsRecommendedAdapter(List<MyFriendsRecommended> myFriendsRecommendedList, Context context) {
        this.myFriendsRecommendedList = myFriendsRecommendedList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myfriendsrecommended_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyFriendsRecommendedAdapter.ViewHolder holder, int position) {
        final MyFriendsRecommended myFriendsRecommended = myFriendsRecommendedList.get(position);
        holder.name.setText(myFriendsRecommended.getCustomer().getName());
        holder.time.setText(formateddate(myFriendsRecommended.getCreatedAt()));
        holder.details.setText(myFriendsRecommended.getOrderitemArrayList().get(position).getProducts().getDescription());
        if (myFriendsRecommended.getCustomer().getImage() != null)
        Picasso.with(context).load(myFriendsRecommended.getCustomer().getImage()).into(holder.image);
        else

        Picasso.with(context).load(myFriendsRecommended.getProviders().getUrl_image()).resize(100,100).into(holder.product_image);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener2.onItemClick(myFriendsRecommended);
            }
        };

        holder.linearLayout.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return myFriendsRecommendedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, product_image;
        TextView name, details, time;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            product_image = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.name);
            details = itemView.findViewById(R.id.details);
            time = itemView.findViewById(R.id.time);
            linearLayout = itemView.findViewById(R.id.linear_checkin);
        }
    }
    public void setOnClickListener(OnItemClickListener3 listener) {
        this.listener2 = listener;
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
        DateTime dateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss a").parseDateTime(date);
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
