package ua.profarma.medbook.events.visits;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.visits.UserVisitQuestionnaire;

public class EventUserVisitQuestionnaireMPLoad extends Event {
    private UserVisitQuestionnaire mpData;
    public EventUserVisitQuestionnaireMPLoad(UserVisitQuestionnaire item) {
        super(EVENT_USER_VISIT_QUESTIONNAIRE_MP_LOAD);
        mpData = item;
    }

    public UserVisitQuestionnaire getMpData() {
        return mpData;
    }
}
