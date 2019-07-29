package ua.profarma.medbook.ui.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import ua.profarma.medbook.App;
import ua.profarma.medbook.BuildConfig;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.EventEndQrCode;
import ua.profarma.medbook.events.EventLogout;
import ua.profarma.medbook.events.EventStartFeedbackFragment;
import ua.profarma.medbook.events.EventStartVerificationPhoneFragment;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

public class ProfileFragment extends Fragment implements EventListener {


    private BottomInviteFragmentDialog dialog;
    private BottomLoyaltyProgrammFragmentDialog dialog1;
    private TextView nameTv;
    private TextView mailTv;
    private TextView phoneTv;
    private TextView cityTv;
    private TextView specializationTv;
    private TextView lpuTv;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile_layout, container, false);

        ImageView btnOk = rootView.findViewById(R.id.close);
        Button btnExit = rootView.findViewById(R.id.fragment_profile_layout_exit);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onExitDialog();
;            }
        });

        nameTv = rootView.findViewById(R.id.name);
        mailTv = rootView.findViewById(R.id.mail);
        phoneTv = rootView.findViewById(R.id.phone);

        TextView versionTv = rootView.findViewById(R.id.fragment_profile_layout_version);
        if (versionTv != null) {
            String versionCode = String.valueOf(BuildConfig.VERSION_CODE);
            String versionName = BuildConfig.VERSION_NAME;

            versionTv.setText(getString(R.string.medbook_version, versionName, versionCode));

        }


        RelativeLayout llPhone = rootView.findViewById(R.id.fragment_profile_layout_ll_phone);
        llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventStartVerificationPhoneFragment());
            }
        });

        cityTv = rootView.findViewById(R.id.city);
        specializationTv = rootView.findViewById(R.id.specialization);
        lpuTv = rootView.findViewById(R.id.lpu);

        LinearLayout llFeedback = rootView.findViewById(R.id.feedback);
        llFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventRouter.send(new EventStartFeedbackFragment());
            }
        });

        LinearLayout inviteLay = rootView.findViewById(R.id.invite_lay);
        inviteLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = BottomInviteFragmentDialog.newInstance();
                dialog.show(getChildFragmentManager(), null);
            }
        });
        LinearLayout scanQR = rootView.findViewById(R.id.scan_qr);
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity() instanceof IStartScanQR) ((IStartScanQR)getActivity()).startScanQR();
            }
        });

        LinearLayout llLoyaltyProgram = rootView.findViewById(R.id.loyalty_program);
        llLoyaltyProgram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1 = BottomLoyaltyProgrammFragmentDialog.newInstance();
                dialog1.show(getChildFragmentManager(), null);
            }
        });

        updateCaptions();
        return rootView;
    }

    private void onExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.exit_title))
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       onExit();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void onExit() {
        App.logout();
        LogUtils.logD("AppMedbook", "btnExit EventLogout");
        EventRouter.send(new EventLogout());
    }

    private void updateCaptions() {
        nameTv.setText(App.getUser().first_name + " " + App.getUser().middle_name + " " + App.getUser().last_name);
        mailTv.setText(App.getUser().email);
        if (App.getUser().phone == null || App.getUser().phone.isEmpty())
            phoneTv.setText("+380");
        else
            phoneTv.setText(App.getUser().phone);
        if (App.getUser().medicalInstitution != null) {
            if (App.getUser().medicalInstitution.city != null)
                cityTv.setText(App.getUser().medicalInstitution.city.translations[0].name);
            lpuTv.setText(App.getUser().medicalInstitution.translations[0].name);
        }
        if (App.getUser().specialization != null)
            specializationTv.setText(String.valueOf(App.getUser().specialization.translations[0].name));
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_GET_USER_SUCCESS:
                updateCaptions();
                break;
            case Event.EVENT_END_QR_CODE:
                EventEndQrCode eventEndQrCode = (EventEndQrCode) event;
                DialogBuilder.showBottomResultDialog(getActivity(), eventEndQrCode.getMessage(), new BottomResultDialog.Callback() {
                    @Override
                    public void onClose() {
                        getActivity().onBackPressed();
                    }
                });
                //showProgress(false);
                break;
        }
    }

    //Getting the scan results
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                //Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                checkCode(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkCode(String code) {

        //showProgress(true);

        Core.get().Api2Control().checkQr(code);
    }

}
