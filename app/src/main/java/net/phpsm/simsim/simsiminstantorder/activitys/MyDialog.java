package net.phpsm.simsim.simsiminstantorder.activitys;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.ColorTransformation;
import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.services.TrackingService;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyDialog extends Activity {
    public static boolean active = false;
    public static Activity myDialog;
    TextView provider_name, order_no, mobile, order_status, delevery_txt;
    Context context;
    AppSharedPreferences appSharedPreferences;
    TrackingService trackingService = null;
    CircleImageView img_provider;
    TextView iamHere;
    String order_noS;
    Runnable r;
    Runnable r1;
    private Handler handler = new Handler();
    ImageView delivery_status;
    int i = 0;
    String statusOrder;
    ImageView hidialog;
    FrameLayout frame_layout_mobile, call;
    int x = 0;
    String mobile_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.tracking_dialog);

        myDialog = MyDialog.this;

        provider_name = findViewById(R.id.provider_name);
        order_no = findViewById(R.id.order_no);
        //duration = findViewById(R.id.duration_value);
        iamHere = findViewById(R.id.iamHere);
        img_provider = findViewById(R.id.img_provider);
        delivery_status = findViewById(R.id.delivery_status);
        mobile = findViewById(R.id.mobile_value);
        order_status = findViewById(R.id.order_status);
        hidialog = findViewById(R.id.hidialog);
        frame_layout_mobile = findViewById(R.id.frame_layout_mobile);
        call = findViewById(R.id.call);
        delevery_txt = findViewById(R.id.delevery_txt);
        appSharedPreferences = new AppSharedPreferences(getApplicationContext());

        mobile_value = appSharedPreferences.readString("provider_mobile_copy");
       // order_status.setText(appSharedPreferences.readString("statusOrder"));

        frame_layout_mobile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + mobile_value));
                Log.d("calll", "calll");
                if (ActivityCompat.checkSelfPermission(getBaseContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }
                startActivity(callIntent);
                frame_layout_mobile.setVisibility(View.INVISIBLE);
            }
        });


        call.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (x % 2 == 0) {
                    frame_layout_mobile.setVisibility(View.VISIBLE);
                    x++;
                } else {
                    frame_layout_mobile.setVisibility(View.INVISIBLE);
                    x++;
                }
            }
        });

        hidialog.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        filldurationTextview();

        Intent intent = getIntent();
        if (intent != null) {
            String from = intent.getStringExtra("from");
            order_noS = intent.getStringExtra("order_no");
            provider_name.setText(from);
            order_no.setText(order_noS);
            mobile.setText(mobile_value);
            //Toast.makeText(MyDialog.this, mobile_value, Toast.LENGTH_SHORT).show();
            String urlImageProvider = appSharedPreferences.readString("image_copy");
            Picasso.with(MyDialog.this)
                    .load(urlImageProvider)
                    .resize(200, 200)
                    .into(img_provider);
            String urlDeliveryStatus = appSharedPreferences.readString("delivery_status_url_icon");
            Picasso.with(MyDialog.this)
                    .load(urlDeliveryStatus)
                    .resize(200, 200)
                    .transform(new ColorTransformation(Color.parseColor("#e01c4b")))
                    .into(delivery_status);

            String dlivery_status_name_S = appSharedPreferences.readString("dlivery_status_name");
            delevery_txt.setText(dlivery_status_name_S);


            r = new Runnable() {
                public void run() {
                    int x = i++;
                    Log.v("filldurationTextview", x + "");
                    filldurationTextview();
                    handler.postDelayed(this, 5000);
                }
            };
            handler.postDelayed(r, 5000);

            //order_status.setText("PINDING");

            if (appSharedPreferences.readString("statusOrder").equals("")) {

                appSharedPreferences.writeString("statusOrder", "PINDING");
            }
            r1 = new Runnable() {
                public void run() {
                    int x = i++;
                    Log.v("deliveryStatus", x + "");
                    int typeId = appSharedPreferences.readInteger("typeId");
                    if (typeId == 2) {
                        statusOrder = appSharedPreferences.readString("statusOrder");
                        order_status.setText(statusOrder);
                    }
                    handler.postDelayed(this, 100);
                }
            };
            handler.postDelayed(r1, 100);
        }


        /*duration.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                filldurationTextview();
            }
        });*/

        String deliveryStatusName = appSharedPreferences.readString("dlivery_status_name");
        iamHere.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!order_noS.equals("")) {
                    String[] distanse = appSharedPreferences.readString("totalDistanse").split(" ");
                    if (!distanse[0].equals("")) {
                        Log.d("distanse", distanse[0]);
                        if (distanse[1].equalsIgnoreCase("m")) {
                            if (Double.parseDouble(distanse[0]) <= 500) {
                                Log.d("distanse[0]", distanse[0]);
                                switch (deliveryStatusName) {
                                    case "In Car":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.inCar), 1);
                                        break;
                                    case "Dine In":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.dineIn),0);
                                        break;
                                    case "To Window ":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.toWindow),0);
                                        break;
                                    case "Carry Out":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.inCar),0);
                                        break;
                                }
                            } else {
                                CustomToast(getString(R.string.outsideAria), 0);
                            }
                        } else {
                            if (Double.parseDouble(distanse[0]) <= 0.5) {
                                Log.d("distanse[0]", distanse[0]);
                                switch (deliveryStatusName) {
                                    case "In Car":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.inCar), 1);
                                        break;
                                    case "Dine In":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.dineIn), 0);
                                        break;
                                    case "To Window ":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.toWindow), 0);
                                        break;
                                    case "Carry Out":
                                        iamHere(Integer.parseInt(order_noS));
                                        CustomToast(getString(R.string.inCar), 0);
                                        break;
                                }
                            } else {
                                CustomToast(getString(R.string.outsideAria), 0);
                            }
                        }
                    } else {
                        CustomToast(getString(R.string.distanseNull), 0);
                    }
                } else {
                    CustomToast(getString(R.string.orderNumberNull), 0);
                }
            }
        });

        /*btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String str = edt.getText().toString();
				if(str.length() > 0){
//					ChatHeadService.showMsg(MyDialog.this, str);
					Intent it = new Intent(MyDialog.this, TrackingService.class);
					it.putExtra(Utils.EXTRA_MSG, str);
					startService(it);
				}
			}
		});
		
		
		top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});*/

    }

    //filldurationTextview myReciver = new filldurationTextview();

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        active = true;

		/*IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("GET_TIME");*/

        //registerReceiver(myReciver ,intentFilter);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        active = false;

        //unregisterReceiver(myReciver);
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        active = false;
        handler.removeCallbacks(r);
        handler.removeCallbacks(r1);
    }

    public void filldurationTextview() {

        String newDurationValue = appSharedPreferences.readString("totalDuration");
        if (!newDurationValue.equals("")) {

            //duration.setText(newDurationValue);

        }
    }

    private void iamHere(int order_id) {
        String token = appSharedPreferences.readString("access_token");
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SaveItem> call = apiService.iamHere(appSharedPreferences.readString("lang"), "application/json", "Bearer " + token, order_id);
        call.enqueue(new Callback<SaveItem>() {
            @Override
            public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
                if (response.isSuccessful()) {
                    Log.d("heres", "success");
                    Boolean success = response.body().getStatus();
                    if (success) {
                        Log.d("here", "here");
                    }
                } else {
                    APIError apiError = ErrorUtils.parseError(response);
                    Toast.makeText(context, "Message is : " + apiError.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Error is : " + apiError.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SaveItem> call, Throwable t) {
                if (MyDialog.this != null) {
                    Toast.makeText(MyDialog.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
                }
                // progressBar.setVisibility(View.GONE);

            }
        });

    }

    private void CustomToast(String texttoast, int wink) {

        Toast toast = Toast.makeText(MyDialog.this, " "+texttoast+" ", Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.shaptextviewtoasterror);
        TextView text = view.findViewById(android.R.id.message);
        if (wink == 1) {
            text.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_wink, 0);
        }
        text.setTextColor(Color.BLACK);
        if (Locale.getDefault().getLanguage().equals("ar")) {
          //  text.setCompoundDrawables(R.drawable.ic_wink, 0, 0, 0);
        }else{
          //  text.setCompoundDrawables();
        }
        text.setPadding(15, 10, 15, 10);
        text.setGravity(Gravity.CENTER);
        toast.show();

    }

	/*public class filldurationTextview extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//appSharedPreferences.readString("totalDuration")
			if (intent.getAction().equalsIgnoreCase("GET_TIME"))
			if (trackingService.getDuration() != null) {

				String result = trackingService.getDuration();
				Log.e("result1", "Result : " + result);

				((TextView) findViewById(R.id.duration_value)).setText(result + "");
			}
		}
	}*/


}
