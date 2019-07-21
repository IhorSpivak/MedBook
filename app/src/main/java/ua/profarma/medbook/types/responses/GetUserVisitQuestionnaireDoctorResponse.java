package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.visits.UserVisitQuestionnaireDoctor;

public class GetUserVisitQuestionnaireDoctorResponse extends MResponse {
    public UserVisitQuestionnaireDoctor[] items;
}
