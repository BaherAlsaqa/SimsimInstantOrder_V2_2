package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyPurchaseData;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.PurchaseorderItems;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveImage;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

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

public class MyPurchaseDetailsAdapter extends RecyclerView.Adapter<MyPurchaseDetailsAdapter.ViewHolder> {
    List<PurchaseorderItems> myPurchasedatailsList;
    Context context;
    AppSharedPreferences appSharedPreferences;
    public static SaveImage saved=new SaveImage();
  public   interface IClickListener{
        void onItemClick(int position, MyPurchaseData myPurchaseList);
    }
    IClickListener iClickListener;

    public MyPurchaseDetailsAdapter(List<PurchaseorderItems> myPurchasedatailsList, Context context) {
        this.myPurchasedatailsList = myPurchasedatailsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_purchase_details_item, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyPurchaseDetailsAdapter.ViewHolder holder, int position) {
        appSharedPreferences = new AppSharedPreferences(context);
        PurchaseorderItems myPurchasedatails = myPurchasedatailsList.get(position);
       holder.product_name1.setText(myPurchasedatails.getProduct().getTitle());
        Log.d("name",myPurchasedatails.getProduct().getTitle());
        holder.provider_name.setText(myPurchasedatails.getProduct().getProvider().getName());
        holder.datetime.setText(myPurchasedatails.getCreated_at());
        Picasso.with(context)
                .load(myPurchasedatails.getProduct().getUrlImageProduct())
                .placeholder(R.drawable.place_holder_product)
                .resize(200,200)
                .into(holder.image1);
        holder.addpicTv.setText(context.getResources().getString(R.string.addpic));
        if(myPurchasedatails.getCustomer_image()!=null){
            holder.addpicTv.setText(context.getResources().getString(R.string.showpic));
        }
holder.l_save1.setOnCheckedChangeListener(null);
        if(myPurchasedatails.getSave()==1){
    holder.l_save1.setChecked(true);}
        holder.l_save1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveOrder(myPurchasedatails.getId(),"add");
                    Log.d("iddddd",myPurchasedatails.getId()+"");
                    Log.d("iddddd2",myPurchasedatails.getOrder_id()+"");


                }else {
                    saveOrder(myPurchasedatails.getId(),"remove");
                }
            }
        });




    if(myPurchasedatails.getRecommend()!=null){
        holder.recomtv.setText(myPurchasedatails.getRecommend().getType_recommend());
        holder.typeRecomend.setText(myPurchasedatails.getRecommend().getType_recommend());
        if(myPurchasedatails.getRecommend().getType_recommend().equals("recommend")){
holder.recomImg.setBackground(context.getResources().getDrawable(R.drawable.ic_happyface));
        }else if(myPurchasedatails.getRecommend().getType_recommend().equals("itsok")){

            holder.recomImg.setBackground(context.getResources().getDrawable(R.drawable.ic_normalface));

        }else if(myPurchasedatails.getRecommend().getType_recommend().equals("dislike")) {

            holder.recomImg.setBackground(context.getResources().getDrawable(R.drawable.ic_sadface));

        }

        }

        /*holder.l_recom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopupWindow(holder.l_recom1,myPurchasedatails.getId());
            }
        });*/
        holder.radioGroupRecom.setOnCheckedChangeListener(null);
        holder.radioGroupRecom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.recom :
                        saveRecommend(myPurchasedatails.getId(),"recommend");
                        holder.radioButtonRecom.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        holder.radioButtonRecom.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_happyface));
                        break;
                    case R.id.its_ok :
                        saveRecommend(myPurchasedatails.getId(),"itsok");
                        holder.radioButtonItsok.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        holder.radioButtonItsok.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_normalface));
                        break;
                    case R.id.dis_like :
                        saveRecommend(myPurchasedatails.getId(),"dislike");
                        holder.radioButtonDislike.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                        holder.radioButtonDislike.setButtonDrawable(context.getResources().getDrawable(R.drawable.ic_sadface));
                        break;
                }
            }
        });

    holder.l_addpic1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("add","adddd");
            if(myPurchasedatails.getCustomer_image()!=null){
                ((ActivityMain)context).showUserImage(myPurchasedatails.getCustomer_image());
            }else {
                ((ActivityMain) context).ImagePickChooser(myPurchasedatails.getOrder_id(), myPurchasedatails.getId());


            }
        }
    });
    }



    private void saveOrder(int id,  String i) {
       String token = appSharedPreferences.readString("access_token");
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<SaveItem> call = apiService.saveOrderItem1(appSharedPreferences.readString("lang"),"application/json", "Bearer "+token, id, i);
            call.enqueue(new Callback<SaveItem>() {
                @Override
                public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                    if (response.isSuccessful()) {
                 Boolean success=response.body().getStatus();
                        Toast.makeText(context,"saved"+success,Toast.LENGTH_SHORT).show();

                        if(success){
                     Toast.makeText(context,"saved",Toast.LENGTH_SHORT).show();
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
    private void saveRecommend(int id, String recommend) {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.saveRecomendedItem(appSharedPreferences.readString("lang"),"application/json", "Bearer "+ token,id,recommend);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success=response.body().getStatus();
                    Toast.makeText(context,recommend+success,Toast.LENGTH_SHORT).show();

                    if(success){
                        Toast.makeText(context,recommend+success,Toast.LENGTH_SHORT).show();
                    }
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

    @Override
    public int getItemCount() {
        return myPurchasedatailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView image1;
        TextView product_name1, provider_name, typeRecomend, recomtv,addpicTv, datetime;
        ImageView recomImg;
        LinearLayout l_addpic1;
        RadioGroup radioGroupRecom;
        RadioButton radioButtonRecom, radioButtonItsok, radioButtonDislike;

        CheckBox l_save1;

        public ViewHolder(View itemView) {
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

        }
    }
    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }
    public void addEmployee(List<PurchaseorderItems> purchase){
        for(int i=0;i<purchase.size();i++){
            myPurchasedatailsList.add(purchase.get(i));
        }
        notifyDataSetChanged();
    }
    /*private void displayPopupWindow(View anchorView, int orderId) {
        PopupWindow popup = new PopupWindow(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.purchase_recommend_item, null);
        RadioGroup rcommGroup=layout.findViewById(R.id.rcommGroup);
        rcommGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
             switch (checkedId){
                 case R.id.recom:
                     saveRecommend(orderId,"recommend");

                     ((ImageView)((LinearLayout)anchorView).findViewById(R.id.recomimg)).setBackground(context.getResources().getDrawable(R.drawable.ic_happyface));
                     ((TextView)((LinearLayout)anchorView).findViewById(R.id.recomtv)).setText(context.getResources().getString(R.string.recommended));

                     popup.dismiss();
                     break;

                 case R.id.itsok:
                     saveRecommend(orderId,"itsok");
                     (((LinearLayout)anchorView).findViewById(R.id.recomimg)).setBackground(context.getResources().getDrawable(R.drawable.ic_normalface));
                     ((TextView)((LinearLayout)anchorView).findViewById(R.id.recomtv)).setText(context.getResources().getString(R.string.its_ok));

                     popup.dismiss();

                     break;

                 case R.id.dislike:
                     saveRecommend(orderId,"dislike");
                     (((LinearLayout)anchorView).findViewById(R.id.recomimg)).setBackground(context.getResources().getDrawable(R.drawable.ic_sadface));
                     ((TextView)((LinearLayout)anchorView).findViewById(R.id.recomtv)).setText(context.getResources().getString(R.string.dis_like));

                     popup.dismiss();

                     break;
             }
            }
        });
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        popup.setBackgroundDrawable(new BitmapDrawable());
        // Show anchored to button
       // popup.showAtLocation(anchorView, Gravity.BOTTOM, 0,
          //      anchorView.getBottom() );

        popup.showAsDropDown(anchorView);
    }*/

}
