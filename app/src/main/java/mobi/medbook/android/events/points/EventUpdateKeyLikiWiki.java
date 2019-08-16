package mobi.medbook.android.events.points;

import mobi.medbook.android.events.core.Event;

public class EventUpdateKeyLikiWiki extends Event {
    private boolean status;
    private String user_message;
    public EventUpdateKeyLikiWiki(boolean status, String user_message) {
        super(EVENT_UPDATE_KEY_LIKI_WIKI);
        this.status  =status;
        this.user_message = user_message;
    }

    public String getUser_message() {
        return user_message;
    }

    public boolean isStatus() {
        return status;
    }
}
