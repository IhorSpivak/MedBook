package ua.profarma.medbook.events.authorization;

import ua.profarma.medbook.events.core.Event;

public class EventSelectedMedicalInstitution extends Event {
    private String mTitle;
    private int mId;

    public EventSelectedMedicalInstitution(String title, int id) {
        super(Event.EVENT_SELECTED_MEDICAL_INSTITUTION);
        mTitle = title;
        mId = id;
    }

    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }
}
