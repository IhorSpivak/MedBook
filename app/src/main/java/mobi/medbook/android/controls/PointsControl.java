package mobi.medbook.android.controls;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.api.ApiRest;
import mobi.medbook.android.api.MCall;
import mobi.medbook.android.events.EventLogout;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.points.EventLoadFishkaCards;
import mobi.medbook.android.events.points.EventLoadTransactionHistory;
import mobi.medbook.android.types.responses.HistoryExchangeResponse;
import mobi.medbook.android.types.responses.UserFishkaCardDeleteResponse;
import mobi.medbook.android.types.responses.UserFishkaCardsResponse;
import mobi.medbook.android.utils.AppUtils;
import retrofit2.Response;


public class PointsControl {

    public void getTransactionHistoryAll() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getTransactionHistoryAll().enqueue(new MCall<HistoryExchangeResponse>() {

                    @Override
                    public void onSuccess(Response<HistoryExchangeResponse> response) {
                        EventRouter.send(new EventLoadTransactionHistory(response.body().items));
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getUserFishkaCrads() {

        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getUserFishkaCard().enqueue(new MCall<UserFishkaCardsResponse>() {
                    @Override
                    public void onSuccess(Response<UserFishkaCardsResponse> response) {
                        EventRouter.send(new EventLoadFishkaCards(response.body().items));
                    }

                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }


    public void deleteUserFishkaCard(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().deleteUserFishkaCard(id).enqueue(new MCall<UserFishkaCardDeleteResponse>() {
                    @Override
                    public void onSuccess(Response<UserFishkaCardDeleteResponse> response) {

                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }
}
