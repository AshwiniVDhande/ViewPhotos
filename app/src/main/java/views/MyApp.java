package views;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

public class MyApp extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().build());
        }
    }

    /**
     * @return current context of the application
     */
    public static Context getCurrentContext() {
        if (mContext == null) {
            mContext = new MyApp().getApplicationContext();
        }
        return mContext;
    }

}
