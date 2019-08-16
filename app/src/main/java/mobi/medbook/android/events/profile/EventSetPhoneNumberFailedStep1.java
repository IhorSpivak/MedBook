package mobi.medbook.android.events.profile;

import mobi.medbook.android.events.core.Event;



public class EventSetPhoneNumberFailedStep1 extends Event {
    private String message;
    public EventSetPhoneNumberFailedStep1(String message){
        super(SET_PHONE_NUMBER_FAILED_STEP_1);
        this.message = message;

    }

    public String getMessage() {
        return message;
    }
}
