package mobi.medbook.android.ui.news_and_clinical_cases;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.news.UserNews;
import mobi.medbook.android.ui.custom_views.MedBookActivity;


import static android.text.Html.fromHtml;

public class NewsMoreActivity extends MedBookActivity {

    //{{host}}/api/v1/news-article-like-unlike/2
    public static String KEY_ID_NEWS = "KEY_ID_NEWS";
    private int idNews = -1;
    private UserNews userNews = null;
    private ImageView imvLike;
    private TextView tvCountLike;
    private TextView tv_date;
    private ImageView imvComments;
    private TextView tvCountComments;
    private LinearLayout rl_comments;
    private LinearLayout rl_like;
    private boolean stateLike = false;
    private int countLike = 0;
    private int selectLang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_more);

        imvLike = findViewById(R.id.activity_news_more_like);
        tvCountLike = findViewById(R.id.activity_news_more_like_count);
        imvComments = findViewById(R.id.activity_news_more_comments);
        tvCountComments = findViewById(R.id.activity_news_more_comments_count);
        rl_comments = findViewById(R.id.rl_comments);
        rl_like = findViewById(R.id.rl_like);
        rl_like.setOnClickListener(view -> Core.get().NewsControl().like_unlike(userNews.news_article_id));

        if (getIntent() == null) {
            finish();
        } else {
            idNews = getIntent().getIntExtra(KEY_ID_NEWS, -1);
        }
        if (idNews == -1) {
            finish();
        } else {
            for (int i = 0; i < Core.get().NewsControl().getUserNewsList().size(); i++) {
                if (Core.get().NewsControl().getUserNewsList().get(i).id == idNews)
                    userNews = Core.get().NewsControl().getUserNewsList().get(i);

            }
        }
        if (userNews == null)
            finish();

        ImageView imvClose = findViewById(R.id.activity_news_more_toolbar_close);
        imvClose.setOnClickListener(view -> finish());


        TextView tvTitle = findViewById(R.id.activity_news_more_toolbar_title);
        TextView tvDescription = findViewById(R.id.activity_news_more_description);
        ImageView imLogo = findViewById(R.id.activity_news_more_image);


        if (userNews.newsArticle.liked == 1) {
            stateLike = true;
            imvLike.setImageResource(R.drawable.ic_like_liked);
        }
        countLike = userNews.newsArticle.like;
        if (countLike > 0)
            tvCountLike.setText(String.valueOf(countLike));

        selectLang = -1;
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

        imvComments.setImageResource(userNews.newsArticle.comments_count > 0 ? R.drawable.ic_comments_exist : R.drawable.ic_comment);
        rl_comments.setOnClickListener(view -> {
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra(CommentsActivity.KEY_ID, userNews.news_article_id);
            if(selectLang != -1)
            intent.putExtra(CommentsActivity.KEY_TITLE, userNews.newsArticle.translations[selectLang].title);
            startActivity(intent);
        });
        tvCountComments.setText(String.valueOf(userNews.newsArticle.comments_count) + " коментарів");


        tvTitle.setText(userNews.newsArticle.translations[selectLang].title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                tvDescription.setText(Html.fromHtml(userNews.newsArticle.translations[selectLang].description,Html.FROM_HTML_MODE_LEGACY));
            } else {
                tvDescription.setText(Html.fromHtml(userNews.newsArticle.translations[selectLang].description));
            }

        String date = convertTime(userNews.created_at);


        tvDescription.setMovementMethod(LinkMovementMethod.getInstance());

        if (userNews.newsArticle.translations[selectLang].logo != null && !userNews.newsArticle.translations[selectLang].logo.isEmpty()) {
            Picasso.get().load(userNews.newsArticle.translations[selectLang].logo).into(imLogo);
        } else imLogo.setVisibility(View.GONE);

    }


    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_LIKE_UNLIKE:
                stateLike = !stateLike;
                imvLike.setImageResource(stateLike ? R.drawable.ic_like_liked : R.drawable.ic_like_unliked);
                if(stateLike) countLike = countLike + 1;
                else countLike = countLike - 1;
                if (countLike > 0)
                    tvCountLike.setText(String.valueOf(countLike));
                Core.get().NewsControl().updateLike(userNews.news_article_id, stateLike, countLike);
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {
    }

    public String convertTime(long time){
        Date date = new Date(time);
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        return format.format(date);
    }
}
