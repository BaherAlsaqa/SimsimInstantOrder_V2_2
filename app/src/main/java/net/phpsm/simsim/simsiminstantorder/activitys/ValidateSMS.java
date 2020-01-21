package net.phpsm.simsim.simsiminstantorder.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.tapadoo.alerter.Alerter;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateSMS extends AppCompatActivity {

    Button btnActivation;
    EditText enterCode;
    TextView resendCode;
    View underline_code;
    AppSharedPreferences appSharedPreferences;
    int x=0;
    String resend_mobile;
    private String phoneVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;

    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_sms);

        btnActivation = findViewById(R.id.activation);
        enterCode = findViewById(R.id.enter_code);
        resendCode = findViewById(R.id.resend_code);
        underline_code = findViewById(R.id.underline_code);
        appSharedPreferences = new AppSharedPreferences(getApplicationContext());

        resend_mobile = appSharedPreferences.readString("resend_mobile");
        Log.d("VerificatonCallbacks", "resend_mobile : "+resend_mobile);

        fbAuth = FirebaseAuth.getInstance();

        sendCode(resend_mobile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(ValidateSMS.this, R.color.btn_blue));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = new View(ValidateSMS.this);
            view.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup) w.getDecorView()).addView(view);
            view.setBackgroundColor(getResources().getColor(R.color.btn_blue));
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

        btnActivation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!enterCode.getText().toString().equals("")) {
                    int code = Integer.parseInt(enterCode.getText().toString());
                    Log.d("onComplete", "onClick");
                    verifyCode();
                }else{
                    enterCode.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                    underline_code.setBackgroundResource(R.color.orange_line);
//                    Toast.makeText(ValidateSMS.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.error_code), ValidateSMS.this);
                }
            }
        });

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x < 3) {
                    resendCode();
                    x++;
                }else{
//                    Toast.makeText(ValidateSMS.this,, Toast.LENGTH_LONG).show();
                    new ActivityMain().CustomToast1(getString(R.string.resend_code_limit), ValidateSMS.this);
                }
            }
        });

    }

    private void validateCode(int code) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String access_token = appSharedPreferences.readString("access_token");
        Call<SaveItem> call = apiService.validateCode(appSharedPreferences.readString("lang"),"application/json", "Bearer "+access_token, code);
        call.enqueue(new Callback<SaveItem>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {

                    Log.d("validateCode", "validateCode");

                    if (response.body().getStatus() == true) {
                        Log.d("response.body().getStatus()", "true");
                        Log.d("onComplete", "true");
                        appSharedPreferences.writeInteger("is_validate", 1);
                        startActivity(new Intent(ValidateSMS.this, ActivityMain.class).putExtra("fopen", "home"));
                        finish();
//                        Toast.makeText(ValidateSMS.this, , Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.resend_successfully), ValidateSMS.this);
                    }else {
                        Log.d("response.body().getStatus()", "false");
                        Log.d("onComplete", "false");
//                        Toast.makeText(ValidateSMS.this, , Toast.LENGTH_SHORT).show();
                        new ActivityMain().CustomToast1(getString(R.string.validate_code_error), ValidateSMS.this);
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                Log.d("validateCode", "validateCodeFailure");
                Log.d("onComplete", "onFailure");
                if (ValidateSMS.this != null) {
//                    Toast.makeText(ValidateSMS.this, , Toast.LENGTH_SHORT).show();
                    new ActivityMain().CustomToast1(getString(R.string.tryagain), ValidateSMS.this);
                }
            }
        });

    }

    /*private void resendCode() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String access_token = appSharedPreferences.readString("access_token");
        Call<SaveItem> call = apiService.resendCode(appSharedPreferences.readString("lang"),"application/json", "Bearer "+access_token);
        call.enqueue(new Callback<SaveItem>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {

                    Log.d("resendCode", "resendCodeCode");

                    if (response.body().getStatus() == true) {
                        Log.d("response.body().getStatus()", "true");
                        Toast.makeText(ValidateSMS.this, getString(R.string.resend_successfully), Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d("response.body().getStatus()", "false");
                        Toast.makeText(ValidateSMS.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                Log.d("resendCode", "resendCodeFailure");
            }
        });

    }*/

    public void sendCode(String number) {
        Log.d("onComplete", "sendCode");
        setUpVerificatonCallbacks();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                verificationCallbacks);
    }

    private void setUpVerificatonCallbacks() {
        Log.d("onComplete", "setUpVerificatonCallbacks");
        verificationCallbacks =
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(
                            PhoneAuthCredential credential) {
                        Log.d("onVerificationCompleted", "onVerificationCompleted");
                        Log.d("onComplete", "onVerificationCompleted");
                        signInWithPhoneAuthCredential(credential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            // Invalid request
                            Log.d("VerificatonCallbacks", "Invalid credential: "
                                    + e.getLocalizedMessage());
                            Log.d("onComplete", "FirebaseAuthInvalidCredentialsException");
                        } else if (e instanceof FirebaseTooManyRequestsException) {
                            // SMS quota exceeded
                            Log.d("VerificatonCallbacks", "SMS Quota exceeded.");
                            Log.d("onComplete", "FirebaseTooManyRequestsException");
                        }
                    }

                    @Override
                    public void onCodeSent(String verificationId,
                                           PhoneAuthProvider.ForceResendingToken token) {

                        phoneVerificationId = verificationId;
                        resendToken = token;
                        String resend_mobile = appSharedPreferences.readString("resend_mobile");
                        Log.d("VerificatonCallbacks", "onCodeSent - resend_mobile : "+resend_mobile);
                        Log.d("onComplete", "onCodeSent");
                    }
                };
    }

    public void verifyCode() {
        Log.d("onComplete", "verifyCode");
        String code = enterCode.getText().toString();

        PhoneAuthCredential credential =
                PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            enterCode.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_check_edittext, 0);
                            underline_code.setBackgroundResource(R.color.green_line);

                            FirebaseUser user = task.getResult().getUser();
                            String phoneNumber = user.getPhoneNumber();
                            int code = Integer.parseInt(appSharedPreferences.readString("code"));
                            Log.d("onComplete", "onComplete");

                            Typeface txtfont;
                            Typeface titlefont;


                            if(appSharedPreferences.readString("lang").equals("ar")){
                                txtfont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Arabic_R.ttf");
                                titlefont = Typeface.createFromAsset(getAssets(), "fonts/Abdoullah-Ashgar-EL-kharef.ttf");


                            }else {
                                txtfont = Typeface.createFromAsset(getAssets(), "fonts/Nunito-Regular.ttf");
                                titlefont = Typeface.createFromAsset(getAssets(), "fonts/forte.ttf");
                            }

                            Alerter.create(ValidateSMS.this)
                                    .setTitle(getResources().getString(R.string.notification))
                                    .setTitleTypeface(titlefont)
                                    .setTextTypeface(txtfont)
                                    .setTitleAppearance(R.style.AlertTextAppearance_Title)
                                    .setTextAppearance(R.style.AlertTextAppearance_Text)
                                    .setText(getResources().getString(R.string.validatesuccessfully))
                                    .setDuration(10000)
                                    .setBackgroundColorInt(getResources().getColor(R.color.green_line))
                                    .setIcon(getResources().getDrawable(R.drawable.alerter_ic_notifications))
                                    .setIconColorFilter(getResources().getColor(R.color.white))
                                    .show();

                            validateCode(code);

                        } else {
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {
                                enterCode.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_error_edittext, 0);
                                underline_code.setBackgroundResource(R.color.orange_line);
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }

    public void resendCode() {
        String resend_mobile = appSharedPreferences.readString("resend_mobile");
        setUpVerificatonCallbacks();
        Log.d("onComplete", "resendCode");
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                resend_mobile,
                60,
                TimeUnit.SECONDS,
                this,
                verificationCallbacks,
                resendToken);
    }

    public void signOut() {
        fbAuth.signOut();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        signOut();
    }
}
