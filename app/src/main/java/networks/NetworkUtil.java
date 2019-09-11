package networks;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import views.MyApp;

/*
 * Created by Ashwini Dhande on 08-09-2019.
 */

/**
 * NetworkUtil classNetworkUtil to perform networking auth operations
 */
public class NetworkUtil {

    /**
     * Check for internet connection availability.
     *
     * @return true if device is currently connected to the internet (WiFi or Mobile Data), otherwise false.
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) MyApp.getCurrentContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }
}