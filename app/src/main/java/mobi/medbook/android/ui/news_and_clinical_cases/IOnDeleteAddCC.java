package mobi.medbook.android.ui.news_and_clinical_cases;

import mobi.medbook.android.types.news.CCImage;
import mobi.medbook.android.types.news.DrugSelected;
import mobi.medbook.android.types.news.IcodSelected;


public interface IOnDeleteAddCC {
    public void onDeleteIcod(IcodSelected icodSelected);

    public void onDeleteDrug(DrugSelected drugSelected);

    public void onDeleteImage(CCImage ccImage);
}
