package mobi.medbook.android.events.materials;

import mobi.medbook.android.events.core.Event;


public class EventOnSendResultPresentationFailure extends Event {
    private String message;

    public EventOnSendResultPresentationFailure(String message){
        super(EVENT_MATERIAL_PRESENTATION_RESULT_FAILURE);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
