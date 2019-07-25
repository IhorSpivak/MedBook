package ua.profarma.medbook.ui.reference;

import java.util.List;

import ua.profarma.medbook.models.response.BaseResponse;
import ua.profarma.medbook.models.response.ReferenceItem;

interface ReferenceContract {


    interface View {
        void onReferenceListLoaded(List<ReferenceItem> list);



    }

    interface Presenter {

        void loadLitReference();
    }

}
