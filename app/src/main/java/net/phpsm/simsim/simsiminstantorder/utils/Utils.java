package net.phpsm.simsim.simsiminstantorder.utils;


import android.content.Context;
import android.os.Build;
import android.provider.Settings;

public class Utils {
	public static String LogTag = "baher";
	public static String EXTRA_MSG = "extra_msg";
	public static String P_Track_Img = "p_track_img";
	public static  int ToolbarHeight;


	public static boolean canDrawOverlays(Context context){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			return true;
		}else{
			return Settings.canDrawOverlays(context);
		}


	}


    public static int getToolbarHeight(Context context) {
		return ToolbarHeight;
    }

	public static void setToolbarHeight(int toolbarHeight) {
		Utils.ToolbarHeight = toolbarHeight;
	}
}
