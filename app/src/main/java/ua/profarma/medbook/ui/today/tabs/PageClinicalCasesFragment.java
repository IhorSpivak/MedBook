package ua.profarma.medbook.ui.today.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.base.SimpleDividerItemDecoration;
import ua.profarma.medbook.recyclerviews.news.NewsRecyclerItem;
import ua.profarma.medbook.recyclerviews.news.NewsRecyclerView;
import ua.profarma.medbook.ui.custom_views.MedBookFragment;
import ua.profarma.medbook.ui.news_and_clinical_cases.AllClinicalCasesActivity;
import ua.profarma.medbook.ui.news_and_clinical_cases.MyClinicalCasesActivity;

public class PageClinicalCasesFragment extends MedBookFragment {

    private NewsRecyclerView list;
    private RecyclerItems items;
    private RecyclerItems items5;
    private Button btnAllMedicalCases;
    private Button btnMyMedicalCases;

    public static PageClinicalCasesFragment newInstance() {
        PageClinicalCasesFragment fragment = new PageClinicalCasesFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_clinical_cases, container, false);

        list = rootView.findViewById(R.id.fragment_clinical_cases_list);
        btnAllMedicalCases = rootView.findViewById(R.id.fragment_clinical_cases_btn_all);
        btnMyMedicalCases = rootView.findViewById(R.id.fragment_clinical_cases_btn_my);

        btnAllMedicalCases.setOnClickListener(view -> startActivity(new Intent(getActivity(), AllClinicalCasesActivity.class)));
        btnMyMedicalCases.setOnClickListener(view -> startActivity(new Intent(getActivity(), MyClinicalCasesActivity.class)));
        list.init();
        items = new RecyclerItems();
        items5 = new RecyclerItems();
        list.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        initialList();
        onLocalizationUpdate();
        return rootView;
    }

    @Override
    protected void onLocalizationUpdate() {
        btnAllMedicalCases.setText(Core.get().LocalizationControl().getText(R.id.fragment_clinical_cases_btn_all));
        btnMyMedicalCases.setText(Core.get().LocalizationControl().getText(R.id.fragment_clinical_cases_btn_my));
    }
    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        if (event.getEventId() == Event.EVENT_NEWS_LOAD) {
            list.clear();
            items.clear();
            items5.clear();
            initialList();
        }
    }

    private void initialList(){
        if (Core.get().NewsControl().getUserNewsList() != null && !Core.get().NewsControl().getUserNewsList().isEmpty()) {
            for (int i = 0; i < Core.get().NewsControl().getUserNewsList().size(); i++) {
                if (Core.get().NewsControl().getUserNewsList().get(i).news_article_type_id == 2)
                    items.add(new NewsRecyclerItem(Core.get().NewsControl().getUserNewsList().get(i)));
            }
            if (items.size() > 5) {
                btnAllMedicalCases.setVisibility(View.VISIBLE);
                items5.clear();
                for (int i = 0; i < 5; i++)
                    items5.add(items.get(i));
                list.itemsAdd(items5);
            } else {
                btnAllMedicalCases.setVisibility(View.GONE);
                list.itemsAdd(items);
            }
        } else {
            btnAllMedicalCases.setVisibility(View.GONE);
        }
    }
}
