package mobi.medbook.android.events.points;

import mobi.medbook.android.events.core.Event;


public class EventGetSMSExhangePoints extends Event {
    private int key;
    private String message;
    private String transaction;
    private  boolean status;
    private int pointsExchange;

    public EventGetSMSExhangePoints(int key, String transaction, String message, boolean status, int pointsExchange){
        super(EVENT_GET_SMS_EXCHANGE_POINTS);
        this.key = key;
        this.message = message;
        this.status = status;
        this.transaction = transaction;
        this.pointsExchange = pointsExchange;
    }

    public int getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getTransaction() {
        return transaction;
    }

    public int getPointsExchange() {
        return pointsExchange;
    }
}
