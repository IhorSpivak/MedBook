package ua.profarma.medbook.events;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.utils.LogUtils;

public class EventLogout extends Event {
    public EventLogout() {
        super(EVENT_LOGOUT);
        LogUtils.logD("AppMedbook" ,"EVENT_LOGOUT");
    }
}
