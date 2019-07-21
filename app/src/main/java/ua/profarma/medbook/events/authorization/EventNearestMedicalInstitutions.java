package ua.profarma.medbook.events.authorization;

import java.util.ArrayList;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.MedicalInstitution;

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
