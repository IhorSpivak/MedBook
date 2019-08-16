package mobi.medbook.android.recyclerviews.news;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.news.UserNews;
import mobi.medbook.android.ui.today.tabs.IOnSelectNews;


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
