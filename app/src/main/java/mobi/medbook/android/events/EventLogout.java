package mobi.medbook.android.events;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.utils.LogUtils;

public class EventLogout extends Event {
    public EventLogout() {
        super(EVENT_LOGOUT);
        LogUtils.logD("AppMedbook" ,"EVENT_LOGOUT");
    }
}
