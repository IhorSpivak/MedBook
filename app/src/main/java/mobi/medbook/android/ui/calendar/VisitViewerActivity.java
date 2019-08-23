package mobi.medbook.android.ui.calendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Date;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.visits.EventStartQRCode;
import mobi.medbook.android.events.visits.EventVisitStartFailed;
import mobi.medbook.android.events.visits.EventVisitStarting;
import mobi.medbook.android.types.visits.UserVisit;

import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.DateHelper;
import mobi.medbook.android.utils.DialogBuilder;
import mobi.medbook.android.utils.LogUtils;
import mobi.medbook.android.utils.TextUtils;
import mobi.medbook.android.utils.TimeUtils;


public class VisitViewerActivity extends MedBookActivity {

    private final String TAG = "AppMedbook/VisitViewerActivity";

    public static String KEY_ID_VISIT = "KEY_ID_VISIT";
    private int mIdVisit;
    private TextView tvTitle;
    private ImageView dotStatus;
    private TextView tvStatus;
    private long today;
    private UserVisit userVisit;
    private Button btnMain;
    private Button btnCancel;
    private TextView tvDate;
    private TextView tvName;
    private TextView tvChangeDate;
    private TextView tvTitleVisit;
    private TextView tvDescription;
    private StatusVisit status;
    private Handler h;
    private ImageView imPush;
    private RelativeLayout llBell;


    private Handler.Callback hc = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            imPush.setEnabled(true);
            return true;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_viewer);

        llBell = findViewById(R.id.llBell);

        imPush = findViewById(R.id.activity_visit_viewer_push);
        if (isMedPred()) {
            llBell.setOnClickListener(view -> {
                if(isOnline()) {
                    Core.get().VisitsControl().pushNotificationBeforeStartVisit(userVisit.id);
                    Toast.makeText(this, "Повідомлення було відправлене лікарю", Toast.LENGTH_LONG).show();
                    llBell.setEnabled(false);
                    h = new Handler(hc);
                    h.sendEmptyMessageDelayed(1, 16000);
                } else {
                    Toast.makeText(this, "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
                }

            });
        }

        LogUtils.logD(TAG, "isMedPred = " + isMedPred());

        today = VisitUtils.getTodayTime();
        LogUtils.logD(TAG, "current time GMT +0, 00:00 = " + today);

        btnMain = findViewById(R.id.activity_visit_viewer_btn_main);
        btnCancel = findViewById(R.id.activity_visit_viewer_btn_cancel);

        tvDate = findViewById(R.id.activity_visit_viewer_time);
        tvChangeDate = findViewById(R.id.activity_visit_viewer_other_time);
        tvName = findViewById(R.id.activity_visit_viewer_name);
        tvTitleVisit = findViewById(R.id.activity_visit_viewer_title_of_visit);
        tvDescription = findViewById(R.id.activity_visit_viewer_description_of_visit);

        tvTitle = findViewById(R.id.activity_visit_viewer_toolbar_title);
        ImageView imClose = findViewById(R.id.activity_visit_viewer_toolbar_close);
        dotStatus = findViewById(R.id.activity_visit_viewer_dot);
        tvStatus = findViewById(R.id.activity_visit_viewer_status);

        findViewById(R.id.activity_visit_viewer_other_time).setOnClickListener(view -> {
            Intent intent = new Intent(this, ChangeDateVisitActivity.class);
            intent.putExtra(ChangeDateVisitActivity.KEY_ID_VISIT, mIdVisit);
            intent.putExtra(ChangeDateVisitActivity.TITLE, tvTitleVisit.getText().toString());

            startActivity(intent);
        });


        imClose.setOnClickListener(view -> finish());

        if (getIntent() == null) {
            finish();
        } else {
            mIdVisit = getIntent().getIntExtra(KEY_ID_VISIT, -1);
            if (mIdVisit == -1)
                finish();
            else
                userVisit = Core.get().VisitsControl().getUserVisitForId(mIdVisit);
        }
        if (userVisit == null)
            finish();

        tvName.setText(TextUtils.getString(userVisit.partner.partner.last_name) + " " + TextUtils.getString(userVisit.partner.partner.first_name)
                + " " + TextUtils.getString(userVisit.partner.partner.middle_name));
        if (userVisit.visit.title == null || userVisit.visit.title.isEmpty())
            tvTitle.setVisibility(View.GONE);
        else
            tvTitleVisit.setText(userVisit.visit.title);

        if (userVisit.visit.description == null || userVisit.visit.description.isEmpty())
            tvDescription.setVisibility(View.GONE);
        else
            tvDescription.setText(userVisit.visit.description);

        updateStatus();

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                v.setEnabled(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                }, 2000);

                if(isOnline()) {
                    switch (status) {
                        case ACCEPTED:
                            Core.get().VisitsControl().startVisit(userVisit.id, false);
                            break;
                        case NEW:
                            Core.get().VisitsControl().visitAccept(userVisit.id);
                            break;
                        case STARTED:
                            //Core.get().VisitsControl().visitEnd(userVisit.id);
                            break;
                    }
                } else {
                    Toast.makeText(VisitViewerActivity.this, "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
                }

            }
        });


        btnCancel.setOnClickListener(view -> {
            if(isOnline()) {
                Core.get().VisitsControl().visitDecline(userVisit.id);
                finish();
            } else {
                Toast.makeText(this, "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
            }
        });
        onLocalizationUpdate();

    }


    public boolean isOnline() {
        boolean a = false ;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            a = true;
        }
        return a;
    }

    private void updateStatus() {
        status = VisitUtils.getStatus(userVisit, today);
        if (tvStatus != null && dotStatus != null) {
            switch (status) {
                case PROCESSING:
                    updateStatus(getResources().getColor(R.color.color_circle_visit_new), Core.get().LocalizationControl().getText(R.id.status_visits_processing), R.drawable.circle_visit_new);
                    break;
                case ACCEPTED:
                    if(isMedPred()) {
                        imPush.setVisibility(View.VISIBLE);
                    }
                    updateStatus(getResources().getColor(R.color.color_circle_visit_accepted), Core.get().LocalizationControl().getText(R.id.status_visits_accepted), R.drawable.circle_visit_accepted);
                    break;
                case CANCELED:
                    tvChangeDate.setVisibility(View.GONE);
                    updateStatus(getResources().getColor(R.color.color_circle_visit_canceled), Core.get().LocalizationControl().getText(R.id.status_visits_canceled), R.drawable.circle_visit_canceled);
                    btnMain.setVisibility(View.INVISIBLE);
                    btnCancel.setVisibility(View.INVISIBLE);
                    break;
                case ENDED:
                    tvChangeDate.setVisibility(View.GONE);
                    btnMain.setVisibility(View.INVISIBLE);
                    btnCancel.setVisibility(View.INVISIBLE);
                    updateStatus(getResources().getColor(R.color.color_circle_visit_ended), Core.get().LocalizationControl().getText(R.id.status_visits_ended), R.drawable.circle_visit_ended);
                    break;
                case FAILED:
                    tvChangeDate.setVisibility(View.GONE);
                    updateStatus(getResources().getColor(R.color.color_circle_visit_failed), Core.get().LocalizationControl().getText(R.id.status_visits_failed), R.drawable.circle_visit_failed);
                    btnMain.setVisibility(View.INVISIBLE);
                    btnCancel.setVisibility(View.INVISIBLE);
                    break;
                case NEW:
                    updateStatus(getResources().getColor(R.color.color_circle_visit_new), Core.get().LocalizationControl().getText(R.id.status_visits_new), R.drawable.circle_visit_new);
                    break;
                case STARTED:
                    tvChangeDate.setVisibility(View.GONE);
                    updateStatus(getResources().getColor(R.color.color_circle_visit_started), Core.get().LocalizationControl().getText(R.id.status_visits_started), R.drawable.circle_visit_started);
                    break;
                case EMPTY:
                    btnMain.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.GONE);
                    tvChangeDate.setVisibility(View.GONE);
                    break;
            }
        }
    }

    private boolean isMedPred() {
        return !(App.getUser().specialization == null || App.getUser().specialization.is_medpred == null || App.getUser().specialization.is_medpred == 0);
    }

    private void setDate() {
        Calendar calFrom = Calendar.getInstance();
        calFrom.setTimeInMillis(userVisit.time_from * 1000);
        Calendar calTo = Calendar.getInstance();
        calTo.setTimeInMillis(userVisit.time_to * 1000);
        int year = calFrom.get(Calendar.YEAR);
        int month = calFrom.get(Calendar.MONTH);
        int day = calFrom.get(Calendar.DAY_OF_MONTH);
        int dayWeek = calFrom.get(Calendar.DAY_OF_WEEK);
        int hourFrom = calFrom.get(Calendar.HOUR_OF_DAY);
        int minutesFrom = calFrom.get(Calendar.MINUTE);

        int hourTo = calTo.get(Calendar.HOUR_OF_DAY);
        int minutesTo = calTo.get(Calendar.MINUTE);


        tvDate.setText(TimeUtils.getDayOfWeek(dayWeek) + ", " + day + " " + TimeUtils.getMonthName_s(month)
                + " " + year + "\n" + hourFrom + ":" + TimeUtils.getFormatMinutes(minutesFrom) + " - " + hourTo + ":" + TimeUtils.getFormatMinutes(minutesTo));
    }

    private void updateStatus(int color, String text, int resDot) {
        tvStatus.setTextColor(color);
        tvStatus.setText(text);
        dotStatus.setImageResource(resDot);
    }

    @Override
    protected void onLocalizationUpdate() {
        setDate();
        tvChangeDate.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_viewer_other_time));
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_viewer_toolbar_title));
        btnCancel.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_viewer_btn_cancel));

        switch (status) {
            case PROCESSING:
                btnMain.setVisibility(View.INVISIBLE);
                break;
            case ACCEPTED:
                btnMain.setVisibility(View.VISIBLE);
                btnMain.setText(Core.get().LocalizationControl().getText(R.id.btn_visits_start));
                break;
            case CANCELED:
                break;
            case ENDED:
                break;
            case FAILED:
                break;
            case NEW:
                btnMain.setText(Core.get().LocalizationControl().getText(R.id.btn_visits_accept));
                break;
            case STARTED:
                btnMain.setText(Core.get().LocalizationControl().getText(R.id.btn_visits_end));
                break;
            case EMPTY:
                break;
        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            //update after accept
            case Event.EVENT_VISIT_LOAD:
                userVisit = Core.get().VisitsControl().getUserVisitForId(mIdVisit);
                updateStatus();
                onLocalizationUpdate();
                break;
            case Event.EVENT_START_QR_CODE:
                Intent intent = new Intent(this, QrCodeGenerateVisitActivity.class);
                intent.putExtra(QrCodeGenerateVisitActivity.ID_VISIT, userVisit.id);
                intent.putExtra(QrCodeGenerateVisitActivity.TEXT_CODE, ((EventStartQRCode) event).getToken());
                intent.putExtra(QrCodeGenerateVisitActivity.EXPIRED_CODE, ((EventStartQRCode) event).getExpired());
                startActivity(intent);
                break;
            case Event.EVENT_SCAN_QR_CODE:
                //Intent intent1 = new Intent(this, ScanQrVisitActivity.class);
                //intent1.putExtra(ScanQrVisitActivity.ID_VISIT, userVisit.id);
                //startActivity(intent1);
                IntentIntegrator integrator = new IntentIntegrator(this).setOrientationLocked(false).setCaptureActivity(ScanQrVisitActivity.class);
                integrator.addExtra(ScanQrVisitActivity.ID_VISIT, userVisit.id);
                integrator.initiateScan();
                break;
            case Event.EVENT_VISIT_STARTING:
                userVisit.started_at = ((EventVisitStarting) event).getStarted_at();
                updateStatus();
                onLocalizationUpdate();
                if (isMedPred()) {
                    Intent intentMedPred = new Intent(VisitViewerActivity.this, MPAncetaActivity.class);
                    intentMedPred.putExtra(MPAncetaActivity.KEY_ID_VISIT, mIdVisit);
                    startActivity(intentMedPred);
                    finish();
                } else {
                    Intent intentDoc = new Intent(VisitViewerActivity.this, DoctorAncetaActivity.class);
                    intentDoc.putExtra(MPAncetaActivity.KEY_ID_VISIT, mIdVisit);
                    startActivity(intentDoc);
                    finish();
                }
                break;
            case Event.EVENT_VISIT_STARTING_FAILED:
                DialogBuilder.showInfoDialog(this, "Повідомлення", ((EventVisitStartFailed) event).getMessage());
                break;
        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        LogUtils.logD("ScanQrVisitActivity", "onActivityResult");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                //Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                Core.get().VisitsControl().startVisit(userVisit.id, result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
