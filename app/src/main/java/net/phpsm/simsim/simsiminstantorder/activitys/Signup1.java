package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.CheckMobile;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup1 extends AppCompatActivity {

    Button next;
    EditText username, email, mobile;
    Drawable d;
    SharedPreferences.Editor editor;
    public static final String MY_PREFS_NAME = "MySharedP";
    //Drawable drawable, drawable1;
    AVLoadingIndicatorView progressBar;
    AppSharedPreferences appSharedPreferences;
    View underlineUsername, underlineEmail, underlineMobile;
    LinearLayout linearLayoutOpps;
    TextView txtExists, txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        next = findViewById(R.id.next);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        appSharedPreferences = new AppSharedPreferences(Signup1.this);
        mobile = findViewById(R.id.mobile);
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        progressBar = findViewById(R.id.progress);
        underlineUsername = findViewById(R.id.underline_username);
        underlineEmail = findViewById(R.id.underline_email);
        underlineMobile = findViewById(R.id.underline_mobile);
        linearLayoutOpps = findViewById(R.id.linearlayout_opps);
        txtExists = findViewById(R.id.txt_exist);
        txtLogin = findViewById(R.id.txt_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(Signup1.this, R.color.my_statusbar_color_signup));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(Signup1.this);
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

        username.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underlineUsername.setBackgroundResource(R.color.under_line);
        email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underlineEmail.setBackgroundResource(R.color.under_line);
        mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underlineMobile.setBackgroundResource(R.color.under_line);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup1.this, LoginActivity.class));
                finish();
            }
        });

        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                username.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                underlineUsername.setBackgroundResource(R.color.under_line);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                underlineEmail.setBackgroundResource(R.color.under_line);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                underlineMobile.setBackgroundResource(R.color.under_line);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                String usernameS = username.getText().toString();
                String mobileS = mobile.getText().toString();
                String emailS = email.getText().toString();
                /*drawable = getResources().getDrawable(R.drawable.shapedittextlogin_empty);
                drawable1 = getResources().getDrawable(R.drawable.shapedittextlogin);*/
                if (TextUtils.isEmpty(usernameS)) {
                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underlineUsername.setBackgroundResource(R.color.orange_line);
                } else if (TextUtils.isEmpty(emailS)) {
                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underlineEmail.setBackgroundResource(R.color.orange_line);

                } else if (TextUtils.isEmpty(mobileS)) {
                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underlineMobile.setBackgroundResource(R.color.orange_line);
                } else {
                    if (!WifiReciver.conn.equals("")) {
                        if (WifiReciver.conn.equals("1")) {

                            if (mobile.getText().toString().length() > 9) {
                                checkMobile(usernameS, mobileS, emailS);

                                String mobile_S = mobile.getText().toString().substring(0, 1);
                                Log.d("mobile_S", mobile_S);
                                if (mobile_S.equals("0")) {
                                    String mobile_S1 = mobile.getText().toString().substring(1, 10);
                                    Log.d("mobile_S", mobile_S1);
                                    //sendCode(mobile_S1);
                                } else {
                                    //sendCode(mobile);
                                    Log.d("mobile_S", mobile.getText().toString());
                                }

                                progressBar.setVisibility(View.VISIBLE);
                            } else {
                                mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                                underlineMobile.setBackgroundResource(R.color.orange_line);
                                new ActivityMain().CustomToast1(getString(R.string.check_number), Signup1.this);
                            }
                        } else if (WifiReciver.conn.equals("0")) {
                            new ActivityMain().CustomToast1(getString(R.string.connection_message), Signup1.this);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

       /* next.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    d = getResources().getDrawable(R.drawable.shapbtnlogin1_click);
                    next.setTextColor(getResources().getColor(R.color.recycler_color));
                    next.setBackgroundDrawable(d);
                }
                if(event.getAction() == MotionEvent.ACTION_UP){
                    d = getResources().getDrawable(R.drawable.shapbtnlogin1);
                    next.setTextColor(getResources().getColor(R.color.bold_name));
                    next.setBackgroundDrawable(d);
                }
                return false;
            }
        });*/

    }


    private int checkMobile(final String usernameS, final String mobileS, final String emailS) {
        final int[] x = new int[1];
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CheckMobile> call = apiService.checkmobile(appSharedPreferences.readString("lang"), "application/json", mobileS, emailS);
        call.enqueue(new Callback<CheckMobile>() {
            @Override
            public void onResponse(Call<CheckMobile> call, Response<CheckMobile> response) {
                String error_msg = "";
                if (response.isSuccessful()) {
                    try {
                        if (response.body().getStatus().equals("true")) {
                            x[0] = 1;
                            editor.putString("username", usernameS);
                            editor.putString("mobile", mobileS);
                            editor.putString("email", emailS);
                            editor.commit();
                            startActivity(new Intent(getApplicationContext(), Signup2.class));
                            finish();
                            progressBar.setVisibility(View.GONE);

                        } else if (response.body().getStatus().equals("false")) {
                            if (response.body().getError().size() > 1) {
                                x[0] = 2;
                                //both error
                                username.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                underlineUsername.setBackgroundResource(R.color.green_line);
                                email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                                underlineEmail.setBackgroundResource(R.color.orange_line);
                                mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                                underlineMobile.setBackgroundResource(R.color.orange_line);

                                txtExists.setText(getString(R.string.both_exists));
                                linearLayoutOpps.setVisibility(View.VISIBLE);
                            } else {
                                String code = response.body().getError().get(0).getCode();
                                if (code.equals("440")) {
                                    x[0] = 3;
                                    //mobile used
                                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                    underlineUsername.setBackgroundResource(R.color.green_line);
                                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                    underlineEmail.setBackgroundResource(R.color.green_line);
                                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                                    underlineMobile.setBackgroundResource(R.color.orange_line);

                                    txtExists.setText(getString(R.string.mobile_exists));
                                    linearLayoutOpps.setVisibility(View.VISIBLE);

                                } else if (code.equals("441")) {
                                    x[0] = 4;
                                    //email used
                                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                    underlineUsername.setBackgroundResource(R.color.green_line);
                                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                                    underlineEmail.setBackgroundResource(R.color.orange_line);
                                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                    underlineMobile.setBackgroundResource(R.color.green_line);

                                    txtExists.setText(getString(R.string.email_exists));
                                    linearLayoutOpps.setVisibility(View.VISIBLE);

                                } else if (code.equals("442")) {
                                    x[0] = 5;
                                    //email wrong
                                    username.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                    underlineUsername.setBackgroundResource(R.color.green_line);
                                    email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                                    underlineEmail.setBackgroundResource(R.color.orange_line);
                                    mobile.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                                    underlineMobile.setBackgroundResource(R.color.green_line);

                                    txtExists.setText(getString(R.string.email_error));
                                    linearLayoutOpps.setVisibility(View.VISIBLE);
                                }
                            }

                            for (int i = 0; i < response.body().getError().size(); i++) {
                                error_msg = error_msg + response.body().getError().get(i).getError_message() + "  ";
                            }

                            //Toast.makeText(Signup1.this, "Message : " + response.body().getError(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckMobile> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (Signup1.this != null) {
//                    Toast.makeText(Signup1.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), Signup1.this);
                }
            }
        });
        return x[0];
    }

    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(Signup1.this);
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
                progressBar.setVisibility(View.GONE);
            }
        });

    }


}
