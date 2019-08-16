package mobi.medbook.android.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.material.textfield.TextInputLayout;


import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.profile.EventSetPhoneNumberFailedStep1;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.utils.DialogBuilder;


public class VerificationPhoneStep1Frgament extends MedBookFragment {
    public static VerificationPhoneStep1Frgament newInstance() {
        VerificationPhoneStep1Frgament fragment = new VerificationPhoneStep1Frgament();
        return fragment;
    }

    private AppCompatEditText phoneEt;
    private Button btn;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_verification_phone, container, false);
        btn = rootView.findViewById(R.id.fragment_verification_btn);
        phoneEt = rootView.findViewById(R.id.fragment_verification_phone_new_number);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneEt.setEnabled(false);
                btn.setEnabled(false);
                if (phoneEt.getText() != null && !phoneEt.getText().toString().isEmpty() && phoneEt.getText().toString().length() == 13) {
                    if (phoneEt.getText().toString().equals(App.getUser().phone)) {
                        //AppUtils.toastError("Для зміни потрібно обрати новий номер, неможливо змінити поточний номер на поточний ", false);
                        DialogBuilder.showInfoDialog(getActivity(), "Повідомлення", "Для зміни потрібно обрати новий номер, неможливо змінити поточний номер на поточний");
                        phoneEt.setEnabled(true);
                        btn.setEnabled(true);
                    } else {
                        Core.get().Api2Control().verificationPhone(phoneEt.getText().toString(), true);
                    }
                } else {
                    phoneEt.setEnabled(true);
                    btn.setEnabled(true);
                    //AppUtils.toastError("Невірний формат вводу номера. Він повинен бути у форматі +380501112233", false);
                    DialogBuilder.showInfoDialog(getActivity(), "Повідомлення", "Невірний формат вводу номера. Він повинен бути у форматі +380501112233");

                }

            }
        });

        ImageView btnClose = rootView.findViewById(R.id.fragment_verification_phone_back);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });

        AppCompatEditText tiedOldNumber = rootView.findViewById(R.id.fragment_verification_phone_old_number);
        TextInputLayout tilOldNumber = rootView.findViewById(R.id.fragment_verification_phone_old_number_lay);
        boolean oldNumber = App.getUser().phone == null || App.getUser().phone.isEmpty();
        tilOldNumber.setVisibility(oldNumber ? View.INVISIBLE : View.VISIBLE);
        if (!oldNumber)
            tiedOldNumber.setText(App.getUser().phone);

        return rootView;
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if(event.getEventId() == Event.SET_PHONE_NUMBER_FAILED_STEP_1){
            DialogBuilder.showInfoDialog(getActivity(), "Повідомлення", ((EventSetPhoneNumberFailedStep1)event).getMessage());
            phoneEt.setEnabled(true);
            btn.setEnabled(true);
        }
    }
}
