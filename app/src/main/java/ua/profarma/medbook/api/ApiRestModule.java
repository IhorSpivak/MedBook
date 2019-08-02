package ua.profarma.medbook.api;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
import ua.profarma.medbook.models.NewReferenceResponse;
import ua.profarma.medbook.models.NewRequestReference;
import ua.profarma.medbook.types.MedicalInstitutionTranslate;
import ua.profarma.medbook.types.NearestMedicalInstitutionItems;
import ua.profarma.medbook.types.RegistrationInfo;
import ua.profarma.medbook.types.RequestTest;
import ua.profarma.medbook.types.SpecializationItems;
import ua.profarma.medbook.types.news.ImageLoadResponse;
import ua.profarma.medbook.types.requests.AccessToken;
import ua.profarma.medbook.types.requests.AuthorizeRequest;
import ua.profarma.medbook.types.requests.ChangeTimeVisitRequest;
import ua.profarma.medbook.types.requests.CreateUserVisitRequest;
import ua.profarma.medbook.types.requests.DeviceRequest;
import ua.profarma.medbook.types.requests.NewCommentRequest;
import ua.profarma.medbook.types.requests.PhoneUserUpdate;
import ua.profarma.medbook.types.requests.PointsAgreementRequest;
import ua.profarma.medbook.types.requests.ReactionNotificationRequest;
import ua.profarma.medbook.types.requests.RefreshAccessTokenRequest;
import ua.profarma.medbook.types.requests.Register;
import ua.profarma.medbook.types.requests.RequestMedicalCaseBody;
import ua.profarma.medbook.types.requests.RequestResultTime;
import ua.profarma.medbook.types.requests.RestoreRequest;
import ua.profarma.medbook.types.requests.ResultMPAncetaRequest;
import ua.profarma.medbook.types.requests.StartVisitRequest;
import ua.profarma.medbook.types.requests.TermsAgreementRequest;
import ua.profarma.medbook.types.responses.AccessTokenInfo;
import ua.profarma.medbook.types.responses.AgreementResponse;
import ua.profarma.medbook.types.responses.AuthorizeInfo;
import ua.profarma.medbook.types.responses.ChangeTimeVisitResponse;
import ua.profarma.medbook.types.responses.CreateUserVisitResponse;
import ua.profarma.medbook.types.responses.DeleteCommentResponse;
import ua.profarma.medbook.types.responses.DevicesResponse;
import ua.profarma.medbook.types.responses.DoctorAncetaResultResponse;
import ua.profarma.medbook.types.responses.DrugsResponse;
import ua.profarma.medbook.types.responses.GetUserVisitQuestionnaireDoctorResponse;
import ua.profarma.medbook.types.responses.GetUserVisitQuestionnaireMPResponse;
import ua.profarma.medbook.types.responses.HistoryExchangeResponse;
import ua.profarma.medbook.types.responses.IcodResponse;
import ua.profarma.medbook.types.responses.LikeUnlikeResponse;
import ua.profarma.medbook.types.responses.MaterialTranslateResponse;
import ua.profarma.medbook.types.responses.MaterialsResponse;
import ua.profarma.medbook.types.responses.MedPredResultResponse;
import ua.profarma.medbook.types.responses.MedicalCaseBodyDeleteResponse;
import ua.profarma.medbook.types.responses.MedicalCaseBodyResponse;
import ua.profarma.medbook.types.responses.NewCommentResponse;
import ua.profarma.medbook.types.responses.NotificationsResponse;
import ua.profarma.medbook.types.responses.PresentationResultResponse;
import ua.profarma.medbook.types.responses.PushNotificationBeforeStartVisitResponse;
import ua.profarma.medbook.types.responses.ReactionNotificationResponse;
import ua.profarma.medbook.types.responses.RestoreInfoRequest;
import ua.profarma.medbook.types.responses.StartVisitResponse;
import ua.profarma.medbook.types.responses.TestResultsResponse;
import ua.profarma.medbook.types.responses.UpdateStatusVisitResponse;
import ua.profarma.medbook.types.responses.UserDashboardVisitsResponse;
import ua.profarma.medbook.types.responses.UserFishkaCardDeleteResponse;
import ua.profarma.medbook.types.responses.UserFishkaCardsResponse;
import ua.profarma.medbook.types.responses.UserInfo;
import ua.profarma.medbook.types.responses.UserInfoResponse;
import ua.profarma.medbook.types.responses.UserNewsResponse;
import ua.profarma.medbook.types.responses.UserNotificationReactionResponse;
import ua.profarma.medbook.types.responses.UserRelationVisitsResponse;
import ua.profarma.medbook.types.responses.UserVisitsResponse;
import ua.profarma.medbook.types.responses.VisitEndResponse;
import ua.profarma.medbook.types.visits.AnswerDoctorResult;

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
    Call<DrugsResponse>getDrugs(@Query("title")  String search, @Query("per-page")  int per_page,  @Query("our")  int our);


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
