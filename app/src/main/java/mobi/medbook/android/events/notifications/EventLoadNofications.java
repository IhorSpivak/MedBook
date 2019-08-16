package mobi.medbook.android.events.notifications;

import mobi.medbook.android.events.core.Event;

public class EventLoadNofications extends Event {
    public EventLoadNofications(boolean success, String message) {
        super(EVENT_LOAD_NOTIFICATIONS);
    }
}
