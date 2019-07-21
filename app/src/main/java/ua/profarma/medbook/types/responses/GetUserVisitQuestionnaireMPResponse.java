package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.visits.UserVisitQuestionnaire;

public class GetUserVisitQuestionnaireMPResponse extends MResponse {
    public UserVisitQuestionnaire[] items;

}
