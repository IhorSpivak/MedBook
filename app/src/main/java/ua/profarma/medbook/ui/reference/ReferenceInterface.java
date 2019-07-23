package ua.profarma.medbook.ui.reference;

import ua.profarma.medbook.types.news.CCImage;
import ua.profarma.medbook.types.news.DrugSelected;
import ua.profarma.medbook.types.news.IcodSelected;

public interface ReferenceInterface {
        public void onDeleteIcod(IcodSelected icodSelected);

        public void onDeleteDrug(DrugSelected drugSelected);


}
