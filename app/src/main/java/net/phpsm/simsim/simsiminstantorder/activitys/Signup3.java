package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;

import net.phpsm.simsim.simsiminstantorder.GPSTracker;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.CustomerResponse;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class Signup3 extends AppCompatActivity {

    GPSTracker gpsTracker;
    Button signup;
    Drawable drawable;
    EditText password, repassword;
    SharedPreferences sharedPreferences_Get;
    private ProgressDialog progressDialog;
   // SharedPreferences.Editor editor;
    AppSharedPreferences appSharedPreferences;
    View underline_password, underline_repassword;

    CheckBox roles_checkbox;
    TextView terms_condition;
    TextView between_char;
    String termsAndCondetionsURL = "https://simsim.phpsm.net/simsimlaravel/tearms";

    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup3);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        roles_checkbox=findViewById(R.id.roles_checkbox);
        roles_checkbox.setPaintFlags(roles_checkbox.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        signup = findViewById(R.id.signup);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        sharedPreferences_Get = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        //editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        appSharedPreferences = new AppSharedPreferences(Signup3.this);
        underline_password = findViewById(R.id.underline_password);
        underline_repassword = findViewById(R.id.underline_repassword);
        terms_condition = findViewById(R.id.terms_condition);
        between_char = findViewById(R.id.between_char);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 4){
                    Log.d("count", "count < 4");
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underline_password.setBackgroundResource(R.color.orange_line);

                    between_char.setTextColor(Color.parseColor("#f26820"));
                }else if (s.length() > 8){
                    Log.d("count", "count > 8");
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underline_password.setBackgroundResource(R.color.orange_line);

                    between_char.setTextColor(Color.parseColor("#f26820"));
                }else{
                    Log.d("count", "count between 4-8");
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    underline_password.setBackgroundResource(R.color.under_line);

                    between_char.setTextColor(Color.parseColor("#B8B8B8"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        terms_condition.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    terms_condition.setTextColor(Color.parseColor("#e01c4b"));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    terms_condition.setTextColor(Color.parseColor("#B8B8B8"));
                }

                return false;
            }
        });

        fbAuth = FirebaseAuth.getInstance();

        terms_condition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup3.this, WebPage.class).putExtra("social_url", termsAndCondetionsURL));
            }
        });

        password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underline_password.setBackgroundResource(R.color.under_line);
        repassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underline_repassword.setBackgroundResource(R.color.under_line);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Signup3.this, R.color.my_statusbar_color_signup));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(Signup3.this);
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

        progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String passwordS, repasswordS;
                passwordS = password.getText().toString();
                repasswordS = repassword.getText().toString();
                if (TextUtils.isEmpty(passwordS)){
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underline_password.setBackgroundResource(R.color.orange_line);
                }else if (TextUtils.isEmpty(repasswordS)){
                    repassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underline_repassword.setBackgroundResource(R.color.orange_line);
                }else if (passwordS.length() < 4){
                    Log.d("count", "count < 4");
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underline_password.setBackgroundResource(R.color.orange_line);

                    between_char.setTextColor(Color.parseColor("#f26820"));
                }else if (!passwordS.equals(repasswordS)) {
                    repassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underline_repassword.setBackgroundResource(R.color.orange_line);
//                    Toast.makeText(Signup3.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1("Password does not match", Signup3.this);
                }else if(!roles_checkbox.isChecked()){
//                    Toast.makeText(Signup3.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1("please checked the terms and conditions", Signup3.this);
                }else {
                    String username = sharedPreferences_Get.getString("username", "");
                    String mobile = sharedPreferences_Get.getString("mobile", "");
                    String email=sharedPreferences_Get.getString("email","");
                    Intent intent = getIntent();
                    String cartype = null;
                    int carcolor = 0;
                    int carbrand = 0;
                    if (intent != null) {
                        cartype = intent.getStringExtra("cartype");
                        carcolor = intent.getIntExtra("carcolor", 00);
                        carbrand = intent.getIntExtra("carbrand",0);
                        //carmodel = intent.getStringExtra("carmodel");
                    }

                   // progressDialog.setTitle(getString(R.string.inserting));
                  //  progressDialog.setMessage(getString(R.string.please_wait));
                    //progressDialog.show();

                    gpsTracker = new GPSTracker(Signup3.this);

                    if (WifiReciver.conn != "") {
                        if (WifiReciver.conn == "1") {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                            underline_password.setBackgroundResource(R.color.green_line);
                            between_char.setTextColor(Color.parseColor("#5cb85c"));
                            repassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                            underline_repassword.setBackgroundResource(R.color.green_line);
                            //StartDialog(username, passwordS, mobile, email, cartype, carcolor, carbrand);
                            registerData(username, passwordS, mobile, email, cartype, carcolor, carbrand);
                        } else if (WifiReciver.conn == "0") {
                            new ActivityMain().CustomToast1(getString(R.string.connection_message), Signup3.this);
                           // progressDialog.dismiss();
                        }
                    }

                    /*Toast.makeText(Signup3.this, username, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup3.this, mobile, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup3.this, cartype, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup3.this, carcolor+"", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup3.this, carbrand, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup3.this, carmodel, Toast.LENGTH_SHORT).show();
                    Toast.makeText(Signup3.this, passwordS, Toast.LENGTH_SHORT).show();*/
                }
            }
        });

        /*signup.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin1_click);
                    signup.setTextColor(getResources().getColor(R.color.recycler_color));
                    signup.setBackgroundDrawable(drawable);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    drawable = getResources().getDrawable(R.drawable.shapbtnlogin1);
                    signup.setTextColor(getResources().getColor(R.color.bold_name));
                    signup.setBackgroundDrawable(drawable);
                }
                return false;
            }
        });*/

    }
   private void registerData(String name, String pass, String mobile, String email, String cartype, int carcolor, int carbrand){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
       Call<CustomerResponse> call = apiService.registerCustomer(appSharedPreferences.readString("lang"),3, "pzavbkx5WTC9HaEoKWEhY31xXqgXFNBzAYGzMUXb", "password", name, email, pass, carcolor,  carbrand, cartype, mobile,"",gpsTracker.getLatitude()+"",gpsTracker.getLongitude()+"");
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {

                try {
                    CustomerResponse customerResponse = response.body();
                    if (response.isSuccessful()) {
                        if (!customerResponse.getAccess_token().equals("")) {

                            String mobile_S = mobile.substring(0,1);
                            Log.d("mobile_S", mobile_S);
                            if (mobile_S.equals("0")) {
                                String mobile_S1 = mobile.substring(1,10);
                                Log.d("mobile_S", mobile_S1);
                                appSharedPreferences.writeString("resend_mobile", "+966"+mobile_S1);
                            }else{
                                appSharedPreferences.writeString("resend_mobile", "+966"+mobile);
                                Log.d("mobile_S", mobile);
                            }
                            String token_type = customerResponse.getToken_type();
                            String access_token = customerResponse.getAccess_token();
                            String refresh_token = customerResponse.getRefresh_token();
                            int is_validate = customerResponse.getIs_validate();
                            String code = customerResponse.getCode();
                            appSharedPreferences.writeString("token_type", token_type);
                            appSharedPreferences.writeString("access_token", access_token);
                            appSharedPreferences.writeString("refresh_token", refresh_token);
                            appSharedPreferences.writeInteger("is_validate", is_validate);
                            appSharedPreferences.writeString("code", code);

                            appSharedPreferences.writeString("location","");

                            progressDialog.dismiss();
//                            Toast.makeText(Signup3.this, , Toast.LENGTH_SHORT).show();
                            new ActivityMain().CustomToast1(getString(R.string.register_success), Signup3.this);
                            //TODO//////////////////////////////////////////////////////////////////////////////////////////////////////////

                            startActivity(new Intent(getApplicationContext(), ValidateSMS.class));
                            finish();

                        }else if (customerResponse.getStatus().equals("false")) {
//                            Toast.makeText(Signup3.this, , Toast.LENGTH_SHORT).show();
                            new ActivityMain().CustomToast1(customerResponse.getError(), Signup3.this);
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
                if (Signup3.this != null) {
//                    Toast.makeText(Signup3.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), Signup3.this);
                }
                progressDialog.dismiss();
            }
        });
    }
    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(Signup3.this);
        dialog1.show();
        dialog1.setContentView(R.layout.message_dialog);
        final TextView message = dialog1.findViewById(R.id.message);
        final Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        String char_message = null;
        if(conn == 0)
            char_message = getApplicationContext().getString(R.string.connection_message);
        else if (conn == 1)
            char_message = getApplicationContext().getString(R.string.tryagain);
        else if (conn == 2)
            char_message = messagedialg;
        message.setText(char_message);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    private void StartDialog(String username,String passwordS,String mobile,String email,String cartype,int carcolor,int carbrand) {
        Dialog dialog = new Dialog(Signup3.this);
        dialog.show();
        dialog.setContentView(R.layout.location_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setCancelable(true);
        dialog.setTitle("");
        Button openMap=dialog.findViewById(R.id.open_map);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Map2Activity.class).putExtra("username",username).putExtra("passwordS",passwordS)
                .putExtra("mobile",mobile) .putExtra("email",email).putExtra("cartype",cartype).putExtra("carcolor",carcolor)
                                .putExtra("carbrand",carbrand)

                );

            }
        });
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface interface3, int keyCode,
                                 KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    //killActivity();

                }
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            gpsTracker.stopUsingGPS();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
