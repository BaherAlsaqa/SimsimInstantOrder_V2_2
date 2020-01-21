package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener3;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.MyFriendsRecommended;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suleiman on 19/10/16.
 */

public class PaginationMyFriendsRecommendedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private ArrayList<MyFriendsRecommended> myFriendsRecommendedList;
    private Context context;

    private boolean isLoadingAdded = false;
    private OnItemClickListener3 listener2;
    String purchaseFrom;
    AppSharedPreferences appSharedPreferences;

    public PaginationMyFriendsRecommendedAdapter(Context context) {
        this.context = context;
        myFriendsRecommendedList = new ArrayList<>();
    }

    public List<MyFriendsRecommended> getMovies() {
        return myFriendsRecommendedList;
    }

    public void setMovies(ArrayList<MyFriendsRecommended> myFriendsRecommendedList) {
        this.myFriendsRecommendedList = myFriendsRecommendedList;
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
        View v1 = inflater.inflate(R.layout.myfriendsrecommended_style, parent, false);
        viewHolder = new Holder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        appSharedPreferences = new AppSharedPreferences(context);

        final MyFriendsRecommended myFriendsRecommended = myFriendsRecommendedList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final Holder holder1 = (Holder) holder;
                purchaseFrom = context.getString(R.string.purchaseFrom);
                holder1.name.setText(myFriendsRecommended.getCustomer().getName());
                holder1.time.setText(formateddate(myFriendsRecommended.getCreatedAt()));
                if (myFriendsRecommended.getCustomer().getUrl_image() != null)
                    Picasso.with(context)
                            .load(myFriendsRecommended.getCustomer().getUrl_image())
                            .placeholder(R.drawable.place_holder_user)
                            .resize(200, 200)
                            .into(holder1.image);
                if (myFriendsRecommended.getProviders() != null) {
                    holder1.purchaseFrom.setText(purchaseFrom+" "+myFriendsRecommended.getProviders().getName());
                    Picasso.with(context)
                            .load(myFriendsRecommended.getProviders().getUrl_image())
                            .placeholder(R.drawable.place_holder_provider)
                            .resize(200, 200)
                            .into(holder1.provider_image);
                }

                holder1.followCheckbox.setOnCheckedChangeListener(null);
                holder1.followCheckbox.setText(context.getResources().getString(R.string.follow));
//                holder1.followCheckbox.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_plus_button), null, null, null);

                holder1.followCheckbox.setOnClickListener(null);
                holder1.followCheckbox.setChecked(myFriendsRecommended.getCustomer().getIs_follow());
                if (myFriendsRecommended.getIs_freind() == true) {
                    holder1.followCheckbox.setVisibility(View.GONE);
                    holder1.arrow.setVisibility(View.VISIBLE);
                }else{
                    holder1.followCheckbox.setVisibility(View.VISIBLE);
                    holder1.arrow.setVisibility(View.GONE);
                }
                if (holder1.followCheckbox.isChecked()) {
                    holder1.followCheckbox.setVisibility(View.GONE);
                    holder1.arrow.setVisibility(View.VISIBLE);

                    //holder1.followCheckbox.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                }
                holder1.followCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            buttonView.setText(context.getResources().getString(R.string.following));
                            buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                            AddCustomerToFollowing(myFriendsRecommended.getCustomer().getId(), "follow");
                            myFriendsRecommended.getCustomer().setIs_follow(true);

                        } else {
                            buttonView.setText(context.getResources().getString(R.string.follow));
                            buttonView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_plus_button), null, null, null);
                            AddCustomerToFollowing(myFriendsRecommended.getCustomer().getId(), "unfollow");
                            myFriendsRecommended.getCustomer().setIs_follow(false);

                        }
                    }
                });

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener2.onItemClick(myFriendsRecommended);
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
        return myFriendsRecommendedList == null ? 0 : myFriendsRecommendedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == myFriendsRecommendedList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(MyFriendsRecommended m) {
        myFriendsRecommendedList.add(m);
        notifyItemInserted(myFriendsRecommendedList.size() - 1);
    }

    public void addAll(ArrayList<MyFriendsRecommended> myFriendsRecommendedList) {
        for (MyFriendsRecommended result : myFriendsRecommendedList) {
            add(result);
        }
    }

    public void remove(MyFriendsRecommended m) {
        int position = myFriendsRecommendedList.indexOf(m);
        if (position > -1) {
            myFriendsRecommendedList.remove(position);
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
        add(new MyFriendsRecommended());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
if(myFriendsRecommendedList.size()>0){
        int position = myFriendsRecommendedList.size() - 1;
        MyFriendsRecommended result = getItem(position);

        if (result != null) {
            myFriendsRecommendedList.remove(position);
            notifyItemRemoved(position);
        }}
    }

    public MyFriendsRecommended getItem(int position) {
        return myFriendsRecommendedList.get(position);
    }

    public void deleteAllItems() {
        myFriendsRecommendedList.clear();
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
        ImageView image, provider_image, arrow;
        TextView name, purchaseFrom, time;
        CardView cardView;
        CheckBox followCheckbox;

        public Holder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            provider_image = itemView.findViewById(R.id.provider_image);
            name = itemView.findViewById(R.id.name);
            purchaseFrom = itemView.findViewById(R.id.purchaseFrom);
            time = itemView.findViewById(R.id.time);
            cardView = itemView.findViewById(R.id.card_view);
            followCheckbox = itemView.findViewById(R.id.follow_checkbox);
            arrow = itemView.findViewById(R.id.arrow);
        }
    }

    public void setOnClickListener(OnItemClickListener3 listener) {
        this.listener2 = listener;
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    private void AddCustomerToFollowing(int customerId, String operation) {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.FollowCustomer(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, customerId, operation);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body().getStatus();
                    // Toast.makeText(context,"saved"+success,Toast.LENGTH_SHORT).show();

                    if (success) {
                        Toast.makeText(context, success + "", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    APIError apiError = ErrorUtils.parseError(response);
                    Toast.makeText(context, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();
                    // progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);

            }
        });
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
