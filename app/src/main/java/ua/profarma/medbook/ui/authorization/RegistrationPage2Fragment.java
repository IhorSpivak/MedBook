package ua.profarma.medbook.ui.authorization;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.authorization.EventSelectedMedicalInstitution;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.types.SpecializationTranslate;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

public class RegistrationPage2Fragment extends Fragment implements EventListener {

    private ArrayList<SpecializationTranslate>      mList = new ArrayList<>();
    private AutoCompleteTextView                    mEditTextSpecialization;
    private TextView                                mEditTextMedicalInstitute;
    private String TAG = "AppMedbook/RegistrationPage2Fragment";

    private int     mIdSpec         = -1;
    private int     mIdMecInst      = -1;
    private String  mMedInstName    = null;


    public static RegistrationPage2Fragment newInstance() {

        final RegistrationPage2Fragment fragment = new RegistrationPage2Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_registration_step_2, container, false);

        mEditTextMedicalInstitute = rootView.findViewById(R.id.fragment_registration_page_2_tv_medical_institute);

        LinearLayout mapsBtn = rootView.findViewById(R.id.fragment_registration_page_2_til_medical_institutes);
        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditTextSpecialization.getWindowToken(), 0);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {
                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            showMessageForNeedPermission();
                        } else
                            showMessageForNeedPermission();
                    } else {
                        if(getActivity() instanceof IAuthActivity){
                            ((IAuthActivity)getActivity()).onMedicalInstituteNearestSearch();
                        }
                    }
                } else {
                    if(getActivity() instanceof IAuthActivity){
                        ((IAuthActivity)getActivity()).onMedicalInstituteNearestSearch();
                    }
                }
            }
        });

        mEditTextSpecialization = rootView.findViewById(R.id.fragment_registration_page_2_tiet_spes);
        mEditTextSpecialization.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                SpecializationTranslate selection = (SpecializationTranslate) parent.getItemAtPosition(position);
                mIdSpec = selection.specialization_id;
                LogUtils.logD(TAG, "hide keyboard");
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mEditTextSpecialization.getWindowToken(), 0);
            }
        });

        EventRouter.register(this);
        Core.get().getDataForRegistrationControl().getSpecializations();

        Button btnNext = rootView.findViewById(R.id.fragment_registration_page_2_bnt_next);
        TextView tvPrev = rootView.findViewById(R.id.fragment_registration_page_2_tv_back);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIdMecInst == -1){
                    DialogBuilder.showInfoDialog(getActivity(),
                            Core.get().LocalizationControl().getText(R.id.general_message),
                            Core.get().LocalizationControl().getText(R.id.dont_select_med_inst));
                }
                else {
                    if(mIdSpec == -1){
                        DialogBuilder.showInfoDialog(getActivity(),
                                Core.get().LocalizationControl().getText(R.id.general_message),
                                Core.get().LocalizationControl().getText(R.id.dont_select_spes));
                    }
                    else {
                        Core.get().AuthorizationControl().setSpecialization_id(mIdSpec);
                        Core.get().AuthorizationControl().setMedical_institution_id(mIdMecInst);
                        //EventRouter.send(new EventFragmentRegistrationPage3());
                        if(getActivity() instanceof IAuthActivity){
                            ((IAuthActivity)getActivity()).onRegistrationStep3();
                        }
                    }
                }
            }
        });
        tvPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (mMedInstName != null) {
            mEditTextMedicalInstitute.setText(mMedInstName);
            mEditTextMedicalInstitute.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        return rootView;
    }

    private void showMessageForNeedPermission() {
        new AlertDialog.Builder(getActivity())
                .setMessage(getString(R.string.need_access_location))
                .setPositiveButton(getString(R.string.btn_ok), listener)
                .setNegativeButton(getString(R.string.btn_cancel), listener)
                .create()
                .show();
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {

        final int BUTTON_NEGATIVE = -2;
        final int BUTTON_POSITIVE = -1;

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;

                case BUTTON_POSITIVE:
                    ActivityCompat.requestPermissions(
                            getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            AuthorizationActivity.PERMISSIONS_REQUEST_LOCATION);
                    dialog.dismiss();
                    break;
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_REGISTRATION_LIST:
                mList.clear();
                int selectLang = -1;
                for (int i = 0; i < Core.get().getDataForRegistrationControl().getmListOfSpecialization().items.size(); i++) {
                    for(int j = 0; j < Core.get().getDataForRegistrationControl().getmListOfSpecialization().items.get(i).translations.length; j++){
                        //Log.d("ghgjghjghjhgj", Core.get().getDataForRegistrationControl().getmListOfSpecialization().items.get(i).translations[j].language +", " + Locale.getDefault().toLanguageTag());
                        if(Core.get().getDataForRegistrationControl().getmListOfSpecialization().items.get(i).translations[j].language.substring(0, 2).equals(Locale.getDefault().toLanguageTag().substring(0, 2) )){
                            selectLang = j;
                        }
                    }
                    if (selectLang == -1){
                        for(int j = 0; j < Core.get().getDataForRegistrationControl().getmListOfSpecialization().items.get(i).translations.length; j++){
                            if(Core.get().getDataForRegistrationControl().getmListOfSpecialization().items.get(i).translations[j].language.substring(0, 2).equals("uk")){
                                selectLang = j;
                            }
                        }
                    }
                    //Log.d("ghgjghjghjhgj", "selectLang = " + selectLang);

                    mList.add(Core.get().getDataForRegistrationControl().getmListOfSpecialization().items.get(i).translations[selectLang]);
                }
                ArrayAdapter<SpecializationTranslate> mAutoCompleteAdapter = new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_dropdown_item_1line, mList);

                mEditTextSpecialization.setAdapter(mAutoCompleteAdapter);
                break;
            case Event.EVENT_SELECTED_MEDICAL_INSTITUTION:
                EventSelectedMedicalInstitution eventSelectedMedicalInstitution = (EventSelectedMedicalInstitution) event;
                mMedInstName = eventSelectedMedicalInstitution.getmTitle();
                mIdMecInst = eventSelectedMedicalInstitution.getmId();

                break;
        }
    }

}
