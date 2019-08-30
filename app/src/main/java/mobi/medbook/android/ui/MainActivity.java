package mobi.medbook.android.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.EventGetDeviceError;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.core.EventListener;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialDescriptionStart;
import mobi.medbook.android.events.materials.EventMaterialInfoFragmentStart;
import mobi.medbook.android.ui.authorization.AuthorizationActivity;
import mobi.medbook.android.ui.calendar.CalendarFragment;
import mobi.medbook.android.ui.calendar.DoctorAncetaActivity;
import mobi.medbook.android.ui.calendar.IOnVisit;
import mobi.medbook.android.ui.calendar.MPAncetaActivity;
import mobi.medbook.android.ui.calendar.StatusVisit;
import mobi.medbook.android.ui.calendar.VisitUtils;
import mobi.medbook.android.ui.calendar.VisitViewerActivity;
import mobi.medbook.android.ui.materials.FragmentDescriptionMaterial;
import mobi.medbook.android.ui.materials.MaterialDetailsFragment;
import mobi.medbook.android.ui.materials.MaterialInfoFragment;
import mobi.medbook.android.ui.materials.MaterialsEnum;
import mobi.medbook.android.ui.materials.MaterialsFragment;
import mobi.medbook.android.ui.news_and_clinical_cases.NewsMoreActivity;
import mobi.medbook.android.ui.news_and_clinical_cases.ViewerMedicalCaseActivity;
import mobi.medbook.android.ui.prescriptions.PrescriptionsFragment;
import mobi.medbook.android.ui.reference.ReferenceListMainFragment;
import mobi.medbook.android.ui.today.TodayFragment;
import mobi.medbook.android.ui.today.tabs.IOnSelectNews;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.DialogBuilder;
import mobi.medbook.android.utils.LogUtils;


public class MainActivity extends AppCompatActivity implements EventListener, IOnVisit, IOnSelectNews {


    private final static String TERMS_AND_AGREEMENTS_FRAGMENT = "MainActivity.fragment.terms_and_agreements";
    private final static String MATERIAL_DETAILS_FRAGMENT = "MainActivity.fragment.material";
    private final static String MATERIAL_INFO_FRAGMENT = "MainActivity.fragment.material.info";
    private final static String MATERIAL_DESCRIPTION_FRAGMENT = "MainActivity.fragment.material_description";

    private LinearLayout llAppNavBarRoot;
    private LinearLayout llToday;
    private LinearLayout llCalendar;
    private LinearLayout llMaterials;
    private LinearLayout llReference;
    //private LinearLayout llPrescriptions;
    private int fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setColorStatusBar("today");

        setContentView(R.layout.activity_main);

        fragmentId = R.id.navigation_app_bar_today;
        FragmentTransaction transaction0 = getSupportFragmentManager().beginTransaction();
        transaction0.replace(R.id.activity_main_container, TodayFragment.newInstance());
        transaction0.commit();

        if (!App.accessTokenExist()) {
            LogUtils.logD("AuthorizationControl", "MainActivity !accessTokenExist startActivity AuthorizationActivity");
            startActivity(new Intent(this, AuthorizationActivity.class));
            App.startAuth();
        } else {
            Core.get().UserControl().getUserContext(this);
            long time = System.currentTimeMillis() / 1000;
            Core.get().NotificationControl().getNotifications(time, time + 86400, 1000);
            LogUtils.logD("GETMATERIAL", "onCreate MainActivity");
            Core.get().UserContentControl().getMaterials();
            Core.get().NewsControl().getUserNews();
            Core.get().VisitsControl().getUserVisits();
            Core.get().VisitsControl().getDashboardVisits();
        }

        llAppNavBarRoot = findViewById(R.id.navigation_app_bar_layout);
        llToday = findViewById(R.id.navigation_app_bar_today);
        llCalendar = findViewById(R.id.navigation_app_bar_calendar);
        llMaterials = findViewById(R.id.navigation_app_bar_materials);
        llReference= findViewById(R.id.navigation_app_bar_reference);
        //llPrescriptions = findViewById(R.id.navigation_app_bar_prescriptions);

        llToday.setOnClickListener(onClickAppNavBarListener);
        llToday.setSelected(true);
        fragmentId = R.id.navigation_app_bar_today;
        llCalendar.setOnClickListener(onClickAppNavBarListener);
        llMaterials.setOnClickListener(onClickAppNavBarListener);
        llReference.setOnClickListener(onClickAppNavBarListener);
        //llPrescriptions.setOnClickListener(onClickAppNavBarListener);

        if (!App.ismTermAgreements() && App.accessTokenExist()) {
            initTermAgreements();
        }

        EventRouter.register(this);

    }

    private void setColorStatusBar(String flag) {
        Window window = getWindow();
        if(flag.equals("today")){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        if(flag.equals("materials")){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().getDecorView().setSystemUiVisibility(0);
        }
        if(flag.equals("calendar")){

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            getWindow().getDecorView().setSystemUiVisibility(0);


        }

    }

    View.OnClickListener onClickAppNavBarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) {
                return;
            }

            for (int i = 0; i < llAppNavBarRoot.getChildCount(); i++) {
                llAppNavBarRoot.getChildAt(i).setSelected(false);
            }

            v.setSelected(true);

            if (fragmentId != v.getId()) {
                FragmentManager fm = getSupportFragmentManager();
                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                switch (v.getId()) {
                    case R.id.navigation_app_bar_today:
                        Core.get().UserContentControl().setSelectedMaterial(null);
                        fragmentId = R.id.navigation_app_bar_today;
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.activity_main_container, TodayFragment.newInstance());
                        transaction1.commit();
                        long time = System.currentTimeMillis() / 1000;
                        Core.get().NotificationControl().getNotifications(time, time + 86400, 1000);
                        Core.get().UserContentControl().getMaterials();
                        Core.get().NewsControl().getUserNews();
                        Core.get().VisitsControl().getDashboardVisits();
                        setColorStatusBar("today");
                        break;
                    case R.id.navigation_app_bar_calendar:
                        fragmentId = R.id.navigation_app_bar_calendar;
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.activity_main_container, CalendarFragment.newInstance());
                        transaction2.commit();
                        setColorStatusBar("calendar");
                        break;
                    case R.id.navigation_app_bar_materials:
                        fragmentId = R.id.navigation_app_bar_materials;
                        FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.activity_main_container, MaterialsFragment.newInstance());
                        LogUtils.logD("GETMATERIAL", "case R.id.navigation_app_bar_materials:");
                        Core.get().UserContentControl().getMaterials();
                        transaction3.commit();
                        setColorStatusBar("materials");
                        break;
                    case R.id.navigation_app_bar_prescriptions:
                        fragmentId = R.id.navigation_app_bar_prescriptions;
                        FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.activity_main_container, PrescriptionsFragment.newInstance());
                        transaction4.commit();
                        break;
                        case R.id.navigation_app_bar_reference:
                            fragmentId = R.id.navigation_app_bar_reference;
                            setColorStatusBar("materials");
                            FragmentTransaction transaction5 = getSupportFragmentManager().beginTransaction();
                            transaction5.replace(R.id.activity_main_container, ReferenceListMainFragment.newInstance());
                            transaction5.commit();
                            setColorStatusBar("materials");
                        break;
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        EventRouter.unregister(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onEvent(Event event) {
        switch (event.getEventId()) {
            case Event.EVENT_LOGIN_SUCCESS:
                initTermAgreements();
                break;
            case Event.EVENT_GET_USER_SUCCESS:
                break;
            case Event.EVENT_TERMS_AND_AGREEMENTS_OK:
                App.setTermAgreements();
                llToday.setSelected(true);
                llToday.setEnabled(true);
                llCalendar.setEnabled(true);
                llMaterials.setEnabled(true);
                //llPrescriptions.setEnabled(true);
                break;
            case Event.EVENT_LOGOUT:
                LogUtils.logD("AuthorizationControl", "MainActivity EVENT_LOGOUT");
                if (!App.isAuthStart()) {
                    Intent intent = new Intent(this, AuthorizationActivity.class);
                    startActivity(intent);
                    App.startAuth();
                }
                break;
            case Event.EVENT_ERROR_GET_DEVICE:
                EventGetDeviceError eventGetDeviceError = (EventGetDeviceError) event;
                AppUtils.toastError(eventGetDeviceError.getMessage(), false);
                break;
            case Event.EVENT_MATERIAL_DETAILS_FRAGMENT_START:
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.addToBackStack(MATERIAL_DETAILS_FRAGMENT);
                transaction2.replace(R.id.activity_main_container, MaterialDetailsFragment.newInstance(), MATERIAL_DETAILS_FRAGMENT);
                transaction2.commit();
                break;
            case Event.EVENT_MATERIAL_INFO_FRAGMENT_START:
                EventMaterialInfoFragmentStart eventMaterialInfoFragmentStart = (EventMaterialInfoFragmentStart) event;
                FragmentTransaction transaction4 = getSupportFragmentManager().beginTransaction();
                transaction4.addToBackStack(MATERIAL_INFO_FRAGMENT);
                transaction4.replace(R.id.activity_main_container, MaterialInfoFragment.newInstance(), MATERIAL_INFO_FRAGMENT);
                transaction4.commit();
                break;
            case Event.EVENT_MATERIAL_FRAGMENT_CLOSE:
                //fucking hack for close Fragment from other Activity with EventBus
                //call super.onActivityResult first before your logic and the issue will get fixed as FragmentActivity's
                // onActivityResult calls mFragments.noteStateNotSaved();
                super.onActivityResult(-1, -1, null);
                getSupportFragmentManager().popBackStackImmediate(MATERIAL_DETAILS_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case Event.EVENT_MATERIAL_DESCRIPTION_FRAGMENT_START:
                FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                transaction3.addToBackStack(MATERIAL_DESCRIPTION_FRAGMENT);
                EventMaterialDescriptionStart eventMaterialDescriptionStart = (EventMaterialDescriptionStart) event;
                MaterialsEnum type = eventMaterialDescriptionStart.getType();
                int idType = eventMaterialDescriptionStart.getIdType();
                transaction3.replace(R.id.activity_main_container, FragmentDescriptionMaterial.newInstance(type, idType), MATERIAL_DESCRIPTION_FRAGMENT);
                transaction3.commit();
                break;
            case Event.EVENT_MATERIAL_DESCRIPTION_FRAGMENT_CLOSE:
                super.onActivityResult(-1, -1, null);
                getSupportFragmentManager().popBackStackImmediate(MATERIAL_DESCRIPTION_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
        }
    }

    private void initTermAgreements() {
        llToday.setEnabled(false);
        llCalendar.setEnabled(false);
        llMaterials.setEnabled(false);
        //llPrescriptions.setEnabled(false);
        llToday.setSelected(false);
        llCalendar.setSelected(false);
        llMaterials.setSelected(false);
        //llPrescriptions.setSelected(false);
//        super.onActivityResult(-1, -1, null);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.activity_main_container, FragmentTermsAndAgreements.newInstance(), TERMS_AND_AGREEMENTS_FRAGMENT);
//        transaction.commit();
        Intent intent = new Intent(this, TermAndPointAgreementsActivity.class);
        intent.putExtra(TermAndPointAgreementsActivity.KEY_TYPE, 1);
        startActivity(intent);
    }

    @Override
    public void onSelectVisit(int idVisit) {
        if (Core.get().VisitsControl().getUserVisitForId(idVisit) != null) {
            if (VisitUtils.getStatus(Core.get().VisitsControl().getUserVisitForId(idVisit), VisitUtils.getTodayTime()) == StatusVisit.STARTED)
                if (isMedPred()) {
                    Intent intentMedPred = new Intent(MainActivity.this, MPAncetaActivity.class);
                    intentMedPred.putExtra(MPAncetaActivity.KEY_ID_VISIT, idVisit);
                    startActivity(intentMedPred);
                } else {
                    Intent intentDoc = new Intent(MainActivity.this, DoctorAncetaActivity.class);
                    intentDoc.putExtra(DoctorAncetaActivity.KEY_ID_VISIT, idVisit);
                    startActivity(intentDoc);
                }
            else {
                Intent intent = new Intent(this, VisitViewerActivity.class);
                intent.putExtra(VisitViewerActivity.KEY_ID_VISIT, idVisit);
                startActivity(intent);
            }
        } else {
            DialogBuilder.showInfoDialog(this, "Повідомлення", "Дані про зустрічі ще заватажуються, спробуйте пізніше");
        }
    }

    private boolean isMedPred() {
        return !(App.getUser().specialization == null || App.getUser().specialization.is_medpred == null || App.getUser().specialization.is_medpred == 0);
    }

    @Override
    public void onSelectNews(int id) {
        Intent intent = new Intent(this, NewsMoreActivity.class);
        intent.putExtra(NewsMoreActivity.KEY_ID_NEWS, id);
        startActivity(intent);
    }

    @Override
    public void onSelectCC(Integer news_clinical_case_id, Integer news_article_id) {
        LogUtils.logD("vgnhvygjbhn", "onSelectCC, news_clinical_case_id = " + news_clinical_case_id);
        Intent intent = new Intent(this, ViewerMedicalCaseActivity.class);
        intent.putExtra(ViewerMedicalCaseActivity.KEY_ID_CC, news_clinical_case_id);
        intent.putExtra(ViewerMedicalCaseActivity.KEY_NA_ID_CC, news_article_id);
        intent.putExtra(ViewerMedicalCaseActivity.KEY_MY_CC, false);
        startActivity(intent);
    }
}
