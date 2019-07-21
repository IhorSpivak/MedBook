package ua.profarma.medbook.events.materials;

import ua.profarma.medbook.events.core.Event;

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
