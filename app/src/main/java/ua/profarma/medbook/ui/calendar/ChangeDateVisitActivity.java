package ua.profarma.medbook.ui.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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
import androidx.appcompat.widget.PopupMenu;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.types.visits.UserVisit;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;
import ua.profarma.medbook.utils.TimeUtils;

public class ChangeDateVisitActivity extends MedBookActivity {

    private TextView tvTitleToolbar;
    private TextView tvTitle;
    private TextView tvSubTitle;
    private TextView tvTitleNew;
    private Button btnCreate;

    private TextView tvDateTitle;
    private TextView tvDateValue;
    private TextView tvTimeTitle;
    private TextView tvTimeValue;
    private TextView tvDurationTitle;
    private TextView tvDurationValue;

    private LinearLayout llDate;
    private LinearLayout llTime;
    private LinearLayout llDuration;

    private int mIdVisit;
    private UserVisit userVisit;
    private int mSelectTimeDuration;
    private long mSelectTimeVisit;

    public static final String KEY_ID_VISIT = "KEY_ID_VISIT";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_date_visit);

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

        mSelectTimeDuration = (int) (userVisit.time_to - userVisit.time_from);

        ImageView imvClose = findViewById(R.id.activity_change_date_visit_toolbar_close);
        imvClose.setOnClickListener(view -> finish());
        tvTitleToolbar = findViewById(R.id.activity_change_date_visit_toolbar_title);

        tvTitle = findViewById(R.id.activity_change_date_visit_title);
        tvSubTitle = findViewById(R.id.activity_change_date_visit_subtitle);
        tvTitleNew = findViewById(R.id.activity_change_date_visit_title_new);
        btnCreate = findViewById(R.id.activity_change_date_visit_btn_create);


        llDate = findViewById(R.id.activity_change_date_visit_layout_date);
        llTime = findViewById(R.id.activity_change_date_visit_layout_time);
        llDuration = findViewById(R.id.activity_change_date_visit_layout_duration);

        tvDateTitle = findViewById(R.id.activity_change_date_visit_layout_date_title);
        tvDateValue = findViewById(R.id.activity_change_date_visit_layout_date_value);
        tvTimeTitle = findViewById(R.id.activity_change_date_visit_layout_time_title);
        tvTimeValue = findViewById(R.id.activity_change_date_visit_layout_time_value);
        tvDurationTitle = findViewById(R.id.activity_change_date_visit_layout_duration_title);
        tvDurationValue = findViewById(R.id.activity_change_date_visit_layout_duration_value);

        Calendar calFrom = Calendar.getInstance();
        calFrom.setTimeInMillis(userVisit.time_from * 1000);
        int startYear = calFrom.get(Calendar.YEAR);
        int starthMonth = calFrom.get(Calendar.MONTH);
        int startDay = calFrom.get(Calendar.DAY_OF_MONTH);
        int startHours = calFrom.get(Calendar.HOUR);
        int startMinutes = calFrom.get(Calendar.MINUTE);

        llDate.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    ChangeDateVisitActivity.this, dateListener, startYear, starthMonth, startDay);
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
                    ChangeDateVisitActivity.this, timeListener, startHours, startMinutes, true);
            timePickerDialog.show();
        });

        llDuration.setOnClickListener(view -> showPopupMenuDuration(view));


        Date date0 = new Date(userVisit.time_from * 1000);
        DateFormat format1 = new SimpleDateFormat("dd MMM yyyy", new Locale(App.getLanguage().equals("ru") ? "rus" : "uk", "UA"));
        String formatted1 = format1.format(date0);
        DateFormat format2 = new SimpleDateFormat("HH:mm");
        String formatted2 = format2.format(date0);
        tvDateValue.setText(formatted1);
        tvTimeValue.setText(formatted2);
        tvDurationValue.setText(String.valueOf(userVisit.time_to - userVisit.time_from));

        int year = calFrom.get(Calendar.YEAR);
        int month = calFrom.get(Calendar.MONTH);
        int day = calFrom.get(Calendar.DAY_OF_MONTH);
        int dayWeek = calFrom.get(Calendar.DAY_OF_WEEK);
        int hourFrom = calFrom.get(Calendar.HOUR_OF_DAY);
        int minutesFrom = calFrom.get(Calendar.MINUTE);

        Calendar calTo = Calendar.getInstance();
        calTo.setTimeInMillis(userVisit.time_to * 1000);

        int hourTo = calTo.get(Calendar.HOUR_OF_DAY);
        int minutesTo = calTo.get(Calendar.MINUTE);


        tvSubTitle.setText(TimeUtils.getDayOfWeek(dayWeek) + ", " + day + " " + TimeUtils.getMonthName_s(month)
                + " " + year + "\n" + hourFrom + ":" + TimeUtils.getFormatMinutes(minutesFrom) + " - " + hourTo + ":" + TimeUtils.getFormatMinutes(minutesTo));

        btnCreate.setOnClickListener(view -> {
            Core.get().VisitsControl().changeVisitTime(userVisit.id, mSelectTimeVisit / 1000, mSelectTimeDuration);
            finish();
        });
        setDateTime(userVisit.time_from * 1000);
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvDateTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_layout_date_title));
        tvTimeTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_layout_time_title));
        tvDurationTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_visit_layout_duration_title));

        tvTitleToolbar.setText(Core.get().LocalizationControl().getText(R.id.activity_change_date_visit_toolbar_title));
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_change_date_visit_title));
        tvTitleNew.setText(Core.get().LocalizationControl().getText(R.id.activity_change_date_visit_title_new));
        btnCreate.setText(Core.get().LocalizationControl().getText(R.id.activity_change_date_visit_btn_create));
        setDurationText();
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

    TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(mSelectTimeVisit);
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
                setDateTime(mSelectTimeVisit);
            }
        }
    };
    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(mSelectTimeVisit);
            int startYear = cal.get(Calendar.YEAR);
            int starthMonth = cal.get(Calendar.MONTH);
            int startDay = cal.get(Calendar.DAY_OF_MONTH);
            if (year < startYear || month < starthMonth || dayOfMonth < startDay) {
                setDateTime(0);
                AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.activity_add_visit_error_date_select), true);
            } else {
                cal.set(year, month, dayOfMonth);
                mSelectTimeVisit = cal.getTimeInMillis();
                setDateTime(mSelectTimeVisit);
            }
        }
    };

    private void setDateTime(long selectTimeVisit) {
        LogUtils.logD("fthgjghjghj", "selectTimeVisit = " + selectTimeVisit);
        if (selectTimeVisit != 0)
            mSelectTimeVisit = selectTimeVisit;
        else
            mSelectTimeVisit = System.currentTimeMillis() + 60 * 30 * 1000;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(mSelectTimeVisit);
        Date date0 = new Date(mSelectTimeVisit);
        DateFormat format1 = new SimpleDateFormat("dd MMM yyyy", new Locale(App.getLanguage().equals("ru") ? "rus" : "uk", "UA"));
        String formatted1 = format1.format(date0);
        DateFormat format2 = new SimpleDateFormat("HH:mm");
        String formatted2 = format2.format(date0);
        tvDateValue.setText(formatted1);
        tvTimeValue.setText(formatted2);
    }


}
