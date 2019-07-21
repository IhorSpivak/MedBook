package ua.profarma.medbook.recyclerviews.drug_selected;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.news.DrugSelected;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnDeleteAddCC;

public class DrugSelectedRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private DrugSelected drugSelected;

    public DrugSelectedRecyclerItem(DrugSelected drug) {
        this.drugSelected = drug;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof DrugSelectedViewHolder) {
            ((DrugSelectedViewHolder) holder).init(this, drugSelected);
        }
    }
    @Override
    public int getViewType() {
        return 0;
    }

    public DrugSelected getDrugSelected() {
        return drugSelected;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onDeleteDrug(drugSelected);
    }

    private IOnDeleteAddCC getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnDeleteAddCC) {
                return (IOnDeleteAddCC) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public DrugSelected getDrug() {
        return drugSelected;
    }
}
