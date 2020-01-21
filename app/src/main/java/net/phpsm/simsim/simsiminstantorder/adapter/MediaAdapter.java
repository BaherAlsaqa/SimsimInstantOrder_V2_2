package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderAditionalView;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderItemView;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Datum;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.services.TrackingService;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.Utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.github.memfis19.annca.internal.configuration.AnncaConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Url;

/**
 * Created by baher on 16/11/2017.
 */

public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<String> mediaList;
    Context context;
    private static final int TYPE_IMG = 1;
    private static final int TYPE_video = 2;
    Boolean isFinish = false;
    boolean isSelected = false;
    AppSharedPreferences appSharedPreferences;

    public interface IClickListener {
        void onItemClick(int position, String url);
    }

    IClickListener iClickListener;
    int x = 0;

    @Override
    public int getItemViewType(int position) {
        String[] separated = mediaList.get(position).split("\\.");
        if (separated[separated.length - 1].trim().equals("jpg") || separated[separated.length - 1].trim().equals("png") || separated[separated.length - 1].trim().equals("jpeg")) {
            return TYPE_IMG;
        }else {
            return TYPE_video;
        }

    }

    public MediaAdapter( Context context,List<String> mediaList) {
        this.mediaList = mediaList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_IMG) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.img_item, viewGroup, false);
            ViewImgHolder holderR = new ViewImgHolder(view);
            return holderR;
        } else if (viewType == TYPE_video) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item, viewGroup, false);
            ViewVideoHolder holderF = new ViewVideoHolder(view);
            return holderF;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        appSharedPreferences = new AppSharedPreferences(context);

        if (holder instanceof ViewImgHolder) {
            final String img_url = mediaList.get(position);


            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            try {
                inputStream = cr.openInputStream(Uri.parse("file://" +img_url));
                Bitmap bitmap1 = BitmapFactory.decodeStream(inputStream);

                ((ViewImgHolder) holder).item_img.setImageBitmap( modifyOrientation(bitmap1, Uri.parse("file://" +img_url)));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ((ViewImgHolder) holder).delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaList.remove(position);
                    notifyDataSetChanged();
                    ((ActivityMain)context).showAddButton();
                }
            });
            ((ViewImgHolder) holder).item_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ActivityMain)context).showUserImage("file://" + img_url);
                }
            });


        } else if ((holder instanceof ViewVideoHolder)) {
            final String vid_url = mediaList.get(position);


            ((ViewVideoHolder) holder).videoView.setVideoURI(Uri.parse("file://" + vid_url));
            ((ViewVideoHolder) holder).videoView.start();
            ((ViewVideoHolder) holder).delete_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaList.remove(position);
                    notifyDataSetChanged();
                    ((ActivityMain)context).showAddButton();

                }
            });
            ((ViewVideoHolder) holder).videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    Log.d("rrr","rrr");
                    ((ActivityMain)context).showUserVideo("file://" + vid_url);

                    return false;
                }
            });

        }

    }



    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    public class ViewImgHolder extends RecyclerView.ViewHolder /*implements View.OnCreateContextMenuListener*/ {
    ImageView item_img;
    ImageButton delete_item;

        public ViewImgHolder(View itemView) {
            super(itemView);
            item_img=itemView.findViewById(R.id.item_img);
            delete_item=itemView.findViewById(R.id.delete_item);



        }


    }


    public class ViewVideoHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        ImageButton delete_item;

        public ViewVideoHolder(View v) {
            super(v);
            videoView=itemView.findViewById(R.id.item_vid);
            delete_item=itemView.findViewById(R.id.delete_item);

// Add your UI Components here
        }

    }


    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }
    public static Bitmap modifyOrientation(Bitmap bitmap, Uri image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path.getPath());
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);
            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

}
