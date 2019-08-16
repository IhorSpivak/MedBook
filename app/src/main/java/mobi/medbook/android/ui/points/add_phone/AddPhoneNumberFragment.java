package mobi.medbook.android.ui.points.add_phone;

import android.os.Bundle;
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
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.utils.DialogBuilder;


public class AddPhoneNumberFragment extends MedBookFragment {

    private Button bttAdd;
    private AppCompatEditText inputNuber;
    private TextInputLayout inputLayout;

    public static AddPhoneNumberFragment newInstance() {
        AddPhoneNumberFragment fragment = new AddPhoneNumberFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_phone_number, container, false);
        bttAdd = rootView.findViewById(R.id.fragment_add_phone_number_add_btn);
        inputNuber = rootView.findViewById(R.id.fragment_add_phone_number_input);
        inputLayout = rootView.findViewById(R.id.fragment_add_phone_number_input_layout);

        bttAdd.setOnClickListener(view -> {
            inputNuber.setEnabled(false);
            bttAdd.setEnabled(false);
            if (inputNuber.getText() != null && !inputNuber.getText().toString().isEmpty() && inputNuber.getText().toString().length() == 13) {
                Core.get().Api2Control().verificationPhone(inputNuber.getText().toString(), true);
            } else {
                inputNuber.setEnabled(true);
                bttAdd.setEnabled(true);
                DialogBuilder.showInfoDialog(getActivity(), "Повідомлення", "Невірний формат вводу номера. Він повинен бути у форматі +380501112233");
            }
        });

        return rootView;
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.SET_PHONE_NUMBER_FAILED_STEP_1) {
            inputNuber.setEnabled(true);
            bttAdd.setEnabled(true);
        }
    }

    @Override
    protected void onLocalizationUpdate() {
        bttAdd.setText(Core.get().LocalizationControl().getText(R.id.fragment_add_phone_number_add_btn));
        inputLayout.setHint(Core.get().LocalizationControl().getText(R.id.fragment_add_phone_number_input));
    }
}
