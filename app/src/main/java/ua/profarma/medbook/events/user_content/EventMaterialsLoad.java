package ua.profarma.medbook.events.user_content;

import ua.profarma.medbook.events.core.Event;

public class EventMaterialsLoad extends Event {

    private boolean success;
    private String message;

    public EventMaterialsLoad(boolean success, String message) {
        super(EVENT_MATERIALS_LOAD);
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
