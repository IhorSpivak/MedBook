package ua.profarma.medbook.ui.calendar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.visits.EventUpdateQRCode;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.ui.custom_views.graphic_key.GenerateCodeView;
import ua.profarma.medbook.ui.custom_views.graphic_key.VisitCodeGenerator;

public class QrCodeGenerateVisitActivity extends MedBookActivity {
    public static final String ID_VISIT = "ID_VISIT";
    public static final String TEXT_CODE = "TEXT_CODE";
    public static final String EXPIRED_CODE = "EXPIRED_CODE";

    private VisitCodeGenerator.Settings settings;


    public static final int CODE_ROWS_COUNT=3;
    public static final int CODE_COLUMNS_COUNT=3;
    public static final int CODE_LENGTH=4;
    public static final int VALID_INTERVAL_SECONDS=10;

    private TextView tvTitle;
    private Button btn;
    private ImageView imvQrCode;
    private int idVisit;
    private Handler h;
    private Mode state;
    private GenerateCodeView codeGK;

    private enum Mode {
        STATE_QR_CODE,
        STATE_GRAPHIC_KEY
    }

    Handler.Callback hc = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            Core.get().VisitsControl().startVisit(idVisit, true);
            return true;
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state = Mode.STATE_QR_CODE;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_qr_code_generate_visit);
        tvTitle = findViewById(R.id.activity_qr_code_generate_visit_toolbar_title);
        btn = findViewById(R.id.activity_qr_code_generate_visit_btn);
        ImageView imvClose = findViewById(R.id.activity_qr_code_generate_visit_toolbar_close);
        imvClose.setOnClickListener(view -> finish());
        imvQrCode = findViewById(R.id.activity_qr_code_generate_visit_qr);
        codeGK = findViewById(R.id.activity_qr_code_generate_visit_gk);
        codeGK.setVisibility(View.GONE);
        if (getIntent() != null) {
            String token = getIntent().getStringExtra(TEXT_CODE);
            long expired = getIntent().getLongExtra(EXPIRED_CODE, -1);
            idVisit = getIntent().getIntExtra(ID_VISIT, -1);

            if (idVisit == -1)
                finish();

            updateQrCode(token);

            if (expired > (System.currentTimeMillis() / 1000)) {
                h = new Handler(hc);
                h.sendEmptyMessageDelayed(1, (expired - System.currentTimeMillis() / 1000 - 1) * 1000);
            }
        }
        btn = findViewById(R.id.activity_qr_code_generate_visit_btn);
        btn.setOnClickListener(view -> {
            if (state == Mode.STATE_GRAPHIC_KEY) state = Mode.STATE_QR_CODE;
            else if (state == Mode.STATE_QR_CODE) state = Mode.STATE_GRAPHIC_KEY;

            if (state == Mode.STATE_GRAPHIC_KEY) {
                btn.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_qr));
                imvQrCode.setVisibility(View.GONE);
                codeGK.setVisibility(View.VISIBLE);
                h.removeCallbacksAndMessages(null);
                Core.get().VisitsControl().startVisit(idVisit, true);
            } else if (state == Mode.STATE_QR_CODE) {
                btn.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_gk));
                imvQrCode.setVisibility(View.VISIBLE);
                h.removeCallbacksAndMessages(null);
                Core.get().VisitsControl().startVisit(idVisit, true);
                codeGK.setVisibility(View.GONE);
            }
        });
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_toolbar_title));
        btn.setText(state == Mode.STATE_GRAPHIC_KEY ? Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_qr) :
                Core.get().LocalizationControl().getText(R.id.activity_qr_code_generate_visit_btn_gk));
    }

    private void updateQrCode(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imvQrCode.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_UPDATE_QR_CODE:
                EventUpdateQRCode eventUpdateQRCode = (EventUpdateQRCode) event;
                if (state == Mode.STATE_QR_CODE)
                    updateQrCode(eventUpdateQRCode.getToken());
                else if (state == Mode.STATE_GRAPHIC_KEY) {
                    settings = new VisitCodeGenerator.Settings(CODE_COLUMNS_COUNT, CODE_ROWS_COUNT, CODE_LENGTH, VALID_INTERVAL_SECONDS);
                    codeGK.initCodeGenerator(idVisit, settings);
                    codeGK.initArray(eventUpdateQRCode.getToken());
                }
                long delay = (eventUpdateQRCode.getExpired() - System.currentTimeMillis() / 1000 - 1) * 1000;
                if (eventUpdateQRCode.getExpired() > (System.currentTimeMillis() / 1000)) {
                    h = new Handler(hc);
                    h.sendEmptyMessageDelayed(1, delay);
                }
                break;
            case Event.EVENT_VISIT_STARTING:
                finish();
                break;
        }
    }
}
