package ua.profarma.medbook.recyclerviews.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ua.profarma.medbook.App;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.news.UserNews;

public class NewsViewHolder extends BaseViewHolder {

    private TextView tvTime;
    private TextView tvTitle;

    private ImageView imv;
    private View rootView;


    public NewsViewHolder(View itemView) {
        super(itemView);
        tvTime = itemView.findViewById(R.id.item_news_time);
        tvTitle = itemView.findViewById(R.id.item_news_title);
        imv = itemView.findViewById(R.id.item_news_image);
        rootView = itemView.findViewById(R.id.item_news_root);
    }

    public void init(NewsRecyclerItem owner, UserNews userNews) {
        rootView.setOnClickListener(owner);
        int selectLang = -1;
        for (int i = 0; i < userNews.newsArticle.translations.length; i++) {
            if (userNews.newsArticle.translations[i].language.substring(0, 2).equals(App.getLanguage())) {
                selectLang = i;
            }
        }
        if (selectLang == -1) {
            for (int i = 0; i < userNews.newsArticle.translations.length; i++) {
                if (userNews.newsArticle.translations[i].language.substring(0, 2).equals("uk")) {
                    selectLang = i;
                }
            }
        }
        if(tvTime != null){
            Date date = new Date(userNews.time_from * 1000L);
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            DateFormat format = new SimpleDateFormat("hh:mm dd MMM yyyy");
            //format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);
            tvTime.setText(formatted);
        }
        if(tvTitle != null){
            tvTitle.setText(userNews.newsArticle.translations[selectLang].title);
        }
        if(imv != null) {
            if (userNews.newsArticle.translations[selectLang].logo != null && !userNews.newsArticle.translations[selectLang].logo.isEmpty())
                Picasso.get().load(userNews.newsArticle.translations[selectLang].logo).into(imv);
            else
                imv.setVisibility(View.GONE);

        }
    }
}
