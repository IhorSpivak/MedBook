package mobi.medbook.android.events;

import mobi.medbook.android.events.core.Event;

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
