package mobi.medbook.android.api;

import java.util.List;

import mobi.medbook.android.models.NewReferenceResponse;
import mobi.medbook.android.models.NewRequestReference;
import mobi.medbook.android.types.MedicalInstitutionTranslate;
import mobi.medbook.android.types.NearestMedicalInstitutionItems;
import mobi.medbook.android.types.RegistrationInfo;
import mobi.medbook.android.types.RequestTest;
import mobi.medbook.android.types.SpecializationItems;
import mobi.medbook.android.types.news.ImageLoadResponse;
import mobi.medbook.android.types.requests.AccessToken;
import mobi.medbook.android.types.requests.AuthorizeRequest;
import mobi.medbook.android.types.requests.ChangeTimeVisitRequest;
import mobi.medbook.android.types.requests.CreateUserVisitRequest;
import mobi.medbook.android.types.requests.DeviceRequest;
import mobi.medbook.android.types.requests.NewCommentRequest;
import mobi.medbook.android.types.requests.PhoneUserUpdate;
import mobi.medbook.android.types.requests.PointsAgreementRequest;
import mobi.medbook.android.types.requests.ReactionNotificationRequest;
import mobi.medbook.android.types.requests.RefreshAccessTokenRequest;
import mobi.medbook.android.types.requests.Register;
import mobi.medbook.android.types.requests.RequestMedicalCaseBody;
import mobi.medbook.android.types.requests.RequestResultTime;
import mobi.medbook.android.types.requests.RestoreRequest;
import mobi.medbook.android.types.requests.ResultMPAncetaRequest;
import mobi.medbook.android.types.requests.StartVisitRequest;
import mobi.medbook.android.types.requests.TermsAgreementRequest;
import mobi.medbook.android.types.responses.AccessTokenInfo;
import mobi.medbook.android.types.responses.AgreementResponse;
import mobi.medbook.android.types.responses.AuthorizeInfo;
import mobi.medbook.android.types.responses.ChangeTimeVisitResponse;
import mobi.medbook.android.types.responses.CreateUserVisitResponse;
import mobi.medbook.android.types.responses.DeleteCommentResponse;
import mobi.medbook.android.types.responses.DevicesResponse;
import mobi.medbook.android.types.responses.DoctorAncetaResultResponse;
import mobi.medbook.android.types.responses.DrugsResponse;
import mobi.medbook.android.types.responses.GetUserVisitQuestionnaireDoctorResponse;
import mobi.medbook.android.types.responses.GetUserVisitQuestionnaireMPResponse;
import mobi.medbook.android.types.responses.HistoryExchangeResponse;
import mobi.medbook.android.types.responses.IcodResponse;
import mobi.medbook.android.types.responses.LikeUnlikeResponse;
import mobi.medbook.android.types.responses.MaterialTranslateResponse;
import mobi.medbook.android.types.responses.MaterialsResponse;
import mobi.medbook.android.types.responses.MedPredResultResponse;
import mobi.medbook.android.types.responses.MedicalCaseBodyDeleteResponse;
import mobi.medbook.android.types.responses.MedicalCaseBodyResponse;
import mobi.medbook.android.types.responses.NewCommentResponse;
import mobi.medbook.android.types.responses.NotificationsResponse;
import mobi.medbook.android.types.responses.PresentationResultResponse;
import mobi.medbook.android.types.responses.PushNotificationBeforeStartVisitResponse;
import mobi.medbook.android.types.responses.ReactionNotificationResponse;
import mobi.medbook.android.types.responses.RestoreInfoRequest;
import mobi.medbook.android.types.responses.StartVisitResponse;
import mobi.medbook.android.types.responses.TestResultsResponse;
import mobi.medbook.android.types.responses.UpdateStatusVisitResponse;
import mobi.medbook.android.types.responses.UserDashboardVisitsResponse;
import mobi.medbook.android.types.responses.UserFishkaCardDeleteResponse;
import mobi.medbook.android.types.responses.UserFishkaCardsResponse;
import mobi.medbook.android.types.responses.UserInfo;
import mobi.medbook.android.types.responses.UserInfoResponse;
import mobi.medbook.android.types.responses.UserNewsResponse;
import mobi.medbook.android.types.responses.UserNotificationReactionResponse;
import mobi.medbook.android.types.responses.UserRelationVisitsResponse;
import mobi.medbook.android.types.responses.UserVisitsResponse;
import mobi.medbook.android.types.responses.VisitEndResponse;
import mobi.medbook.android.types.visits.AnswerDoctorResult;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiRestModule {

//    @GET("specialization-translation")
//    Call<List<SpecializationTranslate>> getSpecializations(@Query("name") String query);

    @GET("specialization?expand=translations&show_doctor=1&is_medpred=0&per-page=666")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<SpecializationItems> getSpecializations();

    @GET("medical-institution-translation")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<List<MedicalInstitutionTranslate>> getMedicalInstitutes();

    @GET("nearest-medical-institution")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<NearestMedicalInstitutionItems> getNearestMedicalInstitutes(@Query("lat") double lat, @Query("lon") double lon, @Query("qty") int qtu);

    @POST("register")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<RegistrationInfo> register(@Body Register body);

    @POST("authorize")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AuthorizeInfo> authorize(@Body AuthorizeRequest body);

    @POST("request-password-reset")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<RestoreInfoRequest> restore(@Body RestoreRequest body);

    @POST("access-token")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AccessTokenInfo> accessToken(@Body AccessToken body);

    @POST("access-refresh")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AccessTokenInfo> refreshAccessToken(@Body RefreshAccessTokenRequest body);

    @GET("user?expand=specialization.translations,medicalInstitution.translations,medicalInstitution.city.translations")
    Call<UserInfo> getUser();

    @PATCH("user/{user_id}")
    Call<UserInfo> updateStatusTermsAndAgreements(@Path(value = "user_id", encoded = true) int userId, @Body TermsAgreementRequest body);

    @PATCH("user/{user_id}")
    Call<UserInfoResponse> updateStatusPointsAgreements(@Path(value = "user_id", encoded = true) int userId, @Body PointsAgreementRequest body);

    @PATCH("user/{user_id}")
    Call<UserInfo> updatePhone(@Path(value = "user_id", encoded = true) int userId, @Body PhoneUserUpdate body);

    @GET("agreement")
    Call<AgreementResponse> getAgreement(@Query("agreement_type_id") int agreement_type_id, @Query("expand") String expand);
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * UserContent * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    @GET("user-product-materials-content")
    Call<MaterialsResponse> getMaterials();

    @GET("product-translation/{product_translation_id}")
    Call<MaterialTranslateResponse> getMaterialTranslate(@Path(value = "product_translation_id", encoded = true) int productTranslationId);


    @POST("test-result/batch-save")
    Call<TestResultsResponse> sendTestResult(@Body RequestTest[] body);

    @PATCH("user-presentation-content/{presentation_id}")
    Call<PresentationResultResponse> sendTimeResultPresentation(@Path(value = "presentation_id", encoded = true) int presentationId, @Body RequestResultTime body);

    @PATCH("user-video-content/{video_id}")
    Call<TestResultsResponse> sendTimeResultVideo(@Path(value = "video_id", encoded = true) int videoId, @Body RequestResultTime body);

    @GET("user-notification-reaction")
    Call<UserNotificationReactionResponse> userNotificationReaction();
    /*= = = = = = = = = = = * * * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * END UserContent * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * * * = = = = = = = = = =*/

    @GET("device")
    Call<DevicesResponse> getDevice();

    @POST("device")
    Call<DevicesResponse> newDevice(@Body DeviceRequest body);

    @PATCH("device/{id}")
    Call<DevicesResponse> updatePushToken(@Path(value = "id", encoded = true) int id, @Body DeviceRequest body);

    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = * * * UserNotificationContent * * * = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/

    //user-notification-content?expand=notification,notification.translations,notification.notificationResultType
    // &from=1556769441&to=1557561008&notification_result_accepted=0&per-page=5&sort=-time_from
    @GET("user-notification-content?expand=notification,notification.translations,notification.notificationResultType&sort=-time_from")
    Call<NotificationsResponse> getNotifications(@Query("from")  long from,
                                                 @Query("to") long to,
                                                 @Query("notification_result_accepted")  int notification_result_accepted,
                                                 @Query("per-page") double per_page);

    @PATCH("user-notification-content/{id}")
    Call<ReactionNotificationResponse> reactionNotification(@Path(value = "id", encoded = true) int id, @Body ReactionNotificationRequest body);
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * END UserNotificationContent * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/


    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = = * * * POINTS * * * = = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    @GET("points-transaction-history?expand=translations&sort=-created_at")
    Call<HistoryExchangeResponse> getTransactionHistoryAll();

    @GET("points-transaction-history?expand=translations&sort=-created_at")
    Call<HistoryExchangeResponse> getTransactionHistory(@Query("created_from") long created_from, @Query("created_to") long created_to);

    @GET("user-fishka-card")
    Call<UserFishkaCardsResponse> getUserFishkaCard();

    @DELETE("user-fishka-card/{id}")
    Call<UserFishkaCardDeleteResponse> deleteUserFishkaCard(@Path(value = "id", encoded = true) int id);

    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * END POINTS * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/


    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = = * * * VISITS * * * = = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/

    @GET("user-relation?expand=userOne,userTwo&per-page=333")
    Call<UserRelationVisitsResponse>getUsersRelationForVisits();

    @POST("user-visit-create")
    Call<CreateUserVisitResponse>createUserVisit(@Body CreateUserVisitRequest body);


    @GET("user-calendar-content?expand=visit,event,partner&per-page=2000")
    Call<UserVisitsResponse>getUserVisits();

    @GET("user-calendar-content?expand=visit,event,partner_accepted")
    Call<UserVisitsResponse>getUserVisits(@Query("created_from") long from, @Query("created_to") long to);

    @PATCH("user-visit-decline/{id}")
    Call<UpdateStatusVisitResponse>visitDecline(@Path(value = "id", encoded = true) int id);

    @PATCH("user-visit-accept/{id}")
    Call<UpdateStatusVisitResponse>visitAccept(@Path(value = "id", encoded = true) int id);

    @PATCH("user-visit-start/{id}")
    //@Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<StartVisitResponse>startVisit(@Path(value = "id", encoded = true) int id);

    @PATCH("user-visit-start/{id}")
    Call<StartVisitResponse>startVisitQR( @Path(value = "id", encoded = true) int id, @Body StartVisitRequest body);

    @PATCH("user-visit-questionnaire/{id}")
    Call<GetUserVisitQuestionnaireMPResponse> getUserVisitQuestionnaireMP(@Path(value = "id", encoded = true) int id);

    @PATCH("user-visit-questionnaire/{id}")
    Call<GetUserVisitQuestionnaireDoctorResponse> getUserVisitQuestionnaireDoctor(@Path(value = "id", encoded = true) int id);


    @PATCH("visit-medpred-result/{id}")
    Call<MedPredResultResponse> visitMedPredResult(@Path(value = "id", encoded = true) int id, @Body ResultMPAncetaRequest body);

    @POST("visit-doctor-result/batch-save")
    Call<DoctorAncetaResultResponse> visitDoctorResult(@Body AnswerDoctorResult[]  body);

    @PATCH("user-visit-end/{id}")
    Call<VisitEndResponse> visitEnd(@Path(value = "id", encoded = true) int id);

    @PATCH("user-visit-change-time/{id}")
    Call<ChangeTimeVisitResponse> changeTime(@Path(value = "id", encoded = true) int id, @Body ChangeTimeVisitRequest body);

    @GET("user-dashboard-visit?per-page=5")
    Call<UserDashboardVisitsResponse> getDashboardVisits();


    @GET("user-visit-before-start/{id}")
    Call<PushNotificationBeforeStartVisitResponse> pushNotificationBeforeStartVisit(@Path(value = "id", encoded = true) int id);
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * END VISITS * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/


    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * NEWS * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/

    @GET("user-news-article-content?expand=newsArticle,newsArticle.newsArticleType,newsArticle.translations,newsArticle.like,newsArticle.liked,newsArticle.comments_count&per-page=1000")
    Call<UserNewsResponse>getUserNews();

    @GET("/api/v1/icod")
    Call<IcodResponse>getIcod(@Query("owner_id")  int owner_id, @Query("per-page")  int per_page, @Query("expand")  String expand);

    @GET("icod?owner=1&per-page=1000&expand=translations")
    Call<IcodResponse>getIcod();

    @GET("drugs")
    Call<DrugsResponse>getDrugs(@Query("title")  String search, @Query("per-page")  int per_page, @Query("our")  int our);


    @Multipart
    @POST("upload-image")
    Call<ImageLoadResponse> upload(@Part MultipartBody.Part file);

    @DELETE("news-clinical-case/{id}")
    Call<MedicalCaseBodyDeleteResponse> deleteMedicalCase(@Path(value = "id", encoded = true) int id);

    @POST("news-clinical-case")
    Call<MedicalCaseBodyResponse> createMedicalCase(@Body RequestMedicalCaseBody requestMedicalCaseBody);

    @POST("pharm-advice")
    Call<NewReferenceResponse> createReferenseCase(@Body NewRequestReference requestMedicalCaseBody);

    @PATCH("news-clinical-case/{id}")
    Call<MedicalCaseBodyResponse> editMedicalCase(@Path(value = "id", encoded = true) int id, @Body RequestMedicalCaseBody requestMedicalCaseBody);

    @GET("news-clinical-case")
    Call<MedicalCaseBodyResponse> getMyMedicalCases(@Query("author_id")  int author_id, @Query("expand") String expand, @Query("per-page") int per_page, @Query("sort") String sort);

    @GET("news-clinical-case")
    Call<MedicalCaseBodyResponse> getMedicalCase(@Query("id")  int id, @Query("expand") String expand);

    @GET("news-article-like-unlike/{id}")
    Call<LikeUnlikeResponse> like_unlike(@Path(value = "id", encoded = true) int id);

    @GET("user-news-article-content")
    Call<UserNewsResponse>  getComments(@Query("news_article_id")  int news_article_id, @Query("expand") String expand);

    @POST("comment")
    Call<NewCommentResponse> newComment(@Body NewCommentRequest body);

    @DELETE("comment/{id}")
    Call<DeleteCommentResponse> deleteComment(@Path(value = "id", encoded = true) int id);
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * END NEWS * * * * = = = = = = = = = =*/
    /*= = = = = = = = = = = * * * * * * * * * * * * = = = = = = = = = =*/

}
