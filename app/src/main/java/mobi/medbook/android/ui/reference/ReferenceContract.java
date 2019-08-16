package mobi.medbook.android.ui.reference;

import java.util.List;

import mobi.medbook.android.models.response.ReferenceItem;


interface ReferenceContract {


    interface View {
        void onReferenceListLoaded(List<ReferenceItem> list);



    }

    interface Presenter {

        void loadLitReference();
    }

}
