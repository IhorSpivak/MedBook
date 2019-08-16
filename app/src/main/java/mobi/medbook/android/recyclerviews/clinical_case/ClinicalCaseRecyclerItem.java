package mobi.medbook.android.recyclerviews.clinical_case;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.news.MedicalCaseItem;
import mobi.medbook.android.ui.news_and_clinical_cases.IOnSelectCCForView;


public class ClinicalCaseRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private MedicalCaseItem madicalCaseItem;

    public ClinicalCaseRecyclerItem(MedicalCaseItem madicalCaseItem) {
        this.madicalCaseItem = madicalCaseItem;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof ClinicalCaseViewHolder) {
            ((ClinicalCaseViewHolder) holder).init(this, madicalCaseItem);
        }
    }

    @Override
    public int getViewType() {
        return 1;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onSecelectCC(madicalCaseItem.id, madicalCaseItem.news_clinical_case_status_id);
    }

    private IOnSelectCCForView getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectCCForView) {
                return (IOnSelectCCForView) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
