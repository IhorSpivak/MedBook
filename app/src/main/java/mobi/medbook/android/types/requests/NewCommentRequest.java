package mobi.medbook.android.types.requests;

public class NewCommentRequest {
    	public String entity = "newsArticle";
        public Integer entityId;
        public String content;
        public int status = 1;
        public Integer parentId;
}
