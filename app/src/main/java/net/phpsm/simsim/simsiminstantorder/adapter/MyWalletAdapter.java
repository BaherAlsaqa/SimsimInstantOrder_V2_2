package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Wallet;

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

public class MyWalletAdapter extends RecyclerView.Adapter<MyWalletAdapter.ViewHolder> {
    List<Wallet> walletList;
    Context context;

    public MyWalletAdapter(List<Wallet> walletList, Context context) {
        this.walletList = walletList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wallet_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyWalletAdapter.ViewHolder holder, int position) {
        final Wallet wallet = walletList.get(position);
       holder.datetime.setText(formateddate(wallet.getCreated_at()));
        holder.pointsnum.setText(wallet.getPointNumber().toString());
       // holder.title.setText(wallet.getTitle());
        if (wallet.getProvider() != null) {
            Picasso.with(context).load(wallet.getProvider().getUrl_image()).into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public void deleteAllDate() {
        walletList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView datetime, pointsnum, title;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            datetime = itemView.findViewById(R.id.datetime);
            pointsnum = itemView.findViewById(R.id.pointsnum);
            title = itemView.findViewById(R.id.title1);
        }
    }
    public void addWallet(List<Wallet> wallets){
        for(int i=0;i<wallets.size();i++){
            walletList.add(wallets.get(i));
        }
        notifyDataSetChanged();
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
