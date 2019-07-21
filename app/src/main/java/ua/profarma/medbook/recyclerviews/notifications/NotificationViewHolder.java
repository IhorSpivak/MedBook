package ua.profarma.medbook.recyclerviews.notifications;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import ua.profarma.medbook.App;
import ua.profarma.medbook.R;
import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.types.notification.Notification;

public class NotificationViewHolder extends BaseViewHolder {

    private View rootView;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvTime;

    private TextView tvLeftBtn;
    private TextView tvRightBtn;

    private ImageView imv;


    public NotificationViewHolder(View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.item_notification_title);
        tvDescription = itemView.findViewById(R.id.item_notification_description);
        tvTime = itemView.findViewById(R.id.item_notification_time);
        tvLeftBtn = itemView.findViewById(R.id.item_notification_left_btn);
        tvRightBtn = itemView.findViewById(R.id.item_notification_right_btn);

        imv = itemView.findViewById(R.id.item_notification_image);



        rootView = itemView;
    }

    public void init(NotificationRecyclerItem owner, Notification notificationItem) {
        rootView.setOnClickListener(owner);
        tvLeftBtn.setOnClickListener(owner);
        tvRightBtn.setOnClickListener(owner);
        int selectLang = -1;
        for (int i = 0; i < notificationItem.notification.translations.length; i++) {
            if (notificationItem.notification.translations[i].language.substring(0, 2).equals(App.getLanguage())) {
                selectLang = i;
            }
        }
        if (selectLang == -1) {
            for (int i = 0; i < notificationItem.notification.translations.length; i++) {
                if (notificationItem.notification.translations[i].language.substring(0, 2).equals("uk")) {
                    selectLang = i;
                }
            }
        }
        if(tvTitle != null){
            tvTitle.setText(notificationItem.notification.translations[selectLang].title);
        }
        if(tvDescription != null){
            tvDescription.setText(notificationItem.notification.translations[selectLang].description);
        }
        if(tvTime != null){
            Date date = new Date(notificationItem.time_from * 1000L);
            //DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            //DateFormat format = new SimpleDateFormat("hh:mm:ss dd MMM yyyy");
            DateFormat format = new SimpleDateFormat("HH:mm");
            //format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
            String formatted = format.format(date);
            tvTime.setText(formatted);
        }
        if(tvLeftBtn != null){
            tvLeftBtn.setText(notificationItem.notification.translations[selectLang].left_button_name);
        }
        if(tvRightBtn != null){
            tvRightBtn.setText(notificationItem.notification.translations[selectLang].right_button_name);
        }

        if(imv != null) {
            if (notificationItem.notification.translations[selectLang].logo != null && !notificationItem.notification.translations[selectLang].logo.isEmpty())
                Picasso.get().load(notificationItem.notification.translations[selectLang].logo).into(imv);
            else
                imv.setVisibility(View.GONE);

        }
    }
}
