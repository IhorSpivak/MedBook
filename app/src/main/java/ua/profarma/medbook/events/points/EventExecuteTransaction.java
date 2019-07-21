package ua.profarma.medbook.events.points;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.points.ExecuteTransactionData;
import ua.profarma.medbook.types.responses.ExecuteTransactionResponse;

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
