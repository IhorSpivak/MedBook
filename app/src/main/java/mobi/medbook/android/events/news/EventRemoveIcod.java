package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.news.Icod;

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
