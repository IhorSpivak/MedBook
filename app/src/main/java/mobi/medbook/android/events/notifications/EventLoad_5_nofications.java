package mobi.medbook.android.events.notifications;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.notification.Notification;

public class EventLoad_5_nofications extends Event {
    private Notification[] items;
    private String message;

    public EventLoad_5_nofications(Notification[] items, String message) {
        super(EVENT_LOAD_5_NOTIFICATIONS);
        this.items = items;
        this.message  =message;
    }

    public String getMessage() {
        return message;
    }

    public Notification[] getItems() {
        return items;
    }
}
