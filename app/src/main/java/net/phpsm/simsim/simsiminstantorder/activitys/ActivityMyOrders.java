package net.phpsm.simsim.simsiminstantorder.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.adapter.MyOrdersAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderAditionalView;
import net.phpsm.simsim.simsiminstantorder.models.Objects.OrderItemView;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.Datum;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.MyOrders;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderAdditional;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.OrderItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class ActivityMyOrders extends AppCompatActivity {

    List<Datum> myOrdersList ;
    RecyclerView myOrderRecyclerView;
    AVLoadingIndicatorView progressBar;
    String token,refreshToken;
    int pageId=1;


    String nextPage;
    MyOrdersAdapter myOrdersAdapter;
    AppSharedPreferences appSharedPreferences;
    static ActivityMyOrders orders;
    SwipeRefreshLayout swiperefresh;
    int totalItemCount;
    View internet;
    View empityData;

    public static ActivityMyOrders getInstance(){
        return   orders;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        orders=this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        internet=findViewById(R.id.no_internet);
        empityData=findViewById(R.id.no_data);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
////////////////////////////////////////////////////////////////////////
        myOrderRecyclerView = findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progress);
        swiperefresh=findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));



        myOrdersList = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        myOrderRecyclerView.setLayoutManager(layoutManager);
        myOrdersAdapter = new MyOrdersAdapter(myOrdersList,this);
        myOrderRecyclerView.setAdapter(myOrdersAdapter);
        appSharedPreferences = new AppSharedPreferences(ActivityMyOrders.this);

        token = appSharedPreferences.readString("access_token");
        refreshToken=appSharedPreferences.readString("refresh_token");


        progressBar.setVisibility(View.VISIBLE);
      //  myOrdersAdapter.setIsFinish(false);

        getOrdersData();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageId=1;
                myOrdersList.clear();
                myOrdersAdapter.deleteAllData();
                getOrdersData();

            }
        });
        myOrderRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                // totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible +1>= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    if(nextPage!=null){
                        pageId++;
                   //     progressBar.setVisibility(View.VISIBLE);
                        getOrdersData();
                    }
                    //you have reached to the bottom of your recycler view
                }
            }
        });


     /*   myOrdersAdapter.setiClickListener(new MyOrdersAdapter.IClickListener() {
            @Override
            public void onItemClick(int position, Datum myOrderItems) {
                Log.d("clik",position+"");
                details_container.setVisibility(View.VISIBLE);
                if(myOrderItems!=null){
                    totalpriceTv.setText("Total price : "+myOrderItems.getTotalPrice()+" S.R");

                    Picasso.with(ActivityMyOrders.this).load(myOrderItems.getProvider().getUrl_image()).resize(100,100).into(providerImg);
                    providerNameTv.setText(myOrderItems.getProvider().getName());
                    List<OrderItem> data=myOrderItems.getOrderItems();
                    if(data!=null){
                        for(int i=0;i<data.size();i++){
                            OrderItemView orderItemView= new OrderItemView(ActivityMyOrders.this,data.get(i));
                            itemsContainer.addView(orderItemView);
                        }
                        List<OrderAdditional> additionals=myOrderItems.getOrderAdditionals();
                        if(additionals!=null){
                            for (int i2=0; i2<additionals.size();i2++){
                                OrderAditionalView orderAditionalView=new OrderAditionalView(ActivityMyOrders.this,additionals.get(i2));
                                itemsContainer.addView(orderAditionalView);
                            }
                        }
                    }
                }
              //  startActivity(new Intent(getApplicationContext(),OrderDetails.class).putExtra("orderItems",myOrderItems));

            }
        });
*/
    }

    private void getOrdersData() {
        if (!isNetworkConnected() &&pageId==1) {
            swiperefresh.setRefreshing(false);
            myOrderRecyclerView.setVisibility(View.GONE);
            internet.setVisibility(View.VISIBLE);
            empityData.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);

        } else {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MyOrders> call = apiService.getUserOrders(appSharedPreferences.readString("lang"),"api/v1/myorders?page="+pageId,"application/json","Bearer "+token);
        call.enqueue(new Callback<MyOrders>() {
            @Override
            public void onResponse(Call<MyOrders> call, Response<MyOrders> response) {
                if (response.isSuccessful()) {
                    swiperefresh.setRefreshing(false);
                    empityData.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    myOrderRecyclerView.setVisibility(View.VISIBLE);
                    internet.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);


                    myOrdersList=response.body().getItems().getData();
                    if(myOrdersList.size()==0 && pageId==1){
                        empityData.setVisibility(View.VISIBLE);
                        swiperefresh.setRefreshing(false);
                        myOrderRecyclerView.setVisibility(View.GONE);
                        internet.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }
                    totalItemCount=myOrdersList.size();
                    /*for (int i=0; i<response.body().getItems().getData().size(); i++){
                        myOrdersList = response.body().getItems().getData();
                        for (int j=0; j<response.body().getItems().getData().get(i).getOrderItems().size(); j++){
                            myOrdersList.get(i).setOrderItems(response.body().getItems().getData().get(i).getOrderItems());
                            for (int h=0; h<response.body().getItems().getData().get(i).getOrderItems().get(j).getProduct().getProductAdditionals().size(); h++){
                                myOrdersList.get(i).getOrderItems().get(j).getProduct().setProductAdditionals(response.body().getItems().getData().get(i).getOrderItems().get(j).getProduct().getProductAdditionals());
                            }
                        }
                    }*/
                    //Log.d("sssssssssize", response.body().getItems().getData().get(0).getOrderItems().get(0).getProduct().getProductAdditionals().size()+"");
                    nextPage=response.body().getItems().getNextPageUrl();
                    if(nextPage==null){
                        myOrdersAdapter.setIsFinish();
                    }
                    myOrdersAdapter.addOrders(myOrdersList);
                    // myPurchaseAdapter.notifyDataSetChanged();
                   // Log.d("xxxx",myPurchaseList.size()+"");

                }
                else if (response.code()==401){
                    swiperefresh.setRefreshing(false);

                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss","ssss");

                  //  Toast.makeText(ActivityMyOrders.this, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    ActivityMain.getInstance().RefreshUserToken(refreshToken,R.id.myorders);
                }
            }
            @Override
            public void onFailure(Call<MyOrders> call, Throwable t) {

                if (ActivityMyOrders.this != null){
                    progressBar.setVisibility(View.GONE);
                    swiperefresh.setRefreshing(false);
                    myOrderRecyclerView.setVisibility(View.GONE);
                    internet.setVisibility(View.VISIBLE);
                    empityData.setVisibility(View.GONE);
//                    Toast.makeText(ActivityMyOrders.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });}
    }
    public void closeActivity () {
        Intent intent = getIntent();
        finish();
    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ActivityMyOrders.this, texttoast, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
