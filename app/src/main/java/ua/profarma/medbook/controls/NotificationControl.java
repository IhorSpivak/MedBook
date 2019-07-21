package ua.profarma.medbook.controls;

import java.util.ArrayList;
import java.util.List;

import br.vince.easysave.EasySave;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.api.ApiRest;
import ua.profarma.medbook.api.MCall;
import ua.profarma.medbook.events.EventLogout;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.notifications.EventLoadNofications;
import ua.profarma.medbook.events.notifications.EventLoad_5_nofications;
import ua.profarma.medbook.types.notification.Notification;
import ua.profarma.medbook.types.requests.ReactionNotificationRequest;
import ua.profarma.medbook.types.responses.NotificationsResponse;
import ua.profarma.medbook.types.responses.ReactionNotificationResponse;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

public class NotificationControl {

    private String TAG = "AppMedbook/NotificationControl";
    private ArrayList<Notification> items;

    public ArrayList<Notification> getItems() {
        return items;
    }

    public int getQuantityNotification() {
        int quantityNotification = 0;
        if (items != null) {
            for (Notification item : items) {
                if (item.time_from < (System.currentTimeMillis() / 1000) &&
                        item.time_to >= (System.currentTimeMillis() / 1000)) {
                    quantityNotification++;
                }
            }
        }
        return quantityNotification;
    }

    public void getNotifications(long from, long to, int per_page) {
        if (per_page != 5) {
            List<Notification> cashNotifications = new EasySave(App.getAppContext()).retrieveList("notifications.data", Notification[].class);
            if (items == null || items.isEmpty()) {
                items = new ArrayList<>();
                items.addAll(cashNotifications);
            }
        }
        if (App.isUpdateNotifications() || per_page == 5) {
            if (Core.get().AuthorizationControl().updateAccessToken()) {
                if (per_page != 5) App.setUpdateLastTimeNotifications();
                if (AppUtils.isNetworkAvailable(App.getAppContext())) {

                    LogUtils.logD(TAG, "from = " + from);
                    LogUtils.logD(TAG, "to = " + to);
                    LogUtils.logD(TAG, "per_page = " + per_page);
                    ApiRest.PointAccess().getNotifications(from, to, 0, per_page).enqueue(new Callback<NotificationsResponse>() {
                        @Override
                        public void onResponse(Call<NotificationsResponse> call, Response<NotificationsResponse> response) {
                            if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                                if (response.isSuccessful()) {
                                    if (response.body().success) {
                                        if (per_page == 5) {
                                            LogUtils.logD(TAG, "EventLoad_5_nofications START");
                                            EventRouter.send(new EventLoad_5_nofications(response.body().items, null));
                                        } else {
                                            if (items == null)
                                                items = new ArrayList<>();
                                            else
                                                items.clear();
                                            for (int i = 0; i < response.body().items.length; i++) {
                                                items.add(response.body().items[i]);
                                            }
                                            new EasySave(App.getAppContext()).saveList("notifications.data", items);
                                            EventRouter.send(new EventLoadNofications(true, null));
                                        }
                                    } else {
                                        App.clearUpdateLastTimeNotifications();
                                    }
                                } else {
                                    App.clearUpdateLastTimeNotifications();
                                }
                            } else {
                                App.clearUpdateLastTimeNotifications();
                            }
                        }

                        @Override
                        public void onFailure(Call<NotificationsResponse> call, Throwable t) {
                            App.clearUpdateLastTimeNotifications();
                        }
                    });
                } else {
                }
            } else
                EventRouter.send(new EventLogout());

        }
    }

    public void reactionNotification(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().reactionNotification(id, new ReactionNotificationRequest()).enqueue(new MCall<ReactionNotificationResponse>() {
                    @Override
                    public void onSuccess(Response<ReactionNotificationResponse> response) {
                        //EventRouter.send(new EventDeleteNotif(id));
                    }
                });
            }
            else {
                //not Available network
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void clearData(){
        new EasySave(App.getAppContext()).saveList("notifications.data", null);
        items = null;
    }

}
