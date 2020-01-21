package net.phpsm.simsim.simsiminstantorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WifiReciver extends BroadcastReceiver {

    public static String conn = "1";

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);


        if (info!=null && info.isConnected()){
            if (info.getType()==ConnectivityManager.TYPE_WIFI){
                conn = "1";
            }
        } else {
            conn = "0";
        }
    }
}
