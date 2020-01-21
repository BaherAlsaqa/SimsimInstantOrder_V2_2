package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.adapter.CarBrandsAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.CarColorAdapter;
import net.phpsm.simsim.simsiminstantorder.adapter.SpinAdapter;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener13;
import net.phpsm.simsim.simsiminstantorder.listeners.OnItemClickListener16;
import net.phpsm.simsim.simsiminstantorder.models.responses.CarColors;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.CarBrandList;
import net.phpsm.simsim.simsiminstantorder.models.responses.CarBrands;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.CarColorList;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.GridLayout.HORIZONTAL;
import static android.widget.GridLayout.VERTICAL;

public class Signup2 extends AppCompatActivity {

    Button next;
    Drawable drawable;
    TextView select_color, select_car_brand;
    int a, b, c, d, e = 0;
    LinearLayout ll_select_color;
    Animation zoom_in;
    // EditText carmodel;
    String cartypeS="0";
    String carcolorS, carbrandS;
    int carcolorId,carBrandsId;
    ArrayList<CarBrands> brandsResponsList = new ArrayList<>();
    ArrayList<CarBrands> brandsResponsListFill = new ArrayList<>();
    ArrayList<CarColors> colorsResponsList = new ArrayList<>();
    ArrayList<CarColors> colorsResponsListFill = new ArrayList<>();
    AVLoadingIndicatorView progressBar;
    AVLoadingIndicatorView progressBarDialog,brandprogressBarDialog;
    RecyclerView mRecyclerView,carBrandsRecyclerView;
    CarColorAdapter adapter;
    CarBrandsAdapter carBrandsadapter;
    Dialog dialog,brandDialog;
    RadioGroup carRadioGroup;
    AppSharedPreferences appSharedPreferences;
    View underlinecarcolor, underlinecarbrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        next = findViewById(R.id.next);
        select_color = findViewById(R.id.select_color);
        select_car_brand=findViewById(R.id.select_car_brand);
        ll_select_color = findViewById(R.id.ll_select_color);
        progressBar = findViewById(R.id.progress);
        underlinecarcolor = findViewById(R.id.underline_carcolor);
        underlinecarbrand = findViewById(R.id.underline_carbrand);
        appSharedPreferences = new AppSharedPreferences(Signup2.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Signup2.this, R.color.my_statusbar_color_signup));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(Signup2.this);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(getResources().getColor(R.color.my_statusbar_color_signup));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
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
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        select_color.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_painter_color, 0, R.drawable.ic_expand_more_black_24dp, 0);
        underlinecarcolor.setBackgroundResource(R.color.under_line);

        select_car_brand.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_expand_more_black_24dp, 0);
        underlinecarbrand.setBackgroundResource(R.color.under_line);

        zoom_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        carRadioGroup= findViewById(R.id.car_radioGroup);
        carRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
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


        if (WifiReciver.conn != "") {
            if (WifiReciver.conn == "1") {
                progressBar.setVisibility(View.VISIBLE);
                carbrands();
            } else if (WifiReciver.conn == "0") {
                new ActivityMain().CustomToast1(getString(R.string.connection_message), Signup2.this);
                progressBar.setVisibility(View.GONE);
            }
        }

        /*next.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin1_click);
                    next.setTextColor(getResources().getColor(R.color.recycler_color));
                    next.setBackgroundDrawable(drawable);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin1);
                    next.setTextColor(getResources().getColor(R.color.bold_name));
                    next.setBackgroundDrawable(drawable);
                }
                return false;
            }
        });*/
        select_car_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car_brands_dialog();
            }
        });

        ll_select_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                color_dialog();

            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // carmodelS = carmodel.getText().toString();
                if (TextUtils.isEmpty(cartypeS) || cartypeS.equals("0")) {
//                    Toast.makeText(Signup2.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1("car type field required", Signup2.this);
                } else if (TextUtils.isEmpty(carcolorS)) {
//                    Toast.makeText(Signup2.this, "car color field required", Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1("car color field required", Signup2.this);
                } else if (TextUtils.isEmpty(carbrandS)) {
//                    Toast.makeText(Signup2.this, "car brand field required", Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1("car brand field required", Signup2.this);
                }
                //  else if (TextUtils.isEmpty(carmodelS)) {
                //      carmodel.setBackgroundResource(R.drawable.shapedittextlogin_empty);
                //   }
                else {
                    startActivity(new Intent(getApplicationContext(), Signup3.class)
                            .putExtra("cartype", cartypeS)
                            .putExtra("carcolor", carcolorId)
                            .putExtra("carbrand", carBrandsId)
                    );
                    finish();
                }
            }
        });


    }

    private void car_brands_dialog() {
        brandDialog = new Dialog(Signup2.this);
        brandDialog.show();
        brandDialog.setContentView(R.layout.car_brands_dialog);
        ///
        brandDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        brandDialog.setCancelable(false);
        brandDialog.setTitle("");
        carBrandsRecyclerView = brandDialog.findViewById(R.id.recyclerViewDialog);

        brandprogressBarDialog = brandDialog.findViewById(R.id.progress);
        carBrandsRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));


        Button close=brandDialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brandDialog.dismiss();
            }
        });
        carbrands();

    }

    public void color_dialog() {
        dialog = new Dialog(Signup2.this);
        dialog.show();
        dialog.setContentView(R.layout.color_dialog);
        dialog.setCancelable(false);
        dialog.setTitle("");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mRecyclerView = dialog.findViewById(R.id.recyclerViewDialog);
        progressBarDialog = dialog.findViewById(R.id.progress);
        Button close=dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
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
                        adapter = new CarColorAdapter(colorsResponsList, getApplicationContext());
                        mRecyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        progressBarDialog.setVisibility(View.GONE);
                        adapter.setOnClickListener(new OnItemClickListener13() {
                            @Override
                            public void onItemClick(CarColors item) {
                                dialog.dismiss();
                                carcolorS = item.getColor_name();
                                carcolorId = item.getId();
                                select_color.setHint(carcolorS);

                                select_color.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_painter_color, 0, R.drawable.ic_check_edittext, 0);
                                underlinecarcolor.setBackgroundResource(R.color.green_line);

//                                Toast.makeText(Signup2.this, , Toast.LENGTH_SHORT).show();
                                new ActivityMain().CustomToast1("Color name : "+carcolorS, Signup2.this);
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
                if (Signup2.this != null) {
//                    Toast.makeText(Signup2.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), Signup2.this);
                }
                progressBarDialog.setVisibility(View.GONE);
            }
        });
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
                        carBrandsadapter = new CarBrandsAdapter(brandsResponsList, getApplicationContext());
                        carBrandsRecyclerView.setAdapter(carBrandsadapter);
                        carBrandsadapter.notifyDataSetChanged();
                        brandprogressBarDialog.setVisibility(View.GONE);
                        carBrandsadapter.setOnClickListener(new OnItemClickListener16() {
                            @Override
                            public void onItemClick(CarBrands item) {

                                brandDialog.dismiss();
                                carbrandS = item.getName();
                                carBrandsId = item.getId();

                                select_car_brand.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                underlinecarbrand.setBackgroundResource(R.color.green_line);

                                // carcolorId = item.getId();
                                select_car_brand.setHint(carbrandS);


                            }
                        });
                        // SpinAdapter spinAdapter = new SpinAdapter(Signup2.this, android.R.layout.simple_spinner_dropdown_item, brandsResponsList);
                        // spinners.setAdapter(spinAdapter);
                        //  spinAdapter.notifyDataSetChanged();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<CarBrandList> call, Throwable t) {
                if (Signup2.this != null) {
//                    Toast.makeText(Signup2.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), Signup2.this);
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void message_dialog(final int conn) {

        final Dialog dialog1 = new Dialog(Signup2.this);
        dialog1.show();
        dialog1.setContentView(R.layout.message_dialog);
        final TextView message = dialog1.findViewById(R.id.message);
        final Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        String char_message = null;
        if (conn == 0)
            char_message = getApplicationContext().getString(R.string.connection_message);
        message.setText(char_message);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
