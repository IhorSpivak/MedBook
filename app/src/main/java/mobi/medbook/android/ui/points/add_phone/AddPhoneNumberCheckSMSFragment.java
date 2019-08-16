package mobi.medbook.android.ui.points.add_phone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.EventSetPhoneStep_2;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.utils.DialogBuilder;


public class AddPhoneNumberCheckSMSFragment extends MedBookFragment {


    private static final String ARGS_KEY = "add_phone_number_check_sms.code";
    private static final String ARGS_PHONE = "add_phone_number_check_sms.phone";

    private int key;
    private String phone;

    private Handler h;
    private Button btnAgainGetCode;
    private Button btnCheckCode;
    private TextInputLayout inputLayout;
    private AppCompatEditText input;

    Handler.Callback hc = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            btnAgainGetCode.setEnabled(true);
            return true;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) {
            return;
        }

        key = args.getInt(ARGS_KEY, 0);
        phone = args.getString(ARGS_PHONE, "");
    }

    public static AddPhoneNumberCheckSMSFragment newInstance(int key, String phone) {
        AddPhoneNumberCheckSMSFragment fragment = new AddPhoneNumberCheckSMSFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_KEY, key);
        args.putString(ARGS_PHONE, phone);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_phone_number_check_sms, container, false);

        btnAgainGetCode = rootView.findViewById(R.id.fragment_add_phone_number_check_sms_again_btn);
        btnCheckCode = rootView.findViewById(R.id.fragment_add_phone_number_check_sms_check_btn);
        inputLayout = rootView.findViewById(R.id.fragment_add_phone_number_check_sms_input_layout);
        input = rootView.findViewById(R.id.fragment_add_phone_number_check_sms_input);
        btnAgainGetCode.setEnabled(false);

        btnAgainGetCode.setOnClickListener(view -> {
            Core.get().Api2Control().verificationPhone(phone, false);
            input.setText("");
            h = new Handler(hc);
            h.sendEmptyMessageDelayed(1, 60 * 1000);
            btnAgainGetCode.setEnabled(false);
        });

        btnCheckCode.setOnClickListener(view -> {
            if (input.getText().toString() != null && !input.getText().toString().isEmpty() && Integer.valueOf(input.getText().toString()) == key) {
                Core.get().UserControl().updatePhone(phone);
            } else {
                DialogBuilder.showInfoDialog(getActivity(), Core.get().LocalizationControl().getText(R.id.general_message),
                        Core.get().LocalizationControl().getText(R.id.fragment_add_phone_number_check_sms_msg_check_code));
            }
        });


        h = new Handler(hc);
        h.sendEmptyMessageDelayed(1, 60 * 1000);

        return rootView;
    }

    @Override
    protected void onLocalizationUpdate() {
        inputLayout.setHint(Core.get().LocalizationControl().getText(R.id.fragment_add_phone_number_check_sms_input_layout));
        btnCheckCode.setHint(Core.get().LocalizationControl().getText(R.id.fragment_add_phone_number_check_sms_check_btn));
        btnAgainGetCode.setHint(Core.get().LocalizationControl().getText(R.id.fragment_add_phone_number_check_sms_again_btn));
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_SET_PHONE_STEP_2:
                key = ((EventSetPhoneStep_2) event).getKey();
                phone = ((EventSetPhoneStep_2) event).getPhone();
                break;
        }
    }
}
