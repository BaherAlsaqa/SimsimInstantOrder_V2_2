package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityAddFriends;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener;
import net.phpsm.simsim.simsiminstantorder.models.Objects.UsersFilter;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UsersDatum;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

/**
 * Created by baher on 16/11/2017.
 */

public class MyAddFriendsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<UsersDatum> addfriendsList;
    List<UsersDatum> addfriendsFilteredList;
    UsersFilter filter;
    Context context;
    private OnItemClickListener listener1;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Boolean isFinish = false;
    AppSharedPreferences appSharedPreferences;

    public MyAddFriendsAdapter(List<UsersDatum> addfriendsList, Context context) {
        this.addfriendsList = addfriendsList;
        this.context = context;
        this.addfriendsFilteredList = addfriendsList;
        filter = new UsersFilter(addfriendsList, this);

    }

    @Override
    public int getItemViewType(int position) {
        if (position >= addfriendsFilteredList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public void setList(List<UsersDatum> list) {
        if ((list.size() == 0)) {
            ((ActivityAddFriends) context).NextPageOfUsers();
            this.addfriendsFilteredList = list;

        } else {
            this.addfriendsFilteredList = list;

        }
    }

    //call when you want to filter
    public void filterList(String text) {
        filter.filter(text);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addusers_style, viewGroup, false);
            ViewItemHolder holderR = new ViewItemHolder(view);
            return holderR;
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_progress_footer, viewGroup, false);
            ViewFooterHolder holderF = new ViewFooterHolder(view);
            return holderF;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        appSharedPreferences = new AppSharedPreferences(context);
        if (holder instanceof ViewItemHolder) {

            final UsersDatum addfriends = addfriendsFilteredList.get(position);
            ((ViewItemHolder) holder).name.setText(addfriends.getName());

            ((ViewItemHolder) holder).location.setText(addfriends.getName());
            Picasso.with(context)
                    .load(addfriends.getUrl_image())
                    .placeholder(R.drawable.place_holder_user)
                    .resize(200, 200)
                    .into(((ViewItemHolder) holder).image);
            ((ViewItemHolder) holder).followCheckbox.setOnCheckedChangeListener(null);
            ((ViewItemHolder) holder).followCheckbox.setText(context.getResources().getString(R.string.follow));
            ((ViewItemHolder) holder).followCheckbox.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            ((ViewItemHolder) holder).followCheckbox.setOnClickListener(null);
            ((ViewItemHolder) holder).followCheckbox.setChecked(addfriends.getIsFollow());

            if (((ViewItemHolder) holder).followCheckbox.isChecked()) {
                ((ViewItemHolder) holder).followCheckbox.setText(context.getResources().getString(R.string.following));
                ((ViewItemHolder) holder).followCheckbox.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            }
            ((ViewItemHolder) holder).followCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setText(context.getResources().getString(R.string.following));
                        buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                        AddCustomerToFollowing(addfriendsFilteredList.get(position).getId(), "follow");
                        addfriendsFilteredList.get(position).setIsFollow(true);

                    } else {
                        buttonView.setText(context.getResources().getString(R.string.follow));
                        //buttonView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_plus_button), null, null, null);
                        AddCustomerToFollowing(addfriendsFilteredList.get(position).getId(), "unfollow");
                        addfriendsFilteredList.get(position).setIsFollow(false);

                    }
                }
            });

        } else if ((holder instanceof ViewFooterHolder)) {
            if (addfriendsFilteredList.size() > 0) {
                ((ViewFooterHolder) holder).progressBar.setVisibility(View.VISIBLE);

            }
            if (isFinish) {
                Log.d("isfinash", "finish");
                ((ViewFooterHolder) holder).progressBar.setVisibility(View.GONE);
            }
            // MyPurchaseData myPurchase = myPurchaseList.get(position);

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
                        Log.d("success", success+"");
                        //Toast.makeText(context, success + "", Toast.LENGTH_SHORT).show();
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

    public void setIsFinish() {
        isFinish = true;
    }

    @Override
    public int getItemCount() {
        return addfriendsFilteredList.size() + 1;
    }

    public void deleteAllData() {
        addfriendsList.clear();
        notifyDataSetChanged();
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, location;
        CheckBox followCheckbox;

        public ViewItemHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            followCheckbox = itemView.findViewById(R.id.follow_checkbox);
        }
    }

    public class ViewFooterHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ViewFooterHolder(View v) {
            super(v);
            progressBar = v.findViewById(R.id.progressBar1);
            // Add your UI Components here
        }

    }

    public void addFriends(List<UsersDatum> usersData) {
        for (int i = 0; i < usersData.size(); i++) {
            addfriendsList.add(usersData.get(i));
        }
        addfriendsFilteredList = addfriendsList;
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener1 = listener;
    }
}
