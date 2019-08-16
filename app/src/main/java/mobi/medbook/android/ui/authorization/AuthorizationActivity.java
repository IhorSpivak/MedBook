package mobi.medbook.android.ui.authorization;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.Constants;
import mobi.medbook.android.utils.LogUtils;


public class AuthorizationActivity extends MedBookActivity implements EventListener, IAuthActivity {
    private static final String TAG_AUTHORIZATION_OPTIONS = "ua.medbook.tag.authorization_options";

    private static final String TAG_REGISTRATION_STEP_1 = "ua.medbook.tag.registration_step_1";
    private static final String TAG_REGISTRATION_STEP_2 = "ua.medbook.tag.registration_step_2";
    private static final String TAG_REGISTRATION_STEP_3 = "ua.medbook.tag.registration_step_3";
    private static final String TAG_RESTORE_FRAGMENT = "ua.medbook.tag.restore_fragment";
    private static final String TAG_CHECK_EMAIL_INFO_FRAGMENT = "ua.medbook.tag.check_email_info";
    private static final String TAG_MAP = "ua.medbook.tag.map";
    private int count = 0;

    public static final int MIN_SYMBOLS_PASSWORD = 5;

    public final static int PERMISSIONS_REQUEST_LOCATION = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.logD("AuthorizationControl", "AuthorizationActivity onCreate");
        setContentView(R.layout.activity_authorization);


        if (savedInstanceState == null) {
            showFragment(LoginFragment.newInstance(), TAG_AUTHORIZATION_OPTIONS, false);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
                    PackageManager.PERMISSION_GRANTED) {
                showMessageForNeedPermission();
            } else {
                readPhoneInfo();
            }
        } else {
            readPhoneInfo();
        }
    }

    private void readPhoneInfo() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
        if (imei != null && !imei.isEmpty()) {
            LogUtils.logE("imht6rufghygjgei", "imei = " + imei);
            App.setImei(imei);
        } else {
            LogUtils.logE("imht6rufghygjgei", "(Build)imei = " + android.os.Build.SERIAL);
            App.setImei(android.os.Build.SERIAL);
        }

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            LogUtils.logE("imht6rufghygjgei", "name = " + model);
            App.setDeviceName(model);
        } else {
            LogUtils.logE("imht6rufghygjgei", "name = " + (manufacturer) + " " + model);
            App.setDeviceName(manufacturer + " " + model);
        }
    }

    private void showFragment(final Fragment fragment, final String tag, final boolean root) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.right_to_center, R.anim.center_to_left, R.anim.left_to_center, R.anim.center_to_right);
        transaction.addToBackStack(tag);

        transaction.replace(R.id.activity_authorization_container, fragment, tag);
        transaction.commit();
        count++;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showFragment(NewMedicalInstitutionFragment.newInstance(), TAG_MAP, false);
                } else {

                }
            }
            case Constants.READ_PHONE_STATE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readPhoneInfo();
                } else {
                    finishAffinity();
                }
                break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }

        }
    }

    @Override
    protected void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0 || count == 0) {

            this.finish();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            AppUtils.toastMessage(Core.get().LocalizationControl().getText(R.id.app_exit_on_back_hint), true);
            count--;
        } else {
            getSupportFragmentManager().popBackStack();
            count--;
            //TODO hardcode
            for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
                if (getSupportFragmentManager().getFragments().get(i).getTag().contains("android:switcher")) {
                    FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
                    trans.remove(getSupportFragmentManager().getFragments().get(i));
                    trans.commit();
                }
            }
        }
    }


    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_REGISTRATION_SUCCESS:
                getSupportFragmentManager().popBackStackImmediate(TAG_REGISTRATION_STEP_1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStackImmediate(TAG_REGISTRATION_STEP_2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStackImmediate(TAG_REGISTRATION_STEP_3, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                showFragment(CheckEmailForInstructionFragment.newInstance(), TAG_CHECK_EMAIL_INFO_FRAGMENT, false);
                break;
            case Event.EVENT_LOGIN_SUCCESS:
                LogUtils.logD("AuthorizationControl", "AuthorizationActivity EVENT_LOGIN_SUCCESS");

                long time = System.currentTimeMillis() / 1000;
                Core.get().NotificationControl().getNotifications(time, time + 86400, 1000);
                Core.get().UserControl().getUser();
                if (Core.get().UserContentControl().getListMaterial() == null || Core.get().UserContentControl().getListMaterial().isEmpty())
                    LogUtils.logD("GETMATERIAL", "EVENT_LOGIN_SUCCESS TodayFragment");
                Core.get().UserContentControl().getMaterials();
                Core.get().NewsControl().getUserNews();
                Core.get().VisitsControl().getUserVisits();
                Core.get().VisitsControl().getDashboardVisits();
                App.endAuth();
                finish();
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {

    }


    private void showMessageForNeedPermission() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.need_access_read_phone_state))
                .setPositiveButton(getString(R.string.btn_ok), listener)
                .setNegativeButton(getString(R.string.btn_cancel), listener)
                .create()
                .show();
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        final int BUTTON_NEGATIVE = -2;
        final int BUTTON_POSITIVE = -1;

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_NEGATIVE:
                    dialog.dismiss();
                    finishAffinity();
                    break;

                case BUTTON_POSITIVE:
                    checkReadPhoneStatePermissions();
                    dialog.dismiss();
                    break;
            }
        }
    };

    private boolean checkReadPhoneStatePermissions() {
        if (ContextCompat.checkSelfPermission(this, Constants.READ_PHONE_STATE_PERMISSION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Constants.READ_PHONE_STATE_PERMISSION}, Constants.READ_PHONE_STATE_PERMISSION_REQUEST_CODE);
            return false;
        }

        return true;
    }

    @Override
    public void onRegistrationStep1() {
        showFragment(RegistrationPage1Fragment.newInstance(), TAG_REGISTRATION_STEP_1, false);
    }

    @Override
    public void onRegistrationStep2() {
        showFragment(RegistrationPage2Fragment.newInstance(), TAG_REGISTRATION_STEP_2, false);
    }

    @Override
    public void onRegistrationStep3() {
        showFragment(RegistrationPage3Fragment.newInstance(), TAG_REGISTRATION_STEP_2, false);
    }

    @Override
    public void onMedicalInstituteNearestSearch() {
        showFragment(NewMedicalInstitutionFragment.newInstance(), TAG_MAP, false);
    }

    @Override
    public void onRestore() {
        showFragment(RestoreFragment.newInstance(), TAG_RESTORE_FRAGMENT, false);
    }

    @Override
    public void onRestoreSuccess() {
        getSupportFragmentManager().popBackStackImmediate(TAG_RESTORE_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        showFragment(CheckEmailForInstructionFragment.newInstance(), TAG_CHECK_EMAIL_INFO_FRAGMENT, false);
    }
}
