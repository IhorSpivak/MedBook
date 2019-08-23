package mobi.medbook.android.ui.calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.requests.CreateUserVisitRequest;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.utils.DateHelper;
import mobi.medbook.android.utils.DialogBuilder;
import mobi.medbook.android.utils.TextUtils;


public class AddVisitActivity extends MedBookActivity /*implements TimePickerDialog.OnTimeSetListener*/ {


    public static int SELECT_MEMBER_VISIT = 5678;

    public static String SELECT_DAY = "SELECT_DAY";
    public static String ADD_VISIT_ID_MEMBER = "ADD_VISIT_ID_MEMBER";
    public static String ADD_VISIT_LAST_NAME = "ADD_VISIT_LAST_NAME";
    public static String ADD_VISIT_FIRST_NAME = "ADD_VISIT_FIRST_NAME";
    public static String ADD_VISIT_MIDDLE_NAME = "ADD_VISIT_MIDDLE_NAME";

    private LinearLayout llDate;
    private LinearLayout llTime;
    private LinearLayout llDuration;
    private LinearLayout llSecondMember;


    private TextView tvDateTitle;
    private TextView tvDateValue;
    private TextView tvTimeTitle;
    private TextView tvTimeValue;
    private TextView tvDurationTitle;
    private TextView tvDurationValue;

    private TextView tvTitle;
    private TextView tvVisit;
    private TextView tvNote;

    private AppCompatEditText inputVisit;
    private AppCompatEditText inputNote;
    private TextInputLayout layoutVisit;
    private TextInputLayout layoutNote;

    private Button btnCreate;


    private TextView tvSelectSecondTitle;
    private TextView tvSelectSecondValue;
    Calendar cal = Calendar.getInstance();


    private int mSelectUserVisitId = -1;
    private String mSelectUserVisitName;
    private long mSelectTimeVisit;
    private int mSelectTimeDuration;
    private String mSelectTitleVisit;
    private String mSelectDecriptionVisit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);
        ImageView imClose = findViewById(R.id.activity_add_visit_toolbar_close);
        imClose.setOnClickListener(view -> finish());
        tvSelectSecondTitle = findViewById(R.id.activity_add_visit_title_add_second);
        tvSelectSecondValue = findViewById(R.id.activity_add_visit_value_add_second);
        tvTitle = findViewById(R.id.activity_add_visit_toolbar_title);
        tvVisit = findViewById(R.id.activity_add_visit_title);
        tvNote = findViewById(R.id.activity_add_visit_notes_title);
        inputNote = findViewById(R.id.activity_add_visit_notes_title_input);
        layoutVisit = findViewById(R.id.activity_add_visit_title_layout);
        layoutNote = findViewById(R.id.activity_add_visit_notes_title_layout);
        llDate = findViewById(R.id.activity_add_visit_layout_date);
        llTime = findViewById(R.id.activity_add_visit_layout_time);
        llDuration = findViewById(R.id.activity_add_visit_layout_duration);
        llSecondMember = findViewById(R.id.activity_add_visit_layout_second_member);
        tvDateTitle = findViewById(R.id.activity_add_visit_layout_date_title);
        tvDateValue = findViewById(R.id.activity_add_visit_layout_date_value);
        tvTimeTitle = findViewById(R.id.activity_add_visit_layout_time_title);
        tvTimeValue = findViewById(R.id.activity_add_visit_layout_time_value);
        tvDurationTitle = findViewById(R.id.activity_add_visit_layout_duration_title);
        tvDurationValue = findViewById(R.id.activity_add_visit_layout_duration_value);
        btnCreate = findViewById(R.id.activity_add_visit_btn_create);
        inputVisit = findViewById(R.id.activity_add_visit_title_input);

        inputVisit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextUtils.hideKeyboard(v);
                }
            }
        });



        setDateTime(getIntent().getLongExtra(SELECT_DAY, 0));
        mSelectTimeDuration = 10 * 60;
        setDurationText();

        cal.setTime(new Date());

        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                }, 2000);
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(new Date());
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddVisitActivity.this, -1, (view1, year, month, dayOfMonth) -> {
                    cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    tvDateValue.setText(DateHelper.getStringFromCalendarForEditProfile(cal));
                    mSelectTimeVisit = cal.getTimeInMillis();
                    setDateTimeSptamp(mSelectTimeVisit / 1000);
                },
                        currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH),
                        currentCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        llTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                }, 2000);
                Calendar currentCalendar = Calendar.getInstance();
                currentCalendar.setTime(new Date());
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddVisitActivity.this,
                        (view11, hourOfDay, minute) -> {
                            cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            cal.set(Calendar.MINUTE, minute);
                            tvTimeValue.setText(DateHelper.getStringFromCalendarForOrder(cal));
                            mSelectTimeVisit = cal.getTimeInMillis();
                            setDateTimeSptamp(mSelectTimeVisit / 1000);

                        }, currentCalendar.get(Calendar.HOUR_OF_DAY), currentCalendar.get(Calendar.MINUTE) +30 , true);
                timePickerDialog.show();
            }
        });




        llDuration.setOnClickListener(view -> showPopupMenuDuration(view));

        llSecondMember.setOnClickListener(view ->{
                llSecondMember.setEnabled(false);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                llSecondMember.setEnabled(true);
            }
        }, 2000);

                startActivityForResult(new Intent(AddVisitActivity.this, SelectSecondMemberActivity.class), SELECT_MEMBER_VISIT);
        });

        btnCreate.setOnClickListener(view -> {

            if (isOnline()) {

                if (mSelectUserVisitId != -1) {
                    if (inputVisit.getText() != null && !inputVisit.getText().toString().isEmpty()) {
                        btnCreate.setEnabled(false);
                        CreateUserVisitRequest createUserVisitRequest = new CreateUserVisitRequest();
                        createUserVisitRequest.description = inputNote.getText().toString();
                        createUserVisitRequest.title = inputVisit.getText().toString();
                        createUserVisitRequest.invited_user_id = mSelectUserVisitId;
                        createUserVisitRequest.user_id = App.getUser().id;
                        createUserVisitRequest.appointed_time_from = mSelectTimeVisit;
                        createUserVisitRequest.appointed_time_to = mSelectTimeVisit + mSelectTimeDuration;
                        Core.get().VisitsControl().createUserVisit(createUserVisitRequest);
                        Toast.makeText(this, (Core.get().LocalizationControl().getText(R.id.visits_create_ok)), Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, Core.get().LocalizationControl().getText(R.id.no_select_visit_title), Toast.LENGTH_LONG).show();
                        btnCreate.setEnabled(true);
                    }
                } else {
                    Toast.makeText(this, Core.get().LocalizationControl().getText(R.id.no_select_visit_member), Toast.LENGTH_LONG).show();
                    btnCreate.setEnabled(true);
                }
            } else {
                Toast.makeText(this, "Схоже, що нема інтернет-з'єднання", Toast.LENGTH_LONG).show();
            }
        });

        Core.get().VisitsControl().getUsersRelationForVisits();
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



    private void setDateTimeSptamp(long selectTimeVisit) {

        if (selectTimeVisit != 0)
            mSelectTimeVisit = selectTimeVisit;
        else
            mSelectTimeVisit = System.currentTimeMillis() + 60 * 30 * 1000;
    }



    private void setDateTime(long selectTimeVisit) {

        if (selectTimeVisit != 0) {
            if (selectTimeVisit >= getStartCurrentDay() && selectTimeVisit < getEndCurrentDay()) {
                if (selectTimeVisit < (System.currentTimeMillis()/1000 + 60 * 30))
                    mSelectTimeVisit = (System.currentTimeMillis()/1000 + 60 * 30);
                else mSelectTimeVisit = selectTimeVisit;
            } else {
                mSelectTimeVisit = (selectTimeVisit + 9 * 60 * 60);
            }
        } else {
            mSelectTimeVisit = (System.currentTimeMillis()/1000 + 60 * 30);
        }

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mSelectTimeVisit * 1000);
        Date date0 = new Date(mSelectTimeVisit * 1000);
        DateFormat format1 = new SimpleDateFormat("dd MMM yyyy", new Locale(App.getLanguage().equals("ru") ? "rus" : "uk", "UA"));
        String formatted1 = format1.format(date0);
        DateFormat format2 = new SimpleDateFormat("HH:mm");
        String formatted2 = format2.format(date0);
        tvDateValue.setText(formatted1);
        tvTimeValue.setText(formatted2);
    }

    private long getStartCurrentDay() {
        long startCurrentDay = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startCurrentDay);
        int startYear = cal.get(Calendar.YEAR);
        int starthMonth = cal.get(Calendar.MONTH);
        int startDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(startYear, starthMonth, startDay, 0, 0, 0);
        startCurrentDay = cal.getTimeInMillis() / 1000;
        return startCurrentDay;
    }

    private long getEndCurrentDay() {
        long endCurrentDay = System.currentTimeMillis();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(endCurrentDay);
        int endYear = cal.get(Calendar.YEAR);
        int endMonth = cal.get(Calendar.MONTH);
        int endDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(endYear, endMonth, endDay, 23, 59, 59);
        endCurrentDay = cal.getTimeInMillis() / 1000;
        return endCurrentDay;
    }


    @Override
    protected void onLocalizationUpdate() {
        inputVisit.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_title_input_default));
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_toolbar_title));
        tvVisit.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_title));
        tvNote.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_notes_title));
        tvDateTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_layout_date_title));
        tvTimeTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_layout_time_title));
        tvDurationTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_layout_duration_title));

        layoutVisit.setHint(Core.get().LocalizationControl().getText(R.id.activity_add_visit_title_input));
        layoutNote.setHint(Core.get().LocalizationControl().getText(R.id.activity_add_visit_notes_title_input));

        tvSelectSecondTitle.setText(App.getUser().specialization == null || App.getUser().specialization.is_medpred == null || App.getUser().specialization.is_medpred == 0 ?
                Core.get().LocalizationControl().getText(R.id.activity_add_visit_add_mp) : Core.get().LocalizationControl().getText(R.id.activity_add_visit_add_doc));

        btnCreate.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_btn_create));

        tvSelectSecondValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_value_add_second));
        setDurationText();
    }





    private void showPopupMenuDuration(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popupmenu_duration_visit);
        MenuItem item0 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_0);
        item0.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_0));
        MenuItem item1 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_1);
        item1.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_1));
        MenuItem item2 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_2);
        item2.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_2));
        MenuItem item3 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_3);
        item3.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_3));
        MenuItem item4 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_4);
        item4.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_4));
        MenuItem item5 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_5);
        item5.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_5));

        MenuItem item6 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_6);
        item6.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_6));
        MenuItem item7 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_7);
        item7.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_7));
        MenuItem item8 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_8);
        item8.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_8));
        MenuItem item9 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_9);
        item9.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_9));
        MenuItem item10 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_10);
        item10.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_10));
        MenuItem item11 = popupMenu.getMenu().findItem(R.id.activity_add_visit_popup_menu_11);
        item11.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_11));

        popupMenu.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.activity_add_visit_popup_menu_0:
                    mSelectTimeDuration = 10 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_1:
                    mSelectTimeDuration = 20 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_2:
                    mSelectTimeDuration = 30 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_3:
                    mSelectTimeDuration = 40 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_4:
                    mSelectTimeDuration = 50 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_5:
                    mSelectTimeDuration = 60 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_6:
                    mSelectTimeDuration = 70 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_7:
                    mSelectTimeDuration = 80 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_8:
                    mSelectTimeDuration = 90 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_9:
                    mSelectTimeDuration = 100 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_10:
                    mSelectTimeDuration = 110 * 60;
                    setDurationText();
                    return true;
                case R.id.activity_add_visit_popup_menu_11:
                    mSelectTimeDuration = 120 * 60;
                    setDurationText();
                    return true;
                default:
                    return false;
            }
        });
        popupMenu.show();
    }

    private void setDurationText() {
        switch (mSelectTimeDuration) {
            case 10 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_0));
                break;
            case 20 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_1));
                break;
            case 30 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_2));
                break;
            case 40 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_3));
                break;
            case 50 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_4));
                break;
            case 60 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_5));
                break;
            case 70 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_6));
                break;
            case 80 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_7));
                break;
            case 90 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_8));
                break;
            case 100 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_9));
                break;
            case 110 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_10));
                break;
            case 120 * 60:
                tvDurationValue.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_popup_menu_11));
                break;

        }
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_MEMBERS_VISIT_LOAD:
                if (Core.get().VisitsControl().getMemberVisitItems().length > 1) {
                    //startActivityForResult(new Intent(AddVisitActivity.this, SelectSecondMemberActivity.class), SELECT_MEMBER_VISIT);
                } else {
                    if (Core.get().VisitsControl().getMemberVisitItems().length > 0) {
                        if (Core.get().VisitsControl().getMemberVisitItems()[0].userOne.id != App.getUser().id) {
                            tvSelectSecondValue.setText(TextUtils.getString(Core.get().VisitsControl().getMemberVisitItems()[0].userOne.last_name) + " " +
                                    TextUtils.getString(Core.get().VisitsControl().getMemberVisitItems()[0].userOne.first_name) + " " +
                                    TextUtils.getString(Core.get().VisitsControl().getMemberVisitItems()[0].userOne.middle_name));
                            mSelectUserVisitId = Core.get().VisitsControl().getMemberVisitItems()[0].userOne.id;
                        } else if (Core.get().VisitsControl().getMemberVisitItems()[0].userTwo.id != App.getUser().id) {
                            tvSelectSecondValue.setText(TextUtils.getString(Core.get().VisitsControl().getMemberVisitItems()[0].userTwo.last_name) + " " +
                                    TextUtils.getString(Core.get().VisitsControl().getMemberVisitItems()[0].userTwo.first_name) + " " +
                                    TextUtils.getString(Core.get().VisitsControl().getMemberVisitItems()[0].userTwo.middle_name));
                            mSelectUserVisitId = Core.get().VisitsControl().getMemberVisitItems()[0].userTwo.id;
                        }

                    } else {
                        DialogBuilder.showInfoDialog(this, "Повідомлення", "Немає контактів для створення зустрічі");
                    }
                }
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_MEMBER_VISIT && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                mSelectUserVisitId = data.getIntExtra(ADD_VISIT_ID_MEMBER, -1);
                tvSelectSecondValue.setText(TextUtils.getString(data.getStringExtra(ADD_VISIT_LAST_NAME) + " " +
                        TextUtils.getString(data.getStringExtra(ADD_VISIT_FIRST_NAME)) + " " +
                        TextUtils.getString(data.getStringExtra(ADD_VISIT_MIDDLE_NAME))));
            }
        }
    }
}
