package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.news.Icod;

public class EventGetIcods extends Event {

    private Icod[] items;
    private int level;
    private int owner_id;

    public EventGetIcods(int level, Icod[] items, int owner_id) {
        super(EVENT_ICOD_LOAD);
        this.level = level;
        this.owner_id = owner_id;
        this.items = new Icod[items.length];
        for(int i = 0; i < items.length; i++){
            this.items[i] = items[i];
        }
    }

    public Icod[] getItems() {
        return items;
    }


    public int getLevel() {
        return level;
    }


    public int getOwner_id() {
        return owner_id;
    }
}
