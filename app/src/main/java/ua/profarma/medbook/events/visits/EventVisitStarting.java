package ua.profarma.medbook.events.visits;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.utils.LogUtils;

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
