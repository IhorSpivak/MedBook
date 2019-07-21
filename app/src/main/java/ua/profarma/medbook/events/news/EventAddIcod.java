package ua.profarma.medbook.events.news;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.news.Icod;

public class EventAddIcod extends Event {
    private Icod icod;
    public EventAddIcod(Icod icod){
        super(EVENT_ADD_ICOD);
        this.icod = icod;
    }

    public Icod getIcod() {
        return icod;
    }
}
