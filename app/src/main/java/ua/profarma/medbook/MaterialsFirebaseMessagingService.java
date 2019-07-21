package ua.profarma.medbook;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.NotificationCompat;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ua.profarma.medbook.ui.MainActivity;
import ua.profarma.medbook.utils.LogUtils;

public class MaterialsFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    private Target loadtarget;

    public void loadBitmap(String url, String title, String body) {

        if (loadtarget == null) loadtarget = new Target() {

            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                handleLoadedBitmap(bitmap, title , body);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
        new Handler(Looper.getMainLooper()).post(() -> {
            Picasso.get().load(url).into(loadtarget);
        });
    }

    private void handleLoadedBitmap(Bitmap b, String title, String body) {
        NotificationCompat.Builder notificationCompatBuilder = null;

        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Имя канала";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationManager mNotificationManager =
                (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

//            // Create PendingIntent
//            Intent resultIntent = new Intent(this, MainActivity.class);
//            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setShowBadge(true);
            mChannel.enableVibration(true);
            mChannel.enableLights(true);
            mChannel.setLightColor(App.getAppContext().getColor(R.color.colorPrimary));
            mNotificationManager.createNotificationChannel(mChannel);
            notificationCompatBuilder = new NotificationCompat.Builder(App.getAppContext());
            notificationCompatBuilder.setContentTitle(title);
            notificationCompatBuilder.setContentText(body);
            notificationCompatBuilder.setSmallIcon(R.mipmap.ic_nb_today1);
            notificationCompatBuilder.setChannelId(CHANNEL_ID);
            notificationCompatBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));
            notificationCompatBuilder.setAutoCancel(true);
            notificationCompatBuilder.build();
        } else {

            notificationCompatBuilder = new NotificationCompat.Builder(App.getAppContext());
                     notificationCompatBuilder.setContentTitle(title);
                     notificationCompatBuilder.setContentText(body);
                     notificationCompatBuilder.setSmallIcon(R.mipmap.ic_nb_today1);
                     notificationCompatBuilder.setPriority(Notification.PRIORITY_MAX);
                     notificationCompatBuilder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0));
                     notificationCompatBuilder.setAutoCancel(true);
                     notificationCompatBuilder.build();
        }

        if (b != null) {
            NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
            style.bigPicture(b);
            style.setSummaryText(body);
            notificationCompatBuilder.setStyle(style);
        }

        mNotificationManager.notify(notifyID, notificationCompatBuilder.build());
    }


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. UserVisitItem messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. UserVisitItem
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        LogUtils.logD(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LogUtils.logD(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LogUtils.logD(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            LogUtils.logD(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());
            LogUtils.logD(TAG, "Message Notification Icon: " + remoteMessage.getNotification().getIcon());


            // Sets an ID for the notification, so it can be updated.
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = "Имя канала";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            // Create a notification and set the notification channel.
            Notification notification = null;

            if (remoteMessage.getData().get("transition_category").equals("calendar")) {
                Core.get().VisitsControl().getUserVisits();
                Core.get().VisitsControl().getDashboardVisits();
            }


            String loadUrlIcon = null;
            if(remoteMessage.getData() != null && !remoteMessage.getData().isEmpty()){
                if(remoteMessage.getData().containsKey("attachment-url"))
                    loadUrlIcon = remoteMessage.getData().get("attachment-url");
            }
            if(loadUrlIcon!= null){

                LoadImageThread thread = new LoadImageThread(loadUrlIcon, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                thread.start();

            } else {


                NotificationManager mNotificationManager =
                        (NotificationManager) App.getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

//            // Create PendingIntent
//            Intent resultIntent = new Intent(this, MainActivity.class);
//            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                    mNotificationManager.createNotificationChannel(mChannel);
                    notification = new Notification.Builder(App.getAppContext(), mChannel.getId())
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setSmallIcon(R.mipmap.ic_nb_today1)
                            .setChannelId(CHANNEL_ID)
                            .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
                            //.setContentIntent(resultPendingIntent)
                            .setAutoCancel(true)
                            .build();
                } else {

                    notification = new Notification.Builder(App.getAppContext())
                            .setContentTitle(remoteMessage.getNotification().getTitle())
                            .setContentText(remoteMessage.getNotification().getBody())
                            .setSmallIcon(R.mipmap.ic_nb_today1)
                            .setPriority(Notification.PRIORITY_MAX)
                            .setContentIntent(PendingIntent.getActivity(this, 0, new Intent(), 0))
                            //.setContentIntent(resultPendingIntent)
                            .setAutoCancel(true)
                            .build();
                }


                notification.flags |= Notification.FLAG_AUTO_CANCEL;

                mNotificationManager.notify(notifyID, notification);
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        LogUtils.logD(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */
//    private void scheduleJob() {
//        // [START dispatch_job]
//        OneTimeWorkRequest work = new OneTimeWorkRequest.Builder(MyWorker.class)
//                .build();
//        WorkManager.getInstance().beginWith(work).enqueue();
//        // [END dispatch_job]
//    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        LogUtils.logD(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_nb_today1)
                        .setContentTitle(getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private class LoadImageThread extends Thread{

        private String loadUrlIcon;
        private String title;
        private String body;

        public LoadImageThread(String loadUrlIcon, String title, String body){
            this.title = title;
            this.body = body;
            this.loadUrlIcon = loadUrlIcon;
        }

        @Override
        public void run() {
            loadBitmap(loadUrlIcon, title, body);
        }
    }
}
