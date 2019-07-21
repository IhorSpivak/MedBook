package ua.profarma.medbook.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.ViewfinderView;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.core.EventListener;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.visits.EventVisitStartFailed;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.ui.custom_views.graphic_key.EnterCodeView;
import ua.profarma.medbook.ui.custom_views.graphic_key.VisitCodeGenerator;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.CODE_COLUMNS_COUNT;
import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.CODE_LENGTH;
import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.CODE_ROWS_COUNT;
import static ua.profarma.medbook.ui.calendar.QrCodeGenerateVisitActivity.VALID_INTERVAL_SECONDS;

public class ScanQrVisitActivity extends MedBookActivity {

    private int idVisits;
    private EnterCodeView enterCodeView;

    private TextView tvTitle;
    private Button btnScanQr;

    private Mode state;

    private CaptureManager capture;
    private DecoratedBarcodeView qrScannerView;
    private ViewfinderView viewfinderView;

    public static final String ID_VISIT = "ID_VISIT";

    private enum Mode {
        STATE_QR_CODE,
        STATE_GRAPHIC_KEY
    }

    private String addCode(int x, int y) {

        LogUtils.logD("hfr6yughbjjhj", "x = " + x + ", y = " + y);
        if (x == 0 && y == 0) return "0";
        if (x == 1 && y == 0) return "1";
        if (x == 2 && y == 0) return "2";
        if (x == 0 && y == 1) return "3";
        if (x == 1 && y == 1) return "4";
        if (x == 2 && y == 1) return "5";
        if (x == 0 && y == 2) return "6";
        if (x == 1 && y == 2) return "7";
        if (x == 2 && y == 2) return "8";
        return "";
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_scan_visit);

        state = Mode.STATE_QR_CODE;

        qrScannerView = findViewById(R.id.activity_qr_visit_scan_code);
        viewfinderView = findViewById(R.id.zxing_viewfinder_view);


        capture = new CaptureManager(this, qrScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();


        tvTitle = findViewById(R.id.activity_qr_visit_toolbar_title);
        btnScanQr = findViewById(R.id.activity_qr_visit_btn_qr);

        enterCodeView = findViewById(R.id.activity_qr_visit_enter_code);

        enterCodeView.setVisibility(View.GONE);

        if (getIntent() != null) {
            idVisits = getIntent().getIntExtra(ID_VISIT, -1);
        }

        VisitCodeGenerator.Settings settings = new VisitCodeGenerator.Settings(CODE_COLUMNS_COUNT, CODE_ROWS_COUNT, CODE_LENGTH, VALID_INTERVAL_SECONDS);

        enterCodeView.initCodeGenerator(idVisits, settings);
        enterCodeView.setEnterCodeEndListener(code -> {

            StringBuffer result = new StringBuffer();
            for (int i = 0; i < code.length; i++)
                result.append(addCode(code[i][0], code[i][1]));


            LogUtils.logD("hfr6yughbjjhj", "result = " + result.toString());
            if (result.length() == 4)

                Core.get().VisitsControl().startVisit(idVisits, result.toString());
            else
                DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), "Невірний код");
//            if (!syncTimeManager.checkIfTimeSyncedOrAlert(this, success -> {
//            })) {
//                return;
//            }
//
//            if (!validateCode(code)) {
//                holder.enterCodeView.setError(true);
//                setErrorStatusText();
//                return;
//            }
//
//            setSuccessResult();
//            onBackPressed();
        });

        //enterCodeView.setOnClearListener(this::setDefaultStatusText);

        ImageView btnBack = findViewById(R.id.activity_qr_visit_toolbar_close);
        btnBack.setOnClickListener(view -> finish());
        EventRouter.register(this);
        //qr code scanner object
        //intializing scan object
        onLocalizationUpdate();


        btnScanQr.setOnClickListener(view -> {
            if (state == Mode.STATE_GRAPHIC_KEY) state = Mode.STATE_QR_CODE;
            else if (state == Mode.STATE_QR_CODE) state = Mode.STATE_GRAPHIC_KEY;

            if (state == Mode.STATE_GRAPHIC_KEY) {
                btnScanQr.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_qr));
                enterCodeView.setVisibility(View.VISIBLE);
                qrScannerView.setVisibility(View.GONE);
            } else if (state == Mode.STATE_QR_CODE) {
                btnScanQr.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_gk));
                enterCodeView.setVisibility(View.GONE);
                qrScannerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_VISIT_STARTING:
                LogUtils.logD("ScanQrVisitActivity", "EVENT_VISIT_STARTING");
                finish();
                break;
            case Event.EVENT_VISIT_STARTING_FAILED:
                LogUtils.logD("ScanQrVisitActivity", "EVENT_VISIT_STARTING_FAILED");
                DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message), ((EventVisitStartFailed) event).getMessage());
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_visit_toolbar_title));
        if (state == Mode.STATE_GRAPHIC_KEY) {
            btnScanQr.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_qr));
        } else if (state == Mode.STATE_QR_CODE) {
            btnScanQr.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_gk));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return qrScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
