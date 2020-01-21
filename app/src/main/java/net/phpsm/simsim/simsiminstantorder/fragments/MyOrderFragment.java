package net.phpsm.simsim.simsiminstantorder.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.activitys.LoginActivity;
import net.phpsm.simsim.simsiminstantorder.activitys.Signup1;
import net.phpsm.simsim.simsiminstantorder.activitys.YourOrderComplete;
import net.phpsm.simsim.simsiminstantorder.adapter.DeliveyStatusDialogAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.MyAdditionAdapterUpdate;
import net.phpsm.simsim.simsiminstantorder.adapter.MyOrderAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.helpers.RecyclerItemTouchHelper;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener15;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener16;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener17;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener18;
import net.phpsm.simsim.simsiminstantorder.models.OrderLoader;
import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;
import net.phpsm.simsim.simsiminstantorder.models.UpOrder;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Additionals;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.OrderObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Product;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Products;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ProviderOnOff;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.DeliveryStatusList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.services.TrackingService;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.SimpleDividerItemDecoration;
import net.phpsm.simsim.simsiminstantorder.utils.Utils;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.BIND_AUTO_CREATE;
import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain.OVERLAY_PERMISSION_REQ_CODE_CHATHEAD;
import static net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain.OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG;


public class MyOrderFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private View view;
    ArrayList<UpOrder> myOrderList = new ArrayList<>();
    RecyclerView mRecyclerView;
    MyOrderAdapter adapter;
    Button btncash;
    ImageButton imageView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    TextView totalpricenum;
    double totalpricenumS;
    AppSharedPreferences appSharedPreferences;
    AVLoadingIndicatorView progressBar;
    String token,refresh_token;
    int p_id;
    int b_id;
    DeliveyStatusDialogAdapter deliveyStatusDialogAdapter;
    RecyclerView recyclerViewDelivery;
    ArrayList<Delivery_Status> delivery_statusArrayList = new ArrayList<>();
    ArrayList<Delivery_Status> delivery_statusArrayList_isAvailable = new ArrayList<>();
    private LinearLayout linearLayout;
    MyAdditionAdapterUpdate adapter1;
    Dialog dialog1;
String new_str;
    ////////////////////////Additional////////////////////
    double price = 0.0;
    double additionalsPrice = 0.0;
    double totalPrice = 0.0;
    TextView productPrice, provider_duration;
    double additionalPrice = 0.0;
    int order_no;
    //////////////////Tracking///////////////////

    //public static Button btnStartService, btnShowMsg;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private ArrayList<String> permissionsToRequest;
    String latLngString;
    LatLng latLng;
    TrackingService trackingService = null;
    ServiceConnection serviceConnection;
    private Firebase firebase;
    //////////////////////////////////////



    public static MyOrderFragment newInstance() {
        MyOrderFragment fragment = new MyOrderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_order2, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(10);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btncash = view.findViewById(R.id.btncash);
        totalpricenum = view.findViewById(R.id.totalpricenum);
        progressBar = view.findViewById(R.id.progress);
        linearLayout = view .findViewById(R.id.linear_layout_top);

        appSharedPreferences = new AppSharedPreferences(getContext());
        //sharedPreferencesGet = getActivity().getSharedPreferences(Signup1.MY_PREFS_NAME, MODE_PRIVATE);

        token = appSharedPreferences.readString("access_token");
        refresh_token=appSharedPreferences.readString("refresh_token");

        myOrderList.clear();

        myOrderList = appSharedPreferences.readArray("uporder");

        p_id = appSharedPreferences.readInteger("p_id");
        b_id = appSharedPreferences.readInteger("b_id");

        adapter = new MyOrderAdapter(myOrderList, getContext());
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(
                getActivity()));
        mRecyclerView.setAdapter(adapter);
        new_str = "ListCustomerTrackTime";

        Firebase.setAndroidContext(getActivity());
        firebase = new Firebase("https://simsiminstantorder1.firebaseio.com/"+new_str);
        adapter.setOnClickListener(new OnItemClickListener17() {
            @Override
            public void onItemClick(UpOrder item) {
                additions_dialog(item);
            }
        });

        /*String totalPrice = appSharedPreferences.readString("totalPrice");
        totalpricenum.setText(totalPrice+" "+getString(R.string.coin));*/

        for (int i=0; i<myOrderList.size(); i++){
            String[] productPriceUpdate = String.valueOf(myOrderList.get(i).getPriceAdditional()).split(" ");
            totalpricenumS += Double.parseDouble(productPriceUpdate[0]);
        }

        totalpricenum.setText(totalpricenumS+" "+getString(R.string.s_r));
        totalpricenumS = 0.0;

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);

        btncash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (myOrderList.size() > 0) {
                    if (WifiReciver.conn != "") {
                        if (WifiReciver.conn == "1") {
                            if (!token.equals("")) {
                                //TODO ////////////////////////////DoneOrderToServer/////////////////////////
                                int delivery_status_id = appSharedPreferences.readInteger("delivery_status_id");
                                DoneOrderToServer(myOrderList, "CASH", p_id, b_id, delivery_status_id);
                                //getProviderOnOff();
                            }else{
                                Typeface txtfont;
                                Typeface titlefont;


                                if(appSharedPreferences.readString("lang").equals("ar")){
                                    txtfont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Ubuntu-Arabic_R.ttf");
                                    titlefont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Abdoullah-Ashgar-EL-kharef.ttf");


                                }else {
                                    txtfont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Nunito-Regular.ttf");
                                    titlefont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/forte.ttf");
                                }
                                Alerter.create(getActivity())
                                        .setTitle(getResources().getString(R.string.dearCustomer))
                                        .setTitleTypeface(titlefont)
                                        .setTextTypeface(txtfont)
                                        .setTitleAppearance(R.style.AlertTextAppearance_Title)
                                        .setTextAppearance(R.style.AlertTextAppearance_Text)
                                        .setText(getResources().getString(R.string.mustLogin))
                                        .enableInfiniteDuration(true)
                                        .setBackgroundColorInt(getResources().getColor(R.color.colorPrimary))
                                        .setIcon(getResources().getDrawable(R.drawable.alerter_ic_notifications))
                                        .setIconColorFilter(getResources().getColor(R.color.white))
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                startActivity(new Intent(getActivity(), LoginActivity.class));
                                                getActivity().finish();

                                            }
                                        })
                                        .show();
                            }
                        } else if (WifiReciver.conn == "0") {
                            new ActivityMain().CustomToast1(getString(R.string.connection_message), getActivity());
                        }
                    }
                }else{
                    if (getActivity() != null) {
//                        Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.order_empty), getActivity());
                    }
                }
/*
                Intent i = new Intent(getContext(), YourOrderComplete.class);
                i.setFlags(i.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);*/
            }
        });

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(mRecyclerView);


        //////////////////////////////////////////////////Tracking/////////////////////////////////////////////////////////

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
        }

        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            else {
                fetchLocation();
            }
        } else {
            fetchLocation();
        }

        /////////////////////////////////////////

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                TrackingService.MyBinder binder = (TrackingService.MyBinder) iBinder;
                trackingService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //mRecyclerView.removeAllViews();
        Log.d("onDestroy", "onDestroy");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    public boolean DoneOrderToServer(ArrayList<UpOrder> upOrder, final String PaymentMethod, final int ProviderID, final int BranchId, final int DeliveryStatus_id) {


        token = appSharedPreferences.readString("access_token");
        refresh_token = appSharedPreferences.readString("refresh_token");
        final ArrayList<Product> products = new ArrayList<>();
        ArrayList<Additionals> additionals ;

        if (upOrder.size() > 0) {
            for (int i = 0; i < upOrder.size(); i++) {
                additionals = new ArrayList<>();
                if (upOrder.get(i).getAdditions().size() > 0) {
                    for (int j = 0; j < upOrder.get(i).getAdditions().size(); j++) {
                        additionals.add(new Additionals(upOrder.get(i).getAdditions().get(j).getId(), upOrder.get(i).getAdditions().get(j).getSelected()));
                    }
                }
                products.add(new Product(upOrder.get(i).getId(), upOrder.get(i).getQuantity(), additionals));
            }
        }
        /////////////////
        progressBar.setVisibility(View.VISIBLE);
        int transaction_type_id = appSharedPreferences.readInteger("transaction_type_id");
        int affiliate_customer_id = appSharedPreferences.readInteger("affiliate_customer_id");
        int order_item_id = appSharedPreferences.readInteger("order_item_id");
        int product_id = appSharedPreferences.readInteger("product_id");


        /////////////////
        int checkin_arrival = appSharedPreferences.readInteger("checkin_arrival");
        ApiService apiInterface = ApiClient.getClient().create(ApiService.class);
        Call<OrderObject> call3 = apiInterface.POST_ORDER2(appSharedPreferences.readString("lang"),"application/json","Bearer "+token,"application/json",new OrderLoader(products,PaymentMethod,ProviderID,BranchId,DeliveryStatus_id,transaction_type_id,affiliate_customer_id,order_item_id,product_id,checkin_arrival));
        call3.enqueue(new Callback<OrderObject>() {
            @Override
            public void onResponse(Call<OrderObject> call, Response<OrderObject> response) {
                if (response.code()==200) {
                    if(response.body().getStatus().equals("true")){
                        Log.d("dddc", "true");
                        appSharedPreferences.writeInteger("transaction_type_id", 0);
                        appSharedPreferences.writeInteger("affiliate_customer_id", 0);
                        appSharedPreferences.writeInteger("order_item_id", 0);
                        appSharedPreferences.writeInteger("product_id", 0);
                        //////////////////////////////

                        myOrderList.clear();
                        appSharedPreferences.writeString("displaytotalprice_gone", "1");

                        /////////////////////////////////////////////////

                        ArrayList arrayList = new ArrayList();
                        appSharedPreferences.writeArray("uporder", arrayList);
                        appSharedPreferences.writeString("totalPrice", "");
                        appSharedPreferences.writeString("price", "");
                        order_no = response.body().getItems().getId();
                        /////////////////////Tracking//////////////////

                        Log.d(Utils.LogTag, "lst_StartService -> Utils.canDrawOverlays(Main.this): " + Utils.canDrawOverlays(getActivity()));

                        if(Utils.canDrawOverlays(getActivity())) {
                            //TODO ///////////////////startTracking////////////////////

                            String customer_id = appSharedPreferences.readString("customer_id");

                            int customer_track_time = appSharedPreferences.readInteger("customer_track_time");
                            Firebase parentC = firebase.child(customer_id).child(String.valueOf(order_no));
                            parentC.child("distance").setValue("0");
//      parentC.child("duration").setValue(totalDuration);
                            parentC.child("duration").setValue(customer_track_time);
                            parentC.child("latLongString").setValue(latLngString);


                            appSharedPreferences.writeString("provider_mobile_copy", appSharedPreferences.readString("provider_mobile"));
                            appSharedPreferences.writeString("image_copy", appSharedPreferences.readString("image"));
                            appSharedPreferences.writeString("name_copy", appSharedPreferences.readString("name"));
                            appSharedPreferences.writeString("statusOrder", "PINDING");
                            startTracking();
                        }
                        else{
                            requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
                        }

                         //order_no = response.body().getItems().getId();

                        //TODO //////////////////////Customer_track_time////////////////////////

                        int customer_track_time = response.body().getItems().getCustomer_track_time();

                        appSharedPreferences.writeInteger("customer_track_time", customer_track_time);

                        order_delivered_dialog();

                        appSharedPreferences.writeString("order_no", order_no+"");

                        ///////////////////////////////////////////////

                        Log.e("ORDER","isSuccessful == true " + response.body().getStatus());
//                        Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                 //      new ActivityMain().CustomToast1(getString(R.string.orderCompleted), getActivity());
                        progressBar.setVisibility(View.GONE);
                    }else if(response.body().getStatus().equals("false")){

                        Log.d("dddc", "false");
                        Log.e("ORDER","isSuccessful == false" + response.body().getStatus());
                        if (!response.body().getError().equals("")) {
                            if (getActivity() != null) {
//                            Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                                new ActivityMain().CustomToast1(getString(R.string.provider_close), getActivity());
                            }
                        }
                        progressBar.setVisibility(View.GONE);

                    }

                }
                else if (response.code() == 500) {
//                    Toast.makeText(getContext(), "Error 500", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    /*Gson gson = new Gson();
                    String Order = gson.toJson(new OrderLoader(products,PaymentMethod,ProviderID,BranchId,DeliveryStatus_id));
//                    Log.e("ORDER_LOADER",""+new OrderLoader(products,PaymentMethod,ProviderID,BranchId,DeliveryStatus).toString());
                    Log.e("ORDER_LOADER",""+Order);*/
                }else if (response.code()==401){
                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
                    ActivityMain.getInstance().RefreshUserToken(refresh_token,3);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<OrderObject> call, Throwable t) {
                call.cancel();
                Log.e("ORDER","onFailure");
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    progressBar.setVisibility(View.GONE);
                }

            }
        });
        return true;
    }

    private void delivery_status_dialog() {

        dialog1 = new Dialog(getActivity());
        dialog1.show();
        dialog1.setContentView(R.layout.delivery_status_dialog);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        recyclerViewDelivery = dialog1.findViewById(R.id.recyclerViewDeliveryDialog);

        recyclerViewDelivery.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        getDeliveryStatus();

    }

    private void getDeliveryStatus(){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<DeliveryStatusList> call = apiService.getDeliveryStatus(appSharedPreferences.readString("lang"),"application/json","Bearer "+token, p_id);
        call.enqueue(new Callback<DeliveryStatusList>() {
            @Override
            public void onResponse(Call<DeliveryStatusList> call, Response<DeliveryStatusList> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {

                        delivery_statusArrayList_isAvailable.clear();
                        delivery_statusArrayList = response.body().getDelivery_statusArrayList();


                        for (int i = 0; i < delivery_statusArrayList.size(); i++){
                            if (delivery_statusArrayList.get(i).getAvailable().equals("true")){
                                delivery_statusArrayList_isAvailable.add(delivery_statusArrayList.get(i));
                            }
                        }

                        deliveyStatusDialogAdapter = new DeliveyStatusDialogAdapter(delivery_statusArrayList_isAvailable, view.getContext());
                        recyclerViewDelivery.setAdapter(deliveyStatusDialogAdapter);

                        deliveyStatusDialogAdapter.setOnClickListener(new OnItemClickListener15() {
                            @Override
                            public void onItemClick(Delivery_Status item) {
                                Integer onoff = appSharedPreferences.readInteger("onoff");
                                Log.d("onoff", onoff+"");
                                if (onoff == 1) {
                                    //Toast.makeText(getActivity(), "one : "+item.getId()+" ,two : "+item.id, Toast.LENGTH_SHORT).show();
                                    //TODO ////////////////////////////DoneOrderToServer/////////////////////////
                                    DoneOrderToServer(myOrderList, "CASH", p_id, b_id, item.getId());
                                    appSharedPreferences.writeString("delivery_status_url_icon", item.getUrl_icon());
                                    appSharedPreferences.writeString("dlivery_status_name", item.getName());
                                    dialog1.dismiss();
                                }else{
//                                    Toast.makeText(getActivity(), "The provider is closed", Toast.LENGTH_SHORT).show();
                                    new ActivityMain().CustomToast1(getString(R.string.provider_close), getActivity());
                                }
                            }
                        });
                    }
                }

                else if (response.code()==401){
                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
                    ActivityMain.getInstance().RefreshUserToken(refresh_token,3);

                }
            }

            @Override
            public void onFailure(Call<DeliveryStatusList> call, Throwable t) {
//                Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });
    }

    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(getContext());
        dialog1.show();
        dialog1.setContentView(R.layout.message_dialog);
        final TextView message = dialog1.findViewById(R.id.message);
        final Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        String char_message = null;
        if(conn == 0)
            char_message = getString(R.string.connection_message);
        else if (conn == 1)
            char_message = getString(R.string.tryagain);
        else if (conn == 2)
            char_message = messagedialg;
        message.setText(char_message);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {

        if (viewHolder instanceof MyOrderAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = myOrderList.get(viewHolder.getAdapterPosition()).getTitle();

            // backup of removed item for undo purpose
            final UpOrder deletedItem = myOrderList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            adapter.removeItem(viewHolder.getAdapterPosition());

            appSharedPreferences.writeArray("uporder", myOrderList);

            ////////////////////////////////////////////////////////////////

            for (int i=0; i<myOrderList.size(); i++){
                String[] productPriceUpdate = String.valueOf(myOrderList.get(i).getPriceAdditional()).split(" ");
                totalpricenumS += Double.parseDouble(productPriceUpdate[0]);
            }

            totalpricenum.setText(totalpricenumS+" "+getString(R.string.s_r));
            appSharedPreferences.writeString("totalPrice", String.valueOf(totalpricenumS));
            totalpricenumS = 0.0;

            // showing snack bar with Undo option
            /*Snackbar snackbar = Snackbar
                    .make(linearLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    adapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();*/
        }

    }

    private void additions_dialog(UpOrder products) {

        //   final int[] quantity_X = {0};
        final int[] quant = {1};
        final Dialog dialog1 = new Dialog(getActivity());
        ArrayList<ProductAdditional> productAdditionalArrayList = new ArrayList<>();
        RecyclerView mRecyclerView1;
        dialog1.show();
        dialog1.setContentView(R.layout.addition_dialog);
        LinearLayout description_layout= dialog1.findViewById(R.id.description_layout);
        LinearLayout addittion_layout= dialog1.findViewById(R.id.addittion_layout);
        Button description= dialog1.findViewById(R.id.description);
        Button add_item=dialog1.findViewById(R.id.add_item);
        View v1= dialog1.findViewById(R.id.v1);
        View v2= dialog1.findViewById(R.id.v2);
        ImageView productImg= dialog1.findViewById(R.id.img_v);
        TextView productName= dialog1.findViewById(R.id.name);
        TextView product_description = dialog1.findViewById(R.id.product_description);
        TextView price= dialog1.findViewById(R.id.price);
        Picasso.with(getContext())
                .load(products.getUrlImageProduct())
                .fit()
                .into(productImg);
        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        productName.setText(products.getTitle());
        product_description.setText(products.getDescription());
        price.setText(products.getPrice()+" "+getActivity().getResources().getString(R.string.s_r));

        addittion_layout.setVisibility(View.VISIBLE);
        add_item.setTextColor(getResources().getColor(R.color.colorPrimary));
        description.setTextColor(getResources().getColor(R.color.bold_name));
        v2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        v1.setBackgroundColor(getResources().getColor(R.color.bold_name));
        description_layout.setVisibility(View.GONE);

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setTextColor(getResources().getColor(R.color.colorPrimary));
                add_item.setTextColor(getResources().getColor(R.color.bold_name));
                description_layout.setVisibility(View.VISIBLE);
                v1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                v2.setBackgroundColor(getResources().getColor(R.color.bold_name));
                addittion_layout.setVisibility(View.GONE);
            }
        });

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description.setTextColor(getResources().getColor(R.color.bold_name));
                add_item.setTextColor(getResources().getColor(R.color.colorPrimary));
                addittion_layout.setVisibility(View.VISIBLE);
                description_layout.setVisibility(View.GONE);
                v1.setBackgroundColor(getResources().getColor(R.color.bold_name));
                v2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        quant[0] = products.getQuantity();

        /*CardView card = findViewById(R.id.card);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater
                    .loadStateListAnimator(this, R.animator.on_touch_cardview1);
            card.setStateListAnimator(stateListAnimator);
        }*/

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button close = dialog1.findViewById(R.id.close);
        Button saveBtn = dialog1.findViewById(R.id.savebtn);
        //ImageView img_v = dialog1.findViewById(R.id.img_v);
        //Picasso.with(getActivity()).load(products.getUrlImageProduct()).into(img_v);
        //TextView provider_name = dialog1.findViewById(R.id.provider_name);
        String provider_name_value = appSharedPreferences.readString("name");
        //provider_name.setText(provider_name_value);
        ImageButton plus = dialog1.findViewById(R.id.increase);
        ImageButton minus = dialog1.findViewById(R.id.decrease);
        TextView quantity = dialog1.findViewById(R.id.quantity);
        productPrice = dialog1.findViewById(R.id.product_price_value);
        /*productsQuant = dialog1.findViewById(R.id.products_quantity_value);
        totalPriceT = dialog1.findViewById(R.id.total_price_value);*/

        saveBtn.setText(getString(R.string.update));

        productPrice.setText(products.getPriceAdditional()+" "+getString(R.string.s_r));
        //productsQuant.setText(myOrderList.size()+"");
        String totalPriceV = appSharedPreferences.readString("totalPrice");
        //totalPriceT.setText(totalPriceV+" "+getString(R.string.coin));

        quantity.setText(products.getQuantity()+"");

        mRecyclerView1 = dialog1.findViewById(R.id.recyclerView1);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        productAdditionalArrayList = products.getAdditions();

        adapter1 = new MyAdditionAdapterUpdate(productAdditionalArrayList, getActivity());
        mRecyclerView1.setAdapter(adapter1);

        ArrayList<ProductAdditional> finalProductAdditionalArrayList = productAdditionalArrayList;
        adapter1.setOnCheckedChangeListener(new OnItemClickListener18() {
            @Override
            public void OnCheckedChangeListener(int position, RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.yes_additional_radiobutton:
                        finalProductAdditionalArrayList.get(position).setSelected(2);

                        if (finalProductAdditionalArrayList.get(position).getSelected() == 2) {
                            for (int i = 0; i < finalProductAdditionalArrayList.size(); i++) {
                                if (finalProductAdditionalArrayList.get(i).getSelected() == 0) {
                                    additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_1_price();
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                                    try {
                                        additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                                    try {
                                        additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Log.e("SELECTED", "" + finalProductAdditionalArrayList.get(position).getSelected());

                        productPrice.setText(((products.getPrice() * quant[0]) + (additionalPrice * quant[0])) + " " + getString(R.string.s_r));
                        additionalPrice = 0.0;
                        //Toast.makeText(getActivity(), finalProductAdditionalArrayList.get(position).getSelected() + "", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.no_addtional_radiobutton:
                        finalProductAdditionalArrayList.get(position).setSelected(1);

                        if (finalProductAdditionalArrayList.get(position).getSelected() == 1) {
                            for (int i = 0; i < finalProductAdditionalArrayList.size(); i++) {
                                if (finalProductAdditionalArrayList.get(i).getSelected() == 0) {
                                    additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_1_price();
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                                    try {
                                        additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                                    try {
                                        additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Log.e("SELECTED", "" + finalProductAdditionalArrayList.get(position).getSelected());

                        productPrice.setText((products.getPrice() * quant[0]) + (additionalPrice * quant[0])+ " " + getString(R.string.s_r));
                        additionalPrice = 0.0;
                        //Toast.makeText(getActivity(), finalProductAdditionalArrayList.get(position).getSelected() + "", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.extra_additional_radiobutton:
                        finalProductAdditionalArrayList.get(position).setSelected(0);

                        if (finalProductAdditionalArrayList.get(position).getSelected() == 0) {
                            for (int i = 0; i < finalProductAdditionalArrayList.size(); i++) {
                                if (finalProductAdditionalArrayList.get(i).getSelected() == 0) {
                                    additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_1_price();
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                                    try {
                                        additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                                    try {
                                        additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        Log.e("SELECTED", "" + finalProductAdditionalArrayList.get(position).getSelected());

                        productPrice.setText(((products.getPrice() * quant[0]) + (additionalPrice * quant[0])) + " " + getString(R.string.s_r));
                        additionalPrice = 0.0;
                        //Toast.makeText(getActivity(), finalProductAdditionalArrayList.get(position).getSelected() + "", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quant[0]++;
                quantity.setText(quant[0] + "");
                //String[] productPriceS4 = productPrice.getText().toString().split(" ");
                if (finalProductAdditionalArrayList.size() > 0) {
                    for (int i = 0; i < finalProductAdditionalArrayList.size(); i++) {
                        if (finalProductAdditionalArrayList.get(i).getSelected() == 0) {
                            additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_1_price();
                        }
                        else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                            additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                        }
                        else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                            additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                        }
                        Log.e("SELECTED", "" + finalProductAdditionalArrayList.get(i).getSelected());
                    }
                }
                Log.d("additionalPrice", (additionalPrice * quant[0])+"");
                productPrice.setText(((products.getPrice() * quant[0]) + (additionalPrice * quant[0])) + " " + getString(R.string.s_r));
                additionalPrice = 0.0;
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(quantity.getText().toString()) > 1){
                    if (quant[0]>1) {
                        //TODO/////////////////////////////////////////////////
                        //String[] productPriceS4 = productPrice.getText().toString().split(" ");
                        if (finalProductAdditionalArrayList.size() > 0) {
                            for (int i = 0; i < finalProductAdditionalArrayList.size(); i++) {
                                if (finalProductAdditionalArrayList.get(i).getSelected() == 0) {
                                    additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_1_price();
                                } else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                                    additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                                } else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                                    additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                                }
                                Log.e("SELECTED", "" + finalProductAdditionalArrayList.get(i).getSelected());
                            }
                        }
                        productPrice.setText((((products.getPrice() * quant[0]) + additionalPrice * quant[0]) - (products.getPrice() + additionalPrice)) + " " + getString(R.string.s_r));
                        Log.d("productPrice", ((products.getPrice() * quant[0]) + additionalPrice * quant[0]) + "");
                        Log.d("productPrice", (products.getPrice() + additionalPrice) + "");
                        quant[0]--;
                        quantity.setText(quant[0] + "");
                        additionalPrice = 0.0;
                    }
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                products.setQuantity(quant[0]);
                products.setAdditions(finalProductAdditionalArrayList);
                String[] productPriceS = String.valueOf(productPrice.getText().toString()).split(" ");
                products.setPriceAdditional(Double.valueOf(productPriceS[0]));
                adapter.notifyDataSetChanged();

                for (int i=0; i<myOrderList.size(); i++){
                    String[] productPriceUpdate = String.valueOf(myOrderList.get(i).getPriceAdditional()).split(" ");
                    totalpricenumS += Double.parseDouble(productPriceUpdate[0]);
                }

                totalpricenum.setText(totalpricenumS+" "+getString(R.string.s_r));
                appSharedPreferences.writeString("totalPrice", String.valueOf(totalpricenumS));
                totalpricenumS = 0.0;

                appSharedPreferences.writeArray("uporder", myOrderList);

                dialog1.dismiss();

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    ///////////////////////////////////Tracking/////////////////////////////////////////

    /*Button.OnClickListener lst_StartService = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Log.d(Utils.LogTag, "lst_StartService -> Utils.canDrawOverlays(Main.this): " + Utils.canDrawOverlays(getActivity()));

            if(Utils.canDrawOverlays(getActivity()))
                startChatHead();
            else{
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD);
            }
        }

    };*/


    /*Button.OnClickListener lst_ShowMsg = new Button.OnClickListener(){

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(Utils.canDrawOverlays(getActivity()))
                showChatHeadMsg();
            else{
                requestPermission(OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG);
            }

        }

    };*/

    public void startTracking(){
        Log.d("p_image", appSharedPreferences.readString("image")+"");
        String provider_lat = appSharedPreferences.readString("provider_lat");
        String provider_long = appSharedPreferences.readString("provider_long");

        Log.d("latlongtracking", provider_lat+" | "+provider_long);

        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(getContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, 1234);
            } else if (Settings.canDrawOverlays(getContext())) {
                getActivity().startService(new Intent(getActivity(), TrackingService.class)
                        .putExtra(Utils.P_Track_Img, appSharedPreferences.readString("image"))
                        .putExtra("provider_lat", provider_lat)
                        .putExtra("provider_long", provider_long));
                // getActivity().finish();
            }
        }
        else
        {
            getActivity().startService(new Intent(getActivity(), TrackingService.class)
                    .putExtra(Utils.P_Track_Img, appSharedPreferences.readString("image"))
                    .putExtra("provider_lat", provider_lat)
                    .putExtra("provider_long", provider_long));
            //getActivity().finish();

        }
        //getActivity().bindService(new Intent(getActivity(), TrackingService.class), serviceConnection, BIND_AUTO_CREATE);
    }
    private void showChatHeadMsg(){
        java.util.Date now = new java.util.Date();
        String str = "test by henry  " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);

        Intent it = new Intent(getActivity(), TrackingService.class);
        it.putExtra(Utils.EXTRA_MSG, str);
        getActivity().startService(it);
    }

    private void needPermissionDialog(final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You need to allow permission");
        builder.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        requestPermission(requestCode);
                    }
                });
        builder.setNegativeButton("Cancel", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        builder.setCancelable(false);
        builder.show();
    }



    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();


    }

    private void requestPermission(int requestCode){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getActivity().getPackageName()));
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD) {
            if (!Utils.canDrawOverlays(getActivity())) {
                needPermissionDialog(requestCode);
            }else{
                //startTracking();
            }

        }else if(requestCode == OVERLAY_PERMISSION_REQ_CODE_CHATHEAD_MSG){
            if (!Utils.canDrawOverlays(getActivity())) {
                needPermissionDialog(requestCode);
            }else{
                showChatHeadMsg();
            }

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                } else {
                    fetchLocation();
                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void fetchLocation() {

        SmartLocation.with(getActivity()).location()
                .oneFix()
                .start(new OnLocationUpdatedListener() {
                    @Override
                    public void onLocationUpdated(Location location) {
                        latLngString = location.getLatitude() + "," + location.getLongitude();
                        latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    }
                });
    }

    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    public void order_delivered_dialog() {

        final Dialog dialog1 = new Dialog(getActivity());
        dialog1.show();
        dialog1.setContentView(R.layout.order_delivered_dialog);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //TODO ///////////////////////move/////////////////////////

        Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        Button btn_time1 = dialog1.findViewById(R.id.btn_time1);
        Button btn_time2 = dialog1.findViewById(R.id.btn_time2);
        Button btn_time3 = dialog1.findViewById(R.id.btn_time3);
        Button btn_time4 = dialog1.findViewById(R.id.btn_time4);
        LinearLayout linear_btns = dialog1.findViewById(R.id.linear_btns);
        TextView txtReady = dialog1.findViewById(R.id.txt_ready);
        Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder(btn_cancel, order_no);
            }
        });

        int plus_num1 = 5;
        int plus_num2 = 10;
        int plus_num3 = 15;
        int plus_num4 = 20;

        TextView deliveredTime = dialog1.findViewById(R.id.delivered_time);
        int checkin_arrival = appSharedPreferences.readInteger("checkin_arrival");

        if (checkin_arrival == 1){
            linear_btns.setVisibility(View.GONE);
            txtReady.setVisibility(View.GONE);
        }else{
            linear_btns.setVisibility(View.VISIBLE);
            txtReady.setVisibility(View.VISIBLE);
        }
        int customer_track_time = appSharedPreferences.readInteger("customer_track_time");

        deliveredTime.setText(customer_track_time+" "+getString(R.string.minutes));

        btn_time1.setText(customer_track_time+plus_num1+" "+getString(R.string.gettimemin));
        btn_time2.setText(customer_track_time+plus_num2+" "+getString(R.string.gettimemin));
        btn_time3.setText(customer_track_time+plus_num3+" "+getString(R.string.gettimemin));
        btn_time4.setText(customer_track_time+plus_num4+" "+getString(R.string.gettimemin));

        //TODO ////////////////////////////////////end move//////////////////////////////////////////////


        dialog1.setCancelable(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPrepareTime(order_no, customer_track_time);
                Intent intent = new Intent(getContext(), ActivityMain.class).putExtra("fopen", "home");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

                dialog1.dismiss();
            }
        });

        btn_time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPrepareTime(order_no, customer_track_time+plus_num1);
                Intent intent = new Intent(getContext(), ActivityMain.class).putExtra("fopen", "home");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

                dialog1.dismiss();
            }
        });
        btn_time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPrepareTime(order_no, customer_track_time+plus_num2);
                Intent intent = new Intent(getContext(), ActivityMain.class).putExtra("fopen", "home");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

                dialog1.dismiss();
            }
        });
        btn_time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPrepareTime(order_no, customer_track_time+plus_num3);
                Intent intent = new Intent(getContext(), ActivityMain.class).putExtra("fopen", "home");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

                dialog1.dismiss();
            }
        });
        btn_time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPrepareTime(order_no, customer_track_time+plus_num4);
                Intent intent = new Intent(getContext(), ActivityMain.class).putExtra("fopen", "home");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

                dialog1.dismiss();
            }
        });

    }
    private void sendPrepareTime(int order_id, int customer_track_time) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String access_token = appSharedPreferences.readString("access_token");
        Call<SaveItem> call = apiService.sendPrepareTime("application/json", "Bearer "+access_token, order_id, customer_track_time);
        call.enqueue(new Callback<SaveItem>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {

                    Log.d("validateCode", "validateCode");

                    if (response.body().getStatus() == true) {
                        Log.d("response.body().getStatus()", "true");

                    }else {
                        Log.d("response.body().getStatus()", "false");

                        if (MyOrderFragment.this != null) {
//                    Toast.makeText(ActivityChat.this,, Toast.LENGTH_SHORT).show();
                            new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                Log.d("validateCode", "validateCodeFailure");
                if (MyOrderFragment.this != null) {
//                    Toast.makeText(ActivityChat.this,, Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });

    }

    private void getProviderOnOff(){
        token = appSharedPreferences.readString("access_token");
        int p_idS = appSharedPreferences.readInteger("p_id");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProviderOnOff> call = apiService.provider_onoff("application/json","Bearer "+token, p_idS);
        call.enqueue(new Callback<ProviderOnOff>() {
            @Override
            public void onResponse(Call<ProviderOnOff> call, Response<ProviderOnOff> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {

                        if (response.body().getItems().equals("on")){
                            //Toast.makeText(getActivity(), "one : "+item.getId()+" ,two : "+item.id, Toast.LENGTH_SHORT).show();
                            //TODO ////////////////////////////DoneOrderToServer/////////////////////////
                            int delivery_status_id = appSharedPreferences.readInteger("delivery_status_id");
                            DoneOrderToServer(myOrderList, "CASH", p_id, b_id, delivery_status_id);
                        }else{
//                            Toast.makeText(getActivity(), getString(R.string.provider_close), Toast.LENGTH_SHORT).show();
                            new ActivityMain().CustomToast1(getString(R.string.provider_close), getActivity());
                        }

                    }else{
                        Log.d("false", "false_onoff");
//                        Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    }
                }

                else if (response.code()==401){
                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
                    ActivityMain.getInstance().RefreshUserToken(refresh_token,3);

                }
            }

            @Override
            public void onFailure(Call<ProviderOnOff> call, Throwable t) {
                Log.d("onFailure", "onFailure_onoff");
//                Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });
    }
    private void cancelOrder(Button btn_cancel, int order_id) {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.cancelOrder("application/json", "Bearer " + token, order_id);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    boolean success = response.body().getStatus();

                    if (success == true) {
                        btn_cancel.setText(R.string.canceled);
                        Log.d("successt", "successt : "+success+"");
                        Intent intent = new Intent(getContext(), ActivityMain.class).putExtra("fopen", "home");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();

                    } else {
                        // Toast.makeText(context, context.getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain),getActivity());
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                //Toast.makeText(context, context.getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                new ActivityMain().CustomToast1(getString(R.string.tryagain),getActivity());

            }
        });
    }
}
