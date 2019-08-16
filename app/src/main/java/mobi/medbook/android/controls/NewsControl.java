package mobi.medbook.android.controls;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.vince.easysave.EasySave;
import mobi.medbook.android.App;
import mobi.medbook.android.Core;
import mobi.medbook.android.api.ApiRest;
import mobi.medbook.android.api.MCall;
import mobi.medbook.android.events.EventLogout;
import mobi.medbook.android.events.core.EventRouter;
import mobi.medbook.android.events.news.EventClinicalCaseLoad;
import mobi.medbook.android.events.news.EventDrugsLoad;
import mobi.medbook.android.events.news.EventGetIcods;
import mobi.medbook.android.events.news.EventImageLoadClinicCase;
import mobi.medbook.android.events.news.EventImageLoadClinicCaseStop;
import mobi.medbook.android.events.news.EventLikeUnlike;
import mobi.medbook.android.events.news.EventMyClinicalCasesLoad;
import mobi.medbook.android.events.news.EventNewsLoad;
import mobi.medbook.android.types.news.ImageLoadResponse;
import mobi.medbook.android.types.news.MedicalCaseItem;
import mobi.medbook.android.types.news.UserNews;
import mobi.medbook.android.types.requests.RequestMedicalCaseBody;
import mobi.medbook.android.types.responses.DrugsResponse;
import mobi.medbook.android.types.responses.IcodResponse;
import mobi.medbook.android.types.responses.LikeUnlikeResponse;
import mobi.medbook.android.types.responses.MedicalCaseBodyDeleteResponse;
import mobi.medbook.android.types.responses.MedicalCaseBodyResponse;
import mobi.medbook.android.types.responses.UserNewsResponse;
import mobi.medbook.android.utils.AppUtils;
import mobi.medbook.android.utils.LogUtils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;


public class NewsControl {

    private final String TAG = "AppMedBook/NewsControl";
    private ArrayList<UserNews> userNewsList;
    private ArrayList<MedicalCaseItem> clinicalCaseList;

    public ArrayList<UserNews> getUserNewsList() {
        return userNewsList;
    }

    public ArrayList<MedicalCaseItem> getClinicalCaseList() {
        return clinicalCaseList;
    }

    public void getUserNews() {
        List<UserNews> cashUserNews = new EasySave(App.getAppContext()).retrieveList("usernews.data", UserNews[].class);
        if (userNewsList == null || userNewsList.isEmpty()) {
            userNewsList = new ArrayList<>();
            userNewsList.addAll(cashUserNews);
        }
        LogUtils.logD(TAG, "START getUserNews");
        if (!App.isUpdateUserNews()) {

        } else {
            if (Core.get().AuthorizationControl().updateAccessToken()) {
                if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                    ApiRest.PointAccess().getUserNews().enqueue(new MCall<UserNewsResponse>() {
                        @Override
                        public void onSuccess(Response<UserNewsResponse> response) {

                            if (userNewsList == null)
                                userNewsList = new ArrayList<>();
                            else userNewsList.clear();

                            if (response != null && response.body() != null) {
                                userNewsList.addAll(Arrays.asList(response.body().items));
                                App.setUpdateUserNews();
                                EventRouter.send(new EventNewsLoad());
                                new EasySave(App.getAppContext()).saveList("usernews.data", userNewsList);
                            }
                        }
                    });
                } else {
                    //AppUtils.showNoInternetConnect();
                }
            } else
                EventRouter.send(new EventLogout());
        }
    }

    public void clearData() {
        userNewsList.clear();
        new EasySave(App.getAppContext()).saveList("usernews.data", userNewsList);
    }

    public void getIcodLevelRoot() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {

                ApiRest.PointAccess().getIcod().enqueue(new MCall<IcodResponse>() {
                    @Override
                    public void onSuccess(Response<IcodResponse> response) {
                        if (response != null && response.body() != null)
                            EventRouter.send(new EventGetIcods(0, response.body().items, 0));
                    }

                    @Override
                    public void unSuccess(Response response) {
                        //super.unSuccess(response);
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getIcod(int level, int owner_id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {

                ApiRest.PointAccess().getIcod(owner_id, 1000, "translations").enqueue(new MCall<IcodResponse>() {
                    @Override
                    public void onSuccess(Response<IcodResponse> response) {
                        if (response != null && response.body() != null)
                            EventRouter.send(new EventGetIcods(level, response.body().items, owner_id));
                    }

                    @Override
                    public void unSuccess(Response response) {
                        //super.unSuccess(response);
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getDrugs(String text) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {

                ApiRest.PointAccess().getDrugs(text, 150, 1).enqueue(new MCall<DrugsResponse>() {
                    @Override
                    public void onSuccess(Response<DrugsResponse> response) {
                        if (response != null && response.body() != null)
                            EventRouter.send(new EventDrugsLoad(response.body().items));
                    }

                    @Override
                    public void unSuccess(Response response) {
                        //super.unSuccess(response);
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void uploadImage(String filename, RequestBody requestFile) {


        MultipartBody.Part body =
                MultipartBody.Part.createFormData("imageFile", filename, requestFile);

        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().upload(body).enqueue(new MCall<ImageLoadResponse>() {
                    @Override
                    public void onSuccess(Response<ImageLoadResponse> response) {
                        if (response != null && response.body() != null && response.body().items != null
                                && response.body().items.length > 0 && response.body().items[0] != null &&
                                !response.body().items[0].isEmpty())
                            EventRouter.send(new EventImageLoadClinicCase(response.body().items[0]));
                        else
                            AppUtils.toastError("Не вдалося заватажити зображення або виникла непередбачувана помилка при його завантаженні", false);
                    }

                    @Override
                    public void unSuccess(Response response) {
                        super.unSuccess(response);
                        EventRouter.send(new EventImageLoadClinicCaseStop());
                    }

                    @Override
                    public void onFailure() {
                        super.onFailure();
                        EventRouter.send(new EventImageLoadClinicCaseStop());
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());

    }

    public void createMedicalCase(RequestMedicalCaseBody requestMedicalCaseBody) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().createMedicalCase(requestMedicalCaseBody).enqueue(new MCall<MedicalCaseBodyResponse>() {
                    @Override
                    public void onSuccess(Response<MedicalCaseBodyResponse> response) {
                        getMyMedicalCases();
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void editMedicalCase(int id, RequestMedicalCaseBody requestMedicalCaseBody) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().editMedicalCase(id, requestMedicalCaseBody).enqueue(new MCall<MedicalCaseBodyResponse>() {
                    @Override
                    public void onSuccess(Response<MedicalCaseBodyResponse> response) {
                        getMyMedicalCases();
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getMyMedicalCases() {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getMyMedicalCases(
                        App.getUser().id,
                        "newsClinicalCaseDrugs.drug,newsClinicalCaseImages,newsClinicalCaseIcods.icod.translations,newsClinicalCaseTargets,newsClinicalCaseStatus",
                        2500,
                        "-updated_at"
                ).enqueue(new MCall<MedicalCaseBodyResponse>() {
                    @Override
                    public void onSuccess(Response<MedicalCaseBodyResponse> response) {
                        if (response != null && response.body() != null && response.body().items != null && response.body().items.length > 0) {

                            if (clinicalCaseList == null)
                                clinicalCaseList = new ArrayList<>();
                            else
                                clinicalCaseList.clear();

                            for (int i = 0; i < response.body().items.length; i++)
                                clinicalCaseList.add(response.body().items[i]);
                            EventRouter.send(new EventMyClinicalCasesLoad(response.body().items));
                        }
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void getMedicalCase(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().getMedicalCase(
                        id,
                        "newsClinicalCaseDrugs.drug,newsClinicalCaseImages,newsClinicalCaseIcods.icod.translations,newsClinicalCaseTargets,newsClinicalCaseStatus,newsArticles.like,newsArticles.liked,newsArticles.comments_count"
                ).enqueue(new MCall<MedicalCaseBodyResponse>() {
                    @Override
                    public void onSuccess(Response<MedicalCaseBodyResponse> response) {
                        if (response != null && response.body() != null && response.body().items != null && response.body().items.length > 0) {
                            EventRouter.send(new EventClinicalCaseLoad(response.body().items[0]));
                        }
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void like_unlike(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().like_unlike(id).enqueue(new MCall<LikeUnlikeResponse>() {
                    @Override
                    public void onSuccess(Response<LikeUnlikeResponse> response) {
                        EventRouter.send(new EventLikeUnlike());
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }

    public void updateLike(int news_article_id, boolean stateLike, int countLike) {
        for (UserNews item : userNewsList) {
            if (item.news_article_id == news_article_id) {
                item.newsArticle.liked = stateLike ? 1 : 0;
                item.newsArticle.like = countLike;
            }
        }
    }

    public void deleteCC(int id) {
        if (Core.get().AuthorizationControl().updateAccessToken()) {
            if (AppUtils.isNetworkAvailable(App.getAppContext())) {
                ApiRest.PointAccess().deleteMedicalCase(id).enqueue(new MCall<MedicalCaseBodyDeleteResponse>() {
                    @Override
                    public void onSuccess(Response<MedicalCaseBodyDeleteResponse> response) {
                        getMyMedicalCases();
                    }
                });
            } else {
                AppUtils.showNoInternetConnect();
            }
        } else
            EventRouter.send(new EventLogout());
    }
}
