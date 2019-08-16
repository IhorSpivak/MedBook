package mobi.medbook.android.events.points;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.points.FishkaCard;


public class EventLoadFishkaCards extends Event {

    private FishkaCard[] items;

    public EventLoadFishkaCards(FishkaCard[] items) {
        super(EVENT_LOAD_FISHKA_CARDS);
        this.items = items;
    }

    public FishkaCard[] getItems() {
        return items;
    }
}
