package vip.dengwj.feitian_union.utils;

import android.widget.Toast;

import vip.dengwj.feitian_union.base.BaseApplication;

public class ToastUtils {
    private static Toast toast;

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getAppContent(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
