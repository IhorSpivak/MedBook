package mobi.medbook.android.events.notifications;

import mobi.medbook.android.events.core.Event;



public class EventDeleteNotif extends Event {
    private int id;

    public EventDeleteNotif(int id) {
        super(EVENT_DELETE_NOTIFICATION);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
