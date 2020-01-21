package net.phpsm.simsim.simsiminstantorder.models.Objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderItem;

/**
 * Created by HP on 1/20/2018.
 */

public class OrderItemView extends LinearLayout {
    OrderItem orderItem;
    public OrderItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OrderItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderItemView(Context context , OrderItem orderItem ) {
        super(context);
        this.orderItem=orderItem;
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.order_item_view, null);
        TextView itemtitleTv=view.findViewById(R.id.itemtitleTv);
        itemtitleTv.setText(orderItem.getProduct().getTitle());

        TextView itemamountTv=view.findViewById(R.id.itemamountTv);
        itemamountTv.setText("x"+orderItem.getAmount());

        TextView itempriceTv=view.findViewById(R.id.itempriceTv);
        itempriceTv.setText(orderItem.getTotalPriceItem()+"");
        addView(view);
    }
}