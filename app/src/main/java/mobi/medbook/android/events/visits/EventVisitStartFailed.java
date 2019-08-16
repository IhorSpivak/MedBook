package mobi.medbook.android.events.visits;


import mobi.medbook.android.events.core.Event;

public class EventVisitStartFailed extends Event {
    private String message;
    public EventVisitStartFailed(String message){
        super(EVENT_VISIT_STARTING_FAILED);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
