package mobi.medbook.android.ui.today;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.notifications.EventLoad_5_nofications;
import mobi.medbook.android.recyclerviews.base.RecyclerItems;
import mobi.medbook.android.recyclerviews.tasks.TaskRecyclerItem;
import mobi.medbook.android.recyclerviews.tasks.TasksRecyclerView;
import mobi.medbook.android.recyclerviews.visits.VisitRecyclerItem;
import mobi.medbook.android.recyclerviews.visits.VisitsDashboardRecyclerView;
import mobi.medbook.android.types.TaskMaterial;
import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.types.notification.Notification;
import mobi.medbook.android.ui.NotificationsActivity;
import mobi.medbook.android.ui.TermAndPointAgreementsActivity;
import mobi.medbook.android.ui.calendar.VisitUtils;
import mobi.medbook.android.ui.custom_views.MedBookFragment;

import mobi.medbook.android.ui.custom_views.NonSwipeableViewPager;
import mobi.medbook.android.ui.materials.MaterialsEnum;
import mobi.medbook.android.ui.points.PointsActivity;
import mobi.medbook.android.ui.profile.ProfileActivity;
import mobi.medbook.android.ui.today.tabs.TodayPagerAdapter;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;


public class TodayFragment extends MedBookFragment {

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    private RecyclerItems itemsVisits;
    private String TAG = "AppMedbook/TodayFragment";

    private ArrayList<TaskMaterial> taskMaterials;
    private ArrayList<TaskMaterial> sortingTaskMaterials;

    private CardView taskContainer;
    private LinearLayout mNotificationsLL;
    private TextView mNotification_0_tv;
    private LinearLayout mNotification_1;
    private TextView mNotification_1_title;
    private TextView mNotification_1_description;
    private LinearLayout mNotification_2;
    private TextView mNotification_2_title;
    private TextView mNotification_2_description;
    private LinearLayout mNotification_3;
    private TextView mNotification_3_title;
    private TextView mNotification_3_description;
    private LinearLayout mNotification_4;
    private TextView mNotification_4_title;
    private TextView mNotification_4_description;
    private LinearLayout mNotification_5;
    private TextView mNotification_5_title;
    private TextView mNotification_5_description;

    private Button btnPoints;

    private TasksRecyclerView tasksRecyclerView;
    private TextView titleTasksList;
    private TextView tvProfile;


    private TabLayout newsTabs;
    private NonSwipeableViewPager newsPager;
    private mobi.medbook.android.ui.today.tabs.CustomTab viewTab1;
    private mobi.medbook.android.ui.today.tabs.CustomTab viewTab2;
    private int selectedTab = -1;
    private SwipeRefreshLayout swipeRefreshLayout;

    private VisitsDashboardRecyclerView list;
    private CardView calendarVisitBlock;







    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_today, container, false);
        LogUtils.logD("fhjgj67uygjgh", "onCreateView");
        list = rootView.findViewById(R.id.fragment_today_list_visits);
        calendarVisitBlock = rootView.findViewById(R.id.fragment_today_list_visits_block);
        calendarVisitBlock.setVisibility(View.GONE);
        list.init();
        itemsVisits = new RecyclerItems();

        updateUI();



        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateUI();
            }
        });

        newsTabs = rootView.findViewById(R.id.fragment_today_news_tabs);
        newsPager = rootView.findViewById(R.id.fragment_today_news_viewpager);

        viewTab1 = (mobi.medbook.android.ui.today.tabs.CustomTab) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_today, null, true);
        viewTab2 = (mobi.medbook.android.ui.today.tabs.CustomTab) LayoutInflater.from(getContext()).inflate(R.layout.custom_tab_today, null, true);

        newsTabs.addTab(newsTabs.newTab().setCustomView(viewTab1), 0);
        newsTabs.addTab(newsTabs.newTab().setCustomView(viewTab2), 1);

        viewTab1.setSelect();

        newsTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                newsPager.setCurrentItem(tab.getPosition(), true);
                selectedTab = tab.getPosition();
                switch (selectedTab) {
                    case 0:
                        viewTab1.setSelect();
                        viewTab2.setUnSelect();
                        //viewTab3.setUnSelect();
                        break;
                    case 1:
                        viewTab2.setSelect();
                        viewTab1.setUnSelect();
                        //viewTab3.setUnSelect();
                        break;
                    case 2:
                        //viewTab3.setSelect();
                        viewTab2.setUnSelect();
                        viewTab1.setUnSelect();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        TodayPagerAdapter adapter = new TodayPagerAdapter(getChildFragmentManager());
        newsPager.setAdapter(adapter);


        titleTasksList = rootView.findViewById(R.id.fragment_today_list_tasks_title);
        taskContainer = rootView.findViewById(R.id.fragment_today_tasks_container);
        taskContainer.setVisibility(View.GONE);

        titleTasksList.setVisibility(View.GONE);
        taskContainer.setVisibility(View.GONE);

        tasksRecyclerView = rootView.findViewById(R.id.fragment_today_list_tasks);
        LinearLayout mdm_btn = rootView.findViewById(R.id.mdm_btn);
        mdm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainDepartmentsActivity.class);
                startActivity(intent);
            }
        });
        btnPoints = rootView.findViewById(R.id.fragment_today_points);
        if (App.getUser() != null) {
            if (App.getUser().points_agreement == 0) {
                btnPoints.setTextSize(16);
                btnPoints.setText("Умови участі в програмі лояльності");
            } else {
                if (App.getUser().points != null) {
                    btnPoints.setTextSize(28);
                    Double points = Double.parseDouble(App.getUser().points);
                    btnPoints.setText(String.valueOf(points.intValue()));
                }
            }
        }
        btnPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getUser().points_agreement == 0) {
                    Intent intent = new Intent(getActivity(), TermAndPointAgreementsActivity.class);
                    intent.putExtra(TermAndPointAgreementsActivity.KEY_TYPE, 2);
                    getActivity().startActivity(intent);
                } else {
                    if (App.getUser().points_agreement == 1) {
                        Intent intent = new Intent(getActivity(), PointsActivity.class);
                        getActivity().startActivity(intent);
                    }
                }
            }
        });
        mNotificationsLL = rootView.findViewById(R.id.fragment_today_ll_notifications);
        mNotificationsLL.setVisibility(View.GONE);

        mNotificationsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent notificationActivityIntent = new Intent(getActivity(), NotificationsActivity.class);
                startActivity(notificationActivityIntent);
            }
        });
        mNotification_0_tv = rootView.findViewById(R.id.fragment_today_notification_0);
        mNotification_1 = rootView.findViewById(R.id.fragment_today_notification_1);
        mNotification_1_title = rootView.findViewById(R.id.fragment_today_notification_1_title);
        mNotification_1_description = rootView.findViewById(R.id.fragment_today_notification_1_text);
        mNotification_2 = rootView.findViewById(R.id.fragment_today_notification_2);
        mNotification_2_title = rootView.findViewById(R.id.fragment_today_notification_2_title);
        mNotification_2_description = rootView.findViewById(R.id.fragment_today_notification_2_text);
        mNotification_3 = rootView.findViewById(R.id.fragment_today_notification_3);
        mNotification_3_title = rootView.findViewById(R.id.fragment_today_notification_3_title);
        mNotification_3_description = rootView.findViewById(R.id.fragment_today_notification_3_text);
        mNotification_4 = rootView.findViewById(R.id.fragment_today_notification_4);
        mNotification_4_title = rootView.findViewById(R.id.fragment_today_notification_4_title);
        mNotification_4_description = rootView.findViewById(R.id.fragment_today_notification_4_text);
        mNotification_5 = rootView.findViewById(R.id.fragment_today_notification_5);
        mNotification_5_title = rootView.findViewById(R.id.fragment_today_notification_5_title);
        mNotification_5_description = rootView.findViewById(R.id.fragment_today_notification_5_text);


        tvProfile = rootView.findViewById(R.id.profile);
        if (App.getUser() != null)
            if (App.getUser().first_name != null && !App.getUser().first_name.isEmpty() &&
                    App.getUser().last_name != null && !App.getUser().last_name.isEmpty()) {
                String acronim = App.getUser().first_name.substring(0, 1) + App.getUser().last_name.substring(0, 1);
                tvProfile.setText(acronim.toUpperCase());
            }
        tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (App.getUser() != null)
                    //EventRouter.send(new EventStartProfileFragment());
                    getActivity().startActivity(new Intent(getActivity(), ProfileActivity.class));
                else
                    AppUtils.toastMessage("Інформація про користувача ще завантажуеться, спробуйте пізніше", true);
            }
        });
        if (Core.get().UserContentControl().getListMaterial() == null || Core.get().UserContentControl().getListMaterial().isEmpty()) {
            //TASK START IN ACTIVITY
//            if (App.accessTokenExist()) {
//                LogUtils.logD("GETMATERIAL", "onCreateView TodayFragment");
//                Core.get().UserContentControl().getMaterials();
//            }
        } else {
            createTaskMaterial();
        }
        onLocalizationUpdate();
        return rootView;
    }

    public void updateUI(){
        Core.get().UserControl().getUser();
        long time = System.currentTimeMillis() / 1000;
        App.clearUpdateLastTimeNotifications();
        App.clearUpdateLastTimeMaterials();
        App.clearUpdateLastTimeUserNews();
        Core.get().NotificationControl().getNotifications(time, time + 86400, 1000);
        LogUtils.logD("GETMATERIAL", "onCreate MainActivity");
        Core.get().UserContentControl().getMaterials();
        Core.get().NewsControl().getUserNews();
        Core.get().NotificationControl().getNotifications(time - 2, time + 2, 5);
        Core.get().VisitsControl().getDashboardVisits();
        Core.get().VisitsControl().getUserVisits();

    }


    @Override
    protected void onLocalizationUpdate() {
        titleTasksList.setText(Core.get().LocalizationControl().getText(R.id.fragment_today_list_tasks_title));
        viewTab1.setTitle(Core.get().LocalizationControl().getText(R.id.today_tab_1));
        viewTab2.setTitle(Core.get().LocalizationControl().getText(R.id.today_tab_2));
        updateTitleNotif();
    }

    private void updateTitleNotif() {
        String msg = Core.get().NotificationControl().getQuantityNotification() + "\n" + Core.get().LocalizationControl().getText(R.id.fragment_today_notification_0);
        Spannable spannableText = new SpannableString(msg);
        spannableText.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.notification_count_color)),
                0, msg.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableText.setSpan(new RelativeSizeSpan(3f), 0, msg.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mNotification_0_tv.setText(spannableText);
    }

    @Override
    public void onEvent(Event event) {
        LogUtils.logD(TAG, "EventId = " + event.getEventId());
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOGOUT:
                calendarVisitBlock.setVisibility(View.GONE);
                mNotificationsLL.setVisibility(View.GONE);
                btnPoints.setTextSize(16);
                btnPoints.setText("Умови участі в програмі лояльності");
                createTaskMaterial();
                break;
            case Event.EVENT_LOAD_DASHBOARD_VISITS:
                swipeRefreshLayout.setRefreshing(false);
                list.clear();
                itemsVisits.clear();
                if (Core.get().VisitsControl().getNearest() != null)
                    for (int i = 0; i < Core.get().VisitsControl().getNearest().length; i++) {
                        if (Core.get().VisitsControl().getNearest()[i].partner != null)
                            itemsVisits.add(new VisitRecyclerItem(Core.get().VisitsControl().getNearest()[i], VisitUtils.getTodayTime()));
                    }
                if (itemsVisits.isEmpty()) {
                    calendarVisitBlock.setVisibility(View.GONE);
                } else {
                    list.itemsAdd(itemsVisits);
                    calendarVisitBlock.setVisibility(View.VISIBLE);
                }
                break;
            case Event.EVENT_LOGIN_SUCCESS:
                if (Core.get().UserContentControl().getListMaterial() == null || Core.get().UserContentControl().getListMaterial().isEmpty())
                    LogUtils.logD("GETMATERIAL", "EVENT_LOGIN_SUCCESS TodayFragment");
                Core.get().UserContentControl().getMaterials();
                break;
            case Event.EVENT_MATERIALS_LOAD:
                swipeRefreshLayout.setRefreshing(false);
                createTaskMaterial();
                break;
            case Event.EVENT_LOAD_NOTIFICATIONS:
                swipeRefreshLayout.setRefreshing(false);
                updateTitleNotif();
                break;
            case Event.EVENT_GET_USER_SUCCESS:
                swipeRefreshLayout.setRefreshing(false);
                if (App.getUser() != null) {
                    if (App.getUser().points_agreement == 0) {
                        btnPoints.setTextSize(16);
                        btnPoints.setText("Умови участі в програмі лояльності");
                    } else {
                        if (App.getUser().points != null) {
                            btnPoints.setTextSize(28);
                            Double points = Double.parseDouble(App.getUser().points);
                            btnPoints.setText(String.valueOf(points.intValue()));
                        }
                    }
                }
                if (App.getUser() != null && App.getUser().first_name != null && !App.getUser().first_name.isEmpty() &&
                        App.getUser().last_name != null && !App.getUser().last_name.isEmpty()) {
                    String acronim = App.getUser().first_name.substring(0, 1) + App.getUser().last_name.substring(0, 1);
                    tvProfile.setText(acronim.toUpperCase());
                }
                break;
            case Event.EVENT_LOAD_5_NOTIFICATIONS:
                swipeRefreshLayout.setRefreshing(false);
                EventLoad_5_nofications eventLoad_5_nofications = (EventLoad_5_nofications) event;
                LogUtils.logD(TAG, "eventLoad_5_nofications.getMembersVisitItems().length = " + eventLoad_5_nofications.getItems().length);
                if (eventLoad_5_nofications.getItems().length == 0) {
                    mNotificationsLL.setVisibility(View.GONE);
                } else {
                    mNotificationsLL.setVisibility(View.VISIBLE);

                    int selectLang = -1;
                    for (int i = 0; i < eventLoad_5_nofications.getItems()[0].notification.translations.length; i++) {
                        if (eventLoad_5_nofications.getItems()[0].notification.translations[i].language.substring(0, 2).equals(App.getLanguage())) {
                            selectLang = i;
                        }
                    }
                    if (selectLang == -1) {
                        for (int i = 0; i < eventLoad_5_nofications.getItems()[0].notification.translations.length; i++) {
                            if (eventLoad_5_nofications.getItems()[0].notification.translations[i].language.substring(0, 2).equals("uk")) {
                                selectLang = i;
                            }
                        }
                    }
                    LogUtils.logD(TAG, "selectLang = " + selectLang);

                    if (eventLoad_5_nofications.getItems().length == 1 &&
                            eventLoad_5_nofications.getItems()[0].time_from < System.currentTimeMillis() / 1000 &&
                            eventLoad_5_nofications.getItems()[0].time_to >= System.currentTimeMillis() / 1000) {
                        mNotification_2.setVisibility(View.GONE);
                        mNotification_3.setVisibility(View.GONE);
                        mNotification_4.setVisibility(View.GONE);
                        mNotification_5.setVisibility(View.GONE);
                        LogUtils.logD("fghhgfhgfh456yfgh", "getItems().length == 1");
                        mNotification_1.setVisibility(View.VISIBLE);
                        mNotification_1_title.setText(eventLoad_5_nofications.getItems()[0].notification.translations[selectLang].title);
                        mNotification_1_description.setText(eventLoad_5_nofications.getItems()[0].notification.translations[selectLang].description);

                    } else {
                        mNotification_2.setVisibility(View.GONE);
                        mNotification_3.setVisibility(View.GONE);
                        mNotification_4.setVisibility(View.GONE);
                        mNotification_5.setVisibility(View.GONE);
                        mNotification_1.setVisibility(View.GONE);
                        mNotificationsLL.setVisibility(View.GONE);
                    }


                    if (eventLoad_5_nofications.getItems().length == 2) {
                        mNotification_3.setVisibility(View.GONE);
                        mNotification_4.setVisibility(View.GONE);
                        mNotification_5.setVisibility(View.GONE);
                        LogUtils.logD("fghhgfhgfh456yfgh", "getItems().length == 2");
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[0], selectLang, mNotification_1, mNotification_1_title, mNotification_1_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[1], selectLang, mNotification_2, mNotification_2_title, mNotification_2_description);
                    }


                    if (eventLoad_5_nofications.getItems().length == 3) {
                        mNotification_4.setVisibility(View.GONE);
                        mNotification_5.setVisibility(View.GONE);
                        LogUtils.logD("fghhgfhgfh456yfgh", "getItems().length == 3");
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[0], selectLang, mNotification_1, mNotification_1_title, mNotification_1_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[1], selectLang, mNotification_2, mNotification_2_title, mNotification_2_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[2], selectLang, mNotification_3, mNotification_3_title, mNotification_3_description);
                    }

                    if (eventLoad_5_nofications.getItems().length == 4) {
                        mNotification_5.setVisibility(View.GONE);
                        LogUtils.logD("fghhgfhgfh456yfgh", "getItems().length == 4");
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[0], selectLang, mNotification_1, mNotification_1_title, mNotification_1_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[1], selectLang, mNotification_2, mNotification_2_title, mNotification_2_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[2], selectLang, mNotification_3, mNotification_3_title, mNotification_3_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[3], selectLang, mNotification_4, mNotification_4_title, mNotification_4_description);
                    }

                    if (eventLoad_5_nofications.getItems().length == 5) {
                        LogUtils.logD("fghhgfhgfh456yfgh", "getItems().length == 5");
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[0], selectLang, mNotification_1, mNotification_1_title, mNotification_1_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[1], selectLang, mNotification_2, mNotification_2_title, mNotification_2_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[2], selectLang, mNotification_3, mNotification_3_title, mNotification_3_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[3], selectLang, mNotification_4, mNotification_4_title, mNotification_4_description);
                        checkVisivilityNotification(eventLoad_5_nofications.getItems()[4], selectLang, mNotification_5, mNotification_5_title, mNotification_5_description);
                    }
                }
                break;
        }
    }

    private void checkVisivilityNotification(Notification item, int selectLang, LinearLayout ll, TextView tvTitle, TextView tvDesription) {
        if (item.time_from < (System.currentTimeMillis() / 1000) &&
                item.time_to >= (System.currentTimeMillis() / 1000)) {
            ll.setVisibility(View.VISIBLE);
            mNotificationsLL.setVisibility(View.VISIBLE);
            tvTitle.setText(item.notification.translations[selectLang].title);
            tvDesription.setText(item.notification.translations[selectLang].description);
        } else {
            ll.setVisibility(View.GONE);
        }
    }

    private void createTaskMaterial() {
        taskMaterials = new ArrayList<>();

        if (Core.get().UserContentControl().getListMaterial() != null)
            for (Material item : Core.get().UserContentControl().getListMaterial()) {
                if (item.videos.length > 0) {
                    for (int i = 0; i < item.videos.length; i++)
                        if (item.videos[i].result_time == null || item.videos[i].result_time.isEmpty()) {

                            int selectLang = -1;
                            for (int j = 0; j < item.videos[i].video.translations.length; j++) {
                                if (item.videos[i].video.translations[j].language.substring(0, 2).equals(App.getLanguage())) {
                                    selectLang = j;
                                }
                            }
                            if (selectLang == -1) {
                                for (int j = 0; j < item.videos[i].video.translations.length; j++) {
                                    if (item.videos[i].video.translations[j].language.substring(0, 2).equals("uk")) {
                                        selectLang = j;
                                    }
                                }
                            }
                            taskMaterials.add(new TaskMaterial(item, item.id, item.videos[i].id, item.videos[i].video.translations[selectLang].title, MaterialsEnum.VIDEO, item.videos[i].time_from));
                        }
                }
                if (item.presentations.length > 0) {
                    for (int i = 0; i < item.presentations.length; i++)
                        if (item.presentations[i].result_time == null || item.presentations[i].result_time.isEmpty()) {

                            int selectLang = -1;
                            for (int j = 0; j < item.presentations[i].presentation.translations.length; j++) {
                                if (item.presentations[i].presentation.translations[j].language.substring(0, 2).equals(App.getLanguage())) {
                                    selectLang = j;
                                }
                            }
                            if (selectLang == -1) {
                                for (int j = 0; j < item.presentations[i].presentation.translations.length; j++) {
                                    if (item.presentations[i].presentation.translations[j].language.substring(0, 2).equals("uk")) {
                                        selectLang = j;
                                    }
                                }
                            }
                            taskMaterials.add(new TaskMaterial(item, item.id, item.presentations[i].id,
                                    item.presentations[i].presentation.translations[selectLang].title, MaterialsEnum.PRESENTATION, item.presentations[i].time_from));
                        }
                }
                if (item.tests.length > 0) {
                    for (int i = 0; i < item.tests.length; i++)
                        if (item.tests[i].test.results == null || item.tests[i].test.results.length == 0) {

                            int selectLang = -1;
                            for (int j = 0; j < item.tests[i].test.translations.length; j++) {
                                if (item.tests[i].test.translations[j].language.substring(0, 2).equals(App.getLanguage())) {
                                    selectLang = j;
                                }
                            }
                            if (selectLang == -1) {
                                for (int j = 0; j < item.tests[i].test.translations.length; j++) {
                                    if (item.tests[i].test.translations[j].language.substring(0, 2).equals("uk")) {
                                        selectLang = j;
                                    }
                                }
                            }

                            taskMaterials.add(new TaskMaterial(item,item.id, item.tests[i].id, item.tests[i].test.translations[selectLang].title, MaterialsEnum.TEST, item.tests[i].time_from));
                        }
                }
            }


        tasksRecyclerView.init();
        Collections.sort(taskMaterials, new Comparator<TaskMaterial>() {
            @Override
            public int compare(TaskMaterial lhs, TaskMaterial rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.timeCreate > rhs.timeCreate ? -1 : (lhs.timeCreate < rhs.timeCreate) ? 1 : 0;
            }
        });

        if (taskMaterials.size() >= 5)
            for (int i = 0; i < 5; i++) {

                tasksRecyclerView.itemAdd(new TaskRecyclerItem(taskMaterials.get(i)));
            }
        else {
            for (int i = 0; i < taskMaterials.size(); i++) {
                tasksRecyclerView.itemAdd(new TaskRecyclerItem(taskMaterials.get(i)));
            }
        }

        titleTasksList.setVisibility(taskMaterials.size() == 0 ? View.GONE : View.VISIBLE);
        taskContainer.setVisibility(taskMaterials.size() == 0 ? View.GONE : View.VISIBLE);
        tasksRecyclerView.setVisibility(taskMaterials.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.logD(TAG, "onResume");
        updateTitleNotif();
        if (App.accessTokenExist()) {
            LogUtils.logD(TAG, "accessTokenExist");
            long time = System.currentTimeMillis() / 1000;
            Core.get().NotificationControl().getNotifications(time - 2, time + 2, 5);
            //Core.get().VisitsControl().getDashboardVisits();
        }
        if (selectedTab != -1) {
            newsTabs.getTabAt(selectedTab).select();
        }
    }

}
