package mobi.medbook.android.recyclerviews.drug_selected;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;
import mobi.medbook.android.types.news.DrugSelected;
import mobi.medbook.android.ui.news_and_clinical_cases.IOnDeleteAddCC;


public class DrugSelectedRecyclerItem extends RecyclerItem implements View.OnClickListener, View.OnLongClickListener {

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

    @Override
    public boolean onLongClick(View v) {
        if (getActivity(v.getContext()) != null)
            getActivity(v.getContext()).onDeleteDrug(drugSelected);
        return false;
    }
}
