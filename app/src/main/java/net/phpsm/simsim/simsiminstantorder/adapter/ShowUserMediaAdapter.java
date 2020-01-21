package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.ui.ExoVideoPlaybackControlView;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;
import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.Objects.mediaObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zero1 on 10/05/2018.
 */

public class ShowUserMediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<mediaObject> mediaList;
    Context context;
    private static final int TYPE_IMG = 1;
    private static final int TYPE_video = 2;
    AppSharedPreferences appSharedPreferences;

    public interface IClickListener {
        void onItemClick(int position, String url);
    }

    IClickListener iClickListener;
    int x = 0;

    @Override
    public int getItemViewType(int position) {
        String[] separated = mediaList.get(position).getItemUrl().split("\\.");
        if (separated[separated.length - 1].trim().equals("jpg") || separated[separated.length - 1].trim().equals("png") || separated[separated.length - 1].trim().equals("jpeg")) {
            return TYPE_IMG;
        }else {
            return TYPE_video;
        }

    }

    public ShowUserMediaAdapter(Context context, List<mediaObject> mediaList) {
        this.mediaList = mediaList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_IMG) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_img_item, viewGroup, false);
            ViewImgHolder holderR = new ViewImgHolder(view);
            return holderR;
        } else if (viewType == TYPE_video) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_video_item, viewGroup, false);
            ViewVideoHolder holderF = new ViewVideoHolder(view);
            return holderF;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        appSharedPreferences = new AppSharedPreferences(context);

        if (holder instanceof ViewImgHolder) {
            Log.d("ss","sssssss");
            final mediaObject img_url = mediaList.get(position);
            Picasso.with(context).load(img_url.getItemUrl()).placeholder(R.drawable.place_holder_product).into(((ViewImgHolder) holder).item_img);
            ((ViewImgHolder) holder).close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context instanceof ActivityMain){
                        ((ActivityMain)context).closeMediaDialog();

                    }else if(context instanceof  ActivityMainCall){
                        ((ActivityMainCall)context).closeMediaDialog();

                    }

                }
            });

            final boolean[] delshow = {false};
            ((ViewImgHolder) holder).item_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!delshow[0]){
                        ((ViewImgHolder) holder).top.setVisibility(View.VISIBLE);
                        delshow[0] =true;
                    }else {
                        ((ViewImgHolder) holder).top.setVisibility(View.INVISIBLE);
                        delshow[0] =false;

                    }

                }
            });

          ((ViewImgHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  deleteImage(img_url.getOrderId(),img_url.getOrderItemId(),img_url.getItemName(),position);
              }
          });
        } else if ((holder instanceof ViewVideoHolder)) {
            final mediaObject vid_url = mediaList.get(position);

            SimpleMediaSource mediaSource = new SimpleMediaSource(vid_url.getItemUrl());
            ((ViewVideoHolder) holder).videoView.play(mediaSource);

            ((ViewVideoHolder) holder).videoView.setBackgroundColor(context.getResources().getColor(R.color.Black4));
            ((ViewVideoHolder) holder).videoView.removeViewAt(ExoVideoPlaybackControlView.CUSTOM_VIEW_TOP);

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View del_view= inflater.inflate(R.layout.delete_item, null);
              ((ViewVideoHolder) holder).videoView.addCustomView(ExoVideoPlaybackControlView.CUSTOM_VIEW_TOP,del_view );
            del_view.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      deleteImage(vid_url.getOrderId(),vid_url.getOrderItemId(),vid_url.getItemName(),position);

                  }
              });



            ((ViewVideoHolder) holder).videoView.setBackListener(new ExoVideoPlaybackControlView.ExoClickListener() {
                @Override
                public boolean onClick(@Nullable View view, boolean isPortrait) {

                    if(context instanceof ActivityMain){
                        ((ViewVideoHolder) holder).videoView.stop();
                        ((ActivityMain)context).closeMediaDialog();

                    }else if(context instanceof  ActivityMainCall){
                        ((ViewVideoHolder) holder).videoView.stop();
                        ((ActivityMainCall)context).closeMediaDialog();

                    }
                    return false;
                }
            });




        }

    }


    public void deleteImage(Integer orderid,Integer orderItemId, String fileName,Integer position) {
        AppSharedPreferences appSharedPreferences = new AppSharedPreferences(context);
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.deleteUserMedia("application/json","Bearer " + token,orderid,orderItemId, fileName);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body().getStatus();
                    //Toast.makeText(context,"delete "+success,Toast.LENGTH_SHORT).show();

                    if (success) {
                       removeAt(position);
                        //Toast.makeText(context, "delete " + success+position, Toast.LENGTH_SHORT).show();

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
        return mediaList.size();
    }

    public class ViewImgHolder extends RecyclerView.ViewHolder /*implements View.OnCreateContextMenuListener*/ {
        ImageView item_img;
        ImageButton close;
        RelativeLayout top;
        ImageView delete;
        public ViewImgHolder(View itemView) {
            super(itemView);
            item_img=itemView.findViewById(R.id.item_img);
            close=itemView.findViewById(R.id.close);
            top=itemView.findViewById(R.id.top);
            delete=itemView.findViewById(R.id.delete);


        }


    }

    public class ViewVideoHolder extends RecyclerView.ViewHolder {
        ExoVideoView videoView;
        ImageButton close;

        public ViewVideoHolder(View v) {
            super(v);
            videoView=itemView.findViewById(R.id.selectedVideo);

            //close=itemView.findViewById(R.id.close);

// Add your UI Components here
        }

    }


    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }

    public void removeAt(int position) {
        mediaList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mediaList.size());
        if(mediaList.size()==0){
            ActivityMain.getInstance().updatePurchaseItem();
            ActivityMain.getInstance().closeMediaDialog();
        }

    }
}
