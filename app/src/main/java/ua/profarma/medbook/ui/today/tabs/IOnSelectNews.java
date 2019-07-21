package ua.profarma.medbook.ui.today.tabs;

public interface IOnSelectNews {
    public void onSelectNews(int id);
    public void onSelectCC(Integer news_clinical_case_id, Integer news_article_id);
}
