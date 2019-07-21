package ua.profarma.medbook.ui.points;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.EventSetPhoneStep_1;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.points.EventExecuteTransaction;
import ua.profarma.medbook.events.points.EventGetSMSExhangePoints;
import ua.profarma.medbook.events.profile.EventSetPhoneNumberFailedStep1;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.ui.points.Likiwiki.ExchangeLikiWikiFragment;
import ua.profarma.medbook.ui.points.add_phone.AddPhoneNumberCheckSMSFragment;
import ua.profarma.medbook.ui.points.add_phone.AddPhoneNumberFragment;
import ua.profarma.medbook.ui.points.add_phone.IOnAddPhoneNumber;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

public class ExchangePointsForFishkaActivity extends MedBookActivity implements IOnAddPhoneNumber {
    public static final String CARD_NUMBER = "ExchangePointsForFishka_CARD_NUMBER";
    private final String TAG_EXCHANGE_POINT = "ExchangePointsForFishkaActivity.ExchangePointsFragment";
    private final String TAG_EXCHANGE_POINT_SMS = "ExchangePointsForFishkaActivity.ExchangePointsSMSFragment";
    private final String TAG_SUCCESS_EXCHANGE = "ExchangePointsForFishkaActivity.SuccessExchangeFragment";
    private final String TAG_ADD_PHONE_NUMBER_CHECK_SMS = "ExchangePointsForFishkaActivity.TAG_ADD_PHONE_NUMBER_CHECK_SMS";
    private final String TAG_ADD_PHONE_NUMBER = "ExchangePointsForFishkaActivity.TAG_ADD_PHONE_NUMBER";

    private TextView title;
    private TextView subTitle;
    private String mCardNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_points_for_fishka);
        if (getIntent() != null) {
            mCardNumber = getIntent().getStringExtra(CARD_NUMBER);
        }

        ImageView imvClose = findViewById(R.id.activity_exchange_points_for_fishka_close);
        title = findViewById(R.id.activity_exchange_points_for_fishka_title);
        subTitle = findViewById(R.id.activity_exchange_points_for_fishka_number_card);
        imvClose.setOnClickListener(view -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                finish();
            else {
                onBackPressed();
            }
        });
        showFragment(ExchangePointsFragment.newInstance(), TAG_EXCHANGE_POINT, true);
        onLocalizationUpdate();
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                onLocalizationUpdate();
            }
        });
    }

    @Override
    protected void onLocalizationUpdate() {
        LogUtils.logD("ygvhjhgkmhjb", "BackStackEntryCount = " + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() == 0)
            title.setText(Core.get().LocalizationControl().getText(R.id.activity_exchange_points_for_fishka_title));
        else
            title.setText(Core.get().LocalizationControl().getText(R.id.activity_exchange_points_for_fishka_title_2));
        subTitle.setText(String.format(Core.get().LocalizationControl().getText(R.id.activity_exchange_points_for_fishka_number_card), mCardNumber));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_LOGOUT)
            finish();
        switch (event.getEventId()) {
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
                showFragment(ExchangePointsFragment.newInstance(), TAG_EXCHANGE_POINT, true);
                break;

            case Event.EVENT_EXECUTE_TRANSACTION:
                EventExecuteTransaction eventExecuteTransaction = (EventExecuteTransaction) event;
                if (eventExecuteTransaction.isStatus()) {
                    getSupportFragmentManager().popBackStack();
                    showFragment(ExchangePointsFishkaSuccessFragment.newInstance(eventExecuteTransaction.getData(), eventExecuteTransaction.getMessage()), TAG_SUCCESS_EXCHANGE, true);
                } else {
                    DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), eventExecuteTransaction.getMessage());
                }

                break;
            case Event.EVENT_GET_SMS_EXCHANGE_POINTS:
                EventGetSMSExhangePoints eventGetSMSExhangePoints = (EventGetSMSExhangePoints) event;
                if (eventGetSMSExhangePoints.isStatus()) {
                    if (getSupportFragmentManager().getBackStackEntryCount() == 0)
                        showFragment(ExchangePointsSMSFragment.newInstance("fishka", eventGetSMSExhangePoints.getKey(), eventGetSMSExhangePoints.getTransaction(), eventGetSMSExhangePoints.getPointsExchange(), mCardNumber), TAG_EXCHANGE_POINT_SMS, false);
                } else {
                    if (eventGetSMSExhangePoints.getMessage() != null && !eventGetSMSExhangePoints.getMessage().isEmpty()) {
                        DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), eventGetSMSExhangePoints.getMessage());
                    }
                }
                break;
        }
    }

    private void showFragment(final Fragment fragment, final String tag, final boolean root) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.setCustomAnimations(R.anim.right_to_center, R.anim.center_to_left, R.anim.left_to_center, R.anim.center_to_right);
        if (!root) transaction.addToBackStack(tag);
        transaction.replace(R.id.activity_exchange_points_for_fishka_container, fragment, tag);
        transaction.commit();
    }


    @Override
    public void onAddPhoneNumber() {
        showFragment(AddPhoneNumberFragment.newInstance(), TAG_ADD_PHONE_NUMBER, true);
    }
}
