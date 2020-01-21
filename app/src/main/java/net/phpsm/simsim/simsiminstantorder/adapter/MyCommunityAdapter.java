package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
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
import net.phpsm.simsim.simsiminstantorder.activitys.Signup1;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyPurchaseData;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.UsersDatum;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by baher on 16/11/2017.
 */

public class MyCommunityAdapter extends RecyclerView.Adapter<MyCommunityAdapter.ViewHolder> {
    List<UsersDatum> communityList;
    Context context;
    AppSharedPreferences appSharedPreferences;

    public interface IClickListener {
        void onItemClick(int position, MyPurchaseData myPurchaseList);
    }

    MyCommunityAdapter.IClickListener iClickListener;

    public MyCommunityAdapter(List<UsersDatum> communityList, Context context) {
        this.communityList = communityList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addusers_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyCommunityAdapter.ViewHolder holder, int position) {
        appSharedPreferences = new AppSharedPreferences(context);
        final UsersDatum comunityItem = communityList.get(position);
        holder.name.setText(comunityItem.getName());
        holder.location.setText(comunityItem.getName());
        Picasso.with(context)
                .load(comunityItem.getImage())
                .placeholder(R.drawable.place_holder_user)
                .resize(200, 200)
                .into(holder.image);
        holder.followCheckbox.setOnCheckedChangeListener(null);
        holder.followCheckbox.setText(context.getResources().getString(R.string.follow));
        holder.followCheckbox.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_plus_button), null, null, null);

        holder.followCheckbox.setOnClickListener(null);
        holder.followCheckbox.setChecked(comunityItem.getIsFollow());

        if (holder.followCheckbox.isChecked()) {
            holder.followCheckbox.setText(context.getResources().getString(R.string.following));
            holder.followCheckbox.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }
        holder.followCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText(context.getResources().getString(R.string.following));
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    AddCustomerToFollowing(communityList.get(position).getId(), "follow");
                    communityList.get(position).setIsFollow(true);

                } else {
                    buttonView.setText(context.getResources().getString(R.string.follow));
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_plus_button), null, null, null);
                    AddCustomerToFollowing(communityList.get(position).getId(), "unfollow");
                    communityList.get(position).setIsFollow(false);

                }
            }
        });
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

    @Override
    public int getItemCount() {
        return communityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, location;
        CheckBox followCheckbox;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            location = itemView.findViewById(R.id.location);
            followCheckbox = itemView.findViewById(R.id.follow_checkbox);
        }
    }

    public void addFriends(List<UsersDatum> usersData) {
        for (int i = 0; i < usersData.size(); i++) {
            communityList.add(usersData.get(i));
        }
        notifyDataSetChanged();
    }

    public void setiClickListener(MyCommunityAdapter.IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

}
