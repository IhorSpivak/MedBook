package ua.profarma.medbook.events;

import ua.profarma.medbook.events.core.Event;

public class EventSetPhoneStep_1 extends Event {

    private int key;
    private String phone;
    public EventSetPhoneStep_1(int key, String phone) {
        super(EVENT_SET_PHONE_STEP_1);
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
