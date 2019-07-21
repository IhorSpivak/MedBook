package ua.profarma.medbook.ui.custom_views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.utils.LogUtils;
import ua.profarma.medbook.utils.TimeUtils;

import static java.util.Calendar.YEAR;

public class MainVisitCalendarView extends FrameLayout implements View.OnClickListener {

    private TextView tvSun;
    private TextView tvMon;
    private TextView tvTue;
    private TextView tvWed;
    private TextView tvThu;
    private TextView tvFri;
    private TextView tvSat;
    private TextView tvCurrentDate;
    private ViewPager viewPager;

    private int month, year;

    private FilterChangeListener filterChangeListener;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private HashSet<Long> visits = new HashSet<>();

    private static final int todayColor = Color.argb(255, 62, 132, 213);
    private static final int backgroundRes = R.drawable.circle_primary;

    private Locale locale = new Locale(Core.get().LocalizationControl().getText(R.id.calendar_local));
    private Calendar currentMonth = Calendar.getInstance(locale);
    private Calendar minMonth = Calendar.getInstance(locale);
    private Calendar pickedMonth = Calendar.getInstance(locale);

    private Calendar calCurrentSelected = Calendar.getInstance(locale);

    private CalendarViewPagerAdapter adapter;


    public MainVisitCalendarView(@NonNull Context context) {
        this(context, null);
    }

    public MainVisitCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainVisitCalendarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        minMonth = Calendar.getInstance();
        minMonth.setTimeInMillis(0);
        initializeUILayout();
    }

    private void initializeUILayout() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.main_visits_calendar, this, false);
        addView(view);

        tvCurrentDate = view.findViewById(R.id.main_visits_calendar_current_date);
        viewPager = view.findViewById(R.id.main_visits_calendar_view_pager);

        tvMon = findViewById(R.id.main_visits_calendar_mon);
        tvTue = findViewById(R.id.main_visits_calendar_tue);
        tvWed = findViewById(R.id.main_visits_calendar_wed);
        tvThu = findViewById(R.id.main_visits_calendar_thu);
        tvFri = findViewById(R.id.main_visits_calendar_fri);
        tvSat = findViewById(R.id.main_visits_calendar_sat);
        tvSun = findViewById(R.id.main_visits_calendar_sun);

        onLocalizationUpdate();
    }

    public void initViewPager() {
        adapter = new CalendarViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pickedMonth = getCalendarForPosition(position);
                updateCalendarTitle();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(diffMonths());
        //notifyPages();
        updateFilterDates();
    }

    private void updateFilterDates() {

        visits.clear();
        if(Core.get().VisitsControl().getUserVisitItems() != null)
        for (int i = 0; i < Core.get().VisitsControl().getUserVisitItems().length; i++) {

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Core.get().VisitsControl().getUserVisitItems()[i].time_from * 1000);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(year, month, day, 0, 0, 0);
            Long today = Long.valueOf(cal.getTimeInMillis());
            visits.add(today);
        }
        LogUtils.logD("fu56utygjh", "visits.size = " + visits.size());
        notifyPages();
    }

    private int diffMonths() {
        int diffYear = currentMonth.get(Calendar.YEAR) - minMonth.get(Calendar.YEAR);
        int diffMonth = currentMonth.get(Calendar.MONTH) - minMonth.get(Calendar.MONTH);

        return diffYear * 12 + diffMonth;
    }


    private void updateCalendarTitle() {
        year = pickedMonth.get(YEAR);
        month = pickedMonth.get(Calendar.MONTH);

        tvCurrentDate.setText(TimeUtils.getMonthAndYearString(month, year));
    }


    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        if (tag instanceof Calendar) {
            calCurrentSelected = (Calendar) tag;
            //calCurrentSelected.setTimeZone(TimeZone.getTimeZone("GMT"));
            if (filterChangeListener != null) {
                filterChangeListener.filterChanged(calCurrentSelected);
            }

            notifyPages();
        }
    }

    private void notifyPages() {
        for (int i = 0; i < viewPager.getChildCount(); i++) {
            View view = viewPager.getChildAt(i);
            if (view == null) {
                return;
            }
            CalendarViewPagerAdapter.CalendarGrid calendarGrid = (CalendarViewPagerAdapter.CalendarGrid) view;

            calendarGrid.notifyAdapter();
        }
    }

    private Calendar getCalendarForPosition(int position) {
        Calendar cal = (Calendar) minMonth.clone();
        cal.add(Calendar.MONTH, position);
        return cal;
    }

    public void onLocalizationUpdate() {
        tvMon.setText(Core.get().LocalizationControl().getText(R.id.main_visits_calendar_mon));
        tvTue.setText(Core.get().LocalizationControl().getText(R.id.main_visits_calendar_tue));
        tvWed.setText(Core.get().LocalizationControl().getText(R.id.main_visits_calendar_wed));
        tvThu.setText(Core.get().LocalizationControl().getText(R.id.main_visits_calendar_thu));
        tvFri.setText(Core.get().LocalizationControl().getText(R.id.main_visits_calendar_fri));
        tvSat.setText(Core.get().LocalizationControl().getText(R.id.main_visits_calendar_sat));
        tvSun.setText(Core.get().LocalizationControl().getText(R.id.main_visits_calendar_sun));

        locale = new Locale(Core.get().LocalizationControl().getText(R.id.calendar_local));
        updateCalendarTitle();
    }

    private class CalendarViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            CalendarGrid gridView = new CalendarGrid(getContext(), getCalendarForPosition(position), MainVisitCalendarView.this);
            viewPager.addView(gridView);
            return gridView;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof View) {
                container.removeView((View) object);
            }
        }

        private class CalendarGrid extends GridView {
            private CalendarAdapter mAdapter;
            private final Calendar calendar;
            private final OnClickListener onClickListener;
            private List<Date> dayValueInCells = new ArrayList<>();

            public Calendar getCalendar() {
                return calendar;
            }

            public CalendarGrid(Context context, @NonNull Calendar calendar, OnClickListener onClickListener) {
                super(context);
                this.calendar = calendar;
                this.onClickListener = onClickListener;
                initGrid();
                setUpCalendarAdapter();
            }

            private void initGrid() {
                setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                setScrollContainer(false);
                setNumColumns(7);
                setVerticalScrollBarEnabled(false);
                setHorizontalScrollBarEnabled(false);
                int padding = (int) getResources().getDimension(R.dimen.padding_default_and_half);
                setPadding(padding, 0, padding, 0);
                setOnTouchListener((v, event) -> event.getAction() == MotionEvent.ACTION_MOVE);
            }

            public void setUpCalendarAdapter() {
                Calendar mCal = (Calendar) calendar.clone();
                mCal.set(Calendar.DAY_OF_MONTH, 1);
                int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 2;
                mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);
                dayValueInCells.clear();

                while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
                    dayValueInCells.add(mCal.getTime());
                    mCal.add(Calendar.DAY_OF_MONTH, 1);
                }

                mAdapter = new CalendarAdapter(getContext(), dayValueInCells);
                setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            public void notifyAdapter() {
                mAdapter.notifyDataSetChanged();
            }

            public class CalendarAdapter extends ArrayAdapter {
                private LayoutInflater mInflater;
                private List<Date> monthlyDates;
                private Calendar today;

                public CalendarAdapter(Context context, List<Date> days) {
                    super(context, R.layout.calendar_cell);
                    this.monthlyDates = days;
                    today = Calendar.getInstance();
                    mInflater = LayoutInflater.from(context);
                }

                @NonNull
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = convertView;
                    if (view == null) {
                        view = mInflater.inflate(R.layout.calendar_cell, parent, false);
                    }

                    //TODO
                    //today.setTimeInMillis(SyncTimeManager.getInstance().getSynchronizedTime());
                    Date mDate = monthlyDates.get(position);
                    Calendar dateCal = Calendar.getInstance();
                    dateCal.setTime(mDate);
                    int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
                    int displayMonth = dateCal.get(Calendar.MONTH);
                    int displayYear = dateCal.get(YEAR);

                    view.findViewById(R.id.back).setBackgroundResource(0);
                    view.findViewById(R.id.mini_back).setBackgroundResource(0);
                    TextView cellNumber = view.findViewById(R.id.text);
                    Drawable dot = view.findViewById(R.id.dot).getBackground();

                    if (displayMonth == calendar.get(Calendar.MONTH) && displayYear == calendar.get(Calendar.YEAR)) {
                        cellNumber.setTextColor(getResources().getColor(R.color.calendar_day));
                        if (dot != null) {
                            dot.setColorFilter(null);
                        }
                    } else {
                        cellNumber.setTextColor(getResources().getColor(R.color.calendar_not_included_current_month));
                        dot.setColorFilter(getResources().getColor(R.color.calendar_not_included_current_month), PorterDuff.Mode.SRC_IN);
                    }

                    cellNumber.setText(String.valueOf(dayValue));
                    setupTodayMark(today, dateCal, view);
                    view.setTag(dateCal);
                    view.setOnClickListener(onClickListener);

                    setUpWithVisits(view, dateCal);

                    return view;
                }

                @Override
                public int getCount() {
                    return monthlyDates.size();
                }

                @Nullable
                @Override
                public Object getItem(int position) {
                    return monthlyDates.get(position);
                }

                @Override
                public int getPosition(Object item) {
                    return monthlyDates.indexOf(item);
                }
            }

            protected void setupTodayMark(Calendar today, Calendar dateCal, View view) {
                if (today.get(YEAR) == dateCal.get(YEAR)
                        && today.get(Calendar.DAY_OF_YEAR) == dateCal.get(Calendar.DAY_OF_YEAR)) {
                    ((TextView) view.findViewById(R.id.text)).setTextColor(getContext().getResources().getColor(R.color.black));
                    view.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

                }
            }

            private void setUpWithVisits(View v, Calendar dateCal) {
                drawDot(v, dateCal);
                drawBackground(v, dateCal);
            }

            private void drawBackground(View v, Calendar dateCal) {
                if (calCurrentSelected == null) {
                    return;
                }

                if (calCurrentSelected.get(Calendar.YEAR) != dateCal.get(Calendar.YEAR) || calCurrentSelected.get(Calendar.DAY_OF_YEAR) != dateCal.get(Calendar.DAY_OF_YEAR)) {
                    return;
                }

                Calendar calendar = Calendar.getInstance();

                TextView text = v.findViewById(R.id.text);
                View back = v.findViewById(R.id.mini_back);
                Drawable dot = v.findViewById(R.id.dot).getBackground();
                View line = v.findViewById(R.id.line);

                back.setBackgroundResource(backgroundRes);

                if (text != null) {
                    text.setTextColor(Color.WHITE);
                    //text.setTextColor(Color.BLACK);
                }
                if (dot != null) {
                    dot.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                    //dot.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                }
                if (line != null) {
                    line.setBackgroundColor(Color.WHITE);
                    // line.setBackgroundColor(Color.BLACK);
                }

                if (dateCal.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) && dateCal.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR)) {
                    if (text != null) {
                        text.setTextColor(Color.WHITE);
                    }
                    back.getBackground().setColorFilter(todayColor, PorterDuff.Mode.SRC_IN);
                }
            }

            private void drawDot(View view, Calendar dateCal) {
                View dot = view.findViewById(R.id.dot);
                dot.setVisibility(visits.contains(createRowDate(dateCal)) ? View.VISIBLE : INVISIBLE);
            }
        }
    }

    public static Long createRowDate(Calendar currentDate) {

        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH);
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        currentDate.set(year, month, day, 0, 0, 0);
        return Long.valueOf(currentDate.getTimeInMillis());
    }

    public interface FilterChangeListener {
        void filterChanged(Calendar cal);
    }

    public void setFilterChangeListener(FilterChangeListener filterChangeListener) {
        this.filterChangeListener = filterChangeListener;
    }

}
