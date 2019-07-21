package ua.profarma.medbook.ui.points;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

public class ExchangePointsSMSFragment extends MedBookFragment {

    private Handler h;
    private Button btnSendCodeAgain;
    private Button btnExchangePoints;
    private AppCompatEditText inputCode;
    private TextInputLayout layoutCode;
    public String verification_type;
    private static final String KEY_BUNDLE_VERIFICATION_TYPE = "KEY_BUNDLE_VERIFICATION_TYPE";
    private static final String KEY_BUNDLE_KEY = "KEY_BUNDLE_KEY";
    private static final String KEY_BUNDLE_TRANSACTION = "KEY_BUNDLE_TRANSACTION";
    private static final String KEY_BUNDLE_VALUE = "KEY_BUNDLE_VALUE";
    private static final String KEY_BUNDLE_PAYMENTS_DETAILS = "KEY_BUNDLE_PAYMENTS_DETAILS";

    Handler.Callback hc = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            btnSendCodeAgain.setEnabled(true);
            return true;
        }
    };

    public static ExchangePointsSMSFragment newInstance(String verification_type, int key, String transaction_id, int value, String payments_details) {
        ExchangePointsSMSFragment fragment = new ExchangePointsSMSFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_BUNDLE_VERIFICATION_TYPE, verification_type);
        bundle.putInt(KEY_BUNDLE_KEY, key);
        bundle.putString(KEY_BUNDLE_TRANSACTION, transaction_id);
        bundle.putInt(KEY_BUNDLE_VALUE, value);
        bundle.putString(KEY_BUNDLE_PAYMENTS_DETAILS, payments_details);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exchange_points_sms, container, false);
        btnSendCodeAgain = rootView.findViewById(R.id.fragment_exchange_points_sms_send_code_again);
        btnExchangePoints = rootView.findViewById(R.id.fragment_exchange_points_sms_send_exchange_points);
        inputCode = rootView.findViewById(R.id.fragment_exchange_points_sms_input);
        layoutCode = rootView.findViewById(R.id.fragment_exchange_points_sms_input_layout);

        String transaction = getArguments().getString(KEY_BUNDLE_TRANSACTION);
        verification_type = getArguments().getString(KEY_BUNDLE_VERIFICATION_TYPE);
        int key = getArguments().getInt(KEY_BUNDLE_KEY);
        int value = getArguments().getInt(KEY_BUNDLE_VALUE);
        String payments_details = getArguments().getString(KEY_BUNDLE_PAYMENTS_DETAILS);

        btnSendCodeAgain.setOnClickListener(view -> Core.get().Api2Control().getSMSForExchangePoints(value, verification_type));

        btnExchangePoints.setOnClickListener(view -> {
                    if (inputCode.getText().length() > 0 && Integer.valueOf(inputCode.getText().toString()) == key)
                        Core.get().Api2Control().executeTransaction(transaction, key, value, payments_details);
                    else {
                        DialogBuilder.showInfoDialog(getActivity(), Core.get().LocalizationControl().getText(R.id.general_message),
                                Core.get().LocalizationControl().getText(R.id.fragment_add_phone_number_check_sms_msg_check_code));
                    }
                }
        );

        btnSendCodeAgain.setEnabled(false);
        h = new

                Handler(hc);
        h.sendEmptyMessageDelayed(1, 60 * 1000);

        return rootView;
    }


    @Override
    protected void onLocalizationUpdate() {
        btnSendCodeAgain.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_sms_send_code_again));
        btnExchangePoints.setText(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_sms_send_exchange_points));
        layoutCode.setHint(Core.get().LocalizationControl().getText(R.id.fragment_exchange_points_sms_input));
    }
}
