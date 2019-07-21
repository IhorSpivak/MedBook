package ua.profarma.medbook.events.news;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.news.Icod;

public class EventRemoveIcod extends Event {
    private Icod icod;
    public EventRemoveIcod(Icod icod){
        super(EVENT_REMOVE_ICOD);
        this.icod = icod;
    }

    public Icod getIcod() {
        return icod;
    }
}
