package net.phpsm.simsim.simsiminstantorder.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityChat;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMessages;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.Objects.MessagesFilter;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MassegDatum;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

/**
 * Created by baher on 16/11/2017.
 */

public class MyMessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   List<MassegDatum> messageList;
    List<MassegDatum> messageListFilter;
    MessagesFilter filter;
    Context context;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Boolean isFinish=false;

    public void deleteAllData() {
        messageList.clear();
        notifyDataSetChanged();
    }

    public   interface IClickListener{
        void onItemClick(int position, MassegDatum myMsgItems);
    }
    IClickListener iClickListener;
    public MyMessagesAdapter(List<MassegDatum> messageList, Context context) {
        this.messageList = messageList;
        this.messageListFilter=messageList;
        this.context = context;
        filter = new MessagesFilter(messageList, this);
    }

    @Override
    public int getItemViewType(int position) {
        if(position >= messageListFilter.size()){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    public void setList(List<MassegDatum> list) {
        if ((list.size()==0)){
            ((ActivityMessages)context).NextPageOfMeessages();
            this.messageListFilter = list;

        }else {
            this.messageListFilter = list;

        }
    }
    //call when you want to filter
    public void filterList(String text) {
        filter.filter(text);

    }
    @Override
    public  RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType==TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_style, viewGroup, false);
            ViewItemHolder holderR = new ViewItemHolder(view);
            return holderR;
        }else if(viewType==TYPE_FOOTER){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_progress_footer, viewGroup, false);
            ViewFooterHolder holderF = new ViewFooterHolder(view);
            return holderF;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewItemHolder ) {
            final MassegDatum message = messageListFilter.get(position);
            ((ViewItemHolder)holder).name.setText(message.getProvider().getName());
            ((ViewItemHolder)holder).content.setText(message.getContent());
           // ((ViewItemHolder)holder).time.setText(parseDateToddMMyyyy(message.getSentAt()));
            Picasso.with(context).load(message.getProvider().getUrl_image()).into(((ViewItemHolder)holder).image);
            ((ViewItemHolder)holder).name.setTextColor(context.getResources().getColor(R.color.bold_name));
            ((ViewItemHolder)holder).content.setTextColor(context.getResources().getColor(R.color.bold_name));
            ((ViewItemHolder)holder).time.setTextColor(context.getResources().getColor(R.color.bold_name));
            //((ViewItemHolder)holder).image.setBorderColor(context.getResources().getColor(R.color.bold_name));
            ((ViewItemHolder) holder).image2.setBackground(context.getResources().getDrawable(R.drawable.borders2));
            ((ViewItemHolder)holder).image1.setBackground(context.getResources().getDrawable(R.drawable.ic_true_message));
            ((ViewItemHolder)holder).buttonViewOption.setBackground(context.getResources().getDrawable(R.drawable.ic_settings));
            if (message.getCustomerRead().equals("0")) {
                ((ViewItemHolder)holder).name.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ViewItemHolder)holder).content.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                ((ViewItemHolder)holder).time.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                //((ViewItemHolder)holder).image.setBorderColor(context.getResources().getColor(R.color.colorPrimary));
                ((ViewItemHolder) holder).image2.setBackground(context.getResources().getDrawable(R.drawable.borders_red));

                ((ViewItemHolder)holder).image1.setBackground(context.getResources().getDrawable(R.drawable.ic_arrow_ready));
                ((ViewItemHolder)holder).buttonViewOption.setBackground(context.getResources().getDrawable(R.drawable.ic_settings_red));
            }
            ((ViewItemHolder)holder).buttonViewOption.setOnClickListener(null);
            ((ViewItemHolder)holder).buttonViewOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(view.getContext(), ((ViewItemHolder)holder).buttonViewOption);
                    popup.inflate(R.menu.options_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                              /*  case R.id.o_menu1:
                                    Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.o_menu2:
                                    Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.o_menu3:
                                    Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    break;*/

                                case R.id.delete_item:
                                    Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    DeleteMessage(message.getId());
                                    break;

                             /*   case R.id.o_menu5:
                                    Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.o_menu6:
                                    Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.o_menu7:
                                    Toast.makeText(context, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                                    break;*/
                            }
                            return false;
                        }
                    });
                    popup.show();

                }
            });

            ((ViewItemHolder)holder).container.setOnClickListener(null);
            ((ViewItemHolder)holder).container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (message.getCustomerRead().equals("0")) {
                        SetMessageAsRead(message.getId());
                        ((ViewItemHolder)holder).name.setTextColor(context.getResources().getColor(R.color.bold_name));
                        ((ViewItemHolder)holder).content.setTextColor(context.getResources().getColor(R.color.bold_name));
                        ((ViewItemHolder)holder).time.setTextColor(context.getResources().getColor(R.color.bold_name));
                       // ((ViewItemHolder)holder).image.setBorderColor(context.getResources().getColor(R.color.bold_name));
                        ((ViewItemHolder) holder).image2.setBackground(context.getResources().getDrawable(R.drawable.borders2));

                        ((ViewItemHolder)holder).image1.setBackground(context.getResources().getDrawable(R.drawable.ic_true_message));
                        ((ViewItemHolder)holder).buttonViewOption.setBackground(context.getResources().getDrawable(R.drawable.ic_settings));
                    }
                    if (context instanceof ActivityMessages) {
                        //((ActivityMessages) context).ShowPopUpDialog(message.getProvider().getUrl_image(), message.getProvider().getName(), message.getContent(), message.getSentAt());
                        ((ActivityMessages) context).startActivity(new Intent(context, ActivityChat.class)
                                .putExtra("p_m_id", message.getProvider().getId()));
                    }
                    // ShowPopUpDialog(message.getProvider().getUrl_image(),message.getProvider().getName(),message.getContent(),message.getSentAt());


                }
            });
        }else if ((holder instanceof ViewFooterHolder)){
            if(messageListFilter.size()>0){
                ((ViewFooterHolder)holder).progressBar.setVisibility(View.VISIBLE);

            }            if(isFinish){
                Log.d("isfinash","finish");
                ((ViewFooterHolder)holder).progressBar.setVisibility(View.GONE);
            }
            // MyPurchaseData myPurchase = myPurchaseList.get(position);

        }

    }

    private void DeleteMessage(Integer id) {
        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(context);
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.deleteMessage(appSharedPreferences.readString("lang"),id,"application/json","Bearer "+ token);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success=response.body().getStatus();
                   //Toast.makeText(context,"delete "+success,Toast.LENGTH_SHORT).show();

                    if(success){
                        Toast.makeText(context,"delete "+success,Toast.LENGTH_SHORT).show();
                    }
                    DeleteMessageFromList(id);
                }
                else {
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

    private void DeleteMessageFromList(Integer id) {
        for (int i=0;i<messageList.size();i++){
            if(messageList.get(i).getId()==id){
                messageList.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    private void SetMessageAsRead(int msgId) {
        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(context);
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.MessageRead(appSharedPreferences.readString("lang"),"application/json","Bearer "+ token,msgId);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success=response.body().getStatus();
                    if(success){
                        Toast.makeText(context,"read",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    APIError apiError = ErrorUtils.parseError(response);
                    Toast.makeText(context, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                // progressBar.setVisibility(View.GONE);

            }
        });

    }

    public void setIsFinish(){
        isFinish=true;
    }
    @Override
    public int getItemCount() {
        return messageListFilter.size()+1;
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder {
        ImageView  buttonViewOption,image1;
        ImageView image;
        CardView container;
        TextView name, content, time;
        FrameLayout image2;
        public ViewItemHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            buttonViewOption = itemView.findViewById(R.id.buttonViewOption);
            container=itemView.findViewById(R.id.container);
            image1=itemView.findViewById(R.id.image1);
            image2=itemView.findViewById(R.id.image2);
        }
    }

    public class ViewFooterHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ViewFooterHolder(View v) {
            super(v);
            progressBar= v.findViewById(R.id.progressBar1);
            // Add your UI Components here
        }

    }

    public void addMessages(List<MassegDatum> messageItems){
        for(int i=0;i<messageItems.size();i++){
            messageList.add(messageItems.get(i));

        }
        messageListFilter=messageList;

        notifyDataSetChanged();
    }

    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
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
}
