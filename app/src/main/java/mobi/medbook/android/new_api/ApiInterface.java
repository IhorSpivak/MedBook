package mobi.medbook.android.new_api;

import java.util.List;

import io.reactivex.Observable;
import mobi.medbook.android.models.response.BaseResponse;
import mobi.medbook.android.models.response.ReferenceItem;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface ApiInterface {


    @GET("pharm-advice?expand=icods,icods.icod,icods.icod.translations,drugs,drugs.drug,patients&per-page=1000&")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Observable<BaseResponse<List<ReferenceItem>>> getListReference(@Header("Authorization") String key_user, @Query("author_id" ) String user_id);



}
