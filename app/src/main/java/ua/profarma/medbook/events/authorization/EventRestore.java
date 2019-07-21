package ua.profarma.medbook.events.authorization;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.utils.LogUtils;

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
