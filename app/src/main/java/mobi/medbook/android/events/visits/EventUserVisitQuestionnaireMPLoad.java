package mobi.medbook.android.events.visits;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.visits.UserVisitQuestionnaire;

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
