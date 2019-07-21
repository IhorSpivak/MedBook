package ua.profarma.medbook.ui.calendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.recyclerviews.visits.VisitRecyclerItem;
import ua.profarma.medbook.recyclerviews.visits.VisitRecyclerItems;
import ua.profarma.medbook.recyclerviews.visits.VisitsRecyclerView;
import ua.profarma.medbook.types.visits.UserVisit;
import ua.profarma.medbook.ui.custom_views.MainVisitCalendarView;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.utils.DialogBuilder;
import ua.profarma.medbook.utils.LogUtils;

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
            if (Core.get().VisitsControl().getUnaccepted() != null && Core.get().VisitsControl().getUnaccepted().length > 0)
                startActivity(new Intent(getActivity(), IncomingVisits.class));
            else
                DialogBuilder.showInfoDialog(getActivity(), Core.get().LocalizationControl().getText(R.id.general_message),
                        Core.get().LocalizationControl().getText(R.id.calendar_fragment_no_exist_unaccepted_visits));
        });
        ImageView imAddVisit = rootView.findViewById(R.id.main_visits_calendar_add_visits);
        imAddVisit.setOnClickListener(view -> {
            LogUtils.logD("5yfhghjrthf", "selectDay = " + selectDay + ", today = " + today);
            if ((selectDay == 0) || !(selectDay < today)) {
                Intent intent = new Intent(getActivity(), AddVisitActivity.class);
                intent.putExtra(AddVisitActivity.SELECT_DAY, selectDay);
                startActivity(intent);
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
