package ua.profarma.medbook.controls;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.api.ApiRest;
import ua.profarma.medbook.api.MCall;
import ua.profarma.medbook.events.EventCloseVerificationFragmentSteps;
import ua.profarma.medbook.events.EventGetUserSuccess;
import ua.profarma.medbook.events.EventLogout;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.profile.EventAgreementsDontLoad;
import ua.profarma.medbook.events.profile.EventAgreementsLoad;
import ua.profarma.medbook.events.profile.EventPointsAgreementsOk;
import ua.profarma.medbook.types.requests.PhoneUserUpdate;
import ua.profarma.medbook.types.requests.PointsAgreementRequest;
import ua.profarma.medbook.types.requests.TermsAgreementRequest;
import ua.profarma.medbook.types.responses.AgreementResponse;
import ua.profarma.medbook.types.responses.UserInfo;
import ua.profarma.medbook.types.responses.UserInfoResponse;
import ua.profarma.medbook.types.user.Agreement;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

public class UserControl {
    public void getUser() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getUser().enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().success) {
                                    App.setUserInfo(response.body().items[0]);
                                    EventRouter.send(new EventGetUserSuccess());
                                }

                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), false);
                                } catch (Exception e) {
                                    AppUtils.toastError(e.getMessage(), false);
                                }
                            }
                        } else {
                            AppUtils.toastError(App.getAppContext().getString(R.string.message_401), false);
                            App.logout();
                            LogUtils.logD("EventLogout", "UserControl/getUser");
                            EventRouter.send(new EventLogout());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {

                        AppUtils.toastError("response onFailure " + t.getLocalizedMessage(), false);
                    }
                });
            } else {
                AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void updateStatusTermsAndAgreements(int statusTermsAndAgreements) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().updateStatusTermsAndAgreements(App.getUser().id, new TermsAgreementRequest(statusTermsAndAgreements)).enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().success) {
                                    App.updateUserTermsAgreement(response.body().items[0].terms_agreement);
                                    //EventRouter.send(new EventGetUserSuccess());
                                }
                            } else {
                                AppUtils.toastError(response.body() != null && response.body().errors != null && response.body().errors.message != null && response.body().errors.message.isEmpty() ? response.body().errors.message : "response unsuccessful", false);

                            }
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), false);
                            } catch (Exception e) {
                                AppUtils.toastError(e.getMessage(), false);
                            }
                            App.logout();
                            LogUtils.logD("EventLogout", "UserControl/updateStatusTermsAndAgreements");
                            EventRouter.send(new EventLogout());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        AppUtils.toastError("response onFailure " + t.getLocalizedMessage(), false);
                    }
                });
            } else {
                AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void updateStatusPointsAgreements(int statusPointsAgreements) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().updateStatusPointsAgreements(App.getUser().id, new PointsAgreementRequest(statusPointsAgreements)).enqueue(new MCall<UserInfoResponse>() {
                    @Override
                    public void onSuccess(Response<UserInfoResponse> response) {
                        getUser();
                        EventRouter.send(new EventPointsAgreementsOk());
                    }
                });
            } else
                AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        } else {
            LogUtils.logD("EventLogout", "UserControl/updateStatusPointsAgreements");
            EventRouter.send(new EventLogout());
        }
    }

    public void getAgreements(int idType) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getAgreement(idType, "translations,agreementType").enqueue(new MCall<AgreementResponse>() {
                    @Override
                    public void onSuccess(Response<AgreementResponse> response) {
                        if (response != null && response.body() != null && response.body().items.length > 0) {
                            Agreement agreement = response.body().items[0];
                            int selectLang = -1;
                            for (int i = 0; i < agreement.translations.length; i++) {
                                if (agreement.translations[i].language.substring(0, 2).equals(App.getLanguage())) {
                                    selectLang = i;
                                }
                            }
                            if (selectLang == -1) {
                                for (int i = 0; i < agreement.translations.length; i++) {
                                    if (agreement.translations[i].language.substring(0, 2).equals("uk")) {
                                        selectLang = i;
                                    }
                                }
                            }
                            if (selectLang != -1)
                                EventRouter.send(new EventAgreementsLoad(agreement.translations[selectLang].title, agreement.translations[selectLang].description));
                        } else
                            EventRouter.send(new EventAgreementsDontLoad());
                    }

                    @Override
                    public void unSuccess(Response response) {
                        EventRouter.send(new EventAgreementsDontLoad());
                    }
                });
            } else
                AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        } else {
            LogUtils.logD("EventLogout", "UserControl/getAgreements");
            EventRouter.send(new EventLogout());
        }
    }

    public void updatePhone(String phone) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().updatePhone(App.getUser().id, new PhoneUserUpdate(phone)).enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().success) {
                                    App.updateUserPhone(response.body().items[0].phone);
                                    EventRouter.send(new EventCloseVerificationFragmentSteps());
                                } else {
                                    AppUtils.toastError((response.body().errors != null && response.body().errors.message != null && !response.body().errors.message.isEmpty()) ? response.body().errors.message : "response unsuccess", false);

                                }
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), false);
                                } catch (Exception e) {
                                    AppUtils.toastError("response unsuccessful " + e.getMessage(), false);
                                }
                            }
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), false);
                            } catch (Exception e) {
                                AppUtils.toastError(e.getMessage(), false);
                            }
                            App.logout();
                            LogUtils.logD("AppMedbook", "updatePhone EventLogout");
                            EventRouter.send(new EventLogout());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        AppUtils.toastError("response onFailure " + t.getLocalizedMessage(), false);
                    }
                });
            } else {
                AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
            }
        } else
            EventRouter.send(new EventLogout());
    }
}
