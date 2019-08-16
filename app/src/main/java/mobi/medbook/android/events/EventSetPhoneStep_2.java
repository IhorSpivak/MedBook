package mobi.medbook.android.events;

import mobi.medbook.android.events.core.Event;

public class EventSetPhoneStep_2 extends Event {

    private int key;
    private String phone;
    public EventSetPhoneStep_2(int key, String phone) {
        super(EVENT_SET_PHONE_STEP_2);
        this.key = key;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public int getKey() {
        return key;
    }
}
