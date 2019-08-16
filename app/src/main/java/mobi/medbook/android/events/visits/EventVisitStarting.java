package mobi.medbook.android.events.visits;

import mobi.medbook.android.events.core.Event;


public class EventVisitStarting extends Event {

    private Long started_at;
    public EventVisitStarting(Long started_at){
        super(EVENT_VISIT_STARTING);
        this.started_at = started_at;
    }

    public Long getStarted_at() {
        return started_at;
    }
}
