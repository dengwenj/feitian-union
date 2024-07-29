package vip.dengwj.feitian_union.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context appContent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (appContent == null) {
            appContent = getBaseContext();
        }
    }

    public static Context getAppContent() {
        return appContent;
    }
}
