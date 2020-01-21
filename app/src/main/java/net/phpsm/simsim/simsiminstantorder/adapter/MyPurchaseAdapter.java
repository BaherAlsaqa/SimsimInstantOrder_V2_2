package net.phpsm.simsim.simsiminstantorder.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.Objects.mediaObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyPurchaseData;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.PurchaseorderItems;
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

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.memfis19.annca.Annca;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import io.github.memfis19.annca.internal.ui.preview.PreviewActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by baher on 16/11/2017.
 */

public class MyPurchaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<PurchaseorderItems> myPurchaseList;
    Context context;
    private static final int CAPTURE_MEDIA = 368;

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Boolean isFinish=false;
    AppSharedPreferences appSharedPreferences;

    public void deleteAllData() {
        myPurchaseList.clear();
        notifyDataSetChanged();
    }

    public   interface IClickListener{
        void onItemClick(int position, PurchaseorderItems myPurchaseList);
    }
    IClickListener iClickListener;

    public MyPurchaseAdapter(List<PurchaseorderItems> myPurchaseList, Context context) {
        this.myPurchaseList = myPurchaseList;
        this.context = context;
    }
    @Override
    public int getItemViewType(int position) {
       if(position >= myPurchaseList.size()){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
      if(viewType==TYPE_ITEM) {
          View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mypurchase_style, viewGroup, false);
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
        if(holder instanceof ViewItemHolder ){

            PurchaseorderItems myPurchase = myPurchaseList.get(position);
            appSharedPreferences = new AppSharedPreferences(context);
            ((ViewItemHolder) holder).product_name1.setText(myPurchase.getProduct().getTitle());
            //Log.d("name",myPurchase.getProduct().getTitle());
            ((ViewItemHolder) holder).provider_name.setText(myPurchase.getProduct().getProvider().getName());
            ((ViewItemHolder) holder).datetime.setText(formateddate(myPurchase.getCreated_at()));
            ((ViewItemHolder) holder).l_point.setVisibility(View.VISIBLE);
            ((ViewItemHolder) holder).l_yummy.setVisibility(View.VISIBLE);

            // ((ViewItemHolder) holder).yammy_num.setText(myPurchase.getCount_react_count()+"");
            Picasso.with(context)
                    .load(myPurchase.getProduct().getUrlImageProduct())
                    .placeholder(R.drawable.place_holder_product)
                    .resize(200,200)
                    .into(((ViewItemHolder) holder).image1);


            if (myPurchase.getCount_react_count() != null) {
                if(myPurchase.getCount_react_count()==0){
                    ((ViewItemHolder) holder).l_yummy.setVisibility(View.INVISIBLE);
                    if (myPurchase.getReview_points() != null) {
                        if(myPurchase.getReview_points()==0){
                            ((ViewItemHolder) holder).l_point.setVisibility(View.INVISIBLE);
                        }else {
                            ((ViewItemHolder) holder).l_point.setVisibility(View.VISIBLE);
                            ((ViewItemHolder) holder).point_num.setText(myPurchase.getReview_points()*myPurchase.getAmount() + "");}
                    }else{
                        ((ViewItemHolder) holder).l_point.setVisibility(View.INVISIBLE);
                        ((ViewItemHolder) holder).point_num.setText(0 + "");
                    }
                }else {
                ((ViewItemHolder) holder).l_yummy.setVisibility(View.VISIBLE);
                    ((ViewItemHolder) holder).l_point.setVisibility(View.INVISIBLE);

                    ((ViewItemHolder) holder).yammy_num.setText(myPurchase.getCount_react_count()+"");}

            }else{
                ((ViewItemHolder) holder).l_yummy.setVisibility(View.INVISIBLE);
                ((ViewItemHolder) holder).yammy_num.setText(0 + "");
                if (myPurchase.getReview_points() != null) {
                    if(myPurchase.getReview_points()==0){
                        ((ViewItemHolder) holder).l_point.setVisibility(View.INVISIBLE);
                    }else {
                        ((ViewItemHolder) holder).l_point.setVisibility(View.VISIBLE);
                        ((ViewItemHolder) holder).point_num.setText(myPurchase.getReview_points()*myPurchase.getAmount() + "");}
                }else{
                    ((ViewItemHolder) holder).l_point.setVisibility(View.INVISIBLE);
                    ((ViewItemHolder) holder).point_num.setText(0 + "");
                }
            }
            ((ViewItemHolder) holder).addimage.setBackground(context.getResources().getDrawable(R.drawable.ic_pic11_1));
            //((ViewItemHolder) holder).addpicTv.setText(context.getResources().getString(R.string.addpic));
            if(myPurchase.getCustomer_file_a_url()!=null || myPurchase.getCustomer_file_b_url()!=null || myPurchase.getCustomer_file_c_url()!=null )
                ((ViewItemHolder) holder).addimage.setBackground(context.getResources().getDrawable(R.drawable.ic_pic22));
            ((ViewItemHolder) holder).radioButtonRecom.setTextColor(context.getResources().getColor(R.color.bold_name));
            ((ViewItemHolder) holder).radioButtonRecom.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_happyface2));
            ((ViewItemHolder) holder).radioButtonItsok.setTextColor(context.getResources().getColor(R.color.bold_name));
            ((ViewItemHolder) holder).radioButtonItsok.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_normalface1));
            ((ViewItemHolder) holder).radioButtonDislike.setTextColor(context.getResources().getColor(R.color.bold_name));
            ((ViewItemHolder) holder).radioButtonDislike.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_sadface1));

            if(myPurchase.getRecommend()!=null){

                if(myPurchase.getRecommend().getType_recommend().equals("recommend")){
                    ((ViewItemHolder) holder).radioButtonRecom.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((ViewItemHolder) holder).radioButtonRecom.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_happyface));
                }else if(myPurchase.getRecommend().getType_recommend().equals("itsok")){
                    ((ViewItemHolder) holder).radioButtonItsok.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((ViewItemHolder) holder).radioButtonItsok.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_normalface));
                }else if(myPurchase.getRecommend().getType_recommend().equals("dislike")) {
                    ((ViewItemHolder) holder).radioButtonDislike.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                    ((ViewItemHolder) holder).radioButtonDislike.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_sadface));
                }

            }

        /*holder.l_recom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupWindow(holder.l_recom1,myPurchase..getOrderItems().get(position).getId());
            }
        });*/
            ((ViewItemHolder) holder).radioGroupRecom.setOnCheckedChangeListener(null);
            ((ViewItemHolder) holder).radioGroupRecom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId){
                        case R.id.recom :
                            saveRecommend(myPurchase.getId(),"recommend");
                            ((ViewItemHolder) holder).radioButtonRecom.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            ((ViewItemHolder) holder).radioButtonRecom.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_happyface));
                            ((ViewItemHolder) holder).radioButtonItsok.setTextColor(context.getResources().getColor(R.color.bold_name));
                            ((ViewItemHolder) holder).radioButtonItsok.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_normalface1));
                            ((ViewItemHolder) holder).radioButtonDislike.setTextColor(context.getResources().getColor(R.color.bold_name));
                            ((ViewItemHolder) holder).radioButtonDislike.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_sadface1));
                            break;
                        case R.id.its_ok :
                            saveRecommend(myPurchase.getId(),"itsok");
                            ((ViewItemHolder) holder).radioButtonRecom.setTextColor(context.getResources().getColor(R.color.bold_name));
                            ((ViewItemHolder) holder).radioButtonRecom.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_happyface2));
                            ((ViewItemHolder) holder).radioButtonItsok.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            ((ViewItemHolder) holder).radioButtonItsok.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_normalface));
                            ((ViewItemHolder) holder).radioButtonDislike.setTextColor(context.getResources().getColor(R.color.bold_name));
                            ((ViewItemHolder) holder).radioButtonDislike.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_sadface1));
                            break;
                        case R.id.dis_like :
                            saveRecommend(myPurchase.getId(),"dislike");
                            ((ViewItemHolder) holder).radioButtonRecom.setTextColor(context.getResources().getColor(R.color.bold_name));
                            ((ViewItemHolder) holder).radioButtonRecom.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_happyface2));
                            ((ViewItemHolder) holder).radioButtonItsok.setTextColor(context.getResources().getColor(R.color.bold_name));
                            ((ViewItemHolder) holder).radioButtonItsok.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_normalface1));
                            ((ViewItemHolder) holder).radioButtonDislike.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                            ((ViewItemHolder) holder).radioButtonDislike.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_sadface));
                            break;
                    }
                }
            });

            ((ViewItemHolder) holder).l_addpic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("add","adddd");

                    ArrayList<mediaObject> mediaUrls=new ArrayList<>();
                    if(myPurchase.getCustomer_file_a_url()!=null || myPurchase.getCustomer_file_b_url()!=null || myPurchase.getCustomer_file_c_url()!=null ) {
                        if (myPurchase.getCustomer_file_a_url() != null) {
                            mediaUrls.add(new mediaObject(myPurchase.getOrder_id(),myPurchase.getId(),"customer_file_a",myPurchase.getCustomer_file_a_url()));
                        }
                        if (myPurchase.getCustomer_file_b_url() != null) {
                            mediaUrls.add(new mediaObject(myPurchase.getOrder_id(),myPurchase.getId(),"customer_file_b",myPurchase.getCustomer_file_b_url()));
                        }
                        if (myPurchase.getCustomer_file_c_url() != null) {
                            mediaUrls.add(new mediaObject(myPurchase.getOrder_id(),myPurchase.getId(),"customer_file_c",myPurchase.getCustomer_file_c_url()));
                        }

                        ((ActivityMain) context).showUserMedia(mediaUrls);
                    } else {
                        appSharedPreferences.writeInteger("orderId",myPurchase.getOrder_id());
                        appSharedPreferences.writeInteger("orderItemId",myPurchase.getId());

                        AnncaConfiguration.Builder photo_video = new AnncaConfiguration.Builder((ActivityMain)context, CAPTURE_MEDIA);
                        photo_video.setMediaAction(AnncaConfiguration.MEDIA_ACTION_UNSPECIFIED);
                        photo_video.setMediaQuality(AnncaConfiguration.MEDIA_QUALITY_MEDIUM);
                        photo_video.setCameraFace(AnncaConfiguration.CAMERA_FACE_REAR);
                        photo_video.setMediaResultBehaviour(AnncaConfiguration.PREVIEW);
                        //photo_video.setVideoFileSize(5 * 1024 * 1024);
                        photo_video.setMinimumVideoDuration(1000);
                        photo_video.setVideoDuration(30000);

                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        new Annca(photo_video.build()).launchCamera();

                    }
                }
            });


        }else if ((holder instanceof ViewFooterHolder)){
            if(myPurchaseList.size()>0){
                ((ViewFooterHolder)holder).progressBar.setVisibility(View.VISIBLE);

            }              if(isFinish){
              Log.d("isfinash","finish");
    ((ViewFooterHolder)holder).progressBar.setVisibility(View.GONE);
}
           // MyPurchaseData myPurchase = myPurchaseList.get(position);

        }


    }
public void setIsFinish(){
        isFinish=true;
}
    @Override
    public int getItemCount() {
        return myPurchaseList.size()+1;
    }

    public class ViewItemHolder extends RecyclerView.ViewHolder {
        ImageView image1;
        TextView product_name1, provider_name, addpicTv, point_num, datetime, yammy_num;
        LinearLayout l_addpic1;
        RadioGroup radioGroupRecom;
        RadioButton radioButtonRecom, radioButtonItsok, radioButtonDislike;
        ImageView addimage;
        LinearLayout l_point,l_yummy;

        public ViewItemHolder(View itemView) {
            super(itemView);
            image1 = itemView.findViewById(R.id.image1);
            product_name1 = itemView.findViewById(R.id.product_name1);
            provider_name = itemView.findViewById(R.id.provider_name);
            datetime = itemView.findViewById(R.id.datetime);
            //l_recom1 = itemView.findViewById(R.id.l_recom1);
            l_addpic1 = itemView.findViewById(R.id.l_addpic1);
            addpicTv=itemView.findViewById(R.id.addpicTv);
            radioGroupRecom = itemView.findViewById(R.id.radio_group_recom);
            radioButtonRecom = itemView.findViewById(R.id.recom);
            radioButtonItsok = itemView.findViewById(R.id.its_ok);
            radioButtonDislike = itemView.findViewById(R.id.dis_like);
            point_num = itemView.findViewById(R.id.point_num);
            addimage = itemView.findViewById(R.id.addimage);
            yammy_num = itemView.findViewById(R.id.yammy_num);
            l_point= itemView.findViewById(R.id.l_point);
            l_yummy=itemView.findViewById(R.id.l_yummy);

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
    public void setiClickListener(IClickListener iClickListener) {

        this.iClickListener = iClickListener;
    }
    public void addEmployee(List<PurchaseorderItems> purchase){
        for(int i=0;i<purchase.size();i++){
            myPurchaseList.add(purchase.get(i));
        }
        notifyDataSetChanged();
    }

    private void saveRecommend(int id, String recommend) {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.saveRecomendedItem(appSharedPreferences.readString("lang"),"application/json", "Bearer "+ token,id,recommend);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success=response.body().getStatus();

                    /*if(success == true)
                        Toast.makeText(context,recommend+" "+success,Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context,recommend+" "+success,Toast.LENGTH_SHORT).show();*/
                }
                else {
                    APIError apiError = ErrorUtils.parseError(response);
                    new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), context);
                    new ActivityMain().CustomToast1("Error is : " + apiError.getError(), context);
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
