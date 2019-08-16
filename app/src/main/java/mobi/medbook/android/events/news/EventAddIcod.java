package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;

import mobi.medbook.android.types.news.Icod;

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
