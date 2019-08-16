package mobi.medbook.android.events.visits;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.utils.LogUtils;

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
