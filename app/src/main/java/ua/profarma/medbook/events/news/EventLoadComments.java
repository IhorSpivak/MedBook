package ua.profarma.medbook.events.news;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.news.Comment;

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
