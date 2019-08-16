package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.news.Comment;

public class EventLoadComments extends Event {

    private Comment[] comments;
    public EventLoadComments(Comment[] comments) {
        super(EVENT_LOAD_COMMENTS);
        this.comments = new Comment[comments.length];
        for (int i = 0; i < this.comments.length; i++){
            this.comments[i] = comments[i];
        }
    }

    public Comment[] getComments() {
        return comments;
    }
}
