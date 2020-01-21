package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ContactInfoItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.Objects.ContactInfoObject;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.phpsm.simsim.simsiminstantorder.activitys.Signup1.MY_PREFS_NAME;

public class ContactUs extends AppCompatActivity {
    EditText message;
    Button sendBtn;
    String content;
    TextView mobile, telephone, fax, email, address;
    ImageButton google, facebook, twitter, instagram;
    AVLoadingIndicatorView progressBar;
    AppSharedPreferences appSharedPreferences;
    String token ,refreshToken;
    static ContactUs contactUs;
    SwipeRefreshLayout swiperefresh;

    public static ContactUs getInstance(){
        return   contactUs;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        contactUs=this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        appSharedPreferences = new AppSharedPreferences(ContactUs.this);
        swiperefresh=findViewById(R.id.swiperefresh);
        swiperefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));

        mobile = findViewById(R.id.mobile_text);
        telephone = findViewById(R.id.telephone_text);
        fax = findViewById(R.id.fax_text);
        email = findViewById(R.id.email_text);
        address = findViewById(R.id.address_text);
        google = findViewById(R.id.google);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);
        instagram = findViewById(R.id.instagram);
        progressBar = findViewById(R.id.progress);
        message = findViewById(R.id.message);
        sendBtn = findViewById(R.id.btnsend);
        if(appSharedPreferences.readString("msg_content")!=null){
            String msg_content=appSharedPreferences.readString("msg_content");
            if(!msg_content.equals("")){
                appSharedPreferences.writeString("msg_content","");
                Log.d("editor msg",msg_content+"");
                message.setText(msg_content);
                sendMessage(msg_content);
            }
        }
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
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(toolbar.getTitle());
        getSupportActionBar().setDisplayShowTitleEnabled(false);
////////

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(message.getText().toString())) {
                    content = message.getText().toString();
                    if (WifiReciver.conn != "") {
                        if (WifiReciver.conn == "1") {
                            sendMessage(content);
                        } else if (WifiReciver.conn == "0") {
                            CustomToast(getString(R.string.connection_message));
                        }
                    }

                } else {
                    message.setBackground(getResources().getDrawable(R.drawable.shapaddmessageerror));
                }
            }
        });

        ///////////////////

        if (WifiReciver.conn != "") {
            if (WifiReciver.conn == "1") {
                getInformation();
            } else if (WifiReciver.conn == "0") {
                CustomToast(getString(R.string.connection_message));
            }
        }
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (WifiReciver.conn != "") {
                    if (WifiReciver.conn == "1") {
                        getInformation();
                    } else if (WifiReciver.conn == "0") {
                        CustomToast(getString(R.string.connection_message));
                    }
                }


            }
        });
    }

    private void sendMessage(String content) {
        progressBar.setVisibility(View.VISIBLE);
        token = appSharedPreferences.readString("access_token");
        refreshToken=appSharedPreferences.readString("refresh_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String finalContent = content;

        Call<SaveItem> call = apiService.ContactUs(appSharedPreferences.readString("lang"),"application/json","Bearer " + token, content);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body().getStatus();
                    if (success) {
//                        Toast.makeText(ContactUs.this, , Toast.LENGTH_SHORT).show();
                        CustomToast(getResources().getString(R.string.sendmsgSuccess));
                        progressBar.setVisibility(View.GONE);
                    }else {
                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(ContactUs.this, , Toast.LENGTH_SHORT).show();
                        CustomToast(getResources().getString(R.string.sendmsgunSuccess));
                    }
                }else if (response.code()==401){
                    progressBar.setVisibility(View.GONE);
                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss","ssss");
                  //  Toast.makeText(ContactUs.this, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    appSharedPreferences.writeString("msg_content", finalContent);
                    ActivityMain.getInstance().RefreshUserToken(refreshToken,R.id.Contact_us);
                }
                message.setBackground(getResources().getDrawable(R.drawable.shapaddmessage));

            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (ContactUs.this != null) {
//                    Toast.makeText(ContactUs.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }

    private void getInformation() {
        progressBar.setVisibility(View.VISIBLE);
        String token = appSharedPreferences.readString("access_token");
        refreshToken=appSharedPreferences.readString("refresh_token");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ContactInfoObject> call = apiService.getContactInfo(appSharedPreferences.readString("lang"),"application/json","Bearer " + token);
        call.enqueue(new Callback<ContactInfoObject>() {
            @Override
            public void onResponse(Call<ContactInfoObject> call, Response<ContactInfoObject> response) {
                if (response.isSuccessful()) {
                    swiperefresh.setRefreshing(false);
                    String success = response.body().getStatus();
                    ContactInfoItem contactInfoItem = response.body().getItems();
                    progressBar.setVisibility(View.GONE);
                    if (success.equals("true")) {
//                        Toast.makeText(ContactUs.this, , Toast.LENGTH_SHORT).show();
                        //CustomToast("successfully");
                        progressBar.setVisibility(View.GONE);
                        mobile.setText(contactInfoItem.getPhone1());
                        telephone.setText(contactInfoItem.getPhone2());
                        fax.setText(contactInfoItem.getFax());
                        email.setText(contactInfoItem.getCompany().getEmail());
                        address.setText(contactInfoItem.getCompany().getAddress());

                        google.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ContactUs.this, WebPage.class).putExtra("social_url", contactInfoItem.getGooglePlus()));
                            }
                        });
                        facebook.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ContactUs.this, WebPage.class).putExtra("social_url", contactInfoItem.getFacebook()));
                            }
                        });
                        twitter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ContactUs.this, WebPage.class).putExtra("social_url", contactInfoItem.getTwitter()));
                            }
                        });
                        instagram.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(ContactUs.this, WebPage.class).putExtra("social_url", contactInfoItem.getInstagram()));
                            }
                        });
                    }
                } else if (response.code()==401){
                    swiperefresh.setRefreshing(false);

                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss","ssss");
                   // Toast.makeText(ContactUs.this, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    ActivityMain.getInstance().RefreshUserToken(refreshToken,R.id.Contact_us);
                }
                message.setBackground(getResources().getDrawable(R.drawable.shapaddmessage));

            }

            @Override
            public void onFailure(Call<ContactInfoObject> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (ContactUs.this != null) {
                    swiperefresh.setRefreshing(false);

//                    Toast.makeText(ContactUs.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
            }
        });
    }

    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(ContactUs.this);
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

    public void closeActivity (){
        finish();
        //  startActivity(intent);
    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(ContactUs.this, texttoast, Toast.LENGTH_LONG);
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
