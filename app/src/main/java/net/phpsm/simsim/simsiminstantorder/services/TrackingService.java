package net.phpsm.simsim.simsiminstantorder.services;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.MyDialog;
import net.phpsm.simsim.simsiminstantorder.apiservices.ApiService;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient;
import net.phpsm.simsim.simsiminstantorder.clients.ApiClient1;
import net.phpsm.simsim.simsiminstantorder.models.StoreModel;
import net.phpsm.simsim.simsiminstantorder.models.responses.APIError;
import net.phpsm.simsim.simsiminstantorder.models.responses.lists.SaveItem;
import net.phpsm.simsim.simsiminstantorder.models.responses.map.ResultDistanceMatrix;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.ErrorUtils;
import net.phpsm.simsim.simsiminstantorder.utils.Utils;

import java.util.ArrayList;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class TrackingService extends Service {
	private WindowManager windowManager;
	private RelativeLayout chatheadView, removeView;
	private LinearLayout txtView, txt_linearlayout;
	private ImageView chatheadImg, removeImg;
	private TextView  txt1;
	private int x_init_cord, y_init_cord, x_init_margin, y_init_margin;
	private Point szWindow = new Point();
	private boolean isLeft = true;
	private String sMsg = "";
	private LocationManager locManager;
	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	private LocationListener locListener = new myLocationListener();
	static final Double EARTH_RADIUS = 6371.00;
	private Handler handler = new Handler();
	ApiService apiService;
	String latLngString;
	LatLng latLng;
	private ArrayList<String> permissionsRejected = new ArrayList<>();
	private ArrayList<String> permissions = new ArrayList<>();
	private final static int ALL_PERMISSIONS_RESULT = 101;
	private ArrayList<String> permissionsToRequest;
	private Firebase firebase;
	private FirebaseUser user;
	private String str;
	private String new_str;
	private String user_id;
	private Runnable r;
	String p_img_load;
	AppSharedPreferences appSharedPreferences;
	String duration;
	Dialog dialog;
	String customer_id;
	String order_no;
	String provider_lat = "21.460315";
	String provider_long = "39.239445";

	IBinder binder = new MyBinder();
	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@SuppressWarnings("deprecation")

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("service", "TrackingService.onCreate()");

		str = "ListDistanceAndDuration";
		new_str = "ListCustomerTrackTime";

		Firebase.setAndroidContext(this);
		firebase = new Firebase("https://simsiminstantorder1.firebaseio.com/"+new_str);
		FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
		user = firebaseAuth.getCurrentUser();
		//user_id = user.getUid().toString();
		user_id = FirebaseInstanceId.getInstance().getToken();

		Log.d("user_id", user_id);

	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void handleStart(){
		windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

		LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);

		int LAYOUT_FLAG;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
		} else {
			LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
		}

		removeView = (RelativeLayout)inflater.inflate(R.layout.remove, null);
		WindowManager.LayoutParams paramRemove = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				LAYOUT_FLAG,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		paramRemove.gravity = Gravity.TOP | Gravity.LEFT;

		removeView.setVisibility(View.GONE);
		removeImg = (ImageView)removeView.findViewById(R.id.remove_img);
		windowManager.addView(removeView, paramRemove);


		chatheadView = (RelativeLayout) inflater.inflate(R.layout.chathead, null);
		chatheadImg = (ImageView)chatheadView.findViewById(R.id.provider_tracking_img);

		apiService = ApiClient1.getClient().create(ApiService.class);
		appSharedPreferences = new AppSharedPreferences(this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			windowManager.getDefaultDisplay().getSize(szWindow);
		} else {
			int w = windowManager.getDefaultDisplay().getWidth();
			int h = windowManager.getDefaultDisplay().getHeight();
			szWindow.set(w, h);
		}

		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				LAYOUT_FLAG,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		params.gravity = Gravity.TOP | Gravity.LEFT;
		params.x = 0;
		params.y = 100;
		windowManager.addView(chatheadView, params);

		chatheadView.setOnTouchListener(new View.OnTouchListener() {
			long time_start = 0, time_end = 0;
			boolean isLongclick = false, inBounded = false;
			int remove_img_width = 0, remove_img_height = 0;

			Handler handler_longClick = new Handler();
			Runnable runnable_longClick = new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Log.d(Utils.LogTag, "Into runnable_longClick");

					isLongclick = true;
					removeView.setVisibility(View.VISIBLE);
					chathead_longclick();
				}
			};

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) chatheadView.getLayoutParams();

				int x_cord = (int) event.getRawX();
				int y_cord = (int) event.getRawY();
				int x_cord_Destination, y_cord_Destination;

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						time_start = System.currentTimeMillis();
						handler_longClick.postDelayed(runnable_longClick, 600);

						remove_img_width = removeImg.getLayoutParams().width;
						remove_img_height = removeImg.getLayoutParams().height;

						x_init_cord = x_cord;
						y_init_cord = y_cord;

						x_init_margin = layoutParams.x;
						y_init_margin = layoutParams.y;

						if(txtView != null){
							txtView.setVisibility(View.GONE);
							myHandler.removeCallbacks(myRunnable);
						}
						break;
					case MotionEvent.ACTION_MOVE:
						int x_diff_move = x_cord - x_init_cord;
						int y_diff_move = y_cord - y_init_cord;

						x_cord_Destination = x_init_margin + x_diff_move;
						y_cord_Destination = y_init_margin + y_diff_move;

						if(isLongclick){
							int x_bound_left = szWindow.x / 2 - (int)(remove_img_width * 1.5);
							int x_bound_right = szWindow.x / 2 +  (int)(remove_img_width * 1.5);
							int y_bound_top = szWindow.y - (int)(remove_img_height * 1.5);

							if((x_cord >= x_bound_left && x_cord <= x_bound_right) && y_cord >= y_bound_top){
								inBounded = true;

								int x_cord_remove = (int) ((szWindow.x - (remove_img_height * 1.5)) / 2);
								int y_cord_remove = (int) (szWindow.y - ((remove_img_width * 1.5) + getStatusBarHeight() ));

								if(removeImg.getLayoutParams().height == remove_img_height){
									removeImg.getLayoutParams().height = (int) (remove_img_height * 1.5);
									removeImg.getLayoutParams().width = (int) (remove_img_width * 1.5);

									WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeView.getLayoutParams();
									param_remove.x = x_cord_remove;
									param_remove.y = y_cord_remove;

									windowManager.updateViewLayout(removeView, param_remove);
								}

								layoutParams.x = x_cord_remove + (Math.abs(removeView.getWidth() - chatheadView.getWidth())) / 2;
								layoutParams.y = y_cord_remove + (Math.abs(removeView.getHeight() - chatheadView.getHeight())) / 2 ;

								windowManager.updateViewLayout(chatheadView, layoutParams);
								break;
							}else{
								inBounded = false;
								removeImg.getLayoutParams().height = remove_img_height;
								removeImg.getLayoutParams().width = remove_img_width;

								WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeView.getLayoutParams();
								int x_cord_remove = (szWindow.x - removeView.getWidth()) / 2;
								int y_cord_remove = szWindow.y - (removeView.getHeight() + getStatusBarHeight() );

								param_remove.x = x_cord_remove;
								param_remove.y = y_cord_remove;

								windowManager.updateViewLayout(removeView, param_remove);
							}

						}


						layoutParams.x = x_cord_Destination;
						layoutParams.y = y_cord_Destination;

						windowManager.updateViewLayout(chatheadView, layoutParams);
						break;
					case MotionEvent.ACTION_UP:
						isLongclick = false;
						removeView.setVisibility(View.GONE);
						removeImg.getLayoutParams().height = remove_img_height;
						removeImg.getLayoutParams().width = remove_img_width;
						handler_longClick.removeCallbacks(runnable_longClick);

						if(inBounded){
							if(MyDialog.active){
								MyDialog.myDialog.finish();
							}

							handler.removeCallbacks(r);
							stopService(new Intent(TrackingService.this, TrackingService.class));
							appSharedPreferences.writeString("startTracking", "0");
							appSharedPreferences.writeString("totalDuration", "");
							appSharedPreferences.writeString("totalDistanse", "");
							appSharedPreferences.writeString("statusOrder", "");
							inBounded = false;
							break;
						}


						int x_diff = x_cord - x_init_cord;
						int y_diff = y_cord - y_init_cord;

						if(Math.abs(x_diff) < 5 && Math.abs(y_diff) < 5){
							time_end = System.currentTimeMillis();
							if((time_end - time_start) < 300){
								chathead_click();
							}
						}

						y_cord_Destination = y_init_margin + y_diff;

						int BarHeight =  getStatusBarHeight();
						if (y_cord_Destination < 0) {
							y_cord_Destination = 0;
						} else if (y_cord_Destination + (chatheadView.getHeight() + BarHeight) > szWindow.y) {
							y_cord_Destination = szWindow.y - (chatheadView.getHeight() + BarHeight );
						}
						layoutParams.y = y_cord_Destination;

						inBounded = false;
						resetPosition(x_cord);

						break;
					default:
						Log.d(Utils.LogTag, "chatheadView.setOnTouchListener  -> event.getAction() : default");
						break;
				}
				return true;
			}
		});


		txtView = (LinearLayout)inflater.inflate(R.layout.txt, null);
		txt1 = (TextView) txtView.findViewById(R.id.txt1);
		txt_linearlayout = (LinearLayout)txtView.findViewById(R.id.txt_linearlayout);


		WindowManager.LayoutParams paramsTxt = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.WRAP_CONTENT,
				LAYOUT_FLAG,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
				PixelFormat.TRANSLUCENT);
		paramsTxt.gravity = Gravity.TOP | Gravity.LEFT;

		txtView.setVisibility(View.GONE);
		windowManager.addView(txtView, paramsTxt);

	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);

		if(windowManager == null)
			return;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            windowManager.getDefaultDisplay().getSize(szWindow);
        } else {
            int w = windowManager.getDefaultDisplay().getWidth();
            int h = windowManager.getDefaultDisplay().getHeight();
            szWindow.set(w, h);
        }

		WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) chatheadView.getLayoutParams();

	    if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
	    	Log.d(Utils.LogTag, "ChatHeadService.onConfigurationChanged -> landscap");

	    	if(txtView != null){
				txtView.setVisibility(View.GONE);
			}

	    	if(layoutParams.y + (chatheadView.getHeight() + getStatusBarHeight()) > szWindow.y){
	    		layoutParams.y = szWindow.y- (chatheadView.getHeight() + getStatusBarHeight());
	    		windowManager.updateViewLayout(chatheadView, layoutParams);
	    	}

	    	if(layoutParams.x != 0 && layoutParams.x < szWindow.x){
				resetPosition(szWindow.x);
			}

	    } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
	    	Log.d(Utils.LogTag, "ChatHeadService.onConfigurationChanged -> portrait");

	    	if(txtView != null){
				txtView.setVisibility(View.GONE);
			}

	    	if(layoutParams.x > szWindow.x){
				resetPosition(szWindow.x);
			}

	    }

	}

	private void resetPosition(int x_cord_now) {
		if(x_cord_now <= szWindow.x / 2){
			isLeft = true;
			moveToLeft(x_cord_now);

		} else {
			isLeft = false;
			moveToRight(x_cord_now);

		}

    }
	 private void moveToLeft(final int x_cord_now){
		 	final int x = szWindow.x - x_cord_now;

	        new CountDownTimer(350, 10) {//500    3
	        	WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) chatheadView.getLayoutParams();
	            public void onTick(long t) {
	                long step = (500 - t)/5;
	                mParams.x = 0 - (int)(double)bounceValue(step, x );
	                windowManager.updateViewLayout(chatheadView, mParams);
	            }
	            public void onFinish() {
	            	mParams.x = 0;
	                windowManager.updateViewLayout(chatheadView, mParams);
	            }
	        }.start();
	 }
	 private  void moveToRight(final int x_cord_now){
	        new CountDownTimer(350, 10) {//500    3
	        	WindowManager.LayoutParams mParams = (WindowManager.LayoutParams) chatheadView.getLayoutParams();
	            public void onTick(long t) {
	                long step = (500 - t)/5;
	                mParams.x = szWindow.x + (int)(double)bounceValue(step, x_cord_now) - chatheadView.getWidth();
	                windowManager.updateViewLayout(chatheadView, mParams);
	            }
	            public void onFinish() {
	            	mParams.x = szWindow.x - chatheadView.getWidth();
	                windowManager.updateViewLayout(chatheadView, mParams);
	            }
	        }.start();
	    }

	 private double bounceValue(long step, long scale){
	        double value = scale * Math.exp(-0.055 * step) * Math.cos(0.08 * step);
	        return value;
	    }

	 private int getStatusBarHeight() {
		int statusBarHeight = (int) Math.ceil(25 * getApplicationContext().getResources().getDisplayMetrics().density);
	    return statusBarHeight;
	}

	private void chathead_click(){
		if(MyDialog.active){
			MyDialog.myDialog.finish();
		}else{
			Intent it = new Intent(this,MyDialog.class)
					.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP)
					.putExtra("from", appSharedPreferences.readString("name_copy"))
					.putExtra("order_no", appSharedPreferences.readString("order_no"));
	        startActivity(it);
		}

	}

	private void chathead_longclick(){
		Log.d(Utils.LogTag, "Into ChatHeadService.chathead_longclick() ");

		WindowManager.LayoutParams param_remove = (WindowManager.LayoutParams) removeView.getLayoutParams();
		int x_cord_remove = (szWindow.x - removeView.getWidth()) / 2;
		int y_cord_remove = szWindow.y - (removeView.getHeight() + getStatusBarHeight() );

		param_remove.x = x_cord_remove;
		param_remove.y = y_cord_remove;

		windowManager.updateViewLayout(removeView, param_remove);
	}

	private void showMsg(String sMsg){
		if(txtView != null && chatheadView != null ){
			Log.d(Utils.LogTag, "ChatHeadService.showMsg -> sMsg=" + sMsg);
			txt1.setText(sMsg);
			myHandler.removeCallbacks(myRunnable);

			WindowManager.LayoutParams param_chathead = (WindowManager.LayoutParams) chatheadView.getLayoutParams();
			WindowManager.LayoutParams param_txt = (WindowManager.LayoutParams) txtView.getLayoutParams();

			txt_linearlayout.getLayoutParams().height = chatheadView.getHeight();
			txt_linearlayout.getLayoutParams().width = szWindow.x / 2;

			if(isLeft){
				param_txt.x = param_chathead.x + chatheadImg.getWidth();
				param_txt.y = param_chathead.y;

				txt_linearlayout.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			}else{
				param_txt.x = param_chathead.x - szWindow.x / 2;
				param_txt.y = param_chathead.y;

				txt_linearlayout.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			}

			txtView.setVisibility(View.VISIBLE);
			windowManager.updateViewLayout(txtView, param_txt);

			myHandler.postDelayed(myRunnable, 4000);

		}

	}

	Handler myHandler = new Handler();
	Runnable myRunnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if(txtView != null){
				txtView.setVisibility(View.GONE);
			}
		}
	};
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		//Toast.makeText(getBaseContext(), "Service Started", Toast.LENGTH_SHORT).show();

		permissions.add(ACCESS_FINE_LOCATION);
		permissions.add(ACCESS_COARSE_LOCATION);

		/*permissionsToRequest = findUnAskedPermissions(permissions);


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


			if (permissionsToRequest.size() > 0)
				requestPermissions((Activity) getApplicationContext(),permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
			else {
				fetchLocation();
			}
		} else {
			fetchLocation();
		}*/

		if (intent.getExtras() != null) {

			provider_lat = intent.getStringExtra("provider_lat");
			provider_long = intent.getStringExtra("provider_long");

			Log.d("latlongtracking", provider_lat+" | "+provider_long);

		}else{
			Log.d("NotificationTag" , "null");
		}

		r = new Runnable() {
			public void run() {
				Log.v("Debug", "Hello");
				fetchLocation();
				location();
				fetchDistance();

				/*if (intent.getExtras() != null) {
					Object value ;
					for (String key : intent.getExtras().keySet()) {
						if(key.equals("statusOrder")) {
							value = intent.getExtras().get(key); // value will represend your message body... Enjoy It
							Log.d("NotificationTag" , key+"____" + value);
						}
					}
				}else{
					Log.d("NotificationTag" , "null");
				}*/

				/*String[] distanse = appSharedPreferences.readString("totalDistanse").split(" ");

				if (!distanse[0].equals("")) {
					Log.d("ServiceDistanse", "not null");
					if (Double.parseDouble(distanse[0]) <= 0.5) {
						String order_no = appSharedPreferences.readString("order_no");
						addArrival(Integer.parseInt(order_no));
						Log.d("ServiceDistanse", "distanse in "+distanse[0]);
					}else {
						Log.d("ServiceDistanse", "distanse out "+distanse[0]);
					}
				}else{
					Log.d("ServiceDistanse", "is null");
				}*/

				handler.postDelayed(this, 5000);
			}
		};
		handler.postDelayed(r, 5000);

		// TODO Auto-generated method stub
		Log.d(Utils.LogTag, "ChatHeadService.onStartCommand() -> startId=" + startId);

		if(intent != null){
			Bundle bd = intent.getExtras();

			if(bd != null) {
				sMsg = bd.getString(Utils.EXTRA_MSG);
				p_img_load = bd.getString(Utils.P_Track_Img);
			}
			if(sMsg != null && sMsg.length() > 0){
				if(startId == Service.START_STICKY){
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							showMsg(sMsg);
						}
					}, 5000);

				}else{
					showMsg(sMsg);
				}

			}
			if (p_img_load != null && p_img_load.length() > 0){
				if(startId == Service.START_STICKY){
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Picasso.with(getApplicationContext()).load(p_img_load).resize(200, 200).into(chatheadImg);
							Intent intent1 = new Intent("GET_TIME");
							intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
							sendBroadcast(intent1);
						}
					}, 5000);

				}else{
					Picasso.with(this).load(p_img_load).resize(200, 200).into(chatheadImg);
				}
			}

		}

		if(startId == Service.START_STICKY) {
			handleStart();
			return super.onStartCommand(intent, flags, startId);
		}else{
			return  Service.START_NOT_STICKY;
		}

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if(chatheadView != null){
			windowManager.removeView(chatheadView);
		}

		if(txtView != null){
			windowManager.removeView(txtView);
		}

		if(removeView != null){
			windowManager.removeView(removeView);
		}

	}

	public void location() {
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		try {
			gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}
		Log.v("Debug", "in on create.. 2");
		if (gps_enabled) {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
			Log.v("Debug", "Enabled..");
		}
		if (network_enabled) {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
			Log.v("Debug", "Disabled..");
		}
		Log.v("Debug", "in on create..3");
	}

	private class myLocationListener implements LocationListener
	{
		double lat_old=0.0;
		double lon_old=0.0;
		double lat_new;
		double lon_new;
		double time=10;
		double speed=0.0;
		@Override
		public void onLocationChanged(Location location) {
			Log.v("Debug", "in onLocation changed..");
			if(location!=null){
				locManager.removeUpdates(locListener);
				//String Speed = "Device Speed: " +location.getSpeed();
				lat_new=location.getLongitude();
				lon_new =location.getLatitude();
				String longitude = "Longitude: " +location.getLongitude();
				String latitude = "Latitude: " +location.getLatitude();
				double distance =CalculationByDistance(lat_new, lon_new, lat_old, lon_old);
				speed = distance/time;
				/*Toast.makeText(getApplicationContext(), longitude+"\n"+latitude+"\nDistance is: "
						+distance+"\nSpeed is: "+speed , Toast.LENGTH_SHORT).show();*/
				lat_old=lat_new;
				lon_old=lon_new;
			}
		}
		@Override
		public void onProviderDisabled(String provider) {}
		@Override
		public void onProviderEnabled(String provider) {}
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {}
	}

	public double CalculationByDistance(double lat1, double lon1, double lat2, double lon2) {
		double Radius = EARTH_RADIUS;
		double dLat = Math.toRadians(lat2-lat1);
		double dLon = Math.toRadians(lon2-lon1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return Radius * c;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////

	private void fetchLocation() {

		SmartLocation.with(this).location()
				.oneFix()
				.start(new OnLocationUpdatedListener() {
					@Override
					public void onLocationUpdated(Location location) {
						latLngString = location.getLatitude() + "," + location.getLongitude();
						latLng = new LatLng(location.getLatitude(), location.getLongitude());
						//Toast.makeText(TrackingService.this, "latLngString : "+latLngString, Toast.LENGTH_SHORT).show();
						Log.d("latLngString", latLngString);
					}
				});
	}

	private void fetchDistance() {

		Log.d("fetchDistance", "fetchDistance");

		/*try {
			provider_lat = appSharedPreferences.readString("provider_lat");
			provider_long = appSharedPreferences.readString("provider_long");
		} catch (Exception e) {
			e.printStackTrace();
		}*/

			customer_id = appSharedPreferences.readString("customer_id");
			order_no = appSharedPreferences.readString("order_no");

			Call<ResultDistanceMatrix> call = apiService.getDistance(latLngString, provider_lat + "," + provider_long);
			call.enqueue(new Callback<ResultDistanceMatrix>() {
                @Override
                public void onResponse(Call<ResultDistanceMatrix> call, Response<ResultDistanceMatrix> response) {

                    ResultDistanceMatrix resultDistance = response.body();
                    if ("OK".equalsIgnoreCase(resultDistance.status)) {

                        Log.d("OK", "OK");

                        ResultDistanceMatrix.InfoDistanceMatrix infoDistanceMatrix = resultDistance.rows.get(0);
                        ResultDistanceMatrix.InfoDistanceMatrix.DistanceElement distanceElement = infoDistanceMatrix.elements.get(0);
                        if ("OK".equalsIgnoreCase(distanceElement.status)) {

                            Log.d("status", "status");

                            ResultDistanceMatrix.InfoDistanceMatrix.ValueItem itemDuration = distanceElement.duration;
                            ResultDistanceMatrix.InfoDistanceMatrix.ValueItem itemDistance = distanceElement.distance;
                            String totalDistance = String.valueOf(itemDistance.text);
                            String totalDuration = String.valueOf(itemDuration.text);

                            int customer_track_time = appSharedPreferences.readInteger("customer_track_time");

                            Firebase parentC = firebase.child(customer_id).child(order_no);
                            parentC.child("distance").setValue(totalDistance);
    //						parentC.child("duration").setValue(totalDuration);
                            parentC.child("duration").setValue(customer_track_time);
                            parentC.child("latLongString").setValue(latLngString);

                            //Toast.makeText(TrackingService.this, "totalDistance"+totalDistance+" totalDuration"+totalDuration, Toast.LENGTH_SHORT).show();
                            Log.d("disAndDur", "totalDistance"+totalDistance+" totalDuration"+totalDuration);
                            appSharedPreferences.writeString("totalDuration", totalDuration);
                            appSharedPreferences.writeString("totalDistanse", totalDistance);

                            duration = totalDuration;
                            /*UpdateDurationListener updateDurationListener = (UpdateDurationListener)getApplicationContext();
                            updateDurationListener.filldurationTextview(totalDuration);*/
                            //new MyDialog().filldurationTextview(totalDuration);

                            new StoreModel(totalDistance, totalDuration);

                        }else if ("ZERO_RESULTS".equalsIgnoreCase(distanceElement.status)){
                            //Toast.makeText(TrackingService.this, getString(R.string.no_path), Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("elsestatus", "elsestatus");
                        }
                    }else
                        Log.d("elseOK", "elseOK");

                }

                @Override
                public void onFailure(Call<ResultDistanceMatrix> call, Throwable t) {
                    call.cancel();
                }
            });
	}

	private void addArrival(int order_id) {
		String token = appSharedPreferences.readString("access_token");
		ApiService apiService = ApiClient.getClient().create(ApiService.class);
		Call<SaveItem> call = apiService.addArrival(appSharedPreferences.readString("lang"),"application/json","Bearer "+ token,order_id);
		call.enqueue(new Callback<SaveItem>() {
			@Override
			public void onResponse(Call<SaveItem> call, Response<SaveItem> response) {
				if (response.isSuccessful()) {
					Log.d("heres", "success");
					Boolean success=response.body().getStatus();
					if(success){
						Log.d("here", "here");
					}
				}
				else {
					APIError apiError = ErrorUtils.parseError(response);
					Log.e("APIError", apiError.getMessage());
				}
			}
			@Override
			public void onFailure(Call<SaveItem> call, Throwable t) {
				//Toast.makeText(TrackingService.this, getString(R.string.tryagain), Toast.LENGTH_SHORT).show();
				// progressBar.setVisibility(View.GONE);

			}
		});

	}

	private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
		ArrayList<String> result = new ArrayList<>();

		for (String perm : wanted) {
			if (!hasPermission(perm)) {
				result.add(perm);
			}
		}

		return result;
	}

	private boolean hasPermission(String permission) {
		if (canMakeSmores()) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
			}
		}
		return true;
	}

	private boolean canMakeSmores() {
		return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
	}

	public class MyBinder extends Binder {
		public TrackingService getService(){
			return TrackingService.this;
		}
	}

	public String getDuration() {

		Log.d("getDuration", "duration : "+duration);
		return duration;

	}

	/*public interface UpdateDurationListener {
		void filldurationTextview(String durationValue);
	}*/
}
