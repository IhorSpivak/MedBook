package mobi.medbook.android.ui.points.Likiwiki;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.EventSetPhoneStep_1;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.points.EventExecuteTransaction;
import mobi.medbook.android.events.points.EventGetSMSExhangePoints;
import mobi.medbook.android.events.profile.EventSetPhoneNumberFailedStep1;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.ui.points.ExchangePointsSMSFragment;
import mobi.medbook.android.ui.points.add_phone.AddPhoneNumberCheckSMSFragment;
import mobi.medbook.android.ui.points.add_phone.AddPhoneNumberFragment;
import mobi.medbook.android.ui.points.add_phone.IOnAddPhoneNumber;
import mobi.medbook.android.utils.DialogBuilder;


public class ExchangeLikiWikiActivity extends MedBookActivity implements IOnAddPhoneNumber {

    private final String TAG_EXCHANGE_LIKIWIKI = "TAG_EXCHANGE_LIKIWIKI";
    private final String TAG_ADD_PHONE_NUMBER = "TAG_ADD_PHONE_NUMBER";
    private final String TAG_ADD_PHONE_NUMBER_CHECK_SMS = "TAG_ADD_PHONE_NUMBER_CHECK_SMS";
    private final String TAG_EXCHANGE_POINT_SMS = "TAG_EXCHANGE_POINT_SMS";
    private final String TAG_SUCCESS_EXCHANGE = "TAG_SUCCESS_EXCHANGE";

    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_likiwiki);
        ImageView ibClose = findViewById(R.id.activity_exchange_likiwiki_close);
        ibClose.setOnClickListener(view -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                finish();
            else {
                onBackPressed();
            }
        });
        tvTitle = findViewById(R.id.activity_exchange_likiwiki_title);

        showFragment(ExchangeLikiWikiFragment.newInstance(), TAG_EXCHANGE_LIKIWIKI, true);
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_exchange_likiwiki_title));
    }

    private void showFragment(final Fragment fragment, final String tag, final boolean root) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.right_to_center, R.anim.center_to_left, R.anim.left_to_center, R.anim.center_to_right);
        if (!root) transaction.addToBackStack(tag);
        transaction.replace(R.id.activity_exchange_likiwiki_container, fragment, tag);
        transaction.commit();
    }

    @Override
    public void onAddPhoneNumber() {
        showFragment(AddPhoneNumberFragment.newInstance(), TAG_ADD_PHONE_NUMBER, true);
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_EXECUTE_TRANSACTION:
                EventExecuteTransaction eventExecuteTransaction = (EventExecuteTransaction) event;
                if (eventExecuteTransaction.isStatus()) {
                    getSupportFragmentManager().popBackStack();
                    showFragment(ExchangePointsLikiWikiSuccessFragment.newInstance(eventExecuteTransaction.getData(), eventExecuteTransaction.getMessage()), TAG_SUCCESS_EXCHANGE, true);
                } else {
                    DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), eventExecuteTransaction.getMessage());
                }

                break;

            case Event.EVENT_SET_PHONE_STEP_1:
                EventSetPhoneStep_1 eventSetPhoneStep_1 = (EventSetPhoneStep_1) event;
                showFragment(AddPhoneNumberCheckSMSFragment.newInstance(eventSetPhoneStep_1.getKey(),
                        eventSetPhoneStep_1.getPhone()), TAG_ADD_PHONE_NUMBER_CHECK_SMS, false);
                break;
            case Event.SET_PHONE_NUMBER_FAILED_STEP_1:
                DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), ((EventSetPhoneNumberFailedStep1) event).getMessage());
                break;
            case Event.EVENT_CLOSE_VERIFICATION_PHONE_STEPS:
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                showFragment(ExchangeLikiWikiFragment.newInstance(), TAG_EXCHANGE_LIKIWIKI, true);
                break;

            case Event.EVENT_GET_SMS_EXCHANGE_POINTS:
                EventGetSMSExhangePoints eventGetSMSExhangePoints = (EventGetSMSExhangePoints) event;
                if (eventGetSMSExhangePoints.isStatus()) {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                        showFragment(ExchangePointsSMSFragment.newInstance("likiwiki", eventGetSMSExhangePoints.getKey(), eventGetSMSExhangePoints.getTransaction(), eventGetSMSExhangePoints.getPointsExchange(), App.getUser().likiwiki_auth_token), TAG_EXCHANGE_POINT_SMS, false);
                } else {
                    if (eventGetSMSExhangePoints.getMessage() != null && !eventGetSMSExhangePoints.getMessage().isEmpty()) {
                        DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), eventGetSMSExhangePoints.getMessage());
                    }
                }
                break;
        }
    }
}
