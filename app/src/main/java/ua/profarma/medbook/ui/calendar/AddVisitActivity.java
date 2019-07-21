package ua.profarma.medbook.ui.calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.recyclerviews.visits_second_member.SecondMemberVisitRecyclerItem;
import ua.profarma.medbook.types.requests.CreateUserVisitRequest;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;
import ua.profarma.medbook.utils.TextUtils;

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

        inputVisit = findViewById(R.id.activity_add_visit_title_input);
        inputVisit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    TextUtils.hideKeyboard(v);
                }
            }
        });
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


        setDateTime(getIntent().getLongExtra(SELECT_DAY, 0));
        mSelectTimeDuration = 10 * 60;
        setDurationText();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mSelectTimeVisit);
        int startYear = cal.get(Calendar.YEAR);
        int starthMonth = cal.get(Calendar.MONTH);
        int startDay = cal.get(Calendar.DAY_OF_MONTH);
        int startHours = cal.get(Calendar.HOUR);
        int startMinutes = cal.get(Calendar.MINUTE);

        llDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddVisitActivity.this, dateListener, startYear, starthMonth, startDay);
            datePickerDialog.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_date_picker_title));
            datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, Core.get().LocalizationControl().getText(R.id.activity_add_visit_picker_btn_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    dateListener.onDateSet(datePicker, datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());

                }
            });
            datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, Core.get().LocalizationControl().getText(R.id.activity_add_visit_picker_btn_cancel), (Message) null);
            datePickerDialog.show();
        });

        llTime.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    AddVisitActivity.this, timeListener, startHours, startMinutes, true);
            //timePickerDialog.setTitle(Core.get().LocalizationControl().getText(R.id.activity_add_visit_time_picker_title));
//            timePickerDialog.setButton(TimePickerDialog.BUTTON_POSITIVE, Core.get().LocalizationControl().getText(R.id.activity_add_visit_picker_btn_ok), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    final Calendar c = Calendar.getInstance();
//                    timePickerDialog.updateTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
//                    onTimeSet(null, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
//
//                }
//            });
//            timePickerDialog.setButton(TimePickerDialog.BUTTON_NEGATIVE, Core.get().LocalizationControl().getText(R.id.activity_add_visit_picker_btn_cancel), (Message) null);
            timePickerDialog.show();
        });

        llDuration.setOnClickListener(view -> showPopupMenuDuration(view));

        llSecondMember.setOnClickListener(view -> startActivityForResult(new Intent(AddVisitActivity.this, SelectSecondMemberActivity.class), SELECT_MEMBER_VISIT)/*Core.get().VisitsControl().getUsersRelationForVisits()*/);

        btnCreate.setOnClickListener(view -> {
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
                    finish();
                } else {
                    AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_select_visit_title), true);
                    btnCreate.setEnabled(true);
                }
            } else {
                AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_select_visit_member), true);
                btnCreate.setEnabled(true);
            }
        });

        Core.get().VisitsControl().getUsersRelationForVisits();
        onLocalizationUpdate();
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

//    private void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(mSelectTimeVisit);
//        int startYear = cal.get(Calendar.YEAR);
//        int starthMonth = cal.get(Calendar.MONTH);
//        int startDay = cal.get(Calendar.DAY_OF_MONTH);
//        int startHours = cal.get(Calendar.HOUR);
//        int startMinutes = cal.get(Calendar.MINUTE);
//        if ((hourOfDay * 60 + minute) < (startHours * 60 + startMinutes + 10) || (hourOfDay == 23 && minute > 50)) {
//            setDateTime(0);
//            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.activity_add_visit_error_time_select), true);
//        } else {
//            cal.set(startYear, starthMonth, startDay, hourOfDay, minute);
//            mSelectTimeVisit = cal.getTimeInMillis();
//            setDateTime(mSelectTimeVisit);
//        }
//    }


    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(mSelectTimeVisit * 1000);
            int startYear = cal.get(Calendar.YEAR);
            int starthMonth = cal.get(Calendar.MONTH);
            int startDay = cal.get(Calendar.DAY_OF_MONTH);

            cal.set(startYear, starthMonth, startDay, hourOfDay, minute);

            long currentTime = (System.currentTimeMillis() / 1000);
            long selectTime = (cal.getTimeInMillis() / 1000);

            LogUtils.logD("fthgjghjghj", "currentTime = " + currentTime);
            LogUtils.logD("fthgjghjghj", "selectTime  = " + selectTime);

            if (selectTime < (currentTime + 30 * 60)) {
                setDateTime(0);
                AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.activity_add_visit_error_time_select), true);
            } else {
                cal.set(startYear, starthMonth, startDay, hourOfDay, minute);
                mSelectTimeVisit = cal.getTimeInMillis();
                LogUtils.logD("fthgjghjghj", "mSelectTimeVisit  = " + selectTime);
                setDateTime(mSelectTimeVisit / 1000);
            }
        }
    };
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(mSelectTimeVisit * 1000);
            int startYear = cal.get(Calendar.YEAR);
            int starthMonth = cal.get(Calendar.MONTH);
            int startDay = cal.get(Calendar.DAY_OF_MONTH);
            if (year < startYear || month < starthMonth || dayOfMonth < startDay) {
                setDateTime(0);
                AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.activity_add_visit_error_date_select), true);
            } else {
                cal.set(year, month, dayOfMonth);
                mSelectTimeVisit = cal.getTimeInMillis();
                setDateTime(mSelectTimeVisit / 1000);
            }
        }
    };

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


    private void setDateTime(long selectTimeVisit) {
        LogUtils.logD("fthgjghjghj", "StartCurrentDay = " + getStartCurrentDay());
        LogUtils.logD("fthgjghjghj", "selectTimeVisit = " + selectTimeVisit);
        LogUtils.logD("fthgjghjghj", "EndCurrentDay   = " + getEndCurrentDay());
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
