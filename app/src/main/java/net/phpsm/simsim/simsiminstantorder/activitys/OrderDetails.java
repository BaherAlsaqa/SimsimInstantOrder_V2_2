package net.phpsm.simsim.simsiminstantorder.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderAditionalView;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderItemView;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Datum;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetails extends AppCompatActivity {
    CircleImageView providerImg;
    TextView providerNameTv,totalpriceTv;
    LinearLayout itemsContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /////////
        toolbar.setNavigationIcon(R.drawable.ic_back_arrow);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                }
        );
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       providerImg=findViewById(R.id.providerImg);
       providerNameTv=findViewById(R.id.providerNameTv);
       totalpriceTv=findViewById(R.id.totalpriceTv);
       itemsContainer=findViewById(R.id.itemsContainer);


        Intent intent=getIntent();
        Bundle bundle= intent.getExtras();

        Datum orderItems= (Datum) getIntent().getSerializableExtra("orderItems");
        if(orderItems!=null){
            totalpriceTv.setText(getResources().getString(R.string.total_price)+orderItems.getTotalPrice()+" "+getResources().getString(R.string.s_r));

         Picasso.with(this).load(orderItems.getProvider().getUrl_image()).resize(100,100).into(providerImg);
            providerNameTv.setText(orderItems.getProvider().getName());
            List<OrderItem> data=orderItems.getOrderItems();
            if(data!=null){
                itemsContainer.removeAllViews();
                for(int i=0;i<data.size();i++){
                    OrderItemView orderItemView= new OrderItemView(this,data.get(i));
                     itemsContainer.addView(orderItemView);
                }
                List<OrderAdditional> additionals=orderItems.getOrderAdditionals();
                if(additionals!=null){
                    for (int i2=0; i2<additionals.size();i2++){
                        OrderAditionalView orderAditionalView=new OrderAditionalView(this,additionals.get(i2));
                        itemsContainer.addView(orderAditionalView);
                    }
                }
            }
        }


    }
}
