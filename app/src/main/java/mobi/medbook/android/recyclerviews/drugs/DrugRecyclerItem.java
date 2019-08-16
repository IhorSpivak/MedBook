package mobi.medbook.android.recyclerviews.drugs;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;

import mobi.medbook.android.types.news.Drug;
import mobi.medbook.android.ui.news_and_clinical_cases.IOnSelectDrug;


public class DrugRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private Drug drug;

    public Drug getDrug() {
        return drug;
    }

    public DrugRecyclerItem(Drug drug) {
        this.drug = drug;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof DrugViewHolder) {
            ((DrugViewHolder) holder).init(this, drug);
        }
    }
    @Override
    public int getViewType() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onSelectDrug(drug);
    }

    private IOnSelectDrug getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof IOnSelectDrug) {
                return (IOnSelectDrug) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
