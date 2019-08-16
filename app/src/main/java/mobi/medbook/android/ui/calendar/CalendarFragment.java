package mobi.medbook.android.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.recyclerviews.visits.VisitRecyclerItem;
import mobi.medbook.android.recyclerviews.visits.VisitRecyclerItems;
import mobi.medbook.android.recyclerviews.visits.VisitsRecyclerView;
import mobi.medbook.android.types.visits.UserVisit;
import mobi.medbook.android.ui.custom_views.MainVisitCalendarView;
import mobi.medbook.android.ui.custom_views.MedBookFragment;
import mobi.medbook.android.utils.LogUtils;


public class CalendarFragment extends MedBookFragment implements MainVisitCalendarView.FilterChangeListener {

    private String TAG = "AppMedbook/CalendarFragment";
    private View rootView;
    private MainVisitCalendarView calendarView;
    private VisitsRecyclerView list;
    private VisitRecyclerItems items;
    private long today;
    private long selectDay = 0;


    public static CalendarFragment newInstance() {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = rootView.findViewById(R.id.fragment_calendar_calendar_view);
        list = rootView.findViewById(R.id.fragment_calendar_list);
        list.init();
        ImageView imRecentsAddVisits = rootView.findViewById(R.id.main_visits_calendar_recents_visits);
        imRecentsAddVisits.setOnClickListener(view -> {
                startActivity(new Intent(getActivity(), IncomingVisits.class));
        });
        ImageView imAddVisit = rootView.findViewById(R.id.main_visits_calendar_add_visits);
        imAddVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        v.setEnabled(true);
                    }
                }, 2000);
                if ((selectDay == 0) || !(selectDay < today)) {
                    Intent intent = new Intent(getActivity(), AddVisitActivity.class);
                    intent.putExtra(AddVisitActivity.SELECT_DAY, selectDay);
                    startActivity(intent);
                }
            }
        });
        calendarView.setFilterChangeListener(this);

        today = VisitUtils.getTodayTime();
        //LogUtils.logD(TAG, "current time GMT +0, 00:00 = " + today);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                calendarView.initViewPager();
            }
        });
        Core.get().VisitsControl().getUserVisits();
    }

    @Override
    protected void onLocalizationUpdate() {

    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_VISIT_LOAD:
                list.clear();
                if (items == null) items = new VisitRecyclerItems();
                for (int i = 0; i < Core.get().VisitsControl().getUserVisitItems().length; i++) {
                    UserVisit item = Core.get().VisitsControl().getUserVisitItems()[i];
                    //LogUtils.logD(TAG, "selectDay = " + selectDay);
                    //LogUtils.logD(TAG, "item.time_from = " + item.time_from);
                    //LogUtils.logD(TAG, "today = " + today);
                    if (item.partner != null) {
                        if (selectDay == 0) {
                            if (item.time_from >= today && item.time_from < (today + 86399)) {
                                //LogUtils.logD(TAG, "add today");
                                list.itemAdd(new VisitRecyclerItem(item, today));
                            }
                        } else {
                            if (item.time_from >= selectDay && item.time_from < (selectDay + 86399)) {
                                //LogUtils.logD(TAG, "add selectday");
                                list.itemAdd(new VisitRecyclerItem(item, today));
                            }
                        }
                        //LogUtils.logD(TAG, "================================");
                        //items.add(new VisitRecyclerItem(Core.get().VisitsControl().getUserVisitItems()[i], today));
                    }
                }
//                list.clear();
//                for (RecyclerItem item : items) {
//                    if (((VisitRecyclerItem) item).getUserVisit().time_from > today && ((VisitRecyclerItem) item).getUserVisit().time_from < (today + 86400))
//                        list.itemAdd(item);
//                }
                calendarView.initViewPager();
                break;
        }
    }

    @Override
    public void filterChanged(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        LogUtils.logD(TAG, "filterChanged time year = " + year + ", month = " + month + ", day = " + day);
        cal.set(year, month, day, 0, 0, 0);
        selectDay = cal.getTimeInMillis() / 1000;
        list.clear();
        for (int i = 0; i < Core.get().VisitsControl().getUserVisitItems().length; i++) {
            UserVisit item = Core.get().VisitsControl().getUserVisitItems()[i];
            LogUtils.logD(TAG, "filterChanged id = " + item.id + ", time_from = " + item.time_from + ", selectDay = " + selectDay + ", title = " + item.visit.title);
            if (item.time_from >= selectDay && item.time_from < (selectDay + 86399) && item.partner != null)
                list.itemAdd(new VisitRecyclerItem(item, today));
        }
    }
}
