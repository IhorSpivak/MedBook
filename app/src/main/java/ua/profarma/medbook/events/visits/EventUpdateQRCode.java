package ua.profarma.medbook.events.visits;

import ua.profarma.medbook.events.core.Event;

public class EventUpdateQRCode extends Event {
    private String token;
    private long expired;
    public EventUpdateQRCode(String token, long expired){
        super(EVENT_UPDATE_QR_CODE);
        this.token = token;
        this.expired = expired;
    }

    public String getToken() {
        return token;
    }

    public long getExpired() {
        return expired;
    }
}
