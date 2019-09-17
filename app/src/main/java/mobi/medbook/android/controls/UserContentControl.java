package mobi.medbook.android.controls;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.vince.easysave.EasySave;
import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.R;
import mobi.medbook.android.api.ApiRest;
import mobi.medbook.android.api.MCall;
import mobi.medbook.android.events.EventLogout;
import mobi.medbook.android.events.core.ConcurrentArrayList;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.materials.EventMaterialSelectedUpdate;
import mobi.medbook.android.events.materials.EventOnSendResultPresentationFailure;
import mobi.medbook.android.events.materials.EventOnSendResultPresentationSuccess;
import mobi.medbook.android.events.materials.EventTestResultLoad;
import mobi.medbook.android.events.materials.EventUserNotificationReaction;
import mobi.medbook.android.events.user_content.EventMaterialTranlateLoad;
import mobi.medbook.android.events.user_content.EventMaterialsLoad;
import mobi.medbook.android.types.RequestTest;
import mobi.medbook.android.types.materials.Material;
import mobi.medbook.android.types.requests.RequestResultTime;
import mobi.medbook.android.types.responses.MaterialTranslateResponse;
import mobi.medbook.android.types.responses.MaterialsResponse;
import mobi.medbook.android.types.responses.PresentationResultResponse;
import mobi.medbook.android.types.responses.TestResultsResponse;
import mobi.medbook.android.types.responses.UserNotificationReactionResponse;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class UserContentControl {

    private String TAG = "AppMedbook/UserContentControl";
    private ArrayList<Material> materials = new ArrayList<>();
    private Material selectedMaterial = new Material();

    public ArrayList<Material> getListMaterial() {
        return materials;
    }

    public Material getSelectedMaterial() {
        return selectedMaterial;
    }

    public void setSelectedMaterial(Material selectedMaterial) {
        this.selectedMaterial = selectedMaterial;
    }


    public void clearData() {
        new EasySave(App.getAppContext()).saveList("materials.data", null);
        materials = null;
        LogUtils.logD("ghhgjghj", "clearData");
        selectedMaterial = null;
    }

    public void getMaterials() {
        List<Material> cashMaterials = new EasySave(App.getAppContext()).retrieveList("materials.data", Material[].class);
        if (materials == null || materials.isEmpty()) {
            materials = new ArrayList<>();
            materials.addAll(cashMaterials);
        }

        LogUtils.logD(TAG, "START getMaterials");
        if (!App.isUpdateMaterials()) {

        } else {
            if (Core.get().AuthorizationControl().updateAccessToken()) {
                if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                    App.setUpdateLastTimeMaterials();
                    ApiRest.PointAccess().getMaterials().enqueue(new Callback<MaterialsResponse>() {
                        @Override
                        public void onResponse(Call<MaterialsResponse> call, Response<MaterialsResponse> response) {
                            if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null && response.body().success) {
                                        //if (response.body().items.length > 0) {
                                        LogUtils.logD(TAG, "response.body().items.length = " + response.body().items.length);

                                        if (materials == null)
                                            materials = new ArrayList<>();
                                        else materials.clear();

                                        materials.addAll(Arrays.asList(response.body().items));

                                        LogUtils.logD(TAG, "materials.size() = " + materials.size());
                                        if (selectedMaterial != null) {
                                            for (Material item : materials) {
                                                if (item.id == selectedMaterial.id) {
                                                    selectedMaterial = item;
                                                    EventRouter.send(new EventMaterialSelectedUpdate());
                                                }
                                            }
                                        }

                                        new EasySave(App.getAppContext()).saveList("materials.data", materials);
                                        LogUtils.logD(TAG, "load true");
                                        EventRouter.send(new EventMaterialsLoad(true, null));
                                        LogUtils.logD(TAG, "materials.size() = " + materials.size());

                                        //} else {
                                        //   App.clearUpdateLastTimeMaterials();
                                        //EventRouter.send(new EventMaterialsLoad(false, App.getAppContext().getString(R.string.no_materials)));
                                        //}
                                    } else {
                                        App.clearUpdateLastTimeMaterials();
                                        if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
                                            EventRouter.send(new EventMaterialsLoad(false, response.body().errors.message));
                                        }
                                    }
                                } else {
                                    App.clearUpdateLastTimeMaterials();
                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        //LogUtils.logD("hfghfjgfj", "NOT Successful = " + jObjError.getJSONObject("errors").getInt("code"));
                                        EventRouter.send(new EventMaterialsLoad(false, jObjError.getJSONObject("errors").getString("message")));
                                    } catch (Exception e) {
                                        EventRouter.send(new EventMaterialsLoad(false, e.getMessage()));
                                    }
                                }
                            } else {
                                AppUtils.toastError(App.getAppContext().getString(R.string.message_401), false);
                                App.logout();
                                LogUtils.logD("AppMedbook", "getMaterials EventLogout");
                                EventRouter.send(new EventLogout());
                            }
                        }

                        @Override
                        public void onFailure(Call<MaterialsResponse> call, Throwable t) {
                            App.clearUpdateLastTimeMaterials();
                            EventRouter.send(new EventMaterialsLoad(false, t.getLocalizedMessage()));
                        }
                    });
                } else {
                    App.clearUpdateLastTimeMaterials();
                    //AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
                    //EventRouter.send(new EventMaterialsLoad(false, App.getAppContext().getString(R.string.no_internet_connection)));
                }
            } else {
                App.clearUpdateLastTimeMaterials();
                EventRouter.send(new EventLogout());
            }
        }

    }

    //for load description material
    public void getMaterialTranslate(int productTranslationId) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getMaterialTranslate(productTranslationId).enqueue(new Callback<MaterialTranslateResponse>() {
                    @Override
                    public void onResponse(Call<MaterialTranslateResponse> call, Response<MaterialTranslateResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().success) {

                                EventRouter.send(new EventMaterialTranlateLoad(true, null, response.body().items[0]));
                            } else {
                                if (response.body().errors.message != null && !response.body().errors.message.isEmpty()) {
                                    EventRouter.send(new EventMaterialTranlateLoad(false, response.body().errors.message, null));
                                }
                            }
                        } else {
                            try {
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                EventRouter.send(new EventMaterialTranlateLoad(false, jObjError.getJSONObject("errors").getString("message"), null));
                            } catch (Exception e) {
                                EventRouter.send(new EventMaterialTranlateLoad(false, e.getMessage(), null));
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MaterialTranslateResponse> call, Throwable t) {
                        EventRouter.send(new EventMaterialTranlateLoad(false, t.getLocalizedMessage(), null));
                    }
                });
            } else {
                //AppUtils.toastError(App.getAppContext().getString(R.string.no_internet_connection), false);
                EventRouter.send(new EventMaterialTranlateLoad(false, App.getAppContext().getString(R.string.no_internet_connection), null));
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void sendTimeResultVideo() {
        if (AppUtils.isNetworkAvailable(App.getAppContext())) {
        } else {
        }
    }

    public void sendTimeResultPresentation(int materialId, int id, long resultTime) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                RequestResultTime requestResultTime = new RequestResultTime();
                requestResultTime.result_time = resultTime;
                ApiRest.PointAccess().sendTimeResultPresentation(id, requestResultTime).enqueue(new Callback<PresentationResultResponse>() {
                    @Override
                    public void onResponse(Call<PresentationResultResponse> call, Response<PresentationResultResponse> response) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {
                            if (response.isSuccessful()) {
                                if (response.body().success) {
//                                LogUtils.logD("D/OkHttp: ","materialId = " + materialId);
//                                LogUtils.logD("D/OkHttp: ","id = " + id);
//                                for (int i = 0; i < materials.size(); i++) {
//                                    if (materials.get(i).id == materialId) {
//                                        for(int j = 0; j < materials.get(i).presentations.length; j++){
//                                            if(materials.get(i).presentations[j].id == id){
//                                                LogUtils.logD("D/OkHttp: ","sendTimeResultPresentation SET");
//                                                selectedMaterial.presentations[j].result_time = String.valueOf(resultTime);
//                                                materials.get(i).presentations[j].result_time = String.valueOf(resultTime);
//                                            }
//                                        }
//                                    }
//                                }
                                    App.clearUpdateLastTimeMaterials();
                                    getMaterials();
                                    Core.get().UserControl().getUser();
                                    EventRouter.send(new EventOnSendResultPresentationSuccess(materialId, id, String.valueOf(resultTime)));
                                } else {
                                    //error(unsuccess)
                                    if (response.body().errors.message != null && !response.body().errors.message.isEmpty())
                                        EventRouter.send(new EventOnSendResultPresentationFailure(response.body().errors.message));
                                }
                            } else {
                                //error(unSuccessful)
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    EventRouter.send(new EventOnSendResultPresentationFailure(jObjError.getJSONObject("errors").getString("message")));
                                } catch (Exception e) {
                                    EventRouter.send(new EventOnSendResultPresentationFailure(e.getMessage()));
                                }
                            }
                        } else {
                            //error 401 501
                            AppUtils.toastError(App.getAppContext().getString(R.string.message_401), false);
                            App.logout();
                            LogUtils.logD("AppMedbook", "sendTimeResultPresentation EventLogout");
                            EventRouter.send(new EventLogout());
                        }
                    }

                    @Override
                    public void onFailure(Call<PresentationResultResponse> call, Throwable t) {
                        ////error failure
                        EventRouter.send(new EventOnSendResultPresentationFailure(t.getLocalizedMessage()));
                    }
                });
            } else {
                //error no Internet
                EventRouter.send(new EventOnSendResultPresentationFailure(App.getAppContext().getString(R.string.no_internet_connection)));
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void sendTestResult(ConcurrentArrayList<RequestTest>/*RequestTest[]*/ itemsList) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                RequestTest[] items = new RequestTest[itemsList.size()];
                for (int i = 0; i < itemsList.size(); i++) {
                    items[i] = new RequestTest();
                    items[i].user_test_content_id = itemsList.get(i).user_test_content_id;
                    items[i].test_question_answer_id = itemsList.get(i).test_question_answer_id;
                    items[i].test_question_id = itemsList.get(i).test_question_id;
                    items[i].product_id = itemsList.get(i).product_id;
                    items[i].open_answer_text = itemsList.get(i).open_answer_text;
                }
                ApiRest.PointAccess().sendTestResult(items).enqueue(new Callback<TestResultsResponse>() {
                    @Override
                    public void onResponse(Call<TestResultsResponse> call, Response<TestResultsResponse> response) {
                        if (response.code() != ApiRest.CODE_401 && response.code() != ApiRest.CODE_521) {

                            if (response.isSuccessful()) {

                                if (response.body().success) {
                                    LogUtils.logD(TAG, "correctAnswers = " + response.body().resultsCheck.correctAnswers);
                                    LogUtils.logD(TAG, "questions = " + response.body().resultsCheck.questions);
                                    LogUtils.logD(TAG, "PointsEarned = " + response.body().resultsCheck.pointsEarned);
                                    LogUtils.logD(TAG, "balance = " + response.body().resultsCheck.balance);

                                    EventRouter.send(new EventTestResultLoad(null, response.body().resultsCheck));
                                    Core.get().UserControl().getUser();
                                    App.clearUpdateLastTimeMaterials();
                                    getMaterials();
                                } else {
                                    EventRouter.send(new EventTestResultLoad(response.body().errors.message, null));
                                }
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    EventRouter.send(new EventTestResultLoad(jObjError.getJSONObject("errors").getString("message"), null));
                                } catch (Exception e) {
                                    EventRouter.send(new EventTestResultLoad(e.getMessage(), null));
                                }
                            }

                        } else {
                            EventRouter.send(new EventTestResultLoad(App.getAppContext().getString(R.string.message_401), null));
                            App.logout();
                            LogUtils.logD("AppMedbook", "sendTestResult EventLogout");
                            EventRouter.send(new EventLogout());
                        }
                    }

                    @Override
                    public void onFailure(Call<TestResultsResponse> call, Throwable t) {
                        EventRouter.send(new EventTestResultLoad(t.getLocalizedMessage(), null));
                    }
                });

            } else {
                EventRouter.send(new EventTestResultLoad(App.getAppContext().getString(R.string.no_internet_connection), null));
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void userNotificationReaction() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().userNotificationReaction().enqueue(new MCall<UserNotificationReactionResponse>() {
                    @Override
                    public void onSuccess(Response<UserNotificationReactionResponse> response) {
                        if (response != null && response.body() != null && response.body().items != null && response.body().items.length > 0)
                            EventRouter.send(new EventUserNotificationReaction(response.body().items[0].reactions));
                        else {

                        }
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }
}
