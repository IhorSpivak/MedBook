package mobi.medbook.android.ui.reference;

import android.content.Context;

import io.reactivex.android.schedulers.AndroidSchedulers;
import mobi.medbook.android.new_api.ApiComponent;


public class ReferencePresenter implements ReferenceContract.Presenter {

    private ReferenceContract.View view;
    private Context context;

    public ReferencePresenter(ReferenceContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void loadLitReference() {
        ApiComponent.provideApi().getListreference()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            view.onReferenceListLoaded(response.getData());
                        },

                        throwable -> { });
    }
}
