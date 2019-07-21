package ua.profarma.medbook.events.visits;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.visits.UserDashboardVisit;
import ua.profarma.medbook.types.visits.UserVisit;
import ua.profarma.medbook.utils.LogUtils;

public class EventLoadDashBoardVisit extends Event {

    public EventLoadDashBoardVisit() {
        super(EVENT_LOAD_DASHBOARD_VISITS);
    }

}
