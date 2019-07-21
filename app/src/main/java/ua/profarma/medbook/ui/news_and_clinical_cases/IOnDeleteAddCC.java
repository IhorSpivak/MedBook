package ua.profarma.medbook.ui.news_and_clinical_cases;

import ua.profarma.medbook.types.news.CCImage;
import ua.profarma.medbook.types.news.DrugSelected;
import ua.profarma.medbook.types.news.IcodSelected;

public interface IOnDeleteAddCC {
    public void onDeleteIcod(IcodSelected icodSelected);

    public void onDeleteDrug(DrugSelected drugSelected);

    public void onDeleteImage(CCImage ccImage);
}
