package mobi.medbook.android.controls;


import android.os.AsyncTask;
import android.provider.Settings;

import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.api.ApiRest;
import mobi.medbook.android.api.ApiRestRefreshToken;
import mobi.medbook.android.events.authorization.EventLoginFailure;
import mobi.medbook.android.events.authorization.EventLoginSuccess;
import mobi.medbook.android.events.authorization.EventRegistrationSuccess;
import mobi.medbook.android.events.authorization.EventRegistrationUnSuccess;
import mobi.medbook.android.events.authorization.EventRestore;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.types.RegistrationInfo;
import mobi.medbook.android.types.requests.AccessToken;
import mobi.medbook.android.types.requests.AuthorizeRequest;
import mobi.medbook.android.types.requests.RefreshAccessTokenRequest;
import mobi.medbook.android.types.requests.Register;
import mobi.medbook.android.types.requests.RestoreRequest;
import mobi.medbook.android.types.responses.AccessTokenInfo;
import mobi.medbook.android.types.responses.AuthorizeInfo;
import mobi.medbook.android.types.responses.RestoreInfoRequest;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class AuthorizationControl {

    private String mEmail;
    private String mPassword;
    private String mFirstName;
    private String mMiddleName;
    private String mLastName;
    private int mSpecializationId;
    private int mMedicalInstitutionId;


    public void setEmail(String email) {
        mEmail = email;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setFirstName(String first_name) {
        mFirstName = first_name;
    }

    public void setMiddleName(String middle_name) {
        mMiddleName = middle_name;
    }

    public void setLastName(String last_name) {
        mLastName = last_name;
    }

    public void setSpecialization_id(int specialization_id) {
        mSpecializationId = specialization_id;
    }

    public void setMedical_institution_id(int medical_institution_id) {
        mMedicalInstitutionId = medical_institution_id;
    }

    public void register() {

        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            ApiRest.PointAccess().register(new Register(mEmail, mPassword, mFirstName, mMiddleName, mLastName, mSpecializationId, mMedicalInstitutionId)).enqueue(new Callback<RegistrationInfo>() {
                @Override
                public void onResponse(Call<RegistrationInfo> call, Response<RegistrationInfo> response) {
                    if (response.isSuccessful()) {
                        if (response.body().success) {
                            //AppUtils.toastOk(App.getAppContext().getString(R.string.check_email_to_confirm_your_registration), false);
                            EventRouter.send(new EventRegistrationSuccess());
                        } else {
                            if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
                                //AppUtils.toastError(response.body().errors.message, false);
                                EventRouter.send(new EventRegistrationUnSuccess(response.body().errors.message));
                            }
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            //AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), false);
                            String message = jObjError.getJSONObject("errors").getString("message");
                            EventRouter.send(new EventRegistrationUnSuccess(message));
                        } catch (Exception e) {
                            //AppUtils.toastError(e.getMessage(), false);
                            EventRouter.send(new EventRegistrationUnSuccess(e.getMessage()));
                        }
                        //AppUtils.toastError(response.errorBody(), false);
                    }
                }

                @Override
                public void onFailure(Call<RegistrationInfo> call, Throwable t) {
                    if (t.getLocalizedMessage() != null && !t.getLocalizedMessage().isEmpty())
                        //AppUtils.toastError(t.getLocalizedMessage(), false);
                        EventRouter.send(new EventRegistrationUnSuccess(t.getLocalizedMessage()));
                    else
                        //AppUtils.toastError(App.getAppContext().getString(R.string.throwable_registration), false);
                        EventRouter.send(new EventRegistrationUnSuccess(App.getAppContext().getString(R.string.throwable_registration)));
                }
            });
        } else {
            AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), true);
        }
    }

    public void authorize(String username, String password) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            String android_id = Settings.Secure.getString(App.getAppContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            ApiRestRefreshToken.PointAccess().authorize(new AuthorizeRequest(username, password, android_id)).enqueue(new Callback<AuthorizeInfo>() {
                @Override
                public void onResponse(Call<AuthorizeInfo> call, Response<AuthorizeInfo> response) {
                    if (response.isSuccessful()) {
                        if (response.body().success) {
                            LogUtils.logD("AuthorizationControl", "authorize success");
                            accessToken(response.body().items[0].authorization_code, response.body().items[0].user.id);
                        } else {
                            if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
                                AppUtils.toastError(response.body().errors.message, false);
                                LogUtils.logD("AuthorizationControl", "authorize unSuccess");
                                EventRouter.send(new EventLoginFailure());
                            }
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), false);
                        } catch (Exception e) {
                            AppUtils.toastError(e.getMessage(), false);
                        }
                        LogUtils.logD("AuthorizationControl", "authorize unSuccessful");
                        EventRouter.send(new EventLoginFailure());
                    }
                }

                @Override
                public void onFailure(Call<AuthorizeInfo> call, Throwable t) {
                    AppUtils.toastError(t.getLocalizedMessage(), false);
                    LogUtils.logD("AuthorizationControl", "authorize Failure");
                    EventRouter.send(new EventLoginFailure());
                }
            });
        } else {
            AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        }
    }

    public void accessToken(String authorization_code, int userId) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            ApiRestRefreshToken.PointAccess().accessToken(new AccessToken(authorization_code)).enqueue(new Callback<AccessTokenInfo>() {
                @Override
                public void onResponse(Call<AccessTokenInfo> call, Response<AccessTokenInfo> response) {
                    if (response.isSuccessful()) {
                        if (response.body().success) {
                            LogUtils.logD("AuthorizationControl", "accessToken success");
                            App.setAccessToken(response.body().items[0].access_token);
                            App.setRefreshToken(response.body().items[0].refresh_token);
                            App.setAccessTokenExpires(response.body().items[0].expires_at);
                            App.setRefreshTokenExpires(response.body().items[0].refresh_expires_at);
                            EventRouter.send(new EventLoginSuccess());
                            Core.get().DeviceControl().getDevice(userId);
                        } else {
                            if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
                                AppUtils.toastError(response.body().errors.message, false);
                                LogUtils.logD("AuthorizationControl", "accessToken unSuccess");
                                EventRouter.send(new EventLoginFailure());
                            }
                        }
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), false);
                        } catch (Exception e) {
                            AppUtils.toastError(e.getMessage(), false);
                        }
                        LogUtils.logD("AuthorizationControl", "accessToken unSuccessful");
                        EventRouter.send(new EventLoginFailure());
                    }
                }

                @Override
                public void onFailure(Call<AccessTokenInfo> call, Throwable t) {
                    AppUtils.toastError(t.getLocalizedMessage(), false);
                    LogUtils.logD("AuthorizationControl", "accessToken Failure");
                    EventRouter.send(new EventLoginFailure());
                }
            });
        } else {
            AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        }
    }

    public void restore(String email) {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            ApiRestRefreshToken.PointAccess().restore(new RestoreRequest(email)).enqueue(new Callback<RestoreInfoRequest>() {
                @Override
                public void onResponse(Call<RestoreInfoRequest> call, Response<RestoreInfoRequest> response) {
                    if (response.isSuccessful()) {
                        LogUtils.logD("hfghfghjfg", "Successful");
                        if (response.body().success) {
                            LogUtils.logD("hfghfghjfg", "success");
                            EventRouter.send(new EventRestore(true, null));
                        } else {
                            if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
                                LogUtils.logD("hfghfghjfg", "no success");
                                EventRouter.send(new EventRestore(false, response.body().errors.message));
                            }
                        }
                    } else {
                        LogUtils.logD("hfghfghjfg", "un Successful");
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            EventRouter.send(new EventRestore(false, jObjError.getJSONObject("errors").getString("message")));
                        } catch (Exception e) {
                            EventRouter.send(new EventRestore(false, e.getMessage()));
                        }
                        //EventRouter.send(new EventLoginFailure());
                        //EventRouter.send(new EventRestore(false, response.body().errors.message));
                    }
                }

                @Override
                public void onFailure(Call<RestoreInfoRequest> call, Throwable t) {
                    EventRouter.send(new EventRestore(false, t.getLocalizedMessage()));
                }
            });
        } else {
            AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
        }
    }

    public synchronized boolean updateAccessToken() {
        boolean result = true;
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
            if (App.getmAccessTokenExpires() <= (System.currentTimeMillis() / 1000)) {
                LogUtils.logD("D/OkHttp:", "-------------------------------------point 2");
                if (App.getRefreshToken() != null && !App.getRefreshToken().isEmpty()) {
                    LogUtils.logD("D/OkHttp:", "-------------------------------------point 3");
                    MyAsyncTask task = new MyAsyncTask();
                    task.execute();

                    try {
                        retrofit2.Response<AccessTokenInfo> accessTokenInfo = task.get();
                        if (accessTokenInfo != null && accessTokenInfo.isSuccessful() && accessTokenInfo.body() != null && accessTokenInfo.body().success) {
                            App.setAccessToken(accessTokenInfo.body().items[0].access_token);
                            App.setRefreshToken(accessTokenInfo.body().items[0].refresh_token);
                            App.setAccessTokenExpires(accessTokenInfo.body().items[0].expires_at);
                            App.setRefreshTokenExpires(accessTokenInfo.body().items[0].refresh_expires_at);
                            result = true;
                            LogUtils.logD("D/OkHttp:", "-------------------------------------OK");
                        } else {
                            result = false;
                            LogUtils.logD("D/OkHttp:", "-------------------------------------NOT OK");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        for (int i = 0; i < e.getStackTrace().length; i++)
                            LogUtils.logD("D/OkHttp:", e.getStackTrace()[i].toString());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        for (int i = 0; i < e.getStackTrace().length; i++)
                            LogUtils.logD("D/OkHttp:", e.getStackTrace()[i].toString());
                    }
                    LogUtils.logD("D/OkHttp:", "UP");
                } else result = false;
            } else result = true;
        }
        LogUtils.logD("D/OkHttp:", "result = " + result);
        return result;
    }

    private class MyAsyncTask extends AsyncTask<Void, Integer, retrofit2.Response<AccessTokenInfo>> {
        @Override
        protected retrofit2.Response<AccessTokenInfo> doInBackground(Void... voids) {
            try {
                return ApiRestRefreshToken.PointAccess().refreshAccessToken(new RefreshAccessTokenRequest(App.getRefreshToken())).execute();
            } catch (IOException e) {
                LogUtils.logD("D/OkHttp:", e.getLocalizedMessage());
                return null;
            }
        }
    }
}
