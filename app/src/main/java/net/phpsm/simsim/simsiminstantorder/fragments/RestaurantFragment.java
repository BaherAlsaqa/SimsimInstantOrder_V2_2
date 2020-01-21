package net.phpsm.simsim.simsiminstantorder.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.GPSTracker;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.activitys.LoginActivity;
import net.phpsm.simsim.simsiminstantorder.adapter.MyAdditionAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.MyProductCategoryAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.PaginationRestaurantAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient1;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener11;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener18;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener5;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener9;
import net.phpsm.simsim.simsiminstantorder.models.Objects.mediaObject;
import net.phpsm.simsim.simsiminstantorder.models.ProductAdditional;
import net.phpsm.simsim.simsiminstantorder.models.ProductCategory;
import net.phpsm.simsim.simsiminstantorder.models.Restaurant;
import net.phpsm.simsim.simsiminstantorder.models.StoreModel;
import net.phpsm.simsim.simsiminstantorder.models.UpOrder;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Delivery_Status;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.NotificationObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.Products;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.RestaurantItemsObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.RestaurantObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.AddProviderFavorit;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.DeliveryStatusList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.ProductsCategoriesList;
import net.phpsm.simsim.simsiminstantorder.models.responses.map.ResultDistanceMatrix;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.GridSpacingItemDecoration;
import net.phpsm.simsim.simsiminstantorder.utils.PaginationScrollListener;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by baher on 18/11/2017.
 */
//BaseActivity
public class RestaurantFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    ArrayList<Products> productsArrayList = new ArrayList<>();
    List<Restaurant> restaurantListCategory = new ArrayList<>();
    RecyclerView mRecyclerView;
    PaginationRestaurantAdapter adapter;
    ImageView favoritAdd;
    int x = 0;
    ArrayList<ProductCategory> productCategoryArrayList = new ArrayList<>();
    ArrayList<Delivery_Status> delivery_statusArrayList = new ArrayList<>();
    RecyclerView mRecyclerViewHorez;
    MyProductCategoryAdapter categoryAdapter;
    String token, refreshToken;
    int p_id;
    int b_id;
    String mobile;
    AppSharedPreferences appSharedPreferences;
    //ImageView coverImage, deliverytype1, deliverytype2, deliverytype3, deliverytype4;
    ImageView image;
    TextView name, cuisine, totalpricetv, status_name;
    RelativeLayout displaytotalprice;
    AVLoadingIndicatorView progressBar;
    MyAdditionAdapter adapter1;
    ArrayList<UpOrder> upOrderArrayList_SP = new ArrayList<>();
    String is_favorite;
    int yesAdditional = 0;
    double price = 0.0;
    double additionalsPrice = 0.0;
    double totalPrice = 0.0;
    TextView productPrice, provider_duration, provider_distance;
    String latLngString;
    Location location;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    double additionalPrice = 0.0;
    GPSTracker gpsTracker;
    ArrayList<UpOrder> cartList = new ArrayList<>();
    /////////////////////////////////////////////////////////////////

    ////////////
    private Timer mTimer;
    //private UpdateCallDurationTask mDurationTask;

    private String mCallId;
    String is_Fav_fromRefresh;
    private TextView mCallDuration;
    private TextView mCallState;
    private TextView mCallerName;

    ////////////////////////////////

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 2;
    private int currentPage = PAGE_START;

    ApiService apiService;
    ApiService apiService1;
    Call<RestaurantObject> call;
    int b_category_id = 1;
    GridLayoutManager gridLayoutManager;
    private OnItemClickListener5 listener1;
    Menu menu;
    ImageView cartImage,notifiImage;
    RelativeLayout cartItemLayout,notificationItemLayout;
    TextView cartItemNumber,notifiItemNumber;
    CircleImageView animation;
    boolean isReturnAnimation=true;
    boolean back=false;
    boolean notifiback=false;
    int StorednotificationCount,ApiNotificationCount;
    int spacing;

    int newNotifiNumber=0;

    ImageView deliveryStatusImage;

    String displaytotalprice_gone;

    Dialog dialog1;
    Dialog dialog2;
    SliderLayout sliderShow;
SwipeRefreshLayout swiperefresh;
    private Handler handler = new Handler();
    Runnable r;
    ////////////////////////////////////////////

    public static RestaurantFragment newInstance() {
        RestaurantFragment fragment = new RestaurantFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_restaurant, container, false);

        setHasOptionsMenu(true);


        mRecyclerView = view.findViewById(R.id.recyclerView);
        sliderShow = view.findViewById(R.id.flipper_layout);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));
        swiperefresh.setOnRefreshListener(this);



      /*  int spanCount = 2; // 2 columns
        if (Locale.getDefault().getLanguage().equals("ar")) {

           spacing = -20  ; // 80px
        }else{
            spacing = 20; // 80px
        }
        boolean includeEdge = true;
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));*/

        favoritAdd = view.findViewById(R.id.favoritadd);
        //coverImage = view.findViewById(R.id.cover_image);
        image = view.findViewById(R.id.image);
        name = view.findViewById(R.id.name);
        cuisine = view.findViewById(R.id.cuisine_value);
        deliveryStatusImage = view.findViewById(R.id.delivery_status_image);
        appSharedPreferences = new AppSharedPreferences(getContext());
        /*deliverytype1 = view.findViewById(R.id.deliverytype1);
        deliverytype2 = view.findViewById(R.id.deliverytype2);
        deliverytype3 = view.findViewById(R.id.deliverytype3);
        deliverytype4 = view.findViewById(R.id.deliverytype4);*/

        //recyclerViewDelivery = view.findViewById(R.id.recyclerViewDelivery);
        mRecyclerViewHorez = view.findViewById(R.id.recyclerViewHorizontal);
        progressBar = view.findViewById(R.id.progress);
        provider_duration = view.findViewById(R.id.provider_duration);
        provider_distance = view.findViewById(R.id.distance_value);
        provider_distance.setText(String.format("%.2f",Double.parseDouble(appSharedPreferences.readString("branch_distance")))+" km");
        displaytotalprice = view.findViewById(R.id.displaytotalprice);
        totalpricetv=view.findViewById(R.id.totalprice);
        status_name = view.findViewById(R.id.status_name);
        gpsTracker = new GPSTracker(getContext());

        /////
      //  if (displaytotalprice.getVisibility() == View.VISIBLE) {
             r = new Runnable() {
                public void run() {
                    String totalPriceS = appSharedPreferences.readString("totalPrice");

                    totalpricetv.setText(getString(R.string.totalprice1) + " " + totalPriceS +" "+ getString(R.string.s_r));

                    handler.postDelayed(this, 100);
                }
            };
            handler.postDelayed(r, 100);
       // }

        ////

        if (appSharedPreferences.readString("displaytotalprice_gone") != null) {
            displaytotalprice_gone = appSharedPreferences.readString("displaytotalprice_gone");
        }
        if ((displaytotalprice_gone != null) & (displaytotalprice_gone.equals("1"))){
            displaytotalprice.setVisibility(View.GONE);
        }
        displaytotalprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), ActivityMainCall.class)
                        .putExtra("fcall", "order"));

                back = true;
            }
        });

        adapter = new PaginationRestaurantAdapter(getContext());

//        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);


        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.hasFixedSize();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.setAdapter(adapter);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            fetchLocation();
        }

        //recyclerViewDelivery.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewHorez.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));

        StorednotificationCount= appSharedPreferences.readInteger("notifi_count");

        //sharedPreferencesGet = getActivity().getSharedPreferences(Signup1.MY_PREFS_NAME, MODE_PRIVATE);
        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");

        p_id = appSharedPreferences.readInteger("p_id");
        b_id = appSharedPreferences.readInteger("b_id");
        is_Fav_fromRefresh = appSharedPreferences.readString("fav");
        if (is_Fav_fromRefresh.equals("1")) {

            addProvidersFav(p_id);
            // favoritAdd.setBackgroundResource(R.drawable.ic_fav1);

        } else if (is_Fav_fromRefresh.equals("0")) {
            removeProvidersFav(p_id);


        }
        String cover_image = appSharedPreferences.readString("cover_image");
        String sImage = appSharedPreferences.readString("image");
        String sName = appSharedPreferences.readString("name");
        String sAddress = appSharedPreferences.readString("address");
        String sCuisine = appSharedPreferences.readString("cuisine");
        is_favorite = appSharedPreferences.readString("is_favorite");


            /*Picasso.with(getContext())
                    .load(cover_image)
                    .fit()
                    .into(coverImage);*/



           List<String> coverImgs= appSharedPreferences.readArraymobileCallCenter("coverImgs");
           for(int i=0;i<coverImgs.size();i++){
               if(coverImgs.get(i)!=null){
                   DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
                   textSliderView.image(coverImgs.get(i)).description(null);
                   textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                       @Override
                       public void onSliderClick(BaseSliderView slider) {
                           ArrayList<mediaObject> imagurls=new ArrayList<>();
                           for(int i=0;i<coverImgs.size();i++){
                               imagurls.add(new mediaObject(0,0,"",coverImgs.get(i)));
                           }
                           ActivityMainCall.getInstance().showUserMedia(imagurls);
                       }
                   });
                   sliderShow.addSlider(textSliderView);
               }
           }








        Picasso.with(getContext())
                .load(sImage)
                .placeholder(R.drawable.place_holder_provider)
                .resize(200, 200)
                .into(image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<mediaObject> imagurl=new ArrayList<>();
                imagurl.add(new mediaObject(0,0,"",sImage)) ;
                ActivityMainCall.getInstance().showUserMedia(imagurl);
            }
        });
        name.setText(sName);
        //address.setText(sAddress);
        cuisine.setText(sCuisine);

        if (is_favorite.equals("true")) {
            favoritAdd.setBackgroundResource(R.drawable.ic_fav1);
            favoritAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (x % 2 == 0) {
                        favoritAdd.setBackgroundResource(R.drawable.ic_fav);

                        addProvidersFav(p_id);

                        x++;
                    } else {
                        favoritAdd.setBackgroundResource(R.drawable.ic_fav1);

                        removeProvidersFav(p_id);

                        x++;
                    }
                }
            });
        } else {
            favoritAdd.setBackgroundResource(R.drawable.ic_fav);
            favoritAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (x % 2 == 0) {
                        favoritAdd.setBackgroundResource(R.drawable.ic_fav1);

                        addProvidersFav(p_id);

                        x++;
                    } else {
                        favoritAdd.setBackgroundResource(R.drawable.ic_fav);

                        removeProvidersFav(p_id);

                        x++;
                    }
                }
            });
        }
        //TODO //////////////////////////////////
        /*adapter = new MyRestaurantAdapter(restaurantList, getContext());
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        getDeliveryStatus();
        //////////////////////////
        getProductsCategories();
        ////////////////////////////////

        apiService = ApiClient.getClient().create(ApiService.class);
        apiService1 = ApiClient1.getClient().create(ApiService.class);

        fetchLocation();
        fetchDistance();

        mRecyclerView.addOnScrollListener(new PaginationScrollListener(gridLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        ///////////////////////////////////////////////////////////////////
        adapter.setOnClickListener(new OnItemClickListener9() {
            @Override
            public void onItemClick(Products products, View v) {

                if (products.getStockout() != null) {
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.Quantity_implemented), getActivity());
                } else {
                    additions_dialog(products);
                }
            }
        });

        //TODO ///////////////////toast login////////////////////

        if(token.equals("")){
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

        return view;
    }

    private void getDeliveryStatus() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<DeliveryStatusList> call = apiService.getDeliveryStatus(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, p_id);
        call.enqueue(new Callback<DeliveryStatusList>() {
            @Override
            public void onResponse(Call<DeliveryStatusList> call, Response<DeliveryStatusList> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {
                        delivery_statusArrayList = response.body().getDelivery_statusArrayList();
                        /*deliveyStatusAdapter = new DeliveyStatusAdapter(delivery_statusArrayList, view.getContext());
                        recyclerViewDelivery.setAdapter(deliveyStatusAdapter);*/
                        Picasso.with(getContext())
                                .load(delivery_statusArrayList.get(0).getUrl_icon())
                                .transform( new ColorTransformation(Color.parseColor("#e01c4b") ) )
                                .resize(200, 200)
                                .into(deliveryStatusImage);
                        status_name.setText(delivery_statusArrayList.get(0).getName());
                        appSharedPreferences.writeInteger("delivery_status_id", delivery_statusArrayList.get(0).getId());
                        appSharedPreferences.writeString("delivery_status_url_icon", delivery_statusArrayList.get(0).getUrl_icon());
                        appSharedPreferences.writeString("dlivery_status_name", delivery_statusArrayList.get(0).getName());
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<DeliveryStatusList> call, Throwable t) {
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });
    }

    private void getProductsCategories() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ProductsCategoriesList> call = apiService.getProductsCategoriesList(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, p_id);
        call.enqueue(new Callback<ProductsCategoriesList>() {
            @Override
            public void onResponse(Call<ProductsCategoriesList> call, Response<ProductsCategoriesList> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("true")) {
                        productCategoryArrayList = response.body().getProductCategoryArrayList();
                        categoryAdapter = new MyProductCategoryAdapter(productCategoryArrayList, view.getContext());
                        mRecyclerViewHorez.setAdapter(categoryAdapter);

                        if (productCategoryArrayList.size() > 0) {
                            b_category_id = productCategoryArrayList.get(0).getId();

                            loadFirstPage();
                        }
                        ////////////////////////////////////

                        categoryAdapter.setOnClickListener(new OnItemClickListener11() {
                            @Override
                            public void onItemClick(ProductCategory item) {

                                isLoading = false;
                                isLastPage = false;
                                currentPage = PAGE_START;
                                b_category_id = item.getId();

                                loadFirstPage();

                            }
                        });
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<ProductsCategoriesList> call, Throwable t) {
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });
    }

    private void additions_dialog(Products products) {

        //   final int[] quantity_X = {0};
        final int[] quant = {1};
        dialog2 = new Dialog(getActivity());
        ArrayList<ProductAdditional> productAdditionalArrayList = new ArrayList<>();
        RecyclerView mRecyclerView1;
        dialog2.show();
        dialog2.setContentView(R.layout.addition_dialog);
        LinearLayout description_layout= dialog2.findViewById(R.id.description_layout);
        LinearLayout addittion_layout= dialog2.findViewById(R.id.addittion_layout);
        Button description= dialog2.findViewById(R.id.description);
        Button add_item=dialog2.findViewById(R.id.add_item);
        View v1= dialog2.findViewById(R.id.v1);
        View v2= dialog2.findViewById(R.id.v2);
        ImageView productImg= dialog2.findViewById(R.id.img_v);
        TextView productName= dialog2.findViewById(R.id.name);
        TextView product_description = dialog2.findViewById(R.id.product_description);
        TextView price= dialog2.findViewById(R.id.price);
        Picasso.with(getContext())
                .load(products.getUrlImageProduct())
                .fit()
                .into(productImg);
        productImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<mediaObject> imagurl=new ArrayList<>();
                imagurl.add(new mediaObject(0,0,"",products.getUrlImageProduct())) ;
                ActivityMainCall.getInstance().showUserMedia(imagurl);
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



        /*CardView card = findViewById(R.id.card);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StateListAnimator stateListAnimator = AnimatorInflater
                    .loadStateListAnimator(this, R.animator.on_touch_cardview1);
            card.setStateListAnimator(stateListAnimator);
        }*/

        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button close = dialog2.findViewById(R.id.close);
        Button saveBtn = dialog2.findViewById(R.id.savebtn);
        TextView product_name = dialog2.findViewById(R.id.product_name);
        String provider_name_value = appSharedPreferences.readString("name");
        //provider_name.setText(provider_name_value);
        product_name.setText(products.getTitle());
       // product_description.setText(products.getDescription());
        ImageButton plus = dialog2.findViewById(R.id.increase);
        ImageButton minus = dialog2.findViewById(R.id.decrease);
        TextView quantity = dialog2.findViewById(R.id.quantity);
        productPrice = dialog2.findViewById(R.id.product_price_value);
        /*productsQuant = dialog2.findViewById(R.id.products_quantity_value);
        totalPriceT = dialog2.findViewById(R.id.total_price_value);*/

        productPrice.setText(products.getPrice()+" "+getString(R.string.s_r));
        //productsQuant.setText(upOrderArrayList_SP.size()+"");
        String totalPriceV = appSharedPreferences.readString("totalPrice");
        //totalPriceT.setText(totalPriceV+" "+getString(R.string.coin));

        mRecyclerView1 = dialog2.findViewById(R.id.recyclerView1);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        productAdditionalArrayList = products.getProductAdditionals();

        adapter1 = new MyAdditionAdapter(productAdditionalArrayList, getActivity());
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
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 0");
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                                    additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 1");
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                                    additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 2");
                                }else{
                                    Log.d("SELECTED", "null");
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
                                    try {
                                        additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_1_price();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 0");
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                                    try {
                                        additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 1");
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                                    try {
                                        additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 2");
                                }else{
                                    Log.d("SELECTED", "null");
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
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 0");
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 1) {
                                    additionalPrice = additionalPrice + finalProductAdditionalArrayList.get(i).getChoice_2_price();
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 1");
                                }
                                else if (finalProductAdditionalArrayList.get(i).getSelected() == 2) {
                                    additionalPrice = additionalPrice + (finalProductAdditionalArrayList.get(i).getChoice_3_price());
                                    Log.d("SELECTED", "finalProductAdditionalArrayList | 2");
                                }else{
                                    Log.d("SELECTED", "null");
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
                        }else {

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
                if (quant[0] > 1) {
                    //TODO/////////////////////////////////////////////////
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
                    productPrice.setText((((products.getPrice() * quant[0]) + additionalPrice * quant[0]) -  (products.getPrice() + additionalPrice)) + " " + getString(R.string.s_r));
                    Log.d("productPrice", ((products.getPrice() * quant[0]) + additionalPrice * quant[0])+"");
                    Log.d("productPrice", (products.getPrice() + additionalPrice)+"");
                    quant[0]--;
                    quantity.setText(quant[0] + "");
                    additionalPrice = 0.0;
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int p_id_cart = appSharedPreferences.readInteger("p_id_cart");
                int p_id = appSharedPreferences.readInteger("p_id");
                String provider_name = appSharedPreferences.readString("name");
                cartList = appSharedPreferences.readArray("uporder");

                if (p_id != p_id_cart & cartList.size() > 0) {
                    Log.d("delete_cart_dialog", cartList.size()+" -");
                    String provider_name_cart = appSharedPreferences.readString("provider_name_cart");
                    delete_cart_dialog(provider_name_cart, provider_name, products, quant[0], p_id);
                }else {

                    add_product_to_cart(products, quant[0], p_id);

                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

    }

    private void showFullImage() {


    }


    public  void  doCartAnimation(){
        cartItemNumber.setVisibility(View.VISIBLE);
        cartItemNumber.setText(appSharedPreferences.readArray("uporder").size()+"");
        final Animation animShake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
        cartItemLayout.startAnimation(animShake);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.call_toolbar, menu);
        this.menu=menu;
        Log.d("safa","ssssss");
        cartItemLayout= (RelativeLayout) menu.findItem(R.id.call_cart).getActionView();
         cartImage = cartItemLayout.findViewById(R.id.img);
        cartItemNumber= cartItemLayout.findViewById(R.id.cart_num);
        cartList= appSharedPreferences.readArray("uporder");
        if(cartList.size()>99){
            cartItemNumber.setVisibility(View.VISIBLE);
            cartItemNumber.setText("99+");
        }else if (cartList.size()==0) {
            cartItemNumber.setVisibility(View.GONE);
        }
        else {
            cartItemNumber.setVisibility(View.VISIBLE);
            cartItemNumber.setText(cartList.size()+"");
        }

        cartItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ActivityMainCall.class)
                        .putExtra("fcall", "order"));
                 back=true;

            }
        });
///2////
        notificationItemLayout= (RelativeLayout) menu.findItem(R.id.notification).getActionView();
        notifiImage = notificationItemLayout.findViewById(R.id.img);
        notifiItemNumber= notificationItemLayout.findViewById(R.id.notif_num);
        if(newNotifiNumber>99){
            notifiItemNumber.setVisibility(View.VISIBLE);
            notifiItemNumber.setText("99+");
        }else if (newNotifiNumber==0) {
            notifiItemNumber.setVisibility(View.INVISIBLE);}
        else {
            notifiItemNumber.setVisibility(View.VISIBLE);
            notifiItemNumber.setText(newNotifiNumber+"");}
        notificationItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityMainCall.class).putExtra("fcall", "notify"));
                notifiback=true;
                notifiItemNumber.setVisibility(View.INVISIBLE);
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.call_cart) {
            //Toast.makeText(getContext(), "cart", Toast.LENGTH_SHORT).show();
            /*android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout_call, MyOrderFragment.newInstance());
            transaction.commit();*/

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addProvidersFav(int p_id) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AddProviderFavorit> call = apiService.addProviderFav(appSharedPreferences.readString("lang"),"application/json", "Bearer " + appSharedPreferences.readString("access_token"), p_id, "add");
        call.enqueue(new Callback<AddProviderFavorit>() {
            @Override
            public void onResponse(Call<AddProviderFavorit> call, Response<AddProviderFavorit> response) {

                if (response.isSuccessful()) {
                    AddProviderFavorit providers = response.body();
                    if (providers.getStatus().equals("true")) {
//                        Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.favorite_add), getActivity());
                        favoritAdd.setBackgroundResource(R.drawable.ic_fav1);
                        appSharedPreferences.writeString("fav", "");


                    }
                } else if (response.code() == 401) {
                    APIError apiError = ErrorUtils.parseError(response);
                    appSharedPreferences.writeString("fav", "1");

//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
                    ActivityMain.getInstance().RefreshUserToken(refreshToken, 2);


                }
            }

            @Override
            public void onFailure(Call<AddProviderFavorit> call, Throwable t) {
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });
    }

    private void removeProvidersFav(int p_id) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<AddProviderFavorit> call = apiService.addProviderFav(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, p_id, "remove");
        call.enqueue(new Callback<AddProviderFavorit>() {
            @Override
            public void onResponse(Call<AddProviderFavorit> call, Response<AddProviderFavorit> response) {

                if (response.isSuccessful()) {
                    AddProviderFavorit providers = response.body();
                    if (providers.getStatus().equals("true")) {
                        favoritAdd.setBackgroundResource(R.drawable.ic_fav);
                        appSharedPreferences.writeString("fav", "");

//                        Toast.makeText(getContext(), , Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.favorite_remove), getActivity());

                    }
                } else if (response.code() == 401) {
                    appSharedPreferences.writeString("fav", "0");

                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
                    ActivityMain.getInstance().RefreshUserToken(refreshToken, 2);
                }
            }

            @Override
            public void onFailure(Call<AddProviderFavorit> call, Throwable t) {
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });
    }

    private void loadFirstPage() {
        Log.e(getTag(), "loadFirstPage: " + currentPage);
        progressBar.setVisibility(View.VISIBLE);
        callTopRatedMoviesApi().enqueue(new Callback<RestaurantObject>() {
            @Override
            public void onResponse(Call<RestaurantObject> call, Response<RestaurantObject> response) {

                Log.d("statusss", response.body().getStatus() + "");
                if (response.body().getStatus().equals("true")) {
                    Log.e(getTag(), "First status : true");
                    productsArrayList.clear();
                    adapter.clear();
                    productsArrayList = response.body().getRestaurantItemsObject().getData();
                    /*for (int i=0; i<homeProvidersList.size(); i++) {
                        deliveryStatusArrayList = homeProvidersList.get(i).getDelivery_Status();
                    }*/
                    TOTAL_PAGES = response.body().getRestaurantItemsObject().getLastPage();
                    //Toast.makeText(getContext(), "total page : "+TOTAL_PAGES, Toast.LENGTH_SHORT).show();
                    productsArrayList = fetchResults(response);
                    adapter.addAll(productsArrayList);
                    if (currentPage < TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;

                    progressBar.setVisibility(View.GONE);
                } else {
                    if (getActivity() != null) {
//                        Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<RestaurantObject> call, Throwable t) {
                t.printStackTrace();
                try {
                    if (getActivity() != null) {
//                        Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                        progressBar.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ArrayList<Products> fetchResults(Response<RestaurantObject> response) {
        RestaurantObject restaurantObject = response.body();
        RestaurantItemsObject restaurantItemsObject = restaurantObject.getRestaurantItemsObject();
        return restaurantItemsObject.getData();
    }

    private void loadNextPage() {

        //Toast.makeText(getContext(), "load next page", Toast.LENGTH_SHORT).show();
        Log.d(getTag(), "loadNextPage: " + currentPage);

        callTopRatedMoviesApi().enqueue(new Callback<RestaurantObject>() {
            @Override
            public void onResponse(Call<RestaurantObject> call, Response<RestaurantObject> response) {

                if (response.body().getStatus().equals("true")) {
                    adapter.removeLoadingFooter();
                    isLoading = false;

                    productsArrayList = fetchResults(response);
                    adapter.addAll(productsArrayList);

                    if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
                    else isLastPage = true;
                } else {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }

            @Override
            public void onFailure(Call<RestaurantObject> call, Throwable t) {
                t.printStackTrace();
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });
    }


    private Call<RestaurantObject> callTopRatedMoviesApi() {
        if (b_id > 0) {
            //Toast.makeText(getActivity(), "is branche", Toast.LENGTH_SHORT).show();
            return call = apiService.getBrancheProducts(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, p_id, b_id, b_category_id, currentPage);
        } else {
            //Toast.makeText(getActivity(), "not branche", Toast.LENGTH_SHORT).show();
            return call = apiService.getProducts(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, p_id, b_category_id, currentPage);
        }
    }

    public void onRowClicked2(Products row, int quantity) {

        String[] total = productPrice.getText().toString().split(" ");

        ArrayList<ProductAdditional> additions = new ArrayList<>();
        if (row.getProductAdditionals().size() > 0) {
            for (int i = 0; i < row.getProductAdditionals().size(); i++) {
                if (row.getProductAdditionals().get(i).getSelected() >= 0) {
                    additions.add(new ProductAdditional(
                            row.getProductAdditionals().get(i).getId(),
                            row.getProductAdditionals().get(i).getName(),
                            row.getProductAdditionals().get(i).getChoice_1(),
                            row.getProductAdditionals().get(i).getChoice_1_price(),
                            row.getProductAdditionals().get(i).getChoice_2(),
                            row.getProductAdditionals().get(i).getChoice_2_price(),
                            row.getProductAdditionals().get(i).getChoice_3(),
                            row.getProductAdditionals().get(i).getChoice_3_price(),
                            row.getProductAdditionals().get(i).getProductId(),
                            row.getProductAdditionals().get(i).getProviderId(),
                            row.getProductAdditionals().get(i).getImage(),
                            row.getProductAdditionals().get(i).getUrlImage(),
                            row.getProductAdditionals().get(i).getSelected()));
                }
            }
        }
        //POSDataFragment fragment = (POSDataFragment) ((AppCompatActivity) context).getSupportFragmentManager().findFragmentById(R.id.fragment);
        //MyOrderFragment fragment = MyOrderFragment.newInstance();
        upOrderArrayList_SP.add(new UpOrder(row.getId(), row.getTitle(), quantity, row.getDescription(), row.getPrice(),Double.valueOf(total[0]), row.getDiscount(), row.getTrial(), row.getCreatedAt(), row.getUpdatedAt(), row.getDeletedAt(), row.getProviderId(), row.getImageProduct(), row.getAmount(), row.getProductCategoryId(), row.getUrlImageProduct(), additions));
        if (appSharedPreferences.readArray("uporder") == null) {
            appSharedPreferences.writeArray("uporder", upOrderArrayList_SP);
        } else {
            ArrayList<UpOrder> upOrder = appSharedPreferences.readArray("uporder");
            upOrder.add(new UpOrder(row.getId(), row.getTitle(), quantity, row.getDescription(), row.getPrice(),Double.valueOf(total[0]), row.getDiscount(), row.getTrial(), row.getCreatedAt(), row.getUpdatedAt(), row.getDeletedAt(), row.getProviderId(), row.getImageProduct(), row.getAmount(), row.getProductCategoryId(), row.getUrlImageProduct(), additions));
            appSharedPreferences.writeArray("uporder", upOrder);
        }

        if (row.getProductAdditionals().size() > 0) {
            for (int i = 0; i < row.getProductAdditionals().size(); i++) {
                if (row.getProductAdditionals().get(i).getSelected() > 0) {
                    if (row.getProductAdditionals().get(i).getSelected() == 1)
                        additionalsPrice = additionalsPrice + row.getProductAdditionals().get(i).getChoice_2_price();
                    else if (row.getProductAdditionals().get(i).getSelected() == 2)
                        additionalsPrice = additionalsPrice + (row.getProductAdditionals().get(i).getChoice_3_price());
                }
                row.getProductAdditionals().get(i).setSelected(0);
                Log.e("SELECTED", "" + row.getProductAdditionals().get(i).getSelected());
                adapter1.notifyDataSetChanged();
            }
        }
        appSharedPreferences.writeString("additionalsPrice", additionalsPrice+"");

        price = price + (additionalsPrice * quantity) + (row.getPrice() * quantity);

        appSharedPreferences.writeString("price", price + "");

        //row.setPriceAdditional(total[0]);

        totalPrice = totalPrice + Double.parseDouble(total[0]);

        //totalPrice = Double.parseDouble(appSharedPreferences.readString("price"));

        appSharedPreferences.writeString("totalPrice", totalPrice+"");
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void fetchLocation() {

                latLngString = gpsTracker.getLatitude() + "," + gpsTracker.getLongitude();
                Log.d("latLngString", latLngString);
    }

    private void fetchDistance() {

        Log.d("fetchDistance", "fetchDistance");
        String provider_lat = appSharedPreferences.readString("provider_lat");
        String provider_long = appSharedPreferences.readString("provider_long");

        String customer_id = appSharedPreferences.readString("customer_id");
        String order_no = appSharedPreferences.readString("order_no");

        Call<ResultDistanceMatrix> call = apiService1.getDistance(latLngString, provider_lat + "," + provider_long);
        call.enqueue(new Callback<ResultDistanceMatrix>() {
            @Override
            public void onResponse(Call<ResultDistanceMatrix> call, Response<ResultDistanceMatrix> response) {

                ResultDistanceMatrix resultDistance = response.body();
                if ("OK".equalsIgnoreCase(resultDistance.status)) {

                    Log.d("OK", "OK");

                    ResultDistanceMatrix.InfoDistanceMatrix infoDistanceMatrix = resultDistance.rows.get(0);
                    ResultDistanceMatrix.InfoDistanceMatrix.DistanceElement distanceElement = infoDistanceMatrix.elements.get(0);
                    if ("OK".equalsIgnoreCase(distanceElement.status)) {

                        Log.d("status", "status");

                        ResultDistanceMatrix.InfoDistanceMatrix.ValueItem itemDuration = distanceElement.duration;
                        ResultDistanceMatrix.InfoDistanceMatrix.ValueItem itemDistance = distanceElement.distance;
                        String totalDistance = String.valueOf(itemDistance.text);
                        String totalDuration = String.valueOf(itemDuration.text);
                        if(getActivity()!=null)
                        provider_duration.setText(totalDuration+" "+getActivity().getResources().getString(R.string.bycar));
                      //  provider_distance.setText(totalDistance);

                        //Toast.makeText(TrackingService.this, "totalDistance"+totalDistance+" totalDuration"+totalDuration, Toast.LENGTH_SHORT).show();


						/*UpdateDurationListener updateDurationListener = (UpdateDurationListener)getApplicationContext();
						updateDurationListener.filldurationTextview(totalDuration);*/
                        //new MyDialog().filldurationTextview(totalDuration);

                        new StoreModel(totalDistance, totalDuration);

                    }else if ("ZERO_RESULTS".equalsIgnoreCase(distanceElement.status)){
                        try {
                            //Toast.makeText(getActivity(), getString(R.string.no_path), Toast.LENGTH_SHORT).show();
                            provider_duration.setText(R.string.no_path);
                            //provider_distance.setText(R.string.no_path);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        Log.d("elsestatus", "elsestatus");
                    }
                }else
                    Log.d("elseOK", "elseOK");

            }

            @Override
            public void onFailure(Call<ResultDistanceMatrix> call, Throwable t) {
                call.cancel();
            }
        });

    }
    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please Turn ON your GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(back){
            refreshCart();}
        else if(notifiback){
            refreshNotify();
        }
    }

    public void refreshCart(){
        //cartItemLayout= (RelativeLayout) menu.findItem(R.id.call_cart).getActionView();

      //  cartImage = cartItemLayout.findViewById(R.id.img);
       // cartItemNumber= cartItemLayout.findViewById(R.id.cart_num);
    cartList= appSharedPreferences.readArray("uporder");
    if(cartList.size()>99){
        cartItemNumber.setVisibility(View.VISIBLE);
        cartItemNumber.setText("99+");
    }else if (cartList.size()==0) {
        cartItemNumber.setVisibility(View.GONE);
    }
    else {
        cartItemNumber.setVisibility(View.VISIBLE);
        cartItemNumber.setText(cartList.size()+"");

    }

}
    private void GetNotificationsCount(int notifPageId1) {
        Log.d("pageidd", notifPageId1 +"");
        token = appSharedPreferences.readString("access_token");
        refreshToken=appSharedPreferences.readString("refresh_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<NotificationObject> call = apiService.getNotification(appSharedPreferences.readString("lang"),"application/json","api/v1/notifications?page="+notifPageId1,"Bearer "+token);
        call.enqueue(new Callback<NotificationObject>() {
            @Override
            public void onResponse(Call<NotificationObject> call, Response<NotificationObject> response) {
                if (response.isSuccessful()) {
                    ApiNotificationCount=response.body().getNotificationItemsObject().getTotal();
                    if(ApiNotificationCount==StorednotificationCount){

                    }else {
                        newNotifiNumber=ApiNotificationCount-StorednotificationCount;
                        // badge.setVisibility(View.VISIBLE);
                    }
                    // Log.d("nextPage",nextPageNotification);


                }
                else if (response.code()==401){
                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();
                    //new ActivityMain().CustomToast1("Message is : " + apiError.getMessage(), getActivity());
                    ActivityMain.getInstance().RefreshUserToken(refreshToken,5);

                }
            }
            @Override
            public void onFailure(Call<NotificationObject> call, Throwable t) {
                if (getActivity() != null){
//                    Toast.makeText(getActivity(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                }
            }
        });

    }

    public void refreshNotify(){
        //cartItemLayout= (RelativeLayout) menu.findItem(R.id.call_cart).getActionView();

        //  cartImage = cartItemLayout.findViewById(R.id.img);
        // cartItemNumber= cartItemLayout.findViewById(R.id.cart_num);
        GetNotificationsCount(1);
        if(newNotifiNumber>99){
            notifiItemNumber.setVisibility(View.VISIBLE);
            notifiItemNumber.setText("99+");
        }else if (newNotifiNumber==0) {
            notifiItemNumber.setVisibility(View.GONE);
        }
        else {
            notifiItemNumber.setVisibility(View.VISIBLE);
            notifiItemNumber.setText(newNotifiNumber+"");

        }

    }

    private void delete_cart_dialog(String providerNameCart, String provider_name, Products products, int quant1, int p_id) {

        dialog1 = new Dialog(getActivity());
        dialog1.show();
        dialog1.setContentView(R.layout.confirm_delete_cart_dialog);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);
        Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        TextView cart_provider = dialog1.findViewById(R.id.cart_provider);

        Log.d("providerNameCart", "providerNameCart : "+providerNameCart);
       // cart_provider.setText(getResources().getString(R.string.delete_cart_details)+" "+providerNameCart);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList arrayList = new ArrayList();
                appSharedPreferences.writeArray("uporder", arrayList);
                add_product_to_cart(products, quant1, p_id);
                appSharedPreferences.writeString("provider_name_cart", provider_name);
                dialog1.dismiss();
            }
        });
    }

    private void add_product_to_cart(Products products, int quant1, int p_id) {

        Log.d("delete_cart_dialog", "+");
        appSharedPreferences.writeInteger("p_id_cart", p_id);
        onRowClicked2(products, quant1);
        displaytotalprice.setVisibility(View.VISIBLE);
        appSharedPreferences.writeString("totalPrice", totalPrice + "");
        String totalPriceS = appSharedPreferences.readString("totalPrice");
        totalpricetv.setText(getString(R.string.totalprice1) + " " + totalPriceS + " "+ getString(R.string.s_r));

        Animation anim = new AlphaAnimation(1.0f, 0.3f);
        anim.setDuration(1000); //You can manage the blinking time with this parameter
        anim.setStartOffset(30);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        displaytotalprice.startAnimation(anim);
        //Toast.makeText(getContext(), "Total price : "+totalPrice, Toast.LENGTH_SHORT).show();
        dialog2.dismiss();

        ////// my code ///////////
        //((ActivityMainCall)getActivity()).resetAnimation();

        ((ActivityMainCall) getActivity()).doAnimations(products.getUrlImageProduct());

        //  ((ActivityMainCall)getActivity()).resetAnimation();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gpsTracker.stopUsingGPS();
        handler.removeCallbacks(r);
    }

    public void setDisplaytotalprice_gone(){
        displaytotalprice.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        ((ActivityMainCall)getActivity()).reOpenRes();
        swiperefresh.setRefreshing(false);
    }


    public void cancelRunable() {
        Log.d("sss","ssssssss");
        handler.removeCallbacks(r);

    }
}
