package mobi.medbook.android.events;

import mobi.medbook.android.events.core.Event;




public class EventEndQrCode extends Event {
    private boolean status;
    private String message;
    public EventEndQrCode(boolean status, String message){
        super(EVENT_END_QR_CODE);
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
