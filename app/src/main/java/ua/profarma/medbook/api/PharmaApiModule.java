package ua.profarma.medbook.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import ua.profarma.medbook.types.requests.ActivationCodeForExchangeRequest;
import ua.profarma.medbook.types.requests.AddFishkaRequest;
import ua.profarma.medbook.types.requests.CheckQrRequest;
import ua.profarma.medbook.types.requests.CheckUserLikiWikiRequest;
import ua.profarma.medbook.types.requests.ExecuteTransactionRequest;
import ua.profarma.medbook.types.requests.GetActivationCodeRequest;
import ua.profarma.medbook.types.responses.AddFishkaResponse;
import ua.profarma.medbook.types.responses.CheckQrResponse;
import ua.profarma.medbook.types.responses.CheckUserLikiWikiResponse;
import ua.profarma.medbook.types.responses.ExecuteTransactionResponse;
import ua.profarma.medbook.types.responses.VerificationPhoneResponse;

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
