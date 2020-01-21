package net.phpsm.simsim.simsiminstantorder.models.Objects;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;

/**
 * Created by HP on 1/20/2018.
 */

public class ProductAditionalView extends LinearLayout {
    ProductAdditional orderItem;
    public ProductAditionalView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public ProductAditionalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProductAditionalView(Context context , ProductAdditional orderItem ) {
        super(context);
        this.orderItem=orderItem;
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.order_item_view, null);
        TextView itemtitleTv=view.findViewById(R.id.itemtitleTv);
        itemtitleTv.setText(orderItem.getName());

        TextView itemamountTv=view.findViewById(R.id.itemamountTv);

        itemamountTv.setText("x"+orderItem.getSelected());

        TextView itempriceTv=view.findViewById(R.id.itempriceTv);
        itempriceTv.setText(orderItem.getPrice()+"");
        Log.d("here",orderItem.getName());
        addView(view);
    }
}