package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.news.MedicalCaseItem;

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
