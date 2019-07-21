package ua.profarma.medbook.types.responses;

import ua.profarma.medbook.types.Errors;
import ua.profarma.medbook.types.notification.Notification;

public class NotificationsResponse {
    public boolean success;
    public Errors errors;
    public Notification[] items;
}
