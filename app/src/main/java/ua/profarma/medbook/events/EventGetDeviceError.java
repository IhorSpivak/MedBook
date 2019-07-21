package ua.profarma.medbook.events;

import ua.profarma.medbook.events.core.Event;

public class EventGetDeviceError extends Event {
    private String message;

    public EventGetDeviceError(String message){
        super(EVENT_ERROR_GET_DEVICE);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
