package ua.profarma.medbook.controls;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.api.PharmaApi;
import ua.profarma.medbook.events.EventEndQrCode;
import ua.profarma.medbook.events.EventSetPhoneStep_1;
import ua.profarma.medbook.events.EventSetPhoneStep_2;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.points.EventAddCardFishka;
import ua.profarma.medbook.events.points.EventExecuteTransaction;
import ua.profarma.medbook.events.points.EventGetSMSExhangePoints;
import ua.profarma.medbook.events.points.EventUpdateKeyLikiWiki;
import ua.profarma.medbook.events.profile.EventSetPhoneNumberFailedStep1;
import ua.profarma.medbook.types.points.ExecuteTransactionData;
import ua.profarma.medbook.types.requests.ActivationCodeForExchangeRequest;
import ua.profarma.medbook.types.requests.AddFishkaRequest;
import ua.profarma.medbook.types.requests.CheckQrRequest;
import ua.profarma.medbook.types.requests.CheckUserLikiWikiRequest;
import ua.profarma.medbook.types.requests.ExecuteTransactionRequest;
import ua.profarma.medbook.types.requests.GetActivationCodeRequest;
import ua.profarma.medbook.types.responses.AddFishkaResponse;
import ua.profarma.medbook.types.responses.CheckQrResponse;
import ua.profarma.medbook.types.responses.CheckUserLikiWikiResponse;
import ua.profarma.medbook.types.responses.ExecuteTransactionResponse;
import ua.profarma.medbook.types.responses.VerificationPhoneResponse;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

public class PharmaApiControl {
    public void verificationPhone(final String phone, final boolean step1) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            PharmaApi.PointAccess().getActivationCode(new GetActivationCodeRequest(App.getUser().id, phone)).enqueue(new Callback<VerificationPhoneResponse>() {
                @Override
                public void onResponse(Call<VerificationPhoneResponse> call, Response<VerificationPhoneResponse> response) {
                    if (response.body() != null)
                        if (response.body().status) {
                            EventRouter.send(step1 ? new EventSetPhoneStep_1(response.body().data.key, phone) : new EventSetPhoneStep_2(response.body().data.key, phone));
                        } else {
                            EventRouter.send(new EventSetPhoneNumberFailedStep1(response.body().user_message));
                        }
                    else
                        EventRouter.send(new EventSetPhoneNumberFailedStep1("Unknown error for change phone or add phone"));
                }

                @Override
                public void onFailure(Call<VerificationPhoneResponse> call, Throwable t) {
                    EventRouter.send(new EventSetPhoneNumberFailedStep1(t.getLocalizedMessage()));
                }
            });
        } else {
            EventRouter.send(new EventSetPhoneNumberFailedStep1(Core.get().LocalizationControl().getText(R.id.no_internet_connection)));
        }
    }

    public void checkQr(final String text) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            PharmaApi.PointAccess().chekQR(new CheckQrRequest(text)).enqueue(new Callback<CheckQrResponse>() {
                @Override
                public void onResponse(Call<CheckQrResponse> call, Response<CheckQrResponse> response) {
                    if (response.body().isStatus()) {
                        //AppUtils.toastOk(response.body().getUserMessage(), false);
                        EventRouter.send(new EventEndQrCode(true, response.body().getUserMessage()));
                    } else {
                        //AppUtils.toastError(response.body().getUserMessage(), false);
                        EventRouter.send(new EventEndQrCode(false, response.body().getUserMessage()));
                    }
                }

                @Override
                public void onFailure(Call<CheckQrResponse> call, Throwable t) {
                    //AppUtils.toastError(t.getLocalizedMessage(), false);
                    EventRouter.send(new EventEndQrCode(false, t.getLocalizedMessage()));
                }
            });
        } else {
            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_internet_connection), false);
        }
    }

    public void addFishkaCard(int user_id, String fishka_card_number, String phone) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            PharmaApi.PointAccess().addCard(new AddFishkaRequest(user_id, fishka_card_number, phone)).enqueue(new Callback<AddFishkaResponse>() {
                @Override
                public void onResponse(Call<AddFishkaResponse> call, Response<AddFishkaResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().status) {
                            EventRouter.send(new EventAddCardFishka(response.body(), null, true));
                        } else {
                            //status = false;
                            EventRouter.send(new EventAddCardFishka(null, response.body().user_message, false));
                        }
                    } else {
                        //unSuccessful
                        EventRouter.send(new EventAddCardFishka(null, response.body().user_message, false));
                    }
                }

                @Override
                public void onFailure(Call<AddFishkaResponse> call, Throwable t) {
                    EventRouter.send(new EventAddCardFishka(null, t.getLocalizedMessage(), false));
                }
            });
        } else {
            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_internet_connection), false);
            EventRouter.send(new EventAddCardFishka(null, null, false));
        }
    }

    public void getSMSForExchangePoints(int pointsExchange, String verification_type) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            PharmaApi.PointAccess().getActivationCodeForExchangeFishka(new ActivationCodeForExchangeRequest(
                    App.getUser().id, App.getUser().phone, verification_type
            )).enqueue(new Callback<VerificationPhoneResponse>() {
                @Override
                public void onResponse(Call<VerificationPhoneResponse> call, Response<VerificationPhoneResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().status) {
                            LogUtils.logD("hfgjghj567rftgh", "pointsExchange = " + pointsExchange);
                            EventRouter.send(new EventGetSMSExhangePoints(response.body().data.key, response.body().data.transaction, null, true, pointsExchange));
                        } else {
                            //status = false;
                            if (response.body().user_message == null || response.body().user_message.isEmpty()) {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    EventRouter.send(new EventGetSMSExhangePoints(-1, null, jObjError.getJSONObject("errors").getString("message"), false, 0));
                                } catch (Exception e) {
                                    EventRouter.send(new EventGetSMSExhangePoints(-1, null, e.getMessage(), false, 0));
                                }
                            } else
                                EventRouter.send(new EventGetSMSExhangePoints(-1, null, response.body().user_message, false, 0));
                        }
                    } else {
                        //unSuccessful
                        if (response.body().user_message == null || response.body().user_message.isEmpty()) {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                EventRouter.send(new EventGetSMSExhangePoints(-1, null, jObjError.getJSONObject("errors").getString("message"), false, 0));
                            } catch (Exception e) {
                                EventRouter.send(new EventGetSMSExhangePoints(-1, null, e.getMessage(), false, 0));
                            }
                        } else
                            EventRouter.send(new EventGetSMSExhangePoints(-1, null, response.body().user_message, false, 0));
                    }
                }

                @Override
                public void onFailure(Call<VerificationPhoneResponse> call, Throwable t) {
                    EventRouter.send(new EventGetSMSExhangePoints(-1, null, t.getLocalizedMessage(), false, 0));
                }
            });
        } else {
            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_internet_connection), false);
        }
    }

    public void executeTransaction(String transaction, int key, int value, String payment_details) {
//        LogUtils.logD("jghjghghkjh", "transaction = " + transaction);
//        LogUtils.logD("jghjghghkjh", "key = " + key);
//        LogUtils.logD("jghjghghkjh", "value = " + value);
//        LogUtils.logD("jghjghghkjh", "payment_details = " + payment_details);
        //FOR TEST
        //ExecuteTransactionData data = new ExecuteTransactionData();
        //data.value = 500;
        //data.AmountToEnrollment = 350;
        //EventRouter.send(new EventExecuteTransaction(data, "", true));
        //END FOR TEST
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            PharmaApi.PointAccess().executeTransaction(new ExecuteTransactionRequest(transaction, key, App.getUser().id, value, payment_details)).enqueue(new Callback<ExecuteTransactionResponse>() {
                @Override
                public void onResponse(Call<ExecuteTransactionResponse> call, Response<ExecuteTransactionResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body().status) {
                            EventRouter.send(new EventExecuteTransaction(response.body().data, null, true));
                        } else {
                            //status = false;
                            if (response.body().user_message == null || response.body().user_message.isEmpty()) {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    EventRouter.send(new EventExecuteTransaction(null, jObjError.getJSONObject("errors").getString("message"), false));
                                } catch (Exception e) {
                                    EventRouter.send(new EventExecuteTransaction(null, e.getMessage(), false));
                                }
                            } else
                                EventRouter.send(new EventExecuteTransaction(null, response.body().user_message, false));
                        }
                    } else {
                        //unSuccessful
                        if (response.body().user_message == null || response.body().user_message.isEmpty()) {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                EventRouter.send(new EventExecuteTransaction(null, jObjError.getJSONObject("errors").getString("message"), false));
                            } catch (Exception e) {
                                EventRouter.send(new EventExecuteTransaction(null, e.getMessage(), false));
                            }
                        } else
                            EventRouter.send(new EventExecuteTransaction(null, response.body().user_message, false));
                    }
                }

                @Override
                public void onFailure(Call<ExecuteTransactionResponse> call, Throwable t) {
                    EventRouter.send(new EventExecuteTransaction(null, t.getLocalizedMessage(), false));
                }
            });
        } else {
            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_internet_connection), false);
        }
    }

    public void checkUserLikiWiki(CheckUserLikiWikiRequest checkUserLikiWikiRequest) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            PharmaApi.PointAccess().checkUserLikiWiki(checkUserLikiWikiRequest).enqueue(new Callback<CheckUserLikiWikiResponse>() {
                @Override
                public void onResponse(Call<CheckUserLikiWikiResponse> call, Response<CheckUserLikiWikiResponse> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().status && response.body().data.item.likiwiki_auth_token != null && !response.body().data.item.likiwiki_auth_token.isEmpty())
                                App.updateLikiWikiToken(response.body().data.item.likiwiki_auth_token);
                                EventRouter.send(new EventUpdateKeyLikiWiki(response.body().status, response.body().user_message));
                        } else {
                            EventRouter.send(new EventUpdateKeyLikiWiki(false, response.message()));
                        }
                    } else {
                        EventRouter.send(new EventUpdateKeyLikiWiki(false, response.message()));
                    }
                }

                @Override
                public void onFailure(Call<CheckUserLikiWikiResponse> call, Throwable t) {
                    EventRouter.send(new EventUpdateKeyLikiWiki(false, t.getLocalizedMessage()));
                }
            });
        } else {
            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.no_internet_connection), false);
        }
    }
}
