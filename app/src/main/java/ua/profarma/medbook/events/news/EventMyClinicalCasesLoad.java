package ua.profarma.medbook.events.news;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.news.MedicalCaseItem;

public class EventMyClinicalCasesLoad extends Event {
    private MedicalCaseItem[] items;
    public EventMyClinicalCasesLoad(MedicalCaseItem[] items) {
        super(EVENT_LOAD_MY_CLINIC_CASES);
        this.items = items;
    }

    public MedicalCaseItem[] getItems() {
        return items;
    }
}
