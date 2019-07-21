package ua.profarma.medbook.events.materials;

import ua.profarma.medbook.events.core.Event;

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
