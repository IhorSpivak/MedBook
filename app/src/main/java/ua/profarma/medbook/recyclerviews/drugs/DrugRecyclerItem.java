package ua.profarma.medbook.recyclerviews.drugs;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.news.Drug;
import ua.profarma.medbook.types.points.HistoryExchange;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnSelectDrug;

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
