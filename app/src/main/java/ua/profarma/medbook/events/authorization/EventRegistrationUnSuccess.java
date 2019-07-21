package ua.profarma.medbook.events.authorization;

import ua.profarma.medbook.events.core.Event;

public class EventRegistrationUnSuccess extends Event {
    private String message;
    public EventRegistrationUnSuccess(String message) {
        super(EVENT_REGISTRATION_UNSUCCESS);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
