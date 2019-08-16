package mobi.medbook.android.events.news;

import mobi.medbook.android.events.core.Event;

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
