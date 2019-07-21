package ua.profarma.medbook.types.notification;

public class Notification {
    public Integer id;
    public Integer content_target_id;
    public Integer notification_target_id;
    public Integer user_id;
    public Integer notification_id;
    public Integer notification_points;
    public Integer product_id;
    public Integer delivered;
    public Long time_from;
    public Long time_to;
    public Integer notification_result_type_id;
    public Integer notification_result_accepted;
    public Long result_time;
    public NotificationItem notification;
}
