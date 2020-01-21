package net.phpsm.simsim.simsiminstantorder.models.Objects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;

/**
 * Created by HP on 1/20/2018.
 */

public class OrderAditionalView extends LinearLayout {
    OrderAdditional orderItem;
    public OrderAditionalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OrderAditionalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderAditionalView(Context context , OrderAdditional orderItem ) {
        super(context);
        this.orderItem=orderItem;
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.order_item_view, null);
        TextView itemtitleTv=view.findViewById(R.id.itemtitleTv);
        itemtitleTv.setText(orderItem.getAdditional().getName());

        TextView itemamountTv=view.findViewById(R.id.itemamountTv);
        itemamountTv.setText("x"+orderItem.getAdditionalAmount());

        TextView itempriceTv=view.findViewById(R.id.itempriceTv);
        itempriceTv.setText(orderItem.getAdditionalTotalPrice()+"");
        addView(view);
    }
}