package mobi.medbook.android.recyclerviews.icod_selected;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import mobi.medbook.android.recyclerviews.base.BaseViewHolder;
import mobi.medbook.android.recyclerviews.base.RecyclerItem;

import mobi.medbook.android.types.news.IcodSelected;
import mobi.medbook.android.ui.news_and_clinical_cases.IOnDeleteAddCC;

public class IcodSelectedRecyclerItem extends RecyclerItem implements View.OnClickListener,View.OnLongClickListener {

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

    @Override
    public boolean onLongClick(View v) {
        if (getActivity(v.getContext()) != null)
            getActivity(v.getContext()).onDeleteIcod(icodSelected);
        return false;
    }
}
