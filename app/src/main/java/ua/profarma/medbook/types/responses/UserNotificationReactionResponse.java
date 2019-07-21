package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.api.MResponse;
import ua.profarma.medbook.types.materials.UserNotificationReaction;

public class UserNotificationReactionResponse extends MResponse {
    public UserNotificationReaction[] items;
}
