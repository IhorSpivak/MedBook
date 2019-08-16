package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;

import mobi.medbook.android.types.news.Drug;

public class EventDrugsLoad extends Event {
    private Drug[] items;
    public EventDrugsLoad(Drug[] items){
        super(EVENT_DRUGS_LOAD);
        this.items = items;
    }

    public Drug[] getItems() {
        return items;
    }
}
