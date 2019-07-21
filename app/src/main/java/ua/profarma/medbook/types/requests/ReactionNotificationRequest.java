package ua.profarma.medbook.types.requests;

public class ReactionNotificationRequest {
    public int notification_result_accepted;
    public long result_time;

    public ReactionNotificationRequest() {
        notification_result_accepted = 1;
        result_time = (System.currentTimeMillis() / 1000);
    }

}
