package ua.profarma.medbook.events.visits;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.utils.LogUtils;

public class EventStartQRCode extends Event {
    private String token;
    private long expired;
    public EventStartQRCode(String token, long expired){
        super(EVENT_START_QR_CODE);
        this.token = token;
        this.expired = expired;
        LogUtils.logD("thfghfjghj", "expires_at = " + (expired));
    }

    public String getToken() {
        return token;
    }

    public long getExpired() {
        return expired;
    }
}
