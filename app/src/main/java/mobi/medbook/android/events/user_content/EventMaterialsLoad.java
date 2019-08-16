package mobi.medbook.android.events.user_content;

import mobi.medbook.android.events.core.Event;

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
