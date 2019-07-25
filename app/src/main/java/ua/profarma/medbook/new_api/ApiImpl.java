package ua.profarma.medbook.new_api;

import java.util.List;

import io.reactivex.Observable;
import ua.profarma.medbook.App;
import ua.profarma.medbook.models.response.BaseResponse;
import ua.profarma.medbook.models.response.ReferenceItem;

public class ApiImpl {

    private ApiInterface mApi;

    public ApiImpl(ApiInterface apiInterface) {
        mApi = apiInterface;
    }

    public Observable<BaseResponse<List<ReferenceItem>>> getListreference() {
        return mApi.getListReference("Bearer " + App.getAccessToken());

    }
}