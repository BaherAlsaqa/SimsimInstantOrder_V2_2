package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener19;
import net.phpsm.simsim.simsiminstantorder.models.responses.Chat;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Suleiman on 19/10/16.
 */

public class PaginationChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private static int SENDER = 0;
    private static int RECEVER = 1;

    private ArrayList<Chat> ChatList;
    private Context context;

    private boolean isLoadingAdded = false;
    private OnItemClickListener19 listener1;
    View v1;
    LayoutInflater inflater;
    Chat chat;
    ViewGroup viewGroup;

    public PaginationChatAdapter(Context context) {
        this.context = context;
        ChatList = new ArrayList<>();
    }

    public ArrayList<Chat> getChatList() {
        return ChatList;
    }

    public void setChatList(ArrayList<Chat> ChatList) {
        this.ChatList = ChatList;
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

        v1 = inflater.inflate(R.layout.chat_style, parent,false);

        viewHolder = new Holder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        chat = ChatList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final Holder holder1 = (Holder) holder;

                if (chat.getSender() == 1){
                    holder1.linearLayoutChat.setGravity(Gravity.LEFT);
                    holder1.message.setBackgroundResource(R.drawable.shapebackgroundmessage);
                    holder1.message.setTextColor(context.getResources().getColor(R.color.white));

                }else if (chat.getSender() == 2){
                    holder1.linearLayoutChat.setGravity(Gravity.RIGHT);
                    holder1.message.setBackgroundResource(R.drawable.shapebackgroundmessage1);
                    holder1.message.setTextColor(context.getResources().getColor(R.color.bold_name));
                }

                holder1.message.setText(chat.getContent());
                holder1.date.setText(formateddate(chat.getSent_at()));

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener1.onItemClick(chat, v);
                    }
                };

                break;

            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return ChatList == null ? 0 : ChatList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return (position == ChatList.size() - 1 && isLoadingAdded) ? LOADING : ITEM ;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Chat r) {
        ChatList.add(r);
        notifyItemInserted(ChatList.size() - 1);
    }

    public void addAll(ArrayList<Chat> ChatList) {
        for (Chat result : ChatList) {
            add(result);
        }
    }

    public void remove(Chat r) {
        int position = ChatList.indexOf(r);
        if (position > -1) {
            ChatList.remove(position);
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
        add(new Chat());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = ChatList.size() - 1;
        Chat result = getItem(position);

        if (result != null) {
            ChatList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Chat getItem(int position) {
        return ChatList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class Holder extends RecyclerView.ViewHolder {

        TextView message;
        LinearLayout linearLayoutChat;
        TextView date;

        public Holder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            linearLayoutChat = itemView.findViewById(R.id.linear_layout_chat);
            date=itemView.findViewById(R.id.date);
        }
    }

    public void setOnClickListener(OnItemClickListener19 listener) {
        this.listener1 = listener;
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public void setFilter(ArrayList<Chat> orders) {
        ChatList = new ArrayList<>();
        ChatList.addAll(orders);
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
