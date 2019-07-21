package ua.profarma.medbook.events.points;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.responses.AddFishkaResponse;

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
