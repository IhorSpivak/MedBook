package mobi.medbook.android.events.materials;

import mobi.medbook.android.events.core.Event;


public class EventUserNotificationReaction extends Event {

    private int reactions;

    public EventUserNotificationReaction(int reactions) {
        super(EVENT_USER_NOTIFICATION_REACTION);
        this.reactions = reactions;
    }

    public int getReactions() {
        return reactions;
    }
}
