package mobi.medbook.android.ui.news_and_clinical_cases;

public interface IOnClicksComment {
    public void onSelectReplyComment(Integer selectReply, Integer position, String name);
    public void onDeleteComment(Integer id, Integer position);
}
