package ua.profarma.medbook.events.points;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.points.FishkaCard;

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
