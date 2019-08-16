package mobi.medbook.android.types.responses;

import mobi.medbook.android.api.MResponse;
import mobi.medbook.android.types.visits.UserVisitQuestionnaireDoctor;

public class GetUserVisitQuestionnaireDoctorResponse extends MResponse {
    public UserVisitQuestionnaireDoctor[] items;
}
