package ua.profarma.medbook.ui.news_and_clinical_cases;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.news.EventMyClinicalCasesLoad;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.base.SimpleDividerItemDecoration;
import ua.profarma.medbook.recyclerviews.clinical_case.ClinicalCaseRecyclerItem;
import ua.profarma.medbook.recyclerviews.clinical_case.ClinicalCasesRecyclerView;
import ua.profarma.medbook.ui.TermAndPointAgreementsActivity;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.utils.LogUtils;

public class MyClinicalCasesActivity extends MedBookActivity implements IOnSelectCCForView {

    private ClinicalCasesRecyclerView list;
    private RecyclerItems items;
    private TextView tvTitle;
    private int idCC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_clinical_cases);
        tvTitle = findViewById(R.id.activity_my_clinical_cases_toolbar_title);
        ImageView imClose = findViewById(R.id.activity_my_clinical_cases_toolbar_close);
        imClose.setOnClickListener(view -> finish());

        list = findViewById(R.id.activity_my_clinical_cases_list);
        list.init();
        list.addItemDecoration(new SimpleDividerItemDecoration(this));
        items = new RecyclerItems();
//        list.addItemDecoration(new SimpleDividerItemDecoration(this));
//        if (Core.get().NewsControl().getUserNewsList() != null && !Core.get().NewsControl().getUserNewsList().isEmpty()) {
//            for (int i = 0; i < Core.get().NewsControl().getUserNewsList().size(); i++) {
//                if (Core.get().NewsControl().getUserNewsList().get(i).news_article_type_id == 1)
//                    items.add(new ClinicalCaseRecyclerItem(Core.get().NewsControl().getUserNewsList().get(i)));
//            }
//            list.itemsAdd(items);
//        }
        ImageView imAddClinicalCase = findViewById(R.id.activity_my_clinical_cases_toolbar_add);
        imAddClinicalCase.setOnClickListener(view ->{
            Intent intent;
            intent = new Intent(this, TermAndPointAgreementsActivity.class);
            intent.putExtra(TermAndPointAgreementsActivity.KEY_TYPE, 3);
            idCC = -1;
            startActivity(intent);
        });
        Core.get().NewsControl().getMyMedicalCases();
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_my_clinical_cases_toolbar_title));
    }

//    @Override
//    public void onSelectNews(int id) {
//        Intent intent = new Intent(this, NewsMoreActivity.class);
//        intent.putExtra(NewsMoreActivity.KEY_ID_NEWS, id);
//        startActivity(intent);
//    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LOAD_MY_CLINIC_CASES:
                items.clear();
                list.clear();
                EventMyClinicalCasesLoad eventClinicalCaseLoad = (EventMyClinicalCasesLoad) event;
                for (int i = 0; i < eventClinicalCaseLoad.getItems().length; i++) {
                    items.add(new ClinicalCaseRecyclerItem(eventClinicalCaseLoad.getItems()[i]));
                }
                list.itemsAdd(items);
                break;
            case Event.EVENT_POINTS_AGREEMENTS_OK:
                //remarked and new
                Intent intent;
                intent = new Intent(MyClinicalCasesActivity.this, AddClinicalCaseActivity.class);
                intent.putExtra(AddClinicalCaseActivity.KEY_ID_CC, idCC);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onSecelectCC(int id, int typeMedicalCaseStatus) {
        Intent intent;
        //review and publish
        if (typeMedicalCaseStatus == 2 || typeMedicalCaseStatus == 4) {
            intent = new Intent(MyClinicalCasesActivity.this, ViewerMedicalCaseActivity.class);
            intent.putExtra(ViewerMedicalCaseActivity.KEY_ID_CC, id);
            intent.putExtra(ViewerMedicalCaseActivity.KEY_MY_CC, true);
        } else {
            //remarked and new
            //intent = new Intent(MyClinicalCasesActivity.this, AddClinicalCaseActivity.class);
            //intent.putExtra(AddClinicalCaseActivity.KEY_ID_CC, id);
            intent = new Intent(this, TermAndPointAgreementsActivity.class);
            intent.putExtra(TermAndPointAgreementsActivity.KEY_TYPE, 3);
            idCC = id;
        }
        startActivity(intent);
    }
}
