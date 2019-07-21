package ua.profarma.medbook.recyclerviews.icod_selected;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import ua.profarma.medbook.recyclerviews.base.BaseViewHolder;
import ua.profarma.medbook.recyclerviews.base.RecyclerItem;
import ua.profarma.medbook.types.news.IcodSelected;
import ua.profarma.medbook.ui.news_and_clinical_cases.IOnDeleteAddCC;

public class IcodSelectedRecyclerItem extends RecyclerItem implements View.OnClickListener {

    private IcodSelected icodSelected;

    public IcodSelectedRecyclerItem(IcodSelected icodSelected) {
        this.icodSelected = icodSelected;
    }

    @Override
    public void fill(BaseViewHolder holder) {
        if (holder instanceof IcodSelectedViewHolder) {
            ((IcodSelectedViewHolder) holder).init(this, icodSelected);
        }
    }
    @Override
    public int getViewType() {
        return 0;
    }

    public IcodSelected getIcodSelected() {
        return icodSelected;
    }

    @Override
    public void onClick(View view) {
        if (getActivity(view.getContext()) != null)
            getActivity(view.getContext()).onDeleteIcod(icodSelected);
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
}
