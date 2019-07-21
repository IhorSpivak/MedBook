package ua.profarma.medbook.events.news;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.news.MedicalCaseItem;

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
