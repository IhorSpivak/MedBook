package ua.profarma.medbook.events.notifications;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.notification.Notification;

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
