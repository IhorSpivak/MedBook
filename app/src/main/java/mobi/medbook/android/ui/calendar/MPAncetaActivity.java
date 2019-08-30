package mobi.medbook.android.ui.calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.visits.EventUserVisitQuestionnaireMPLoad;
import mobi.medbook.android.events.visits.EventUserVisitQuestionnaireUpdate;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.products.ProductRecyclerItem;
import mobi.medbook.android.recyclerviews.products.ProductsRecyclerView;
import mobi.medbook.android.types.visits.Product;
import mobi.medbook.android.types.visits.UserVisit;
import mobi.medbook.android.types.visits.UserVisitQuestionnaire;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.DialogBuilder;
import mobi.medbook.android.utils.LogUtils;
import mobi.medbook.android.utils.TextUtils;
import mobi.medbook.android.utils.TimeUtils;


public class MPAncetaActivity extends MedBookActivity implements IOnSelectProduct {

    public static String KEY_ID_VISIT = "KEY_ID_VISIT";

    private final String TAG = "AppMedBook/MPAncetaActivity";

    private final int REQUEST_CODE = 2666;


    private boolean flagSaveMPAncet = true;

    private UserVisitQuestionnaire mpData;

    private int mIdVisit;
    private UserVisit userVisit;

    private TextView tvTitle;
    private ImageView dotStatus;
    private TextView tvStatus;
    private Button btnSend;
    private Button btnCancel;
    private TextView tvDate;
    private TextView tvName;
    private TextView tvTitleVisit;
    private TextView tvDescription;
    private ImageView imPromo;
    private TextView tvPromo;
    private ProductsRecyclerView listProducts;
    private EditText tvPatientFlow;
    private RelativeLayout llPatientFlow;
    private TextView tvTimer;
    private Handler h;
    private int visitTimer;

    private RecyclerItems items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_mp_anketa);

        tvTimer = findViewById(R.id.activity_visit_mp_anceta_timer);
        listProducts = findViewById(R.id.activity_visit_mp_anceta_list_product);
        listProducts.init();
        tvPromo = findViewById(R.id.activity_visit_mp_anceta_promo_text);
        imPromo = findViewById(R.id.activity_visit_mp_anceta_promo_im);
        tvPromo.setVisibility(View.GONE);
        imPromo.setVisibility(View.GONE);

        btnSend = findViewById(R.id.activity_visit_mp_anceta_btn_send);
        btnCancel = findViewById(R.id.activity_visit_mp_anceta_btn_cancel);

        tvDate = findViewById(R.id.activity_visit_mp_anceta_time);
        tvName = findViewById(R.id.activity_visit_mp_anceta_name);
        tvTitleVisit = findViewById(R.id.activity_visit_mp_anceta_title_of_visit);
        tvDescription = findViewById(R.id.activity_visit_mp_anceta_description_of_visit);

        tvTitle = findViewById(R.id.activity_visit_mp_anceta_toolbar_title);
        ImageView imClose = findViewById(R.id.activity_visit_mp_anceta_toolbar_close);
        dotStatus = findViewById(R.id.activity_visit_mp_anceta_dot);
        tvStatus = findViewById(R.id.activity_visit_mp_anceta_status);


        tvStatus.setTextColor(getResources().getColor(R.color.color_circle_visit_started));
        tvStatus.setText(Core.get().LocalizationControl().getText(R.id.status_visits_started));
        dotStatus.setImageResource(R.drawable.circle_visit_started);

        imClose.setOnClickListener(view -> {
                    mpData.data.patientFlow = Integer.parseInt(tvPatientFlow.getText().toString());
                finish();});


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

        tvPatientFlow = findViewById(R.id.activity_visit_mp_anceta_patient_flow_value);

        tvPatientFlow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Toast.makeText(MPAncetaActivity.this, "Збережено", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });

        tvPatientFlow.setBackgroundResource(android.R.color.transparent);

        llPatientFlow = findViewById(R.id.activity_visit_mp_anceta_block_patient_flow);
        llPatientFlow.setEnabled(false);
        llPatientFlow.setOnClickListener(view -> {
            tvPatientFlow.requestFocus();
            InputMethodManager imm=(InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        });

        Core.get().VisitsControl().getUserVisitQuestionnaireMP(userVisit.id);

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

        btnSend.setOnClickListener(view -> {
            mpData.data.patientFlow = Integer.parseInt(tvPatientFlow.getText().toString());

            if(isOnline()) {
                onFinishMeetingSuccess();
            } else {
                Toast.makeText(this, "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
            }


        });

        btnCancel.setOnClickListener(view -> {
            mpData.data.patientFlow = Integer.parseInt(tvPatientFlow.getText().toString());
            if(isOnline()){
                onFinishMeeting();
            } else {
                Toast.makeText(this, "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
            }

        });
//        h = new Handler(hc);
//        h.sendEmptyMessageDelayed(1, 1000);
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

    private void onFinishMeetingSuccess() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Завершити зустріч успішно?")
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mpData != null && mpData.data != null) {
                            if (mpData == null || mpData.data == null) {
                                    Core.get().VisitsControl().visitMedPredResult(userVisit.id, 1);
                                    finish();
                            } else {
                                if (Core.get().VisitsControl().isStartSendMPAnceta()) {
                                    if(visitTimer >= 600) {
                                        Core.get().VisitsControl().visitMedPredResult(userVisit.id, 1);
                                        finish();
                                    } else {
                                        DialogBuilder.showInfoDialog(MPAncetaActivity.this, "Повідомлення", "Мінимальна тривалість зустрічі 10 хвилин");
                                    }
                                } else
                                    DialogBuilder.showInfoDialog(MPAncetaActivity.this, "Повідомлення", "Не можливо завершити візіт, оскільки не заповнені усі дані по продуктам");
                            }
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void onFinishMeeting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Завершити зустріч неуспішно?")
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Так", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Core.get().VisitsControl().visitMedPredResult(userVisit.id, 0);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private Handler.Callback hc = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            if(msg.what == 1) {
                visitTimer = (int) ((System.currentTimeMillis() / 1000) - userVisit.started_at);
                if (visitTimer >= 0) {
                    LogUtils.logD("yjghmhbhgmnb", "sec = " + visitTimer);
                    Date d = new Date(visitTimer * 1000L);
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
                    df.setTimeZone(TimeZone.getTimeZone("GMT"));
                    String time = df.format(d);
                    tvTimer.setText(time);
                }
                h = new Handler(hc);
                h.sendEmptyMessageDelayed(1, 1000);
            }
            return true;
        }
    };

    private void setDate() {

        Locale local = new Locale("ua", "UA");
        Calendar calFrom = Calendar.getInstance(local);
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

    @Override
    protected void onLocalizationUpdate() {
        setDate();
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_viewer_toolbar_title));
        btnCancel.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_mp_anceta_btn_cancel));
        btnSend.setText(Core.get().LocalizationControl().getText(R.id.activity_visit_mp_anceta_btn_send));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_DONT_DOWNLOAD_MP_ANKETA:
                DialogBuilder.showInfoDialog(this, Core.get().LocalizationControl().getText(R.id.general_message),
                        Core.get().LocalizationControl().getText(R.id.mp_anketa_dont_downloading_anketa));
                break;
            case Event.EVENT_USER_VISIT_QUESTIONNAIRE_MP_UPDATE:
                for (int i = 0; i < items.size(); i++) {
                    if (((ProductRecyclerItem) items.get(i)).getProduct().productId.equals(((EventUserVisitQuestionnaireUpdate) event).getProduct().productId)) {
                        ((ProductRecyclerItem) items.get(i)).setProduct(((EventUserVisitQuestionnaireUpdate) event).getProduct());
                        listProducts.itemUpdate(i);
                    }
                }
                break;
            case Event.EVENT_USER_VISIT_QUESTIONNAIRE_MP_LOAD:
                llPatientFlow.setEnabled(true);
                mpData = ((EventUserVisitQuestionnaireMPLoad) event).getMpData();
                if (mpData != null && mpData.data != null) {
                    if (items == null) items = new RecyclerItems();
                    tvPatientFlow.setText(String.valueOf(mpData.data.patientFlow));

                    if (mpData.data.promoArr != null && mpData.data.promoArr.length > 0) {
                        imPromo.setVisibility(View.VISIBLE);
                        tvPromo.setVisibility(View.VISIBLE);
                        imPromo.setOnClickListener(onClickListener);
                        tvPromo.setOnClickListener(onClickListener);
                    }
                    listProducts.clear();
                    if (mpData.data.productsArr != null && mpData.data.productsArr.length > 0) {
                        for (int i = 0; i < mpData.data.productsArr.length; i++) {
                            items.add(new ProductRecyclerItem(mpData.data.productsArr[i]));
                        }
                        listProducts.itemsAdd(items);
                    }
                    break;
                } else {
                    llPatientFlow.setVisibility(View.INVISIBLE);
                }
        }
    }

    View.OnClickListener onClickListener = view -> {
        flagSaveMPAncet = false;
        startActivity(new Intent(MPAncetaActivity.this, PromoActivity.class));
    };

    @Override
    public void onSelectProduct(Product product) {
        flagSaveMPAncet = false;
        Intent intent = new Intent(MPAncetaActivity.this, ProductActivity.class);
        intent.putExtra(ProductActivity.KEY_PRODUCT_ID, product.productId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case REQUEST_CODE:
                        LogUtils.logD(TAG, "patientFlow = " + mpData.data.patientFlow);
                        mpData.data.patientFlow = data.getIntExtra(EditNumberFieldActivity.KEY_FIELD_SAVE, 0);
                        LogUtils.logD(TAG, "patientFlow = " + mpData.data.patientFlow);
                        tvPatientFlow.setText(String.valueOf(mpData.data.patientFlow));
                        break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        flagSaveMPAncet = true;
        h = new Handler(hc);
        h.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.logD("yjghmhbhgmnb", "h.removeMessages");
        h.removeMessages(1);
        if (flagSaveMPAncet)
            Core.get().VisitsControl().saveMPAncet(mIdVisit);
    }
}
