package mobi.medbook.android.events.authorization;

import java.util.ArrayList;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.MedicalInstitution;

public class EventNearestMedicalInstitutions extends Event {
    private ArrayList<MedicalInstitution> mListOfNearestMedicalInstitute;
    public EventNearestMedicalInstitutions(ArrayList<MedicalInstitution> listOfNearestMedicalInstitute) {
        super(EVENT_NEAREST_MEDICAL_INSTITUTIONS);
        mListOfNearestMedicalInstitute = listOfNearestMedicalInstitute;
    }

    public ArrayList<MedicalInstitution> getmListOfNearestMedicalInstitute() {
        return mListOfNearestMedicalInstitute;
    }
}
