package ua.profarma.medbook.types.news;

public class NewsArticle {

    public int id;
    public Integer news_article_type_id;
    public Integer news_clinical_case_id;
    public Long time_from;
    public Long time_to;

    public NewsArticleType newsArticleType;
    public NewsTranslation translations[];

    public int like;
    public int liked;
    public int comments_count;
    public Comment[] comments;
}
