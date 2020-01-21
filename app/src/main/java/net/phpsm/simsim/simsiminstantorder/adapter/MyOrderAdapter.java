package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener;
import net.phpsm.simsim.simsiminstantorder.R;

import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener17;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderAditionalView;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderItemView;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderItemView2;
import net.phpsm.simsim.simsiminstantorder.models.Objects.ProductAditionalView;
import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;
import net.phpsm.simsim.simsiminstantorder.models.UpOrder;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baher on 16/11/2017.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    ArrayList<UpOrder> myOrderList;
    Context context;
    private OnItemClickListener17 listener1;

    public MyOrderAdapter(ArrayList<UpOrder> myOrderList, Context context) {
        this.myOrderList = myOrderList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myorder_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyOrderAdapter.ViewHolder holder, int position) {

        String coin = context.getString(R.string.coin);
        final UpOrder myOrder = myOrderList.get(position);

       // holder.details.setText(myOrder.getTitle()+"");
        Log.d("uporder",myOrderList.size()+"");

       holder.itemtitleTv.setText(myOrder.getTitle());
       holder.itempriceTv.setText(myOrder.getPriceAdditional()+" "+context.getResources().getString(R.string.s_r));
       holder.itemamountTv.setText("x "+myOrder.getQuantity());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.onItemClick(myOrder);
            }
        };

        holder.cardView.setOnClickListener(listener);


    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemtitleTv, itemamountTv, itempriceTv;
        public LinearLayout  foreground_layout;
        public LinearLayout  background_layout;
        CardView cardView;
       // LinearLayout itemsContainer;

        public ViewHolder(View itemView) {
            super(itemView);

           foreground_layout = itemView.findViewById(R.id.foreground_layout);
           background_layout = itemView.findViewById(R.id.background_layout);
           itemtitleTv=itemView.findViewById(R.id.itemtitleTv);
           itemamountTv=itemView.findViewById(R.id.itemamountTv);
           itempriceTv=itemView.findViewById(R.id.itempriceTv);
           cardView = itemView.findViewById(R.id.card_view);

        }
    }
    public void setOnClickListener(OnItemClickListener17 listener) {
        this.listener1 = listener;
    }
    public void removeItem(int position) {
        myOrderList.remove(position);

        notifyItemRemoved(position);
    }

    /*public void restoreItem(UpOrder item, int position) {
        myOrderList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }*/
}
