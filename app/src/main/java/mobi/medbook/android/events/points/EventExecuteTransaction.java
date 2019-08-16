package mobi.medbook.android.events.points;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.points.ExecuteTransactionData;


public class EventExecuteTransaction extends Event {
    private String message;
    private boolean status;
    private ExecuteTransactionData data;

    public EventExecuteTransaction(ExecuteTransactionData data, String message, boolean status) {
        super(EVENT_EXECUTE_TRANSACTION);
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public ExecuteTransactionData getData() {
        return data;
    }
}
