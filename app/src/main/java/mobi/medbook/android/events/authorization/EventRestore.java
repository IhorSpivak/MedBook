package mobi.medbook.android.events.authorization;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.utils.LogUtils;

public class EventRestore extends Event {
    private boolean success;
    private String messsage;

    public EventRestore(boolean success, String message) {
        super(EVENT_RESTORE);
        LogUtils.logD("hfghfghjfg", "EventRestore");
        this.success = success;
        this.messsage = message;
        LogUtils.logD("hfghfghjfg", "success = " + success);
        LogUtils.logD("hfghfghjfg", "message = " + message);
    }

    public String getMesssage() {
        return messsage;
    }

    public boolean isSuccess() {
        return success;
    }
}
