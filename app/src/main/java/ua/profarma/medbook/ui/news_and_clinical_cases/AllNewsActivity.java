package ua.profarma.medbook.ui.news_and_clinical_cases;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.RecyclerItems;
import ua.profarma.medbook.recyclerviews.base.SimpleDividerItemDecoration;
import ua.profarma.medbook.recyclerviews.news.NewsRecyclerItem;
import ua.profarma.medbook.recyclerviews.news.NewsRecyclerView;
import ua.profarma.medbook.ui.custom_views.MedBookActivity;
import ua.profarma.medbook.ui.today.tabs.IOnSelectNews;

public class AllNewsActivity extends MedBookActivity implements IOnSelectNews {

    private NewsRecyclerView list;
    private RecyclerItems items;
    private TextView tvTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);
        tvTitle = findViewById(R.id.activity_all_news_toolbar_title);
        ImageView imClose = findViewById(R.id.activity_all_news_toolbar_close);
        imClose.setOnClickListener(view -> finish());

        list = findViewById(R.id.activity_all_news_list);
        list.init();
        items = new RecyclerItems();
        list.addItemDecoration(new SimpleDividerItemDecoration(this));
        if (Core.get().NewsControl().getUserNewsList() != null && !Core.get().NewsControl().getUserNewsList().isEmpty()) {
            for (int i = 0; i < Core.get().NewsControl().getUserNewsList().size(); i++) {
                if (Core.get().NewsControl().getUserNewsList().get(i).news_article_type_id == 1)
                    items.add(new NewsRecyclerItem(Core.get().NewsControl().getUserNewsList().get(i)));
            }
            list.itemsAdd(items);
        }
        onLocalizationUpdate();
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_all_news_toolbar_title));
    }

    @Override
    public void onSelectNews(int id) {
        Intent intent = new Intent(this, NewsMoreActivity.class);
        intent.putExtra(NewsMoreActivity.KEY_ID_NEWS, id);
        startActivity(intent);
    }

    @Override
    public void onSelectCC(Integer news_clinical_case_id, Integer news_article_id) {}
}
