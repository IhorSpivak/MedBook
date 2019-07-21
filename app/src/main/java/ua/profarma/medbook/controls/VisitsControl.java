package ua.profarma.medbook.controls;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import br.vince.easysave.EasySave;
import retrofit2.Call;
import retrofit2.Response;
import ua.profarma.medbook.App;
import ua.profarma.medbook.Core;
import ua.profarma.medbook.R;
import ua.profarma.medbook.api.ApiRest;
import ua.profarma.medbook.api.MCall;
import ua.profarma.medbook.events.EventLogout;
import ua.profarma.medbook.events.core.EventRouter;
import ua.profarma.medbook.events.visits.EventDontDownLoadMpAnketa;
import ua.profarma.medbook.events.visits.EventLoadDashBoardVisit;
import ua.profarma.medbook.events.visits.EventStartQRCode;
import ua.profarma.medbook.events.visits.EventStartQRScanner;
import ua.profarma.medbook.events.visits.EventUpdateQRCode;
import ua.profarma.medbook.events.visits.EventUserRelationVisitsLoad;
import ua.profarma.medbook.events.visits.EventUserVisitQuestionnaireDoctorLoad;
import ua.profarma.medbook.events.visits.EventUserVisitQuestionnaireMPLoad;
import ua.profarma.medbook.events.visits.EventUserVisitQuestionnaireUpdate;
import ua.profarma.medbook.events.visits.EventUserVisitsLoad;
import ua.profarma.medbook.events.visits.EventVisitStartFailed;
import ua.profarma.medbook.events.visits.EventVisitStarting;
import ua.profarma.medbook.types.requests.ChangeTimeVisitRequest;
import ua.profarma.medbook.types.requests.CreateUserVisitRequest;
import ua.profarma.medbook.types.requests.ResultMPAncetaRequest;
import ua.profarma.medbook.types.requests.StartVisitRequest;
import ua.profarma.medbook.types.responses.ChangeTimeVisitResponse;
import ua.profarma.medbook.types.responses.CreateUserVisitResponse;
import ua.profarma.medbook.types.responses.DoctorAncetaResultResponse;
import ua.profarma.medbook.types.responses.GetUserVisitQuestionnaireDoctorResponse;
import ua.profarma.medbook.types.responses.GetUserVisitQuestionnaireMPResponse;
import ua.profarma.medbook.types.responses.MedPredResultResponse;
import ua.profarma.medbook.types.responses.PushNotificationBeforeStartVisitResponse;
import ua.profarma.medbook.types.responses.StartVisitResponse;
import ua.profarma.medbook.types.responses.UpdateStatusVisitResponse;
import ua.profarma.medbook.types.responses.UserDashboardVisitsResponse;
import ua.profarma.medbook.types.responses.UserRelationVisitsResponse;
import ua.profarma.medbook.types.responses.UserVisitsResponse;
import ua.profarma.medbook.types.responses.VisitEndResponse;
import ua.profarma.medbook.types.visits.AnswerDoctorResult;
import ua.profarma.medbook.types.visits.AnswerVisit;
import ua.profarma.medbook.types.visits.CacheMpAncet;
import ua.profarma.medbook.types.visits.Product;
import ua.profarma.medbook.types.visits.Promo;
import ua.profarma.medbook.types.visits.UserRelationItem;
import ua.profarma.medbook.types.visits.UserVisit;
import ua.profarma.medbook.types.visits.UserVisitQuestionnaire;
import ua.profarma.medbook.types.visits.UserVisitQuestionnaireDoctor;
import ua.profarma.medbook.utils.AppUtils;
import ua.profarma.medbook.utils.LogUtils;

public class VisitsControl {

    private String TAG = "VisitsControl";

    private final String KEY_CASH_MP_ANCETS = "mp_ancets.data";


    private CopyOnWriteArrayList<CacheMpAncet> mpAncets;

    private AnswerDoctorResult[] resultsDoctor;

    private UserRelationItem[] memberVisitItems;
    private UserVisit[] userVisitItems;
    private UserVisit[] nearest;
    private UserVisit[] unaccepted;
    private UserVisitQuestionnaire userVisitQuestionnaireMP;
    private int resultIdUserVisitQuestionnaireMP = -1;
    private UserVisitQuestionnaireDoctor userVisitQuestionnaireDoctor;


    public UserVisit[] getUnaccepted() {
        return unaccepted;
    }

    public UserVisit[] getNearest() {
        return nearest;
    }

    public UserVisitQuestionnaireDoctor getUserVisitQuestionnaireDoctor() {
        return userVisitQuestionnaireDoctor;
    }

    public UserRelationItem[] getMemberVisitItems() {
        return memberVisitItems;
    }

    public UserVisitQuestionnaire getUserVisitQuestionnaireMP() {
        return userVisitQuestionnaireMP;
    }

    public UserVisit[] getUserVisitItems() {
        return userVisitItems;
    }

    public void getUsersRelationForVisits() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getUsersRelationForVisits().enqueue(new MCall<UserRelationVisitsResponse>() {
                    @Override
                    public void onSuccess(Response<UserRelationVisitsResponse> response) {
                        memberVisitItems = response.body().items;
                        EventRouter.send(new EventUserRelationVisitsLoad());
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void createUserVisit(CreateUserVisitRequest createUserVisitRequest) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().createUserVisit(createUserVisitRequest).enqueue(new MCall<CreateUserVisitResponse>() {
                    @Override
                    public void onSuccess(Response<CreateUserVisitResponse> response) {
                        getUserVisits();
                        AppUtils.toastOk(Core.get().LocalizationControl().getText(R.id.visits_create_ok), true);
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getUserVisits() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getUserVisits().enqueue(new MCall<UserVisitsResponse>() {
                    @Override
                    public void onSuccess(Response<UserVisitsResponse> response) {
                        userVisitItems = response.body().items;
                        EventRouter.send(new EventUserVisitsLoad());
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public UserVisit getUserVisitForId(int id) {
        if (userVisitItems == null || userVisitItems.length == 0) return null;
        else
            for (int i = 0; i < userVisitItems.length; i++) {
                if (userVisitItems[i].id == id)
                    return userVisitItems[i];
            }
        return null;
    }

    public void visitAccept(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().visitAccept(id).enqueue(new MCall<UpdateStatusVisitResponse>() {
                    @Override
                    public void onSuccess(Response<UpdateStatusVisitResponse> response) {
                        if (response.body() != null && response.body().items.length == 1) {
                            getUserVisitForId(id).accepted_at = response.body().items[0].accepted_at;
                            getUserVisitForId(id).time_from = response.body().items[0].time_from;
                            getUserVisitForId(id).time_to = response.body().items[0].time_to;
                            EventRouter.send(new EventUserVisitsLoad());
                            getDashboardVisits();
                        }
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }


    public void pushNotificationBeforeStartVisit(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().pushNotificationBeforeStartVisit(id).enqueue(new MCall<PushNotificationBeforeStartVisitResponse>() {
                    @Override
                    public void onSuccess(Response<PushNotificationBeforeStartVisitResponse> response) {

                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void visitDecline(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().visitDecline(id).enqueue(new MCall<UpdateStatusVisitResponse>() {
                    @Override
                    public void onSuccess(Response<UpdateStatusVisitResponse> response) {
                        getUserVisits();
                        getDashboardVisits();
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void startVisit(int id, boolean updateCode) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().startVisit(id).enqueue(new MCall<StartVisitResponse>() {
                    @Override
                    public void onSuccess(Response<StartVisitResponse> response) {
                        if (response != null && response.body() != null && response.body().items[0].token != null && !response.body().items[0].token.isEmpty()) {
                            if (updateCode) {
                                EventRouter.send(new EventUpdateQRCode(response.body().items[0].token, response.body().items[0].expires_at));
                            } else
                                EventRouter.send(new EventStartQRCode(response.body().items[0].token, response.body().items[0].expires_at));
                        } else {
                            if (response != null && response.body() != null && response.body().items[0].visit.started_at != null) {
                                //start visit doctor
                                //AppUtils.toastOk("START VISIT", false);
                                EventRouter.send(new EventVisitStarting(response.body().items[0].visit.started_at));
                            }
                        }
                    }

                    @Override
                    public void unSuccess(Response<StartVisitResponse> response) {
                        LogUtils.logD("AppMedbook/mCall", "startVisitMain/unSuccess");
                        if (response.code() == 404) {
                            String message;
                            if (response.body() == null) {
                                message = getMessageError(response);
                            } else {
                                message = response.body().errors.message;
                            }
                            EventRouter.send(new EventVisitStartFailed(message));
                        } else if (App.getUser().specialization.is_medpred == 1)
                            EventRouter.send(new EventStartQRScanner());
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void startVisit(int id, String qrCode) {
        LogUtils.logD("startVisit", "qrCode = " + qrCode);
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().startVisitQR(id, new StartVisitRequest(qrCode)).enqueue(new MCall<StartVisitResponse>() {
                    @Override
                    public void onSuccess(Response<StartVisitResponse> response) {
                        if (response != null && response.body() != null && response.body().items[0].visit.started_at != null) {
                            //start visit mp
                            //AppUtils.toastOk("START VISIT", false);
                            EventRouter.send(new EventVisitStarting(response.body().items[0].visit.started_at));
                        }
                    }

                    @Override
                    public void unSuccess(Response<StartVisitResponse> response) {
                        String message;
                        if (response.body() == null) {
                            message = getMessageError(response);
                        } else {
                            message = response.body().errors.message;
                        }
                        LogUtils.logD("hfr6yughbjjhj", "message = " + message);
                        EventRouter.send(new EventVisitStartFailed(message));
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getUserVisitQuestionnaireMP(int id) {
        List<CacheMpAncet> cashMPAncets = new EasySave(App.getAppContext()).retrieveList(KEY_CASH_MP_ANCETS, CacheMpAncet[].class);

        if (mpAncets == null || mpAncets.isEmpty()) {
            mpAncets = new CopyOnWriteArrayList<>(cashMPAncets);
        }

        //mpAncets.clear();
        for (CacheMpAncet item : mpAncets) {
            if (System.currentTimeMillis() - item.date > 86400000) mpAncets.remove(item);
        }

        if (mpAncets == null || mpAncets.isEmpty()) {
            getUserVisitQuestionnaireMPFromServer(id);
        } else {
            boolean flagResponse = true;
            for (CacheMpAncet item : mpAncets) {
                if (item.idVisit == id) {
                    flagResponse = false;
                    userVisitQuestionnaireMP = item.userVisitQuestionnaire;
                    resultIdUserVisitQuestionnaireMP = item.userVisitQuestionnaire.result_id;
                    mpAncets.remove(item);
                    EventRouter.send(new EventUserVisitQuestionnaireMPLoad(userVisitQuestionnaireMP));

                }
            }
            if (flagResponse) getUserVisitQuestionnaireMPFromServer(id);
        }
    }

    private void getUserVisitQuestionnaireMPFromServer(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getUserVisitQuestionnaireMP(id).enqueue(new MCall<GetUserVisitQuestionnaireMPResponse>() {
                    @Override
                    public void onSuccess(Response<GetUserVisitQuestionnaireMPResponse> response) {
                        userVisitQuestionnaireMP = response.body().items[0];
                        resultIdUserVisitQuestionnaireMP = response.body().items[0].result_id;
                        EventRouter.send(new EventUserVisitQuestionnaireMPLoad(response.body().items[0]));
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getUserVisitQuestionnaireDoctor(int id, int visit_id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getUserVisitQuestionnaireDoctor(id).enqueue(new MCall<GetUserVisitQuestionnaireDoctorResponse>() {
                    @Override
                    public void onSuccess(Response<GetUserVisitQuestionnaireDoctorResponse> response) {
                        if (response != null && response.body() != null && response.body().items.length > 0) {
                            userVisitQuestionnaireDoctor = response.body().items[0];

                            resultsDoctor = new AnswerDoctorResult[userVisitQuestionnaireDoctor.data.length];
                            for (int i = 0; i < userVisitQuestionnaireDoctor.data.length; i++) {
                                resultsDoctor[i] = new AnswerDoctorResult();
                                resultsDoctor[i].user_id = App.getUser().id;
                                resultsDoctor[i].visit_id = visit_id;
                                resultsDoctor[i].visit_result_question_id = userVisitQuestionnaireDoctor.data[i].id;
                                resultsDoctor[i].visit_result_question_answer_id = -1;
                            }
                            EventRouter.send(new EventUserVisitQuestionnaireDoctorLoad());
                        }
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void visitMedPredResult(int id, int success) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ResultMPAncetaRequest body = new ResultMPAncetaRequest();
                body.successful_visit = success;
                Gson gson = new Gson();
                if (userVisitQuestionnaireMP != null && userVisitQuestionnaireMP.data != null) {
                    body.user_json = gson.toJson(userVisitQuestionnaireMP.data);
                    ApiRest.PointAccess().visitMedPredResult(resultIdUserVisitQuestionnaireMP, body).enqueue(new MCall<MedPredResultResponse>() {
                        @Override
                        public void onSuccess(Response<MedPredResultResponse> response) {
                            visitEnd(id);
                        }
                    });
                } else {
                    EventRouter.send(new EventDontDownLoadMpAnketa());
                }
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void visitDoctorResult(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().visitDoctorResult(resultsDoctor).enqueue(new MCall<DoctorAncetaResultResponse>() {
                    @Override
                    public void onSuccess(Response<DoctorAncetaResultResponse> response) {
                        visitEnd(id);
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    private void visitEnd(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().visitEnd(id).enqueue(new MCall<VisitEndResponse>() {
                    @Override
                    public void onSuccess(Response<VisitEndResponse> response) {
                        getUserVisits();
                        getDashboardVisits();
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }


    public void changeVisitTime(int id, long time_from, int duration) {
        LogUtils.logD("vjghjghjghjm", "time_from = " + time_from + ", duration = " + duration);
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().changeTime(id, new ChangeTimeVisitRequest(time_from, (time_from + duration))).enqueue(new MCall<ChangeTimeVisitResponse>() {
                    @Override
                    public void onSuccess(Response<ChangeTimeVisitResponse> response) {
                        getUserVisits();
                        getDashboardVisits();
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void setResultDoctorAnswer(AnswerVisit answer) {
        for (int i = 0; i < resultsDoctor.length; i++)
            if (resultsDoctor[i].visit_result_question_id == answer.visit_result_question_id)
                resultsDoctor[i].visit_result_question_answer_id = answer.id;
    }


    public void setProductResultMP(Product product) {
        for (int i = 0; i < userVisitQuestionnaireMP.data.productsArr.length; i++)
            if (userVisitQuestionnaireMP.data.productsArr[i].productId.equals(product.productId)) {
                LogUtils.logD(TAG, "productId = " + userVisitQuestionnaireMP.data.productsArr[i].productId);
                LogUtils.logD(TAG, "productName = " + userVisitQuestionnaireMP.data.productsArr[i].productName);
                LogUtils.logD(TAG, "productType = " + userVisitQuestionnaireMP.data.productsArr[i].productType);
                LogUtils.logD(TAG, "newPlanRec = " + userVisitQuestionnaireMP.data.productsArr[i].newPlanRec);
                LogUtils.logD(TAG, "lastPlanRec = " + userVisitQuestionnaireMP.data.productsArr[i].lastPlanRec);
                LogUtils.logD(TAG, "factRec = " + userVisitQuestionnaireMP.data.productsArr[i].factRec);
                LogUtils.logD(TAG, "note = " + userVisitQuestionnaireMP.data.productsArr[i].note);
                userVisitQuestionnaireMP.data.productsArr[i] = product;
                LogUtils.logD(TAG, "NEW productId = " + userVisitQuestionnaireMP.data.productsArr[i].productId);
                LogUtils.logD(TAG, "NEW productName = " + userVisitQuestionnaireMP.data.productsArr[i].productName);
                LogUtils.logD(TAG, "NEW productType = " + userVisitQuestionnaireMP.data.productsArr[i].productType);
                LogUtils.logD(TAG, "NEW newPlanRec = " + userVisitQuestionnaireMP.data.productsArr[i].newPlanRec);
                LogUtils.logD(TAG, "NEW lastPlanRec = " + userVisitQuestionnaireMP.data.productsArr[i].lastPlanRec);
                LogUtils.logD(TAG, "NEW factRec = " + userVisitQuestionnaireMP.data.productsArr[i].factRec);
                LogUtils.logD(TAG, "NEW note = " + userVisitQuestionnaireMP.data.productsArr[i].note);
                EventRouter.send(new EventUserVisitQuestionnaireUpdate(userVisitQuestionnaireMP.data.productsArr[i]));
            }
    }

    public void setPromo(Promo promo) {
        for (int i = 0; i < userVisitQuestionnaireMP.data.productsArr.length; i++)
            if (userVisitQuestionnaireMP.data.promoArr[i].seria.equals(promo.seria)) {
                LogUtils.logD(TAG, "seria = " + userVisitQuestionnaireMP.data.promoArr[i].seria);
                LogUtils.logD(TAG, "code = " + userVisitQuestionnaireMP.data.promoArr[i].code);
                LogUtils.logD(TAG, "count = " + userVisitQuestionnaireMP.data.promoArr[i].count);
                LogUtils.logD(TAG, "issuedQty = " + userVisitQuestionnaireMP.data.promoArr[i].issuedQty);
                LogUtils.logD(TAG, "docIdStock = " + userVisitQuestionnaireMP.data.promoArr[i].docIdStock);
                LogUtils.logD(TAG, "name = " + userVisitQuestionnaireMP.data.promoArr[i].name);
                userVisitQuestionnaireMP.data.promoArr[i] = promo;
                LogUtils.logD(TAG, "NEW seria = " + userVisitQuestionnaireMP.data.promoArr[i].seria);
                LogUtils.logD(TAG, "NEW code = " + userVisitQuestionnaireMP.data.promoArr[i].code);
                LogUtils.logD(TAG, "NEW count = " + userVisitQuestionnaireMP.data.promoArr[i].count);
                LogUtils.logD(TAG, "NEW issuedQty = " + userVisitQuestionnaireMP.data.promoArr[i].issuedQty);
                LogUtils.logD(TAG, "NEW docIdStock = " + userVisitQuestionnaireMP.data.promoArr[i].docIdStock);
                LogUtils.logD(TAG, "NEW name = " + userVisitQuestionnaireMP.data.promoArr[i].name);
            }
    }


    public boolean isStartSendDoctorAnceta() {
        for (int i = 0; i < resultsDoctor.length; i++)
            if (resultsDoctor[i].visit_result_question_answer_id == -1)
                return false;
        return true;
    }

    public boolean isStartSendMPAnceta() {
        if (userVisitQuestionnaireMP.data.patientFlow < 1) return false;
        if (userVisitQuestionnaireMP.data.productsArr == null) return true;
        for (int i = 0; i < userVisitQuestionnaireMP.data.productsArr.length; i++) {
            Product productItem = userVisitQuestionnaireMP.data.productsArr[i];
            switch (productItem.productType) {
                case 1:
                    if (productItem.newPlanRec < 1 && productItem.impossible == 0) return false;
                    break;
                case 2:
                    if (productItem.factRec < 1 && productItem.impossible == 0) return false;
                    break;
                case 3:
                    if (productItem.newPlanRec < 1 && productItem.impossible == 0) return false;
                    if (productItem.factRec < 1 && productItem.impossible == 0) return false;
                    break;
            }
        }
        return true;
    }

    public void getDashboardVisits() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getDashboardVisits().enqueue(new MCall<UserDashboardVisitsResponse>() {
                    @Override
                    public void onSuccess(Response<UserDashboardVisitsResponse> response) {
                        if (response != null && response.body() != null) {
                            nearest = response.body().items.nearest;
                            unaccepted = response.body().items.unaccepted;
                        } else {
                            nearest = null;
                            unaccepted = null;
                        }
                        EventRouter.send(new EventLoadDashBoardVisit());
                    }

                    //for hide error parsing... if not null object, else array
                    @Override
                    public void onFailure(Call<UserDashboardVisitsResponse> call, Throwable t) {
                    }

                    @Override
                    public void unSuccess(Response response) {
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void clearData() {
        userVisitItems = null;
        nearest = null;
        unaccepted = null;
    }

    public void saveMPAncet(int idVisit) {
        CacheMpAncet cashMpAncet = new CacheMpAncet();
        cashMpAncet.idVisit = idVisit;
        cashMpAncet.date = System.currentTimeMillis();
        cashMpAncet.userVisitQuestionnaire = userVisitQuestionnaireMP;

        if (mpAncets == null) mpAncets = new CopyOnWriteArrayList<>();
        mpAncets.add(cashMpAncet);

        if (mpAncets != null && !mpAncets.isEmpty())
            new EasySave(App.getAppContext()).saveList(KEY_CASH_MP_ANCETS, mpAncets);

        //userVisitQuestionnaireMP = null;
    }
}
