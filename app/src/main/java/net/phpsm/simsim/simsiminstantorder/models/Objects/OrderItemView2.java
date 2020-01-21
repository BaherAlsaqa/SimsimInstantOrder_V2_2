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

public class OrderItemView2 extends LinearLayout {
    String title;
    String quantity;
    String price;
    public OrderItemView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public OrderItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OrderItemView2(Context context ,  String title, String quantity, String price) {
        super(context);
        this.title=title;
        this.quantity=quantity;
        this.price=price;

        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.order_item_view, null);
        TextView itemtitleTv=view.findViewById(R.id.itemtitleTv);
        itemtitleTv.setText(title);

        TextView itemamountTv=view.findViewById(R.id.itemamountTv);
        itemamountTv.setText("x"+quantity);

        TextView itempriceTv=view.findViewById(R.id.itempriceTv);
        itempriceTv.setText(price+"");
        addView(view);
    }
}