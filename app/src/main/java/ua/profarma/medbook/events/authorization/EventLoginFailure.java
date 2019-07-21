package ua.profarma.medbook.events.authorization;

import ua.profarma.medbook.events.core.Event;

public class EventLoginFailure extends Event {
    public EventLoginFailure() {
        super(EVENT_LOGIN_FAILURE);
    }
}
