package mobi.medbook.android.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.core.Event;
import mobi.medbook.android.events.notifications.EventDeleteNotif;
import mobi.medbook.android.recyclerviews.notifications.NotificationRecyclerItem;
import mobi.medbook.android.recyclerviews.notifications.NotificationRecyclerItems;
import mobi.medbook.android.recyclerviews.notifications.NotificationsRecyclerView;
import mobi.medbook.android.types.notification.Notification;
import mobi.medbook.android.ui.custom_views.MedBookActivity;


public class NotificationsActivity extends MedBookActivity implements IOnReactionNotification {
    private TextView tvTitle;
    private NotificationsRecyclerView list;
    private NotificationRecyclerItems items = new NotificationRecyclerItems();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ImageView imvClose = findViewById(R.id.activity_notifications_close);
        tvTitle = findViewById(R.id.activity_notifications_title);
        imvClose.setOnClickListener(view -> finish());
        list = findViewById(R.id.activity_notifications_list);
        list.init();
        list.setItemAnimator(null);
        int i = 0;
        if (Core.get().NotificationControl().getItems() != null) {
            for (Notification item : Core.get().NotificationControl().getItems()) {
                if (item.time_from < (System.currentTimeMillis() / 1000) &&
                        item.time_to >= (System.currentTimeMillis() / 1000)) {
                    items.add(new NotificationRecyclerItem(i, item));
                    i++;
                }
            }
            list.itemsAdd(items);
        }
        onLocalizationUpdate();
    }

    @Override
    public void onEvent(Event event) {
        super.onEvent(event);
        switch (event.getEventId()) {
            case Event.EVENT_DELETE_NOTIFICATION:
                int id = ((EventDeleteNotif) event).getId();
                if (items.delete(id) != -1) {
                    list.itemRemove(items.delete(id));
                    items.remove(items.delete(id));
                }
                break;
            case Event.EVENT_LOAD_NOTIFICATIONS:
                if (items == null)
                    list.init();
                else {
                    items.clear();
                    int i = 0;
                    for (Notification item : Core.get().NotificationControl().getItems()) {
                        if (item.time_from < (System.currentTimeMillis() / 1000) &&
                                item.time_to >= (System.currentTimeMillis() / 1000)) {
                            items.add(new NotificationRecyclerItem(i, item));
                            i++;
                        }
                    }
                    list.itemsAdd(items);
                }
                if (items.size() == 0) finish();
                break;
        }
    }

    @Override
    protected void onLocalizationUpdate() {
        tvTitle.setText(Core.get().LocalizationControl().getText(R.id.activity_notifications_title));
    }

    @Override
    public void onReactionNotification(int id) {
        if (items.delete(id) != -1) {
            list.itemRemove(items.delete(id));
            items.remove(items.delete(id));
        }
    }

    @Override
    public void onStartLinkView(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
