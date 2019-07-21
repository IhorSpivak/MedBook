package ua.profarma.medbook.controls;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ua.profarma.medbook.App;
import ua.profarma.medbook.BuildConfig;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.api.ApiRest;
import ua.profarma.medbook.events.EventGetDeviceError;
import ua.profarma.medbook.events.EventLogout;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.types.requests.DeviceRequest;
import ua.profarma.medbook.types.responses.DevicesResponse;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

public class DeviceControl {

//    public void getDevice() {
//        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
//
//            ApiRest.PointAccess().getDevice("Bearer " + App.getAccessToken()).enqueue(new Callback<MaterialsResponse>() {
//                @Override
//                public void onResponse(Call<MaterialsResponse> call, Response<MaterialsResponse> response) {
//                    if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
//                        if (response.isSuccessful()) {
//                            if (response.body().success) {
//
//
//                            } else {
//                                if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
//                                    EventRouter.send(new EventMaterialsLoad(false, response.body().errors.message));
//                                }
//                            }
//                        } else {
//                            try {
//                                JSONObject jObjError = new JSONObject(response.errorBody().string());
//                                EventRouter.send(new EventMaterialsLoad(false, jObjError.getJSONObject("errors").getString("message")));
//                            } catch (Exception e) {
//                                EventRouter.send(new EventMaterialsLoad(false, e.getMessage()));
//                            }
//                        }
//                    } else {
//                        AppUtils.toastError(App.getAppContext().getString(R.string.message_401), false);
//                        App.logout();
//                        EventRouter.send(new EventLogout());
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<MaterialsResponse> call, Throwable t) {
//                    EventRouter.send(new EventMaterialsLoad(false, t.getLocalizedMessage()));
//                }
//            });
//
//        } else {
//            EventRouter.send(new EventMaterialsLoad(false, App.getAppContext().getString(R.string.no_internet_connection)));
//        }
//    }

    public void getDevice(int userId) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getDevice().enqueue(new Callback<DevicesResponse>() {
                    @Override
                    public void onResponse(Call<DevicesResponse> call, Response<DevicesResponse> response) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            if (response.isSuccessful()) {
                                if (response.body().success) {
                                    if (response.body().items.length > 0) {
                                        String versionCode = String.valueOf(BuildConfig.VERSION_CODE);
                                        String versionName = BuildConfig.VERSION_NAME;
                                        String appVersion = versionName + "." + versionCode;
                                        if (response.body().items[0].push_token == null ||
                                                response.body().items[0].push_token.isEmpty() ||
                                                !response.body().items[0].push_token.equals(App.getPushToken()) ||
                                                response.body().items[0].application_version == null ||
                                                response.body().items[0].application_version.isEmpty() ||
                                                !response.body().items[0].application_version.equals(appVersion)) {

                                            updatePushToken(response.body().items[0].id, userId);
                                        }
                                    } else {
                                        newDevice(userId);
                                    }
                                } else {
                                    if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
                                        EventRouter.send(new EventGetDeviceError("getDevice(): " + response.body().errors.message));
                                    }
                                }
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    EventRouter.send(new EventGetDeviceError("getDevice(): " + jObjError.getJSONObject("errors").getString("message")));
                                } catch (Exception e) {
                                    EventRouter.send(new EventGetDeviceError("getDevice(): " + e.getMessage()));
                                }
                            }
                        } else {
                            AppUtils.toastError(App.getAppContext().getString(R.string.message_401), false);
                            App.logout();
                            LogUtils.logD("AppMedbook", "getDevice EventLogout");
                            EventRouter.send(new EventLogout());
                        }
                    }

                    @Override
                    public void onFailure(Call<DevicesResponse> call, Throwable t) {
                        EventRouter.send(new EventGetDeviceError("getDevice(): " + t.getLocalizedMessage()));
                    }
                });

            } else {
                EventRouter.send(new EventGetDeviceError(App.getAppContext().getString(R.string.no_internet_connection)));
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void newDevice(int userId) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                DeviceRequest newDeviceRequest = new DeviceRequest();
                newDeviceRequest.device_name = App.getmDeviceName();
                newDeviceRequest.imei = App.getImei();
                String versionCode = String.valueOf(BuildConfig.VERSION_CODE);
                String versionName = BuildConfig.VERSION_NAME;
                newDeviceRequest.application_version = versionName + "." + versionCode;
                newDeviceRequest.push_token = App.getPushToken();
                newDeviceRequest.user_id = userId;

                ApiRest.PointAccess().newDevice(newDeviceRequest).enqueue(new Callback<DevicesResponse>() {
                    @Override
                    public void onResponse(Call<DevicesResponse> call, Response<DevicesResponse> response) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            if (response.isSuccessful()) {
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    EventRouter.send(new EventGetDeviceError("newDevice(): " + jObjError.getJSONObject("errors").getString("message")));
                                } catch (Exception e) {
                                    EventRouter.send(new EventGetDeviceError("newDevice(): " + e.getMessage()));
                                }
                            }
                        } else {
                            AppUtils.toastError(App.getAppContext().getString(R.string.message_401), false);
                            App.logout();
                            LogUtils.logD("AppMedbook", "newDevice EventLogout");
                            EventRouter.send(new EventLogout());
                        }
                    }

                    @Override
                    public void onFailure(Call<DevicesResponse> call, Throwable t) {
                        EventRouter.send(new EventGetDeviceError("newDevice(): " + t.getLocalizedMessage()));
                    }
                });

            } else {
                EventRouter.send(new EventGetDeviceError(App.getAppContext().getString(R.string.no_internet_connection)));
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void updatePushToken(int deviceId, int userId) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                DeviceRequest newDeviceRequest = new DeviceRequest();
                newDeviceRequest.device_name = App.getmDeviceName();
                newDeviceRequest.imei = App.getImei();
                String versionCode = String.valueOf(BuildConfig.VERSION_CODE);
                String versionName = BuildConfig.VERSION_NAME;
                newDeviceRequest.application_version = versionName + "." + versionCode;
                newDeviceRequest.push_token = App.getPushToken();
                newDeviceRequest.user_id = userId;
                newDeviceRequest.id = deviceId;

//            DevicePushUpdateRequest devicePushUpdateRequest = new DevicePushUpdateRequest();
//            devicePushUpdateRequest.push_token = App.getPushToken();
                ApiRest.PointAccess().updatePushToken(deviceId, newDeviceRequest).enqueue(new Callback<DevicesResponse>() {
                    @Override
                    public void onResponse(Call<DevicesResponse> call, Response<DevicesResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<DevicesResponse> call, Throwable t) {

                    }
                });
            } else {
                EventRouter.send(new EventGetDeviceError(App.getAppContext().getString(R.string.no_internet_connection)));
            }
        } else
            EventRouter.send(new EventLogout());

    }
}
