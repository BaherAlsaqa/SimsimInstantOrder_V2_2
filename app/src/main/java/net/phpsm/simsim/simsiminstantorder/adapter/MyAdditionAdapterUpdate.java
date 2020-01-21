package net.phpsm.simsim.simsiminstantorder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener18;
import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;

import java.util.List;

/**
 * Created by baher on 16/11/2017.
 */

public class MyAdditionAdapterUpdate extends RecyclerView.Adapter<MyAdditionAdapterUpdate.ViewHolder> {
    List<ProductAdditional> productAdditionalList;
    Context context;
    private OnItemClickListener18 listener1;

    public MyAdditionAdapterUpdate(List<ProductAdditional> productAdditionalList, Context context) {
        this.productAdditionalList = productAdditionalList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addition_style, viewGroup, false);
        ViewHolder holderR = new ViewHolder(view);
        return holderR;
    }

    @Override
    public void onBindViewHolder(MyAdditionAdapterUpdate.ViewHolder holder, int position) {
        final ProductAdditional productAdditional = productAdditionalList.get(position);
        holder.name.setText(productAdditional.getName());
        Picasso.with(context).load(productAdditional.getUrlImage()).into(holder.image);
        holder.choice1.setText(productAdditional.getChoice_1());
        holder.choice2.setText(productAdditional.getChoice_2());
        holder.choice3.setText(productAdditional.getChoice_3());
        Log.d("imggg",productAdditional.getUrlImage()+"/"+productAdditional.getImage());
        Log.d("choices", productAdditional.getChoice_1()+" | "+productAdditional.getChoice_2()+" | "+productAdditional.getChoice_3());

        switch (productAdditionalList.get(position).getSelected()){
            case 2:
                holder.additionalRadioGroup.check(R.id.yes_additional_radiobutton);
                break;
            case 1:
                holder.additionalRadioGroup.check(R.id.no_addtional_radiobutton);
                break;
            case 0:
                holder.additionalRadioGroup.check(R.id.extra_additional_radiobutton);
                break;
            case -1:
                holder.additionalRadioGroup.clearCheck();
        }

        /*View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener1.OnCheckedChangeListener(position, holder.additionalRadioGroup, productAdditionalList.get(position).getSelected());
            }
        };*/

        RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                listener1.OnCheckedChangeListener(position, group, checkedId);
            }
        };

        holder.additionalRadioGroup.setOnCheckedChangeListener(listener2);
    }

    @Override
    public int getItemCount() {
        return productAdditionalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, choice1, choice2, choice3;
        RadioGroup additionalRadioGroup;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.img_v);
            name = itemView.findViewById(R.id.name);
            additionalRadioGroup = itemView.findViewById(R.id.additional_radiogroup);
            choice1 = itemView.findViewById(R.id.choice1);
            choice2 = itemView.findViewById(R.id.choice2);
            choice3 = itemView.findViewById(R.id.choice3);
        }
    }

    public void setOnCheckedChangeListener(OnItemClickListener18 listener) {
        this.listener1 = listener;
    }

}
