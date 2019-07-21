package ua.profarma.medbook.events.notifications;

import ua.profarma.medbook.events.core.Event;

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
