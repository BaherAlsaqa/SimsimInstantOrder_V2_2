package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.utilities.Utilities;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sinch.android.rtc.SinchError;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.LoginResponse;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.CustomerObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.DeviceToken;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.PProfileObject;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class LoginActivity extends AppCompatActivity {
    Button login, signup;
    Drawable d;
    EditText mobile, password;
    private ProgressDialog progressDialog;
    TextView forget, skip;

    String error;
    String mobileS;
    AppSharedPreferences appSharedPreferences;
    View underlineMobile, underlinePassword;
    int is_validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.my_statusbar_color_login));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(LoginActivity.this);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(getResources().getColor(R.color.my_statusbar_color_login));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);

        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        signup = findViewById(R.id.signup);
        login = findViewById(R.id.loginButton);
        mobile = findViewById(R.id.mobile);
        password = findViewById(R.id.password);
        forget = findViewById(R.id.forget);
        underlineMobile = findViewById(R.id.underline_mobile);
        underlinePassword = findViewById(R.id.underline_password);
        progressDialog = new ProgressDialog(this);

        appSharedPreferences = new AppSharedPreferences(LoginActivity.this);

        mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underlineMobile.setBackgroundResource(R.color.under_line);

        password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underlinePassword.setBackgroundResource(R.color.under_line);

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int mobileLength = s.length();
                Log.d("mobileLength", mobileLength + "");
                if (mobileLength == 10) {
                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                    underlineMobile.setBackgroundResource(R.color.green_line);
                    Log.d("10", 10 + "");
                } else {
                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    underlineMobile.setBackgroundResource(R.color.under_line);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                underlinePassword.setBackgroundResource(R.color.under_line);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobileS = mobile.getText().toString();
                String passwordS = password.getText().toString();

                if (TextUtils.isEmpty(mobileS)) {
                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underlineMobile.setBackgroundResource(R.color.orange_line);
                } else if (TextUtils.isEmpty(passwordS)) {
                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                    underlineMobile.setBackgroundResource(R.color.under_line);

                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underlinePassword.setBackgroundResource(R.color.orange_line);
                } else {

                    //startActivity(new Intent(getApplicationContext(), ActivityMain.class).putExtra("fopen", "home"));
                    //progressDialog.dismiss();
                    if (!WifiReciver.conn.equals("")) {
                        if (WifiReciver.conn.equals("1")) {
                            loginData(mobileS, passwordS);
                        } else if (WifiReciver.conn.equals("0")) {
//                            message_dialog(0, "");
                            CustomToast(getString(R.string.connection_message));
                            progressDialog.dismiss();
                        }
                    }

                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Signup1.class));
            }
        });

        login.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    d = getResources().getDrawable(R.drawable.shapbtnlogin1);
                    login.setTextColor(getResources().getColor(R.color.recycler_color));
                    login.setBackgroundDrawable(d);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    d = getResources().getDrawable(R.drawable.shapbtnlogin);
                    login.setTextColor(getResources().getColor(R.color.recycler_color));
                    login.setBackgroundDrawable(d);
                }
                return false;
            }
        });

        signup.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    d = getResources().getDrawable(R.drawable.shapbtnsignup1);
                    signup.setTextColor(getResources().getColor(R.color.recycler_color));
                    signup.setBackgroundDrawable(d);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    d = getResources().getDrawable(R.drawable.shapbtnsignup);
                    signup.setTextColor(getResources().getColor(R.color.recycler_color));
                    signup.setBackgroundDrawable(d);
                }
                return false;
            }
        });

        forget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    forget.setTextColor(Color.parseColor("#e01c4b"));
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    forget.setTextColor(Color.parseColor("#B8B8B8"));
                }

                return false;
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgetPassword.class));
            }
        });

        skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ActivityMain.class));

            }
        });
        skip.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    d = getResources().getDrawable(R.drawable.shapbtnlogin1_click);
                    skip.setTextColor(getResources().getColor(R.color.white));
                    skip.setBackgroundDrawable(d);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    d = getResources().getDrawable(R.drawable.shapbtnskip);
                    skip.setTextColor(getResources().getColor(R.color.texthint));
                    skip.setBackgroundDrawable(d);
                }
                return false;
            }
        });
    }


    private void loginData(final String mobileS, String pass) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(appSharedPreferences.readString("lang"), "password", 3, "pzavbkx5WTC9HaEoKWEhY31xXqgXFNBzAYGzMUXb", mobileS, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.code() == 200) {

                    appSharedPreferences.writeString("loginMobile", mobileS);
                    appSharedPreferences.writeString("loginPass", pass);

                    //LoginResponse loginResponse = response.body();
                    String token_type = response.body().getToken_type();
                    String access_token = response.body().getAccess_token();
                    String refresh_token = response.body().getRefresh_token();
                    if (response.body().getIs_validate() != null) {
                        is_validate = response.body().getIs_validate();
                    }

                    //TODO/// FCM ////

                    String tokenfcm = FirebaseInstanceId.getInstance().getToken();

                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                    underlineMobile.setBackgroundResource(R.color.green_line);
                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                    underlinePassword.setBackgroundResource(R.color.green_line);

                    if (is_validate == 1){
                        appSharedPreferences.writeString("token_type", token_type);
                        appSharedPreferences.writeString("access_token", access_token);
                        appSharedPreferences.writeString("refresh_token", refresh_token);
                        appSharedPreferences.writeInteger("is_validate", 1);

                        send_device_token(tokenfcm);
                        Log.d("Successfullll", tokenfcm);
                    }else{
                        validate_message_dialog();
                        progressDialog.dismiss();
                    }

                    //Toast.makeText(LoginActivity.this, access_token, Toast.LENGTH_SHORT).show();


                } else {

                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underlineMobile.setBackgroundResource(R.color.orange_line);

                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underlinePassword.setBackgroundResource(R.color.orange_line);

                    APIError apiError = ErrorUtils.parseError(response);
//                    Toast.makeText(LoginActivity.this, , Toast.LENGTH_SHORT).show();
                    CustomToast("Message is : " + apiError.getMessage());
//                    Toast.makeText(LoginActivity.this, , Toast.LENGTH_SHORT).show();
                    CustomToast("Error is : " + apiError.getError());
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                try {
                    progressDialog.dismiss();
                    Log.d("loginerror", "error");
                    if (LoginActivity.this != null) {
//                        Toast.makeText(LoginActivity.this, , Toast.LENGTH_SHORT).show();
                        CustomToast(getString(R.string.tryagain));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(LoginActivity.this);
        dialog1.show();
        dialog1.setContentView(R.layout.message_dialog);
        final TextView message = dialog1.findViewById(R.id.message);
        final Button btn_ok = dialog1.findViewById(R.id.btn_ok);
        String char_message = null;
        if (conn == 0)
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

    private void send_device_token(String tokenfcm) {
        progressDialog.setTitle(getString(R.string.login));
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        String access_token = appSharedPreferences.readString("access_token");
        Log.d("Successfullll", "tokenfcm");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<DeviceToken> call = apiService.deviceToken(appSharedPreferences.readString("lang"), "application/json", "Bearer " + access_token, "add", tokenfcm, "android");
        call.enqueue(new Callback<DeviceToken>() {
            @Override
            public void onResponse(Call<DeviceToken> call, Response<DeviceToken> response) {
                if (response.isSuccessful()) {
                    String success = response.body().getStatus();
                    if (success.equals("true")) {
                        Log.d("Successfullll", success);
                        startActivity(new Intent(LoginActivity.this, ActivityMain.class));
                        finish();
                        progressDialog.dismiss();
                    } else {
                        Log.d("Successfullll", success);
                    }
                } else {
                    Log.d("Successfullll", "isNotSuccessful");
                }

            }

            @Override
            public void onFailure(Call<DeviceToken> call, Throwable t) {
                Log.d("Successfullll", "error");
                if (LoginActivity.this != null) {
//                    Toast.makeText(LoginActivity.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }

    ////////////////////////////////////////////////////////////////////////////
    public void validate_message_dialog() {

        final Dialog dialog1 = new Dialog(LoginActivity.this);
        dialog1.show();
        dialog1.setContentView(R.layout.validate_message_dialog);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_ok = dialog1.findViewById(R.id.btn_ok);

        dialog1.setCancelable(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile_S = mobile.getText().toString().substring(0,1);
                Log.d("mobile_S", mobile_S);
                if (mobile_S.equals("0")) {
                    String mobile_S1 = mobile.getText().toString().substring(1,10);
                    Log.d("mobile_S", mobile_S1);
                    appSharedPreferences.writeString("resend_mobile", "+966"+mobile_S1);
                }else{
                    appSharedPreferences.writeString("resend_mobile", "+966"+mobile);
                    Log.d("mobile_S", mobile.getText().toString());
                }

                startActivity(new Intent(LoginActivity.this, ValidateSMS.class));
                dialog1.dismiss();
            }
        });

    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(LoginActivity.this, texttoast, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        text.setShadowLayer(0,0,0,0);
        text.setTextColor(Color.WHITE);
        text.setPadding(15, 0, 15, 0);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }

}
