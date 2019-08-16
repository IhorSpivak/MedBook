package mobi.medbook.android.events.points;

import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.types.responses.AddFishkaResponse;

public class EventAddCardFishka extends Event {

    private AddFishkaResponse addFishka;
    private String message;
    private boolean state;


    public EventAddCardFishka(AddFishkaResponse addFishka, String message, boolean state){
        super(EVENT_ADD_FISHKA_CARD);
        this.addFishka = addFishka;
        this.message = message;
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public AddFishkaResponse getAddFishka() {
        return addFishka;
    }

    public boolean isState() {
        return state;
    }
}
