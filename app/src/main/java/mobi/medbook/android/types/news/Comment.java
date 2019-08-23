package mobi.medbook.android.types.news;

public class Comment implements Comparable<Comment>{

    public Integer id;
    public String entity;
    public Integer entityId;
    public String content;
    public Integer parentId;
    public Integer level;
    public Integer created_by;
    public Integer updated_by;
    public String relatedTo;
    public String url;
    public Integer status;
    public Integer created_at;
    public String owner_firstname;
    public String owner_middlename;
    public Integer position;
    public Integer childSize = 0;

    @Override
    public int compareTo(Comment o) {
        return o.created_at.compareTo(created_at);
    }
}
