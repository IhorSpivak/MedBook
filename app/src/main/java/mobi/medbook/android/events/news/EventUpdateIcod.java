package mobi.medbook.android.events.news;


import mobi.medbook.android.events.core.Event;

public class EventUpdateIcod extends Event {
    private int id;
    public EventUpdateIcod(int id) {
        super(EVENT_UPDATE_STATE_EXPANDABLE_LIST);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
