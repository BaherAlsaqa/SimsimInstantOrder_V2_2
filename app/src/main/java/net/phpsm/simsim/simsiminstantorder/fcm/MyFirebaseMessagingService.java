package net.phpsm.simsim.simsiminstantorder.fcm;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;

import com.google.firebase.messaging.RemoteMessage;

import net.phpsm.simsim.simsiminstantorder.R;
import net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain;
import net.phpsm.simsim.simsiminstantorder.services.TrackingService;
import net.phpsm.simsim.simsiminstantorder.utils.AppSharedPreferences;
import net.phpsm.simsim.simsiminstantorder.utils.Utils;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by baher on 28/1/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
   AppSharedPreferences appSharedPreferences;
    String orderStatus="";
    int typeId;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
       // super.onMessageReceived(remoteMessage);
       // Log.d("safaaa", remoteMessage.get);
        appSharedPreferences = new AppSharedPreferences(getApplicationContext());
        if (remoteMessage.getData().get("typeId") != null) {
            Log.d("typeId",remoteMessage.getData().get("typeId")+"");
            typeId=Integer.parseInt(remoteMessage.getData().get("typeId"));
           appSharedPreferences.writeInteger("typeId", Integer.parseInt(remoteMessage.getData().get("typeId")));
        }
        try {
            if (remoteMessage.getData().get("objectId") != null) {
                // رقم الطلب من السيرفر
                Log.d("objectId",remoteMessage.getData().get("objectId")+"");
                // رقم الطلب من الجهاز
                Log.d("order_no",remoteMessage.getData().get("order_no")+"");
                Log.v("dataaa", remoteMessage.getData().get("objectId")+"");
                //Log.v("dataaa", remoteMessage.getData().get("provider_image"));

                Log.v("dataaa", remoteMessage.getData().get("latitude")+" | "+remoteMessage.getData().get("longitude"));

                /*appSharedPreferences.writeString("provider_lat", remoteMessage.getData().get("latitude"));
                appSharedPreferences.writeString("provider_long", remoteMessage.getData().get("longitude"));*/

                int orderId = Integer.parseInt(remoteMessage.getData().get("objectId"));
                String order_no = appSharedPreferences.readString("order_no");
                Log.d("order_no", appSharedPreferences.readString("order_no")+" | 123");
                int orderTrackingId = Integer.parseInt(order_no.trim());
                if (orderId == orderTrackingId) {
                    orderStatus = remoteMessage.getData().get("statusOrder");
                    Log.v("dataaa", orderStatus + "");
                    appSharedPreferences.writeString("statusOrder", remoteMessage.getData().get("statusOrder") + "");
                    if (remoteMessage.getData().get("provider_image") != null) {
                        startTracking(remoteMessage.getData().get("provider_image"), remoteMessage.getData().get("statusOrder").toString(), remoteMessage.getData().get("latitude").toString(), remoteMessage.getData().get("longitude").toString());
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        //Log.d("statusOrderSSS", statusOrder);

        if (remoteMessage.getData().get("notificationId") != null) {
            showNotification(remoteMessage.getNotification().getBody(), Integer.parseInt(remoteMessage.getData().get("notificationId")));
            if (isForeground("net.phpsm.simsim.simsiminstantorder.activitys.ActivityMain")) {
                ActivityMain.getInstance().refreshNotify();
            }

        }

    }


    private void showNotification(String message, int notifi_id) {

        Intent i = new Intent(this, ActivityMain.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentIntent(pendingIntent)
                .setColor(Color.parseColor("#e01c4b"))
                ;

       // Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       // builder.setSound(alarmSound);
       // String statusOrder = appSharedPreferences.readString("statusOrder");
if(appSharedPreferences.readInteger("mute")==1){
    builder.setSound(null);
}else {
    if (orderStatus.equalsIgnoreCase("READY")) {
        Log.d("orderStatus", "orderStatus");
        Log.d("statusOrderSSS", "statusOrder.equalsIgnoreCase");
        builder.setVibrate(new long[]{1000, 1000});
        builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifi_ready));
    } else {
        builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifi1));
    }
}
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify(notifi_id, builder.build());

    }


    public boolean isForeground(String myPackage) {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        String componentInfo = runningTaskInfo.get(0).topActivity.getClassName();
        Log.d("ssssssss", runningTaskInfo.get(0).topActivity.getClassName());
        return componentInfo.equals(myPackage);
    }

    private void showOrderStatusMsg(String providerImage, String statusOrder){
        java.util.Date now = new java.util.Date();
        String str = "test by henry  " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
        Log.v("dataaa", providerImage);
        Intent intent = new Intent(MyFirebaseMessagingService.this, TrackingService.class);
        intent.putExtra(Utils.EXTRA_MSG, statusOrder);
        intent.putExtra(Utils.P_Track_Img, providerImage);
        startService(intent);
    }

    public void startTracking(String providerImage, String statusOrder,String provider_lat,String provider_long){
        if(Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(MyFirebaseMessagingService.this)) {
                Log.d("VERSION.SDK_INT", "(Build.VERSION.SDK_INT >= 23");
                /*Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1234);*/
            } else if (Settings.canDrawOverlays(this)) {
                startService(new Intent(this, TrackingService.class)
                        .putExtra(Utils.EXTRA_MSG, statusOrder)
                        .putExtra(Utils.P_Track_Img, providerImage)
                        .putExtra("provider_lat", provider_lat)
                        .putExtra("provider_long", provider_long));
                // getActivity().finish();
            }
        }
        else
        {
            startService(new Intent(this, TrackingService.class)
                    .putExtra(Utils.EXTRA_MSG, statusOrder)
                    .putExtra(Utils.P_Track_Img, providerImage)
                    .putExtra("provider_lat", provider_lat)
                    .putExtra("provider_long", provider_long));
            //getActivity().finish();


        }
        //getActivity().bindService(new Intent(getActivity(), TrackingService.class), serviceConnection, BIND_AUTO_CREATE);
    }
}
