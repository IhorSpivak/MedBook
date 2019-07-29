package ua.profarma.medbook.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.EventEndQrCode;
import ua.profarma.medbook.events.EventSetPhoneStep_1;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.ui.FeedbackFragment;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.DialogBuilder;

public class ProfileActivity extends MedBookActivity implements EventListener, IStartScanQR {

    private final static String FEEDBACK_FRAGMENT = "ProfileActivity.fragment.feedback";
    private final static String PROFILE_FRAGMENT = "ProfileActivity.fragment.profile";
    private final static String VERIFICATION_PHONE_FRAGMENT_STEP_1 = "ProfileActivity.fragment.verification_step_1";
    private final static String VERIFICATION_PHONE_FRAGMENT_STEP_2 = "ProfileActivity.fragment.verification_step_2";


    private ProgressBar pb;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pb = findViewById(R.id.pb);

        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.activity_profile_container, ProfileFragment.newInstance(), PROFILE_FRAGMENT);
        transaction2.commit();

        EventRouter.register(this);
        Core.get().UserControl().getUser();
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_LOGOUT:
                finish();
                break;
            case Event.EVENT_START_FEEDBACK_FRAGMENT:
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.addToBackStack(FEEDBACK_FRAGMENT);
                transaction3.replace(R.id.activity_profile_container, FeedbackFragment.newInstance(), FEEDBACK_FRAGMENT);
                transaction3.commit();
                break;
            case Event.EVENT_CLOSE_FEEDBACK_FRAGMENT:
                getSupportFragmentManager().popBackStackImmediate(FEEDBACK_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case Event.EVENT_START_VERIFICATION_PHONE_FRAGMENT:
                FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                transaction4.addToBackStack(VERIFICATION_PHONE_FRAGMENT_STEP_1);
                transaction4.replace(R.id.activity_profile_container, VerificationPhoneStep1Frgament.newInstance(), VERIFICATION_PHONE_FRAGMENT_STEP_1);
                super.onActivityResult(-1, -1, null);
                transaction4.commit();
                break;
            case Event.EVENT_SET_PHONE_STEP_1:
                EventSetPhoneStep_1 eventSetPhoneStep_1 = (EventSetPhoneStep_1) event;
                FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                transaction5.addToBackStack(VERIFICATION_PHONE_FRAGMENT_STEP_2);
                transaction5.replace(R.id.activity_profile_container, VerificationPhoneStep2Frgament.newInstance(eventSetPhoneStep_1.getKey(),
                        eventSetPhoneStep_1.getPhone()), VERIFICATION_PHONE_FRAGMENT_STEP_2);
                transaction5.commit();
                break;

            case Event.EVENT_CLOSE_VERIFICATION_PHONE_STEPS:
                getSupportFragmentManager().popBackStackImmediate(VERIFICATION_PHONE_FRAGMENT_STEP_2, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                getSupportFragmentManager().popBackStackImmediate(VERIFICATION_PHONE_FRAGMENT_STEP_1, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;

            case Event.EVENT_END_QR_CODE:
                EventEndQrCode eventEndQrCode = (EventEndQrCode) event;
                DialogBuilder.showBottomResultDialog(this, eventEndQrCode.getMessage(), new BottomResultDialog.Callback() {
                    @Override
                    public void onClose() {
                        //onBackPressed();
                    }
                });
                pb.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                //Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                checkCode(result.getContents());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkCode(String code) {

        pb.setVisibility(View.VISIBLE);

        Core.get().Api2Control().checkQr(code);
    }

    @Override
    public void startScanQR() {

        IntentIntegrator qrScan = new IntentIntegrator(this);
        qrScan.setPrompt("Відскануйте Qr код");
        qrScan.initiateScan();
    }
}
