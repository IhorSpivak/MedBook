package ua.profarma.medbook.events.notifications;

import ua.profarma.medbook.events.core.Event;

public class EventLoadNofications extends Event {
    public EventLoadNofications(boolean success, String message) {
        super(EVENT_LOAD_NOTIFICATIONS);
    }
}
