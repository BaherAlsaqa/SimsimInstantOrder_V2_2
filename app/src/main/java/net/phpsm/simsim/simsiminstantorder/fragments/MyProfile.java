package net.phpsm.simsim.simsiminstantorder.fragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kennyc.bottomsheet.BottomSheet;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMainCall;
import net.phpsm.simsim.simsiminstantorder.activitys.Map2Activity;
import net.phpsm.simsim.simsiminstantorder.activitys.MapActivity;
import net.phpsm.simsim.simsiminstantorder.activitys.Signup1;
import net.phpsm.simsim.simsiminstantorder.activitys.Signup2;
import net.phpsm.simsim.simsiminstantorder.adapter.CarBrandsAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.CarColorAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.SpinAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener13;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener16;
import net.phpsm.simsim.simsiminstantorder.models.Objects.mediaObject;
import net.phpsm.simsim.simsiminstantorder.models.UpOrder;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Avatar;
import net.phpsm.simsim.simsiminstantorder.models.responses.Car;
import net.phpsm.simsim.simsiminstantorder.models.responses.CarBrands;
import net.phpsm.simsim.simsiminstantorder.models.responses.CarColors;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.CustomerObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.PProfileObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.UpdateCustomer;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.CarBrandList;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.CarColorList;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.LocaleHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class MyProfile extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static MyProfile newInstance() {
        MyProfile fragment = new MyProfile();
        return fragment;
    }
    private View view;
    ImageView edit, carImage;
    ImageView color;
    CircleImageView image, lastrestimage, lastOrderimage;
    TextView carBrands;
    TextView imageEdit, mobile, email, brandValue, nameValue, carName, colorValue, res_name, order_name, locationvalue;
    EditText emailE, nameE, locationedittext;
    Button update, locationButton;
    Drawable drawable;
    int x = 0;
    int brand_id, brand_id_selection;
    CustomerObject customerObject;
    //ArrayList<CustomerObject> profileListArrayObjectFill = new ArrayList<>();
    ArrayList<Car> carArrayList = new ArrayList<>();
    ArrayList<Car> carArrayListFill = new ArrayList<>();
    ArrayList<CarColors> colorsResponsList = new ArrayList<>();
    ArrayList<CarColors> colorsResponsListFill = new ArrayList<>();
    ArrayList<CarBrands> brandsResponsList = new ArrayList<>();
    ArrayList<CarBrands> brandsResponsListFill = new ArrayList<>();
    RadioButton car1, car2, car3, car4, car5;
    String cartypeS = "", carcolorS = "", carbrandStxt = "";
    int carcolorId, carbrandSId;
    int a, b, c, d, e = 0;
    AVLoadingIndicatorView progressBar;
    AVLoadingIndicatorView progressBarDialog;
    RecyclerView mRecyclerView;
    CarColorAdapter adapter;
    Dialog dialog;
    String token, refreshToken;
    boolean isRefreshing = false;
    InputStream is;
    private static final int PICK_IMAGE_REQUEST = 234;
    Uri file_path;
    ArrayList<UpOrder> myOrderList = new ArrayList<>();
    ////////////////
    public static final String TAG = MyProfile.class.getSimpleName();
    private static final int INTENT_REQUEST_CODE = 100;

    private Button mBtImageSelect;
    private Button mBtImageShow;
    private ProgressBar mProgressBar;
    private String mImageUrl = "";
    private ProgressBar progressBarImage;
    AppSharedPreferences appSharedPreferences;
    RadioGroup carRadioGroup;
    Dialog brandDialog;
    RecyclerView carBrandsRecyclerView;
    AVLoadingIndicatorView brandprogressBarDialog;
    CarBrandsAdapter carBrandsadapter;
    View line1, line2;
    RelativeLayout relative_data;
    ///////////////////
    // 0 getprofile 1 update customer 2 upload image;
    String whichMethod;
    SwipeRefreshLayout swiperefresh;
    TextView langtv,canceltv,arabic,english;
    View lang_sheet;
    BottomSheet bottomSheet;
    SwitchCompat Mute_notificationSwitch,displayPurchasesSwitch;
    String sImage,sName,location;
    RelativeLayout settings_layout;
    int Privac=0;
   int firstCheck=0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_profile, container, false);
        initComponent();

        if(appSharedPreferences.readInteger("mute")==1){
            Mute_notificationSwitch.setChecked(true);
        }
        Mute_notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    appSharedPreferences.writeInteger("mute",1);
                }else {
                    appSharedPreferences.writeInteger("mute",0);

                }
            }
        });

        displayPurchasesSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Privac=0;
                }else {
                    Privac=1;
                }
                if(firstCheck!=0)
                update_customer();

                firstCheck=1;

            }
        });
        langtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet();
            }
        });
        swiperefresh.setColorSchemeColors(getActivity().getResources().getColor(R.color.colorPrimary));
        swiperefresh.setOnRefreshListener(this);

        if (!sImage.equals("")) {
            Picasso.with(getActivity()).load(sImage).into(lastrestimage);
        }
        if (!sName.equals("")) {
            res_name.setText(sName);

        }
        if (!location.equals("")) {
            locationvalue.setText(location);
        }
        if (myOrderList.size() > 0) {
            if (myOrderList.get(0).getTitle() != null) {
                order_name.setText(myOrderList.get(0).getTitle());
            }
            if (myOrderList.get(0).getUrlImageProduct()!=null) {
                if (!myOrderList.get(0).getUrlImageProduct().equals(""))
                    Picasso.with(getActivity()).load(myOrderList.get(0).getUrlImageProduct()).into(lastOrderimage);
            }
        }
        locationButton.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin1);
                    locationButton.setTextColor(getResources().getColor(R.color.recycler_color));
                    locationButton.setBackgroundDrawable(drawable);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin);
                    locationButton.setTextColor(getResources().getColor(R.color.recycler_color));
                    locationButton.setBackgroundDrawable(drawable);
                }
                return false;
            }
        });

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Map2Activity.class).putExtra("profile", "is"));

            }
        });
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chooseFile();
                // initViews();
                Log.d("ssss", "sssss");
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), INTENT_REQUEST_CODE);
            }
        });
        carBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car_brands_dialog();
            }
        });
        if (WifiReciver.conn != "") {
            if (WifiReciver.conn == "1") {
                progressBar.setVisibility(View.VISIBLE);
                getprofile();
                carbrands();
            } else if (WifiReciver.conn == "0") {
                new ActivityMain().CustomToast1(getString(R.string.connection_message), getActivity());
                progressBar.setVisibility(View.GONE);
            }
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x % 2 == 0) {
                    edit.setBackgroundResource(R.drawable.ic_edit11);
                    email.setVisibility(View.GONE);
                    locationvalue.setVisibility(View.GONE);
                    brandValue.setVisibility(View.GONE);
                    carImage.setVisibility(View.GONE);
                    carName.setVisibility(View.GONE);
                    nameValue.setVisibility(View.GONE);
                    emailE.setVisibility(View.VISIBLE);
                    locationButton.setVisibility(View.VISIBLE);
                    carBrands.setVisibility(View.VISIBLE);
                    nameE.setVisibility(View.VISIBLE);
                    carRadioGroup.setVisibility(View.VISIBLE);
                    update.setVisibility(View.VISIBLE);
                    line2.setVisibility(View.VISIBLE);
                    settings_layout.setVisibility(View.GONE);
                    x++;
                    color.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            color_dialog();
                        }
                    });

                    /////
                } else {
                    edit.setBackgroundResource(R.drawable.ic_edit1);
                    email.setVisibility(View.VISIBLE);
                    brandValue.setVisibility(View.VISIBLE);
                    carImage.setVisibility(View.VISIBLE);
                    carName.setVisibility(View.VISIBLE);
                    nameValue.setVisibility(View.VISIBLE);
                    locationvalue.setVisibility(View.VISIBLE);
                    emailE.setVisibility(View.GONE);
                    carBrands.setVisibility(View.GONE);
                    locationButton.setVisibility(View.GONE);
                    nameE.setVisibility(View.GONE);
                    carRadioGroup.setVisibility(View.GONE);
                    update.setVisibility(View.GONE);
                    settings_layout.setVisibility(View.VISIBLE);
                    x++;
                }
            }
        });

        update.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin1);
                    update.setTextColor(getResources().getColor(R.color.recycler_color));
                    update.setBackgroundDrawable(drawable);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin);
                    update.setTextColor(getResources().getColor(R.color.recycler_color));
                    update.setBackgroundDrawable(drawable);
                }
                return false;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_customer();
            }
        });

        carRadioGroup = view.findViewById(R.id.linear_carstypes);
        carRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.car1:
                        cartypeS = "suv";
                        break;
                    case R.id.car2:
                        cartypeS = "sedan";
                        break;
                    case R.id.car3:
                        cartypeS = "sport";
                        break;
                    case R.id.car4:
                        cartypeS = "truck";
                        break;
                    case R.id.car5:
                        cartypeS = "van";
                        break;

                }
            }
        });
        whichMethod = appSharedPreferences.readString("whichMethod");
        if (whichMethod.equals("0")) {
            appSharedPreferences.writeString("whichMethod", "");
            getprofile();
        } else if (whichMethod.equals("1")) {
            update_customer();
        } else if (whichMethod.equals("2")) {
            appSharedPreferences.writeString("whichMethod", "");
            if (getActivity() != null) {
                new ActivityMain().CustomToast1(getString(R.string.imgUploadFailed), getActivity());
            }
        }
        return view;
    }

    private void initComponent() {
        appSharedPreferences = new AppSharedPreferences(getContext());
         sImage = appSharedPreferences.readString("image");
         sName = appSharedPreferences.readString("name");
         location = appSharedPreferences.readString("location");
        Log.d("loc2", location + "");
        myOrderList = appSharedPreferences.readArray("uporder");
        ////
        settings_layout=view.findViewById(R.id.settings_layout);
        locationvalue = view.findViewById(R.id.locationvalue);
        swiperefresh=view.findViewById(R.id.swiperefresh);
        langtv=view.findViewById(R.id.lang);
        Mute_notificationSwitch=view.findViewById(R.id.Mute_notificationSwitch);
        displayPurchasesSwitch=view.findViewById(R.id.displayPurchasesSwitch);
        brandDialog = new Dialog(getActivity());
        brandDialog.setContentView(R.layout.car_brands_dialog);
        carBrandsRecyclerView = brandDialog.findViewById(R.id.recyclerViewDialog);
        brandprogressBarDialog = brandDialog.findViewById(R.id.progress);

        lastrestimage = view.findViewById(R.id.lastrestimage);
        lastOrderimage = view.findViewById(R.id.lastOrderimage);
        order_name = view.findViewById(R.id.ordervalue);
        res_name = view.findViewById(R.id.res_name);
        line1 = view.findViewById(R.id.line1);
        relative_data = view.findViewById(R.id.relative_data);
        car1 = view.findViewById(R.id.car1);
        car2 = view.findViewById(R.id.car2);
        car3 = view.findViewById(R.id.car3);
        car4 = view.findViewById(R.id.car4);
        car5 = view.findViewById(R.id.car5);
        line2 = view.findViewById(R.id.line2);
        edit = view.findViewById(R.id.edit);
        carImage = view.findViewById(R.id.carimage);
        carName = view.findViewById(R.id.carname);
        color = view.findViewById(R.id.color);
        mobile = view.findViewById(R.id.mobilevalue);
        email = view.findViewById(R.id.emailvalue);
        brandValue = view.findViewById(R.id.brandvalue);
        emailE = view.findViewById(R.id.emailedittext);
        locationButton = view.findViewById(R.id.locationBtn);
        carBrands = view.findViewById(R.id.carbrandspin);
        update = view.findViewById(R.id.btnupdate);
        image = view.findViewById(R.id.image);
        colorValue = view.findViewById(R.id.colorvalue);
        nameValue = view.findViewById(R.id.namevalue);
        nameE = view.findViewById(R.id.nameedittext);
        progressBar = view.findViewById(R.id.progress);
        imageEdit = view.findViewById(R.id.imageedit);
        progressBarImage = view.findViewById(R.id.progress_image);
        customerObject = new CustomerObject();
        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");
    }

    private void getprofile() {
        token = appSharedPreferences.readString("access_token");
        refreshToken = appSharedPreferences.readString("refresh_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<PProfileObject> call = apiService.getCustomer(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token);
        call.enqueue(new Callback<PProfileObject>() {
            @Override
            public void onResponse(Call<PProfileObject> call, Response<PProfileObject> response) {

                if (response.isSuccessful()) {
                    try {
                        PProfileObject pProfileObject = response.body();
                        if (pProfileObject.getStatus().equals("true")) {

                            //Toast.makeText(getContext(), "Response true", Toast.LENGTH_SHORT).show();

                            customerObject = pProfileObject.getCustomerObject();

                            String imageS = customerObject.getUrl_image();
                            if(customerObject.getPrivac()==0){
                                displayPurchasesSwitch.setChecked(true);
                            }else {
                                displayPurchasesSwitch.setChecked(false);
                            }

                            if (imageS != null) {
                                appSharedPreferences.writeString("user_Image", imageS);
                                Picasso.with(getContext())
                                        .load(imageS)
                                        .centerCrop()
                                        .resize(200, 200)
                                        .placeholder(R.drawable.place_holder_user)
                                        .error(R.drawable.ic_error_message)
                                        .into(image, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {
                                                progressBarImage.setVisibility(View.GONE);
                                            }

                                            @Override
                                            public void onError() {
                                                progressBarImage.setVisibility(View.GONE);
                                            }
                                        });

                                image.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        ArrayList<mediaObject> imagurl=new ArrayList<>();
                                        imagurl.add(new mediaObject(0,0,"",imageS)) ;
                                        ActivityMainCall.getInstance().showUserMedia(imagurl);
                                    }
                                });
                            } else {

                            }
                            carcolorId=customerObject.getCar().getCarColors().getId();
                            //carbrandSId=customerObject.getCar().getBrand().getId();
                            mobile.setText(customerObject.getMobile());
                            email.setText(customerObject.getEmail());
                            emailE.setText(customerObject.getEmail());
                            nameValue.setText(customerObject.getName());
                            nameE.setText(customerObject.getName());
                            locationvalue.setText(customerObject.getAddress());
                            // appSharedPreferences.writeString("user_followers", customerObject.getFollowers() + "");
                            // appSharedPreferences.writeString("user_following", customerObject.getFollowing() + "");
                            appSharedPreferences.writeString("username", customerObject.getName() + "");
                            (ActivityMain.getInstance()).setUserName(customerObject.getName());
                            //    numberfollowers.setText(customerObject.getFollowers() + "");
                            //     numberfollowing.setText(customerObject.getFollowing() + "");

                            Resources res = getResources();
                            String mDrawableName = customerObject.getCar().getCar_type();
                            Log.d("mDrawableName",mDrawableName);
                            int resID = res.getIdentifier(mDrawableName, "drawable", getActivity().getPackageName());
                            Drawable drawable = res.getDrawable(resID);

                            switch (mDrawableName) {
                                case "suv":
                                    car1.setChecked(true);
                                    break;
                                case "sedan":
                                    car2.setChecked(true);
                                    break;
                                case "sport":
                                    car3.setChecked(true);
                                    break;
                                case "truck":
                                    car4.setChecked(true);
                                    break;
                                case "van":
                                    car5.setChecked(true);
                                    break;

                            }
                            carImage.setVisibility(View.VISIBLE);
                            carImage.setImageDrawable(drawable);
                            carName.setText(customerObject.getCar().getCar_type());
                            color.getDrawable().setColorFilter(Color.parseColor(customerObject.getCar().getCarColors().getColor_code()), PorterDuff.Mode.MULTIPLY);
                            colorValue.setText(customerObject.getCar().getCarColors().getColor_name());

                            // modelValue.setText(customerObject.getCar().getModel());
                            // carModelE.setText(customerObject.getCar().getModel());
                            carbrandSId=customerObject.getCar().getBrand().getId();
                            brandValue.setText(customerObject.getCar().getBrand().getName());

                            brand_id = customerObject.getCar().getBrand().getId();

                            progressBar.setVisibility(View.GONE);
                            /*customerObject.setId(profileListArrayList.get(i).getId());
                            customerObject.setName(profileListArrayList.get(i).getName());
                            customerObject.setEmail(profileListArrayList.get(i).getEmail());
                            customerObject.setMobile(profileListArrayList.get(i).getMobile());
                            customerObject.setImage(profileListArrayList.get(i).getImage());
                            customerObject.setFollowers(profileListArrayList.get(i).getFollowers());
                            customerObject.setFollowing(profileListArrayList.get(i).getFollowing());
                            carArrayList = profileListArrayList.get(i).getCarArrayList();
                            profileListArrayObjectFill.add(customerObject);*/
                            //////////////
                            /*car.setId(carArrayList.get(j).getId());
                             car.setCar_type(carArrayList.get(j).getCar_type());
                             car.setColor(carArrayList.get(j).getColor());
                             car.setBrand_id(carArrayList.get(j).getBrand_id());
                             car.setModel(carArrayList.get(j).getModel());
                             carArrayListFill.add(car);*/


                        }

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    appSharedPreferences.writeString("whichMethod", "");

                } else if (response.code() == 401) {
                    isRefreshing = true;
                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss", "ssss");
                    appSharedPreferences.writeString("whichMethod", "0");
                    //  Toast.makeText(getActivity(), "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    ((ActivityMain) getContext()).RefreshUserToken(refreshToken, R.id.profile);

                }
            }

            @Override
            public void onFailure(Call<PProfileObject> call, Throwable t) {
                //Toast.makeText(getContext(), "Error : " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    progressBar.setVisibility(View.GONE);
                    progressBarImage.setVisibility(View.GONE);

                }
            }
        });
    }

    private void car_brands_dialog() {

        brandDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        brandDialog.setCancelable(false);
        brandDialog.setTitle("");
        carBrandsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        Button close = brandDialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brandDialog.dismiss();
            }
        });
        carbrands();
        brandDialog.show();

    }

    private void carbrands() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CarBrandList> call = apiService.carbrands(appSharedPreferences.readString("lang"));
        call.enqueue(new Callback<CarBrandList>() {
            @Override
            public void onResponse(Call<CarBrandList> call, Response<CarBrandList> response) {
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getStatus().equals("true")) {
                            brandsResponsListFill.clear();
                            brandsResponsList = response.body().getCarBrandsArrayList();
                        }
                        carBrandsadapter = new CarBrandsAdapter(brandsResponsList, getActivity());
                        carBrandsRecyclerView.setAdapter(carBrandsadapter);
                        carBrandsadapter.notifyDataSetChanged();
                        brandprogressBarDialog.setVisibility(View.GONE);
                        carBrandsadapter.setOnClickListener(new OnItemClickListener16() {
                            @Override
                            public void onItemClick(CarBrands item) {
                                brandDialog.dismiss();
                                carbrandStxt = item.getName();
                                carbrandSId = item.getId();
                                brandValue.setText(carbrandStxt);
                                carBrands.setText(carbrandStxt);
                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<CarBrandList> call, Throwable t) {
                // Toast.makeText(getActivity(), "Error : " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    progressBar.setVisibility(View.GONE);
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
        if (conn == 0)
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

    public void color_dialog() {

        dialog = new Dialog(getContext());
        dialog.show();
        dialog.setContentView(R.layout.color_dialog);
        ///
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setTitle(getString(R.string.select_color));
        mRecyclerView = dialog.findViewById(R.id.recyclerViewDialog);
        progressBarDialog = dialog.findViewById(R.id.progress);
        Button btnclose = dialog.findViewById(R.id.close);
        btnclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));

        carcolor();

    }

    private void carcolor() {
        progressBarDialog.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CarColorList> call = apiService.carcolors(appSharedPreferences.readString("lang"));
        call.enqueue(new Callback<CarColorList>() {
            @Override
            public void onResponse(Call<CarColorList> call, Response<CarColorList> response) {

                if (response.isSuccessful()) {
                    try {
                        if (response.body().getStatus().equals("true")) {
                            colorsResponsListFill.clear();
                            colorsResponsList = response.body().getCarColorsArrayList();
                        }
                        adapter = new CarColorAdapter(colorsResponsList, getContext());
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressBarDialog.setVisibility(View.GONE);

                        adapter.setOnClickListener(new OnItemClickListener13() {
                            @Override
                            public void onItemClick(CarColors item) {
                                dialog.dismiss();
                                carcolorS = item.getColor_name();
                                colorValue.setText(carcolorS);
                                carcolorId = item.getId();
                                color.getDrawable().setColorFilter(Color.parseColor(item.getColor_code()), PorterDuff.Mode.MULTIPLY);
                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        progressBarDialog.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<CarColorList> call, Throwable t) {
                //Toast.makeText(getContext(), "Error : " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                if (getActivity() != null) {
//                    Toast.makeText(getContext(), getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    progressBarDialog.setVisibility(View.GONE);
                }
            }
        });
    }

    private void update_customer() {
        progressBar.setVisibility(View.VISIBLE);

        String name = nameE.getText().toString();
        String email1 = emailE.getText().toString();
        final String cartype = cartypeS;
        int carcolor = carcolorId;
        int carbrand = carbrandSId;
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String address = appSharedPreferences.readString("location");
        String lat = appSharedPreferences.readString("latitude");
        String longitude = appSharedPreferences.readString("longitude");
        Log.d("brand", carbrandSId + "");
        token = appSharedPreferences.readString("access_token");

        Call<UpdateCustomer> call = apiService.updateCustomer(appSharedPreferences.readString("lang"),"application/json", "Bearer " + token, name, email1, lat, longitude, carcolor, address, carbrand, cartype,Privac);
        call.enqueue(new Callback<UpdateCustomer>() {
            @Override
            public void onResponse(Call<UpdateCustomer> call, Response<UpdateCustomer> response) {

                if (response.code() == 200) {
                        UpdateCustomer updateCustomer = new UpdateCustomer();
                        updateCustomer = response.body();
                        if (updateCustomer.getStatus().equals("true")) {
                            Log.d("bbbb", "200");
                            progressBar.setVisibility(View.GONE);
                            new ActivityMain().CustomToast1(getString(R.string.updatecomplete), getActivity());
                            appSharedPreferences.writeString("username", name);
                            email.setVisibility(View.VISIBLE);
                            brandValue.setVisibility(View.VISIBLE);
                            nameValue.setVisibility(View.VISIBLE);
                            carImage.setVisibility(View.VISIBLE);
                            carName.setVisibility(View.VISIBLE);
                            emailE.setVisibility(View.GONE);
                            carBrands.setVisibility(View.GONE);
                            nameE.setVisibility(View.GONE);
                            carRadioGroup.setVisibility(View.GONE);
                            locationButton.setVisibility(View.GONE);
                            locationvalue.setVisibility(View.VISIBLE);

                            getprofile();
                            update.setVisibility(View.GONE);
                            settings_layout.setVisibility(View.VISIBLE);
                            edit.setBackgroundResource(R.drawable.ic_edit1);
                            appSharedPreferences.writeString("whichMethod", "");
                        }else{
                            Log.d("bbbb", "false");
                        }

                }else if (response.code() == 401) {
                    isRefreshing = true;
                    APIError apiError = ErrorUtils.parseError(response);
                    progressBar.setVisibility(View.GONE);
                    Log.d("bbbb", "401");
                    appSharedPreferences.writeString("whichMethod", "1");

                    ((ActivityMain) getContext()).RefreshUserToken(refreshToken, R.id.profile);
                } if (response.code() == 500){
                    Log.d("bbbb", "500");
                    progressBar.setVisibility(View.GONE);
                }else {
                    Log.d("bbbb", "other code");
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UpdateCustomer> call, Throwable t) {
                if (getActivity() != null) {
                    Log.d("bbbb", "onFailure");
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_REQUEST_CODE) {
            Log.d("ssss", "sssss2");

            if (resultCode == RESULT_OK) {

                try {
                    Log.d("ssss", "sssss3");
                    is = getActivity().getContentResolver().openInputStream(data.getData());
                    uploadImage(convertImageToByte(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public byte[] convertImageToByte(InputStream is) throws IOException {
        byte[] data = null;
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        data = baos.toByteArray();

        return data;
    }


    private void uploadImage(byte[] imageBytes) {

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
        Call<Avatar> call = apiService.uploadImage(appSharedPreferences.readString("lang"),"application/json", "Bearer " + appSharedPreferences.readString("access_token"), body);
        progressBarImage.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Avatar>() {
            @Override
            public void onResponse(Call<Avatar> call, Response<Avatar> response) {

                if (response.isSuccessful()) {
                    Avatar responseBody = response.body();
                    if (responseBody.getStatus().equals("true")) {
                        String url_new_image = responseBody.getUrl_image();
                        Log.d("url_new_image", url_new_image);
                        ///
                        appSharedPreferences.writeString("user_Image", url_new_image);
                        Picasso.with(getContext())
                                .load(responseBody.getUrl_image())
                                .centerCrop()
                                .resize(200, 200)
                                .placeholder(R.drawable.place_holder_user)
                                .error(R.drawable.ic_error_message)
                                .into(image, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressBarImage.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {
                                        progressBarImage.setVisibility(View.GONE);
                                    }
                                });
                            (ActivityMain.getInstance()).setHeaderDrawerImg(responseBody.getUrl_image());
                        new ActivityMain().CustomToast1(getString(R.string.uploadedSuccess), getActivity());
                        progressBarImage.setVisibility(View.GONE);

                    } else {
                        new ActivityMain().CustomToast1(getString(R.string.uploaderror), getActivity());
                        progressBarImage.setVisibility(View.GONE);
                    }
                    appSharedPreferences.writeString("whichMethod", "");

                } else if (response.code() == 401) {
                    isRefreshing = true;
                    Log.d("ssss", "ssss");
                    appSharedPreferences.writeString("whichMethod", "2");
                    ((ActivityMain) getContext()).RefreshUserToken(refreshToken, R.id.profile);

                }
            }

            @Override
            public void onFailure(Call<Avatar> call, Throwable t) {
                if (getActivity() != null) {
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), getActivity());
                    progressBarImage.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getprofile();
        swiperefresh.setRefreshing(false);
    }
    public void BottomSheet() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        lang_sheet = inflater.inflate(R.layout.language_sheet, null);
        bottomSheet = new BottomSheet.Builder(getContext())
                .setView(lang_sheet).create();
        bottomSheet.setCancelable(true);

        canceltv =  lang_sheet.findViewById(R.id.cancel);
        arabic =  lang_sheet.findViewById(R.id.arabic);
        english =  lang_sheet.findViewById(R.id.english);


        canceltv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheet.dismiss();
            }
        });
        if(appSharedPreferences.readString("lang").equals("ar")){
            arabic.setTextColor(getContext().getResources().getColor(R.color.green));
        }else {
            english.setTextColor(getContext().getResources().getColor(R.color.green));

        }
        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(appSharedPreferences.readString("lang").equals("ar")){
                    bottomSheet.dismiss();
                }else {
                Resources res = getContext().getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.setLocale(new Locale("ar".toLowerCase())); // API 17+ only.
                // Use conf.locale = new Locale(...) if targeting lower versions
                res.updateConfiguration(conf, dm);
                //It is required to recreate the activity to reflect the change in UI.\
                ((ActivityMainCall)getContext()).finish();
                startActivity(new Intent(getContext(), ActivityMainCall.class).putExtra("fcall", "profile"));
                appSharedPreferences.writeString("lang","ar");
                appSharedPreferences.writeInteger("lang_flag",1);
                bottomSheet.dismiss();}
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(appSharedPreferences.readString("lang").equals("en")){
                    bottomSheet.dismiss();
                }else {
                Resources res = getContext().getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.setLocale(new Locale("en".toLowerCase())); // API 17+ only.
             // Use conf.locale = new Locale(...) if targeting lower versions
                res.updateConfiguration(conf, dm);
                appSharedPreferences.writeString("lang","en");

                //It is required to recreate the activity to reflect the change in UI.\
                ((ActivityMainCall)getContext()).finish();
                startActivity(new Intent(getContext(), ActivityMainCall.class).putExtra("fcall", "profile"));
                    appSharedPreferences.writeInteger("lang_flag",1);
                bottomSheet.dismiss();}
            }
        });
        bottomSheet.show();
    }


}
