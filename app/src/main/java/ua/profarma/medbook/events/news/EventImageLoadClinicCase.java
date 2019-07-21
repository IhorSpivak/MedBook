package ua.profarma.medbook.events.news;

import ua.profarma.medbook.events.core.Event;
import ua.profarma.medbook.types.news.CCImage;

public class EventImageLoadClinicCase extends Event {

    private String image;
    public EventImageLoadClinicCase(String image){
        super(EVENT_LOAD_IMAGE_CLINIC_CASE);
        this.image = image;
    }

    public String getImage() {
        return image;
    }
}
