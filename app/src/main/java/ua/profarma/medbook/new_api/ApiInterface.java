package ua.profarma.medbook.new_api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ua.profarma.medbook.App;
import ua.profarma.medbook.models.response.BaseResponse;
import ua.profarma.medbook.models.response.ReferenceItem;
import ua.profarma.medbook.types.NearestMedicalInstitutionItems;

interface ApiInterface {


    @GET("pharm-advice?expand=icods,icods.icod,icods.icod.translations,drugs,drugs.drug,patients&per-page=1000")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<List<ReferenceItem>>> getListReference(@Header("Authorization") String key_user);



}
