package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.CheckMobile;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
    EditText email;
    Button resetPassword;
    AppSharedPreferences appSharedPreferences;
    View underLineEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        email = findViewById(R.id.email);
        resetPassword = findViewById(R.id.resetPassword);
        underLineEmail = findViewById(R.id.underline_email);
        appSharedPreferences = new AppSharedPreferences(ForgetPassword.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(ForgetPassword.this, R.color.my_statusbar_color_login));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(ForgetPassword.this);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(getResources().getColor(R.color.my_statusbar_color_login));
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

        email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
        underLineEmail.setBackgroundResource(R.color.under_line);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, 0, 0);
                underLineEmail.setBackgroundResource(R.color.under_line);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setBackground(getResources().getDrawable(R.drawable.shapedittextlogin_empty));
                } else {
                    sendEmail(email.getText().toString());
                }
            }
        });

    }

    private void sendEmail(String emailS) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.resetPassword(appSharedPreferences.readString("lang"), "application/json", 3, "pzavbkx5WTC9HaEoKWEhY31xXqgXFNBzAYGzMUXb", emailS);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                String error_msg = "";
                if (response.isSuccessful()) {

                    Log.d("sendEmail", "sendEmail");

                    if (response.body().getStatus() == true) {
                        email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                        underLineEmail.setBackgroundResource(R.color.green_line);

//                        Toast.makeText(ForgetPassword.this, , Toast.LENGTH_LONG).show();
                        CustomToast(getString(R.string.sendEmail));
                        order_delivered_dialog();

                    } else {

                        email.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                        underLineEmail.setBackgroundResource(R.color.orange_line);

//                        Toast.makeText(ForgetPassword.this, , Toast.LENGTH_LONG).show();
                        CustomToast(getString(R.string.errorEmail));
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                Log.d("sendEmail", "sendEmailFalure");
                if (ForgetPassword.this != null){
//                    Toast.makeText(ForgetPassword.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });

    }

    ////////////////////////////////////////////////////////////////////////////
    public void order_delivered_dialog() {

        final Dialog dialog1 = new Dialog(ForgetPassword.this);
        dialog1.show();
        dialog1.setContentView(R.layout.link_reset_password_dialog);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btn_ok = dialog1.findViewById(R.id.btn_ok);

        dialog1.setCancelable(false);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPassword.this, LoginActivity.class));
                dialog1.dismiss();
            }
        });

    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ForgetPassword.this, texttoast, Toast.LENGTH_LONG);
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
