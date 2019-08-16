package mobi.medbook.android.events.profile;

import mobi.medbook.android.events.core.Event;


public class EventAgreementsLoad extends Event {
    private String title;
    private String description;

    public EventAgreementsLoad(String title, String description) {
        super(EVENT_AGREEMENTS_LOAD);
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
