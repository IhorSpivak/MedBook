package mobi.medbook.android.ui.news_and_clinical_cases;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.news.EventAddIcod;
import mobi.medbook.android.events.news.EventGetIcods;
import mobi.medbook.android.events.news.EventRemoveIcod;
import mobi.medbook.android.events.news.EventUpdateIcod;
import mobi.medbook.android.types.news.Icod;
import mobi.medbook.android.ui.custom_views.MedBookActivity;
import mobi.medbook.android.ui.news_and_clinical_cases.icods_viewer.ItemIcod;


public class IcodSelectActivity extends MedBookActivity {

    private TextView tvTitle;
    private ProgressBar progressBar;
    private LinearLayout root;
    private Button btn_accept;

    private ArrayList<ItemIcod> itemsThree;
    private ArrayList<ItemIcod> downLevelList;

    private ArrayList<Icod> selected;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icod_select);
        progressBar = findViewById(R.id.activity_icod_select_progress);
        btn_accept = findViewById(R.id.btn_accept);
        progressBar.setVisibility(View.VISIBLE);
        ImageView imClose = findViewById(R.id.activity_icod_select_toolbar_close);
        imClose.setOnClickListener(view ->  onAcceptChose());
        btn_accept.setOnClickListener(view ->  onAcceptChose());

        tvTitle = findViewById(R.id.activity_icod_select_toolbar_title);

        root = findViewById(R.id.activity_icod_select_list);
        root.setEnabled(false);

        itemsThree = new ArrayList<>();
        downLevelList = new ArrayList<>();
        selected = new ArrayList<>();

        Core.get().NewsControl().getIcodLevelRoot();

        onLocalizationUpdate();
    }

    public void onAcceptChose(){
        Intent resultIntent = new Intent();
        int id[] = new int[selected.size()];
        String code[] = new String[selected.size()];
        String title[] = new String[selected.size()];
        for(int i = 0; i < selected.size(); i++){
            id[i] = selected.get(i).id;
            code[i] = selected.get(i).code_icod;
            int selectLang = -1;
            for (int j = 0; j < selected.get(i).translations.length; j++) {
                if (selected.get(i).translations[j].language.substring(0, 2).equals(App.getLanguage())) {
                    selectLang = j;
                }
            }
            if (selectLang == -1) {
                for (int j = 0; j < selected.get(i).translations.length; j++) {
                    if (selected.get(i).translations[j].language.substring(0, 2).equals("uk")) {
                        selectLang = j;
                    }
                }
            }
            title[i] = selected.get(i).translations[selectLang].title;
        }
        resultIntent.putExtra(AddClinicalCaseActivity.SELECT_ICOD_ID, id);
        resultIntent.putExtra(AddClinicalCaseActivity.SELECT_ICOD_CODE, code);
        resultIntent.putExtra(AddClinicalCaseActivity.SELECT_ICOD_TITLE, title);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_add_clinical_cases_add_icod_caption));
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {

            case Event.EVENT_START_PROGRESS:
                progressBar.setVisibility(View.VISIBLE);
                root.setEnabled(false);
                break;

            case Event.EVENT_ADD_ICOD:
                EventAddIcod eventAddIcod = (EventAddIcod) event;
                boolean add = true;
                for (Icod item : selected) {
                    if (item.id == eventAddIcod.getIcod().id) add = false;
                }
                if (add) {
                    selected.add(eventAddIcod.getIcod());
                }
                break;

            case Event.EVENT_REMOVE_ICOD:
                EventRemoveIcod eventRemoveIcod = (EventRemoveIcod) event;
                ArrayList<Icod> temp = new ArrayList<>();
                temp.addAll(selected);
                for (Icod item : temp) {
                    if (item.id == eventRemoveIcod.getIcod().id) selected.remove(item);
                }
                break;

            case Event.EVENT_UPDATE_STATE_EXPANDABLE_LIST:
                int id = -1;
                for (int i = 0; i < itemsThree.size(); i++) {
                    if (itemsThree.get(i).getId() == ((EventUpdateIcod) event).getId())
                        id = i;
                }
                if (id != -1) {
                    while (itemsThree.size() > id)
                        itemsThree.remove(itemsThree.size() - 1);
                }
                Core.get().NewsControl().getIcod(itemsThree.size(), itemsThree.get(itemsThree.size() - 1).getId());
                break;
            case Event.EVENT_ICOD_LOAD:
                EventGetIcods eventGetIcods = (EventGetIcods) event;

                if (eventGetIcods.getLevel() == 0) {
                    root.removeAllViews();
                    for (int i = 0; i < eventGetIcods.getItems().length; i++) {
                        ItemIcod itemIcod = (ItemIcod) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_expand_icod, null, true);
                        itemIcod.setItem(eventGetIcods.getItems()[i], eventGetIcods.getLevel());
                        downLevelList.add(itemIcod);
                        root.addView(itemIcod);
                        itemsThree.clear();
                    }
                } else {
                    if (eventGetIcods.getItems().length > 0) {
                        for (int i = 0; i < downLevelList.size(); i++) {
                            if (downLevelList.get(i).getId() == eventGetIcods.getOwner_id()) {
                                ItemIcod item = downLevelList.get(i);
                                item.setExpand(true);
                                itemsThree.add(item);
                            }
                        }
                        downLevelList.clear();
                        for (int i = 0; i < eventGetIcods.getItems().length; i++) {
                            ItemIcod itemIcod = (ItemIcod) LayoutInflater.from(getBaseContext()).inflate(R.layout.item_expand_icod, null, true);
                            itemIcod.setItem(eventGetIcods.getItems()[i], eventGetIcods.getLevel());
                            downLevelList.add(itemIcod);
                        }
                        root.removeAllViews();
                        for (int i = 0; i < itemsThree.size(); i++) {
                            root.addView(itemsThree.get(i));
                        }
                        for (int i = 0; i < downLevelList.size(); i++) {
                            root.addView(downLevelList.get(i));
                        }
                    } else {
                        for (int i = 0; i < downLevelList.size(); i++) {
                            if (downLevelList.get(i).getId() == eventGetIcods.getOwner_id()) {
                                ItemIcod item = downLevelList.get(i);
                                item.setCheckable(true);
                                item.setChecked(true);
                            }
                        }
                    }
                }
                progressBar.setVisibility(View.GONE);
                root.setEnabled(true);
                break;
        }
    }
}
