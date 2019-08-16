package mobi.medbook.android.types.responses;

import mobi.medbook.android.types.Errors;
import mobi.medbook.android.types.notification.Notification;

public class NotificationsResponse {
    public boolean success;
    public Errors errors;
    public Notification[] items;
}
