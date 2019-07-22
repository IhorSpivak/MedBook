package ua.profarma.medbook.ui.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.utils.AppUtils;

//	        "username": "volodymyr.mokhonko",
//            "email": "volodymyr.mokhonko@gmail.com",
//            "password": "qwerty123",

//            "first_name": "Володимир",
//            "middle_name": "Вікторович",
//            "last_name": "Мохонько"
//              specialization_id": 1,
//        "medical_institution_id": 1,
//        "phone": "+380930909090"

public class RegistrationPage1Fragment extends Fragment {

    public static RegistrationPage1Fragment newInstance() {
        final RegistrationPage1Fragment fragment = new RegistrationPage1Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_registration_step_1, container, false);
        final AppCompatEditText firstNameEditText = rootView.findViewById(R.id.fragment_registration_page_1_tiet_first_name);
        final AppCompatEditText middleNameEditText = rootView.findViewById(R.id.fragment_registration_page_1_tiet_middle_name);
        final AppCompatEditText lastNameEditText = rootView.findViewById(R.id.fragment_registration_page_1_tiet_last_name);

        Button btnNext = rootView.findViewById(R.id.fragment_registration_page_1_bnt_next);
        TextView tvPrev = rootView.findViewById(R.id.fragment_registration_page_1_tv_back);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstNameEditText.getText().toString().isEmpty() && firstNameEditText.getText().length() >= 2) {
                    if (!middleNameEditText.getText().toString().isEmpty() && middleNameEditText.getText().length() >= 2) {
                        if (!lastNameEditText.getText().toString().isEmpty() && lastNameEditText.getText().length() >= 2) {
                            Core.get().AuthorizationControl().setFirstName(firstNameEditText.getText().toString());
                            Core.get().AuthorizationControl().setMiddleName(middleNameEditText.getText().toString());
                            Core.get().AuthorizationControl().setLastName(lastNameEditText.getText().toString());
                            if(getActivity() instanceof IAuthActivity){
                                ((IAuthActivity)getActivity()).onRegistrationStep2();
                            }
                        } else {
                            Toast.makeText(getActivity(), R.string.min_count_lastname, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), R.string.min_count_middlename, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), R.string.min_count_firstname, Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }


}
