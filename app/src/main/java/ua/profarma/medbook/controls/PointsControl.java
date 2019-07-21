package ua.profarma.medbook.controls;

import retrofit2.Response;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.api.ApiRest;
import ua.profarma.medbook.api.MCall;
import ua.profarma.medbook.events.EventLogout;
import ua.profarma.medbook.events.points.EventLoadTransactionHistory;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.points.EventLoadFishkaCards;
import ua.profarma.medbook.types.responses.HistoryExchangeResponse;
import ua.profarma.medbook.types.responses.UserFishkaCardDeleteResponse;
import ua.profarma.medbook.types.responses.UserFishkaCardsResponse;
import ua.profarma.medbook.utils.AppUtils;

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
