package ua.profarma.medbook.ui.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.authorization.EventRegistrationUnSuccess;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.TextUtils;

public class RegistrationPage3Fragment extends MedBookFragment {

    private AppCompatEditText email;
    private AppCompatEditText password;
    private AppCompatEditText passwordConfirm;
    private Button registrationBtn;

    public static RegistrationPage3Fragment newInstance() {
        final RegistrationPage3Fragment fragment = new RegistrationPage3Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_registration_step_3, container, false);
        TextView prevTv = rootView.findViewById(R.id.fragment_registration_page_3_tv_prev);
        prevTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        registrationBtn = rootView.findViewById(R.id.fragment_registration_page_3_btn_registration);
        email = rootView.findViewById(R.id.fragment_registration_page_3_tiet_email);
        password = rootView.findViewById(R.id.fragment_registration_page_3_tiet_password);
        passwordConfirm = rootView.findViewById(R.id.fragment_registration_page_3_tiet_confirm_password);
        registrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isValidEmail(email.getText().toString())) {
                    if (password.getText().length() >= AuthorizationActivity.MIN_SYMBOLS_PASSWORD) {
                        if (password.getText().toString().equals(passwordConfirm.getText().toString())) {
                            registrationBtn.setEnabled(false);
                            Core.get().AuthorizationControl().setEmail(email.getText().toString());
                            Core.get().AuthorizationControl().setPassword(password.getText().toString());
                            Core.get().AuthorizationControl().register();
                        } else {
                            AppUtils.toastError(getString(R.string.confirm_password), true);
                        }
                    } else {
                        String sf = getString(R.string.min_password, AuthorizationActivity.MIN_SYMBOLS_PASSWORD);
                        AppUtils.toastError(sf, true);
                    }
                } else {
                    AppUtils.toastError(getString(R.string.error_email), true);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_REGISTRATION_UNSUCCESS) {
            DialogBuilder.showInfoDialog(getActivity(), Core.get().LocalizationControl().getText(R.id.general_message), ((EventRegistrationUnSuccess) event).getMessage());
            registrationBtn.setEnabled(true);
        }
    }

    @Override
    protected void onLocalizationUpdate() {

    }
}
