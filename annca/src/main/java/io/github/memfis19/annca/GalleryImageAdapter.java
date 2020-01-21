package io.github.memfis19.annca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.util.List;

import io.github.memfis19.annca.internal.ui.BaseAnncaActivity;

/**
 * Created by MSI on 4/8/2018.
 */

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.ViewHolder> {
    List<String> imagesList;
    Context context;
    public   interface IClickListener{
        void onItemClick(int position, String imgUrl);
    }
    IClickListener iClickListener;
    public GalleryImageAdapter(List<String> imagesList, Context context) {
        this.imagesList = imagesList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gallery_image_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }
    @SuppressLint("ResourceType")

    @Override
    public void onBindViewHolder(GalleryImageAdapter.ViewHolder holder,final int position) {
        final String imgUrl = imagesList.get(position);

        RequestCreator picasso= Picasso.with(context).load("file://"+imgUrl);

        picasso.placeholder(R.drawable.place_holder_provider)
               // .resize(200, 200)
                .fit()
                .into(holder.img);
        holder.img.setBackgroundColor(Color.WHITE);
        holder.img.setOnClickListener(new View.OnClickListener() {
              @Override
        public void onClick(View view) {
                  //((CameraActivity)context).LoadGImage(imgUrl);
                  ((BaseAnncaActivity)context).startPreviewActivity(new File(imgUrl));
           Toast.makeText(
                context,
                "position " + position + " " + imgUrl,
                Toast.LENGTH_SHORT).show();

    }
});


    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            // cardView = itemView.findViewById(R.id.card_view);
        }
    }
    public void setiClickListener(IClickListener iClickListener) {
        this.iClickListener = iClickListener;
    }
}
