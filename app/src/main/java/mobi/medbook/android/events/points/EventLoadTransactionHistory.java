package mobi.medbook.android.events.points;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.points.HistoryExchange;


public class EventLoadTransactionHistory extends Event {

    private HistoryExchange[] items;

    public EventLoadTransactionHistory(HistoryExchange[] items) {
        super(EVENT_LOAD_TRANSACTION_HISTORY);
        this.items = items;
    }

    public HistoryExchange[] getItems() {
        return items;
    }
}
