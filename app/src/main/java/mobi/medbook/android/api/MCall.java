package mobi.medbook.android.api;

import org.json.JSONObject;

import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.events.EventLogout;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class MCall<T> implements Callback<T> {
    private final String TAG = "AppMedbook/mCall";
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
            if (response.isSuccessful()) {
                LogUtils.logD(TAG, "isSuccessful");
                if (response.body() instanceof MResponse)
                    if (((MResponse) response.body()).success) {
                        onSuccess(response);
                    } else {
                        unSuccess(response);

                    }
            } else {     LogUtils.logD("AppMedbook/mCall", "point 0");
                unSuccess(response);
                onSuccesful();
                LogUtils.logD(TAG, "unSuccesful");
            }
        } else {
            LogUtils.logD(TAG, "response.code() = " + response.code());
            AppUtils.toastError(Core.get().LocalizationControl().getText(R.id.error_401), true);
            App.logout();
            LogUtils.logD("AppMedbook", "MCall EventLogout");
            EventRouter.send(new EventLogout());
            onLogout();
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        AppUtils.toastError(t.getLocalizedMessage(), true);
        onFailure();
    }

    public void unSuccess(Response<T> response) {
        if (response.body() instanceof MResponse)
            if (((MResponse) response.body()).errors.message != null && !((MResponse) response.body()).errors.message.isEmpty())
                AppUtils.toastError(((MResponse) response.body()).errors.message, true);
            else
                onErrorBody(response);
        else
            onErrorBody(response);
    }

    private void onErrorBody(Response<T> response) {
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            AppUtils.toastError(jObjError.getJSONObject("errors").getString("message"), true);
        } catch (Exception e) {
            AppUtils.toastError(e.getMessage(), true);
        }
    }

    public String getMessageError(Response<T> response){
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            return jObjError.getJSONObject("errors").getString("message");
        } catch (Exception e) {
            return "Unknown error " + response.raw();
        }
    }

    public abstract void onSuccess(Response<T> response);


    public void onSuccesful() {
    }

    public void unSuccess() {
    }

    public void onLogout() {
    }

    public void onFailure() {
    }
}
