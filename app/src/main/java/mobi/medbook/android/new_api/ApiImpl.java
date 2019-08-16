package mobi.medbook.android.new_api;


import java.util.List;

import io.reactivex.Observable;
import mobi.medbook.android.App;
import mobi.medbook.android.models.response.BaseResponse;
import mobi.medbook.android.models.response.ReferenceItem;

public class ApiImpl {

    private ApiInterface mApi;

    public ApiImpl(ApiInterface apiInterface) {
        mApi = apiInterface;
    }

    public Observable<BaseResponse<List<ReferenceItem>>> getListreference() {
        return mApi.getListReference("Bearer " + App.getAccessToken(), Integer.toString(App.getUser().id));

    }

}