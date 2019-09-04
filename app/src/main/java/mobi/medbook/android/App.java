package mobi.medbook.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;

import io.fabric.sdk.android.Fabric;
import mobi.medbook.android.types.User;
import mobi.medbook.android.utils.LogUtils;


public class App extends Application {

    //5 min
    public static long INTERVALE_UPDATE_MATERIALS = 300000L;

    //5 min
    public static long INTERVALE_UPDATE_NOTIFICATIONS = 300000L;

    //5 min
    public static long INTERVALE_UPDATE_USERNEWS = 300000L;

    private static final String APP_PREFERENCES = "ua.medbook";

    private static final String TAG = "AppMedbook";

    private static boolean mAuthStart = false;
    private static Context applicationContext;
    private static boolean mTermAgreements = false;
    private static String mPushToken = null;
    private static String mAccessToken = null;
    private static String mRefreshToken = null;
    private static long mAccessTokenExpires;
    private static long mRefreshTokenExpires;
    private static String mImei = null;
    private static String mDeviceName = null;
    private static String mLanguage = null;
    private static long mUpdateLastTimeMaterials;
    private static long mUpdateLastTimeNotifications;
    private static long mUpdateLastTimeUserNews;

    //objects
    private static SharedPreferences mSettings = null;
    private static User mUser;

    private static final String APP_PREFERENCE_TERM_AGREEMENTS = "ua.medbook.term.agreements";
    private static final String APP_PREFERENCE_ACCESS_TOKEN = "ua.medbook.access.token";
    private static final String APP_PREFERENCE_REFRESH_TOKEN = "ua.medbook.refresh.token";
    private static final String APP_PREFERENCE_ACCESS_TOKEN_EXPIRES = "ua.medbook.access.token.expires";
    private static final String APP_PREFERENCE_REFRESH_TOKEN_EXPIRES = "ua.medbook.refresh.token.expires";
    private static final String APP_PREFERENCE_IMEI = "ua.medbook.imei";
    private static final String APP_PREFERENCE_NAME_DEVICE = "ua.medbook.name_device";
    private static final String APP_PREFERENCE_LANGUAGE = "ua.medbook.language";

    //keys for objects
    private static final String APP_PREFERENCE_USER = "com.medbook.user";

    public static String getPushToken() {
        return mPushToken;
    }

    public static String getAccessToken() {
        return mAccessToken;
    }

    public static String getRefreshToken() {
        return mRefreshToken;
    }

    public static String getImei() {
        return mImei;
    }

    public static String getmDeviceName() {
        return mDeviceName;
    }

    public static User getUser() {
        return mUser;
    }

    public static String getLanguage() {
        return mLanguage;
    }

    public static Context getAppContext() {
        return applicationContext;
    }

    public static long getmAccessTokenExpires() {
        return mAccessTokenExpires;
    }

    public static long getmRefreshTokenExpires() {
        return mRefreshTokenExpires;
    }

    public static void setUpdateLastTimeMaterials(){
        mUpdateLastTimeMaterials = System.currentTimeMillis();
    }
    public static void clearUpdateLastTimeMaterials(){
        mUpdateLastTimeMaterials = 0L;
    }

    public static boolean isUpdateMaterials() {
        LogUtils.logD(TAG, "isUpdateMaterials = " + (System.currentTimeMillis() - mUpdateLastTimeMaterials  > INTERVALE_UPDATE_MATERIALS));
        return System.currentTimeMillis() - mUpdateLastTimeMaterials  > INTERVALE_UPDATE_MATERIALS;
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static void setUpdateUserNews(){
        mUpdateLastTimeUserNews = System.currentTimeMillis();
    }

    public static void clearUserNews(){
        mUpdateLastTimeUserNews = 0L;
    }

    public static boolean isUpdateUserNews() {
        LogUtils.logD(TAG, "isUpdateUserNews = " + (System.currentTimeMillis() - mUpdateLastTimeUserNews  > INTERVALE_UPDATE_USERNEWS));
        return System.currentTimeMillis() - mUpdateLastTimeUserNews  > INTERVALE_UPDATE_USERNEWS;
    }

    public static void setUpdateLastTimeNotifications(){
        mUpdateLastTimeNotifications = System.currentTimeMillis();
    }

    public static boolean ismTermAgreements() {
        return mTermAgreements;
    }

    public static void setTermAgreements(){
        mTermAgreements = true;
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putBoolean(APP_PREFERENCE_TERM_AGREEMENTS, mTermAgreements);
        editor.apply();
    }
    public static void clearUpdateLastTimeUserNews() { mUpdateLastTimeUserNews = 0L; }

    public static void clearUpdateLastTimeNotifications(){
        mUpdateLastTimeNotifications = 0L;
    }

    public static boolean isUpdateNotifications() {
        LogUtils.logD(TAG, "isUpdateNotifications = " + (System.currentTimeMillis() - mUpdateLastTimeNotifications  > INTERVALE_UPDATE_NOTIFICATIONS));
        return System.currentTimeMillis() - mUpdateLastTimeNotifications  > INTERVALE_UPDATE_NOTIFICATIONS;
    }

    public static void setAccessToken(final String token) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCE_ACCESS_TOKEN, token);
        editor.apply();
        mAccessToken = token;
        LogUtils.logD(TAG, "setAccessToken = " + mAccessToken);
    }

    public static void setRefreshToken(final String token) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCE_REFRESH_TOKEN, token);
        editor.apply();
        mRefreshToken = token;
        LogUtils.logD(TAG, "mRefreshToken = " + mRefreshToken);
    }

    public static void setAccessTokenExpires(final long accessTokenExpires) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(APP_PREFERENCE_ACCESS_TOKEN_EXPIRES, accessTokenExpires);
        editor.apply();
        mAccessTokenExpires = accessTokenExpires;
        LogUtils.logD(TAG, "mAccessTokenExpires = " + mAccessTokenExpires);
    }

    public static void setRefreshTokenExpires(final long refreshTokenExpires) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putLong(APP_PREFERENCE_REFRESH_TOKEN_EXPIRES, refreshTokenExpires);
        editor.apply();
        mRefreshTokenExpires = refreshTokenExpires;
        LogUtils.logD(TAG, "mRefreshTokenExpires = " + mRefreshTokenExpires);
    }

    public static void setImei(final String imei) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCE_IMEI, imei);
        editor.apply();
        mImei = imei;
    }

    public static void setDeviceName(final String deviceName) {
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCE_NAME_DEVICE, deviceName);
        editor.apply();
        mDeviceName = deviceName;
    }

    public static void setUserInfo(User user) {
        mUser = user;
        Crashlytics.setUserEmail(user.email);
        Gson gson = new Gson();
        String json = gson.toJson(mUser);
        SharedPreferences.Editor prefsEditor = mSettings.edit();
        prefsEditor.putString(APP_PREFERENCE_USER, json);
        prefsEditor.apply();
    }

    public static boolean accessTokenExist() {
        LogUtils.logD("GETMATERIAL", "accessTokenExist = " + (mAccessToken != null && !mAccessToken.isEmpty()));
        return (mAccessToken != null && !mAccessToken.isEmpty());
    }

    public static void updateUserTermsAgreement(int terms_agreement) {
        mUser.terms_agreement = terms_agreement;
        setUserInfo(mUser);
    }

    public static void updateUserPhone(String phone) {
        mUser.phone = phone;
        setUserInfo(mUser);
    }

    public static void updateLanguage() {
//        String language = Locale.getDefault().toLanguageTag().substring(0, Locale.getDefault().toLanguageTag().indexOf("-"));
//        if (!language.equals(mLanguage)) {
//            if(language.equals("ua") || language.equals("ru"))
//                mLanguage = language;
//            else
//                mLanguage = "ua";
//            EventRouter.send(new EventLocalizationUpdate());
//        }
        mLanguage = "ua";
    }

    public static boolean isAuthStart() {
        return mAuthStart;
    }

    public static void startAuth() {
        mAuthStart  = true;
    }

    public static void endAuth() {
         mAuthStart = false;
    }

    public static void updateLikiWikiToken(String likiwiki_auth_token) {
        mUser.likiwiki_auth_token = likiwiki_auth_token;
        setUserInfo(mUser);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());



        mUpdateLastTimeUserNews = 0;
        mUpdateLastTimeMaterials = 0;
        mUpdateLastTimeNotifications = 0;

        //mLanguage = Locale.getDefault().toLanguageTag().substring(0, Locale.getDefault().toLanguageTag().indexOf("-"));
        updateLanguage();
        LogUtils.logD(TAG, "mLanguage = " + mLanguage);

        applicationContext = getApplicationContext();

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            LogUtils.logW(TAG, "getInstanceId failed " + task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        mPushToken = task.getResult().getToken();
                        LogUtils.logD(TAG, "mPushToken = " + mPushToken);
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, mPushToken);
                        LogUtils.logD(TAG, msg);
                    }
                });

        //unregister not needed because App last destroy and call Terminate() not guaranteed
        //EventRouter.register(this);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        if (mSettings.contains(APP_PREFERENCE_TERM_AGREEMENTS)) {
            mTermAgreements = mSettings.getBoolean(APP_PREFERENCE_TERM_AGREEMENTS, false);
            LogUtils.logD(TAG, "mTermAgreements = " + mTermAgreements);
        }

        if (mSettings.contains(APP_PREFERENCE_ACCESS_TOKEN)) {
            mAccessToken = mSettings.getString(APP_PREFERENCE_ACCESS_TOKEN, "");
            LogUtils.logD(TAG, "mAccessToken = " + mAccessToken);
        }
        if (mSettings.contains(APP_PREFERENCE_IMEI)) {
            mImei = mSettings.getString(APP_PREFERENCE_IMEI, "");
        }
        if (mSettings.contains(APP_PREFERENCE_NAME_DEVICE)) {
            mDeviceName = mSettings.getString(APP_PREFERENCE_NAME_DEVICE, "");
        }
        if (mSettings.contains(APP_PREFERENCE_REFRESH_TOKEN)) {
            mRefreshToken = mSettings.getString(APP_PREFERENCE_REFRESH_TOKEN, "");
            LogUtils.logD(TAG, "mRefreshToken = " + mRefreshToken);
        }


        if (mSettings.contains(APP_PREFERENCE_ACCESS_TOKEN_EXPIRES)) {
            mAccessTokenExpires = mSettings.getLong(APP_PREFERENCE_ACCESS_TOKEN_EXPIRES, 0);
            LogUtils.logD(TAG, "mAccessTokenExpires = " + mAccessTokenExpires);
        }
        if (mSettings.contains(APP_PREFERENCE_REFRESH_TOKEN_EXPIRES)) {
            mRefreshTokenExpires = mSettings.getLong(APP_PREFERENCE_REFRESH_TOKEN_EXPIRES, 0);
            LogUtils.logD(TAG, "mRefreshTokenExpires = " + mRefreshTokenExpires);
        }

        if (mSettings.contains(APP_PREFERENCE_USER)) {
            Gson gson = new Gson();
            String json = mSettings.getString(APP_PREFERENCE_USER, "");
            if (json != null && !json.isEmpty()) {
                mUser = gson.fromJson(json, User.class);
                Crashlytics.setUserEmail(mUser.email);
            }
        }
        //Core.get().AuthorizationControl().updateAccessToken();
    }

    public static void logout() {
        mRefreshToken = null;
        mAccessToken = null;
        mUser = null;
        mDeviceName = null;
        mImei = null;
        mTermAgreements = false;
        mUpdateLastTimeMaterials = 0;
        mUpdateLastTimeNotifications = 0;
        mUpdateLastTimeUserNews = 0;
        SharedPreferences.Editor editor = mSettings.edit();
        editor.clear();
        editor.apply();
        Core.get().VisitsControl().clearData();
        Core.get().UserContentControl().clearData();
        Core.get().NotificationControl().clearData();
        Core.get().NewsControl().clearData();
    }
}

