package mobi.medbook.android.api;

import mobi.medbook.android.types.requests.ActivationCodeForExchangeRequest;
import mobi.medbook.android.types.requests.AddFishkaRequest;
import mobi.medbook.android.types.requests.CheckQrRequest;
import mobi.medbook.android.types.requests.CheckUserLikiWikiRequest;
import mobi.medbook.android.types.requests.ExecuteTransactionRequest;
import mobi.medbook.android.types.requests.GetActivationCodeRequest;
import mobi.medbook.android.types.responses.AddFishkaResponse;
import mobi.medbook.android.types.responses.CheckQrResponse;
import mobi.medbook.android.types.responses.CheckUserLikiWikiResponse;
import mobi.medbook.android.types.responses.ExecuteTransactionResponse;
import mobi.medbook.android.types.responses.VerificationPhoneResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface PharmaApiModule {

    @POST("getVerificationCode")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<VerificationPhoneResponse> getActivationCode(@Body GetActivationCodeRequest body);


    @POST("checkQR")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<CheckQrResponse> chekQR(@Body CheckQrRequest request);

    @POST("fishkaCheckCard")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<AddFishkaResponse> addCard(@Body AddFishkaRequest addFishkaRequest);


    @POST("getVerificationCode")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<VerificationPhoneResponse> getActivationCodeForExchangeFishka(@Body ActivationCodeForExchangeRequest body);

    @POST("executeTransaction")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ExecuteTransactionResponse> executeTransaction(@Body ExecuteTransactionRequest body);


    @POST("checkUserLikiWiki")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<CheckUserLikiWikiResponse> checkUserLikiWiki(@Body CheckUserLikiWikiRequest body);
}
