package ua.profarma.medbook.events.news;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.news.Drug;

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
