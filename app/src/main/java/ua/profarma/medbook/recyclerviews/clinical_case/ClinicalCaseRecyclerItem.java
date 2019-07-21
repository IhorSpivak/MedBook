package ua.profarma.medbook.recyclerviews.clinical_case;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.news.MedicalCaseItem;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnSelectCCForView;
import ua.profarma.medbook.ui.today.tabs.IOnSelectNews;

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
