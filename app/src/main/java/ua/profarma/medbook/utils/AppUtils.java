package ua.profarma.medbook.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;

public class AppUtils {

    private static final String TAG = "AppUtils";

    public static boolean isNetworkAvailable(final Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
        }
        else {
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null && wifiInfo.isConnected()) {
                return true;
            }
            wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiInfo != null && wifiInfo.isConnected()) {
                return true;
            }
            wifiInfo = connectivityManager.getActiveNetworkInfo();
            if (wifiInfo != null && wifiInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    public static void toastMessage(final String message, final boolean shortShow)
    {
        Toast toast = Toast.makeText(App.getAppContext(), message, shortShow ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View toastView = toast.getView(); // This'll return the default View of the Toast.
        /* And now you can get the TextView of the default View of the Toast. */
        TextView toastMessage = toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(18);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setCompoundDrawablePadding(8);
        toastView.setBackgroundResource(R.drawable.toast_message);
        toast.show();
    }

    public static void toastError(final String message, final boolean shortShow)
    {
        Toast.makeText(App.getAppContext(), message, shortShow ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();

    }

    public static void toastOk(final String message, final boolean shortShow)
    {
        Toast toast = Toast.makeText(App.getAppContext(), message, shortShow ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View toastView = toast.getView(); // This'll return the default View of the Toast.
        /* And now you can get the TextView of the default View of the Toast. */
        TextView toastMessage = toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(18);
        toastMessage.setTextColor(Color.WHITE);
        toastMessage.setGravity(Gravity.CENTER);
        toastMessage.setCompoundDrawablePadding(8);
        toastView.setBackgroundResource(R.drawable.toast_ok);
        toast.show();
    }

    public static void showNoInternetConnect(){
        AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_internet_connection), true);
    }

    public static void openLink(String link) {
        if (link == null || link.isEmpty()) {
            return;
        }

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            intent.putExtra("userId", App.getUser().id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getAppContext().startActivity(intent);
        } catch (Exception e) {
            LogUtils.logW(TAG, "openLink: " + e.getMessage());
        }
    }

    public static int convertDpToPx(Context context, int px){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }

    public static int convertDpToSp(Context context, int sp){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

}
