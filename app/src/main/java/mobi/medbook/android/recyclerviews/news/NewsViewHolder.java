package mobi.medbook.android.recyclerviews.news;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.medbook.android.App;
import mobi.medbook.android.R;
import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.types.news.UserNews;

public class NewsViewHolder extends BaseViewHolder {

    private TextView tvTime;
    private TextView tvTitle;

    private ImageView imv;
    private View rootView;

    private RequestManager rb;
    private View view;


    public NewsViewHolder(View itemView) {
        super(itemView);
        view = itemView;
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
            DateFormat format = new SimpleDateFormat("dd MMM yyyy");
            //format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);
            tvTime.setText(formatted);
        }
        if(tvTitle != null){
            tvTitle.setText(userNews.newsArticle.translations[selectLang].title);
        }
        if(imv != null) {
            if (userNews.newsArticle.translations[selectLang].logo != null && !userNews.newsArticle.translations[selectLang].logo.isEmpty()){
                rb = Glide.with(view);
            rb.load(userNews.newsArticle.translations[selectLang].logo).into(imv);
//                Picasso.get().load(userNews.newsArticle.translations[selectLang].logo).into(imv);
        } else
                imv.setVisibility(View.GONE);

        }
    }
}
