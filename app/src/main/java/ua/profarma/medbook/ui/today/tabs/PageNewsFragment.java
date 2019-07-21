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
import ua.profarma.medbook.ui.news_and_clinical_cases.AllNewsActivity;

public class PageNewsFragment extends MedBookFragment {

    private NewsRecyclerView list;
    private RecyclerItems items;
    private RecyclerItems items5;
    private Button btnAllNews;

    public static PageNewsFragment newInstance() {
        PageNewsFragment fragment = new PageNewsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        list = rootView.findViewById(R.id.fragment_news_list);
        btnAllNews = rootView.findViewById(R.id.fragment_news_btn_all_news);
        btnAllNews.setOnClickListener(view -> startActivity(new Intent(getActivity(), AllNewsActivity.class)));
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
        btnAllNews.setText(Core.get().LocalizationControl().getText(R.id.fragment_news_btn_all_news));
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
                if (Core.get().NewsControl().getUserNewsList().get(i).news_article_type_id == 1)
                    items.add(new NewsRecyclerItem(Core.get().NewsControl().getUserNewsList().get(i)));
            }
            if (items.size() > 5) {
                btnAllNews.setVisibility(View.VISIBLE);
                items5.clear();
                for (int i = 0; i < 5; i++)
                    items5.add(items.get(i));
                list.itemsAdd(items5);
            } else {
                btnAllNews.setVisibility(View.GONE);
                list.itemsAdd(items);
            }
        } else {
            btnAllNews.setVisibility(View.GONE);
        }
    }
}
