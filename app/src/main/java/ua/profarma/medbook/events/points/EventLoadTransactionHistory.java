package ua.profarma.medbook.events.points;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.points.HistoryExchange;

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
