package ua.profarma.medbook.ui.news_and_clinical_cases;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.events.news.EventDrugsLoad;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.drugs.DrugRecyclerItem;
import ua.profarma.medbook.recyclerviews.drugs.DrugsRecyclerView;
import ua.profarma.medbook.types.news.Drug;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;

public class DrugSelectActivity extends MedBookActivity implements IOnSelectDrug {

    private TextView tvTitle;
    private DrugsRecyclerView list;
    private RecyclerItems items;
    private AppCompatEditText edSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drug_select);

        edSearch = findViewById(R.id.activity_drug_select_input);
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    Core.get().NewsControl().getDrugs(edSearch.getText().toString());
                    edSearch.clearFocus();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edSearch.getWindowToken(), 0);

                    handled = true;
                }
                return handled;
            }
        });
        tvTitle = findViewById(R.id.activity_drug_select_toolbar_title);
        list = findViewById(R.id.activity_drug_select_list);
        list.init();
        items = new RecyclerItems();


        ImageView imClose = findViewById(R.id.activity_drug_select_toolbar_close);
        imClose.setOnClickListener(view -> {
            finish();
        });
        onLocalizationUpdate();
        Core.get().NewsControl().getDrugs("");
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_drug_caption));
    }
    //{{host}}/api/v1/drugs?per-page=50000&our=1

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_DRUGS_LOAD:
                EventDrugsLoad eventDrugsLoad = (EventDrugsLoad) event;
                list.clear();
                items.clear();
                if (eventDrugsLoad.getItems() != null && eventDrugsLoad.getItems().length > 0) {
                    for (int i = 0; i < eventDrugsLoad.getItems().length; i++) {
                        items.add(new DrugRecyclerItem(eventDrugsLoad.getItems()[i]));
                    }
                    list.itemsAdd(items);
                }

                break;
        }
    }

    @Override
    public void onSelectDrug(Drug drug) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(AddClinicalCaseActivity.SELECT_DRUG_ID, drug.id);
        resultIntent.putExtra(AddClinicalCaseActivity.SELECT_DRUG_TITLE, drug.title);
        setResult(RESULT_OK, resultIntent);
        finish();
        finish();
    }
}
