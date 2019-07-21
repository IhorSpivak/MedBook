package ua.profarma.medbook.recyclerviews.news;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.news.UserNews;
import ua.profarma.medbook.types.notification.Notification;
import ua.profarma.medbook.ui.calendar.IOnVisit;
import ua.profarma.medbook.ui.today.tabs.IOnSelectNews;

public class NewsRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private UserNews userNews;

    public NewsRecyclerItem(UserNews userNews) {
        this.userNews = userNews;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof NewsViewHolder) {
            ((NewsViewHolder) holder).init(this, userNews);
        }
    }

    @Override
    public int getViewType() {
        return 1;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null) {
            if (userNews.news_article_type_id == 1)
                getActivity(view.getContext()).onSelectNews(userNews.id);
            else if (userNews.news_article_type_id == 2)
                getActivity(view.getContext()).onSelectCC(userNews.newsArticle.news_clinical_case_id, userNews.news_article_id);
        }
    }

    private IOnSelectNews getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectNews) {
                return (IOnSelectNews) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
