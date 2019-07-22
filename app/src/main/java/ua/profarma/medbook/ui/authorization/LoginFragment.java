package ua.profarma.medbook.ui.authorization;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;

import ua.profarma.medbook.BuildConfig;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.TextUtils;

public class LoginFragment extends MedBookFragment implements EventListener {

    private static final String EMPTY_STRING = "";

    private AppCompatEditText emailTIET;
    private AppCompatEditText   passwordTIET;
    private Button              btnLogin;
    private TextView            tvRegistration;
    private TextView            tvRestore;
    private ProgressBar         progressBar;
    private TextInputLayout mTextInputLayoutLogin; //getString(R.string.error)
    private ProgressBar pb; //getString(R.string.error)


    public static LoginFragment newInstance() {
        final LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_login, container, false);


        tvRestore = rootView.findViewById(R.id.fragment_login_tv_restore_password);
        mTextInputLayoutLogin = rootView.findViewById(R.id.fragment_login_til_email);
        emailTIET = rootView.findViewById(R.id.fragment_login_tiet_email);
        passwordTIET = rootView.findViewById(R.id.fragment_login_tiet_password);
        btnLogin = rootView.findViewById(R.id.fragment_login_btn_login);
        tvRegistration = rootView.findViewById(R.id.fragment_login_tv_registration);
        pb = rootView.findViewById(R.id.pb);

        onLocalizationUpdate();

        Spinner langSpinner = rootView.findViewById(R.id.fragment_login_spinner);
        langSpinner.setVisibility(View.GONE);
//        langSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

        tvRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                enableButtonsAndClearEditTexts(false);
                if (getActivity() instanceof IAuthActivity) {
                    ((IAuthActivity)getActivity()).onRestore();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = null;
                email = emailTIET.getText().toString();
                String password = null;
                password = passwordTIET.getText().toString();
                if (TextUtils.isValidEmail(email))
                    if (password.length() >= AuthorizationActivity.MIN_SYMBOLS_PASSWORD) {
                        Core.get().AuthorizationControl().authorize(email, password);
                        pb.setVisibility(View.VISIBLE);


                    } else {
                        String sf = getString(R.string.min_password, AuthorizationActivity.MIN_SYMBOLS_PASSWORD);
                        Toast.makeText(getActivity(),sf, Toast.LENGTH_SHORT).show();
//                        enableButtonsAndClearEditTexts(true);
                    }
                else {

                    Toast.makeText(getActivity(), R.string.error_email, Toast.LENGTH_SHORT).show();
//                    enableButtonsAndClearEditTexts(true);
                }
            }
        });
        tvRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                enableButtonsAndClearEditTexts(false);
                if (getActivity() instanceof IAuthActivity) {
                    ((IAuthActivity)getActivity()).onRegistrationStep1();
                }
            }
        });
        EventRouter.register(this);
        return rootView;
    }

    private void showError(String error) {
        mTextInputLayoutLogin.setError(error);
    }

    private void hideError() {
        mTextInputLayoutLogin.setError(EMPTY_STRING);
    }

    @Override
    public void onDestroyView() {
        EventRouter.unregister(this);
        super.onDestroyView();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOGIN_FAILURE:
                pb.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {
        setText(btnLogin, R.id.fragment_login_btn_login);
        setText(tvRegistration, R.id.fragment_login_tv_registration);
        setText(tvRestore, R.id.fragment_login_tv_restore_password);
    }

//    private void enableButtonsAndClearEditTexts(boolean enable) {
//        passwordTIET.setText("");
//        btnLogin.setEnabled(enable);
//        tvRestore.setEnabled(enable);
//        tvRegistration.setEnabled(enable);
//        emailTIET.setEnabled(enable);
//        passwordTIET.setEnabled(enable);
//    }
}
