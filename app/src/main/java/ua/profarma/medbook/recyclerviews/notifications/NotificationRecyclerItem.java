package ua.profarma.medbook.recyclerviews.notifications;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.util.Patterns;
import android.view.View;

import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.notification.Notification;
import ua.profarma.medbook.ui.IOnReactionNotification;
import ua.profarma.medbook.utils.LogUtils;

public class NotificationRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Notification notificationItem;
    private int pos;

    public NotificationRecyclerItem(int pos, Notification notificationItem) {
        this.notificationItem = notificationItem;
        this.pos = pos;
    }

    public Notification getNotificationItem() {
        return notificationItem;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof NotificationViewHolder) {
            ((NotificationViewHolder) holder).init(this, notificationItem);
        }
    }

    @Override
    public int getViewType() {
        return 1;
    }

    @Override
    public void onClick(View view) {

//            "id": 1, "alias": "all"
//            "id": 2, "alias": "left"
//            "id": 3, "alias": "right"
//            "id": 4, "alias": "body"
        if (view.getId() == R.id.item_notification_left_btn && Patterns.WEB_URL.matcher(notificationItem.notification.left_button_link).matches()) {
            getActivity(view.getContext()).onStartLinkView(notificationItem.notification.left_button_link);
        }
        if (view.getId() == R.id.item_notification_right_btn && Patterns.WEB_URL.matcher(notificationItem.notification.right_button_link).matches()) {
            getActivity(view.getContext()).onStartLinkView(notificationItem.notification.right_button_link);
        }
        switch (notificationItem.notification_result_type_id) {
            case 1:
                LogUtils.logD("jvhjhj6tuyjgyh", "case 1");
                if (getActivity(view.getContext()) != null) {
                    LogUtils.logD("jvhjhj6tuyjgyh", "case 1.0");
                    getActivity(view.getContext()).onReactionNotification(notificationItem.id);
                    Core.get().NotificationControl().reactionNotification(notificationItem.id);
                }
                break;
            case 2:
                LogUtils.logD("jvhjhj6tuyjgyh", "case 2");
                if (view.getId() == R.id.item_notification_left_btn) {
                    if (getActivity(view.getContext()) != null) {
                        getActivity(view.getContext()).onReactionNotification(notificationItem.id);
                        Core.get().NotificationControl().reactionNotification(notificationItem.id);
                    }
                }
                break;
            case 3:
                LogUtils.logD("jvhjhj6tuyjgyh", "case 3");
                if (view.getId() == R.id.item_notification_right_btn) {
                    if (getActivity(view.getContext()) != null) {
                        getActivity(view.getContext()).onReactionNotification(notificationItem.id);
                        Core.get().NotificationControl().reactionNotification(notificationItem.id);
                    }
                }
                break;
            case 4:
                LogUtils.logD("jvhjhj6tuyjgyh", "case 4");
                if (view.getId() == R.id.item_notification_description) {
                    if (getActivity(view.getContext()) != null) {
                        getActivity(view.getContext()).onReactionNotification(notificationItem.id);
                        Core.get().NotificationControl().reactionNotification(notificationItem.id);
                    }
                }
                break;
        }
    }

    private IOnReactionNotification getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnReactionNotification) {
                return (IOnReactionNotification) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
