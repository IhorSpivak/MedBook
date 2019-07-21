package ua.profarma.medbook.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.EventCloseVerificationFragmentSteps;
import ua.profarma.medbook.events.EventSetPhoneStep_2;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.utils.AppUtils;

public class VerificationPhoneStep2Frgament extends Fragment implements EventListener {

    private static final String ARGS_KEY = "verification.step_2.code";
    private static final String ARGS_PHONE = "verification.step_2.phone";

    private int key;
    private String phone;

    public static VerificationPhoneStep2Frgament newInstance(int key, String phone) {
        VerificationPhoneStep2Frgament fragment = new VerificationPhoneStep2Frgament();

        Bundle args = new Bundle();
        args.putInt(ARGS_KEY, key);
        args.putString(ARGS_PHONE, phone);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_verification_phone_step_2, container, false);

        ImageView btnClose = rootView.findViewById(R.id.fragment_verification_phone_step_2_back);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventCloseVerificationFragmentSteps());
            }
        });

        TextView tvPhone = rootView.findViewById(R.id.fragment_verification_phone_step_2_phone);
        final AppCompatEditText edCode = rootView.findViewById(R.id.fragment_verification_phone_step_2_code_et);
        Button btnAgainSendCode = rootView.findViewById(R.id.fragment_verification_phone_step_2_btn_again);
        Button btnSaveCode = rootView.findViewById(R.id.fragment_verification_phone_step_2_btn_save);

        tvPhone.setText(phone);
        btnAgainSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Core.get().Api2Control().verificationPhone(phone, false);
                edCode.setText("");
            }
        });
        btnSaveCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edCode.getText().toString()!= null && !edCode.getText().toString().isEmpty() && Integer.valueOf(edCode.getText().toString()) == key) {
                    Core.get().UserControl().updatePhone(phone);
                } else {
                    AppUtils.toastError("Ви ввели невірний код підтвердження, спробуйте ще раз", true);
                }
            }
        });
        EventRouter.register(this);
        return rootView;
    }

    @Override
    public void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

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
