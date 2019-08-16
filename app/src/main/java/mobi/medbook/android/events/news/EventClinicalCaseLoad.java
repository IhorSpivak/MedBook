package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;

import mobi.medbook.android.types.news.MedicalCaseItem;

public class EventClinicalCaseLoad extends Event {
    private MedicalCaseItem item;
    public EventClinicalCaseLoad(MedicalCaseItem item) {
        super(EVENT_LOAD_CLINIC_CASE);
        this.item = item;
    }

    public MedicalCaseItem getItem() {
        return item;
    }
}
