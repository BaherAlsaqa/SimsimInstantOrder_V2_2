package net.phpsm.simsim.simsiminstantorder.activitys;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.WifiReciver;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.CustomerResponse;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.UpdateCustomer;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map2Activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private int PLACE_PICKER_REQUEST = 1;
    AppSharedPreferences appSharedPreferences;
    String name,  pass, mobile,  email,  cartype,isfromProfile,refreshToken;
    int carcolor,  carbrand;
    private ProgressDialog progressDialog;
    static Map2Activity map2Activity;
    public static Map2Activity getInstance(){
        return   map2Activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        map2Activity = this;
        initViews();
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(Map2Activity.this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();}
    }

    private void initViews() {
        appSharedPreferences = new AppSharedPreferences(Map2Activity.this);
        refreshToken=appSharedPreferences.readString("refresh_token");
        progressDialog = new ProgressDialog(this);
Intent intent=getIntent();
name=intent.getStringExtra("username");
pass=intent.getStringExtra("passwordS");
mobile=intent.getStringExtra("mobile");
email=intent.getStringExtra("email");
cartype=intent.getStringExtra("cartype");
carcolor=intent.getIntExtra("carcolor",0);
carbrand=intent.getIntExtra("carbrand",0);
isfromProfile=intent.getStringExtra("profile");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Toast.makeText(Map2Activity.this, , Toast.LENGTH_LONG).show();
        CustomToast(connectionResult.getErrorMessage() + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String placename = String.format("%s", place.getName());
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                String address = String.format("%s", place.getAddress());

               Log.d("place",place.getName().toString()+"....."+place.getAddress()+",,,,,,"+place.getLocale()+",,,,,");


                  progressDialog.setTitle(getString(R.string.inserting));
                  progressDialog.setMessage(getString(R.string.please_wait));
                  progressDialog.show();

                if (WifiReciver.conn != "") {
                    if (WifiReciver.conn == "1") {
                        if(isfromProfile!=null){
                            updateLocation(address+","+placename,latitude,longitude);
                        }else {
                            registerData(name, pass, mobile, email, cartype, carcolor, carbrand, address + "," + placename, latitude, longitude);
                        }    //  registerData(username, passwordS, mobile, email, cartype, carcolor, carbrand);
                    } else if (WifiReciver.conn == "0") {
                        CustomToast(getString(R.string.connection_message));
                        progressDialog.dismiss();
                    }
                }


            }
        }
    }

    private void updateLocation(String address,String latitude,String longitude) {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<UpdateCustomer> call = apiService.updateLocation(appSharedPreferences.readString("lang"),"application/json","Bearer " + appSharedPreferences.readString("access_token"),latitude,longitude, address);
        call.enqueue(new Callback<UpdateCustomer>() {
            @Override
            public void onResponse(Call<UpdateCustomer> call, Response<UpdateCustomer> response) {

                if (response.isSuccessful()) {
                    try {
                        UpdateCustomer updateCustomer = new UpdateCustomer();
                        updateCustomer = response.body();
                        if (updateCustomer.getStatus().equals("true")) {
                            progressDialog.dismiss();
                            appSharedPreferences.writeString("longitude",longitude);
                            appSharedPreferences.writeString("latitude",latitude);
                            appSharedPreferences.writeString("location", address);
                            finish();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else if (response.code()==401){
                    progressDialog.dismiss();

                    APIError apiError = ErrorUtils.parseError(response);
                    Log.d("ssss","ssss");
//                    Toast.makeText(Map2Activity.this, , Toast.LENGTH_SHORT).show();
                    CustomToast("Message is : " + apiError.getMessage());
                    appSharedPreferences.writeString("long",longitude);
                    appSharedPreferences.writeString("lat",latitude);
                    appSharedPreferences.writeString("loc", address);
                    ActivityMain.getInstance().RefreshUserToken(refreshToken,4);
                }
            }

            @Override
            public void onFailure(Call<UpdateCustomer> call, Throwable t) {
                progressDialog.dismiss();
                if (Map2Activity.this != null) {
//                    Toast.makeText(Map2Activity.this, , Toast.LENGTH_SHORT).show();
                    CustomToast(getString(R.string.tryagain));
                }
                //Toast.makeText(getContext(), "Error : " + t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void message_dialog(final int conn, String messagedialg) {

        final Dialog dialog1 = new Dialog(Map2Activity.this);
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

    private void registerData(String name, String pass, String mobile, String email, String cartype, int carcolor, int carbrand,String address,String lat,String longi){
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CustomerResponse> call = apiService.registerCustomer(appSharedPreferences.readString("lang"),3, "pzavbkx5WTC9HaEoKWEhY31xXqgXFNBzAYGzMUXb", "password", name, email, pass, carcolor,  carbrand, cartype, mobile,address,lat,longi);
        call.enqueue(new Callback<CustomerResponse>() {
            @Override
            public void onResponse(Call<CustomerResponse> call, Response<CustomerResponse> response) {

                try {
                    CustomerResponse customerResponse = response.body();
                    if (response.isSuccessful()) {
                        if (!customerResponse.getAccess_token().equals("")) {
                            startActivity(new Intent(getApplicationContext(), SignupDone.class));
                            finish();
                            String token_type = customerResponse.getToken_type();
                            String access_token = customerResponse.getAccess_token();
                            String refresh_token = customerResponse.getRefresh_token();
                            appSharedPreferences.writeString("token_type", token_type);
                            appSharedPreferences.writeString("access_token", access_token);
                            appSharedPreferences.writeString("refresh_token", refresh_token);
                            appSharedPreferences.writeString("location",address);
                            appSharedPreferences.writeString("longitude",longi);
                            appSharedPreferences.writeString("latitude",lat);
                            appSharedPreferences.writeString("username",name);
                            appSharedPreferences.writeString("user_Image","");

                            progressDialog.dismiss();
//                            Toast.makeText(Map2Activity.this, , Toast.LENGTH_SHORT).show();
                            //CustomToast("تم التسجيل");
                        }else if (customerResponse.getStatus().equals("false")) {
//                            Toast.makeText(Map2Activity.this, , Toast.LENGTH_SHORT).show();
                            CustomToast(customerResponse.getError());
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CustomerResponse> call, Throwable t) {
//                Toast.makeText(Map2Activity.this, , Toast.LENGTH_SHORT).show();
                CustomToast(getString(R.string.tryagain));
                progressDialog.dismiss();
            }
        });
    }


    public void closeActivity() {
        finish();
    }

    private void CustomToast(String texttoast) {

        Toast toast = Toast.makeText(Map2Activity.this, texttoast, Toast.LENGTH_LONG);
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